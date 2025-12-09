package ru.avangard.website.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avangard.website.dto.ServiceCreateDTO;
import ru.avangard.website.dto.ServiceUpdateDTO;
import ru.avangard.website.entity.Subcategory;
import ru.avangard.website.repository.IServiceRepository;
import ru.avangard.website.repository.ISubcategoryRepository;
import ru.avangard.website.repository.ServiceShortProjection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ServiceService {

    public final IServiceRepository serviceRepository;
    private final ISubcategoryRepository subcategoryRepository;

    @Value("${upload.dir:uploads}")
    private String uploadDir;

    public ServiceService(IServiceRepository serviceRepository, ISubcategoryRepository subcategoryRepository) {
        this.serviceRepository = serviceRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    public Optional<ru.avangard.website.entity.Service> getServiceByAlias(String alias) {
        return serviceRepository.findByAlias(alias);
    }

    public List<ru.avangard.website.entity.Service> findBySubcategorySubcategoryId(Long id){
        return serviceRepository.findBySubcategorySubcategoryId(id);
    }

    public List<ru.avangard.website.entity.Service> findBySubcategoryCategoryCategoryId(Long id){
        return serviceRepository.findBySubcategoryCategoryCategoryId(id);
    }

    public List<ru.avangard.website.entity.Service> findAllWithSubcategoryAndCategory(){
        return serviceRepository.findAllWithSubcategoryAndCategory();
    }

    public Optional<ru.avangard.website.entity.Service> findByIdWithSubcategoryAndCategory(Long serviceId){
        return serviceRepository.findByIdWithSubcategoryAndCategory(serviceId);
    }

    public List<ru.avangard.website.entity.Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public List<ServiceShortProjection> findShortBySubcategoryId(Long id){
        return serviceRepository.findShortBySubcategoryId(id);
    }

    public Optional<ru.avangard.website.entity.Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public ru.avangard.website.entity.Service createService(ru.avangard.website.entity.Service service) {
        return serviceRepository.save(service);
    }

    public ru.avangard.website.entity.Service updateService(Long id, ru.avangard.website.entity.Service service) {
        service.setServiceId(id);
        return serviceRepository.save(service);
    }

    public List<ServiceShortProjection> findShortByCategoryId(Long id) {
        return serviceRepository.findShortByCategoryId(id);
    }

    private void deleteFileIfPresent(String relativePath) {
        if (relativePath == null || relativePath.isBlank()) {
            return;
        }
        try {
            // Убираем начальный слэш, если он есть (например, "/images/photo.jpg" -> "images/photo.jpg")
            String cleanPath = relativePath.startsWith("/") ? relativePath.substring(1) : relativePath;
            Path filePath = Paths.get(uploadDir, cleanPath).toAbsolutePath().normalize();

            // Дополнительная проверка безопасности: убедимся, что путь внутри uploadDir
            if (filePath.startsWith(Paths.get(uploadDir).toAbsolutePath())) {
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                    System.out.println("Файл успешно удалён: " + filePath);
                }
            }
        } catch (IOException e) {
            // Лучше залогировать ошибку, чем прерывать удаление из БД
            System.err.println("Ошибка при удалении файла: " + relativePath + " - " + e.getMessage());
        }
    }

    @Transactional
    public void deleteService(Long id) {
        ru.avangard.website.entity.Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Услуга не найдена"));

        // Удаляем все файлы, связанные с этой услугой
        deleteFileIfPresent(service.getPicLinkPreview());
        deleteFileIfPresent(service.getPicLinkMain());
        deleteFileIfPresent(service.getVideoLink());

        // Удаляем запись из базы данных
        serviceRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return serviceRepository.existsById(id);
    }

    public ru.avangard.website.entity.Service partialUpdate(Long id, ServiceUpdateDTO dto) {
        ru.avangard.website.entity.Service existing = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Услуга не найдена"));

        String oldPreview = existing.getPicLinkPreview();
        String oldMain = existing.getPicLinkMain();
        String oldVideo = existing.getVideoLink();

        if (dto.getTitle() != null) existing.setTitle(dto.getTitle());
        if (dto.getMainText() != null) existing.setMainText(dto.getMainText());
        if (dto.getPicLinkPreview() != null) existing.setPicLinkPreview(dto.getPicLinkPreview());
        if (dto.getExtraText() != null) existing.setExtraText(dto.getExtraText());
        if (dto.getPrice() != null) existing.setPrice(dto.getPrice());
        if (dto.getImportant() != null) existing.setImportant(dto.getImportant());
        if (dto.getPicLinkMain() != null) existing.setPicLinkMain(dto.getPicLinkMain());
        if (dto.getMetaTitle() != null) existing.setMetaTitle(dto.getMetaTitle());
        if (dto.getMetaDescription() != null) existing.setMetaDescription(dto.getMetaDescription());
        if (dto.getMetaKeywords() != null) existing.setMetaKeywords(dto.getMetaKeywords());
        if (dto.getSubtitle() != null) existing.setSubtitle(dto.getSubtitle());
        if (dto.getSubText() != null) existing.setSubText(dto.getSubText());
        if (dto.getAlias() != null) existing.setAlias(dto.getAlias());
        if (dto.getVideoLink() != null) existing.setVideoLink(dto.getVideoLink());

        if (dto.getPicLinkPreview() != null && !Objects.equals(oldPreview, dto.getPicLinkPreview())) {
            deleteFileIfPresent(oldPreview);
        }
        if (dto.getPicLinkMain() != null && !Objects.equals(oldMain, dto.getPicLinkMain())) {
            deleteFileIfPresent(oldMain);
        }
        if (dto.getVideoLink() != null && !Objects.equals(oldVideo, dto.getVideoLink())) {
            deleteFileIfPresent(oldVideo);
        }
        return serviceRepository.save(existing);
    }

    public ru.avangard.website.entity.Service createService(ServiceCreateDTO dto) {
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("Название услуги обязательно");
        }
        if (dto.getSubcategoryId() == null) {
            throw new IllegalArgumentException("ID подкатегории обязателен");
        }

        Subcategory subcategory = subcategoryRepository.findById(dto.getSubcategoryId())
                .orElseThrow(() -> new RuntimeException("Подкатегория не найдена: " + dto.getSubcategoryId()));

        ru.avangard.website.entity.Service service = new ru.avangard.website.entity.Service();
        service.setTitle(dto.getTitle());
        service.setMainText(dto.getMainText());
        service.setPicLinkPreview(dto.getPicLinkPreview());
        service.setExtraText(dto.getExtraText());
        service.setPrice(dto.getPrice());
        service.setImportant(dto.getImportant());
        service.setPicLinkMain(dto.getPicLinkMain());
        service.setMetaTitle(dto.getMetaTitle());
        service.setMetaDescription(dto.getMetaDescription());
        service.setMetaKeywords(dto.getMetaKeywords());
        service.setSubtitle(dto.getSubtitle());
        service.setSubText(dto.getSubText());
        service.setAlias(dto.getAlias());
        service.setVideoLink(dto.getVideoLink());
        service.setSubcategory(subcategory);
        service.setCategoryId(dto.getCategoryId());

        return (ru.avangard.website.entity.Service) serviceRepository.save(service);
    }

    // Вспомогательный метод (можно вынести в утилиты)

}

