package ru.avangard.website.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "main_page")
public class MainPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 1L; // Используем фиксированный ID для единственной записи

    @Column(name = "about_company_info", length = 3000)
    private String aboutCompanyInfo;

    @Column(name = "about_company_important", length = 1000)
    private String aboutCompanyImportant;

    @Column(name = "about_company_video_url", length = 500)
    private String aboutCompanyVideoUrl;

    @Column(name = "property_valuation_info", length = 3000)
    private String propertyValuationInfo;

    @Column(name = "property_valuation_image_url", length = 500)
    private String propertyValuationImageUrl;

    @Column(name = "property_valuation_price", length = 100)
    private String propertyValuationPrice;

    @Column(name = "meta_title")
    private String metaTitle = "ООО «Авангард» — Юридическая помощь в Томске";

    @Column(name = "meta_description", length = 400)
    private String metaDescription = "ООО «Авангард» - Любой вид Юридической помощи. Помогаем клиентам отстоять их интересы и получить достойную компенсацию в г. Томске и Области";

    @Column(name = "meta_keywords")
    private String metaKeywords = "";

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAboutCompanyInfo() {
        return aboutCompanyInfo;
    }

    public void setAboutCompanyInfo(String aboutCompanyInfo) {
        this.aboutCompanyInfo = aboutCompanyInfo;
    }

    public String getAboutCompanyImportant() {
        return aboutCompanyImportant;
    }

    public void setAboutCompanyImportant(String aboutCompanyImportant) {
        this.aboutCompanyImportant = aboutCompanyImportant;
    }

    public String getAboutCompanyVideoUrl() {
        return aboutCompanyVideoUrl;
    }

    public void setAboutCompanyVideoUrl(String aboutCompanyVideoUrl) {
        this.aboutCompanyVideoUrl = aboutCompanyVideoUrl;
    }

    public String getPropertyValuationInfo() {
        return propertyValuationInfo;
    }

    public void setPropertyValuationInfo(String propertyValuationInfo) {
        this.propertyValuationInfo = propertyValuationInfo;
    }

    public String getPropertyValuationImageUrl() {
        return propertyValuationImageUrl;
    }

    public void setPropertyValuationImageUrl(String propertyValuationImageUrl) {
        this.propertyValuationImageUrl = propertyValuationImageUrl;
    }

    public String getPropertyValuationPrice() {
        return propertyValuationPrice;
    }

    public void setPropertyValuationPrice(String propertyValuationPrice) {
        this.propertyValuationPrice = propertyValuationPrice;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }
}