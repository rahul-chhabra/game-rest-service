# Game Server
_Author: Rahul Chhabra_

### Purpose

This project is the game server. It exposes REST API's for clients to communicate with.

### Technologies
- Java 11.0.9
- Spring Boot 2.4.8
- Apache Maven 3.6.3

### How to Compile
From the project root directory, run the below command  
>`mvn clean install`

### How to start Server
From the project root directory, run the below command  
>`java -jar target/game-rest-service-1.0.0.jar`

### Future Improvements
1. Add more junit test cases to have a better code coverage and quality.  
2. Incorporate SonarQube code quality review comments.  
3. Include authentication for the REST API's.  
4. Maintain history of the player moves.  
5. TIMED_OUT player if there is no move in 15 minutes.  
