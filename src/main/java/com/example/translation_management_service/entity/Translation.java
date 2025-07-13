package com.example.translation_management_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "translation",
        uniqueConstraints = @UniqueConstraint(columnNames = {"locale", "translation_key"}),
        indexes = {
                @Index(columnList = "locale"),
                @Index(columnList = "translation_key")
        }
)

public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5)
    private String locale;

    @Column(name = "translation_key", nullable = false)
    private String key;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToMany
    @JoinTable(
            name = "translation_tags",
            joinColumns = @JoinColumn(name = "translation_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public Translation() { }

    public Translation(String locale, String key, String content) {
        this.locale = locale;
        this.key = key;
        this.content = content;
    }

    public <E> Translation(long l, String en, String k, String c, Set<E> of) {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() { return id; }
    public String getLocale() { return locale; }
    public void setLocale(String locale) { this.locale = locale; }
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Set<Tag> getTags() { return tags; }
    public void setTags(Set<Tag> tags) { this.tags = tags; }
}