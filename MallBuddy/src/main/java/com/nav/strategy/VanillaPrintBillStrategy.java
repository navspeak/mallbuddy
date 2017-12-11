package com.nav.strategy;

import com.nav.domain.DomainObject;
import com.nav.domain.Item;
import com.nav.services.BillingService;


public class VanillaPrintBillStrategy implements IPrintBillStrategy {





	public String printInventory(BillingService billingSvc) {
		return print(billingSvc);
	}

	private String print(BillingService billingService) {
		StringBuilder sb = new StringBuilder();
		String header = String.format("%-4s", "id")+ 
				String.format("%-10s", "|Brand") +
				String.format("%-10s", "|Category")+
				String.format("%-10s", "|Price") +
		sb.append("=======================================================").append("\n");
		sb.append(header).append("\n");
		sb.append("=======================================================").append("\n");

		for(DomainObject obj : billingService
				.getInventoryService()
				.getItemService().listAll()) {
			Item item = (Item) obj;
			String row  = String.format("%-4s", item.getId())	+
					String.format("%-10s", "|"+item.getName())+
					String.format("%-10s", "|"+item.getCategory().getName()) +
					String.format("%-10s", "|"+item.getPrice());

			sb.append(row).append("\n");
		}

		String ret = sb.toString();
		sb = null;
		return ret;
	}
}
