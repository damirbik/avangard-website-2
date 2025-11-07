package ru.avangard.website.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avangard.website.entity.Category;
import ru.avangard.website.service.CategoryService;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://remjest-avangard-testing-e1b1.twc1.net/")
@RequestMapping("/api/categories")
//@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.getAllCategoriesOrdered();
        return ResponseEntity.ok(categories);
    }

//    @GetMapping("/with-subcategories")
//    public ResponseEntity<List<Category>> getAllCategoriesWithSubcategories() {
//        List<Category> categories = categoryService.getAllCategoriesWithSubcategories();
//        return ResponseEntity.ok(categories);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getAllCategoryById(@PathVariable Long id) {
        return categoryService.findAllById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @GetMapping("/{id}/with-subcategories")
//    public ResponseEntity<Category> getCategoryWithSubcategories(@PathVariable Long id) {
//        return categoryService.findByIdWithSubcategories(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    @GetMapping("/search/{name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String name) {
        return categoryService.findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping
//    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
//        Category createdCategory = categoryService.createCategory(category);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        if (!categoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (!categoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
