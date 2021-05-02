package me.DMan16.AxMenu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.Aldreda.AxUtils.Utils.ListenerInventory;
import me.Aldreda.AxUtils.Utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public class MenuWardrobe extends ListenerInventory {
	public Player player;
	
	public MenuWardrobe(Inventory inv, Player player) {
		super(Utils.makeInventory(player,6,Component.translatable("container.inventory").decoration(TextDecoration.ITALIC,false)));
		this.player = player;
	}
}