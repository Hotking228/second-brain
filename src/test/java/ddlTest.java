import com.hotking.entity.Attachment;
import com.hotking.entity.Note;
import com.hotking.entity.Tag;
import com.hotking.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class ddlTest {

    SessionFactory sessionFactory;

    @BeforeEach
    void init() throws IOException {
        sessionFactory = HibernateUtil.buildSessionFactory();
        Tag tag1 = Tag.builder()
                .name("test name")
                .color(Tag.Color.PRIMARY)
                .parent(null)
                .children(null)
                .build();
        Tag tag2 = Tag.builder()
                .name("test name")
                .color(Tag.Color.PRIMARY)
                .parent(tag1)
                .children(null)
                .build();
        Tag tag3 = Tag.builder()
                .name("test name")
                .color(Tag.Color.PRIMARY)
                .parent(tag1)
                .children(null)
                .build();

        Note note = Note.builder()
                .author("test author")
                .title("test title")
                .content("test content")
                .url("test url")
                .tags(List.of(tag2, tag1))
                .build();

        Path path = Paths.get("C:\\images\\Isaak.jpg");

        byte[] image = Files.readAllBytes(path);


        var attachment = Attachment.builder()
                .content(image)
                .contentType(Attachment.ContentType.JPG)
                .note(note)
                .build();

        try(var session = sessionFactory.openSession()){
            session.beginTransaction();

            session.save(tag1);
            session.save(tag2);
            session.save(tag3);

            session.save(note);

            session.save(attachment);

            session.getTransaction().commit();
        }
    }

    @Test
    void readTags(){
        try(var session = sessionFactory.openSession()){
            session.beginTransaction();

            var cb = session.getCriteriaBuilder();
            var criteria = cb.createQuery(Tag.class);
            var tag = criteria.from(Tag.class);
            criteria.select(tag);

            List<Tag> tags = session.createQuery(criteria).list();
            assertThat(tags).hasSizeGreaterThanOrEqualTo(3);

            assertThat(tags.getFirst().getColor().getHex()).isEqualTo(Tag.Color.PRIMARY.getHex());

            session.getTransaction().commit();
        }

        sessionFactory.close();
    }
}
