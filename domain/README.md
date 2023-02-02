# Domain

This module provides just business logic.

According to Clean Architecture & Domain Driven Design (DDD) the domain must 
not depend on any type of framework like Spring or technologies like REST or GraphQL. 
It is said, that those technologies should be separated from the actual domain logic.
This module has a dependency to Spring though, but it is just allowed to use inversion of control (IoC) on use case level, 
i.e. services are annotated with `@Service`, those instances can be injected into class via constructors. 