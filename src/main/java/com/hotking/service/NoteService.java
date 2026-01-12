package com.hotking.service;

import com.hotking.dto.ShowAllNotesDto;
import com.hotking.entity.*;
import com.hotking.repository.NoteRepository;
import com.hotking.repository.TagRepository;
import com.hotking.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;

import javax.swing.text.html.Option;
import java.util.*;

public class NoteService {
    public static List<Note> getAllNotes() {
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJpaHintName(),
                sessionFactory.getCurrentSession().getEntityGraph("withTags")
        );
        List<Note> list = new NoteRepository<Note>(sessionFactory, Note.class).findAll(properties);
        session.getTransaction().commit();
        return list;

    }

    public static Note getNoteById(Integer noteId) {
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Optional<PersonalNote> personalNote = new NoteRepository<>(sessionFactory, PersonalNote.class).findById(noteId);
        Optional<ArticleNote> articleNote = new NoteRepository<>(sessionFactory, ArticleNote.class).findById(noteId);
        Optional<QuoteNote> quoteNote = new NoteRepository<>(sessionFactory, QuoteNote.class).findById(noteId);
        session.getTransaction().commit();

        if(articleNote.isPresent()){
            return articleNote.get();
        } else if(quoteNote.isPresent()){
            return quoteNote.get();
        } else{
            return personalNote.get();
        }
    }

    public static void saveNote(String title, String content, String url, String author, String sourceName, String sourceTypeName, List<Integer> tagsId) {
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Tag> tags = new TagRepository(sessionFactory).findByIds(tagsId);
        if(!author.isEmpty() || !sourceName.isEmpty() || !Objects.equals(sourceTypeName, "NONE")){
            QuoteNote.SourceType sourceType = Arrays.stream(QuoteNote.SourceType.values())
                    .filter(type -> type.name().equals(sourceTypeName))
                    .findFirst()
                    .get();
            QuoteNote quoteNote = QuoteNote.builder()
                    .title(title)
                    .content(content)
                    .sourceName(sourceName)
                    .sourceType(sourceType)
                    .authorName(author)
                    .tags(tags)
                    .url(url)
                    .build();
            new NoteRepository<QuoteNote>(sessionFactory, QuoteNote.class)
                    .save(quoteNote);
        } else if(!url.isEmpty()) {
            ArticleNote articleNote = ArticleNote.builder()
                    .content(content)
                    .url(url)
                    .title(title)
                    .build();
            new NoteRepository<ArticleNote>(sessionFactory, ArticleNote.class)
                    .save(articleNote);
        } else {
            PersonalNote personalNote = PersonalNote.builder()
                    .title(title)
                    .content(content)
                    .build();
            new NoteRepository<PersonalNote>(sessionFactory, PersonalNote.class)
                    .save(personalNote);
        }
        session.getTransaction().commit();
    }

    public static List<Note> getNotesByTags(List<String> tagsId) {
        var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Tag> tags = new ArrayList<>();
        TagRepository tagRepository = new TagRepository(sessionFactory);
        for(int i = 0; i < tagsId.size(); i++){
            tags.add(tagRepository.findById(Integer.parseInt(tagsId.get(i))).get());
        }
        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJpaHintName(),
                sessionFactory.getCurrentSession().getEntityGraph("withTags")
        );
        List<Note> notes = new NoteRepository<Note>(sessionFactory, Note.class).findByTag(tags, properties);
        session.getTransaction().commit();
        return notes;
    }

    public static void delete(Integer noteId) {
        var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        NoteRepository<Note> noteRepository = new NoteRepository<Note>(sessionFactory, Note.class);
        Note note = noteRepository.findById(noteId).get();
        noteRepository.delete(note);
        session.getTransaction().commit();
    }
}
