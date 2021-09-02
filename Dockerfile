FROM openjdk:8
ADD target/news-api-1.0.jar news-api-1.0.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "news-api-1.0.jar"]