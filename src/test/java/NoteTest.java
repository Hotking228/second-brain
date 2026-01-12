import com.hotking.entity.ArticleNote;
import com.hotking.entity.Note;
import com.hotking.entity.Tag;
import com.hotking.repository.NoteRepository;
import com.hotking.repository.TagRepository;
import com.hotking.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteTest {

    private SessionFactory sessionFactory;


    @BeforeEach
    public void init() throws IOException {
        sessionFactory = HibernateUtil.buildSessionFactory();
        TestDataImporter.importData(sessionFactory);
    }

    @AfterEach
    public void close(){
        sessionFactory.close();
    }

    @Test
    public void checkFindNotesByTags(){
        var session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        NoteRepository<Note> noteRepository = new NoteRepository<>(sessionFactory, Note.class);
        TagRepository tagRepository = new TagRepository(sessionFactory);
        List<Tag> tags = List.of(tagRepository.findById(1).get(), tagRepository.findById(2).get());
        List<Note> notes = noteRepository.findByTag(tags, null);
        assertThat(notes).hasSize(1);
        System.out.println(notes);
        session.getTransaction().commit();
    }

    @Test
    public void checkFindNotesByContentOrTitle(){
        var session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String title = "test personal note title";
        String content = "test article note content";
        NoteRepository<Note> noteRepository = new NoteRepository<>(sessionFactory, Note.class);
        List<Note>notes = noteRepository.findByTitleOrContent(title, content);

        assertThat(notes).hasSize(2);
        session.getTransaction().commit();
    }

    @Test
    public void checkFindNotesByDate(){
        var session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        NoteRepository<Note> noteRepository = new NoteRepository<>(sessionFactory, Note.class);
        var start = Instant.now().minus(320, ChronoUnit.MILLIS);
        var finish = Instant.now();
        System.out.println("==============================");
        System.out.println(Instant.now());
        System.out.println("==============================");
        List<Note> notes = noteRepository.findByDate(start, finish);
        assertThat(notes).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    public void checkFindNotesByType(){
        var session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        NoteRepository<ArticleNote> noteRepository = new NoteRepository<>(sessionFactory, ArticleNote.class);
        List<ArticleNote> notes = noteRepository.findByType(ArticleNote.class);
        assertThat(notes).hasSizeGreaterThanOrEqualTo(1);
        System.out.println(notes);
    }
}
