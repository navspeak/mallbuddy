package com.nav.csv.util;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.nav.domain.Brand;
import com.nav.domain.Category;
import com.nav.domain.Item;
import com.nav.exceptions.InvalidEntryInCSVFile;
import com.nav.service.InventoryService;


public class InventoryUtil
{
	private static Logger logger = Logger.getLogger(InventoryUtil.class); 
	public static void buildInventory(InventoryService invSvc, String inventorySource ) throws InvalidEntryInCSVFile {
		List<String[]> itemElements;
		//System.out.println("Building items from " + csvFileLocation + "...");
		try {
			itemElements = CSVUtil.readFromCSV(inventorySource);
			String[] firstLine = itemElements.get(0);
			// The first line contains the number of items in inventory
			if (firstLine.length == 1 &&
					(Integer.parseInt(firstLine[0]) != itemElements.size() - 1)){
				logger.warn("WARN!! The first line doesn't contain the number of items...");
				//This is not really fatal so we can continue
			}
			for(String[] elements : itemElements){
				if (elements.length != 4 ) {
					continue;
				}
				int id = Integer.parseInt(elements[0]);
				String brandName = elements[1].toUpperCase().trim().replaceAll(" ", "_");
				Brand brand = invSvc.getBrandService().getByName(brandName);
				String categoryName = elements[2].toUpperCase().trim().replaceAll(" ", "_");
				Category category = invSvc.getCategoryService().getByName(categoryName);
				Double price = Double.parseDouble(elements[3]);
				Item item = Item.startBuilding().id(id)
						.brand(brand)
						.category(category)
						.price(price).build();
				invSvc.getItemService().saveOrUpdate(item);
			}

		} catch (IOException e) {
			logger.error("Error reading inventory csv " + e);
			throw new InvalidEntryInCSVFile("Error reading inventory csv " + inventorySource);
		} catch (NumberFormatException e){
			logger.error("Error reading id or discount from inventory csv " + e);
			throw new InvalidEntryInCSVFile("Error reading id or discount from inventory csv " + inventorySource);
		}
	}

}
