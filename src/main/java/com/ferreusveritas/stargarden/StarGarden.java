package com.ferreusveritas.stargarden;

import java.util.ArrayList;

import com.ferreusveritas.mcf.features.IFeature;
import com.ferreusveritas.stargarden.features.Banners;
import com.ferreusveritas.stargarden.features.ComputerCraft;
import com.ferreusveritas.stargarden.features.ProjectRed;
import com.ferreusveritas.stargarden.features.Thermal;
import com.ferreusveritas.stargarden.features.Vanilla;
import com.ferreusveritas.stargarden.proxy.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.threadpool.Arrays;

/**
* <p><pre><tt><b>
*  ╭─────────────────╮
*  │                 │
*  │    ✦            │
*  │          ✦      │
*  │                 │
*  │           ✦     │
*  │     ✦           │
*  │             ✦   │
*  │                 │
*  │  █████████████  │
*  │  ▒▒▒▒▒▒▒▒▒▒▒▒▒  │
*  │  ░░░░░░░░░░░░░  │
*  ╞═════════════════╡
*  │   STAR GARDEN   │
*  ╰─────────────────╯
* </b></tt></pre></p>
* 
* <p>
* 2018 Ferreusveritas
* </p>
*
*/
@Mod(modid = ModConstants.MODID, version=ModConstants.VERSION,dependencies=ModConstants.DEPENDENCIES)
public class StarGarden {
	
	@Mod.Instance(ModConstants.MODID)
	public static StarGarden instance;
	
	@SidedProxy(clientSide = "com.ferreusveritas.stargarden.proxy.ClientProxy", serverSide = "com.ferreusveritas.stargarden.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static ArrayList<IFeature> features = new ArrayList<>();
	
	public StarGarden() {
		features.addAll(Arrays.asList(new IFeature[] { new Vanilla(), new Thermal(), new ProjectRed(), new ComputerCraft(), new Banners() }));
	}
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		features.forEach(i -> i.preInit());
		proxy.preInit();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		features.forEach(i -> i.init());
		proxy.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		features.forEach(i -> i.postInit());
	}
	
	@Mod.EventHandler
	public void onFMLLoadComplete(FMLLoadCompleteEvent event) {
		features.forEach(i -> i.onLoadComplete());
	}
	
	@Mod.EventBusSubscriber
	public static class RegistrationHandler {
		
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
		}
		
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			features.forEach(i -> i.registerItems(event.getRegistry()));
		}
		
		@SubscribeEvent
		public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
			features.forEach(i -> i.registerRecipes(event.getRegistry()));
		}
		
		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public static void registerModels(ModelRegistryEvent event) {
			features.forEach(i -> i.registerModels());
		}
		
	}
	
}