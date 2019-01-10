package com.ferreusveritas.stargarden.util;

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
	
    private final Set<IWorldGenerator> worldGenerators;	
    private final Map<IWorldGenerator, Integer> worldGeneratorIndex;
    
	public WorldGeneratorPredicator() {
		worldGenerators = (Set<IWorldGenerator>) Util.getRestrictedObject(GameRegistry.class, null, "worldGenerators");
		worldGeneratorIndex = (Map<IWorldGenerator, Integer>) Util.getRestrictedObject(GameRegistry.class, null, "worldGeneratorIndex");
	}
	
	public void addPredicate(Predicate<String> classNamePredicate, Predicate<World> predicate) {

		Map<IWorldGenerator, Integer> movedGenerators = new HashMap<>();
		
		Iterator<IWorldGenerator> iter = worldGenerators.iterator();

		while(iter.hasNext()) {
			IWorldGenerator gen = iter.next();
			if(classNamePredicate.test(gen.getClass().getName())) {
				movedGenerators.put(gen, worldGeneratorIndex.get(gen));
				worldGeneratorIndex.remove(gen);
				iter.remove();
			}
		}
		
		for(Entry<IWorldGenerator, Integer> entry : movedGenerators.entrySet()) {
			GameRegistry.registerWorldGenerator( (rnd, cx, cz, w, cg, cp) -> { if(predicate.test(w)) entry.getKey().generate(rnd, cx, cz, w, cg, cp); }, entry.getValue() );
		}
		
	}
	
}
