package com.nav.strategy;

import com.nav.service.BillingService;

public interface IPrintBillStrategy {
	
	String printInventory(BillingService billingSvc);

}
