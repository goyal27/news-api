FROM maven:3.8.2-jdk-8-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:8
COPY --from=build /home/app/target/news-api-1.0.jar /usr/local/lib/news-api-1.0.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/usr/local/lib/news-api-1.0.jar"]