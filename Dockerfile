FROM maven:3.8.3-openjdk-17-slim as Builder
WORKDIR /build
COPY ./src ./src
COPY ./pom.xml ./pom.xml
RUN mvn clean package -DskipTests
FROM bellsoft/liberica-openjdk-alpine-musl
WORKDIR /app
COPY --from=builder /build/target/web-app.jar .
CMD ["java", "-jar", "web-app.jar"]