package com.example.translation_management_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.*;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title       = "Translation Management API",
                version     = "1.0.0",
                description = "CRUD + search + export endpoints for translations",
                contact     = @Contact(name="Muhammad Nouman", email="im.nouman123@gmail.com")
        )
)
public class OpenApiConfig { }
