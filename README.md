## Introduction
This is a sample project to resolve the sales tax exercise.

### Class diagram
This is a high level overview of the project

![alt text](https://github.com/marco-caligiuri/sales-project/blob/main/class-diagram.png?raw=true)

`ReceiptService` - is the main class to parse a string into a basket or to format as a string a basket

`Basket` - contains the sales taxes that must be applied and the relation between items and products

`BasketItem` - item is identified by a Product, taxes and quantity

`Product` - product is identified by name, price, type and if it is an imported goods

`ProductType` - list all the product types

`SalesTax` - a generic sales tax

`BasicTax` - a basic sales tax

`ImportDutyTax` - an import duty sales tax

### Testing
Tests were done with `maven`.  
`mvn clean verify` - run all tests  

The sample data are hard coded in the JUnit test case `/src/test/java/test/com/mcaligiuri/shopping/TestSolution.java`
