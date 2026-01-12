package com.hotking.repository;

import com.hotking.entity.QTag;
import com.hotking.entity.Tag;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class TagRepository extends RepositoryBase<Tag, Integer>{

    public TagRepository(SessionFactory sessionFactory){
        super(Tag.class, sessionFactory);
    }

    public Optional<Tag> getByName(String name) {
        return Optional.ofNullable( new JPAQuery<Tag>(sessionFactory.getCurrentSession())
                .select(QTag.tag)
                .from(QTag.tag)
                .where(QTag.tag.name.eq(name))
                .fetchOne());
    }

    public List<Tag> findByIds(List<Integer> ids){
        return sessionFactory.getCurrentSession()
                .createQuery("select t from Tag t where t.id  in :ids", Tag.class)
                .setParameter("ids", ids)
                .list();
    }
}
