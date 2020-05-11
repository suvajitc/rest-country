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

To call local springboot endpoints:
http://localhost:8080/v1/countries/name?search=USA&fullText=false
http://localhost:8080/v1/countries/code?search=IN

To Run the JUnit Tests from Command Line

`mvn clean test`

Please Note: Due to a bug with junit surefire plugin (https://github.com/junit-team/junit4/issues/664), the Parameterized test does not get run when you run mvn test from command line. But you can run the parameterized test from any IDE.

Created By: Suvajit Chakraborty