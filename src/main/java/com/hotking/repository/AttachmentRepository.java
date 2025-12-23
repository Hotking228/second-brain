package com.hotking.repository;

import com.hotking.entity.Attachment;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.SessionFactory;


public class AttachmentRepository extends RepositoryBase<Attachment, Integer>{

    public AttachmentRepository(SessionFactory sessionFactory){
        super(Attachment.class, sessionFactory);
    }
}
