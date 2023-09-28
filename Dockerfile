FROM gradle:8.3-jdk17

WORKDIR /home/gradle/project

COPY . .
RUN echo "systemProp.http.proxyHost=krmp-proxy.9rum.cc\nsystemProp.http.proxyPort=3128\nsystemProp.https.proxyHost=krmp-proxy.9rum.cc\nsystemProp.https.proxyPort=3128" > /root/.gradle/gradle.properties

RUN gradle wrapper

RUN ./gradlew bootJar


CMD ["java","-jar","-Dspring.profiles.active=prod", "/home/gradle/project/build/libs/goorm-7th-earth-0.0.1-SNAPSHOT.jar"]