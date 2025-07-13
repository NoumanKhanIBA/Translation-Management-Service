package com.example.translation_management_service.dto;
import java.util.List;

import lombok.*;
import org.springframework.stereotype.Service;

public class TranslationUpdateDto {

    private String locale;

    private String key;

    private String content;

    private List<String> tags;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}