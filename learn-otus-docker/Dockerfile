FROM maven:3.6.3-openjdk-11-slim
ENV PROJECT_DIR="/otus/project"
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
ADD ./pom.xml $PROJECT_DIR
ADD ./src/ $PROJECT_DIR/src
RUN mvn install

FROM openjdk:11-jre-slim
ENV PROJECT_DIR="/otus/project"
WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/learn-otus-docker-1.0.jar $PROJECT_DIR/
EXPOSE 8080
CMD ["java", "-jar", "/otus/project/learn-otus-docker-1.0.jar"]