#FROM openjdk:8
#COPY target/*.jar /app.jar
#EXPOSE 5050/tcp
#ENTRYPOINT ["java", "-jar", "/app.jar"]

FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 5050
ADD target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]