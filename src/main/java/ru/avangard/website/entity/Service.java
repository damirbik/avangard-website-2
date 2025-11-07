package ru.avangard.website.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "service")
//@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "title", length = 500)
    private String title;

    @Column(name = "main_text", length = 5000)
    private String  mainText;

    @Column(name = "pic_link_preview")
    private String picLinkPreview;

    @Column(name = "extra_text", length = 2000)
    private String extraText;

    @Column(name = "price")
    private String price;

    @Column(name = "important", length = 1000)
    private String important;

    @Column(name = "pic_link_main")
    private String picLinkMain;

    @Column(name = "meta_title")
    private String metaTitle = "ООО «Авангард» — Юридическая помощь в Томске";

    @Column(name = "meta_description", length = 400)
    private String metaDescription = "ООО «Авангард» - Любой вид Юридической помощи. Помогаем клиентам отстоять их интересы и получить достойную компенсацию в г. Томске и Области";

    @Column(name = "meta_keywords")
    private String metaKeywords = "";

    @Column(name = "subtitle")
    private String subtitle;

    @Lob
    @Column(name = "subText", length = 3000)
    private String subText;

    @Column(name = "alias")
    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String referenceText) {
        this.subText = referenceText;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
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

    public String getPicLinkMain() {
        return picLinkMain;
    }

    public void setPicLinkMain(String picLinkMain) {
        this.picLinkMain = picLinkMain;
    }

    public String getImportant() {
        return important;
    }

    public void setImportant(String important) {
        this.important = important;
    }

    public String  getPrice() {
        return price;
    }

    public void setPrice(String  price) {
        this.price = price;
    }

    public String getExtraText() {
        return extraText;
    }

    public void setExtraText(String extraText) {
        this.extraText = extraText;
    }

    public String getPicLinkPreview() {
        return picLinkPreview;
    }

    public void setPicLinkPreview(String picLinkPreview) {
        this.picLinkPreview = picLinkPreview;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id", nullable = false)
    @JsonBackReference("subcategory-service") // Это "обратная" сторона связи
    private Subcategory subcategory;
}
