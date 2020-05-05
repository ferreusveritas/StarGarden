package com.ferreusveritas.stargarden;

import com.ferreusveritas.stargarden.features.ComputerCraft;
import com.ferreusveritas.stargarden.features.Malisis;
import com.ferreusveritas.stargarden.features.ProjectRed;
import com.ferreusveritas.stargarden.features.Railcraft;
import com.ferreusveritas.stargarden.features.Rustic;
import com.ferreusveritas.stargarden.features.Thermal;

public class ModConstants extends com.ferreusveritas.dynamictrees.ModConstants {
	
	public static final String MODID = "stargarden";
	public static final String VERSION = "1.12.2-9999.9999.9999z";//Maxed out version to satisfy dependencies during dev, Assigned from gradle during build, do not change
	
	//Other Mods
	public static final String COMPUTERCRAFT = ComputerCraft.COMPUTERCRAFT;
	public static final String QUARK = "quark";
	public static final String JEI = "jei";
	public static final String BIOMESOPLENTY = "biomesoplenty";
	public static final String PHC_MODID = com.pam.harvestcraft.Reference.MODID;
	public static final String RUSTIC = Rustic.RUSTIC;
	public static final String RAILCRAFT = Railcraft.RAILCRAFT;
	
	public static final String PHC_LATEST = PHC_MODID + AT + com.pam.harvestcraft.Reference.VERSION + ORGREATER;
	
	public static final String DEPENDENCIES 
			= REQAFTER + ProjectRed.PROJECTREDCORE
			+ NEXT
			+ REQAFTER + ProjectRed.PROJECTREDTRANS
			+ NEXT
			+ REQAFTER + Thermal.THERMALFOUNDATION
			+ NEXT
			+ REQAFTER + Thermal.THERMALEXPANSION
			+ NEXT
			+ REQAFTER + JEI
			+ NEXT
			+ REQAFTER + COMPUTERCRAFT
			+ NEXT
			+ REQAFTER + BIOMESOPLENTY
			+ NEXT
			+ REQAFTER + PHC_LATEST
			+ NEXT
			+ REQAFTER + DYNAMICTREES_LATEST
			+ NEXT
			+ REQAFTER + RUSTIC
			+ NEXT
			+ OPTAFTER + RAILCRAFT
			+ NEXT
			+ OPTAFTER + Malisis.MALISISDOORS;
	
}
