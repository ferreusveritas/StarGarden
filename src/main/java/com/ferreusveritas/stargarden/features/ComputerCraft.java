package com.ferreusveritas.stargarden.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import com.ferreusveritas.stargarden.ModConstants;
import com.ferreusveritas.stargarden.util.Util;

import dan200.computercraft.shared.media.items.ItemDiskExpanded;
import dan200.computercraft.shared.media.items.ItemDiskLegacy;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.registries.IForgeRegistry;

public class ComputerCraft implements IFeature {
	
	public static final String COMPUTERCRAFT = "computercraft";
	
	public static Item getComputerCraftItem(String name) {
		return Item.REGISTRY.getObject(new ResourceLocation(COMPUTERCRAFT, name));
	}
	
	public static ArrayList<ResourceLocation> getRemoveRecipeList() {
		ArrayList<ResourceLocation> list = new ArrayList<>();
		
		Arrays.asList("disk_impostor", "normal_computer", "advanced_computer", "normal_monitor", "advanced_monitor",
				"normal_turtle",  "advanced_turtle", "cable", "wired_modem", "wireless_modem", "ender_modem", 
				"normal_pocket_computer",  "advanced_pocket_computer", "disk_drive", "printer",
				"advanced_computer_upgrade", "advanced_pocket_computer_upgrade", "advanced_turtle_upgrade"
				)
		.forEach(name -> list.add(new ResourceLocation(COMPUTERCRAFT, name)));

		//Remove oreDict insensitive recipes
		IntStream.range(0, 16).forEach(i -> list.add(new ResourceLocation(COMPUTERCRAFT, "disk_imposter_" + i)));
		IntStream.range(0, 16).forEach(i -> list.add(new ResourceLocation(COMPUTERCRAFT, "disk_imposter_convert_" + i)));
		
		return list;
	}
	
	@Override
	public void preInit() { }
	
	@Override
	public void createBlocks() { }

	@Override
	public void createItems() { }

	@Override
	public void registerEvents() { }
	
	@Override
	public void init() { }
	
	@Override
	public void postInit() {
		
		getComputerCraftItem("disk").setCreativeTab(null);
		getComputerCraftItem("disk_expanded").setCreativeTab(null);
		
		//Remove Recipes
		getRemoveRecipeList().forEach(Vanilla::removeRecipe);
		
		//Remove subItems from Project Red
		CreativeTabs computerCraftTab = Util.findCreativeTab("ComputerCraft");

		//Add colored disks to creative tabs
		Arrays.asList(EnumDyeColor.values()).forEach(color -> Vanilla.addItem(ItemDiskLegacy.createFromIDAndColour( -1, null, getColorValue(color)), computerCraftTab));
	}
	
	@Override
	public void onLoadComplete() { 
		Vanilla.removeItemStackFromJEI(new ItemStack(getComputerCraftItem("disk")));
	}
	
	public void addColoredFloppyRecipe(EnumDyeColor color) {
		
		ItemStack output = ItemDiskExpanded.createFromIDAndColour(-1, null, getColorValue(color));
		
		GameRegistry.addShapelessRecipe(
			new ResourceLocation(ModConstants.MODID, "coloredFloppy_" + color.getName()),//Name
			null,//Group
			output,//Output
			new Ingredient[] {
				Ingredient.fromStacks(new ItemStack(Items.PAPER)),
				Ingredient.fromStacks(new ItemStack(Items.REDSTONE)),
				new OreIngredient("dye" + Vanilla.dyeValues[color.getDyeDamage()])
			}
		);
	}
	
	public int getColorValue(EnumDyeColor from) {
		return (int) Util.getRestrictedObject(EnumDyeColor.class, from, "field_193351_w", "colorValue");
	}
	
	@Override
	public void registerBlocks(IForgeRegistry<Block> event) { }
	
	@Override
	public void registerItems(IForgeRegistry<Item> registry) { }
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		for(EnumDyeColor color : EnumDyeColor.values()) {
			addColoredFloppyRecipe(color);
		}
		
