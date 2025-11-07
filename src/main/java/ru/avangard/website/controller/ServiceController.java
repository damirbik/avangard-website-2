package ru.avangard.website.controller;


import org.springframework.web.multipart.MultipartFile;
import ru.avangard.website.dto.ServiceCreateDTO;
import ru.avangard.website.dto.ServiceUpdateDTO;
import ru.avangard.website.entity.Service;
import ru.avangard.website.entity.Subcategory;
import ru.avangard.website.repository.ServiceShortProjection;
import ru.avangard.website.service.ServiceService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestParam;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;


@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "https://remjest-avangard-testing-e1b1.twc1.net/")

public class ServiceController {

    private final ServiceService serviceService;
    @Value("${upload.dir:uploads}")
    private String uploadDir;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    /**
     * GET /api/services
     * Получить все услуги (базовая информация)
     */
    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        List<Service> services = serviceService.getAllServices();
        return ResponseEntity.ok(services);
    }

    /**
     * GET /api/services/{id}
     * Получить услугу по ID (базовая информация)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long id) {
        return serviceService.getServiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/services/subcategory/{subcategoryId}
     * Получить все услуги по ID подкатегории
     */
    @GetMapping("/subcategory/{subcategoryId}")
    public ResponseEntity<List<Service>> getServicesBySubcategory(@PathVariable Long subcategoryId) {
        List<Service> services = serviceService.findBySubcategorySubcategoryId(subcategoryId);
        return ResponseEntity.ok(services);
    }

    /**
     * GET /api/services/category/{categoryId}
     * Получить все услуги по ID категории
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Service>> getServicesByCategory(@PathVariable Long categoryId) {
        List<Service> services = serviceService.findBySubcategoryCategoryCategoryId(categoryId);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/subcategory/short/{subcategoryId}")
    public ResponseEntity<List<ServiceShortProjection>> getShortBySubcategoryId(@PathVariable Long subcategoryId){
        List<ServiceShortProjection> services = serviceService.findShortBySubcategoryId(subcategoryId);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/category/short/{categoryId}")
    public ResponseEntity<List<ServiceShortProjection>> getServicesByCategoryShort(@PathVariable Long categoryId) {
        List<ServiceShortProjection> services = serviceService.findShortByCategoryId(categoryId);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/search/{alias}")
    public ResponseEntity<Service> getServiceByAlias(@PathVariable String alias) {
        return serviceService.getServiceByAlias(alias)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Обновление preview-картинки
    @PutMapping("/{id}/preview-image")
    public ResponseEntity<Service> updatePreviewImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {

        Service service = serviceService.getServiceById(id)
                .orElseThrow(() -> new RuntimeException("Услуга не найдена"));

        String imageUrl = uploadImage(file); // вызываем метод ниже
        service.setPicLinkPreview(imageUrl);

        Service updated = serviceService.updateService(id, service);
        return ResponseEntity.ok(updated);
    }

    // Обновление main-картинки
    @PutMapping("/{id}/main-image")
    public ResponseEntity<Service> updateMainImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) throws IOException {

        Service service = serviceService.getServiceById(id)
                .orElseThrow(() -> new RuntimeException("Услуга не найдена"));

        String imageUrl = uploadImage(file);
        service.setPicLinkMain(imageUrl);

        Service updated = serviceService.updateService(id, service);
        return ResponseEntity.ok(updated);
    }

    // Вспомогательный метод для загрузки файла
    private String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new IllegalArgumentException("Файл пуст");

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".") ?
                originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";

        String fileName = UUID.randomUUID() + extension;
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        return "/images/" + fileName;
    }


    @PostMapping
    public ResponseEntity<Service> createService(@RequestBody ServiceCreateDTO dto) {
        Service created = (Service) serviceService.createService(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(
            @PathVariable Long id,
            @RequestBody ServiceUpdateDTO dto) {

        if (!serviceService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Service updated = serviceService.partialUpdate(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/services/{id}
     * Удалить услугу по ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        if (!serviceService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
