package ru.avangard.website.service;

import org.springframework.stereotype.Service;
import ru.avangard.website.dto.ServiceCreateDTO;
import ru.avangard.website.dto.ServiceUpdateDTO;
import ru.avangard.website.entity.Subcategory;
import ru.avangard.website.repository.IServiceRepository;
import ru.avangard.website.repository.ISubcategoryRepository;
import ru.avangard.website.repository.ServiceShortProjection;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    public final IServiceRepository serviceRepository;
    private final ISubcategoryRepository subcategoryRepository;

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

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return serviceRepository.existsById(id);
    }

    public ru.avangard.website.entity.Service partialUpdate(Long id, ServiceUpdateDTO dto) {
        ru.avangard.website.entity.Service existing = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Услуга не найдена"));

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
        service.setSubcategory(subcategory);

        return (ru.avangard.website.entity.Service) serviceRepository.save(service);
    }

    // Вспомогательный метод (можно вынести в утилиты)

}

