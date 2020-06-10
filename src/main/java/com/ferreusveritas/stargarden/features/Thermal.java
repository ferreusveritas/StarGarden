package com.ferreusveritas.stargarden.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import com.ferreusveritas.stargarden.ModConstants;
import com.ferreusveritas.stargarden.util.RecipeHelper;
import com.ferreusveritas.stargarden.util.Util;

import cofh.core.init.CoreProps;
import cofh.core.item.ItemMulti;
import cofh.thermalexpansion.ThermalExpansion;
import cofh.thermalexpansion.item.ItemMorb;
import cofh.thermalexpansion.util.managers.machine.CentrifugeManager;
import cofh.thermalexpansion.util.managers.machine.CompactorManager;
import cofh.thermalexpansion.util.managers.machine.CompactorManager.Mode;
import cofh.thermalexpansion.util.managers.machine.FurnaceManager;
import cofh.thermalexpansion.util.managers.machine.PulverizerManager;
import cofh.thermalexpansion.util.managers.machine.SmelterManager;
import cofh.thermalexpansion.util.managers.machine.TransposerManager;
import cofh.thermalfoundation.init.TFFluids;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;

public class Thermal extends BaseFeature {

	public static final String BIOMESOPLENTY = "biomesoplenty";
	public static final String QUARK = "quark";
	public static final String THERMALEXPANSION = "thermalexpansion";
	public static final String THERMALFOUNDATION = "thermalfoundation";
	public static final String THERMALDYNAMICS = "thermaldynamics";

	public static Item getThermalExpansionItem(String name) {
		return Item.REGISTRY.getObject(new ResourceLocation(THERMALEXPANSION, name));
	}

	public static Item getThermalFoundationItem(String name) {
		return Item.REGISTRY.getObject(new ResourceLocation(THERMALFOUNDATION, name));
	}

	public static Item getThermalDynamicsItem(String name) {
		return Item.REGISTRY.getObject(new ResourceLocation(THERMALDYNAMICS, name));
	}

	public static Item getThermalFoundationMaterial() {
		return getThermalFoundationItem("material");
	}

	public static void removeFurnaceRecipe(ItemStack input) {

		if (input.isEmpty()) {
			return;
		}

		FurnaceManager.removeRecipe(input);
	}

	private static void removeCompactorMintRecipe(ItemStack input) {

		if (input.isEmpty()) {
			return;
		}

		CompactorManager.removeRecipe(input, CompactorManager.Mode.COIN);
	}

