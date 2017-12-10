# mallbuddy
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

