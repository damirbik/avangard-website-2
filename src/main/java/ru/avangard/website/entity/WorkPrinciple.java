package ru.avangard.website.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "work_principle")
public class WorkPrinciple {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @Column(name = "icon_url", length = 500)
    private String iconURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_page_id")
    private MainPage mainPage;

    // Конструкторы
    public WorkPrinciple() {}

    public WorkPrinciple(String text, String iconURL) {
        this.text = text;
        this.iconURL = iconURL;
    }

    // Геттеры и сеттеры

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public MainPage getMainPage() {
        return mainPage;
    }

    public void setMainPage(MainPage mainPage) {
        this.mainPage = mainPage;
    }
}