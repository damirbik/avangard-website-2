package ru.avangard.website.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceCreateDTO {

    private String title;
    private String mainText;
    private String picLinkPreview;
    private String extraText;
    private String price;
    private String important;
    private String picLinkMain;
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;
    private String subtitle;
    private String subText;
    private String alias;

    @JsonProperty("subcategoryId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long subcategoryId;
    //private Long subcategoryId; // ← ID подкатегории

    // Геттеры и сеттеры
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMainText() { return mainText; }
    public void setMainText(String mainText) { this.mainText = mainText; }

    public String getPicLinkPreview() { return picLinkPreview; }
    public void setPicLinkPreview(String picLinkPreview) { this.picLinkPreview = picLinkPreview; }

    public String getExtraText() { return extraText; }
    public void setExtraText(String extraText) { this.extraText = extraText; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getImportant() { return important; }
    public void setImportant(String important) { this.important = important; }

    public String getPicLinkMain() { return picLinkMain; }
    public void setPicLinkMain(String picLinkMain) { this.picLinkMain = picLinkMain; }

    public String getMetaTitle() { return metaTitle; }
    public void setMetaTitle(String metaTitle) { this.metaTitle = metaTitle; }

    public String getMetaDescription() { return metaDescription; }
    public void setMetaDescription(String metaDescription) { this.metaDescription = metaDescription; }

    public String getMetaKeywords() { return metaKeywords; }
    public void setMetaKeywords(String metaKeywords) { this.metaKeywords = metaKeywords; }

    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }

    public String getSubText() { return subText; }
    public void setSubText(String subText) { this.subText = subText; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public Long getSubcategoryId() { return subcategoryId; }
    public void setSubcategoryId(Long subcategoryId) { this.subcategoryId = subcategoryId; }
}