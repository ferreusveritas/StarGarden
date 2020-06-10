package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.stargarden.items.ItemLogo;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class Logo extends BaseFeature {
	
	public static Item logo;
	
	@Override
	public void createItems() {
		logo = new ItemLogo();
	}
	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		registry.register(logo);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(Logo.logo, 0, new ModelResourceLocation(Logo.logo.getRegistryName(), "inventory"));
	}
	
}
