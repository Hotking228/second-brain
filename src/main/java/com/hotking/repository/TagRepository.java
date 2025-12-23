package com.hotking.repository;

import com.hotking.entity.Tag;
import org.hibernate.SessionFactory;

public class TagRepository extends RepositoryBase<Tag, Integer>{

    public TagRepository(SessionFactory sessionFactory){
        super(Tag.class, sessionFactory);
    }
}
