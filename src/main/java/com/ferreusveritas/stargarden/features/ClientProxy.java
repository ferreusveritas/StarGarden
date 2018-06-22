package com.ferreusveritas.stargarden.features;

import java.util.ArrayList;

import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.item.ItemStack;

public class ClientProxy extends CommonProxy {

	@Override
	public void onLoadComplete() {
		super.onLoadComplete();
		RecipeBookClient.rebuildTable();
	}
	
	public void removeItemStackFromJEI(ItemStack stack) {
		if(!stack.isEmpty()) {
			ArrayList<ItemStack> removals = new ArrayList<>();
			removals.add(stack);
			mezz.jei.Internal.getIngredientRegistry().removeIngredientsAtRuntime(ItemStack.class, removals);
		}
	}
	
}
