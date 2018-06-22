package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.mcf.features.IFeature;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
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
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {}
	
	public void removeItemStackFromJEI(ItemStack stack) { }

}
