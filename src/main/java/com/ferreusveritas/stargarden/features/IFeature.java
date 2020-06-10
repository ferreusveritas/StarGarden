package com.ferreusveritas.stargarden.features;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public interface IFeature {
	
	void preInit();
	void createBlocks();
	void createItems();
	void registerEvents();
	void init();
	void postInit();
	void onLoadComplete();
	void registerBlocks(IForgeRegistry<Block> registry);
	void registerItems(IForgeRegistry<Item> registry);
	void registerRecipes(IForgeRegistry<IRecipe> registry);
	void registerEntities(IForgeRegistry<EntityEntry> registry);
	
	@SideOnly(Side.CLIENT)
	void registerModels(ModelRegistryEvent event);
}
