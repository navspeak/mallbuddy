package com.nav.service;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nav.services.IBillingService;
import com.nav.strategy.BillStyle;
import com.nav.strategy.HighestDiscountStrategy;
import com.nav.strategy.IDiscountStrategy;
import com.nav.strategy.IPrintBillStrategy;
import com.nav.strategy.PrintBillWithDiscountStrategy;
import com.nav.strategy.VanillaPrintBillStrategy;

public class BillingService implements IBillingService{

	private InventoryService inv;
	private Map<BillStyle, IPrintBillStrategy> printStrategies;
	private IDiscountStrategy discountStrategy;
	public BillingService(InventoryService invSvc) {
		this.inv = invSvc;
		printStrategies = new LinkedHashMap<>(2);
		//default strategies
		printStrategies.put(BillStyle.VANILLA, new VanillaPrintBillStrategy());
		printStrategies.put(BillStyle.DISCOUNTDETAILS, new PrintBillWithDiscountStrategy());
		discountStrategy = new HighestDiscountStrategy(); //default - overwrite with the setter
	}

	public String printBill(BillStyle style) {
		return printStrategies.get(style).printInventory(this);
	}

	public Double getPrice(Integer choiceId) {
		return inv.getItemService().getById(choiceId).getPrice();
	}

	public Double getEffectivePrice(Integer choiceId) {
		Double price = getPrice(choiceId);
		return price - getDiscount(choiceId) * price / 100;
	}
	
	public Double getDiscount(Integer choiceId) {
		return discountStrategy.computeDiscount(this, choiceId);
	}

	public void addStrategy(BillStyle style, IPrintBillStrategy strategy) {
		printStrategies.put(style, strategy);
	}

	public InventoryService getInventoryService() {
		return inv;
	}

	public void setInventoryService(InventoryService inv) {
		this.inv = inv;
	}
	
	public IDiscountStrategy getStrategy() {
		return discountStrategy;
	}

	public void setStrategy(IDiscountStrategy strategy) {
		this.discountStrategy = strategy;
	}	
}
