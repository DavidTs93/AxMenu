package me.DMan16.AxMenu;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import me.Aldreda.AxUtils.Utils.Utils;

public class AxMenu extends JavaPlugin {
	private static AxMenu instance = null;
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		new MenuMain();
		Utils.chatColorsLogPlugin("&fAxMenu &aloaded!");
	}
	
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		Utils.chatColorsLogPlugin("&fAxMenu &adisabed");
	}
	
	public static FileConfiguration config() {
		return instance.getConfig();
	}
	
	public static AxMenu getInstance() {
		return instance;
	}
}