# Billing Contract Service

## Health
[![codecov](https://codecov.io/gh/wesjordan/billingcontract/branch/master/graph/badge.svg)](https://codecov.io/gh/wesjordan/billingcontract) [![Build Status](https://travis-ci.org/wesjordan/billingcontract.svg?branch=master)](https://travis-ci.org/wesjordan/billingcontract)

## Developer Setup
Export the following environment variables:
```
BILLING_CONTRACT_DB=mysql://USERNAME:PWD@HOST:PORT/billing_contract
```

Download and run the following Docker images:

Image   |   Service    | Recommended 
------- | ------------ |-------------
MySQL | DB | [verified](https://hub.docker.com/_/mysql/) 
Kafka | Distributed streaming platform | example here


## Execution
```
mvn clean install
java -jar target/billingcontract-VERSION.jar
```
To confirm that the app is running, head over to http://localhost:8080/swagger-ui.html 