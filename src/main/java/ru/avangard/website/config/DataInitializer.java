package ru.avangard.website.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.avangard.website.entity.Admin;
import ru.avangard.website.entity.Category;
import ru.avangard.website.entity.Subcategory;
import ru.avangard.website.entity.Service;
import ru.avangard.website.repository.IAdminRepository;
import ru.avangard.website.repository.ICategoryRepository;
import ru.avangard.website.repository.ISubcategoryRepository;
import ru.avangard.website.repository.IServiceRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@Component
//@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ICategoryRepository categoryRepository;
    private final ISubcategoryRepository subcategoryRepository;
    private final IServiceRepository serviceRepository;
    private final IAdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            ICategoryRepository categoryRepository,
            ISubcategoryRepository subcategoryRepository,
            IServiceRepository serviceRepository,
            IAdminRepository adminRepository,
            PasswordEncoder passwordEncoder) {
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.serviceRepository = serviceRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Проверяем, есть ли уже данные в базе
        serviceRepository.deleteAll(); //очистка бд
        subcategoryRepository.deleteAll();
        categoryRepository.deleteAll();
        System.out.println("Старые данные удалены");
        if (categoryRepository.count() > 0) {
            System.out.println("✅ База данных уже содержит данные. Пропускаем инициализацию.");
            return;
        }
        Admin admin = new Admin();
        admin.setLogin("admin");
        admin.setPassword(passwordEncoder.encode("password123"));
        adminRepository.save(admin);

        System.out.println("🔄 Начинаем инициализацию базы данных...");

        // Создаем категории
        Category assessmentServices = new Category();
        assessmentServices.setCategoryName("УСЛУГИ ОЦЕНКИ");

        Category expertise = new Category();
        expertise.setCategoryName("НАШИ ЭКСПЕРТИЗЫ");

        // Сохраняем категории
        assessmentServices = categoryRepository.save(assessmentServices);
        expertise = categoryRepository.save(expertise);

        System.out.println("✅ Создано категорий: " + categoryRepository.count());

        // Создаем подкатегории
        Subcategory vehicleAppraisal = new Subcategory();
        vehicleAppraisal.setSubcategoryName("Оценка автомобилей");
        vehicleAppraisal.setCategory(assessmentServices);

        Subcategory realEstateAppraisal = new Subcategory();
        realEstateAppraisal.setSubcategoryName("Оценка недвижимости");
        realEstateAppraisal.setCategory(assessmentServices);

        Subcategory damageAssessment = new Subcategory();
        damageAssessment.setSubcategoryName("Оценка ущерба");
        damageAssessment.setCategory(assessmentServices);

        Subcategory appraisalOfEquipmentMachineryOfficeEquipment = new Subcategory();
        appraisalOfEquipmentMachineryOfficeEquipment.setSubcategoryName("Оценка оборудования, машин, оргтехники");
        appraisalOfEquipmentMachineryOfficeEquipment.setCategory(assessmentServices);

        Subcategory businessValuation = new Subcategory();
        businessValuation.setSubcategoryName("Оценка бизнеса");
        businessValuation.setCategory(assessmentServices);

        Subcategory valuationOfAllTypesOfAssetsAndProperty = new Subcategory();
        valuationOfAllTypesOfAssetsAndProperty.setSubcategoryName("Оценка всех видов активов и имущества");
        valuationOfAllTypesOfAssetsAndProperty.setCategory(assessmentServices);

        Subcategory lawyersForUtilityDebtCollection = new Subcategory();
        lawyersForUtilityDebtCollection.setSubcategoryName("Юристы по взысканию ЖКХ");
        lawyersForUtilityDebtCollection.setCategory(assessmentServices);

        Subcategory commodityExamination = new Subcategory();
        commodityExamination.setSubcategoryName("Экспертиза товаров");
        commodityExamination.setCategory(expertise);

        Subcategory sanitaryAndEpidemiologicalExamination = new Subcategory();
        sanitaryAndEpidemiologicalExamination.setSubcategoryName("Санитарно-эпидемиологическая экспертиза");
        sanitaryAndEpidemiologicalExamination.setCategory(expertise);

        Subcategory fireAndTechnicalExamination = new Subcategory();
        fireAndTechnicalExamination.setSubcategoryName("Пожарно-техническая экспертиза");
        fireAndTechnicalExamination.setCategory(expertise);

        Subcategory constructionAndInstallationWorkExamination = new Subcategory();
        constructionAndInstallationWorkExamination.setSubcategoryName("Экспертиза строительно-монтажных работ");
        constructionAndInstallationWorkExamination.setCategory(expertise);

        // Сохраняем все подкатегории
        List<Subcategory> subcategories = Arrays.asList(
                vehicleAppraisal, realEstateAppraisal, damageAssessment, appraisalOfEquipmentMachineryOfficeEquipment, businessValuation, valuationOfAllTypesOfAssetsAndProperty,
                lawyersForUtilityDebtCollection, commodityExamination, sanitaryAndEpidemiologicalExamination, fireAndTechnicalExamination, constructionAndInstallationWorkExamination
        );

        subcategoryRepository.saveAll(subcategories);
        System.out.println("✅ Создано подкатегорий: " + subcategoryRepository.count());

        // Создаем услуги
        createServices(vehicleAppraisal, realEstateAppraisal, damageAssessment, appraisalOfEquipmentMachineryOfficeEquipment, businessValuation, valuationOfAllTypesOfAssetsAndProperty, lawyersForUtilityDebtCollection, commodityExamination, sanitaryAndEpidemiologicalExamination, fireAndTechnicalExamination, constructionAndInstallationWorkExamination);

        System.out.println("✅ Создано услуг: " + serviceRepository.count());
        System.out.println("🎉 База данных успешно инициализирована с тестовыми данными!");
    }

    private void createServices(Subcategory vehicleAppraisal, Subcategory realEstateAppraisal, Subcategory damageAssessment, Subcategory appraisalOfEquipmentMachineryOfficeEquipment, Subcategory businessValuation, Subcategory valuationOfAllTypesOfAssetsAndProperty, Subcategory lawyersForUtilityDebtCollection, Subcategory commodityExamination, Subcategory sanitaryAndEpidemiologicalExamination, Subcategory fireAndTechnicalExamination, Subcategory constructionAndInstallationWorkExamination) {

        Service independentDamageAssessmentAfterCarAccident = new Service();
        independentDamageAssessmentAfterCarAccident.setTitle("Независимая оценка ущерба после ДТП");
        independentDamageAssessmentAfterCarAccident.setMainText("Зачастую сумма страхового возмещения, рассчитанная страховой компанией в процессе оценки ущерба автомобиля при ДТП, далека от реальности не в пользу автовладельца. Будь то виновник аварии, с которого взыскивают компенсацию или пострадавший, которому должны выплатить средства на ремонт. И для получения объективных данных необходима независимая экспертиза, которая позволит точно определить, сколько денег потребуется на восстановление автомобиля.");
        independentDamageAssessmentAfterCarAccident.setSubcategory(vehicleAppraisal);
        independentDamageAssessmentAfterCarAccident.setPrice("ОТ 3000 Р");
        independentDamageAssessmentAfterCarAccident.setPicLinkPreview("/images/exp_8.jpg");
        independentDamageAssessmentAfterCarAccident.setPicLinkMain("/images/nezavisimaya_ekspertiza.png");
        independentDamageAssessmentAfterCarAccident.setImportant("Независимую экспертизу автомобиля после ДТП в Томске можно проводить КАК ДО, ТАК И ПОСЛЕ оценки ущерба от страховой компании");
        independentDamageAssessmentAfterCarAccident.setMetaTitle("Независимая оценка ущерба после ДТП в Томске | ООО Авангард");
        independentDamageAssessmentAfterCarAccident.setMetaDescription("Независимая экспертиза автомобиля после ДТП в Томске. Поможем оспорить заниженную выплату по каско или ОСАГО. Подготовим отчет для суда. Гарантия, цены от 3000 руб.");
        independentDamageAssessmentAfterCarAccident.setMetaKeywords("независимая оценка ущерба после ДТП, экспертиза автомобиля Томск, оспаривание выплаты по ОСАГО, оценка ущерба для суда, ООО Авангард, страховая компания занизила выплату, автоэксперт Томск");
        independentDamageAssessmentAfterCarAccident.setExtraText("Специалисты компании ООО «Авангард» в Томске  не только помогут понять, хватит ли выплаченных страховой компанией средств на восстановление авто, но и защитят ваши интересы в суде!");
        independentDamageAssessmentAfterCarAccident.setSubtitle("Кому необходима независимая оценка автомобиля после ДТП?");
        independentDamageAssessmentAfterCarAccident.setAlias("idaaca");

        Service vehicleRepairQualityExamination = new Service();
        vehicleRepairQualityExamination.setTitle("Экспертиза качества ремонта автомобиля");
        vehicleRepairQualityExamination.setMainText("Экспертиза качества ремонта по ОСАГО в Томске нужна для того, чтобы предъявить претензии страховой компании, если был выполнен некачественный ремонт по ОСАГО. Многие ремонтные мастерские, которые выполняют такой вид ремонта, не справляются с потоком клиентов от страховых компаний. Учитывая, что время ремонта строго регламентировано, сотрудники автосервиса не всегда могут их соблюсти. От этого страдает качество, у владельцев автомобилей появляются претензии, которые страховые компании часто не признают. Эти проблемы могут быть решены с помощью проведения независимой экспертизы.");
        vehicleRepairQualityExamination.setSubcategory(vehicleAppraisal);
        vehicleRepairQualityExamination.setPrice("ОТ 5000 Р");
        vehicleRepairQualityExamination.setPicLinkPreview("/images/exp_9.jpg");
        vehicleRepairQualityExamination.setPicLinkMain("/images/inner_car.png");
        vehicleRepairQualityExamination.setMetaTitle("Экспертиза качества ремонта по ОСАГО в Томске | ООО Авангард");
        vehicleRepairQualityExamination.setMetaDescription("Некачественно отремонтировали авто по ОСАГО в Томске? Проведем независимую экспертизу качества ремонта. Официальный отчет для претензий к страховой и в суд. Стоимость от 5000 руб.");
        vehicleRepairQualityExamination.setMetaKeywords("экспертиза качества ремонта автомобиля, некачественный ремонт по ОСАГО Томск, проверка авто после ремонта, экспертиза кузовного ремонта, оценка лакокрасочного покрытия, оспорить ремонт по страховке, независимая экспертиза авто Томск, ООО Авангард");
        vehicleRepairQualityExamination.setExtraText("Наши специалисты могут провести качественную экспертизу автомобиля после покупки (как нового, так и б/у). Также, нами проводится независимая экспертиза после ремонта автомобиля в Томске, которая может включать экспертизу лакокрасочного покрытия автомобиля, кузовного ремонта и качество ремонта узлов и агрегатов");
        vehicleRepairQualityExamination.setAlias("vrqe");

        Service professionalVehicleAppraisal = new Service();
        professionalVehicleAppraisal.setTitle("Профессиональная оценка автомобиля");
        professionalVehicleAppraisal.setMainText("Решить вопрос цены самостоятельно, не допустив при этом ошибок, которые подстерегают на каждом шагу, достаточно сложно. Гораздо проще обратиться к профессиональному оценщику, который осуществит оценку автомобиля и подробно укажет, что и как повлияло на формирование стоимости. Затраты на работу оценщика будут значительно меньше, чем материальные и моральные потери, неизбежные при самостоятельной оценке автомобиля.");
        professionalVehicleAppraisal.setSubcategory(vehicleAppraisal);
        professionalVehicleAppraisal.setPrice("ОТ 3000 Р");
        professionalVehicleAppraisal.setPicLinkPreview("/images/exp_7.jpg");
        professionalVehicleAppraisal.setPicLinkMain("/images/auto_zoom.png");
        professionalVehicleAppraisal.setImportant("Перечень необходимых документов: Свидетельство о регистрации транспортного средства, Паспорт транспортного средства");
        professionalVehicleAppraisal.setMetaTitle("Оценка автомобиля для продажи и суда в Томске | ООО Авангар");
        professionalVehicleAppraisal.setMetaDescription("Профессиональная оценка автомобиля в Томске для суда, продажи или кредита. Точно определяем рыночную стоимость, готовим официальный отчет. Гарантия достоверности. Стоимость от 3000 руб.");
        professionalVehicleAppraisal.setMetaKeywords("оценка автомобиля Томск, профессиональная оценка авто, рыночная стоимость автомобиля, оценка авто для суда, отчет об оценке автомобиля, независимая оценка, ООО Авангард");
        professionalVehicleAppraisal.setExtraText("Независимая оценочная компания «АВАНГАРД» (город Томск) составит профессиональный отчет об оценке автомобиля для определения его рыночной цены перед продажей, а также для предоставления документальных данных о стоимости транспорта в судебные инстанции и коммерческие организации.");
        professionalVehicleAppraisal.setAlias("pva");
        professionalVehicleAppraisal.setSubtitle("Для каких целей необходима данная экспертиза:");

        Service apartmentAndHousingAppraisal = new Service();
        apartmentAndHousingAppraisal.setTitle("Оценка квартир, жилья");
        apartmentAndHousingAppraisal.setMainText("Оценка стоимости недвижимости в Томске, а именно квартиры — это определение рыночной цены права собственности или других прав касательно объекта экспертизы. Для достоверной оценки стоимости объекта жилой недвижимости необходимо учитывать множество факторов : местоположение и тип дома, удаленность от центра, наличие двора и консьержки в подъезде и т. д. Кроме того, отчет об оценке жилой недвижимости, подготовленный профессиональными оценщиками, является официальным документом, который принимается к рассмотрению кредитными отделами банков, судами, другими организациями и государственными учреждениями.");
        apartmentAndHousingAppraisal.setSubcategory(realEstateAppraisal);
        apartmentAndHousingAppraisal.setPrice("ОТ 5000 Р");
        apartmentAndHousingAppraisal.setPicLinkPreview("/images/exp_2.jpg");
        apartmentAndHousingAppraisal.setPicLinkMain("/images/inner_car1.png");
        apartmentAndHousingAppraisal.setImportant("Требуемые документы Список документов может существенно различаться в зависимости от типа объекта. Его следует уточнять при заказе услуги оценки недвижимости.");
        apartmentAndHousingAppraisal.setMetaTitle("Оценка квартир и жилья в Томске для суда, банка, сделки");
        apartmentAndHousingAppraisal.setMetaDescription("Профессиональная оценка рыночной стоимости квартир и жилья в Томске. Подготовим официальный отчет для банка, суда, сделки или наследования. Опытные оценщики, гарантия достоверности.");
        apartmentAndHousingAppraisal.setMetaKeywords("оценка квартиры Томск, оценка стоимости недвижимости, оценка жилья для суда, отчет об оценке квартиры, рыночная стоимость квартиры, оценка недвижимости для банка, независимая оценка");
        apartmentAndHousingAppraisal.setExtraText("Наша компания имеет очень большой опыт работы и весомую практику расчета стоимости жилых и коммерческих объектов самого разного типа. Мы точно определяем текущую стоимость, благодаря чему вы не потеряете время и деньги. Наше кредо — профессиональный подход и оказание услуг высокого уровня.");
        apartmentAndHousingAppraisal.setSubtitle("Когда требуется оценка стоимости квартиры или ее доли?");
        apartmentAndHousingAppraisal.setAlias("aaha");

        Service realEstateMarketValueAppraisal = new Service();
        realEstateMarketValueAppraisal.setTitle("Оценка рыночной стоимости недвижимости");
        realEstateMarketValueAppraisal.setMainText("Независимая оценка недвижимости в Томске — услуга, которая поможет узнать рыночную стоимость объекта. Процедура должна производиться в ряде случаев: при продаже квартиры, дома, офиса и других видов коммерческой и жилой, городской и загородной недвижимости, при судебных разбирательствах и некоторых других ситуациях.");
        realEstateMarketValueAppraisal.setSubcategory(realEstateAppraisal);
        realEstateMarketValueAppraisal.setPrice("ОТ 5000 Р");
        realEstateMarketValueAppraisal.setPicLinkPreview("/images/exp_4.jpg");
        realEstateMarketValueAppraisal.setPicLinkMain("/images/nedvijimost-germanii.png");
        realEstateMarketValueAppraisal.setMetaTitle("Оценка рыночной стоимости недвижимости в Томске | Отчет");
        realEstateMarketValueAppraisal.setMetaDescription("Профессиональная оценка рыночной стоимости любой недвижимости в Томске: квартиры, дома, гаражи, коммерческие объекты. Официальный отчет для суда, банка, сделки купли-продажи.");
        realEstateMarketValueAppraisal.setMetaKeywords("оценка рыночной стоимости недвижимости Томск, оценка стоимости квартиры, оценка коммерческой недвижимости, оценка дома, оценка земельного участка, отчет о стоимости недвижимости");
        realEstateMarketValueAppraisal.setExtraText("В широком смысле, под недвижимостью подразумеваются любые земельные участки, объекты, расположенные на них, сооружения, помещения, строения, здания, гаражи, складские помещения. Они могут быть как жилыми, так и нежилыми, коммерческими и промышленными.");
        realEstateMarketValueAppraisal.setSubtitle("Данная экспертиза нужна в следующих случаях:");
        realEstateMarketValueAppraisal.setAlias("remva");

        Service apartmentFloodDamageAssessment = new Service();
        apartmentFloodDamageAssessment.setTitle("Оценка ущерба от залива квартиры");
        apartmentFloodDamageAssessment.setMainText("Независимая экспертиза при заливе в Томске— самый надежный способ разрешить конфликты между соседями. Потерпевшая сторона заинтересована получить максимальную компенсацию, включая моральный ущерб. А виновная сторона будет пытаться всеми силами уменьшить озвученную сумму выплаты. Эксперт же максимальн беспристрастно оценит размер ущерба от залива и рассчитает стоимость восстановительного ремонта. Оценка ущерба от залива квартиры для суда в Томске в рамках процессуальной деятельности проводится на основании постановления выданного судебными органами. В рамках процесса назначается экспертиза, но ее исполнителя стороны могут выбрать самостоятельно. В рамках судебной экспертизы все необходимые документы передаются судебными исполнителями.");
        apartmentFloodDamageAssessment.setSubcategory(damageAssessment);
        apartmentFloodDamageAssessment.setPrice("ОТ 3000 Р");
        apartmentFloodDamageAssessment.setPicLinkPreview("/images/exp_1.jpg");
        apartmentFloodDamageAssessment.setPicLinkMain("/images/54941.png");
        apartmentFloodDamageAssessment.setImportant("Перечень необходимых документов: Свидетельство о праве собственности либо выписка из ЕГРП. Документы БТИ (экспликация и поэтажный план). Акт управляющей компании о заливе.");
        apartmentFloodDamageAssessment.setMetaTitle("Оценка ущерба после залива квартиры в Томске для суда");
        apartmentFloodDamageAssessment.setMetaDescription("Проведем независимую оценку ущерба после залива квартиры в Томске. Беспристрастный расчет стоимости ремонта. Официальный отчет для суда и взыскания компенсации с виновника.");
        apartmentFloodDamageAssessment.setMetaKeywords("оценка ущерба от залива квартиры Томск, независимая экспертиза залива, залили квартиру оценка ущерба, оценка ущерба для суда, отчет о заливе, ущерб от протечки");
        apartmentFloodDamageAssessment.setExtraText("О факте проведения экспертизы должен быть оповещен виновник залива, поэтому его необходимо за 3 дня предупредить о визите оценщика, а лучше направить телеграмму с уведомлением, а документы сохранить для суда.");
        apartmentFloodDamageAssessment.setSubtitle("Оценка ущерба от залива будет полезна в случаях если:");
        apartmentFloodDamageAssessment.setAlias("afda");

        Service goodsExamination = new Service();
        goodsExamination.setTitle("Товароведческая экспертиза в Томске");
        goodsExamination.setMainText("Товароведческая экспертиза - это экспертиза, в процессе которой исследуются товары и их потребительские свойства. Данная экспертиза широко используется в торговле, промышленности, в случае возникновения спорных ситуаций по годности, качеству, подлинности товаров. Проведение товароведческой экспертизы возможно как в досудебном порядке, так и в случае проведения экспертизы по определению суда.");
        goodsExamination.setSubcategory(commodityExamination);
        goodsExamination.setPrice("ОТ 2000Р");
        goodsExamination.setPicLinkPreview("/images/Untitled.png");
        goodsExamination.setPicLinkMain("/images/tovar2.jpg");
        goodsExamination.setMetaTitle("Товароведческая экспертиза в Томске для суда и досудебного урегулирования");
        goodsExamination.setMetaDescription("Профессиональная товароведческая экспертиза в Томске. Проверяем качество, подлинность и дефекты товаров, одежды, обуви. Официальное заключение для суда, поставщиков и покупателей.");
        goodsExamination.setMetaKeywords("товароведческая экспертиза Томск, экспертиза качества товара, независимая экспертиза товаров, экспертиза одежды и обуви, судебная товароведческая экспертиза, экспертиза для суда");
        goodsExamination.setExtraText("Так же  юристы  нашей компании могут помочь Вам в решении вопроса как в досудебном, так и в судебном порядке.");
        goodsExamination.setSubtitle("Товароведческая экспертиза одежды и обуви");
        goodsExamination.setSubText("Экспертиза одежды - это исследование экспертом качества одежды (меха), в том числе бывшей в употреблении для определения причины образования дефектов и/или процента снижения качества одежды. Экспертиза одежды - проводится в соответствии с требованиями ГОСТ, техническими условиями, образцами товара и его дубликатами. Независимая товарная экспертиза одежды проводится посредством проведения общего осмотра представленной одежды, линейными измерениями и пр. Исходя из целей исследования наиболее часто перед экспертом ставятся следующие задачи:");
        goodsExamination.setAlias("ge");

        Service sanitaryAndEpidemlExamination = new Service();
        sanitaryAndEpidemlExamination.setTitle("Санитарно-эпидемиологическая экспертиза");
        sanitaryAndEpidemlExamination.setMainText("Наша организация осуществляет проведение экспертиз по оценке соответствия объекта исследований требованиям санитарного законодательства. Основной задачей данного рода экспертной оценки является установление соответствия (либо несоответствия) требований, установленных государственными санитарно-эпидемиологическими правилами и гигиеническими нормативами к обеспечению безопасности и  безвредности для человека факторов среды обитания, условий деятельности юридических лиц и граждан, используемых ими территорий, зданий, строений, сооружений, помещений, оборудования, транспортных средств. В отношении безопасности продукции и связанных с требованиями к продукции процессов ее производства, хранения, перевозки, реализации, эксплуатации, применения (использования) и утилизации, экспертная оценка также осуществляется на соответствие требованиям, которые устанавливаются документами, принятыми в соответствии с международными договорами Российской Федерации, и техническими регламентами.");
        sanitaryAndEpidemlExamination.setSubcategory(sanitaryAndEpidemiologicalExamination);
        sanitaryAndEpidemlExamination.setPrice("ОТ 2000Р");
        sanitaryAndEpidemlExamination.setPicLinkPreview("/images/expert-2.webp");
        sanitaryAndEpidemlExamination.setPicLinkMain("/images/saepidem.jpg");
        sanitaryAndEpidemlExamination.setMetaTitle("Санитарно-эпидемиологическая экспертиза в Томске | Протокол");
        sanitaryAndEpidemlExamination.setMetaDescription("Профессиональная санитарно-эпидемиологическая экспертиза в Томске. Проверяем объекты на соответствие СанПиН и гигиеническим нормативам. Официальное заключение для бизнеса и суда.");
        sanitaryAndEpidemlExamination.setMetaKeywords("санитарно-эпидемиологическая экспертиза Томск, экспертиза СанПиН, проверка соответствия санитарным нормам, СЭЭ экспертиза, заключение Роспотребнадзора, гигиеническая экспертиза");
        sanitaryAndEpidemlExamination.setExtraText("Круг решаемых вопросов при проведении экспертизы достаточно широк, может затрагивать практически все сферы жизнедеятельности человека. Обширное нормативно-правовое регулирование данных вопросов позволяет в каждом конкретном случае индивидуально определить объем и методы экспертизы и решить спорную ситуацию с позиции санитарно-эпидемиологического законодательства.");
        sanitaryAndEpidemlExamination.setSubtitle("Объектами экспертной оценки являются:");
        sanitaryAndEpidemlExamination.setSubText("производственные здания, строения и сооружения; объекты жилого назначения (многоквартирные жилые дома, объекты индивидуального жилищного строительства); объекты жилищно-коммунального хозяйства (парикмахерские, бани, прачечные, объекты водоснабжения и канализации, объекты сбора, хранения, размещения и утилизации отходов); организации общественного питания и торговли (кафе, рестораны, магазины и пр.); объекты социального назначения (школы, детские сады, ЛПУ); земельные участки, водные объекты; иные объекты, деятельность которых регламентирована санитарно-эпидемиологическим законодательством. ");
        sanitaryAndEpidemlExamination.setAlias("saee");

//        // UI/UX дизайн
//        Service mobileDesignService = new Service();
//        mobileDesignService.setTitle("Дизайн мобильного приложения");
//        mobileDesignService.setMainText("Полный цикл проектирования UI/UX для мобильных приложений. Прототипирование, визуальный дизайн, гайдлайны.");
//        mobileDesignService.setSubcategory(uiUx);
//        mobileDesignService.setPrice(40000);
//        mobileDesignService.setPicLinkPreview("/images/mobile-design-preview.jpg");
//        mobileDesignService.setPicLinkMain("/images/mobile-design-main.jpg");
//        mobileDesignService.setImportant("Адаптация под iOS и Android гайдлайны.");
//        mobileDesignService.setMetaTitle("UI/UX дизайн мобильных приложений");
//        mobileDesignService.setMetaDescription("Профессиональный дизайн мобильных приложений с учетом UX лучших практик.");
//        mobileDesignService.setMetaKeywords("ui, ux, дизайн, мобильные, приложения");
//        mobileDesignService.setExtraText("Создание интерактивных прототипов.");
//
//        Service webDesignService = new Service();
//        webDesignService.setTitle("Дизайн веб-сайта");
//        webDesignService.setMainText("Создание современного дизайна для веб-сайтов. User research, wireframes, visual design.");
//        webDesignService.setSubcategory(uiUx);
//        webDesignService.setPrice(35000);
//        webDesignService.setPicLinkPreview("/images/web-design-preview.jpg");
//        webDesignService.setPicLinkMain("/images/web-design-main.jpg");
//        webDesignService.setImportant("Адаптивный дизайн для всех устройств.");
//        webDesignService.setMetaTitle("Дизайн веб-сайтов | UI/UX проектирование");
//        webDesignService.setMetaDescription("Разработка удобных и красивых интерфейсов для веб-сайтов любой сложности.");
//        webDesignService.setMetaKeywords("веб-дизайн, ui, ux, интерфейсы");
//        webDesignService.setExtraText("Подготовка макетов для разработчиков.");
//
//        // Графический дизайн
//        Service logoService = new Service();
//        logoService.setTitle("Разработка логотипа");
//        logoService.setMainText("Создание уникального логотипа для вашего бренда. Несколько концепций, фирменные цвета.");
//        logoService.setSubcategory(graphicDesign);
//        logoService.setPrice(20000);
//        logoService.setPicLinkPreview("/images/logo-preview.jpg");
//        logoService.setPicLinkMain("/images/logo-main.jpg");
//        logoService.setImportant("Неограниченное количество правок в процессе.");
//        logoService.setMetaTitle("Дизайн логотипа | Создание бренда");
//        logoService.setMetaDescription("Профессиональный дизайн запоминающегося логотипа для вашей компании.");
//        logoService.setMetaKeywords("логотип, бренд, дизайн, фирменный стиль");
//        logoService.setExtraText("Предоставление логотипа во всех форматах.");
//
//        Service brandingService = new Service();
//        brandingService.setTitle("Фирменный стиль");
//        brandingService.setMainText("Разработка полного фирменного стиля: брендбук, визитки, бланки, презентации.");
//        brandingService.setSubcategory(graphicDesign);
//        brandingService.setPrice(50000);
//        brandingService.setPicLinkPreview("/images/branding-preview.jpg");
//        brandingService.setPicLinkMain("/images/branding-main.jpg");
//        brandingService.setImportant("Полный пакет брендинга под ключ.");
//        brandingService.setMetaTitle("Фирменный стиль | Брендбук разработка");
//        brandingService.setMetaDescription("Создание комплексного фирменного стиля для укрепления позиций бренда.");
//        brandingService.setMetaKeywords("фирменный стиль, брендбук, айдентика, дизайн");
//        brandingService.setExtraText("Готовые шаблоны для всех носителей.");
//
//        // SEO
//        Service seoPromotionService = new Service();
//        seoPromotionService.setTitle("SEO продвижение сайта");
//        seoPromotionService.setMainText("Комплексное SEO продвижение вашего сайта. Аудит, техническая оптимизация, контент-стратегия.");
//        seoPromotionService.setSubcategory(seo);
//        seoPromotionService.setPrice(30000);
//        seoPromotionService.setPicLinkPreview("/images/seo-preview.jpg");
//        seoPromotionService.setPicLinkMain("/images/seo-main.jpg");
//        seoPromotionService.setImportant("Ежемесячные отчеты о результатах.");
//        seoPromotionService.setMetaTitle("SEO продвижение | Поисковая оптимизация");
//        seoPromotionService.setMetaDescription("Профессиональное SEO продвижение сайтов для роста органического трафика.");
//        seoPromotionService.setMetaKeywords("seo, продвижение, оптимизация, трафик");
//        seoPromotionService.setExtraText("Работа с семантическим ядром и ссылочной массой.");
//
//        Service seoAuditService = new Service();
//        seoAuditService.setTitle("Технический SEO аудит");
//        seoAuditService.setMainText("Глубокий анализ технического состояния сайта. Выявление ошибок и рекомендации по исправлению.");
//        seoAuditService.setSubcategory(seo);
//        seoAuditService.setPrice(15000);
//        seoAuditService.setPicLinkPreview("/images/seo-audit-preview.jpg");
//        seoAuditService.setPicLinkMain("/images/seo-audit-main.jpg");
//        seoAuditService.setImportant("Подробный отчет с приоритизацией задач.");
//        seoAuditService.setMetaTitle("SEO аудит | Технический анализ сайта");
//        seoAuditService.setMetaDescription("Профессиональный технический SEO аудит для улучшения позиций в поиске.");
//        seoAuditService.setMetaKeywords("seo, аудит, технический, анализ");
//        seoAuditService.setExtraText("Рекомендации по исправлению ошибок.");
//
//        // SMM
//        Service instagramService = new Service();
//        instagramService.setTitle("Ведение Instagram");
//        instagramService.setMainText("Полное ведение бизнес-аккаунта в Instagram: контент-план, сторис, реклама, аналитика.");
//        instagramService.setSubcategory(smm);
//        instagramService.setPrice(25000);
//        instagramService.setPicLinkPreview("/images/instagram-preview.jpg");
//        instagramService.setPicLinkMain("/images/instagram-main.jpg");
//        instagramService.setImportant("Рост вовлеченности гарантирован.");
//        instagramService.setMetaTitle("SMM Instagram | Продвижение бизнеса");
//        instagramService.setMetaDescription("Профессиональное ведение Instagram для увеличения продаж и узнаваемости бренда.");
//        instagramService.setMetaKeywords("smm, instagram, продвижение, социальные сети");
//        instagramService.setExtraText("Еженедельные отчеты о результатах.");
//
//        Service smmStrategyService = new Service();
//        smmStrategyService.setTitle("Стратегия SMM");
//        smmStrategyService.setMainText("Разработка комплексной SMM стратегии для вашего бизнеса. Анализ ЦА, выбор площадок, KPI.");
//        smmStrategyService.setSubcategory(smm);
//        smmStrategyService.setPrice(20000);
//        smmStrategyService.setPicLinkPreview("/images/smm-strategy-preview.jpg");
//        smmStrategyService.setPicLinkMain("/images/smm-strategy-main.jpg");
//        smmStrategyService.setImportant("Готовая стратегия на 6 месяцев.");
//        smmStrategyService.setMetaTitle("SMM стратегия | Продвижение в соцсетях");
//        smmStrategyService.setMetaDescription("Создание эффективной SMM стратегии для достижения маркетинговых целей.");
//        smmStrategyService.setMetaKeywords("smm, стратегия, продвижение, социальные сети");
//        smmStrategyService.setExtraText("Рекомендации по контенту и таргетингу.");

        // Сохраняем все услуги
        List<Service> services = Arrays.asList(
                independentDamageAssessmentAfterCarAccident, vehicleRepairQualityExamination, professionalVehicleAppraisal, apartmentAndHousingAppraisal,
                realEstateMarketValueAppraisal, apartmentFloodDamageAssessment, goodsExamination, sanitaryAndEpidemlExamination
//                mobileDesignService, webDesignService, logoService, brandingService,
//                seoPromotionService, seoAuditService, instagramService, smmStrategyService
        );

        serviceRepository.saveAll(services);
    }
}