package ru.avangard.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.avangard.website.entity.Service;
import java.util.List;
import java.util.Optional;

@Repository
public interface IServiceRepository extends JpaRepository<Service, Long> {

    // Услуги по ID подкатегории
    List<Service> findBySubcategorySubcategoryId(Long subcategoryId);

    // Услуги по ID категории
    List<Service> findBySubcategoryCategoryCategoryId(Long categoryId);

    Optional<Service> findByAlias(String alias);

    @Query("SELECT s.serviceId as serviceId, s.title as title, s.picLinkPreview as picLinkPreview, s.alias As alias " +
            "FROM Service s WHERE s.subcategory.subcategoryId = :subcategoryId")
    List<ServiceShortProjection> findShortBySubcategoryId(@Param("subcategoryId") Long subcategoryId);

    // Все услуги с подкатегориями и категориями
    @Query("SELECT s FROM Service s JOIN FETCH s.subcategory sub JOIN FETCH sub.category")
    List<Service> findAllWithSubcategoryAndCategory();

    // Услуга по ID с полной загрузкой связей
    @Query("SELECT s FROM Service s JOIN FETCH s.subcategory sub JOIN FETCH sub.category WHERE s.serviceId = :serviceId")
    Optional<Service> findByIdWithSubcategoryAndCategory(Long serviceId);

    @Query("SELECT s.serviceId AS serviceId, " +
            "       s.title AS title, " +
            "       s.picLinkPreview AS picLinkPreview," +
            "       s.alias As alias " +
            "FROM Service s " +
            "WHERE s.subcategory.category.categoryId = :categoryId")
    List<ServiceShortProjection> findShortByCategoryId(@Param("categoryId") Long categoryId);
}