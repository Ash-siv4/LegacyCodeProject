package com.qa.legacy.controller;

import java.math.RoundingMode;
import java.text.DecimalFormat;
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

	// Class constructor
	public CartController(ItemDAO itemDAO, CartItemDAO cartItemDAO, Utils utils) {
		super();
		this.itemDAO = itemDAO;
		this.cartItemDAO = cartItemDAO;
		this.utils = utils;
	}

	/*
	 * The readAll() method calculates and displays the total cost of items in a
	 * shopping cart, including a service charge. It uses a decimal formatter to
	 * round the total cost to two decimal places. The method retrieves a list of
	 * CartItem objects from a data access object (cartItemDAO), iterates through
	 * each item, calculates the adjusted cost, logs information about each item,
	 * and finally logs the total cost of the cart including the service charge. The
	 * list of CartItem objects is then returned.
	 */
	@Override
	public List<CartItem> readAll() {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		List<CartItem> cart = cartItemDAO.readAll();
		Double totalCost = 0.0;
		for (CartItem cartItem : cart) {
			totalCost += cartItem.getItem().getPrice() * 1.0725;
			LOGGER.info(cartItem.toString());
		}
		LOGGER.info("Total cost of cart inc. service charge: £" + df.format(totalCost));
		return cart;
	}

	/*
	 * The create() method allows the user to add an item to a shopping cart. It
	 * retrieves a list of all items from a data access object (itemDAO), logs
	 * information about each item, prompts the user to enter the ID of the item
	 * they want to add to the cart, retrieves the corresponding item from the data
	 * access object, sets its quantity to 1, and creates a new CartItem object with
	 * this item. The CartItem is then added to the shopping cart using a data
	 * access object (cartItemDAO), and the created CartItem is returned.
	 */
	@Override
	public CartItem create() {
		List<Item> items = itemDAO.readAll();
		for (Item item : items) {
			LOGGER.info(item.toString());
		}
		LOGGER.info("Please enter the Item ID you wish to add to the cart");
		long itemID = utils.getLong();
		Item item = itemDAO.readItem(itemID);
		item.setQuantity(1L);
		return cartItemDAO.create(new CartItem(item));
	}

	/*
	 * The update() function allows you to change an item in your shopping cart. It
	 * first asks you to pick the item you want to update, shows you the current
	 * items in your cart, and then asks for the ID of the item you want to switch
	 * to. After showing all available items, it updates the selected item in your
	 * cart and confirms the update.
	 */
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

	/*
	 * The delete() method lets you remove a specific item from your shopping cart.
	 * It displays the current items in the cart, prompts you to enter the ID of the
	 * item you want to delete, and then attempts to delete that item from the cart.
	 */
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
