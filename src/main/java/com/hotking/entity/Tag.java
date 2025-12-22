package com.hotking.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"parent", "children"})
@ToString(exclude = {"parent", "children"})
@Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @Enumerated(EnumType.STRING)
    private Color color;
    private String description;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Tag parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Tag> children = new ArrayList<>();


    @Getter
    @AllArgsConstructor
    public enum Color {
        PRIMARY("#1976D2"),
        SUCCESS("#4CAF50"),
        WARNING("#FF9800"),
        DANGER("#F44336"),
        INFO("#00BCD4"),
        DARK("#212121"),
        LIGHT("#F5F5F5"),

        // Дополнительные
        PURPLE("#9C27B0"),
        PINK("#E91E63"),
        TEAL("#009688"),
        INDIGO("#3F51B5");

        private final String hex;
    }
}
