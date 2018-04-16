FROM openjdk:8-jre-alpine

COPY ./target/*.jar /lib/app.jar

ENTRYPOINT sh -c 'java -jar /lib/app.jar --spring.profiles.active=docker'

EXPOSE 8082