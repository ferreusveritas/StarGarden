package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.stargarden.features.ocean.BiomeProviderOceanSpawn;
import com.ferreusveritas.stargarden.util.Util;

import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Ocean extends BaseFeature {

    public static void SetBiomeProvider(WorldProvider worldProvider, BiomeProvider biomeProvider) {
		Util.setRestrictedObject(WorldProvider.class, worldProvider, biomeProvider, "biomeProvider", "field_76578_c");
    }
	
	public class EventHandler {
		
	    @SubscribeEvent(priority = EventPriority.HIGH)
		public void onCapabilitiesLoad(AttachCapabilitiesEvent<World> event) {
	    	World world = event.getObject();
			if(world.provider.getDimension() == 0) {//Only apply this to dimension 0
				SetBiomeProvider(world.provider, new BiomeProviderOceanSpawn(world.getBiomeProvider()));//Create a passthru wrapper for the biome provider
			}
		}
		
	}
	
	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
	
}
