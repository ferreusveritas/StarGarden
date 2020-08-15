package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.stargarden.ModConstants;

import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class Rustic extends BaseFeature {
	
	public static final String RUSTIC = "rustic";
	
	@Override
	public void postInit() {
		Vanilla.removeOre( new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(RUSTIC, "wildberries"))) , "dyeRed");
		Vanilla.removeOre( new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(RUSTIC, "grapes"))) , "dyePurple");
		Vanilla.removeOre( new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(RUSTIC, "ironberries"))) , "dyeLightGray");
	}
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		ItemStack wildBerries = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(RUSTIC, "wildberries")));
		GameRegistry.addShapelessRecipe(new ResourceLocation(ModConstants.MODID, "wildberriesreddye"), null, new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()), Ingredient.fromStacks(wildBerries));
		
		ItemStack grapes = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(RUSTIC, "grapes")));
		GameRegistry.addShapelessRecipe(new ResourceLocation(ModConstants.MODID, "grapespurpledye"), null, new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage()), Ingredient.fromStacks(grapes));
	}
	
}
