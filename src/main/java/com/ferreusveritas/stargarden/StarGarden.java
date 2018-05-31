package com.ferreusveritas.stargarden;

import com.ferreusveritas.stargarden.proxy.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.oredict.OreDictionary;

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
			"required-after:jei";
	
	@SidedProxy(clientSide = "com.ferreusveritas.stargarden.proxy.ClientProxy", serverSide = "com.ferreusveritas.stargarden.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Item logo;
	
	public static final CreativeTabs starGardenTab = new CreativeTabs(ModConstants.MODID) {
		@SideOnly(Side.CLIENT)
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(logo);
		}
	};
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		Thermal.preInit();
		ProjectRed.preInit();
		
		//ModConfigs.preInit(event);//Naturally this comes first so we can react to settings
		//ModBlocks.preInit();
		logo = new ItemLogo();
		//ModItems.preInit();
		//ModTrees.preInit();

		Banners.preInit();
		
		proxy.preInit();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		Thermal.init();
		ProjectRed.init();
		proxy.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		Thermal.postInit();
		ProjectRed.postInit();
	}
	
	@Mod.EventHandler
	public void onFMLLoadComplete(FMLLoadCompleteEvent event) {
		Thermal.onLoadComplete();
		ProjectRed.onLoadComplete();
	}
	
	@Mod.EventBusSubscriber
	public static class RegistrationHandler {
		
		@SubscribeEvent
		public static void oreRegister(OreDictionary.OreRegisterEvent event) {
			ProjectRed.oreRegister(event.getName(), event.getOre());
		}
		
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			//ModBlocks.registerBlocks(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			event.getRegistry().register(logo);
			//ModItems.registerItems(event.getRegistry());
		}
		
		@SubscribeEvent
		public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
			Thermal.registerRecipes(event.getRegistry());
			ProjectRed.registerRecipes(event.getRegistry());
			//ModRecipes.registerRecipes(event.getRegistry());
		}
		
		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public static void registerModels(ModelRegistryEvent event) {
			ModelLoader.setCustomModelResourceLocation(logo, 0, new ModelResourceLocation(logo.getRegistryName(), "inventory"));
			//ModModels.registerModels(event);
		}
		
	}
	
}