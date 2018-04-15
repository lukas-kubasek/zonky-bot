Zonky Bot
=========

Simple implementation of a Zonky Bot capable of checking the Marketplace for new loans.

The Bot initialises by loading the **N** most recently published loans from the Markerplace
and starts listening for any newly published loans in the given interval **T** (parameters **N**
and **T** are configurable).

The newly published loans are printed to the log and added to the in-memory Loan Repository from
which can be accessed via the provided REST API or via a simple web page rendering the latest loans
in tabular form.

How to Build
------------

This is a standard Maven project and can be built (incl. running the tests) using:

```
git clone https://github.com/lukas-kubasek/zonky-bot.git
cd zonky-bot
mvn clean install
```

How to Run
----------

This is a Spring Boot app that can be run using:

```
 mvn spring-boot:run
```
or
```
java -jar target/zonky-bot-1.0-SNAPSHOT.jar 
```
 
Exposed endpoints
-----------------

Exposed Web UI rendering the latest loans:
* http://localhost:8080

Exposed REST API end-point:
* http://localhost:8080/loan/latest
