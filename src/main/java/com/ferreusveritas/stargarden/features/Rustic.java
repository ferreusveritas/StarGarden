package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.mcf.features.IFeature;
import com.ferreusveritas.stargarden.ModConstants;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class Rustic implements IFeature {
	
	public static final String RUSTIC = "rustic";
	
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
		Vanilla.removeOre( new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(RUSTIC, "wildberries"))) , "dyeRed");
	}
	
	@Override
	public void onLoadComplete() { }
	
	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) { }
	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) { }
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		ItemStack wildBerries = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(RUSTIC, "wildberries")));
		GameRegistry.addShapelessRecipe(new ResourceLocation(ModConstants.MODID, "wildberriesreddye"), null, new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()), Ingredient.fromStacks(wildBerries));
	}
	
	@Override
	public void registerModels() { }
	
}
