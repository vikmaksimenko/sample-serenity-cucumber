# Sample Serenity Cucumber REST Project

This project is based on https://github.com/serenity-bdd/serenity-rest-starter project. 
It was updated to test GitHub Gists API.

## Prerequisites

1. Java 8
2. Maven 3

## Running tests on local environment

1. Make sure that you have JDK 1.8 or greater and Maven 3 installed.
2. Put your GitHub username and token into GITHUB_USER and GITHUB_TOKEN env variables
3. Run tests with `mvn clean verify`

## Running tests with Docker

If you have Docker installed, you may run tests in Docker containers. Don't forget to update
GITHUB_USER and GITHUB_TOKEN env variables

```shell
docker run -it --rm --name my-maven-project -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven -e GITHUB_TOKEN=${GITHUB_TOKEN} -e GITHUB_USER=${GITHUB_USER} maven:3.3-jdk-8 mvn clean verify
```

## Improvements 

1. Implement Screenplay patter and use actors. 
2. Find a way to simplify support of authorised and not-authorised users.
3. Make base URL configurable.    
3. Setup AccountBroker to guarantee that the parallel tests don't use similar account. 
4. For non-production environment test accounts can be read from file or DB.

