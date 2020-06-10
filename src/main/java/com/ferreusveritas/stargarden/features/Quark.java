package com.ferreusveritas.stargarden.features;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class Quark extends BaseFeature {
	
	public static final String QUARK = "quark";
	
	@Override
	public void postInit() {
		removeSpeleothemRecipes();
	}
	
	//The recipes conflict with pillars
	private void removeSpeleothemRecipes() {
		ForgeRegistries.RECIPES.forEach( recipe -> {
			ResourceLocation regName = recipe.getRegistryName();
			if(QUARK.equals(regName.getResourceDomain())) {
				if(regName.getResourcePath().endsWith("_speleothem")) {
					Vanilla.removeRecipe(regName);
				}
			}
		});
	}
	
}
