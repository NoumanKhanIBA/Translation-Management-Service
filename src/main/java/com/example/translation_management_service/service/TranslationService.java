package com.example.translation_management_service.service;

import com.example.translation_management_service.dto.TranslationCreateDto;
import com.example.translation_management_service.dto.TranslationDto;
import com.example.translation_management_service.dto.TranslationUpdateDto;
import com.example.translation_management_service.entity.Tag;
import com.example.translation_management_service.entity.Translation;
import com.example.translation_management_service.repository.TagRepository;
import com.example.translation_management_service.repository.TranslationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

@Service
public class TranslationService {
    private final TranslationRepository translationRepo;
    private final TagRepository tagRepo;

    public TranslationService(TranslationRepository translationRepo, TagRepository tagRepo) {
        this.translationRepo = translationRepo;
        this.tagRepo = tagRepo;
    }

    public TranslationDto create(TranslationCreateDto dto) {
        Translation entity = new Translation(dto.getLocale(), dto.getKey(), dto.getContent());
        if (dto.getTags() != null) {
            entity.setTags(dto.getTags().stream()
                    .map(name -> tagRepo.findByName(name).orElseGet(() -> tagRepo.save(new Tag(name))))
                    .collect(Collectors.toSet()));
        }
        Translation saved = translationRepo.save(entity);
        return mapToDto(saved);
    }

    public Page<TranslationDto> search(String locale,
                                       String keyFragment,
                                       String contentFragment,
                                       java.util.List<String> tags,
                                       Pageable pageable) {

        Page<Translation> results;

        boolean hasLocale = StringUtils.hasText(locale);
        boolean hasKey = StringUtils.hasText(keyFragment);
        boolean hasContent = StringUtils.hasText(contentFragment);
        boolean hasTags = tags != null && !tags.isEmpty();

        if (!hasLocale) {
            // no locale: return all
            results = translationRepo.findAll(pageable);
        } else if (hasTags) {
            results = translationRepo.findByLocaleAndTags(locale, tags, pageable);
        } else if (hasKey) {
            results = translationRepo.findByLocaleAndKeyContaining(locale, keyFragment, pageable);
        } else if (hasContent) {
            results = translationRepo.findByLocaleAndContentContaining(locale, contentFragment, pageable);
        } else {
            // only locale provided: return by locale
            results = translationRepo.findByLocale(locale, pageable);
        }

        return results.map(this::mapToDto);
    }

    public TranslationDto getById(Long id) {
        Translation t = translationRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Translation not found: " + id));
        return mapToDto(t);
    }

    public TranslationDto update(Long id, TranslationUpdateDto dto) {
        Translation existing = translationRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Translation not found: " + id));

        if (StringUtils.hasText(dto.getLocale()))   existing.setLocale(dto.getLocale());
        if (StringUtils.hasText(dto.getKey()))      existing.setKey(dto.getKey());
        if (StringUtils.hasText(dto.getContent()))  existing.setContent(dto.getContent());
        if (dto.getTags() != null) {
            existing.setTags(dto.getTags().stream()
                    .map(name -> tagRepo.findByName(name)
                            .orElseGet(() -> tagRepo.save(new Tag(name))))
                    .collect(Collectors.toSet()));
        }

        return mapToDto(translationRepo.save(existing));
    }
    private TranslationDto mapToDto(Translation entity) {
        return new TranslationDto(
                entity.getId(),
                entity.getLocale(),
                entity.getKey(),
                entity.getContent(),
                entity.getTags().stream()
                        .map(Tag::getName)
                        .collect(Collectors.toList())
        );
    }
}
