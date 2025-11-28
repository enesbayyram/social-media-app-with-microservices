## ---------- Build aşaması ----------
#FROM maven:3.9.4-eclipse-temurin-17 AS builder
#WORKDIR /src
#COPY . .
#RUN mvn clean package -DskipTests
#
##Burada maven kullanarak src yi hedef alarak bir build alıyoruz.
#
## ---------- Run aşaması ----------
#FROM openjdk:17-jdk-slim
#VOLUME /tmp
#COPY --from=builder /src/target/limit-services-*.jar app.jar
#
##Aldığmıız jar file(build) dosyasını ise JDK ile birleştirerek bir image haline getirtiriyoruz.
#
#
#ENTRYPOINT ["java","-jar","/app.jar"]


FROM eclipse-temurin:17-jdk
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
