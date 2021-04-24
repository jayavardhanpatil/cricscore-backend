FROM openjdk:8
COPY target/*.jar /app.jar
EXPOSE 5050/tcp
ENTRYPOINT ["java", "-jar", "/app.jar"]
