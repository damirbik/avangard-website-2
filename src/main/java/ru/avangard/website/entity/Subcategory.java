package ru.avangard.website.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subcategory")
//@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@CrossOrigin(origins = "https://remjest-avangard-testing-e1b1.twc1.net/")

public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subcategory_id")
    private Long subcategoryId;

    @Column(name = "subcategory_name")
    private String subcategoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference("category-subcategory") // Это "обратная" сторона связи
    private Category category;

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public Long getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    @OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("subcategory-service") // Это "владеющая" сторона связи
    private List<Service> services = new ArrayList<>();
}
