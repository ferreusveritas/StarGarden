package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.cathedral.proxy.ModelHelper;
import com.ferreusveritas.stargarden.features.skyhook.BlockSkyHook;
import com.ferreusveritas.stargarden.features.skyhook.ItemBlockSkyHook;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class SkyHook extends BaseFeature {

	Block skyHookBlock;
	
	@Override
	public void createBlocks() {
		skyHookBlock = new BlockSkyHook();
	}
	
	@Override
	public void createItems() {	}
	
	@Override
	public void registerBlocks(IForgeRegistry<Block> registry) {
		registry.register(skyHookBlock);
	}
	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) {
		registry.register(new ItemBlockSkyHook(skyHookBlock).setRegistryName(skyHookBlock.getRegistryName()));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		ModelHelper.regModel(Item.getItemFromBlock(skyHookBlock), 0, skyHookBlock.getRegistryName());
	}
	
}
