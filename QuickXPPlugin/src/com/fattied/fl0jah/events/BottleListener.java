package com.fattied.fl0jah.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import com.fattied.fl0jah.command.BottleCreateExecutor;
import com.fattied.fl0jah.main.Main;

public class BottleListener implements Listener {
	
	@EventHandler
	public void onSplash(PlayerInteractEvent e) {
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		
		if (item.getType() == Material.EXPERIENCE_BOTTLE) {
			
			PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
			
			if (pdc.has(new NamespacedKey(JavaPlugin.getPlugin(Main.class), "cstm_exp_lvl"))) {
				e.setCancelled(true);
								
				//e.getPlayer().getInventory().remove(item);
				item.setAmount(item.getAmount() - 1);
				
				int redeem = pdc.get(new NamespacedKey(JavaPlugin.getPlugin(Main.class), "cstm_exp_lvl"), PersistentDataType.INTEGER);
				
				BottleCreateExecutor.changePlayerExp(e.getPlayer(), BottleCreateExecutor.getPlayerExp(e.getPlayer()) + redeem);
				
				e.getPlayer().sendMessage(ChatColor.GREEN + "Experience Bottle Redeemed!");
				
			}else { return; }
		}else { return; }
	}

}
