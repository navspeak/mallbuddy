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
