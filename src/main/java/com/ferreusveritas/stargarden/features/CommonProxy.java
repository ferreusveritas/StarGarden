package com.ferreusveritas.stargarden.features;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class CommonProxy implements IFeature {

	@Override
	public void preInit() { }

	@Override
	public void createBlocks() { }

	@Override
	public void createItems() { }

	@Override
	public void registerEvents() { }

	@Override
	public void init() { }

	@Override
	public void postInit() { }

	@Override
	public void onLoadComplete() { }

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) { }

	@Override
	public void registerItems(IForgeRegistry<Item> registry) { }

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) { }

	@Override
	public void registerEntities(IForgeRegistry<EntityEntry> registry) { }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(ModelRegistryEvent event) {}
	
	public void removeItemStackFromJEI(ItemStack stack) { }

	public CreativeTabs findCreativeTab(String label) {
		return null;
	}

}
