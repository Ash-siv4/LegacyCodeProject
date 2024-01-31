package com.qa.legacy.persistence.domain;

public class Item {

	private Long id;
	private String name;
	private Double price;
	private Long quantity;

	public Item(Long id, String name, Double price, Long quantity) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public Item(String name, Double price, Long quantity) {
		super();
		this.id = 1L;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public Item(String name, Double price) {
		super();
		this.id = 1L;
		this.name = name;
		this.price = price;
		this.quantity = 1L;
	}
	
	public Item(Long id) {
		super();
		this.id = id;
		this.name = null;
		this.price = null;
		this.quantity = 1L;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", price= �" + price + ", quantity=" + quantity + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}
}
