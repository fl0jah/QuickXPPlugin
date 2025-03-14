package com.fattied.fl0jah.command;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import com.fattied.fl0jah.main.Main;

public class BottleCreateExecutor implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (args.length == 1) {
				int xp_level = Integer.parseInt(args[0]);
				
				if (xp_level <= 0) { player.sendMessage("" + ChatColor.DARK_RED + ChatColor.BOLD + "Enchantment level argument must be non-negative!"); }
				else {
					
					int player_level = getPlayerExp(player);

					if (player_level < xp_level) { 
						player.sendMessage("" + ChatColor.DARK_RED + "You need " + 
								ChatColor.DARK_RED + ChatColor.BOLD + (xp_level - player_level) + 
								ChatColor.DARK_RED + " more experience!"); 
					}else {
						changePlayerExp(player, player_level - xp_level);
						
						ItemStack exp = new ItemStack(Material.EXPERIENCE_BOTTLE);
						ItemMeta exp_meta = exp.getItemMeta();
						exp_meta.setDisplayName("" + ChatColor.DARK_GREEN + ChatColor.ITALIC + "Experience Bottle");
						
						exp_meta.setLore(Arrays.asList("------------", 
								"" + ChatColor.LIGHT_PURPLE + "Experience Points: " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + xp_level,
								"\n",
								ChatColor.WHITE + "Right-click To Redeem"));
						
						PersistentDataContainer pdc = exp_meta.getPersistentDataContainer();
						pdc.set(new NamespacedKey(JavaPlugin.getPlugin(Main.class), "cstm_exp_lvl"), 
								PersistentDataType.INTEGER, 
								xp_level);
						
						exp.setItemMeta(exp_meta);
						
						player.getInventory().addItem(exp);
						
						player.sendMessage(ChatColor.DARK_GREEN + "XP Bottle Created!");
					}
				}
			}else {
				player.sendMessage("" + ChatColor.DARK_RED + ChatColor.BOLD + "Missing enchantment level argument!");
			}
		}else {
			System.out.println("[QuickXP] Command Request From Console Disallowed!");
		}
		return true;
	}
	
	private static int getExpToLevelUp(int level){
        if(level <= 15){
            return 2*level+7;
        } else if(level <= 30){
            return 5*level-38;
        } else {
            return 9*level-158;
        }
    }
 
    // Calculate total experience up to a level
    private static int getExpAtLevel(int level){
        if(level <= 16){
            return (int) (Math.pow(level,2) + 6*level);
        } else if(level <= 31){
            return (int) (2.5*Math.pow(level,2) - 40.5*level + 360.0);
        } else {
            return (int) (4.5*Math.pow(level,2) - 162.5*level + 2220.0);
        }
    }
 
    // Calculate player's current EXP amount
    public static int getPlayerExp(Player player){
        int exp = 0;
        int level = player.getLevel();
     
        // Get the amount of XP in past levels
        exp += getExpAtLevel(level);
     
        // Get amount of XP towards next level
        exp += Math.round(getExpToLevelUp(level) * player.getExp());
     
        return exp;
    }

  // Give or take EXP
  public static void changePlayerExp(Player player, int exp){
	  
   // Reset player's current exp to 0
   player.setExp(0);
   player.setLevel(0);
   
   // Give the player their exp back
   player.giveExp(exp);   
  }

}
