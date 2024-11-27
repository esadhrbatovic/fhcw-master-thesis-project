# Esad Hrbatovic - Master Thesis Project
WIP

## API Documentation:

### Generate Redoc HTML:
This command generates a user-friendly HTML version of your API documentation from the openapi.yaml file using Redocly.

```bash
npx @redocly/cli build-docs openapi.yaml
```

## Building Native Images:

### Quarkus:

#### Build Native Executable:

This command builds a native executable for your Quarkus application using the native profile and sets the quarkus.native.container-build property to true.

```bash
mvn package -Pnative --define quarkus.native.container-build=true
```

#### Containerize:

```bash
docker build -f src/main/docker/Dockerfile.native-micro -t esadh/quarkus-servicename .
```

#### Run Examples:

```bash
docker run -i --rm --name eh-ma-quarkus-authservice -p 8081:8081 esadh/quarkus-authservice
```
```bash
docker run -i --rm --name eh-ma-quarkus-userservice -p 8082:8082 esadh/quarkus-userservice
```

### Spring Boot:

#### Build Native Image:

```bash
mvn -Pnative spring-boot:build-image
```

#### Run Examples:

```bash
docker run -i --rm --name eh-ma-springboot-apigateway -p 8080:8080 esadh/springboot-apigateway
```

### Micronaut:

#### Build Native Image:


```bash
./gradlew dockerBuildNative
```

or for optimized native mage: 

```bash
./gradlew optimizedDockerBuildNative
```

#### Run Examples:

```bash
docker run -i --rm --name eh-ma-micronaut-apigateway -p 8080:8080 esadh/micronaut-apigateway
```