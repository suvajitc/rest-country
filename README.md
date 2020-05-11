###### REST COUNTRY PROJECT
This is a simple project consisting of a springboot application that accepts search requests for country by name or by code and in return calls the rest api end point at https://restcountries.eu/ and returns the data in appropiate format.

To Run the springboot app from command line:

For searching for country by name:
`./mvnw spring-boot:run -Dspring-boot.run.arguments=--mode=name,--search=USA,--fulText=true`

For searching for country by code:
`./mvnw spring-boot:run -Dspring-boot.run.arguments=--mode=code,--search=US`

To Shutdown the application:
Run a curl request or through postman to following endpoint:
localhost:8080/actuator/shutdown (both user and password: admin with basic authentication)

To Run the JUnit Tests from Command Line

`mvn clean test`

Created By: Suvajit Chakraborty