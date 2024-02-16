package com.qa.legacy.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.legacy.persistence.dao.ItemDAO;
import com.qa.legacy.persistence.domain.Item;
import com.qa.legacy.utils.Utils;

public class ItemController implements CrudController<Item> {

	public static final Logger LOGGER = LogManager.getLogger();

	private ItemDAO itemDAO;
	private Utils utils;

	// Class constructor
	public ItemController(ItemDAO itemDAO, Utils utils) {
		super();
		this.itemDAO = itemDAO;
		this.utils = utils;
	}

	/*
	 * The readAll() method retrieves a list of items from a data access object
	 * (itemDAO). It iterates through each item in the list, logging details about
	 * each item using a logger. The method then returns the list of items.
	 */
	@Override
	public List<Item> readAll() {
		List<Item> items = itemDAO.readAll();
		for (Item item : items) {
			LOGGER.info(item.toString());
		}
		return items;
	}

	/*
	 * This method create() prompts the user to input details for a new item (name,
	 * price, quantity). It logs each prompt and retrieves the input using utility
	 * methods. The entered values are used to create an Item object, which is then
	 * stored using a data access object (itemDAO). Finally, a log message confirms
	 * the successful creation of the item, and the created Item is returned.
	 */
	@Override
	public Item create() {
		LOGGER.info("Please enter an item name");
		String itemName = utils.getString();
		LOGGER.info("Please enter a price");
		Double price = utils.getDouble();
		LOGGER.info("Please enter a quantity of stock");
		Long quantity = utils.getLong();
		Item item = itemDAO.create(new Item(itemName, price, quantity));
		LOGGER.info("Item created");
		return item;
	}

	/*
	 * The `update()` method prompts the user to enter an item ID for updating. It
	 * retrieves the current item with the given ID, logs its details, and then
	 * prompts the user to enter new information (name, price, quantity). The
	 * entered details are used to create an updated `Item`, which is then stored by
	 * calling the `update` method on a data access object (`itemDAO`). The method
	 * logs a confirmation message and returns the updated `Item`.
	 */
	@Override
	public Item update() {
		Long id = null;
		Item current = null;
		do {
			LOGGER.info("Please enter the id of the item you would like to update");
			id = utils.getLong();
			current = itemDAO.readItem(id);
		} while (current == null);
		LOGGER.info(current.toString());
		LOGGER.info("Please enter an item name");
		String itemName = utils.getString();
		LOGGER.info("Please enter a price");
		Double price = utils.getDouble();
		LOGGER.info("Please enter a quantity of stock");
		Long quantity = utils.getLong();
		Item item = itemDAO.update(new Item(id, itemName, price, quantity));
		LOGGER.info("Item Updated");
		return item;
	}

	/*
	 * The delete() method displays a list of all items, prompts the user to enter
	 * the ID of the item they want to delete, and attempts to delete it using a
	 * data access object (itemDAO). The method returns a result code indicating the
	 * success or failure of the deletion operation.
	 */
	@Override
	public int delete() {
		for (Item item : readAll()) {
			LOGGER.info(item.toString());
		}
		LOGGER.info("Please enter the id of the item you would like to delete");
		Long id = utils.getLong();
		return itemDAO.delete(id);
	}

}
