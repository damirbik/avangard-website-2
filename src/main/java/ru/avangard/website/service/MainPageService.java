package ru.avangard.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avangard.website.dto.MainPageDto;
import ru.avangard.website.entity.MainPage;
import ru.avangard.website.entity.WorkPrinciple;
import ru.avangard.website.entity.Advantage;
import ru.avangard.website.repository.IMainPageRepository;
import ru.avangard.website.repository.IWorkPrincipleRepository;
import ru.avangard.website.repository.IAdvantageRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MainPageService {

    private final IMainPageRepository mainPageRepository;
    private final IWorkPrincipleRepository workPrincipleRepository;
    private final IAdvantageRepository advantageRepository;

    @Value("${upload.dir:uploads}")
    private String uploadDir;

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

    private void deleteFileIfPresent(String relativePath) {
        if (relativePath == null || relativePath.isBlank()) return;
        try {
            String cleanPath = relativePath.startsWith("/") ? relativePath.substring(1) : relativePath;
            Path filePath = Paths.get(uploadDir, cleanPath).toAbsolutePath().normalize();
            if (filePath.startsWith(Paths.get(uploadDir).toAbsolutePath()) && Files.exists(filePath)) {
                Files.delete(filePath);
                System.out.println("Файл удалён: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Ошибка удаления файла: " + relativePath);
        }
    }


    @Transactional
    public MainPageDto updateMainPage(Long mainPageId, MainPageDto dto) {
        MainPage existingPage = mainPageRepository.findById(mainPageId)
                .orElseThrow(() -> new RuntimeException("MainPage not found with id: " + mainPageId));
        String oldVideoUrl = existingPage.getAboutCompanyVideoUrl();
        String oldImageUrl = existingPage.getPropertyValuationImageUrl();

        // Обновляем основные поля
        existingPage.setAboutCompanyInfo(String.join("\n", dto.getAbout().getInfo()));
        existingPage.setAboutCompanyImportant(dto.getAbout().getImportant());
        existingPage.setAboutCompanyVideoUrl(dto.getAbout().getVideoURL());
        existingPage.setPropertyValuationInfo(dto.getPropertyValuation().getInfo());
        existingPage.setPropertyValuationImageUrl(dto.getPropertyValuation().getImageURL());
        existingPage.setPropertyValuationPrice(dto.getPropertyValuation().getPrice());

        if (!java.util.Objects.equals(oldVideoUrl, dto.getAbout().getVideoURL())) {
            deleteFileIfPresent(oldVideoUrl);
        }
        if (!java.util.Objects.equals(oldImageUrl, dto.getPropertyValuation().getImageURL())) {
            deleteFileIfPresent(oldImageUrl);
        }

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
            defaultPage.setAboutCompanyInfo("/bold/ООО «АВАНГАРД»//bold// — экспертно-оценочная компания, предлагающая широкий спектр услуг по проведению независимой оценки и экспертизе в Томской области, в Томске, в Северске, а также в других регионах.\n" +
                    "Отчетные документы, выдаваемые компанией, принимаются /bold/во всех организациях и учреждениях Российской Федерации.//bold//\n" +
                    "Мы специализируемся главным образом на проведении экспертиз и оценки /bold/любого вида имущества://bold// движимого и недвижимого. Благодаря /bold/высокому уровню профессиональной подготовки и большому опыту//bold//, мы оказываем услуги качественно и в срок.\n" +
                    "/bold/Наша компания включена в реестр НОПРИЗ и является членом СРО.//bold// Регистрационный номер члена СРО № П-116-007024040541-0197 в области инженерных изысканий и в области архитектурно-строительного проектирования и их обязательствах Союз «Межрегиональное объединение организаций в области проектирования «Ярд» (СРО-П-116-18012010).");
            defaultPage.setAboutCompanyImportant("/bold/МЫ НЕ ТЯНЕМ ВРЕМЯ — ВЫ НЕ ТЕРЯЕТЕ ДЕНЬГИ!//bold// /n/ /italic/Все заключения и отчёты мы подготавливаем в минимальные сроки//italic//");
            defaultPage.setAboutCompanyVideoUrl("/videos/main-video.mp4");
            defaultPage.setPropertyValuationInfo("ㅤ/bold/Независимая оценка стоимости недвижимости//bold// (квартиры, дома, земельного участка или участка под строительство) необходима, в случае, если клиенты желают её приобрести, продать или застраховать, при использовании в качестве арендной жилой или нежилой недвижимости, при совершении сделок купли-продажи недвижимых активов и объектов основных средств. Оценка коммерческой или жилой недвижимости /bold/в Томске и Томской области//bold// да и в любом городе часто требуется в случае, если появляется потребность в привлечении акционеров, а также в случае разделения долей собственно./n//n/" +
                    "ㅤКомпания OOO «АВАНГАРД» оказывает /bold/полный перечень услуг//bold// по оценке любой недвижимости в Томске:/n//n/\n" +
                    "ㅤ• Оценка недвижимости/n/\n" +
                    "ㅤ• Оценка жилой недвижимости/n/\n" +
                    "ㅤ• Оценка коттеджа/n/\n" +
                    "ㅤ• Оценка квартиры/n/\n" +
                    "ㅤ• Оценка дачи/n/\n" +
                    "ㅤ• Оценка офисных помещений/n/\n" +
                    "ㅤ• Оценка складов/n/\n" +
                    "ㅤ• Оценка гаражей/n/\n" +
                    "ㅤ• Оценка незавершенного строительства/n/\n");
            defaultPage.setPropertyValuationImageUrl("/images/home.jpg");
            defaultPage.setPropertyValuationPrice("от 3000 ₽");

            // Сначала сохраняем MainPage, чтобы получить ID
            MainPage savedMainPage = mainPageRepository.save(defaultPage);

            // 2. Теперь создаём WorkPrinciple и Advantage, устанавливая им связь с сохранённым MainPage
            // Создаём 4 разных принципа работы с уникальными текстами
            WorkPrinciple wp1 = new WorkPrinciple("Независимость и беспристрастность", "/images/1.svg");
            wp1.setMainPage(savedMainPage);
            workPrincipleRepository.save(wp1);

            WorkPrinciple wp2 = new WorkPrinciple("Строгое соответствие законодательству", "/images/2.svg");
            wp2.setMainPage(savedMainPage);
            workPrincipleRepository.save(wp2);

            WorkPrinciple wp3 = new WorkPrinciple("Высокая квалификация и сертификация экспертов и оценщиков", "/images/3.svg");
            wp3.setMainPage(savedMainPage);
            workPrincipleRepository.save(wp3);

            WorkPrinciple wp4 = new WorkPrinciple("Конфиденциальность", "/images/4.svg");
            wp4.setMainPage(savedMainPage);
            workPrincipleRepository.save(wp4);

// И аналогично для преимуществ
            Advantage adv1 = new Advantage("Качество и оперативность выполнения услуг", "Профессионализм и опыт экспертов и оценщиков, что гарантирует высокое качество услуг и точность результатов.");
            adv1.setMainPage(savedMainPage);
            advantageRepository.save(adv1);

            Advantage adv2 = new Advantage("Объективность и независимость", "Независимые эксперты и оценщики действуют в рамках законодательства и предоставляют непредвзятые заключения и отчеты, что особенно важно при решении спорных вопросов.");
            adv2.setMainPage(savedMainPage);
            advantageRepository.save(adv2);

            Advantage adv3 = new Advantage("Юридическая значимость", "Заключения и отчеты имеет юридическую силу и рассматриваются любыми инстанциями.");
            adv3.setMainPage(savedMainPage);
            advantageRepository.save(adv3);

            Advantage adv4 = new Advantage("Страхование ответственности", "/bold/25 000 000 руб.//bold// — уровень ответственности члена саморегулируемой организации по обязательствам по договору подряда на подготовку проектной документации, в соответствии с которым указанным членом внесен взнос в компенсационный фонд возмещения вреда./n//n//bold/30 000 000 руб.//bold// — страхование ответственности оценочной организации.");
            adv4.setMainPage(savedMainPage);
            advantageRepository.save(adv4);
        }
    }
}