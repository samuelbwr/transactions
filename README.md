# Concurrent Transactions

Simple project built with Java 8 and Spring Boot, that let you insert Transactions and retrieve Statistics about them

To run:
```
mvn clean install
java -jar target/transactions-0.0.1-SNAPSHOT.jar 
```

To interact you can use the following end-points:
```
POST: /transaction
{ "amount": {doubleValue}, "timestamp": {time in millis} }
Returns: 
- 201: If timestamp is in period of latest 60 seconds
- 204: Otherwise

GET: /statistics
Returns: 
{ 
    "sum": {doubleValue}, 
    "avg": {doubleValue}, 
    "min": {doubleValue}, 
    "max": {doubleValue}, 
    "count": {intValue} 
}
```

The end-point /statistics is going to return values in O(1) of the last 60 seconds of activity. 

Code developed with:
 - Java 8
 - Spring Boot 1.5.9
 - H2
 - Maven
 - JUnit