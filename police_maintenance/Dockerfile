	FROM openjdk:8-jdk-alpine
    VOLUME /tmp
    ARG JAR_FILE
    COPY ${JAR_FILE} policemaintenance.jar
    ENTRYPOINT ["java","-jar","/policemaintenance.jar"]