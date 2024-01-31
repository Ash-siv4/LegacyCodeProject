package com.qa.legacy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.legacy.controller.Action;
import com.qa.legacy.controller.CartController;
import com.qa.legacy.controller.CrudController;
import com.qa.legacy.controller.ItemController;
import com.qa.legacy.utils.DBUtils;
import com.qa.legacy.utils.Utils;
import com.qa.legacy.persistence.dao.CartItemDAO;
import com.qa.legacy.persistence.dao.ItemDAO;
import com.qa.legacy.persistence.domain.Domain;

public class LegacyMenu {
	public static final Logger LOGGER = LogManager.getLogger();

	private final CartController cart;
	private final ItemController items;
	private final Utils utils;

	public LegacyMenu() {
		this.utils = new Utils();
		final CartItemDAO cartDAO = new CartItemDAO();
		final ItemDAO itemDAO = new ItemDAO();
		this.cart = new CartController(itemDAO, cartDAO, utils);
		this.items = new ItemController(itemDAO, utils);
	}
	
	public LegacyMenu(Utils utils, CartController cart, ItemController items) {
		this.utils = utils;
		this.cart = cart;
		this.items = items;
	}

	public void menuStart() {
		LOGGER.info("What is your username");
		String username = utils.getString();
		LOGGER.info("What is your password");
		String password = utils.getString();

		DBUtils.connect(username, password);
		Domain domain = null;
		do {
			LOGGER.info("Which entity would you like to use?");
			Domain.printDomains();

			domain = Domain.getDomain(utils);
			boolean changeDomain = false;
			do {

				CrudController<?> active = null;
				switch (domain) {
				case CART:
					active = this.cart;
					break;
				case ITEM:
					active = this.items;
					break;
				case STOP:
					return;
				default:
					break;
				}

				LOGGER.info("What would you like to do with " + domain.name().toLowerCase() + ":");

				Action.printActions();
				Action action = Action.getAction(utils);

				if (action == Action.RETURN) {
					changeDomain = true;
				} else {
					doAction(active, action);
				}
			} while (!changeDomain);
		} while (domain != Domain.STOP);
	}

	public void doAction(CrudController<?> crudController, Action action) {
		switch (action) {
		case CREATE:
			crudController.create();
			break;
		case READ:
			crudController.readAll();
			break;
		case UPDATE:
			crudController.update();
			break;
		case DELETE:
			crudController.delete();
			break;
		case RETURN:
			break;
		default:
			break;
		}
	}
}
