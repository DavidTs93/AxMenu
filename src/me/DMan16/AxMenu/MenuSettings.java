package me.DMan16.AxMenu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;

public class MenuSettings extends MenuMenu {

	MenuSettings(Player player) {
		super(player,Component.translatable("menu.aldreda.settings").decoration(TextDecoration.ITALIC,false));
	}
	
	@Override
	protected void setPageContents(int page) {
	}
	
	@Override
	protected void otherSlot(InventoryClickEvent event, int slot, ItemStack slotItem) {
		if (slot >= inventory.getSize() - 9) return;
		
	}
}