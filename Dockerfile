# Используем образ с предустановленным Maven и Java 17
FROM maven:3.9.6-eclipse-temurin-17

# Рабочая директория
WORKDIR /app

# Копируем всё содержимое проекта
COPY . .

# Собираем проект (без запуска тестов)
RUN mvn clean package -DskipTests

# Запускаем JAR
CMD ["java", "-jar", "target/website-1.0.0.jar"]