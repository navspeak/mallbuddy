package com.nav.strategy;

import org.apache.log4j.Logger;

import com.nav.domain.Category;
import com.nav.domain.Item;
import com.nav.driver.Driver;
import com.nav.service.BillingService;

public class HighestDiscountStrategy implements IDiscountStrategy{

	final static Logger logger = Logger.getLogger(HighestDiscountStrategy.class);
	@Override
	public Double computeDiscount(BillingService billingSvc, Integer choiceId) {
		Item item = billingSvc.getInventoryService().getItemService().getById(choiceId);
		logger.debug("Calculating Discount for item " + item.getId());
		Double brandDiscount = item.getBrand().getDiscount();
		logger.debug("---Discount for Brand " + item.getBrand().getName() + " = " + brandDiscount);
		Category category = item.getCategory();
		Category ptr = category;
		Double categoryDiscount = ptr.getDiscount();
		logger.debug("---Discount for Category " + item.getCategory().getName() + " = " + categoryDiscount);
		while(ptr!=null && ptr.getParent() != null) {
			logger.debug("------Discount for parent Category " + ptr.getParent().getName() + " = " + ptr.getParent().getDiscount());
			categoryDiscount = Math.max(categoryDiscount, ptr.getParent().getDiscount());
			logger.debug("------Max Category discount "+ categoryDiscount);
			ptr = ptr.getParent();
		} 
		logger.debug("------Max Total discount "+ Math.max(brandDiscount, categoryDiscount));
		logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		return Math.max(brandDiscount, categoryDiscount);
	}

	

}
