package com.qa.legacy.persistence.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.legacy.utils.Utils;

public enum Domain {

	ITEM("Individual Items"), CART("Add/Remove Items from the Cart"),
	STOP("To close the application");

	public static final Logger LOGGER = LogManager.getLogger();

	private String description;

	private Domain(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.name() + ": " + this.description;
	}

	public static void printDomains() {
		for (Domain domain : Domain.values()) {
			LOGGER.info(domain.getDescription());
		}
	}

	public static Domain getDomain(Utils utils) {
		Domain domain;
		while (true) {
			try {
				String selection = utils.getString();
				System.out.println(selection);
				domain = Domain.valueOf(selection.toUpperCase());
				break;
			} catch (IllegalArgumentException e) {
				LOGGER.error("Invalid selection please try again");
			}
		}
		return domain;
	}

}
