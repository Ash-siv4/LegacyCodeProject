package com.qa.legacy.persistence.domain;

import java.util.Objects;

public class CartItem {
	
	private Long id;
	private Item item;
	
	public CartItem(Long id, Item item) {
		this.id = id;
		this.item = item;
	}
	
	public CartItem(Item item) {
		this.id = 1L;
		this.item = item;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "CartItem [id=" + id + ", item=" + item + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		return Objects.equals(id, other.id) && Objects.equals(item, other.item);
	}
	
}
