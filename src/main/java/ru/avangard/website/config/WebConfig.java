package ru.avangard.website.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Применяется ко всем эндпоинтам
                .allowedOrigins(
                        "https://remjest-avangard-testing-e1b1.twc1.net", // Основной адрес
                        "http://localhost:3000",
                        "https://avangard-70.ru",
                        "https://www.avangard-70.ru"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Обслуживание изображений из uploads/images/
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:uploads/images/")
                .setCachePeriod(600) // Опционально: настройка кеширования
                .resourceChain(true)
                .addResolver(new PathResourceResolver());

        // Обслуживание видео из uploads/videos/
        registry.addResourceHandler("/videos/**") // URL паттерн для видео
                .addResourceLocations("file:uploads/videos/") // Физическое расположение
                .setCachePeriod(600) // Опционально
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}