package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.stargarden.features.ocean.BiomeProviderOceanSpawn;
import com.ferreusveritas.stargarden.util.Util;

import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Ocean extends BaseFeature {
	
	public class EventHandler {
		
	    @SubscribeEvent(priority = EventPriority.HIGH)
		public void onWorldLoad(WorldEvent.Load event) {
			World world = event.getWorld();
			if(world.provider.getDimension() == 0) {
				Util.setRestrictedObject(WorldProvider.class, world.provider, new BiomeProviderOceanSpawn(world.getBiomeProvider()), "biomeProvider", "field_76578_c");
			}
		}
		
	}
	
	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}
	
}
