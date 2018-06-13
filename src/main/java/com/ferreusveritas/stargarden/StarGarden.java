package com.ferreusveritas.stargarden;

import com.ferreusveritas.mcf.FeatureableMod;
import com.ferreusveritas.stargarden.features.Banners;
import com.ferreusveritas.stargarden.features.ComputerCraft;
import com.ferreusveritas.stargarden.features.ProjectRed;
import com.ferreusveritas.stargarden.features.Thermal;
import com.ferreusveritas.stargarden.features.Vanilla;
import com.ferreusveritas.stargarden.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;

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
@Mod(modid = ModConstants.MODID, version=ModConstants.VERSION, dependencies=ModConstants.DEPENDENCIES)
public class StarGarden extends FeatureableMod {
	
	@Mod.Instance(ModConstants.MODID)
	public static StarGarden instance;
	
	@SidedProxy(clientSide = "com.ferreusveritas.stargarden.proxy.ClientProxy", serverSide = "com.ferreusveritas.stargarden.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@Override
	protected void setupFeatures() {
		addFeatures(new Vanilla(), new Thermal(), new ProjectRed(), new ComputerCraft(), new Banners());
	}
	
}