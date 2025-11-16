package ru.avangard.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.avangard.website.dto.MainPageDto;
import ru.avangard.website.service.MainPageService;

import java.util.Optional;

@RestController
@RequestMapping("/api/main-page")
@CrossOrigin(origins = {
        "https://remjest-avangard-testing-e1b1.twc1.net",
        "http://localhost:3000"
})
public class MainPageController {

    private final MainPageService mainPageService;

    @Autowired
    public MainPageController(MainPageService mainPageService) {
        this.mainPageService = mainPageService;
    }

    @GetMapping
    public ResponseEntity<MainPageDto> getMainPage() {
        Optional<MainPageDto> mainPageDtoOpt = mainPageService.getMainPageAsDto();
        if (mainPageDtoOpt.isPresent()) {
            return ResponseEntity.ok(mainPageDtoOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MainPageDto> updateMainPage(@PathVariable Long id, @RequestBody MainPageDto dto) {
        try {
            MainPageDto updatedDto = mainPageService.updateMainPage(id, dto);
            return ResponseEntity.ok(updatedDto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}