package com.nav.csv.bootstrap;

import com.nav.csv.util.BrandUtil;
import com.nav.csv.util.CSVUtil;
import com.nav.csv.util.CategoryUtil;
import com.nav.csv.util.InventoryUtil;
import com.nav.domain.Brand;
import com.nav.domain.Category;
import com.nav.exceptions.InvalidEntryInCSVFile;
import com.nav.service.BrandService;
import com.nav.service.CategoryService;
import com.nav.service.InventoryService;

public class LoadDomains {
	/**
	 * initBrands
	 * @param brandService
	 * @param brandSource
	 * @throws InvalidEntryInCSVFile
	 */
	public static void initBrands(BrandService brandService, String brandSource) throws InvalidEntryInCSVFile {
		if (brandService == null) return;
		String brandCSVSource = CSVUtil.checkPath(brandSource);
		if (brandCSVSource == null){ // Fill the defaults
			brandService.saveOrUpdate(new Brand("WRANGLER", 10.0));
			brandService.saveOrUpdate(new Brand("ARROW", 20.0));
			brandService.saveOrUpdate(new Brand("VERO_MODA", 60.0));
			brandService.saveOrUpdate(new Brand("UCB", 0.0));
			brandService.saveOrUpdate(new Brand("ADIDAS", 5.0));
			brandService.saveOrUpdate(new Brand("PROVOGUE", 20.0));
		} else {
			BrandUtil.buildBrandList(brandCSVSource, brandService);
		}
	}
    /**
     * initCategories
     * @param categoryService
     * @param categorySource
     * @throws InvalidEntryInCSVFile
     */
	public static void initCategories(CategoryService categoryService, String categorySource) throws InvalidEntryInCSVFile {
		String categoryCSVSource = CSVUtil.checkPath(categorySource);
		if (categoryCSVSource == null){ 
			Category apparel = new Category("APPAREL");
			Category menswear = new Category("MENSWEAR");
			Category womenswear = new Category("WOMENSWEAR");
			Category shirts = new Category("SHIRTS");
			Category trousers = new Category("TROUSERS");
			Category casuals = new Category("CASUALS");
			Category jeans = new Category("JEANS");
			Category dresses = new Category("DRESSES");
			Category footwear = new Category("FOOTWEAR");

			apparel.setChild(menswear);
			apparel.setChild(womenswear);

			menswear.setParent(apparel);
			menswear.setChild(shirts);
			menswear.setChild(trousers);
			menswear.setChild(casuals);
			menswear.setChild(jeans);

			womenswear.setParent(apparel);
			womenswear.setChild(dresses);
			womenswear.setChild(footwear);
			womenswear.setDiscount(50.0);

			shirts.setParent(menswear);
			trousers.setParent(menswear);
			casuals.setParent(menswear);
			casuals.setDiscount(30.0);
			jeans.setParent(menswear);
			jeans.setDiscount(20.0);

			dresses.setParent(womenswear);
			footwear.setParent(womenswear);

			categoryService.saveOrUpdate(apparel);
			categoryService.saveOrUpdate(menswear);
			categoryService.saveOrUpdate(womenswear);
			categoryService.saveOrUpdate(shirts);
			categoryService.saveOrUpdate(trousers);
			categoryService.saveOrUpdate(casuals);
			categoryService.saveOrUpdate(jeans);
			categoryService.saveOrUpdate(dresses);
			categoryService.saveOrUpdate(footwear);
		} else {
			CategoryUtil.buildCategoryList(categoryCSVSource, categoryService);
		}
	}
	/**
	 * initInventory
	 * @param inventoryService
	 * @param inventorySource
	 * @throws InvalidEntryInCSVFile
	 */
	public static void initInventory(InventoryService inventoryService, String inventorySource) throws InvalidEntryInCSVFile{
		InventoryUtil.buildInventory(inventoryService, inventorySource);
		
	}

}
