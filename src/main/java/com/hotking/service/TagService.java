package com.hotking.service;

import com.hotking.entity.Tag;
import com.hotking.repository.TagRepository;
import com.hotking.util.HibernateUtil;

import java.util.List;

public class TagService {

    public static List<Tag> getAllTags(){
        var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        var tags = new TagRepository(sessionFactory).findAll(null);
        session.getTransaction().commit();
        return  tags;
    }
}
