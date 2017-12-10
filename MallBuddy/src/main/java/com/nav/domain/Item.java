package com.nav.domain;

public class Item implements DomainObject{
	private int id;
	private String name="";
	private Category category;
	private Brand brand;
	private Double price;
	
	public Item() {
	}

	public Item(int id, String name, Category category, Brand brand, Double price) {
		this.id = id;
		this.name = "ITEM"+id;
		this.category = category;
		this.brand = brand;
		this.price = price;
	}
	
	public Item(ItemBuilder builder) {
		this.setId(builder.id);
		this.setBrand(builder.brand);
		this.setCategory(builder.category);
		//this.strategy = builder.strategy;
		this.setPrice(builder.price);
	}
	
	public static ItemBuilder startBuilding() {
		return new ItemBuilder();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id =id;
	};

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", category=" + category.getName() + ", brand=" + brand.getName() + ", price=" + price + "]";
	}

}
