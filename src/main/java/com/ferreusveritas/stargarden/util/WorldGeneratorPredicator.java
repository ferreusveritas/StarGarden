package com.ferreusveritas.stargarden.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WorldGeneratorPredicator {
	
    private Set<IWorldGenerator> worldGenerators;	
    private Map<IWorldGenerator, Integer> worldGeneratorIndex;
    
	public WorldGeneratorPredicator() {
		
		try {
			Field worldGenerators = GameRegistry.class.getField("worldGenerators");
			Field worldGeneratorIndex = GameRegistry.class.getField("worldGeneratorIndex");

			worldGenerators.setAccessible(true);
			worldGeneratorIndex.setAccessible(true);
			
			this.worldGenerators = (Set<IWorldGenerator>) worldGenerators.get(null);
			this.worldGeneratorIndex = (Map<IWorldGenerator, Integer>) worldGeneratorIndex.get(null);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addPredicate(String className, Predicate<World> predicate) {

		Map<IWorldGenerator, Integer> movedGenerators = new HashMap<>();
		
		Iterator<IWorldGenerator> iter = worldGenerators.iterator();

		while(iter.hasNext()) {
			IWorldGenerator gen = iter.next();			
			if(gen.getClass().getSimpleName().equals(className)) {
				movedGenerators.put(gen, worldGeneratorIndex.get(gen));
				iter.remove();
			}
		}
		
		for(Entry<IWorldGenerator, Integer> entry : movedGenerators.entrySet()) {
			GameRegistry.registerWorldGenerator( (rnd, cx, cz, w, cg, cp) -> { if(predicate.test(w)) entry.getKey().generate(rnd, cx, cz, w, cg, cp); }, entry.getValue() );
		}
		
	}
	
}
