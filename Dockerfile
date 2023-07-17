
#FROM openjdk:8-jdk-alpine
#RUN apk --no-cache add curl
#RUN curl -u admin:nexus -o Pfe-sdf-1.0.jar "http://192.168.162.222:8081/repository/maven-releases/tn/sdf/Pfe-sdf/1.0/Pfe-sdf-1.0.jar" -L
#ENTRYPOINT ["java","-jar","/Pfe-sdf-1.0.jar"]
#EXPOSE 8087


FROM openjdk:8-jdk-alpine
RUN apk --no-cache add curl
RUN curl -u admin:nexus -o Pfe-sdf-1.0.jar "http://192.168.162.222:8081/repository/maven-releases/tn/sdf/Pfe-sdf/1.0/Pfe-sdf-1.0.jar" -L
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG DEPENDENCY=app.jar
COPY ${DEPENDENCY} /Pfe-sdf-1.0.jar
ENTRYPOINT ["java","-jar","/Pfe-sdf-1.0.jar"]
EXPOSE 8087



