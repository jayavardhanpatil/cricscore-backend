#FROM openjdk:8
#COPY target/*.jar /app.jar
#EXPOSE 5050/tcp
#ENTRYPOINT ["java", "-jar", "/app.jar"]

FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#EXPOSE 5050
#ADD target/*.jar app.jar
#ENV JAVA_OPTS=""
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]
#
## For Java 11, try this
#FROM adoptopenjdk/openjdk11:alpine-jre

# Refer to Maven build -> finalName
ARG JAR_FILE=target/crickscore-0.0.1-SNAPSHOT.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]