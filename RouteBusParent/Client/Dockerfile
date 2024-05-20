# Usamos una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el archivo JAR generado por Maven
COPY target/client-1.0.0-SNAPSHOT.jar app.jar

# Exponemos el puerto en el que correrá la aplicación
EXPOSE 8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
