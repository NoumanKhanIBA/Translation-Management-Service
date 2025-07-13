package com.example.translation_management_service.dto;

import java.util.List;

public class TranslationDto {
    private Long id;
    private String locale;
    private String key;
    private String content;
    private List<String> tags;

    public TranslationDto(Long id, String locale, String key, String content, List<String> tags) {
        this.id = id;
        this.locale = locale;
        this.key = key;
        this.content = content;
        this.tags = tags;
    }

    // getters only
    public Long getId() { return id; }
    public String getLocale() { return locale; }
    public String getKey() { return key; }
    public String getContent() { return content; }
    public List<String> getTags() { return tags; }
}
