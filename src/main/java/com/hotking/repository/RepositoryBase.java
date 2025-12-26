package com.hotking.repository;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.*;
import org.hibernate.SessionFactory;
import org.hibernate.graph.GraphSemantic;

import javax.persistence.EntityGraph;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class RepositoryBase<E, K extends Serializable> implements Repository<E, K>{


    protected Class<E> clazz;
    protected SessionFactory sessionFactory;

    @Override
    public List<E> findAll(Map<String, Object> properties) {
        var session = sessionFactory.getCurrentSession();
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(clazz);
        var root = criteria.from(clazz);

        // Создаём query
        var query = session.createQuery(criteria);

        // Если есть EntityGraph в properties - применяем как hint
        if (properties != null && properties.containsKey(GraphSemantic.LOAD.getJpaHintName())) {
            EntityGraph<?> entityGraph = (EntityGraph<?>) properties.get(GraphSemantic.LOAD.getJpaHintName());
            query.setHint(GraphSemantic.LOAD.getJpaHintName(), entityGraph);
        }

        return query.getResultList();
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
