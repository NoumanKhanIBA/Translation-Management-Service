package com.example.translation_management_service.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.translation_management_service.util.JwtUtil;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

@SpringBootTest
@AutoConfigureMockMvc
class TranslationControllerIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired
    JwtUtil jwtUtil;

    private String token;

    @BeforeEach
    void login() throws Exception {
        String body = """
            {"username":"admin","password":"admin123"}
            """;
        MvcResult res = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();

        var json = res.getResponse().getContentAsString();
        // naive extract token
        token = json.replaceAll("^.*\"token\"\\s*:\\s*\"([^\"]+)\".*$","$1");
    }

    @Test
    void whenCreateAndGet_then200() throws Exception {
        String create = """
            {
              "locale":"fr",
              "key":"test.hello",
              "content":"Bonjour",
              "tags":["web"]
            }
            """;
        // Create
        MvcResult cr = mockMvc.perform(post("/api/translations")
                        .header("Authorization","Bearer "+token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(create))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andReturn();

        // Get by ID
        String id = JsonPath.read(cr.getResponse().getContentAsString(), "$.id").toString();
        mockMvc.perform(get("/api/translations/"+id)
                        .header("Authorization","Bearer "+token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Bonjour"));
    }

    @Test
    void searchByLocale_shouldReturnPaged() throws Exception {
        mockMvc.perform(get("/api/translations/search")
                        .param("locale","en")
                        .param("page","0").param("size","10")
                        .header("Authorization","Bearer "+token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}
