package com.ferreusveritas.stargarden;

import com.ferreusveritas.stargarden.features.ComputerCraft;
import com.ferreusveritas.stargarden.features.HarvestCraft;
import com.ferreusveritas.stargarden.features.ProjectRed;
import com.ferreusveritas.stargarden.features.Thermal;

public class ModConstants extends ModDepend {
	
	public static final String MODID = "stargarden";
	public static final String VERSION = "@VERSION@";
	//public static final String VERSION = "1.12.2-1.0.0";
	
	public static final String DEPENDENCIES 
			= REQ_AFTER + ProjectRed.PROJECTREDCORE
			+ NEXT
			+ REQ_AFTER + ProjectRed.PROJECTREDTRANS
			+ NEXT
			+ REQ_AFTER + Thermal.THERMALFOUNDATION
			+ NEXT
			+ REQ_AFTER + Thermal.THERMALEXPANSION
			+ NEXT
			+ REQ_AFTER + JEI
			+ NEXT
			+ REQ_AFTER + ComputerCraft.COMPUTERCRAFT
			+ NEXT
			+ REQ_AFTER + BIOMESOPLENTY
			+ NEXT
			+ REQ_AFTER + MCF
			+ NEXT
			+ REQ_AFTER + HarvestCraft.HARVESTCRAFT
			//+ NEXT
			//+ REQ_AFTER + Malisis.MALISISDOORS;
			;
	
}
