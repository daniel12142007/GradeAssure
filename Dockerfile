FROM openjdk:17 as build
WORKDIR /app
COPY . ./
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

FROM openjdk:17.0.2-jdk-slim
WORKDIR /app
COPY --from=build /app/target/Kochkor-Ata-0.0.1-SNAPSHOT.jar ./
CMD ["java", "-jar", "gradeAssure-0.0.1-SNAPSHOT.jar"]
EXPOSE 2023