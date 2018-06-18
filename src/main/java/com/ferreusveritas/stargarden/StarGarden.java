package com.ferreusveritas.stargarden;

import com.ferreusveritas.mcf.FeatureableMod;
import com.ferreusveritas.stargarden.features.Banners;
import com.ferreusveritas.stargarden.features.CommonProxy;
import com.ferreusveritas.stargarden.features.ComputerCraft;
import com.ferreusveritas.stargarden.features.HarvestCraft;
import com.ferreusveritas.stargarden.features.Logo;
import com.ferreusveritas.stargarden.features.ProjectRed;
import com.ferreusveritas.stargarden.features.Thermal;
import com.ferreusveritas.stargarden.features.Vanilla;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
*  │ＳＴＡＲ  ＧＡＲＤＥＮ│
*  ╰─────────────────╯
* </b></tt></pre></p>
* <p>
* 2018 Ferreusveritas
* </p>
* 
* Disclaimer: This mod is meant to be used for a personal modpack.  It makes invasive 
* changes to several mods and may cause errors in doing so.  Do not report errors to
* the authors of the modified mods if this project is active.
* 
*/
@Mod(modid = ModConstants.MODID, version=ModConstants.VERSION, dependencies=ModConstants.DEPENDENCIES)
public class StarGarden extends FeatureableMod {
	
	@Mod.Instance(ModConstants.MODID)
	public static StarGarden instance;
	
	@SidedProxy(clientSide = "com.ferreusveritas.stargarden.features.ClientProxy", serverSide = "com.ferreusveritas.stargarden.features.CommonProxy")
	public static CommonProxy proxy;
	
	protected void setupFeatures() {
		addFeatures(
			new Vanilla(),		//Make all colors safe and unified. Moves spawn eggs into their own tab
			new Thermal(),		//Fixes colors. Removes pigments, coins, and nomismatic engines. Removes Morbs and related equipment.
			new ProjectRed(),	//Removes ingredient items for Project Red mods that aren't installed.  Redo recipes to use Thermal Expansion equipment.
			new ComputerCraft(),//Fixes colors. Redo recipes to use Thermal Expansion and Project Red items
			new HarvestCraft(), //Fixes colors. Removes crazy items. Removes fake Tofu related food items. 
			new Banners(),		//Adds banner patterns
			new Logo(),			//Adds a logo item for the server that acts as a method to move around items in creative tabs
			proxy
		);
	}
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		setupFeatures();
		super.preInit(event);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
	@Mod.EventHandler
	public void onLoadComplete(FMLLoadCompleteEvent event) {
		super.onLoadComplete(event);
	}
	
}