package com.qa.legacy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Runner {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	public static void main(String[] args) {
		LegacyMenu menu = new LegacyMenu();
		menu.menuStart();
		LOGGER.info("SO LONG!");
	}
}
