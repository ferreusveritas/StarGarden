package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.WorldGenRegistry.BiomeDataBasePopulatorRegistryEvent;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors.EnumChance;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors.ISpeciesSelector;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors.RandomSpeciesSelector;
import com.ferreusveritas.dynamictrees.api.worldgen.IBiomeDataBasePopulator;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenPredicate;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenVine;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase.Operation;
import com.ferreusveritas.stargarden.ModConstants;
import com.ferreusveritas.stargarden.StarGarden;
import com.ferreusveritas.stargarden.render.RenderSpiderDuvotica;
import com.ferreusveritas.stargarden.util.WorldGeneratorPredicator;
import com.ferreusveritas.stargarden.world.duvotica.BiomeDuvotica;
import com.ferreusveritas.stargarden.world.duvotica.EntitySpiderDuvotica;
import com.ferreusveritas.stargarden.world.duvotica.WorldTypeDuvotica;
import com.ferreusveritas.stargarden.world.maridia.BiomeMaridia;
import com.ferreusveritas.stargarden.world.maridia.WorldTypeMaridia;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid = ModConstants.MODID)
public class Worlds extends BaseFeature {
	
	public static final String DUVOTICA = "duvotica";
	public static final String MARIDIA = "maridia";
	
	public static final String DUVOTICA_SPIDER = "varroa";
	
	public static final int DUVOTICA_SPIDER_ID = 100;
	
	public static WorldType duvotica = null;
	public static WorldType maridia = null;
	
	public static BiomeDuvotica duvoticaBiome = null;
	public static BiomeMaridia maridiaBiome = null;

	@Override
	public void preInit() {
		setupDuvotica();
		setupMaridia();
	}
	
	
	////////////////////////////////////////////////////////////////
	// Duvotica
	////////////////////////////////////////////////////////////////
	
	public void setupDuvotica() {
		duvoticaBiome = new BiomeDuvotica();
		
		registerBiome(duvoticaBiome, new ResourceLocation(ModConstants.MODID, DUVOTICA));
		
		BiomeDictionary.addTypes(duvoticaBiome, BiomeDictionary.Type.LUSH, BiomeDictionary.Type.WET);
		
		duvotica = new WorldTypeDuvotica(DUVOTICA);//This is self registering in it's base WorldType class
		
		registerDuvoticaEntities();
	}
	
	public void registerDuvoticaEntities() {
		EntityRegistry.registerModEntity(new ResourceLocation(ModConstants.MODID, DUVOTICA_SPIDER), EntitySpiderDuvotica.class, DUVOTICA_SPIDER, DUVOTICA_SPIDER_ID, StarGarden.instance, 50, 1, true, 0x822B39, 0xFFE33D);
	}

	
	////////////////////////////////////////////////////////////////
	// Maridia
	////////////////////////////////////////////////////////////////

	public void setupMaridia() {

		maridiaBiome = new BiomeMaridia();
		
		registerBiome(maridiaBiome, new ResourceLocation(ModConstants.MODID, MARIDIA));
		
		BiomeDictionary.addTypes(maridiaBiome, BiomeDictionary.Type.OCEAN);
		
		maridia = new WorldTypeMaridia(MARIDIA);//This is self registering in it's base WorldType class
				
		registerMaridiaEntities();
	}
	
	public void registerMaridiaEntities() { }

	
	////////////////////////////////////////////////////////////////
	// Misc
	////////////////////////////////////////////////////////////////
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntitySpiderDuvotica.class, manager -> new RenderSpiderDuvotica(manager));
	}
	
	@Override
	public void postInit() {
		Species darkoak = TreeRegistry.findSpecies(new ResourceLocation("dynamictrees", "darkoak"));
		darkoak.addGenFeature(new FeatureGenPredicate(
			new FeatureGenVine().setQuantity(16).setMaxLength(8)//Generate vines
			).setBiomePredicate(biome -> biome == duvoticaBiome)
		);
		
		WorldGeneratorPredicator wgPredicator = new WorldGeneratorPredicator();
		
		wgPredicator.addPredicate(name -> "team.chisel.common.util.GenerationHandler".equals(name), world -> world.provider.getDimension() == 0);
		
		duvoticaBiome.assignMaterials();
		maridiaBiome.assignMaterials();
	}
	
	public void registerBiome(Biome biome, ResourceLocation resLoc) {
        biome.setRegistryName(resLoc);
        ForgeRegistries.BIOMES.register(biome);
        BiomeManager.addSpawnBiome(biome);
	}
	
	@SubscribeEvent
	public static void registerDataBasePopulators(final BiomeDataBasePopulatorRegistryEvent event) {
		
		IBiomeDataBasePopulator populator = new IBiomeDataBasePopulator() {
			@Override
			public void populate(BiomeDataBase dbase) {
				Species palmTree = TreeRegistry.findSpecies(new ResourceLocation("dynamictreesbop", "palm"));
				Species darkOakTree = TreeRegistry.findSpecies(new ResourceLocation("dynamictrees", "darkoak"));
				Species yellowAutumn = TreeRegistry.findSpecies(new ResourceLocation("dynamictreesbop", "yellowautumn"));
				
				ISpeciesSelector speciesSelector = new RandomSpeciesSelector().add(darkOakTree, 12).add(palmTree, 8).add(yellowAutumn, 1);
				dbase.setMultipass(duvoticaBiome, pass -> {
					switch(pass) {
						case 0: return 0;//Zero means to run as normal
						case 1: return 5;//Return only radius 5 on pass 1
						case 2: return 3;//Return only radius 3 on pass 2
						default: return -1;//A negative number means to terminate
					}
				});
				dbase.setSpeciesSelector(duvoticaBiome, speciesSelector, Operation.REPLACE);
				dbase.setChanceSelector(duvoticaBiome, (rnd, spc, rad) -> { return rnd.nextFloat() < 0.9f ? EnumChance.OK : EnumChance.UNHANDLED; }, Operation.REPLACE);
				dbase.setDensitySelector(duvoticaBiome, (rnd, nd) -> { return nd * 0.7f; } , Operation.REPLACE);
			};
				
		};
				
		event.register(populator);
	}
	
}


