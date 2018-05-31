package com.ferreusveritas.stargarden;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public interface IFeature {
	
	void preInit();
	void init();
	void postInit();
	void onLoadComplete();		
	void oreRegister(String oreName, ItemStack ore);
	void registerRecipes(IForgeRegistry<IRecipe> registry);
	
}
