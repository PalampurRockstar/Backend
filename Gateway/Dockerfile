# Maven build container 
FROM maven:3.6.3-jdk-8 AS maven_build
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

FROM openjdk:8
EXPOSE 8282
COPY --from=maven_build /tmp/target/gateway.jar /data/gateway.jar
CMD java -jar /data/gateway.jar

