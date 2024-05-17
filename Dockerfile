FROM openjdk:17
COPY ./build/libs/shipdoc-0.0.1-SNAPSHOT.jar shipdoc.jar
ENTRYPOINT ["java", "-jar", "shipdoc.jar"]
