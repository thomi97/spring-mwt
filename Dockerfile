FROM eclipse-temurin:21

WORKDIR /app

COPY target/mwt-spring-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8090

CMD ["java", "-jar", "app.jar"]