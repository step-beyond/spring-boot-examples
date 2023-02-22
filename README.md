# Spring Boot Example Repository

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=step-beyond_spring-boot-examples&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=step-beyond_spring-boot-examples)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=step-beyond_spring-boot-examples&metric=coverage)](https://sonarcloud.io/summary/new_code?id=step-beyond_spring-boot-examples)
[![Java CI](https://github.com/step-beyond/spring-boot-examples/actions/workflows/build.yaml/badge.svg)](https://github.com/step-beyond/spring-boot-examples/actions/workflows/build.yaml)

This repository contains a bunch of examples of how to deal with common 
technical challenges, which developers face frequently, when implementing services 
with SpringBoot.

The project structure is oriented to domain driven design (DDD) and clean architecture.
However, the primary purpose of this repository is to serve continuing education, and 
therefore the architectural concepts are not consistently followed.

## Tools, Frameworks & Conventions

### Spring

#### HTTP Clients

##### REST Clients

* [Feign - Declarative REST Client](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html)
  * used to implement a REST client
  * [Example](infrastructure-petstore-rest-client/src/main/java/today/stepbeyond/examples/springbootexamples/infrastructure/gateways/api/PetStoreApi.java)

#### Messaging

* [RabbitMQ](https://www.rabbitmq.com/)
  * Listener [Example](infrastructure-pet-registration-rabbitmq-listener/src/main/java/today/stepbeyond/examples/springbootxamples/infrastructure/rabbitmq/RabbitMqListener.java)
  * Integration tested with [Testcontainers](infrastructure-pet-registration-rabbitmq-listener/src/test/java/today/stepbeyond/examples/springbootxamples/infrastructure/rabbitmq/RabbitMqListenerIT.java)
* [JMS - ActiveMQ](https://activemq.apache.org/)
  * Producer [Example](infrastructure-pet-jms/src/main/java/today/stepbeyond/examples/springbootexamples/infrastructure/jms/pet/event/JmsPetEventPublisher.java)
  * Integration test with [Testcontainers](application/src/test/java/today/stepbeyond/examples/springbootexamples/application/domain/BirthOfDogIT.java)

### Testing Tools

#### Unit Tests

Unit Tests follow the naming pattern "<TestName>Test". 

* [JUnit5](https://junit.org/junit5/docs/current/user-guide/)
* [AssertJ](https://assertj.github.io/doc/) 
  * used to write fluent assertions
  * [Example](domain/src/test/java/today/stepbeyond/examples/springbootexamples/domain/usecases/DogUseCasesTest.java#L74-L76)
  * [Example](application/src/test/java/today/stepbeyond/examples/springbootexamples/application/domain/BirthOfDogIT.java#L69-L83) for soft assertions. Soft assertions are useful, when multiple assertions are examined in one test. With soft assertions you can disable the default fail fast behavior.
* [Mockito](https://site.mockito.org/)
  * used to mock dependencies to other services
  * [Example](domain/src/test/java/today/stepbeyond/examples/springbootexamples/domain/usecases/DogUseCasesTest.java)

#### Integration Tests

Integration Tests follow the naming pattern "<TestName>IT". An integration test scenario is created with tools, which 
can be easily integrated into JUnit & SpringBoot.

* [WireMock](https://wiremock.org/) 
  * used to test outgoing http requests
  * [Example](infrastructure-petstore-rest-client/src/test/java/today/stepbeyond/examples/springbootexamples/infrastructure/gateways/PetStoreRestClientIT.java)
* [Testcontainers](https://www.testcontainers.org/) 
  * used to start any type of external services, i.e. databases or message queues.
  * [Example](application/src/test/java/today/stepbeyond/examples/springbootexamples/application/domain/BirthOfDogIT.java#L40-L42)
* [Awaitility](http://www.awaitility.org/)
  * used to ensure a given state after a given time. Practical especially for asynchronous communications.
  * [Example](application/src/test/java/today/stepbeyond/examples/springbootexamples/application/domain/BirthOfDogIT.java#L87-L94)

#### Architectural Tests

* [ArchUnit](https://github.com/TNG/ArchUnit/tree/main)
  * used to enforce a defined architecture patterns, conventions and much more
  * Examples:
    * [Module constraints](domain/src/test/java/today/stepbeyond/examples/springbootexamples/domain/ArchitecturalTest.java)
    * [Architecture application tests](application/src/test/java/today/stepbeyond/examples/springbootexamples/ArchTests.java)

### Deployment Tools

* [jib](https://github.com/GoogleContainerTools/jib/)
  * used to build an image without a Docker daemon
  * [Example](application/build.gradle#L31-38)
* [Docker Compose](https://docs.docker.com/compose/)
  * used to start the service together with the surrounding infrastructure, i.e. message broker, databases, etc.
  * [Example](docker-compose.yml)
* [Semantic Release](https://github.com/semantic-release/semantic-release)
  * used to create releases based on semantic versioning. With the conventional commits, the proper release version (MAJOR.MINOR.PATCH)
    will be resolved.

#### Conventions

* This repository follows [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0-beta.4/)
* [Distroless container images](https://github.com/GoogleContainerTools/distroless) contain only your
  application and its runtime dependencies. They do not contain package managers, shells or any other
  programs you would expect to find in a standard Linux distribution.
  * [Example in Jib](application/build.gradle#L34) 
