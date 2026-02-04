# Etapa de build.
FROM maven:3.8.8-eclipse-temurin-17-alpine AS builder
WORKDIR /build

# Copiamos el pom y descargamos las dependencias.
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código fuente y compilamos.
COPY src ./src
RUN mvn clean package -DskipTests

# Layertools descompone el JAR en 4 carpetas: dependencias, loader, snapshots y la app 
# para reutilizar capas.
RUN java -Djarmode=layertools -jar target/*.jar extract --destination target/extracted

# Etapa de ejecución.
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Establecemos el perfil.
ENV SPRING_PROFILES_ACTIVE=docker

# Creamos un usuario del sistema sin privilegios por seguridad.
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Copiamos las capas extraídas del builder de la que menos cambia a la que más:
# 1. Dependencias externas.
COPY --from=builder /build/target/extracted/dependencies/ ./
# 2. El cargador de clases de Spring.
COPY --from=builder /build/target/extracted/spring-boot-loader/ ./
# 3. Dependencias snapshot.
COPY --from=builder /build/target/extracted/snapshot-dependencies/ ./
# 4. El código de la aplicación.
COPY --from=builder /build/target/extracted/application/ ./

# Exponemos el puerto
EXPOSE 8080

# Usamos el launcher nativo de Spring para mejorar el tiempo de arranque y que la
# JVM gestione mejor las capas extraídas.
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]