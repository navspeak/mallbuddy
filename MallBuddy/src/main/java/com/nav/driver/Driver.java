package com.nav.driver;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.nav.csv.util.BillSelectionUtil;
import com.nav.csv.util.CSVUtil;
import com.nav.exceptions.InvalidEntryInCSVFile;
import com.nav.service.BillingService;
import com.nav.service.InventoryService;
import com.nav.strategy.BillStyle;

public class Driver {

	final static Logger logger = Logger.getLogger(Driver.class);
	private static void printUsageAndExit(String err, int s) {
		if (err != null)
			System.err.println( err );
		System.out.println( "Usage: pricebuddy inventory=<inventory.csv> customerinput=<customerInput.csv> [brand=<brands.csv>] [category=<category.csv>]" );
		System.exit(s);
	}
	public static void main(String[] args) {
		if (args.length < 2)
			printUsageAndExit("Invalid Usage!\n", 1);	
		printResults(args);

	}


	private static void printResults(String[] args) {
		String csvInventory = null;
		String csvInput = null;
		String csvBrand = null;
		String csvCategory = null;
		for(int i=0; i < args.length; i++){
			String optionName = args[i].split("=")[0];
			String optionValue = args[i].split("=")[1];
			if ("inventory".equals(optionName))
				csvInventory = CSVUtil.checkPath(optionValue);
			else if ("customerinput".equals(optionName))
				csvInput = CSVUtil.checkPath(optionValue);
			else if ("brand".equals(optionName))
				csvBrand = CSVUtil.checkPath(optionValue);
			else if ("category".equals(optionName))
				csvCategory = CSVUtil.checkPath(optionValue);
		}
		if (csvInventory == null || csvInput == null) {
			printUsageAndExit("Invalid Usage!", 1);	
		}

		logger.debug("Inventory Input: " + csvInventory);
		logger.debug("Brand Input: " + csvBrand);
		logger.debug("Category Input: " + csvCategory);
		InventoryService inventorySvc = new InventoryService();
		try {
			inventorySvc.loadInventory(csvInventory, csvBrand, csvCategory);
			logger.debug("============BRANDS LOADED=================");
			inventorySvc.getBrandService().listAll().forEach(item -> logger.debug(item));
			logger.debug("============CATEGORIES LOADED=================");
			inventorySvc.getCategoryService().listAll().forEach(item -> logger.debug(item));	
			logger.debug("============ITEMS LOADED=================");
			inventorySvc.getItemService().listAll().forEach(item -> logger.debug(item));
			logger.debug("==========================================\n");
		} catch (InvalidEntryInCSVFile e1) {
			printUsageAndExit("Invalid CSV File!", 1);	
		}
		StringBuilder sb = new StringBuilder();
	
		BillingService billSvc = new BillingService(inventorySvc);
		sb.append("\n").append(billSvc.printBill(BillStyle.DISCOUNTDETAILS)).append("\n\n");
		Map<String, Double> map = null;
		try {
			map = BillSelectionUtil.getBillForSelection(billSvc, csvInput);
			logger.debug("User Input");
			map.keySet().stream().forEach(item -> logger.debug(item));
		} catch (IOException e) {
			printUsageAndExit("Invalid CSV Input!", 1);	
		}
		for (Entry<String, Double> entry : map.entrySet() ){
			sb.append("For your choice ").append(entry.getKey())
			.append(" the total cost is Rs. ").append(entry.getValue()).append("\n");
		}
		System.out.println(sb.toString());
		logger.debug(sb.toString());
		sb = null;
	}

	public static void invalidUsageExit() {
		printUsageAndExit("Invalid Usage!", 1);		
	}


}
