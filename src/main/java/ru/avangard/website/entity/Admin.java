package ru.avangard.website.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@Entity
@Table(name = "admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@CrossOrigin(origins = "https://remjest-avangard-testing-e1b1.twc1.net/")

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password; // хранить в виде хеша!
}