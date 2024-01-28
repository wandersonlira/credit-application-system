# :construction: Credit Application System
___
## About 
Credit Application System is a Rest API with Spring Boot built in Kotlin in which this microservice that evaluates credits for customers.
## :recycle: Features
### Client (Customer):
1. [ ]  _**Register:**_
- Request: _firstName, lastName, cpf, income, email, password, zipCode e street_
- Response: _String_

2. [ ]  _**Edit registration:**_
- Request: _id, firstName, lastName, income, zipCode, street_
- Response: _firstName, lastName, income, cpf, email, income, zipCode, street_ 
3. [ ]  _**View profile:**_
- Request: _id_
- Response: _firstName, lastName, income, cpf, email, income, zipCode, street_


### Loan Request (Credit):
1. [ ]  _**Register:**_
- Request: _creditValue, dayFirstOfInstallment, numberOfInstallments e customerId_
- Response: _String_

2. [ ]  _**List all loan requests from a customer:**_
- Request: _customerId_
- Response: _creditCode, creditValue, numberOfInstallment_

3. [ ]  _**View a loan:**_
- Request: _customerId e creditCode_
- Response: _creditCode, creditValue, numberOfInstallment, status, emailCustomer e incomeCustomer_
### :bulb: Observation
* _In case of change, it will be immediately documented in this README as the system evolves_

  
  
