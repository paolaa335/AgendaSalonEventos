# ========== Build stage ==========
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copia Gradle y fuentes
COPY gradlew ./
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src

# En Windows a veces hace falta dar permisos:
# RUN chmod +x gradlew

# Construye el JAR
RUN ./gradlew clean bootJar --no-daemon

# ========== Run stage ==========
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia el JAR compilado
COPY --from=builder /app/build/libs/*.jar app.jar

# Puerto interno (el de tu app)
EXPOSE 8080

# Usaremos el perfil "docker"
ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java","-jar","/app/app.jar"]
