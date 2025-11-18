package ru.avangard.website.dto;

import java.util.List;

public class MainPageDto {

    private About about;
    private PropertyValuation propertyValuation;
    private List<WorkPrinciple> workPrinciples;
    private List<Advantage> advantages;
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;

    // Конструкторы
    public MainPageDto() {}

    public MainPageDto(About about, PropertyValuation propertyValuation, List<WorkPrinciple> workPrinciples, List<Advantage> advantages, String metaDescription, String metaTitle, String metaKeywords) {
        this.about = about;
        this.propertyValuation = propertyValuation;
        this.workPrinciples = workPrinciples;
        this.advantages = advantages;
        this.metaDescription = metaDescription;
        this.metaKeywords = metaKeywords;
        this.metaTitle = metaTitle;
    }

    // Геттеры и сеттеры

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }


    public About getAbout() {
        return about;
    }

    public void setAbout(About about) {
        this.about = about;
    }

    public PropertyValuation getPropertyValuation() {
        return propertyValuation;
    }

    public void setPropertyValuation(PropertyValuation propertyValuation) {
        this.propertyValuation = propertyValuation;
    }

    public List<WorkPrinciple> getWorkPrinciples() {
        return workPrinciples;
    }

    public void setWorkPrinciples(List<WorkPrinciple> workPrinciples) {
        this.workPrinciples = workPrinciples;
    }

    public List<Advantage> getAdvantages() {
        return advantages;
    }

    public void setAdvantages(List<Advantage> advantages) {
        this.advantages = advantages;
    }

    // Вложенные классы для структуры

    public static class About {
        private List<String> info;
        private String important;
        private String videoURL;

        public About() {}

        public About(List<String> info, String important, String videoURL) {
            this.info = info;
            this.important = important;
            this.videoURL = videoURL;
        }

        public List<String> getInfo() {
            return info;
        }

        public void setInfo(List<String> info) {
            this.info = info;
        }

        public String getImportant() {
            return important;
        }

        public void setImportant(String important) {
            this.important = important;
        }

        public String getVideoURL() {
            return videoURL;
        }

        public void setVideoURL(String videoURL) {
            this.videoURL = videoURL;
        }
    }

    public static class PropertyValuation {
        private String info;
        private String imageURL;
        private String price;

        public PropertyValuation() {}

        public PropertyValuation(String info, String imageURL, String price) {
            this.info = info;
            this.imageURL = imageURL;
            this.price = price;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    public static class WorkPrinciple {
        private String text;
        private String iconURL;

        public WorkPrinciple() {}

        public WorkPrinciple(String text, String iconURL) {
            this.text = text;
            this.iconURL = iconURL;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIconURL() {
            return iconURL;
        }

        public void setIconURL(String iconURL) {
            this.iconURL = iconURL;
        }
    }

    public static class Advantage {
        private String header;
        private String description;

        public Advantage() {}

        public Advantage(String header, String description) {
            this.header = header;
            this.description = description;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}