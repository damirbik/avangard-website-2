package ru.avangard.website.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avangard.website.dto.SubcategoryCreateDTO;
import ru.avangard.website.dto.SubcategoryUpdateDTO;
import ru.avangard.website.entity.Subcategory;
import ru.avangard.website.service.SubcategoryService;
import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
//@RequiredArgsConstructor
@CrossOrigin(origins = "https://remjest-avangard-testing-e1b1.twc1.net/")

public class SubcategoryController {

    private final SubcategoryService subcategoryService;

    public SubcategoryController(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GetMapping
    public ResponseEntity<List<Subcategory>> getAllSubcategories() {
        List<Subcategory> subcategories = subcategoryService.getAllSubcategories();
        return ResponseEntity.ok(subcategories);
    }

//    @GetMapping("/with-categories")
//    public ResponseEntity<List<Subcategory>> getAllSubcategoriesWithCategories() {
//        List<Subcategory> subcategories = subcategoryService.getAllSubcategoriesWithCategories();
//        return ResponseEntity.ok(subcategories);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Subcategory> getSubcategoryById(@PathVariable Long id) {
        return subcategoryService.getSubcategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @GetMapping("/{id}/with-services")
//    public ResponseEntity<Subcategory> getSubcategoryWithServices(@PathVariable Long id) {
//        return subcategoryService.getSubcategoryWithServices(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Subcategory>> getSubcategoriesByCategory(@PathVariable Long categoryId) {
        List<Subcategory> subcategories = subcategoryService.getSubcategoriesByCategoryId(categoryId);
        return ResponseEntity.ok(subcategories);
    }

//    @GetMapping("/category/{categoryId}/ordered")
//    public ResponseEntity<List<Subcategory>> getSubcategoriesByCategoryOrdered(@PathVariable Long categoryId) {
//        List<Subcategory> subcategories = subcategoryService.getSubcategoriesByCategoryIdOrdered(categoryId);
//        return ResponseEntity.ok(subcategories);
//    }

    @PostMapping
    public ResponseEntity<Subcategory> createSubcategory(@RequestBody SubcategoryCreateDTO dto) {
        Subcategory created = subcategoryService.createSubcategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subcategory> updateSubcategory(
            @PathVariable Long id,
            @RequestBody SubcategoryUpdateDTO dto) {

        if (!subcategoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Subcategory updated = subcategoryService.partialUpdate(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Long id) {
        if (!subcategoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        subcategoryService.deleteSubcategory(id);
        return ResponseEntity.noContent().build();
    }
}