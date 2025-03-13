package com.fattied.fl0jah.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		
		// Create recipe for experience bottle
		ShapedRecipe recipe = new ShapedRecipe(
				new NamespacedKey(this, "cstm_btle_ench"),
				new ItemStack(Material.EXPERIENCE_BOTTLE));
		
		recipe.shape(" G ", 
					 "TBF", 
					 " S ");
		recipe.setIngredient('G', Material.GLOWSTONE_DUST);
		recipe.setIngredient('T', Material.GHAST_TEAR);
		recipe.setIngredient('B', Material.POTION);
		recipe.setIngredient('F', Material.BLAZE_POWDER);
		recipe.setIngredient('S', Material.SUGAR);
		
		Bukkit.addRecipe(recipe);
		
		System.out.println("[QuickXP] Plugin Successfully Enabled!");
	}
	
	@Override
	public void onDisable() {
		System.out.println("[QuickXP] Plugin Successfully Disabled!");
	}

}
