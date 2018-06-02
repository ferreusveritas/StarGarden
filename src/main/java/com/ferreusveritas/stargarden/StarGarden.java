package com.ferreusveritas.stargarden;

import java.util.ArrayList;

import com.ferreusveritas.stargarden.proxy.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
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
@Mod(modid = ModConstants.MODID, version=ModConstants.VERSION,dependencies=StarGarden.DEPEND)
public class StarGarden {
	
	@Mod.Instance(ModConstants.MODID)
	public static StarGarden instance;
	
	public static final String DEPEND = 
			"required-after:projectred-core;" +
			"required-after:projectred-transmission;" +
			"required-after:thermalfoundation;" +
			"required-after:thermalexpansion;" +
			"required-after:jei;" +
			"required-after:" + ComputerCraft.COMPUTERCRAFT + ";" +
			"required-after:biomesoplenty";
	
	@SidedProxy(clientSide = "com.ferreusveritas.stargarden.proxy.ClientProxy", serverSide = "com.ferreusveritas.stargarden.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Item logo;
	
	public static ArrayList<IFeature> features = new ArrayList<>();
	
	/*public static final CreativeTabs starGardenTab = new CreativeTabs(ModConstants.MODID) {
		@SideOnly(Side.CLIENT)
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(logo);
		}
	};*/
	
	public StarGarden() {
		features.addAll(Arrays.asList(new IFeature[] { new Vanilla(), new Thermal(), new ProjectRed(), new ComputerCraft(), new Banners() }));
	}
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logo = new ItemLogo();

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
			event.getRegistry().register(logo);
		}
		
		@SubscribeEvent
		public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
			features.forEach(i -> i.registerRecipes(event.getRegistry()));
		}
		
		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public static void registerModels(ModelRegistryEvent event) {
			ModelLoader.setCustomModelResourceLocation(logo, 0, new ModelResourceLocation(logo.getRegistryName(), "inventory"));
		}
		
	}
	
}