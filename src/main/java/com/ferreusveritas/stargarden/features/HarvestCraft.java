package com.ferreusveritas.stargarden.features;

import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cofh.thermalexpansion.util.managers.machine.CentrifugeManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public class HarvestCraft extends BaseFeature {
	
	public static final String HARVESTCRAFT = "harvestcraft";
	
	public static Item getHarvestCraftItem(String name) {
		return Item.REGISTRY.getObject(new ResourceLocation(HARVESTCRAFT, name));
	}
	
	public static List<ItemStack> toItemStackList(List<String> names) {
		return names.stream().map(name -> new ItemStack(getHarvestCraftItem(name))).collect(Collectors.toList());
	}
	
	@Override
	public void createItems() {
	}
	
	@Override
	public void init() { }
	
	@Override
	public void postInit() {
		//Remove Centrifuge recipes that make Cooking oil.  Cotton Seed Oil is toxic,  Tea leaf oil is not suitable for cooking.
		List<ItemStack> csRemoveList = toItemStackList(Arrays.asList("mustardseeditem", "cottonseeditem", "tealeafitem"));
		csRemoveList.addAll(Arrays.asList(new ItemStack(Blocks.PUMPKIN), new ItemStack(Items.PUMPKIN_SEEDS)));
		csRemoveList.forEach(CentrifugeManager::removeRecipe);
		
		//Fix sesame seed Centrifuge recipe
		ItemStack oilSesame = new ItemStack(getHarvestCraftItem("sesameoilitem"));
		ItemStack baitGrain = new ItemStack(getHarvestCraftItem("grainbaititem"));
		CentrifugeManager.addRecipe(4000, new ItemStack(getHarvestCraftItem("sesameseedsitem")), asList(oilSesame, baitGrain), null);
	}
	
	@Override
	public void onLoadComplete() {
	}
	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
	}
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
	}
	
}
