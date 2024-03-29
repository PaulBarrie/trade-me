ARG MAVEN_TAG=3.6.3-openjdk-17-slim
ARG MAVEN_DIGEST=sha256:ead687c670f30898fc28e6c7b9dab652360d522b8912ba96eca0f08592a73eec

ARG OPENJDK_TAG=11.0.13-slim-buster
ARG OPENJDK_DIGEST=sha256:75fb50b6f622d02892bed37f9c5479065612f2cc93ebe9099670b0f6ae57b9ef

FROM maven:${MAVEN_TAG}@${MAVEN_DIGEST} AS maven

WORKDIR /usr/src/app
COPY . .
RUN mvn clean package -DskipTests=true

FROM openjdk:${OPENJDK_TAG}@${OPENJDK_DIGEST} AS java

WORKDIR /run
COPY --from=maven /usr/src/app/target/trademe-1.0-SNAPSHOT.jar .

ENTRYPOINT ["java","-jar","trademe-1.0-SNAPSHOT.jar"]

