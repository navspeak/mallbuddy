package com.nav.services;
import com.nav.strategy.BillStyle;


public interface IBillingService {
	public String printBill(BillStyle style);
	public Double getEffectivePrice(Integer choiceId);
	public Double getPrice(Integer choiceId);
	public Double getDiscount(Integer choiceId);
}
