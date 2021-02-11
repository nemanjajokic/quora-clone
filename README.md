# Quora Clone App
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Quora Clone Application built using Spring Boot and Angular. The idea was to clone Quora application with some basic functionalities.

It was made using Spring Boot, Angular, Spring Security, Spring Data JPA, Spring Data REST and MySQL. Jason Web Token was used for authorization.

Users can upload profile picture, create topics, ask questions and answer questions.

<img src="https://i.imgur.com/YTvIlHZ.png">

## Requirements
* JDK 8+
* Node.js and npm
* Angular CLI
* MySQL(or any other RDBMS)
* Maven

## Run Projects
1. configure application.properties
2. backend `$ mvn spring-boot:run`
3. frontend `$ ng serve`
4. Now visit `http://localhost:4200`
5. Swagger documantation `localhost:8080/swagger-ui/`

* File to modify **application.properties** (in `src\main\resources`) change/add database configuration, mail properties, storage path in file system for user images
