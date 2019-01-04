package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.stargarden.util.Util;
import com.ferreusveritas.stargarden.world.StarWorldType;

import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Worlds extends BaseFeature {
	
	public static WorldType duvotica = null;
	
	public class EventHandler {
	    @SubscribeEvent(priority = EventPriority.HIGH)
		public void onWorldLoad(WorldEvent.Load event) {
			World world = event.getWorld();
			if(world.getWorldType() instanceof StarWorldType) {
				StarWorldType starWorldType = (StarWorldType) world.getWorldType();
				if(starWorldType.hasCustomWorldProvider()) {
					Util.setRestrictedObject(WorldProvider.class, world.provider, starWorldType.createCustomWorldProvider(), "biomeProvider", "field_76578_c");
				}
			}
		}
	}
	
	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		
		duvotica = new StarWorldType("duvotica");
	}
	
}
