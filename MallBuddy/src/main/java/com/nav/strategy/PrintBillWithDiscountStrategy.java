package com.nav.strategy;

import java.util.List;
import java.util.Map;

import com.nav.domain.DomainObject;
import com.nav.domain.Item;
import com.nav.service.BillingService;


public class PrintBillWithDiscountStrategy implements IPrintBillStrategy {
	
	public String printInventory(BillingService billingSvc) {
		return print(billingSvc);
	}

	private String print(BillingService billingService) {
		StringBuilder sb = new StringBuilder();
		String header = String.format("%-4s", "id")+ 
				String.format("%-10s", "|Brand") +
				String.format("%-10s", "|Category")+
				String.format("%-10s", "|Price") +
				String.format("%-10s", "|Discount") +
				String.format("%-10s", "|Discounted Price");
		sb.append("==========================================================================").append("\n");
		sb.append(header).append("\n");
		sb.append("==========================================================================").append("\n");
        List<DomainObject> items =   billingService
				.getInventoryService()
				.getItemService().listAll();
		for(DomainObject obj : items) {
			Item item = (Item) obj;
			Double discount = billingService.getDiscount(item.getId());
			Double discountedPrice = billingService.getPrice(item.getId()) * (1 - discount/100);
			String row  = String.format("%-4s", item.getId())	+
					String.format("%-10s", "|"+item.getBrand().getName())+
					String.format("%-10s", "|"+item.getCategory().getName()) +
					String.format("%-10s", "|"+item.getPrice()) +
					String.format("%-10s", "|"+discount) +
					String.format("%-10s", "|"+discountedPrice);
			sb.append(row).append("\n");
		}

		String ret = sb.toString();
		sb = null;
		return ret;
	}

}
