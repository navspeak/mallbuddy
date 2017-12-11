package com.nav.strategy;

import com.nav.services.BillingService;

public interface IDiscountStrategy {
	public Double computeDiscount(BillingService billingSvc, Integer choiceId);
}
