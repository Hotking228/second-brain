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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    @ManyToMany
    @JoinTable(name = "note_tag",
    joinColumns = @JoinColumn(name = "note_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @NotAudited
    private List<Tag> tags;
    @Version
    private Long version;
}

```
