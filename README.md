# notes-app
## Introduction
Web application written as a project for a laboratory class in the subject "Data protection in information systems".

The application allows the logged-in user to store text notes. Some notes can be marked as encrypted. Decryption require a secret password.

The user is be able to register and log in with a password and a second verification component using the TOTP (time-based one-time password) algorithm.

The application allows selected notes (unencrypted) to be made available to other users by making them available as public notes

## Authentication module
### The authentication module provides
 - input validation (with negative bias),
 - delays and attempt limits (to make remote guessing and brute-force attacks more difficult),
 - limited error reporting (e.g. about the reason for authentication refusal),
 - secure password storage (BCryptPasswordEncoder)
 - password strength control, to make the user aware of the problem
 - management of resource permissions.

### Additional functionalities
 - secure connection to the application (encrypted HTTPS connection)
 - Protection against XSS attacks
   
## Technological Stack:
### Backend
- Java 21 LTS
- Spring Boot 3.2.0
- Hibernate
- Maven
- PostgreSQL
- Docker (Dockerized whole Application)
- IntelliJ IDEA Ultimate
### Frontend
- Thymeleaf
- HTML5/CSS
- JavaScript
- Bootstrap
- WebStorm
