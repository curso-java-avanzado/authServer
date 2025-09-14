# =========================
# Etapa 1: Build con Gradle
# =========================
FROM gradle:8.7-jdk17 AS build

WORKDIR /app

# Copiamos solo archivos de configuración primero para cachear dependencias
COPY gradlew ./
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Damos permisos de ejecución al wrapper
RUN chmod +x gradlew

# Descargamos dependencias (no falla si no hay tests aún)
RUN ./gradlew dependencies --no-daemon || return 0

# Ahora copiamos el resto del proyecto
COPY . .

# Compilamos sin tests
RUN ./gradlew clean bootJar -x validateStructure -x test --no-daemon


# =========================
# Etapa 2: Runtime
# =========================
FROM eclipse-temurin:17-jdk-alpine

# Crear usuario no-root
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copiamos el jar desde la etapa de build y lo renombramos
COPY --from=build /app/applications/app-service/build/libs/*.jar app.jar

# Exponer el puerto (ajusta si es 8081 en solicitudes)
EXPOSE 8080

# Variables de entorno JVM (compatibles con HotSpot)
ENV JAVA_OPTS="\
  -XX:+UseContainerSupport \
  -XX:MaxRAMPercentage=70 \
  -Djava.security.egd=file:/dev/./urandom \
"

# Usar usuario seguro
USER appuser

# Arranque de la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
