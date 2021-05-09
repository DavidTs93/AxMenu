package me.DMan16.AxMenu;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.Aldreda.AxUtils.Utils.Utils;
import me.DMan16.AxMenu.MenuMain.MainMenu;

public class AxMenu extends JavaPlugin {
	private static AxMenu instance = null;
	private MenuMain MenuMain;
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		MenuMain = new MenuMain();
		Utils.chatColorsLogPlugin("&fAxMenu &aloaded!");
	}
	
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		for (MainMenu menu : MenuMain.menus) menu.clearInv();
		Utils.chatColorsLogPlugin("&fAxMenu &adisabed");
	}
	
	public static boolean isLobby() {
		return instance.getConfig().getString("server").equalsIgnoreCase("lobby");
	}
	
	public static AxMenu getInstance() {
		return instance;
	}
}