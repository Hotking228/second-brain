package com.hotking.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = false)
@OptimisticLocking(type = OptimisticLockType.VERSION)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("quotenote")
public class QuoteNote extends Note{

    private String url;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SourceType sourceType = SourceType.OTHER;

    private String sourceName;

    private String authorName;

    public enum SourceType{
        NONE,
        BOOK,
        WEB,
        OTHER
    }
}
