package com.notesapp.notesapp.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isEncrypted;

    @Column(nullable = false)
    private Boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    public Note(String title, String content, User user, Boolean aPublic, boolean isEncrypted) {
        this.title = title;
        this.content = content;
        this.author = user;
        this.isPublic = aPublic;
        this.isEncrypted = isEncrypted;
    }
}
