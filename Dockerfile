FROM openjdk:17

WORKDIR /wandu-backend
EXPOSE 8082

# JAR 파일 명시적으로 지정 (빌드된 jar 이름으로 수정)
ARG JAR_FILE=build/libs/wandu-backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]