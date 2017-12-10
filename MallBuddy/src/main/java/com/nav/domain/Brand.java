package com.nav.domain;

public class Brand implements DomainObject{

	private int id;
	private String name;
	private Double discount;
	private String description;
	
	public Brand(String name, Double discount) {
		this.setName(name);
		this.setDiscount(discount);
		this.setDescription(name);
	}
	
	public Brand(String name, Double discount, String description) {
		this.setName(name);
		this.setDiscount(discount);
		this.setDescription(description);
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
		
	}

	@Override
	public String toString() {
		return "Brand [name=" + name + ", discount=" + discount + "]";
	}
	
}
