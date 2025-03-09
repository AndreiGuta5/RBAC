FROM openjdk:17
ADD target/rest-1.0-SNAPSHOT.jar rest-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","rest-1.0-SNAPSHOT.jar"]