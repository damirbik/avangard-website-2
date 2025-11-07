// src/main/java/ru/avangard/website/repository/AdminRepository.java
package ru.avangard.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avangard.website.entity.Admin;

import java.util.Optional;

@Repository
public interface IAdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByLogin(String login);
}