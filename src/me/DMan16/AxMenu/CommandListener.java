package me.DMan16.AxMenu;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

public class CommandListener implements CommandExecutor {
	
	public CommandListener() {
		PluginCommand command = AxMenu.getInstance().getCommand("AxMenu");
		command.setExecutor(this);
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		AxMenu.getInstance().reloadConfig();
		return true;
	}
}