package ru.avangard.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avangard.website.entity.MainPage;

import java.util.Optional;

@Repository
public interface IMainPageRepository extends JpaRepository<MainPage, Long> {
    Optional<MainPage> findById(Long id);
    boolean existsById(Long id);
}