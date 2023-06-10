FROM maven:3.8.5-eclipse-temurin-17 as builder

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN mvn install --no-transfer-progress -DskipTests

FROM builder as dev-envs
CMD ["mvn", "spring-boot:run"]

FROM builder as prepare-production
RUN mkdir -p target/dependency
WORKDIR /workspace/app/target/dependency
RUN jar -xf ../*.jar

FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S spring && adduser -S spring -G spring
RUN mkdir /files
RUN chown spring:spring /files
USER spring:spring
VOLUME /tmp /files
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=prepare-production ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=prepare-production ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8080
ENTRYPOINT ["java","-cp","app:app/lib/*","com.emontazysta.EMontazystaApplication"]