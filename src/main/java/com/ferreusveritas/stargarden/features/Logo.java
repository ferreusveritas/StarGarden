package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.mcf.features.IFeature;
import com.ferreusveritas.stargarden.items.ItemLogo;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class Logo implements IFeature {
	
	public static Item logo;
	
	@Override
	public void preInit() { }
	
	@Override
	public void createBlocks() { }

	@Override
	public void createItems() {
		logo = new ItemLogo();
	}

	@Override
	public void registerEvents() { }
	
	@Override
	public void init() { }
	
	@Override
	public void postInit() { }
	
	@Override
	public void onLoadComplete() { }
	
	@Override
	public void registerBlocks(IForgeRegistry<Block> event) { }
	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		registry.register(logo);
	}
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) { }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(Logo.logo, 0, new ModelResourceLocation(Logo.logo.getRegistryName(), "inventory"));
	}
	
}
