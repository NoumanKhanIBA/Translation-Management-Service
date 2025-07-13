package com.example.translation_management_service.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;


public class TranslationCreateDto {
    @NotNull
    @Size(min = 2, max = 5)
    private String locale;

    @NotBlank
    private String key;

    @NotBlank
    private String content;

    private List<String> tags;


    public TranslationCreateDto(String locale, String key, String content, List<String> tags) {
        this.locale = locale;
        this.key = key;
        this.content = content;
        this.tags = tags;
    }

    // getters and setters
    public String getLocale() { return locale; }
    public void setLocale(String locale) { this.locale = locale; }
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}