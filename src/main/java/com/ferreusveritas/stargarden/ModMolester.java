package com.ferreusveritas.stargarden;

import java.util.ArrayList;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryModifiable;

public class ModMolester {

	public static void removeRecipe(String resource) {
		((IForgeRegistryModifiable)ForgeRegistries.RECIPES).remove(new ResourceLocation(resource));
	}
	
	public static void removeSmelterRecipe(ItemStack output) {
		Map<ItemStack, ItemStack> smeltingList = FurnaceRecipes.instance().getSmeltingList();

		for(Map.Entry<ItemStack, ItemStack> entry : smeltingList.entrySet()) {
			if(output.getItem().equals(entry.getValue().getItem())) {
				if(output.getItemDamage() == entry.getValue().getItemDamage()) {
					smeltingList.remove(entry.getKey());
					return;
				}
			}
		}
	}
	
	public static void removeItemStackFromJEI(ItemStack stack) {
		ArrayList<ItemStack> removals = new ArrayList<>();
		removals.add(stack);
		mezz.jei.Internal.getIngredientRegistry().removeIngredientsAtRuntime(ItemStack.class, removals);
	}
	
}
