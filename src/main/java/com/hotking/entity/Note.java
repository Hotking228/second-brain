package com.hotking.entity;


import com.hotking.audit.AuditableEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@OptimisticLocking(type = OptimisticLockType.VERSION)
@DynamicUpdate
@Audited
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Note extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    @ManyToMany
    @JoinTable(name = "note_tag",
    joinColumns = @JoinColumn(name = "note_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @NotAudited
    private List<Tag> tags;
    @Version
    private Long version;
}
