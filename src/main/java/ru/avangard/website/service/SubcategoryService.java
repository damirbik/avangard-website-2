package ru.avangard.website.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.avangard.website.dto.SubcategoryCreateDTO;
import ru.avangard.website.dto.SubcategoryUpdateDTO;
import ru.avangard.website.entity.Category;
import ru.avangard.website.entity.Subcategory;
import ru.avangard.website.repository.ISubcategoryRepository;
import ru.avangard.website.repository.ICategoryRepository;
import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class SubcategoryService {

    private final ISubcategoryRepository subcategoryRepository;
    private final ICategoryRepository categoryRepository;

    public SubcategoryService(ISubcategoryRepository subcategoryRepository, ICategoryRepository categoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Subcategory> getAllSubcategories() {
        return subcategoryRepository.findAll();
    }

    public Optional<Subcategory> getSubcategoryById(Long id) {
        return subcategoryRepository.findById(id);
    }


    public Optional<Subcategory> getSubcategoryWithServices(Long id) {
        return subcategoryRepository.findByIdWithServices(id);
    }

    public List<Subcategory> getSubcategoriesByCategoryId(Long categoryId) {
        return subcategoryRepository.findByCategoryCategoryId(categoryId);
    }

    public List<Subcategory> getSubcategoriesByCategoryIdOrdered(Long categoryId) {
        return subcategoryRepository.findByCategoryIdOrderBySubcategoryId(categoryId);
    }

    public List<Subcategory> getSubcategoriesByCategoryIdOrderedSpringData(Long categoryId) {
        return subcategoryRepository.findByCategoryCategoryIdOrderBySubcategoryId(categoryId);
    }

    public List<Subcategory> getAllSubcategoriesWithCategories() {
        return subcategoryRepository.findAllWithCategory();
    }

    public Subcategory createSubcategory(SubcategoryCreateDTO dto) {
        if (dto.getSubcategoryName() == null || dto.getSubcategoryName().isBlank()) {
            throw new IllegalArgumentException("Название подкатегории не может быть пустым");
        }
        if (dto.getCategoryId() == null) {
            throw new IllegalArgumentException("ID категории обязателен");
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Категория не найдена: " + dto.getCategoryId()));

        Subcategory subcategory = new Subcategory();
        subcategory.setSubcategoryName(dto.getSubcategoryName());
        subcategory.setCategory(category);
        return subcategoryRepository.save(subcategory);
    }

    public Subcategory updateSubcategory(Long id, Subcategory subcategory) {
        subcategory.setSubcategoryId(id);
        return subcategoryRepository.save(subcategory);
    }

    public void deleteSubcategory(Long id) {
        subcategoryRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return subcategoryRepository.existsById(id);
    }

    public Subcategory partialUpdate(Long id, SubcategoryUpdateDTO dto) {
        Subcategory existing = subcategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Подкатегория не найдена"));

        if (dto.getSubcategoryName() != null) {
            existing.setSubcategoryName(dto.getSubcategoryName());
        }
        return subcategoryRepository.save(existing);
    }

}