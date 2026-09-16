# ================================
# Etapa 1: Build del proyecto
# ================================
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# ================================
# Etapa 2: Imagen final de ejecución
# ================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/wishstore-catalog-service.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]