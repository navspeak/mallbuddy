package com.nav.csv.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nav.domain.Item;
import com.nav.services.BillingService;


public class BillSelectionUtil {

	private static Logger logger = Logger.getLogger(BillSelectionUtil.class);
	/**
	 * Returns map of user choices as key and total cost as value
	 * e.g. "1,5"=2140
	 */
	public static Map<String, Double> getBillForSelection(final BillingService svc, String customerInputFile) throws IOException{
		List<String> custInput = readFromCSVRaw(customerInputFile);
		int count = 0;
		Map<String, Double> map = new HashMap<>(custInput.size());
		for (String choice : custInput ){
			if (count == 0) {
				count++;
				continue;
			}
			Double totalPrice =getEffectivePrice(svc, choice);
			map.put(choice, totalPrice);
		}
		return Collections.unmodifiableMap(map);
	}
	
	/**
	 * Returns final price for customer choices of item
	 */
	public static Double getEffectivePrice(final BillingService svc, String choiceIds) {
		Double totalPrice = 0.0;
		try {
			for (String choice : choiceIds.split(",")) {
				int choiceId = Integer.parseInt(choice);
				Item item = svc.getInventoryService().getItemService().getById(choiceId);
				if (item == null) {
					System.out.println("Your " + choice + " does not match any item number. Moving to next...");
				} else
					totalPrice = totalPrice + svc.getEffectivePrice(choiceId);
			} 
		} catch (Exception e) {
			//Any Exception here means that the choice was not properly provided
			//return null
			System.out.println("ERROR!!! Invalid Choice");
			logger.error("ERROR!!! Invalid Choice " + e);
			return null;
		}
		return totalPrice;
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private static List<String> readFromCSVRaw(String customerInputFile) throws IOException{
		Path filePath = Paths.get(customerInputFile);
		List<String> elements = new LinkedList<>();
		try (BufferedReader reader = Files.newBufferedReader(filePath)) {
			String line = "";
			while ((line = reader.readLine()) != null){
				elements.add(line);
			}
		}
		return elements;
	}
}
