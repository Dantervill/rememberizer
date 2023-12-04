FROM amazoncorretto:17.0.9
COPY target/rememberizer-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
