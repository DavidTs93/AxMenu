package me.DMan16.AxMenu;

import com.destroystokyo.paper.event.player.PlayerRecipeBookClickEvent;
import me.Aldreda.AxUtils.Classes.Listener;
import me.Aldreda.AxUtils.Utils.ListenerInventory;
import me.Aldreda.AxUtils.Utils.Utils;
import me.DMan16.AxItems.Restrictions.Restrictions;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class MenuMain extends Listener {
	private static String translateStore = "menu.aldreda.store";
	private static String translateLobby = "menu.aldreda.lobby";
	
	List<MainMenu> menus;
	private ItemStack store;
	private ItemStack charMain;
	private ItemStack wardrobe;
	private ItemStack help;
	private ItemStack settings;
	private ItemStack lobby;
	
	public MenuMain() {
		store = Restrictions.DropRemove.add(Restrictions.Undroppable.add(Restrictions.Unequippable.add(Restrictions.Unplaceable.add(Utils.makeItem(Material.CHEST,
				Component.translatable(translateStore).decoration(TextDecoration.ITALIC,false),ItemFlag.values())))));
		charMain = Restrictions.DropRemove.add(Restrictions.Undroppable.add(Restrictions.Unequippable.add(Restrictions.Unplaceable.add(Utils.makeItem(Material.PLAYER_HEAD,
				Component.translatable("menu.aldreda.character").decoration(TextDecoration.ITALIC,false),ItemFlag.values())))));
		wardrobe = Restrictions.DropRemove.add(Restrictions.Undroppable.add(Restrictions.Unequippable.add(Restrictions.Unplaceable.add(Utils.makeItem(Material.LEATHER_CHESTPLATE,
				Component.translatable("menu.aldreda.wardrobe").decoration(TextDecoration.ITALIC,false),ItemFlag.values())))));
		help = Restrictions.DropRemove.add(Restrictions.Undroppable.add(Restrictions.Unequippable.add(Restrictions.Unplaceable.add(Utils.makeItem(Material.WRITTEN_BOOK,
				Component.translatable("menu.aldreda.help").decoration(TextDecoration.ITALIC,false),ItemFlag.values())))));
		settings = Restrictions.DropRemove.add(Restrictions.Undroppable.add(Restrictions.Unequippable.add(Restrictions.Unplaceable.add(Utils.makeItem(Material.COMPARATOR,
				Component.translatable("menu.aldreda.settings").decoration(TextDecoration.ITALIC,false),ItemFlag.values())))));
		lobby = Restrictions.DropRemove.add(Restrictions.Undroppable.add(Restrictions.Unequippable.add(Restrictions.Unplaceable.add(Utils.makeItem(Material.COMPASS,
				Component.translatable(translateLobby).decoration(TextDecoration.ITALIC,false),ItemFlag.values())))));
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(AxMenu.getInstance(),"axmenu_main_menu_recipe_wtf_why_mojang_stahp"),lobby.clone());
		recipe.shape("TW","HS");
		recipe.setIngredient('T', new RecipeChoice.MaterialChoice(Material.CHEST,Material.PLAYER_HEAD));
		recipe.setIngredient('W',wardrobe);
		recipe.setIngredient('H',help);
		recipe.setIngredient('S',settings);
		Bukkit.addRecipe(recipe);
		menus = new ArrayList<MainMenu>();
		register(AxMenu.getInstance());
	}
	
	@EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (Utils.isPlayerNPC(event.getPlayer())) return;
		GameMode old = event.getPlayer().getGameMode();
		event.getPlayer().setGameMode(GameMode.SURVIVAL);
		menus.add(new MainMenu(event.getPlayer().getOpenInventory().getTopInventory(),(Player) event.getPlayer()));
		event.getPlayer().setGameMode(old);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	public void onRecipeBookClick(PlayerRecipeBookClickEvent event) {
		if (!event.isCancelled() && event.getPlayer().getOpenInventory().getType() == InventoryType.CRAFTING) event.setCancelled(true);
	}
	
	/*@EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
	public void onWorkbenchOpen(InventoryOpenEvent event) {
		if (event.isCancelled() || event.getInventory().getType() != InventoryType.WORKBENCH ||
				!event.getView().title().equals(Component.translatable("container.crafting"))) return;
		event.setCancelled(true);
		new BukkitRunnable() {
			public void run() {
				event.getPlayer().openInventory(Utils.makeInventory((Player) event.getPlayer(),InventoryType.WORKBENCH,
						Component.translatable("block.minecraft.crafting_table").decoration(TextDecoration.ITALIC,false)));
			}
		}.runTask(AxMenu.getInstance());
	}*/
	
	class MainMenu extends ListenerInventory {
		private Player player;
		private BukkitTask task;
		private ItemStack character;
		
		public MainMenu(Inventory inv, Player player) {
			super(inv);
			this.player = player;
			character = charMain.clone();
			SkullMeta meta = (SkullMeta) character.getItemMeta();
			meta.setOwningPlayer(player);
			character.setItemMeta(meta);
			register(AxMenu.getInstance());
			task = new BukkitRunnable() {
				public void run() {
					setContent(false);
					inventory.setItem(0,lobby.clone());
				}
			}.runTaskTimer(AxMenu.getInstance(),5,5);
		}
		
		private void setContent(boolean force) {
			boolean fix = false;
			if (!force) for (int i = 0; i < 5; i++) if (Utils.isNull(inventory.getItem(i))) {
				fix = true;
				break;
			}
			if (!force && !fix) return;
			ItemStack item;
			if (AxMenu.isLobby()) item = store.clone();
			else item = character.clone();
			inventory.setItem(1,null);
			inventory.setItem(1,item);
			inventory.setItem(2,wardrobe.clone());
			inventory.setItem(3,help.clone());
			inventory.setItem(4,settings.clone());
		}
		
		@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
		public void onInventoryClick(InventoryClickEvent event) {
			if (event.isCancelled() || !event.getView().getTopInventory().equals(inventory) || event.getRawSlot() >= inventory.getSize() || event.getRawSlot() < 0) return;
			event.setCancelled(true);
			if (event.getClick().isCreativeAction()) return;
			if (event.getClick().isKeyboardClick()) new BukkitRunnable() {
				public void run() {
					setContent(true);
					((Player) event.getWhoClicked()).updateInventory();
				}
			}.runTask(AxMenu.getInstance());
			else new BukkitRunnable() {
				public void run() {
					if (event.getRawSlot() == 0) player.openInventory(Bukkit.createInventory(player,1 * 9,Component.translatable(translateLobby)));	// Lobby selector
					else if (event.getRawSlot() == 1) {
						if (AxMenu.isLobby()) player.openInventory(Bukkit.createInventory(player,1 * 9,Component.translatable(translateStore)));	// Store
						else new MenuCharacter(player);
					} else if (event.getRawSlot() == 2) new MenuWardrobe(player);
					else if (event.getRawSlot() == 3) new MenuHelp(player);
					else if (event.getRawSlot() == 4) new MenuSettings(player);
					new BukkitRunnable() {
						public void run() {
							setContent(true);
						}
					}.runTask(AxMenu.getInstance());
				}
			}.runTask(AxMenu.getInstance());
		}
		
		@Override
		@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
		public void unregisterOnClose(InventoryCloseEvent event) {
			if (event.getPlayer().equals(player)) clearInv();
		}
		
		@Override
		@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
		public void unregisterOnLeaveEvent(PlayerQuitEvent event) {
			if (!event.getPlayer().getUniqueId().equals(((OfflinePlayer) inventory.getHolder()).getUniqueId())) return;
			task.cancel();
			clearInv();
			unregister();
			menus.remove(this);
		}
		
		@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
		public void clearOnTeleport(PlayerTeleportEvent event) {
			if (event.isCancelled() || !event.getPlayer().equals(player)) return;
			clearInv();
			new BukkitRunnable() {
				public void run() {
					setContent(true);
				}
			}.runTaskLater(AxMenu.getInstance(),20);
		}
		
		void clearInv() {
			for (int i = 0; i < 5; i++) inventory.setItem(i,null);
		}
	}
}