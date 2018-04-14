FROM openjdk:8-jre-alpine

COPY ./target/*.jar /lib/app.jar
CMD ["java", "-jar", "/lib/app.jar"]

EXPOSE 9999