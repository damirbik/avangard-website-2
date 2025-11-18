package ru.avangard.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avangard.website.dto.MainPageDto;
import ru.avangard.website.entity.MainPage;
import ru.avangard.website.entity.WorkPrinciple;
import ru.avangard.website.entity.Advantage;
import ru.avangard.website.repository.IMainPageRepository;
import ru.avangard.website.repository.IWorkPrincipleRepository;
import ru.avangard.website.repository.IAdvantageRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MainPageService {

    private final IMainPageRepository mainPageRepository;
    private final IWorkPrincipleRepository workPrincipleRepository;
    private final IAdvantageRepository advantageRepository;

    @Autowired
    public MainPageService(IMainPageRepository mainPageRepository, IWorkPrincipleRepository workPrincipleRepository, IAdvantageRepository advantageRepository) {
        this.mainPageRepository = mainPageRepository;
        this.workPrincipleRepository = workPrincipleRepository;
        this.advantageRepository = advantageRepository;
    }

    @Transactional(readOnly = true)
    public Optional<MainPageDto> getMainPageAsDto() {
        Optional<MainPage> mainPageOpt = mainPageRepository.findById(1L);
        if (mainPageOpt.isPresent()) {
            MainPage mp = mainPageOpt.get();

            // Преобразование сущностей в DTO
            List<MainPageDto.WorkPrinciple> workPrinciples = workPrincipleRepository.findByMainPageId(mp.getId()).stream()
                    .map(wp -> new MainPageDto.WorkPrinciple(wp.getText(), wp.getIconURL()))
                    .collect(Collectors.toList());

            List<MainPageDto.Advantage> advantages = advantageRepository.findByMainPageId(mp.getId()).stream()
                    .map(a -> new MainPageDto.Advantage(a.getHeader(), a.getDescription()))
                    .collect(Collectors.toList());

            // Разделение информации о компании по строкам (предполагается, что в базе хранится с переносами строк)
            String[] infoArray = mp.getAboutCompanyInfo() != null ? mp.getAboutCompanyInfo().split("\n") : new String[0];
            List<String> infoList = java.util.Arrays.asList(infoArray);

            MainPageDto.About about = new MainPageDto.About(
                    infoList,
                    mp.getAboutCompanyImportant(),
                    mp.getAboutCompanyVideoUrl()
            );

            MainPageDto.PropertyValuation propertyValuation = new MainPageDto.PropertyValuation(
                    mp.getPropertyValuationInfo(),
                    mp.getPropertyValuationImageUrl(),
                    mp.getPropertyValuationPrice()
            );

            return Optional.of(new MainPageDto(about, propertyValuation, workPrinciples, advantages, "ООО «Авангард» — Юридическая помощь в Томске", "ООО «Авангард» — Юридическая помощь в Томске", ""));
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public MainPageDto updateMainPage(Long mainPageId, MainPageDto dto) {
        MainPage existingPage = mainPageRepository.findById(mainPageId)
                .orElseThrow(() -> new RuntimeException("MainPage not found with id: " + mainPageId));

        // Обновляем основные поля
        existingPage.setAboutCompanyInfo(String.join("\n", dto.getAbout().getInfo()));
        existingPage.setAboutCompanyImportant(dto.getAbout().getImportant());
        existingPage.setAboutCompanyVideoUrl(dto.getAbout().getVideoURL());
        existingPage.setPropertyValuationInfo(dto.getPropertyValuation().getInfo());
        existingPage.setPropertyValuationImageUrl(dto.getPropertyValuation().getImageURL());
        existingPage.setPropertyValuationPrice(dto.getPropertyValuation().getPrice());

        MainPage savedPage = mainPageRepository.save(existingPage);

        // Удаляем старые связи
        workPrincipleRepository.deleteByMainPageId(savedPage.getId());
        advantageRepository.deleteByMainPageId(savedPage.getId());

        // Сохраняем новые связи
        List<WorkPrinciple> newWorkPrinciples = dto.getWorkPrinciples().stream()
                .map(wpDto -> {
                    WorkPrinciple wp = new WorkPrinciple(wpDto.getText(), wpDto.getIconURL());
                    wp.setMainPage(savedPage);
                    return wp;
                })
                .collect(Collectors.toList());
        workPrincipleRepository.saveAll(newWorkPrinciples);

        List<Advantage> newAdvantages = dto.getAdvantages().stream()
                .map(aDto -> {
                    Advantage a = new Advantage(aDto.getHeader(), aDto.getDescription());
                    a.setMainPage(savedPage);
                    return a;
                })
                .collect(Collectors.toList());
        advantageRepository.saveAll(newAdvantages);

        // Возвращаем обновлённый DTO
        return getMainPageAsDto().orElseThrow(() -> new RuntimeException("Failed to retrieve updated MainPageDto"));
    }

    // Метод для инициализации начальных данных (опционально)
    @Transactional
    public void createDefaultMainPageIfNotExists() {
        if (!mainPageRepository.existsById(1L)) {
            // 1. Создаём и сохраняем MainPage (он получит id = 1)
            MainPage defaultPage = new MainPage();
            defaultPage.setAboutCompanyInfo("Информация о компании строка 1\nИнформация о компании строка 2\nИнформация о компании строка 3\nИнформация о компании строка 4");
            defaultPage.setAboutCompanyImportant("Важная информация");
            defaultPage.setAboutCompanyVideoUrl("/videos/default.mp4");
            defaultPage.setPropertyValuationInfo("Информация об оценке недвижимости");
            defaultPage.setPropertyValuationImageUrl("/images/valuation.jpg");
            defaultPage.setPropertyValuationPrice("от 5000 руб.");

            // Сначала сохраняем MainPage, чтобы получить ID
            MainPage savedMainPage = mainPageRepository.save(defaultPage);

            // 2. Теперь создаём WorkPrinciple и Advantage, устанавливая им связь с сохранённым MainPage
            for (int i = 1; i <= 4; i++) {
                WorkPrinciple wp = new WorkPrinciple("Текст принципа " + i, "/images/principle_" + i + ".png");
                wp.setMainPage(savedMainPage); // Устанавливаем связь
                workPrincipleRepository.save(wp); // Теперь можно сохранить

                Advantage adv = new Advantage("Заголовок " + i, "Описание " + i);
                adv.setMainPage(savedMainPage); // Устанавливаем связь
                advantageRepository.save(adv); // Теперь можно сохранить
            }
        }
    }
}