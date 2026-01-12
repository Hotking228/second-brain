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

    public static Tag getByName(String name) {
        var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Tag tag = new TagRepository(sessionFactory).getByName(name).get();
        session.getTransaction().commit();
        return tag;
    }

    public static void save(Tag tag) {
        var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        new TagRepository(sessionFactory).save(tag);
        session.getTransaction().commit();
    }
}
