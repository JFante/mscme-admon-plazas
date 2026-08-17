# Etapa 1: Build con configuración completa
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Variables de entorno para Maven
ENV MAVEN_OPTS="--enable-preview -Xmx2048m"

WORKDIR /app

# Copiar POM primero para mejor cache
COPY pom.xml .

# Descargar dependencias
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar con TODAS las opciones necesarias
RUN mvn clean compile -Dmaven.compiler.enablePreview=true \
    -Dmaven.compiler.source=21 \
    -Dmaven.compiler.target=21 \
    -Dmaven.compiler.compilerArgs="--enable-preview,-parameters"

# Empaquetar
RUN mvn package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copiar JAR
COPY --from=build /app/target/*.jar app.jar

# Variables de entorno para Java
ENV JAVA_OPTS="--enable-preview --add-opens java.base/java.lang=ALL-UNNAMED -Dspring.profiles.active=prod"

EXPOSE 8099

# Salud check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8099/mscme-admon-plazas/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
