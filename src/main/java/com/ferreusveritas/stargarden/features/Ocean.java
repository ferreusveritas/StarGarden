package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.mcf.features.IFeature;
import com.ferreusveritas.mcf.util.Util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class Ocean implements IFeature {
	
	public class EventHandler {
		
		@SubscribeEvent
		public void onWorldLoad(WorldEvent.Load event) {
			World world = event.getWorld();
			if(world.provider.getDimension() == 0) {
				Biome ocean = Biome.REGISTRY.getObject(new ResourceLocation("deep_ocean"));
				Biome deepOcean = Biome.REGISTRY.getObject(new ResourceLocation("deep_ocean"));
				Util.setRestrictedObject(WorldProvider.class, world.provider, new BiomeProviderOceanSpawn(world.getBiomeProvider(), ocean, deepOcean), "biomeProvider", "field_76578_c");
			}
		}
		
	}
	
	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
	
	@Override
	public void createBlocks() { }
	
	@Override
	public void createItems() { }
	
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
	public void registerItems(IForgeRegistry<Item> event) { }
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) { }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() { }
	
}
