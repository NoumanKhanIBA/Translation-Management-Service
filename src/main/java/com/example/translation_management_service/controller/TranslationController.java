package com.example.translation_management_service.controller;

import com.example.translation_management_service.dto.TranslationCreateDto;
import com.example.translation_management_service.dto.TranslationDto;
import com.example.translation_management_service.dto.TranslationUpdateDto;
import com.example.translation_management_service.service.TranslationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/translations")
public class TranslationController {
    private final TranslationService service;

    public TranslationController(TranslationService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TranslationDto> getById(@PathVariable Long id) {
        TranslationDto dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<TranslationDto> create(@Valid @RequestBody TranslationCreateDto dto) {
        TranslationDto created = service.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TranslationDto> update(
            @PathVariable Long id,
            @Valid @RequestBody     TranslationUpdateDto updateDto) {
        TranslationDto dto = service.update(id, updateDto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/search")
    public Page<TranslationDto> search(
            @RequestParam (required = false) String locale,
            @RequestParam(required = false) String key,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) List<String> tags,
            Pageable pageable
    ) {
        return service.search(
                locale,
                key,
                content,
                tags == null ? List.of() : tags,
                pageable
        );
    }
}

