package ru.avangard.website.service;

import org.springframework.stereotype.Service;
import ru.avangard.website.entity.Category;
import ru.avangard.website.repository.ICategoryRepository;
import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class CategoryService {

    public final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategoriesOrdered() {
        return categoryRepository.findAllByOrderByCategoryId();
    }

    public Optional<Category> findAllById(Long id){
        return categoryRepository.findAllById(id);
    }

    public Optional<Category> findByName(String name){
        return categoryRepository.findByCategoryName(name);
    }

    public Optional<Category> findByIdWithSubcategories(Long id){
        return categoryRepository.findByIdWithSubcategories(id);
    }

    public List<Category> getAllCategoriesWithSubcategories() {
        return categoryRepository.findAllWithSubcategories();
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category category) {
        category.setCategoryId(id);
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }
}
