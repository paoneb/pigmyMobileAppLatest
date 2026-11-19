FROM eclipse-temurin:21-jdk AS build

WORKDIR /app
COPY . .


# 1. Copy Maven wrapper and POM files first to cache dependencies
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /app/target/pigmyMobileApp-*[!plain].jar app.jar


EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]