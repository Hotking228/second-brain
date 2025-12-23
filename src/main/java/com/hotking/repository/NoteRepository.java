package com.hotking.repository;

import com.hotking.entity.*;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.SessionFactory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class NoteRepository<E extends Note> extends RepositoryBase<E, Integer>{

    public NoteRepository(SessionFactory sessionFactory, Class<E> clazz){
        super(clazz, sessionFactory);
    }

    public List<E> findByTag(List<Tag> tags){
        var session = sessionFactory.getCurrentSession();

        StringBuilder sb = new StringBuilder();
        for (Tag tag : tags) {
            sb.append(tag.getId());
        }
        String hql = """
                select n
                from Note n
                where (
                select count(distinct t.id)
                from n.tags t
                where t.id in :tagsId
                ) >= :tagsCount
                """;

        return session.createQuery(hql, clazz)
                .setParameter("tagsId", tags.stream().map(Tag::getId).toList())
                .setParameter("tagsCount", (long) tags.size())
                .list();
    }

    public List<E> findByTitleOrContent(String title, String content) {
        var session = sessionFactory.getCurrentSession();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(clazz);
        var note = criteria.from(clazz);
        criteria.select(note).where(cb.or(cb.like(note.get(Note_.content),content), cb.like(note.get(Note_.title), title)));
        return session.createQuery(criteria)
                .list();
    }

    public List<Note> findByDate(Instant start, Instant finish) {
        var session = sessionFactory.getCurrentSession();
        return new JPAQuery<Note>(session)
                .select(QNote.note)
                .from(QNote.note)
                .where(QNote.note.createdAt.between(start, finish))
                .fetch();
    }

    public List<E> findByType(Class<E> noteClass) {
        var session = sessionFactory.getCurrentSession();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(noteClass);
        var note = criteria.from(noteClass);
        criteria.select(note);
        return session.createQuery(criteria)
                .list();
    }
}
