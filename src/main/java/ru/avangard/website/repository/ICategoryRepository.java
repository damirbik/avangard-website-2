package ru.avangard.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.avangard.website.entity.Category;
import java.util.List;
import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

    //Получить все категории, отсортированные по ID
    @Query("SELECT c FROM Category c ORDER BY c.categoryId")
    List<Category> findAllByOrderByCategoryId();

    // Найти категорию по названию
    @Query("SELECT c FROM Category c WHERE c.categoryName = :categoryName")
    Optional<Category> findByCategoryName(String categoryName);

    // Категория с подкатегориями (жадная загрузка)
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.subcategories WHERE c.categoryId = :categoryId")
    Optional<Category> findByIdWithSubcategories(Long categoryId);

    // Все категории с подкатегориями
    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.subcategories")
    List<Category> findAllWithSubcategories();

    @Query("SELECT c FROM Category c WHERE c.categoryId = :id")
    Optional<Category> findAllById(Long id);

}