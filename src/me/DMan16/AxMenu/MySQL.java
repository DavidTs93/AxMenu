package me.DMan16.AxMenu;

import me.Aldreda.AxUtils.AxUtils;
import me.Aldreda.AxUtils.Utils.Utils;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.UUID;

class MySQL {
	
	MySQL() throws SQLException {
		createTable();
	}
	
	private void createTable() throws SQLException {
		Statement statement = AxUtils.getConnection().createStatement();
		DatabaseMetaData data = AxUtils.getConnection().getMetaData();
		statement.execute("CREATE TABLE IF NOT EXISTS Wardrobe (UUID VARCHAR(36) NOT NULL UNIQUE);");
		if (!data.getColumns(null,null,"Wardrobe","UUID").next())
			statement.execute("ALTER TABLE Economy ADD UUID VARCHAR(36) NOT NULL UNIQUE;");
		if (!data.getColumns(null,null,"Wardrobe","Belt").next())
			statement.execute("ALTER TABLE Economy ADD Belt TEXT;");
		if (!data.getColumns(null,null,"Wardrobe","Necklace").next())
			statement.execute("ALTER TABLE Economy ADD Necklace TEXT;");
		if (!data.getColumns(null,null,"Wardrobe","Ring1").next())
			statement.execute("ALTER TABLE Economy ADD Ring1 TEXT;");
		if (!data.getColumns(null,null,"Wardrobe","Ring2").next())
			statement.execute("ALTER TABLE Economy ADD Ring2 TEXT;");
		statement.close();
	}
	
	private ItemStack getItem(UUID ID, String name) throws SQLException {
		if (ID == null) throw new SQLException("ID can't be null");
		PreparedStatement statement = AxUtils.getConnection().prepareStatement("SELECT * FROM Wardrobe WHERE UUID=?;");
		statement.setString(1,ID.toString());
		ResultSet result = statement.executeQuery();
		if (!result.next()) throw new SQLException();
		String str = result.getString(name);
		result.close();
		statement.close();
		try {
			return (ItemStack) Utils.ObjectFromBase64(str);
		} catch (Exception e) {}
		return null;
	}
	
	ItemStack getWardrobeBelt(UUID ID) throws SQLException {
		return getItem(ID,"Belt");
	}
	
	ItemStack getWardrobeNecklace(UUID ID) throws SQLException {
		return getItem(ID,"Necklace");
	}
	
	ItemStack getWardrobeRing1(UUID ID) throws SQLException {
		return getItem(ID,"Ring1");
	}
	
	ItemStack getWardrobeRing2(UUID ID) throws SQLException {
		return getItem(ID,"Ring2");
	}
}