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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    private String password; // хранить в виде хеша!
}