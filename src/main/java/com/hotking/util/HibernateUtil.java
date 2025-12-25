package com.hotking.util;

import com.hotking.entity.Note;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {

    public static SessionFactory buildSessionFactory(){
        Configuration configuration = new Configuration();
        configuration.configure();
        return configuration.buildSessionFactory();
    }
}
