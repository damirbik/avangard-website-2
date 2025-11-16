package ru.avangard.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avangard.website.entity.Advantage;

import java.util.List;

@Repository
public interface IAdvantageRepository extends JpaRepository<Advantage, Long> {
    List<Advantage> findByMainPageId(Long mainPageId);
    void deleteByMainPageId(Long mainPageId);
}