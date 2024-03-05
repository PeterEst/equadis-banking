# build
FROM maven:3-eclipse-temurin-17-alpine AS build
COPY . /app
WORKDIR /app
RUN mvn package -DskipTests

 # slim image
FROM eclipse-temurin:17-jre-jammy
COPY --from=build /app/target/equadis-backend-0.0.1.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]