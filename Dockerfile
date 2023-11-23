FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar mallet-core-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080