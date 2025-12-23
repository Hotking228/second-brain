package com.hotking.repository;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.*;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class RepositoryBase<E, K extends Serializable> implements Repository<E, K>{


    protected Class<E> clazz;
    protected SessionFactory sessionFactory;

    @Override
    public List<E> findAll() {
        var session = sessionFactory.getCurrentSession();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria)
                .list();
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
