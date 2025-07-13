package com.example.translation_management_service.repository;

import com.example.translation_management_service.entity.Translation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TranslationRepository extends JpaRepository<Translation, Long> {
    Page<Translation> findByLocaleAndKeyContaining(String locale, String key, Pageable pageable);
    Page<Translation> findByLocaleAndContentContaining(String locale, String content, Pageable pageable);
    Page<Translation>findByLocale(String locale,Pageable pageable);

    @Query("SELECT t FROM Translation t JOIN t.tags tg WHERE t.locale = :locale AND tg.name IN :tagNames")
    Page<Translation> findByLocaleAndTags(@Param("locale") String locale,
                                          @Param("tagNames") List<String> tagNames,
                                          Pageable pageable);
}