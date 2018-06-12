package com.ferreusveritas.stargarden;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.IForgeRegistry;

public class Security implements IFeature {

	@Override
	public void preInit() {
        MinecraftForge.EVENT_BUS.register(new SecurityHandler());
	}

	@Override
	public void init() { }

	@Override
	public void postInit() { }

	@Override
	public void onLoadComplete() { }

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) { }
	
}
