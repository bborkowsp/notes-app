package com.notesapp.notesapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    private Boolean isEncrypted;

    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    private Boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @Column(nullable = false)
    @EqualsAndHashCode.Exclude
    private String password;

    public Note(@NonNull String title, @NonNull String content, @NonNull User user, @NonNull String password, @NonNull Boolean isPublic, @NotNull boolean isEncrypted) {
        this.title = title;
        this.content = content;
        this.author = user;
        this.password = password;
        this.isPublic = isPublic;
        this.isEncrypted = isEncrypted;
    }

    public String getPassword() {
        return this.password;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }
}
