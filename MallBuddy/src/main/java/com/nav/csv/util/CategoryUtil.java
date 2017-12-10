package com.nav.csv.util;

import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;

import com.nav.domain.Category;
import com.nav.exceptions.InvalidEntryInCSVFile;
import com.nav.services.CategoryService;

public class CategoryUtil {

	private static Logger logger = Logger.getLogger(CategoryUtil.class); 
	public static void buildCategoryList(String csvfile, CategoryService categoryService) throws InvalidEntryInCSVFile {

		List<String[]> brandElements = null;
		try {
			brandElements = CSVUtil.readFromCSV(csvfile);
			for(String[] elements : brandElements){
				if (elements.length != 4 ) {
					continue;
				}
				String name = elements[0].toUpperCase().trim().replaceAll(" ", "_");
				String parentName = elements[1].toUpperCase().trim().replaceAll(" ", "_");
				String childrenNames = elements[2].toUpperCase().trim().replaceAll(" ", "_");
				if ("".equals(childrenNames) || "_".equals(childrenNames)) childrenNames = null;
				if ("".equals(parentName) || "_".equals(parentName)) parentName = null;
				Double discount = Double.parseDouble(elements[3]);
				Category category = categoryService.getByName(name);
				if (category == null) {
					category = new Category(name);
					category.setDiscount(discount);
					categoryService.saveOrUpdate(category);
					logger.debug("Added category "+ name);
				}else
					logger.debug("Category existed already "+ name);
				category.setDiscount(discount);
				Category parent = categoryService.getByName(parentName);
				if (parentName !=null && parent == null) {
					parent = new Category(parentName);
					categoryService.saveOrUpdate(parent);
					logger.debug("Parent added "+ name);
				} else
					logger.debug("Parent is null or already added "+ parentName);
				category.setParent(parent);
				if (childrenNames == null) continue;
				for (String childName : childrenNames.split(":")){
					Category child = categoryService.getByName(childName);
					if (childName!= null && child == null) {
						child = new Category(childName);
						categoryService.saveOrUpdate(child);
						logger.debug("child added "+ childName);
					}else
						logger.debug("child is null or already added "+ childName== null ? "NULL" : childName);
					category.setChild(child);
				}
			}

		} catch (IOException e) {
			logger.error("Error reading inventory csv " + e);
			throw new InvalidEntryInCSVFile("Error reading inventory csv " + csvfile);
		} catch (NumberFormatException e){
			logger.error("Error reading id or discount from inventory csv " + e);
			throw new InvalidEntryInCSVFile("Error reading id or discount from inventory csv " + csvfile);
		}

	}

}
