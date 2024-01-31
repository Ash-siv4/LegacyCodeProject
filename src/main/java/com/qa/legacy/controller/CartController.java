package com.qa.legacy.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.legacy.utils.Utils;
import com.qa.legacy.persistence.dao.CartItemDAO;
import com.qa.legacy.persistence.dao.ItemDAO;
import com.qa.legacy.persistence.domain.CartItem;
import com.qa.legacy.persistence.domain.Item;

public class CartController implements CrudController<CartItem> {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private ItemDAO itemDAO;
	private CartItemDAO cartItemDAO;
	private Utils utils;
	

	public CartController(ItemDAO itemDAO, CartItemDAO cartItemDAO, Utils utils) {
		super();
		this.itemDAO = itemDAO;
		this.cartItemDAO = cartItemDAO;
		this.utils = utils;
	}

	@Override
	public List<CartItem> readAll() {
		List<CartItem> cart = cartItemDAO.readAll();
		for (CartItem cartItem : cart) {
			LOGGER.info(cartItem.toString());
		}
		return cart;
	}

	@Override
	public CartItem create() {
		List<Item> items = itemDAO.readAll();
		for (Item item : items) {
			LOGGER.info(item.toString());
		}
		LOGGER.info("Please enter the Item ID you wish to add to the cart");
		long itemID = utils.getLong();
		return cartItemDAO.create(new CartItem(itemDAO.readItem(itemID)));
	}

	@Override
	public CartItem update() {
		Long id = null;
		CartItem current = null;
		do {
			List<CartItem> cart = cartItemDAO.readAll();
			for (CartItem cartItem : cart) {
				LOGGER.info(cartItem.toString());
			}
			LOGGER.info("Please select the Cart item you wish to update");
			id = utils.getLong();
			current = cartItemDAO.readCartItem(id);
		} while (current == null);
		LOGGER.info(current.toString());
		LOGGER.info("Please enter the ID of the Item you wish to update to:");
		List<Item> items = itemDAO.readAll();
		for (Item item : items) {
			LOGGER.info(item.toString());
		}
		id = utils.getLong();
		current.setItem(itemDAO.readItem(id));
		return cartItemDAO.update(current);
	}

	@Override
	public int delete() {
		List<CartItem> cart = cartItemDAO.readAll();
		for (CartItem cartItem : cart) {
			LOGGER.info(cartItem.toString());
		}
		LOGGER.info("Please enter the id of the Cart Item you would like to delete");
		Long id = utils.getLong();
		return cartItemDAO.delete(id);
	}

}
