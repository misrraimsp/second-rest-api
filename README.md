# REST API for Money Transfer Simulation

## Table of contents
* [General Info](#general-info)
* [Technologies](#technologies)
* [Implementation notes](#implementation-notes)
* [Copyright](#copyright)
* [References](#references)

## General Info
This API model an account service according to REST guidelines. It's able to create, access, find accounts and to transfer money between them.

For more info:
- [Task Definition.pdf](https://github.com/misrraimsp/second-rest-api/blob/master/Spring%20Boot%20Rest%20Exercise.pdf)

## Technologies
Project is created with:
* Java
* Spring Boot
* Spring Web MVC
* Spring Data JPA
* Spring HATEOAS
* H2
* JUnit 5
* Mockito
* Git

## Implementation notes:
- Inputs will not be validated (for example with @Valid). The requirements do not ask for it, and I am short on time. It would be interesting to add it at some future point.
- 4 accounts functionalities have been implemented:
  - see all accounts at *get(/accounts)*
  - see an account at *get(/accounts/{accountId})*
  - create an account at *post(/accounts)*
  - edit an account at *put(/accounts/{accountId})*
- It has been decided that *put* also returns status 201
- The transfers will be modeled by the entity 'Transfer'
- transfers are perform at *post(/transfers)*, passing as argument a 'Transfer' in json
- The @Transactional annotation has been used to ensure that transfers are perform transactionally
- The transaction propagation has been set to 'nested', considering it the most appropriate ([source](https://thorben-janssen.com/transactions-spring-data-jpa/))
- when trying an illegal transaction or when trying to use an account that doesn't exist, the system returns an error message in json format
- there has been no need to unit test the persistence layer since no new queries have been added to the ones inherited from JpaRepository
- It would be interesting to take the currency into account when making transactions. For example, a service could be created to provide the exchange rate. In this exercise, for simplicity and due to not being explicitly indicated in the requirements, this issue has been ignored.

## Copyright

- **year**: 2020
- **project name**: secondrest
- **author**: MISRRAIM SUÁREZ PÉREZ
- **mail**: misrraimsp@gmail.com
- **last version**: 13/09/2020

## References
- [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
- [Unit Testing with Spring Boot](https://reflectoring.io/unit-testing-spring-boot/)
- [Testing MVC Web Controllers with Spring Boot and @WebMvcTest](https://reflectoring.io/spring-boot-web-controller-test/)
- [Testing JPA Queries with Spring Boot and @DataJpaTest](https://reflectoring.io/spring-boot-data-jpa-test/)
- [Integration Tests with Spring Boot and @SpringBootTest](https://reflectoring.io/spring-boot-test/)
- [Mocking with (and without) Spring Boot](https://reflectoring.io/spring-boot-mock/)
- [Testing a HATEOAS service](https://lankydan.dev/2017/09/18/testing-a-hateoas-service)
- [Global exception handling with @ControllerAdvice](https://lankydan.dev/2017/09/12/global-exception-handling-with-controlleradvice)