	public static void addSmelterRecipe(int energy, ItemStack primaryInput, ItemStack secondaryInput, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {

		if (primaryInput.isEmpty() || secondaryInput.isEmpty() || primaryOutput.isEmpty()) {
			return;
		}

		SmelterManager.addRecipe(energy, primaryInput, secondaryInput, primaryOutput, secondaryOutput, secondaryChance);

	}

	public static ArrayList<ItemStack> getMintRemoveList() {
		ArrayList<ItemStack> mintRemoveList = new ArrayList<ItemStack>();

		mintRemoveList.add(new ItemStack(Blocks.IRON_BLOCK));
		mintRemoveList.add(new ItemStack(Items.IRON_INGOT));
		mintRemoveList.add(new ItemStack(Items.IRON_NUGGET));

		mintRemoveList.add(new ItemStack(Blocks.GOLD_BLOCK));
		mintRemoveList.add(new ItemStack(Items.GOLD_INGOT));
		mintRemoveList.add(new ItemStack(Items.GOLD_NUGGET));

		for(int i = 0; i <= 9; i++) { //Copper, Tin, Silver, Lead, Aluminum, Nickle, Platinum, Iridium, Mana Infused
			mintRemoveList.add(new ItemStack(getThermalFoundationItem("storage"), 1, i));//Blocks
			mintRemoveList.add(new ItemStack(getThermalFoundationMaterial(), 1, i + 128));//Ingots
			mintRemoveList.add(new ItemStack(getThermalFoundationMaterial(), 1, i + 192));//Nuggets
		}

		for(int i = 0; i <= 8; i++) { //Steel, Electrum, Invar, Bronze, Constantan, Signalum, Lumium, Enderium
			mintRemoveList.add(new ItemStack(getThermalFoundationItem("storage_alloy"), 1, i));//Blocks
			mintRemoveList.add(new ItemStack(getThermalFoundationMaterial(), 1, i + 160));//Ingots
			mintRemoveList.add(new ItemStack(getThermalFoundationMaterial(), 1, i + 224));//Nuggets
		}

		return mintRemoveList;
	}
	
	public static ArrayList<ItemStack> safeDyesList = null;
	
	public static ArrayList<ItemStack> getSafeDyesList() {
		if(safeDyesList == null) {
			ArrayList<ItemStack> safeDyes = new ArrayList<ItemStack>();
			
			safeDyes.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(BIOMESOPLENTY, "black_dye"))));//Black
			safeDyes.add(new ItemStack(Items.DYE, 1, 1));//Red
			safeDyes.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(BIOMESOPLENTY, "green_dye"))));//Green
			safeDyes.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(BIOMESOPLENTY, "brown_dye"))));//Brown
			safeDyes.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(BIOMESOPLENTY, "blue_dye"))));//Blue
			safeDyes.add(new ItemStack(Items.DYE, 1, 5));//Purple
			safeDyes.add(new ItemStack(Items.DYE, 1, 6));//Cyan
			safeDyes.add(new ItemStack(Items.DYE, 1, 7));//Light Gray
			safeDyes.add(new ItemStack(Items.DYE, 1, 8));//Gray
			safeDyes.add(new ItemStack(Items.DYE, 1, 9));//Pink
			safeDyes.add(new ItemStack(Items.DYE, 1, 10));//Lime
			safeDyes.add(new ItemStack(Items.DYE, 1, 11));//Yellow
			safeDyes.add(new ItemStack(Items.DYE, 1, 12));//Light Blue
			safeDyes.add(new ItemStack(Items.DYE, 1, 13));//Magenta
			safeDyes.add(new ItemStack(Items.DYE, 1, 14));//Orange Dye
			safeDyes.add(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(BIOMESOPLENTY, "white_dye"))));//White
			
			safeDyesList = safeDyes;
		}
		
		return safeDyesList;
	}
	
	public static ArrayList<ItemStack> getPulverizerRemoveList() {
		ArrayList<ItemStack> pulverizerRemoveList = new ArrayList<ItemStack>();

		Item quiltedWool = Item.REGISTRY.getObject(new ResourceLocation(QUARK, "quilted_wool"));

		for(EnumDyeColor color : EnumDyeColor.values()) {
			pulverizerRemoveList.add(new ItemStack(Blocks.WOOL, 1, color.getMetadata()));
			pulverizerRemoveList.add(new ItemStack(quiltedWool, 1, color.getMetadata()));
			pulverizerRemoveList.add(new ItemStack(Block.REGISTRY.getObject(new ResourceLocation(QUARK, "colored_flowerpot_" + color.getName()))));
		}

		pulverizerRemoveList.add(new ItemStack(Blocks.RED_FLOWER, 1, 6));

		return pulverizerRemoveList;
	}

	public static ArrayList<ItemStack> getJeiRemoveList() {
		ArrayList<ItemStack> jeiRemoveList = new ArrayList<ItemStack>();
		jeiRemoveList.add(new ItemStack(getThermalFoundationItem("coin"), 1, 0));
		jeiRemoveList.add(new ItemStack(getThermalExpansionItem("dynamo"), 1, 5));
		jeiRemoveList.add(new ItemStack(getThermalExpansionItem("augment"), 1, 336));
		jeiRemoveList.add(new ItemStack(getThermalExpansionItem("augment"), 1, 720));
		jeiRemoveList.add(new ItemStack(getThermalExpansionItem("morb")));
		return jeiRemoveList;
	}

	public static ArrayList<String> getRecipeRemoveList() {
		ArrayList<String> recipesRemoveList = new ArrayList<String>();
		recipesRemoveList.add(THERMALFOUNDATION + ":dynamo_5");//Remove the recipe for the stupid Numismatic Dynamo
		recipesRemoveList.add(THERMALEXPANSION + ":augment_13");//Numismatic Press
		recipesRemoveList.add(THERMALEXPANSION + ":augment_39");//Lapidary Calibration

		IntStream.rangeClosed(5, 9).forEach(i -> recipesRemoveList.add(THERMALEXPANSION + ":capacitor_" + i));//Capacitor Coloring
		IntStream.rangeClosed(5, 9).forEach(i -> recipesRemoveList.add(THERMALEXPANSION + ":reservoir_" + i));//Reservoir Coloring
		IntStream.rangeClosed(13, 22).forEach(i -> recipesRemoveList.add(THERMALEXPANSION + ":satchel_" + i));//Satchel Coloring
		IntStream.rangeClosed(0, 15).forEach(i -> recipesRemoveList.add(THERMALFOUNDATION + ":rockwool" +  (i != 0 ? ("_" + i) : "")));//Rockwool Coloring

		return recipesRemoveList;
	}

	@Override
	public void preInit() {
		ItemMorb.enable = false;
	}

	@Override
	public void init() {
		ThermalExpansion.CONFIG.set("Device.Tapper", "Enable", false);
		ThermalExpansion.CONFIG.set("Device.MobCatcher", "Enable", false);
		ThermalExpansion.CONFIG.set("Dynamo.Numismatic", "Enable", false);
		ThermalExpansion.CONFIG.save();

		//getThermalDynamicsItem("cover").setCreativeTab(null);

		disableMorbs();
	}

	private void disableMorbs() {
		ArrayList<String> list = new ArrayList<>();

		for (ResourceLocation name : EntityList.getEntityNameList()) {
			Class<? extends Entity> clazz = EntityList.getClass(name);
			if (clazz == null || !EntityLiving.class.isAssignableFrom(clazz)) {
				continue;
			}
			if (EntityList.ENTITY_EGGS.containsKey(name)) {
				list.add(name.toString());
				continue;
			}
		}

		getThermalExpansionItem("morb").setCreativeTab(null);

		ItemMorb.blacklist = list.toArray(new String[0]);
		ItemMorb.enable = false;
	}

	@Override
	public void postInit() {

		//int coinMetas[] = { 0, 1, 64, 65, 66, 67, 68, 69, 70, 71, 72, 96, 97, 98, 99, 100, 101, 102, 103 };
		Item coin = getThermalFoundationItem("coin");
		coin.setCreativeTab(null);//Remove the coin from the creative tabs

		Item pigment = getThermalFoundationItem("dye");
		pigment.setCreativeTab(null);

		getRecipeRemoveList().forEach(Vanilla::removeRecipe);
		getPulverizerRemoveList().forEach(PulverizerManager::removeRecipe);
		getMintRemoveList().forEach(Thermal::removeCompactorMintRecipe);

		for(EnumDyeColor c: EnumDyeColor.values()) {
			//Remove Thermal Expansion Pigments
			Vanilla.removeOre(new ItemStack(pigment, 1, c.getMetadata()), null);
			//Remove concrete powder recipes from Centrifugal Separator
			CentrifugeManager.removeRecipe(new ItemStack(Blocks.CONCRETE_POWDER, 2, c.getMetadata()));
		}

		Item quiltedWool = Item.REGISTRY.getObject(new ResourceLocation(QUARK, "quilted_wool"));
		ItemStack sand = new ItemStack(Blocks.SAND);
		ItemStack gravel = new ItemStack(Blocks.GRAVEL);

		int meta = 15;

		ArrayList<ItemStack> safeDyesList = getSafeDyesList();

		//Recreate pulverizer recipes to produce safe dyes
		for(ItemStack dye: safeDyesList) {
			PulverizerManager.addRecipe(3000, new ItemStack(Blocks.WOOL, 1, meta), new ItemStack(Items.STRING, 4), dye, 15);
			PulverizerManager.addRecipe(3000, new ItemStack(quiltedWool, 1, meta), new ItemStack(Items.STRING, 4), dye, 15);
			CentrifugeManager.addRecipe(2000, new ItemStack(Blocks.CONCRETE_POWDER, 2, meta), Arrays.asList(sand, gravel, dye), Arrays.asList(100, 100, 10), null);
			meta--;
		}

		//Create pulverizer recipes for vanilla to safe dyes
		for(EnumDyeColor color : new EnumDyeColor[] { EnumDyeColor.BLACK, EnumDyeColor.GREEN, EnumDyeColor.BROWN, EnumDyeColor.BLUE }) {
			ItemStack safeDye = safeDyesList.get(color.getDyeDamage()).copy();
			safeDye.setCount(4);
			PulverizerManager.addRecipe(2000, new ItemStack(Items.DYE, 1, color.getDyeDamage()), safeDye);
		}

		//Make white tulip produce white dye
		ItemStack whiteDye = safeDyesList.get(EnumDyeColor.WHITE.getDyeDamage()).copy();
		whiteDye.setCount(4);
		PulverizerManager.addRecipe(2000, new ItemStack(Blocks.RED_FLOWER, 1, 6), whiteDye);

		//Force options to disable Numismatic Dynamo
		cofh.thermalexpansion.block.dynamo.BlockDynamo.enable[5] = false;


		//Allow filling ash blocks with XPJuice to produce netherrack
		Block ashBlock = Block.REGISTRY.getObject(new ResourceLocation(BIOMESOPLENTY, "ash_block"));

		if(ashBlock != null && ashBlock != Blocks.AIR) {

			List<Fluid> xpFluids = new ArrayList<Fluid>();

			xpFluids.add(TFFluids.fluidExperience);
			if (FluidRegistry.isFluidRegistered(CoreProps.ESSENCE)) {
				xpFluids.add(FluidRegistry.getFluid(CoreProps.ESSENCE));
			}

			if (FluidRegistry.isFluidRegistered(CoreProps.XPJUICE)) {
				xpFluids.add(FluidRegistry.getFluid(CoreProps.XPJUICE));
			}

			xpFluids.forEach(f -> TransposerManager.addFillRecipe(16000, new ItemStack(ashBlock), new ItemStack(Blocks.NETHERRACK), new FluidStack(f, 1000), false));
		}


		//Allow grinding a wither skull into black_ash
		Block blackAsh = Block.REGISTRY.getObject(new ResourceLocation(QUARK, "black_ash"));

		if(blackAsh != null && blackAsh != Blocks.AIR) {
			Item material = getThermalFoundationMaterial();
			ItemStack sulfur = new ItemStack(material, 1, 771);
			PulverizerManager.addRecipe(8000, new ItemStack(Items.SKULL, 1, 1), new ItemStack(blackAsh, 3), sulfur, 25);
		}

		ItemStack coalPowder = new ItemStack(getThermalFoundationMaterial(), 1, 768);
		ItemStack charcoalPowder = new ItemStack(getThermalFoundationMaterial(), 1, 769);
		ItemStack sulfurPowder = new ItemStack(getThermalFoundationMaterial(), 1, 771);
		ItemStack niterPowder = new ItemStack(getThermalFoundationMaterial(), 1, 772);
		
		//Turn gunpowder into it's component parts with the centrifuge
		List gunPowderComponents = new ArrayList<ItemStack>();
		gunPowderComponents.add(charcoalPowder);
		gunPowderComponents.add(sulfurPowder);
		gunPowderComponents.add(niterPowder);
		gunPowderComponents.add(niterPowder);

		CentrifugeManager.addRecipe(16000, new ItemStack(Items.GUNPOWDER), gunPowderComponents, null);

		//Turn 4 charcoal powder into coal powder
		ItemStack charcoalPowder4 = charcoalPowder.copy();
		charcoalPowder4.setCount(4);
		CompactorManager.addRecipe(16000, charcoalPowder4, coalPowder, Mode.ALL);
		
	}

	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {

		//Recreate rockwool recipes to respect Ore Dictionary Dyes
		for(EnumDyeColor color : EnumDyeColor.values()) {
			GameRegistry.addShapelessRecipe(
					new ResourceLocation(ModConstants.MODID, "coloredRockwool_" + color.getName()),//Name
					null,//Group
					new ItemStack(getThermalFoundationItem("rockwool"), 1, color.getDyeDamage()),//Output
					new Ingredient[] {
							new OreIngredient("blockRockwool"),
							new OreIngredient("dye" + Vanilla.dyeValues[color.getDyeDamage()])
					}
					);
		}

		Block blackAsh = Block.REGISTRY.getObject(new ResourceLocation(QUARK, "black_ash"));
		Block ashBlock = Block.REGISTRY.getObject(new ResourceLocation(BIOMESOPLENTY, "ash_block"));

		if(blackAsh != null && blackAsh != Blocks.AIR) {
			//Allow Quark wither ash to become a BoP ash_block
			if(ashBlock != null && ashBlock != Blocks.AIR) {
				GameRegistry.addShapedRecipe(
						new ResourceLocation(ModConstants.MODID, "ash_block_from_wither_ash"),
						null,
						new ItemStack(ashBlock),
						"xx", "xx", 'x', new ItemStack(blackAsh)
						);
			}

			//Allow Quark wither ash to become vanilla coal
			GameRegistry.addShapedRecipe(
					new ResourceLocation(ModConstants.MODID, "coal_from_ash"),
					null,
					new ItemStack(Items.COAL),
					"xxx", "xxx", "xxx", 'x', new ItemStack(blackAsh)
					);

		}

		Item material = getThermalFoundationMaterial();
		ItemStack pyrotheum = new ItemStack(material, 1, 1024);
		ItemStack cryotheum = new ItemStack(material, 1, 1025);
		ItemStack pulvObsidian = new ItemStack(material, 1, 770);

		GameRegistry.addShapelessRecipe(
				new ResourceLocation(ModConstants.MODID, "pulv_obsidian"),
				null,
				pulvObsidian,
				new Ingredient[] {
						Ingredient.fromStacks(pyrotheum),
						Ingredient.fromStacks(cryotheum)
				}
				);

		
		//Re-add color recipes for Satchels
		Item satchel = getThermalExpansionItem("satchel");
		ItemStack satchelBasic = new ItemStack(satchel, 1, 0);
		ItemStack satchelHardened = new ItemStack(satchel, 1, 1);
		ItemStack satchelReinforced = new ItemStack(satchel, 1, 2);
		ItemStack satchelSignalum = new ItemStack(satchel, 1, 3);
		ItemStack satchelResonant = new ItemStack(satchel, 1, 4);
		
		for(ItemStack safeDye : getSafeDyesList()) {
			OreDictionary.registerOre("safedye", safeDye);
		}
		
		RecipeHelper.addColorRecipe(satchelBasic, "single_safedye", satchelBasic, "safedye");
		RecipeHelper.addColorRecipe(satchelHardened, "single_safedye", satchelHardened, "safedye");
		RecipeHelper.addColorRecipe(satchelReinforced, "single_safedye", satchelReinforced, "safedye");
		RecipeHelper.addColorRecipe(satchelSignalum, "single_safedye", satchelSignalum, "safedye");
		RecipeHelper.addColorRecipe(satchelResonant, "single_safedye", satchelResonant, "safedye");
		
		RecipeHelper.addColorRecipe(satchelBasic, "double_safedye", satchelBasic, "safedye", "safedye");
		RecipeHelper.addColorRecipe(satchelHardened, "double_safedye", satchelHardened, "safedye", "safedye");
		RecipeHelper.addColorRecipe(satchelReinforced, "double_safedye", satchelReinforced, "safedye", "safedye");
		RecipeHelper.addColorRecipe(satchelSignalum, "double_safedye", satchelSignalum, "safedye", "safedye");
		RecipeHelper.addColorRecipe(satchelResonant, "double_safedye", satchelResonant, "safedye", "safedye");
		
	}

	@Override
	public void onLoadComplete() {
		getJeiRemoveList().forEach(i -> Vanilla.removeItemStackFromJEI(i));

		//Dirty hack to remove Numismatic Press and Lapidary Calibration from the creative tabs
		ItemMulti augment = (ItemMulti) getThermalExpansionItem("augment");
		ArrayList<Integer> list = (ArrayList<Integer>) Util.getRestrictedObject(ItemMulti.class, augment, "itemList");
		for(int meta : new int[] {336, 720}) {//meta for Numismatic Press and Lapidary Calibration respectively
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i) == meta) {
					list.remove(i);
					break;
				}
			}
		}
	}

}
