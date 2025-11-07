package ru.avangard.website.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@CrossOrigin(origins = "https://remjest-avangard-testing-e1b1.twc1.net/")

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:uploads/images/")
                .setCachePeriod(3600) // Опционально: настройка кеширования
                .resourceChain(true)
                .addResolver(new PathResourceResolver());

        registry.addResourceHandler("/videos/**")
                .addResourceLocations("file:uploads/videos/")
                .setCachePeriod(3600) // Опционально: настройка кеширования
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}