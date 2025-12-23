package com.hotking.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.Entity;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
@OptimisticLocking(type = OptimisticLockType.VERSION)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ArticleNote extends Note {

    private String url;
}
