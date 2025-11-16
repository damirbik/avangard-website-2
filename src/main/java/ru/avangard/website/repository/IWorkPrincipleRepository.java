package ru.avangard.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avangard.website.entity.WorkPrinciple;

import java.util.List;

@Repository
public interface IWorkPrincipleRepository extends JpaRepository<WorkPrinciple, Long> {
    List<WorkPrinciple> findByMainPageId(Long mainPageId);
    void deleteByMainPageId(Long mainPageId);
}