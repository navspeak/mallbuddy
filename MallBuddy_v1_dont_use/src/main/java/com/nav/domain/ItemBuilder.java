package com.nav.domain;

public class ItemBuilder {
		int id;
		Brand brand;
		Category category;
		Double price;
		
		public ItemBuilder id(int id) { this.id = id; return this; }
		public ItemBuilder brand(Brand brand) { this.brand = brand; return this; };
		public ItemBuilder category(Category category) { this.category = category; return this; };
		public ItemBuilder price(Double price) { this.price = price; return this; };
		public Item build() {
			return new Item(this);
		}
}
