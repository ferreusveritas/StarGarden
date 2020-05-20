package com.ferreusveritas.stargarden.util;

import com.ferreusveritas.stargarden.ModConstants;
import com.ferreusveritas.stargarden.util.ShapelessColorRecipeFactory.ShapelessColorRecipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.GameData;

public class RecipeHelper {

	public static void addColorRecipe(ItemStack output, String key, Object... input) {
		ResourceLocation location = new ResourceLocation(ModConstants.MODID, output.getItem().getRegistryName().getResourcePath() + "_" + key + "_" + output.getItemDamage());
		ShapelessColorRecipe recipe = new ShapelessColorRecipe(location, output, input);
		recipe.setRegistryName(location);
		GameData.register_impl(recipe);
	}
	
}
