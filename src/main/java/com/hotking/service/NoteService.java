package com.hotking.service;

import com.hotking.dto.ShowAllNotesDto;
import com.hotking.entity.ArticleNote;
import com.hotking.entity.Note;
import com.hotking.entity.PersonalNote;
import com.hotking.entity.QuoteNote;
import com.hotking.repository.NoteRepository;
import com.hotking.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
}
