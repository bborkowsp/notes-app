FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests
ENTRYPOINT ["java", "-Dspring.profiles.active=production", "-jar", "/app/target/notes-app-0.0.1-SNAPSHOT.jar"]
