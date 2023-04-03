FROM openjdk:8-jdk-alpine
EXPOSE 8089
ADD target/pfe-sdf-1.0.jar pfe-sdf-1.0.jar
ENTRYPOINT ["java","-jar","/pfe-sdf-1.0.jar"]