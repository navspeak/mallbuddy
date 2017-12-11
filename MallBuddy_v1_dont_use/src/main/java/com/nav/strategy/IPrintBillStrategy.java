package com.nav.strategy;

import com.nav.services.BillingService;

public interface IPrintBillStrategy {
	
	String printInventory(BillingService billingSvc);

}
