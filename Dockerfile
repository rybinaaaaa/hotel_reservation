FROM openjdk:23-ea-jdk-oracle

ADD target/reservationSystem-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]