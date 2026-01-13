# Second brain
Основная идея проекта - сервис, хранящий заметки пользователя с возможностью добавлять теги на заметки и поиска заметок по тегам.
Главная страница
<img width="1707" height="760" alt="image" src="https://github.com/user-attachments/assets/878bd42b-c1ae-41ff-8bcd-33dbeb1a04b0" />
# Основные инструменты
Основными инструментами, используемыми в процессе разработки являются:
- Gradle + Groovy для реализации жизненного цикла приложения
- Apache Tomcat для деплоя приложения
- Lombok для удобных реализаций стандартных методов и полей
- JUnit 5 для TDD
- HTTPServlets для обработки HTTP запросов
- Postgres как БД
- Hibernate как ORM
Задача проекта - тренировка навыков hibernate, и, поскольку в моих предыдущих проектах все навыки, кроме hibernate уже были рассмотрены подробно, сейчас остановлюсь только на нем.
Hibernate - Object relational mapping - фреймворк, который позволяет разработчику не работать напрямую с базами данных, а работать с сущностями, на слое Repository.
Hibernate использует аннотации для работы с сщуностями, рассмотрим пример:
```java
@Entity//сообщаем hibernate что этот класс является сущностью
@Data//каждая сущность должна содержать конструктор по умолчанию + геттер и сеттер
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@OptimisticLocking(type = OptimisticLockType.VERSION)//оптимистическач блокировка - предовращает все до non repeatable read
@Audited //сообщаем что сущность аудированная
@SuperBuilder//вызываем билдер для базового класса (lombok)
@Inheritance(strategy = InheritanceType.JOINED) // у данной сущности есть наследники, стратегия наследования JOINED является лучшей, поскольку не нарушает нормализацию БД
@NamedEntityGraph(name = "withTags",//граф сущностей для инициализации зависимой таблицы в одном запросе с этой сущностью, поскольку hibernate делает lazy запросы,
                                    //т.е. они откладываются до тех пор, пока мы не обратимся к интересующему нас полю
attributeNodes = {@NamedAttributeNode("tags")
})

public class Note extends AuditableEntity {

    @Id // говорим что поле - id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // говорим что используем SERIAL
    private Integer id;

    private String title;

    private String content;

    @ManyToMany //свзяь между таблицами
    @JoinTable(name = "note_tag",//какую таблицу подключаем
    joinColumns = @JoinColumn(name = "note_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @NotAudited
    private List<Tag> tags;
    @Version//необходимо для OptimisticLocking
    private Long version;
}
```

Note является базовым классом для 3 видов заметок: 
1) пользовтельская заметка
2) цитата
3) заметка из статьи
   
Рассмотрим одного из наследников Note:
```java
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
@OptimisticLocking(type = OptimisticLockType.VERSION)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class QuoteNote extends Note{

    private String url;

    @Enumerated(EnumType.STRING)//говорим что используем название перечисления а не значение, т.е. используем не 0, а "NONE" и тд
    @Builder.Default
    private SourceType sourceType = SourceType.OTHER;

    private String sourceName;

    private String authorName;

    public enum SourceType{
        NONE,
        BOOK,
        WEB,
        OTHER
    }
}
```
# Слой Repository
На данном слое реализуются методы, для запросов в БД.
Рассмотрим интерфейс для CRUD:
```java
public interface Repository<E, K extends Serializable> {

    List<E> findAll(Map<String, Object> properties);

    E save(E entity);

    void delete(E entity);

    void update(E entity);

    Optional<E> findById(K id);
}
```

Каждый класс из данного слоя реализует этот интерфейс:
Удобнее всего использовать Criteria Builder для построения запросов, поскольку не обращаемся напрямую к сущностям, как в hql или QueryDsl:
```java
@AllArgsConstructor
public class RepositoryBase<E, K extends Serializable> implements Repository<E, K>{


    protected Class<E> clazz;
    protected SessionFactory sessionFactory;

    @Override
    public List<E> findAll(Map<String, Object> properties) {
        var session = sessionFactory.getCurrentSession();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(clazz);
        var root = criteria.from(clazz);

        var query = session.createQuery(criteria);

        if (properties != null && properties.containsKey(GraphSemantic.LOAD.getJpaHintName())) {
            EntityGraph<?> entityGraph = (EntityGraph<?>) properties.get(GraphSemantic.LOAD.getJpaHintName());
            query.setHint(GraphSemantic.LOAD.getJpaHintName(), entityGraph);
        }

        return query.getResultList();
    }

    @Override
    public E save(E entity) {
        var session = sessionFactory.getCurrentSession();
        session.save(entity);
        return entity;
    }

    @Override
    public void delete(E entity) {
        var session = sessionFactory.getCurrentSession();
        session.delete(entity);
    }

    @Override
    public void update(E entity) {
        var session = sessionFactory.getCurrentSession();
        session.merge(entity);
    }

    @Override
    public Optional<E> findById(K id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().find(clazz, id));
    }
}
```

Однако hibernate предоставляет возможность использовать hibernate query language(hql) для построения запросов:
```java
 public List<E> findByTag(List<Tag> tags, Map<String, Object> properties){
        var session = sessionFactory.getCurrentSession();

        String hql = """
                select n
                from Note n --следует понимать что hibernate работает с сущностями а не с таблицами
                where (
                select count(distinct t.id)
                from n.tags t
                where t.id in :tagsId
                ) >= :tagsCount
                """;

        var query = session.createQuery(hql, clazz)
                .setParameter("tagsId", tags.stream().map(Tag::getId).toList())
                .setParameter("tagsCount", (long) tags.size());

        if (properties != null && properties.containsKey(GraphSemantic.LOAD.getJpaHintName())) {//это необходимо для подгрузки графа(чтобы hibernate загрузил теги, когда мы ищем заметки)
            EntityGraph<?> entityGraph = (EntityGraph<?>) properties.get(GraphSemantic.LOAD.getJpaHintName());
            query.setHint(GraphSemantic.LOAD.getJpaHintName(), entityGraph);
        }

        return query.list();
    }
```
И последнее удобное средство для построения запросов - QueryDsl:
```java
public List<Note> findByDate(Instant start, Instant finish) {
        var session = sessionFactory.getCurrentSession();
        return new JPAQuery<Note>(session) //уже sql - подобный синтаксис, как в hql, однако мы работаем не со строками
                                           // при этом все так же работаем с сущностями
                .select(QNote.note)
                .from(QNote.note)
                .where(QNote.note.createdAt.between(start, finish))
                .fetch();
    }
```
# Вывод
Таким образом, было разработано веб - приложение с использованием hibernate, и его мощных инструментов для построения запросов
