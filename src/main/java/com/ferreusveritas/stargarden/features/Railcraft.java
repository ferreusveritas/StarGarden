package com.ferreusveritas.stargarden.features;

import com.ferreusveritas.stargarden.ModConstants;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class Railcraft extends BaseFeature {

	public static final String RAILCRAFT = "railcraft";
	
	@Override
	public void postInit() { }
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {

		//Add recipe for the Flux Transformer
		ItemStack fluxTransformer = new ItemStack(Block.REGISTRY.getObject(new ResourceLocation(RAILCRAFT, "flux_transformer")));
		ItemStack redstoneBlock = new ItemStack(Blocks.REDSTONE_BLOCK);
		GameRegistry.addShapedRecipe(new ResourceLocation(ModConstants.MODID, "flux_transformer"), null, fluxTransformer,
			"pip",
			"iri",
			"pip",
			'p', "plateCopper", 
			'i', "ingotGold",
			'r', redstoneBlock);
		
		//Add recipe for the Force Track Emitter
		ItemStack forceTrackEmitter = new ItemStack(Block.REGISTRY.getObject(new ResourceLocation(RAILCRAFT, "force_track_emitter")));
		ItemStack diamondBlock = new ItemStack(Blocks.DIAMOND_BLOCK);
		GameRegistry.addShapedRecipe(new ResourceLocation(ModConstants.MODID, "force_track_emitter"), null, forceTrackEmitter,
				"pip",
				"idi",
				"pip",
				'p', "plateTin", 
				'i', "ingotCopper",
				'd', diamondBlock);

		//Add recipe for the Tool Charge Meter
		ItemStack toolChargeMeter = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(RAILCRAFT, "tool_charge_meter")));
		GameRegistry.addShapedRecipe(new ResourceLocation(ModConstants.MODID, "tool_charge_meter"), null, toolChargeMeter,
				"t t",
				"bgb",
				" c ",
				't', "ingotTin",
				'b', new ItemStack(Blocks.STONE_BUTTON),
				'g', new ItemStack(Blocks.GLASS_PANE),
				'c', "ingotCopper");
		
		//Add recipe for the Electric Locomotive
		ItemStack electricLocomotive = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(RAILCRAFT, "locomotive_electric")));
		ItemStack invertedCageLamp = new ItemStack(ProjectRed.getProjectRedLumItem("inverted_cage_lamp"));
		ItemStack redstoneServo = new ItemStack(Thermal.getThermalFoundationMaterial(), 1, 512);
		ItemStack energyCell = new ItemStack(Thermal.getThermalExpansionItem("cell"));
		ItemStack minecart = new ItemStack(Items.MINECART);
		
		GameRegistry.addShapedRecipe(new ResourceLocation(ModConstants.MODID, "locomotive_electric"), null, electricLocomotive,
			"lp ",
			"scs",
			"gmg",
			'l', invertedCageLamp,
			'p', "plateSteel",
			's', redstoneServo,
			'c', energyCell,
			'g', "gearSteel",
			'm', minecart);
		
		
		//Add recipe for stone ties
		ItemStack rebar = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(RAILCRAFT, "rebar")));
		ItemStack stoneTie = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(RAILCRAFT, "tie")), 1, 1);
		ItemStack slab = new ItemStack(Blocks.STONE_SLAB);
		
		GameRegistry.addShapedRecipe(new ResourceLocation(ModConstants.MODID, "stone_tie"),
			null,
			stoneTie,
			" r ",
			"sss",
			'r', rebar,
			's', slab);
		
	}
	
}
