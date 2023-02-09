# Spring Boot Example Repository

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=step-beyond_spring-boot-examples&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=step-beyond_spring-boot-examples)

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
  * [Example](https://github.com/step-beyond/spring-boot-examples/blob/main/infrastructure-petstore-rest-client/src/main/java/today/stepbeyond/examples/springbootexamples/infrastructure/gateways/api/PetStoreApi.java)

### Testing Tools

#### Unit Tests

Unit Tests follow the naming pattern "<TestName>Test". 

* [JUnit5](https://junit.org/junit5/docs/current/user-guide/)
* [AssertJ](https://assertj.github.io/doc/) 
  * used to write fluent assertions
  * [Example](https://github.com/step-beyond/spring-boot-examples/blob/main/domain/src/test/java/today/stepbeyond/examples/springbootexamples/usecases/DogUseCasesTest.java#L49-L51)
* [Mockito](https://site.mockito.org/)
  * used to mock dependencies to other services
  * [Example](https://github.com/step-beyond/spring-boot-examples/blob/main/domain/src/test/java/today/stepbeyond/examples/springbootexamples/usecases/DogUseCasesTest.java)

#### Integration Tests

Integration Tests follow the naming pattern "<TestName>IT". An integration test scenario is created with tools, which 
can be easily integrated into JUnit & SpringBoot.

* [WireMock](https://wiremock.org/) 
  * used to test outgoing http requests
  * [Example](https://github.com/step-beyond/spring-boot-examples/blob/main/infrastructure-petstore-rest-client/src/test/java/today/stepbeyond/examples/springbootexamples/infrastructure/gateways/PetStoreRestClientIT.java)
* [Testcontainers](https://www.testcontainers.org/) 
  * used to start any type of external services, i.e. databases or message queues.

#### Architectural Tests

* [ArchUnit](https://github.com/TNG/ArchUnit/tree/main)
  * used to enforce a defined architecture patterns, conventions and much more
  * [Example](https://github.com/step-beyond/spring-boot-examples/blob/main/infrastructure-petstore-rest-client/src/test/java/today/stepbeyond/examples/springbootexamples/ArchitecturalTest.java)

#### Conventions

This repository follows [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0-beta.4/) 