		ItemStack steelPlate = new ItemStack(Thermal.getThermalFoundationMaterial(), 1, 352);
		ItemStack electrumPlate = new ItemStack(Thermal.getThermalFoundationMaterial(), 1, 353);
		ItemStack hardenedCapacitor = new ItemStack(Thermal.getThermalExpansionItem("capacitor"), 1, 1);
		ItemStack reinforcedCapacitor = new ItemStack(Thermal.getThermalExpansionItem("capacitor"), 1, 2);
		ItemStack icChip = new ItemStack(ProjectRed.getProjectRedFabItem("ic_chip"));
		ItemStack busConvertor = new ItemStack(ProjectRed.getProjectRedIntItem("gate"), 1, 29);
		ItemStack deviceFrame = new ItemStack(Thermal.getThermalExpansionItem("frame"), 1, 64);
		ItemStack glassBlock = new ItemStack(Blocks.GLASS);
		ItemStack redstoneLamp = new ItemStack(Blocks.REDSTONE_LAMP);
		ItemStack redstoneServo = new ItemStack(Thermal.getThermalFoundationMaterial(), 1, 512);
		ItemStack steamDynamo = new ItemStack(Thermal.getThermalExpansionItem("dynamo"), 1, 0);
		ItemStack steelGear = new ItemStack(Thermal.getThermalFoundationMaterial(), 1, 288);
		ItemStack electrumGear = new ItemStack(Thermal.getThermalFoundationMaterial(), 1, 289);
		ItemStack cache = new ItemStack(Thermal.getThermalExpansionItem("cache"));
		ItemStack signalumIngot = new ItemStack(Thermal.getThermalFoundationMaterial(), 1, 165);
		ItemStack quartz = new ItemStack(Items.QUARTZ);
		ItemStack anode = new ItemStack(ProjectRed.getProjectRedResItem(), 1, 10);
		ItemStack conductivePlate = new ItemStack(ProjectRed.getProjectRedResItem(), 1, 1);
		ItemStack energizedSiliconChip = new ItemStack(ProjectRed.getProjectRedResItem(), 1, 21);
		ItemStack endRod = new ItemStack(Blocks.END_ROD);
		
		ItemStack normalComputer = new ItemStack(getComputerCraftItem("computer"), 1, 0);
		ItemStack advancedComputer = new ItemStack(getComputerCraftItem("computer"), 1, 0x4000);
		ItemStack networkCable = new ItemStack(getComputerCraftItem("cable"), 16, 0);
		ItemStack wiredModem = new ItemStack(getComputerCraftItem("cable"), 1, 1);
		ItemStack wirelessModem = new ItemStack(getComputerCraftItem("peripheral"), 1, 1);
		ItemStack advancedModem = new ItemStack(getComputerCraftItem("advanced_modem"));
		
		GameRegistry.addShapedRecipe(
			new ResourceLocation(ModConstants.MODID, "normal_computer"),
			null,
			normalComputer,
			"pcp",
			"pfp",
			"pxp",
			'p', steelPlate,
			'c', icChip,
			'f', deviceFrame,
			'x', hardenedCapacitor
		);
		
		GameRegistry.addShapedRecipe(
			new ResourceLocation(ModConstants.MODID, "advanced_computer"),
			null,
			advancedComputer,
			"pcp",
			"pfp",
			"pxp",
			'p', electrumPlate,
			'c', icChip,
			'f', deviceFrame,
			'x', reinforcedCapacitor
		);
		
		GameRegistry.addShapedRecipe(
			new ResourceLocation(ModConstants.MODID, "normal_monitor"),
			null,
			new ItemStack(getComputerCraftItem("peripheral"), 1, 2),
			"pcp",
			"pfp",
			"ggg",
			'p', steelPlate,
			'c', redstoneLamp,
			'f', deviceFrame,
			'g', glassBlock
		);
		
		GameRegistry.addShapedRecipe(
			new ResourceLocation(ModConstants.MODID, "advanced_monitor"),
			null,
			new ItemStack(getComputerCraftItem("peripheral"), 1, 4),
			"pcp",
			"pfp",
			"rgb",
			'p', electrumPlate,
			'c', busConvertor,
			'f', deviceFrame,
			'r', new ItemStack(ProjectRed.getProjectRedLumItem("lamp"), 1, 14),
			'g', new ItemStack(ProjectRed.getProjectRedLumItem("lamp"), 1, 13),
			'b', new ItemStack(ProjectRed.getProjectRedLumItem("lamp"), 1, 11)
		);
		
