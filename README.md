# Recipe

## Table of contents
1. [Introduction](#introduction)
2. [Running the application](#running-the-application)
3. [Testing the application](#testing-the-application)
   - [Unit testing](#unit-testing)
   - [Integration testing](#integration-testing)
   - [API documentation](#api-documentation)
4. [Architectural decisions](#architectural-decisions)
   - [Project structure](#project-structure)
   - [Mysql JPA](#mysql-jpa)
   - [Models](#models)
     - [Recipe](#recipe)
     - [Ingredient](#ingredient)

## Introduction
Recipe is a REST (-like) API that lets people manage their recipes.

## Running the application
In order to run the application [Docker](https://www.docker.com/) must be installed. 
By running `./scripts/create-mysql-db.sh` Docker will create a container using the [Mysql 8](https://hub.docker.com/_/mysql) image.
After the container is started and runs, the application can be started by using `mvn spring-boot: run`

## Testing the application
The application can be tested using `mvn test`

### Unit testing
The application contains a few unit tests in the `src/test/nl.quintor.recipe/unit` directory.

### Integration testing
The integration tests are located in the `src/test/nl.quintor.recipe/integration` directory and require the database to be running.
I chose to use [WebTestClient](https://docs.spring.io/spring-framework/reference/testing/webtestclient.html) as it is a newer, cleaner way of testing compared to TestRestTemplate.

### API documentation
After running the application, the API documentation can be found at http://localhost:8080/swagger.

## Architectural decisions
The following are some of the architectural decisions I took.

### Project structure
Although in the past I structured my projects based on what they do e.g. `/controllers`, `/services`, 
I recently started using a structure based on the domain. The main pro for me is the locality of related files.
The `/recipe` directory contains everything, from model to repository, related to a Recipe. 

### MySQL JPA
I used MySQL in combination with JPA due to familiarity. Although I have experience with PostgreSQL, and although
it might be 'better' in the sense of features and performance
I find MySQL easier to work with when doing rapid development.

### Models
I chose to have a **Recipe** model and an **Ingredient** model. 

#### Recipe
Based on the assignment, having a recipe model makes the most sense as it is the primary entity in the domain.
Recipe and Ingredient have a unidirectional many-to-many relationship as the current state of the application only requires
ingredients to be accessible from recipes, but not the other way round.

Implementing whether a Recipe is vegetarian or not could be done through a property (column in the Recipe table) on Recipe, a calculation, or both.
I chose the last option as it was the easiest to implement using 
the [JDK 8 Stream API](https://docs.oracle.com/javase%2F8%2Fdocs%2Fapi%2F%2F/java/util/stream/Stream.html). 
The calculation is done through checking whether all the ingredients are vegetarian or not.

The text search is quite basic and also done through a filter using the JDK 8 Stream API. Although I have read online about optimising String indices, I had no time to apply this as I had no prior experience.

I originally planned to use the [Criteria API](https://docs.oracle.com/cd/E19226-01/820-7627/gjitv/index.html) 
for the filtering of recipes, but this seemed to complex for the time given. Therefore, I used the [JDK 8 Stream API](https://docs.oracle.com/javase%2F8%2Fdocs%2Fapi%2F%2F/java/util/stream/Stream.html) for this as well.

#### Ingredient
Ingredient was optional but sensible to have as a separate model. I chose to give the Ingredient the property of `isVegetarian` so that it can be used to
calculate whether a recipe is vegetarian or not.


