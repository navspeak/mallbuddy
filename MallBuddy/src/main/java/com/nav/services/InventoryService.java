package com.nav.services;

import com.nav.csv.bootstrap.LoadDomains;
import com.nav.exceptions.InvalidEntryInCSVFile;

public class InventoryService {
	
	private BrandService brandService;
	private CategoryService categoryService;
	private ItemService itemService;
	//private BillingService billingService;
	public BrandService getBrandService() {
		return brandService;
	}
	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}
	public CategoryService getCategoryService() {
		return categoryService;
	}
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public ItemService getItemService() {
		return itemService;
	}
	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	/*public BillingService getBillingService() {
		return billingService;
	}
	public void setBillingService(BillingService billingService) {
		this.billingService = billingService;
	}*/
	
	public void loadInventory(String csvInventory, String brandSource, String categorySource) throws InvalidEntryInCSVFile {
		brandService = new BrandService();
		LoadDomains.initBrands(brandService, brandSource);
		categoryService = new CategoryService();
		LoadDomains.initCategories(categoryService, categorySource);
		this.setBrandService(brandService);
		itemService = new ItemService();
		this.setCategoryService(categoryService);
		this.setItemService(itemService);
		LoadDomains.initInventory(this, csvInventory);
		
	}
	
	

}
