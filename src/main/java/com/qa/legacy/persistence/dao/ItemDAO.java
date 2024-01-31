package com.qa.legacy.persistence.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.legacy.persistence.domain.Item;
import com.qa.legacy.utils.DBUtils;

public class ItemDAO implements Dao<Item> {

	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Item modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		String itemName = resultSet.getString("item_name");
		Double price = resultSet.getDouble("item_price");
		Long quantity = resultSet.getLong("item_quantity");
		return new Item(id, itemName, price, quantity);
	}

	@Override
	public List<Item> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM items");) {
			List<Item> items = new ArrayList<>();
			while (resultSet.next()) {
				items.add(modelFromResultSet(resultSet));
			}
			return items;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public Item readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM items ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Item create(Item item) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("INSERT INTO items(item_name, item_price, item_quantity) values('" + item.getName()
					+ "'," + item.getPrice() + "," + item.getQuantity() + ")");
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public void itemIterator(Long id) {
		Item item = readItem(id);
		item.setQuantity(item.getQuantity() - 1);
		update(item);
	}

	public Item readItem(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM items where id = " + id);) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
			LOGGER.info("Item does not exist!");
		}
		return null;
	}

	@Override
	public Item update(Item item) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("update items set item_name ='" + item.getName() + "', item_price ="
					+ item.getPrice() + ", item_quantity =" + item.getQuantity() + " where id =" + item.getId() + ";");
			return readItem(item.getId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			return statement.executeUpdate("delete from items where id = " + id);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

}
