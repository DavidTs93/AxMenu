package me.DMan16.AxMenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import me.Aldreda.AxUtils.Utils.ListenerInventoryPages;
import me.Aldreda.AxUtils.Utils.Utils;
import net.kyori.adventure.text.Component;

abstract class MenuMenu extends ListenerInventoryPages {
	private static ItemStack border = Utils.makeItem(Material.GRAY_STAINED_GLASS_PANE,Component.empty(),ItemFlag.values());
	public Player player;
	
	MenuMenu(Player player, Component name) {
		super(player,player,5,name,AxMenu.getInstance());
		this.player = player;
	}
	
	@Override
	public int maxPage() {
		return 1;
	}
	
	@Override
	protected void reset() {
		for (int i = 0; i < inventory.getSize(); i++) inventory.setItem(i,i >= inventory.getSize() - 9 ? border : null);
	}
}