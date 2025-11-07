package ru.avangard.website.dto;

public class SubcategoryCreateDTO {
    private String subcategoryName;
    private Long categoryId; // ← принимаем ID категории как число

    // геттеры и сеттеры
    public String getSubcategoryName() { return subcategoryName; }
    public void setSubcategoryName(String subcategoryName) { this.subcategoryName = subcategoryName; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
}