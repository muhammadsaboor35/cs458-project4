# COVID Symptoms Tracking Application Backend

This project contains the code for the frontend of CS458 Project 4, COVID Symptoms Tracking application. The src/main folder contains the code for the backend application whereas the src/test folder contains test classes. The database folder contains SQLite database. The backned uses Keycloak OpenID-connect as its authentication server. The realm configuration is given in the realm-export.json file.

## Starting the Authentication Server

You can run the keycloak authentication server in a docker container using:
```shell script
docker pull quay.io/keycloak/keycloak:latest
docker run --name keycloak -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin -p 8180:8080 -p 8543:8443 quay.io/keycloak/keycloak:latest
```

## Running the application

You can run the application using:
```shell script
./mvnw compile quarkus:dev
```

## Testing the application

You can run the unit tests using:
```shell script
./mvnw test
```
