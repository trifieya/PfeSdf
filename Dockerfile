FROM openjdk:8-jdk-alpine
RUN apk --no-cache add curl
RUN curl -u admin:nexus -o Pfe-sdf-1.0.jar "http://192.168.162.222:8081/repository/maven-releases/tn/esprit/sdf/Pfe-sdf/1.0/Pfe-sdf-1.0.jar" -L
ENTRYPOINT ["java","-jar","/Pfe-sdf-1.0.jar"]
EXPOSE 8089



