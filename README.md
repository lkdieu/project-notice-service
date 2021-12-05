# project-notice-service
REST API to manage notification information

## Features

- CRUD notices: Implement notice registration, modification, deletion, and inquiry.
- CRUD notices:  Implement user, modification, deletion, and inquiry.
- Authentication with JWT: Generate JWT Access token and a random string for Refresh token the login.
- Logger: Log info/error message into a log file

## Technologies
- JWT
- Spring boot framework
- Hibernate
- Lombok
- Mockito

## Prerequisites
- JDK 8
- Maven
- SQLServer

## Installation

- Clone the repository: 
  *git clone  https://github.com/lkdieu/project-notice-service.git*
- Install dependencies: 
   *cd [project_name]*; 
   *mvn install*
- Create the configuration file and update with your local config: 
  *cd src/main/resources; 
  cp application-example.properties application.properties.*
- Start Application: 
  *mvn spring-boot:run.*
- Note: An IDE like IntelliJ can perform these tasks for you automatically
## How To Get Started
- You need to run project and create one user: API POST/user
- Use information user you just created to get token by api POST/authenticate
- Use that token to perform actions: get, delete, update notifications and users
