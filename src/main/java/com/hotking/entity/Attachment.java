package com.hotking.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

//    @Lob
    @Column(columnDefinition = "BYTEA")
    private byte[] content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id")
    private Note note;

    @AllArgsConstructor
    @Getter
    public enum ContentType{
        PDF("pdf"),
        PNG("png"),
        JPG("jpg"),
        JPEG("jpeg"),
        JSON("json"),
        XML("xml");

        private final String type;
    }
}
