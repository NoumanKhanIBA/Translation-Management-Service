package com.example.translation_management_service.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Swagger UI static assets – 30 days cache
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/")
                .setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS).cachePublic());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CachingInterceptor());
    }

    private static class CachingInterceptor implements HandlerInterceptor {
        @Override
        public void postHandle(HttpServletRequest request,
                               HttpServletResponse response,
                               Object handler,
                               ModelAndView modelAndView) {
            String path = request.getRequestURI();
            if (path.startsWith("/api/translations/export")) {
                // CDN & browsers can cache this response for 5 minutes
                response.setHeader("Cache-Control", "public, max-age=300");
            }
        }
    }
}
