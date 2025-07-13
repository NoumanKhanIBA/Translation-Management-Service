package com.example.translation_management_service.repository;

import com.example.translation_management_service.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
        Optional<Tag> findByName(String name);
}
