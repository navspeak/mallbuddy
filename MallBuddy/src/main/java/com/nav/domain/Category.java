package com.nav.domain;

import java.util.LinkedList;
import java.util.List;

public class Category implements DomainObject {
	private int id;
	private String name;
	private Category parent;
	private List<Category> children;
	private Double discount = 0.0;

	public Category(String name) {
		this.name = name;
		this.children = new LinkedList<>();
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChild(Category child) {
		this.children.add(child);
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

	@Override
	public String toString() {
		String parentStr = parent == null? "NULL" : parent.getName();
		String childrenStr = "";
		if (children.isEmpty() == false){
			for (Category child : children){
				childrenStr +=child.getName() + ",";
			}
		}
		childrenStr = "[" + childrenStr + "]";
		return "Category [name=" + name + ", parent=" + parentStr + ", children=" + childrenStr + ", discount="
		+ discount + "]";
	}	


}
