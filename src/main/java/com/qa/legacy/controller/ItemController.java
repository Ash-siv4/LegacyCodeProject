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

	public ItemController(ItemDAO itemDAO, Utils utils) {
		super();
		this.itemDAO = itemDAO;
		this.utils = utils;
	}

	@Override
	public List<Item> readAll() {
		List<Item> items = itemDAO.readAll();
		for (Item item : items) {
			LOGGER.info(item.toString());
		}
		return items;
	}

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
