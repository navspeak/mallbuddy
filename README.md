# mallbuddy app
>This app is your buddy and helps you calculate the bill for customer item choices. 
>It applies all the discount without you having to do the number crunching 
>And it prints a handy bill!!

Download the PricebuddyApp folder.
It contains utilities named pricebuddy.bat (for Windows) and pricebuddy.sh (for Unix)
Ensure that your java environment is set properly before running this app
The folder contains:
1. inventory.csv = which contains list of items available in the inventory as comma separated values
2. customerInput.csv = contains customer provided input choices as comma separated values
3. brands.csv = contains brands to be loaded for the inventory with discounts on them
4. category.csv = contains categories to be loaded for the inventory with discounts on each category
```
Usage:
Usage: pricebuddy inventory=<inventory.csv> customerinput=<customerInput.csv> 
       [brand=<brands.csv>] [category=<category.csv>]
NOTE: brand and category are optional. If you do not provide these csv files 
       built-in defaults will be used for brands and categories
```

# CSV File format

**inventory.csv**
```
<Number Of Items>
<Numeric Id 1>, <Brand like Arrow, Vero Moda etc>, <Items like Shirts, footwear etc>, <price> 
<Numeric Id 1>, <Brand like Arrow, Vero Moda etc>, <Items like Shirts, footwear etc>, <price> 
...
```

**customerInput.csv**
```
<Number Of Inputs>
<choice 1>, <choice 2>, <choice 3>, ...,<choice n>
<choice 1>, <choice 2>, <choice 3>, ...,<choice n>
...
```

**brand.csv**
_The app has a built in brand with default discount. If you don't provide this, the default will apply_
```
<Brand like Arrow, Vero Moda etc>, <discount>
<Brand like Arrow, Vero Moda etc>, <discount>
...
```
**category.csv**
_The app has a built in Category with parent and children set up with default discount on each Category. If you don't provide this, the default will apply_

**_NOTE: All categories for this App derive from Category named APPAREL. You may over-write by providing a different Category relationship using this csv_**
```
<Category Name e.g. MENSWEAR>,<Parent Name (leave empty if no parent) e.g. APPAREL>,<Children brands delimited by a colon. Empty for no childred (e.g. Casuals:Shirts):...>,<Discount>
<Category Name e.g. WOMENSWEAR>,<Parent Name (leave empty if no parent) e.g. APPAREL>,<Children brands delimited by a colon. Empty for no childred (e.g. Dresses:Footwear):...>,<Discount>
...
```

## Sample run:
```
C:\PricebuddyApp>pricebuddy.bat inventory=inventory.csv customerinput=customerInput.csv

==========================================================================
id  |Brand    |Category |Price    |Discount |Discounted Price
==========================================================================
1   |ARROW    |SHIRTS   |800.0    |20.0     |640.0
2   |VERO_MODA|DRESSES  |1400.0   |60.0     |560.0
3   |PROVOGUE |FOOTWEAR |1800.0   |50.0     |900.0
4   |WRANGLER |JEANS    |2200.0   |20.0     |1760.0
5   |UCB      |SHIRTS   |1500.0   |0.0      |1500.0


For your choice 1,2,3,4 the total cost is Rs. 3860.0
For your choice 1,5 the total cost is Rs. 2140.0
```
## Logging:
The logging is controlled by a log4j.properties file in pricebuddyApp folder. By default console logging is disabled. All logs go to mallbuddy.log folder in the directory where the app is run from.

# Project Set up

- The layout is a standard Maven project
- The **MallBuddy** is a maven project
 - When you do `maven build`, you can find the project built as a jar named **navsmall-_version_.jar** (Navs' mall)
 - The main driver class is com.nav.driver.Driver
 - The project has tests that run on maven build. The test results can be found in **Mallbuddy/target/surefire-reports**

# Design and Architecture:

## Domain Objects
`(Relevant package : com.nav.domain)`

1. Brand - has a `name` and a `discount` associated with it (can be 0)
2. Category - has a `name` and a `discount` associated with it (can be 0)
3. item - has an integer `id`. It may have a _parent_ `Category`. It may also have one or more or even no _child_ `Category(s)`  

The Domain Objects implement `DomainObject` Interface.
The Domain Objects have an identifier that can be either name and/or an integer Id

### Item Builder
The Items build the inventory. Apart from using constructor and setters of Item class, there is an ItemBuilder class provided.
This provides a fluent interface to build the Item object

## Service and Repository
`(Relevant package : com.nav.repository and com.nav.services)`

The repository supports all CRUD operations viz. following. These operations are encapsulated in `ICRUDService` interface
- Add a new Domain Object to the repository 
- List All Domain Objects from the repository
- update an existing Domain Object in the repository
- Remove a Domain Object from the repository

The common repository service logic is present in:
1. AbstractNumericKeyMapService - for domain objects with integer key (like Item)
2. AbstractStringKeyMapService - for domain objects with String key (like Brand and Category)

These are further sub-classed into concrete implementations viz.:
1. BrandService
2. ItemService

Additionally, there are three more Service classes viz.:
1. InventoryService - This is the service object that contains all other services. The client would use this to get to any other service.
2. BillingService
 - This service class is the cornerstone of the application. It has an `InventoryService` injected at construction time.
 - It has two `Strategy` objects for:
  - generating bill/invoice in desired style, get discounted price and similar operations
  - Computing discount based on bespoke strategies. (In this application we used the Highest discount honored strategy. One can add different strategies)
```
 The service class for each Domain repository has a method to load/init the repository
 In this application, the repository is populated by reading CSV files. The repository source is a HashMap.
```

## Utils
`(Relevant package : com.nav.csv.util)`
1. BrandUtil - has methods to read a csv file and help in populating brands
2. CategoryUtil - has methods to read a csv file and help in populating categories
3. InventoryUtil - has methods to read a csv file and help in populating brands
4. BillingSelectionUtil - has methods to read a customer input csv file and return bill details for the asked choice.

## Strategy
`(Relevant package : com.nav.strategy)`
Contains Strategy classes for Billing Style (represented as `BillingStyle` Enum)

## BootStrap and Driver class
1. com.nav.csv.bootstrap.LoadDomain - creates the inventory
2. com.nav.driver.Driver - has the main entry to the application

# Assumptions:
1. This application runs as a standalone app
2. Each category can be a sub category of only one parent category
3. The input source is csv file in format as described. (However, the app can be extended in future to use any other source)
