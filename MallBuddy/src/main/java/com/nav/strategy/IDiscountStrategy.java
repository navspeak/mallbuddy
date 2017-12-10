package com.nav.strategy;

import com.nav.service.BillingService;

public interface IDiscountStrategy {
	public Double computeDiscount(BillingService billingSvc, Integer choiceId);
}