		GameRegistry.addShapedRecipe(
			new ResourceLocation(ModConstants.MODID, "normal_turtle"),
			null,
			new ItemStack(getComputerCraftItem("turtle")),
			"scs",
			"gfg",
			"sds",
			's', redstoneServo,
			'g', steelGear,
			'c', cache,
			'f', normalComputer,
			'd', steamDynamo
		);
		
		GameRegistry.addShapedRecipe(
			new ResourceLocation(ModConstants.MODID, "advanced_turtle"),
			null,
			new ItemStack(getComputerCraftItem("turtle_advanced")),
			"scs",
			"gfg",
			"sds",
			's', redstoneServo,
			'g', electrumGear,
			'c', cache,
			'f', advancedComputer,
			'd', steamDynamo
		);
		
		GameRegistry.addShapedRecipe(
			new ResourceLocation(ModConstants.MODID, "wireless_modem"),
			null,
			wirelessModem,
			"ppp",
			"qsq",
			"aea",
			'p', steelPlate,
			'q', quartz,
			's', signalumIngot,
			'a', anode,
			'e', energizedSiliconChip
		);
		
		GameRegistry.addShapedRecipe(
			new ResourceLocation(ModConstants.MODID, "advanced_modem"),
			null,
			advancedModem,
			"ppp",
			"qsq",
			"aea",
			'p', electrumPlate,
			'q', endRod,
			's', signalumIngot,
			'a', anode,
			'e', energizedSiliconChip
		);
		
		GameRegistry.addShapedRecipe(
			new ResourceLocation(ModConstants.MODID, "wired_modem"),
			null,
			wiredModem,
			"ppp",
			"csc",
			"aea",
			'p', electrumPlate,
			'c', conductivePlate,
			's', signalumIngot,
			'a', anode,
			'e', energizedSiliconChip
		);
		
		GameRegistry.addShapedRecipe(
			new ResourceLocation(ModConstants.MODID, "cable"),
			null,
			networkCable,
			"wiw",
			"wiw",
			"wiw",
			'w', new ItemStack(Blocks.WOOL, 1, 8),
			'i', signalumIngot
		);
		
		GameRegistry.addShapedRecipe(
			new ResourceLocation(ModConstants.MODID, "normal_pocket_computer"),
			null,
			new ItemStack(getComputerCraftItem("pocket_computer"), 1, 0),
			"pgp",
			"pcp",
			" f ",
			'p', steelPlate,
			'g', glassBlock,
			'c', icChip,
			'f', hardenedCapacitor
		);
		
		GameRegistry.addShapedRecipe(
			new ResourceLocation(ModConstants.MODID, "advanced_pocket_computer"),
			null,
			new ItemStack(getComputerCraftItem("pocket_computer"), 1, 1),
			"pgp",
			"pcp",
			" f ",
			'p', electrumPlate,
			'g', glassBlock,
			'c', icChip,
			'f', reinforcedCapacitor
		);
		
		GameRegistry.addShapedRecipe(
			new ResourceLocation(ModConstants.MODID, "disk_drive"),
			null,
			new ItemStack(getComputerCraftItem("peripheral"), 1, 0),
			"psp",
			"gfg",
			"prp",
			'p', steelPlate,
			's', signalumIngot,
			'g', steelGear,
			'f', deviceFrame,
			'r', redstoneServo
		);
		
		GameRegistry.addShapedRecipe(
			new ResourceLocation(ModConstants.MODID, "printer"),
			null,
			new ItemStack(getComputerCraftItem("peripheral"), 1, 3),
			"123",
			"gfg",
			"prp",
			'1', new ItemStack(Items.DYE, 1, 6),
			'2', new ItemStack(Items.DYE, 1, 13),
			'3', new ItemStack(Items.DYE, 1, 11),
			'p', steelPlate,
			'g', steelGear,
			'f', deviceFrame,
			'r', redstoneServo
		);
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() { }
	
}
