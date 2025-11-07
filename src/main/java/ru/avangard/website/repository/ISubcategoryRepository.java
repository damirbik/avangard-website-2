package ru.avangard.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.avangard.website.entity.Subcategory;
import java.util.List;
import java.util.Optional;

@Repository
public interface ISubcategoryRepository extends JpaRepository<Subcategory, Long> {

    // Подкатегории по ID категории
    List<Subcategory> findByCategoryCategoryId(Long categoryId);


    // Подкатегория с услугами
    @Query("SELECT s FROM Subcategory s LEFT JOIN FETCH s.services WHERE s.subcategoryId = :subcategoryId")
    Optional<Subcategory> findByIdWithServices(Long subcategoryId);

    // Найти подкатегории по ID категории с сортировкой по ID подкатегории
    @Query("SELECT s FROM Subcategory s WHERE s.category.categoryId = :categoryId ORDER BY s.subcategoryId")
    List<Subcategory> findByCategoryIdOrderBySubcategoryId(Long categoryId);

    // Подкатегории по ID категории с сортировкой
    List<Subcategory> findByCategoryCategoryIdOrderBySubcategoryId(Long categoryId);

    // Все подкатегории с категориями
    @Query("SELECT s FROM Subcategory s JOIN FETCH s.category")
    List<Subcategory> findAllWithCategory();
}