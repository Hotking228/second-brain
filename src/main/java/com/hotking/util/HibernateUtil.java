package com.hotking.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    public static SessionFactory buildSessionFactory(){
        Configuration configuration = new Configuration();
        configuration.configure();
        var sessionFactory = configuration.buildSessionFactory();
        return sessionFactory;
    }
}
