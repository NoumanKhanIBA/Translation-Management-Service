package com.example.translation_management_service.performance;

import com.example.translation_management_service.dto.TranslationDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PerformanceIntegrationTest {

    @LocalServerPort
    int port;
    @Autowired TestRestTemplate rest;
    private HttpHeaders headers;

    @BeforeEach
    void auth() {
        var req = Map.of("username","admin","password","admin123");
        var resp = rest.postForEntity("/api/auth/login", req, Map.class);
        String token = (String) resp.getBody().get("token");
        headers = new HttpHeaders();
        headers.setBearerAuth(token);
    }

    @Test
    void searchEndpoint_under200ms() {
        String url = "http://localhost:" + port + "/api/translations/search?locale=en&page=0&size=20";
        long start = System.nanoTime();
        ResponseEntity<PageWrapper> r = rest.exchange(
                url, HttpMethod.GET, new HttpEntity<>(headers), PageWrapper.class);
        long durationMs = (System.nanoTime() - start)/1_000_000;
        assertThat(durationMs).as("search took ms").isLessThan(200);
        assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void exportEndpoint_under500ms() {
        String url = "http://localhost:" + port + "/api/translations/export?locale=en";
        long start = System.nanoTime();
        ResponseEntity<String> r = rest.exchange(
                url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        long durationMs = (System.nanoTime() - start)/1_000_000;
        assertThat(durationMs).as("export took ms").isLessThan(500);
        assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    // helper to deserialize Spring Page<TranslationDto>
    public static class PageWrapper {
        public List<TranslationDto> content;
        public int totalPages;
        public long totalElements;
        // getters/setters...
    }
}
