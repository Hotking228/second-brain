package com.hotking.service;

import com.hotking.dto.ShowAllNotesDto;
import com.hotking.entity.Note;
import com.hotking.repository.NoteRepository;
import com.hotking.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.List;

public class NoteService {
    public static List<ShowAllNotesDto> getAllNotes() {
        SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        List<ShowAllNotesDto> list = new NoteRepository<Note>(sessionFactory, Note.class).findAll().stream()
                .map(note -> new ShowAllNotesDto(note.getId(), note.getTitle(), note.getCreatedAt()))
                .toList();
        session.getTransaction().commit();
        return list;

    }
}
