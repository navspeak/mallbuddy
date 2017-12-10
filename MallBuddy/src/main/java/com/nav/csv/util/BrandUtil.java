package com.nav.csv.util;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.nav.domain.Brand;
import com.nav.exceptions.InvalidEntryInCSVFile;
import com.nav.service.BrandService;

public class BrandUtil {
	private static Logger logger = Logger.getLogger(BrandUtil.class);
	/**
	 * Utility to create Brand List for the inventory
	 * @param csvFilePath - input csv file to populate brands
	 *        Entry Format - <BRAND_NAME>,<DISCOUNT>
	 *        Example - ARROW,25.0
	 * @param brands - List of brands to be created
	 * @throws InvalidEntryInCSVFile 
	 */
	public static void buildBrandList(String csvfile, BrandService brandService) throws InvalidEntryInCSVFile {
		List<String[]> brandElements = null;
		try {
			brandElements = CSVUtil.readFromCSV(csvfile);
			for(String[] elements : brandElements){
				if (elements.length != 2 ) {
					continue;
				}
				String str = elements[0].toUpperCase().trim().replaceAll(" ", "_");
				Double discount = Double.parseDouble(elements[1]);
				brandService.saveOrUpdate(new Brand(str, discount));
			}

		} catch (IOException e) {
			logger.error("Error reading inventory csv "  + e);
			throw new InvalidEntryInCSVFile("Error reading inventory csv " + e);
		} catch (NumberFormatException e){
			logger.error("Error reading id or discount from inventory csv " + e);
			throw new InvalidEntryInCSVFile("Error reading id or discount from inventory csv " + csvfile);
		}

	}
}
