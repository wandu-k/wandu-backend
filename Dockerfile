FROM openjdk:17

WORKDIR /wandu-backend

# JAR 파일 명시적으로 지정 (빌드된 jar 이름으로 수정)
COPY app.jar .

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/wandu-backend/app.jar", "--spring.profiles.active=prod"]