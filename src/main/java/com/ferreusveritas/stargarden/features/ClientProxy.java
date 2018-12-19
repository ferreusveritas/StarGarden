package com.ferreusveritas.stargarden.features;

import java.util.ArrayList;

import net.minecraft.client.util.RecipeBookClient;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ClientProxy extends CommonProxy {

	@Override
	public CreativeTabs findCreativeTab(String label) {
		for(CreativeTabs iTab: CreativeTabs.CREATIVE_TAB_ARRAY) {
			if(iTab.getTabLabel().equals(label)) {
				return iTab;
			}
		}
		return null;
	}
	
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
