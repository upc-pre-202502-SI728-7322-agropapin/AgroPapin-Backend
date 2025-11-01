FROM maven:3-eclipse-temurin-17 as build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-alpine
WORKDIR /app

RUN groupadd -r spring && useradd -r -g spring spring
USER spring

COPY --from=build /target/*.jar demo.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/demo.jar"]