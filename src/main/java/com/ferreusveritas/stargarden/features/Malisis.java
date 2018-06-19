package com.ferreusveritas.stargarden.features;

import java.util.ArrayList;
import java.util.Arrays;

import com.ferreusveritas.mcf.features.IFeature;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public class Malisis implements IFeature {

	public static String MALISISDOORS = "malisisdoors";
	
	public static Item getMalisisDoorsItem(String name) {
		return Item.REGISTRY.getObject(new ResourceLocation(MALISISDOORS, name));
	}
	
	public static Block getMalisisDoorsBlock(String name) {
		return Block.REGISTRY.getObject(new ResourceLocation(MALISISDOORS, name));
	}
	
	public static void removeItemFromMalisisTab(Item item) {
		
		//Class malisisTab = Class.forName("net.malisis.core.inventory.MalisisTab");
		
	}
	
	public static ArrayList<ItemStack> getRemoveItemList() {
		ArrayList<ItemStack> list = new ArrayList<>();
		
		Arrays.asList("saloon", "verticalhatch", "carriage_door", "medieval_door", "rustyhatch", "rustyladder")
		.forEach(name -> list.add(new ItemStack(getMalisisDoorsBlock(name))));

		Arrays.asList("rustyhandle", "forcefielditem")
		.forEach(name -> list.add(new ItemStack(getMalisisDoorsItem(name))));
		
		return list;
	}
	
	public static ArrayList<ResourceLocation> getRemoveRecipeList() {
		ArrayList<ResourceLocation> list = new ArrayList<>();
		Arrays.asList("forcefield_item", "saloon_door", "vertical_hatch", "rusty_hatch", "rusty_handle", "rusty_ladder")
			.forEach(name -> list.add(new ResourceLocation(MALISISDOORS, name)));
		return list;
	}
	
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
	public void postInit() {
		//Remove items from creative 
		getRemoveItemList().forEach(i -> i.getItem().setCreativeTab(null));
		
		//Remove Recipes
		getRemoveRecipeList().forEach(Vanilla::removeRecipe);
	}

	@Override
	public void onLoadComplete() {
		getRemoveItemList().forEach( Vanilla::removeItemStackFromJEI );
	}

	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) { }

	@Override
	public void registerItems(IForgeRegistry<Item> registry) { }

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) { }

	@Override
	public void registerModels() { }
	
}
