package com.example.translation_management_service.service;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import com.example.translation_management_service.dto.TranslationCreateDto;
import com.example.translation_management_service.dto.TranslationDto;
import com.example.translation_management_service.entity.Tag;
import com.example.translation_management_service.entity.Translation;
import com.example.translation_management_service.repository.TagRepository;
import com.example.translation_management_service.repository.TranslationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class TranslationServiceTest {

    @Mock
    TranslationRepository translationRepo;
    @Mock
    TagRepository tagRepo;
    @InjectMocks TranslationService service;

    @Test
    void create_shouldPersistNewTagsAndTranslation() {
        var dto = new TranslationCreateDto("en","k1","c1",List.of("t1","t2"));

        // tagRepo.findByName -> empty so service will call save()
        given(tagRepo.findByName("t1")).willReturn(Optional.empty());
        given(tagRepo.findByName("t2")).willReturn(Optional.empty());

        // whenever save(any Tag) is called, assign an ID based on its name
        given(tagRepo.save(any(Tag.class))).willAnswer(inv -> {
            Tag t = inv.getArgument(0);
            t.setId(t.getName().equals("t1") ? 10L : 20L);
            return t;
        });

        // translationRepo.save(...) echoes back with ID
        given(translationRepo.save(any(Translation.class)))
                .willAnswer(inv -> {
                    Translation t = inv.getArgument(0);
                    t.setId(5L);
                    return t;
                });

        TranslationDto result = service.create(dto);

        assertThat(result.getId()).isEqualTo(5L);
        assertThat(result.getTags()).containsExactlyInAnyOrder("t1","t2");
    }


    @Test
    void search_withLocaleOnly_callsFindByLocale() {
        Page<Translation> page = new PageImpl<>(List.of(
                new Translation(1L,"en","k","c",Set.of())
        ));
        given(translationRepo.findByLocale("en", PageRequest.of(0,10)))
                .willReturn(page);

        var result = service.search("en", null, null, List.of(), PageRequest.of(0,10));

        assertThat(result.getContent()).hasSize(1);
        then(translationRepo).should().findByLocale("en", PageRequest.of(0,10));
    }

    // ... more tests for update, search by key/content, search by tags, exportJson
}
