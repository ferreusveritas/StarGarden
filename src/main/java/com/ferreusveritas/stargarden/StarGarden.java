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
*/
@Mod(modid = ModConstants.MODID, version=ModConstants.VERSION, dependencies=ModConstants.DEPENDENCIES)
public class StarGarden extends FeatureableMod {
	
	@Mod.Instance(ModConstants.MODID)
	public static StarGarden instance;
	
	@SidedProxy(clientSide = "com.ferreusveritas.stargarden.features.ClientProxy", serverSide = "com.ferreusveritas.stargarden.features.CommonProxy")
	public static CommonProxy proxy;
	
	protected void setupFeatures() {
		addFeatures(
			new Vanilla(),
			new Thermal(),
			new ProjectRed(),
			new ComputerCraft(),
			new HarvestCraft(),
			new Banners(),
			new Logo(),
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