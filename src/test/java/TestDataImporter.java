import com.hotking.entity.*;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TestDataImporter {

    public static void importData(SessionFactory sessionFactory) throws IOException {
        Tag tag1 = Tag.builder()
                .name("tag1")
                .color(Tag.Color.PRIMARY)
                .parent(null)
                .children(null)
                .build();
        Tag tag2 = Tag.builder()
                .name("tag2")
                .color(Tag.Color.PRIMARY)
                .parent(tag1)
                .children(null)
                .build();
        Tag tag3 = Tag.builder()
                .name("tag3")
                .color(Tag.Color.PRIMARY)
                .parent(tag1)
                .children(null)
                .build();

        Tag tag4 = Tag.builder()
                .name("tag4")
                .color(Tag.Color.PRIMARY)
                .parent(tag1)
                .children(null)
                .build();

        PersonalNote personalNote = PersonalNote.builder()
                .title("test personal note title")
                .content("test personal note content")
                .tags(List.of(tag2, tag1, tag4))
                .build();

        ArticleNote articleNote = ArticleNote.builder()
                .title("test article note title")
                .content("test article note content")
                .url("test article note url")
                .tags(List.of(tag3, tag1))
                .build();

        QuoteNote quoteNote = QuoteNote.builder()
                .sourceType(QuoteNote.SourceType.BOOK)
                .content("test quote note content")
                .sourceName("test quote note source name")
                .authorName("test quote note author name")
                .url("test quote note url")
                .title("test quote note title")
                .tags(List.of(tag3, tag2))
                .build();

        Path path = Paths.get("C:\\images\\Isaak.jpg");

        byte[] image = Files.readAllBytes(path);


        var attachment = Attachment.builder()
                .content(image)
                .contentType(Attachment.ContentType.JPG)
                .note(personalNote)
                .build();

        try(var session = sessionFactory.openSession()){
            session.beginTransaction();

            session.save(tag1);
            session.save(tag2);
            session.save(tag3);
            session.save(tag4);

            session.save(personalNote);
            session.save(articleNote);
            session.save(quoteNote);

            session.save(attachment);

            session.getTransaction().commit();
        }
    }
}
