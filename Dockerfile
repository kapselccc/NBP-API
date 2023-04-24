FROM eclipse-temurin:17-jdk-jammy
COPY target/NPB_API-0.0.1-SNAPSHOT.jar NPB_API-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "NPB_API-0.0.1-SNAPSHOT.jar"]
EXPOSE 7777