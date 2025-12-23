import com.hotking.entity.*;
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
        TestDataImporter.importData(sessionFactory);
    }

    @Test
    void readTags(){
        try(var session = sessionFactory.openSession()){
            session.beginTransaction();

            var cb = session.getCriteriaBuilder();
            var criteria = cb.createQuery(Tag.class);
            var tag = criteria.from(Tag.class);
            criteria.select(tag);

            ArticleNote articleNote = session.find(ArticleNote.class, 2);
            articleNote.setContent("test new article note content for version change");

            List<Tag> tags = session.createQuery(criteria).list();
            assertThat(tags).hasSizeGreaterThanOrEqualTo(3);

            assertThat(tags.getFirst().getColor().getHex()).isEqualTo(Tag.Color.PRIMARY.getHex());

            session.getTransaction().commit();
        }

        sessionFactory.close();
    }
}
