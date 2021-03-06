package com.ferreusveritas.stargarden.features;

import java.util.ArrayList;

import com.ferreusveritas.stargarden.ModConstants;
import com.ferreusveritas.stargarden.util.Util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class ProjectRed extends BaseFeature {
	
	public static final String PROJECTREDCORE = "projectred-core";
	public static final String PROJECTREDTRANS = "projectred-transmission";
	public static final String PROJECTREDFAB = "projectred-fabrication";
	public static final String PROJECTREDINT = "projectred-integration";
	public static final String PROJECTREDLUM = "projectred-illumination";
	
	public static Item getProjectRedItem(String name) {
		return Item.REGISTRY.getObject(new ResourceLocation(PROJECTREDCORE, name));
	}

	public static Item getProjectRedTransItem(String name) {
		return Item.REGISTRY.getObject(new ResourceLocation(PROJECTREDTRANS, name));
	}
	
	public static Item getProjectRedFabItem(String name) {
		return Item.REGISTRY.getObject(new ResourceLocation(PROJECTREDFAB, name));
	}
	
	public static Item getProjectRedIntItem(String name) {
		return Item.REGISTRY.getObject(new ResourceLocation(PROJECTREDINT, name));
	}
	
	public static Item getProjectRedLumItem(String name) {
		return Item.REGISTRY.getObject(new ResourceLocation(PROJECTREDLUM, name));
	}
	
	public static Item getProjectRedResItem() {
		return Item.REGISTRY.getObject(new ResourceLocation(PROJECTREDCORE, "resource_item"));
	}
	
	public static Item getProjectRedWireItem() {
		return getProjectRedTransItem("wire");
	}
	
	public ArrayList<String> getRemoveOreList() {
		ArrayList<String> list = new ArrayList<String>();
		for(String oreName : new String[]{ "gemRuby", "gemSapphire", "gemPeridot", "ingotCopper", "ingotTin", "ingotSilver" }) {
			list.add(oreName);	
		}
		return list;
	}
	
	public static ArrayList<ItemStack> getRemoveList() {
		
		ArrayList<ItemStack> list = new ArrayList<>();
		
		Item resItem = getProjectRedItem("resource_item");
		Item resPlate = getProjectRedItem("drawplate");
		
		int resMetas[] = {
				100, //Copper Ingot
				101, //Tin Ingot
				102, //Silver Ingot
				103, //Red Alloy Ingot
				104, //Elecrotine Alloy Ingot
				105, //Elecrotine
				200, //Ruby
				201, //Sapphire
				202, //Peridot
				250, //Sandy Coal Compound
				251, //Red Iron Compound
				252, //Elecrotine Iron Compound
				310, //Red Silicon Compound
				311, //Glowing Silicon Compound
				312, //Electro Silicon Compound
				342, //Electro Silicon
				400, //Copper Coil
				401, //Iron Coil
				402, //Gold Coil
				410, //Motor
				420, //Woven Cloth
				421, //Sail
				600  //Null Logic Routing Chip
			};
			
			for(int meta : resMetas) {
				list.add(new ItemStack(resItem, 1, meta));
			}
			
			list.add(new ItemStack(resPlate, 1));
		
		return list;
	}
	
	@Override
	public void postInit() {
		Item resItem = getProjectRedItem("resource_item");
		Item resPlate = getProjectRedItem("drawplate");
		
		resItem.setCreativeTab(null);
		resPlate.setCreativeTab(null);
		
		Thermal.removeFurnaceRecipe(new ItemStack(resItem, 1, 252));//Electrotine Iron Compound
		Thermal.removeFurnaceRecipe(new ItemStack(resItem, 1, 312));//Electrotine Silicon Compound
		Thermal.removeFurnaceRecipe(new ItemStack(resItem, 1, 310));//Red Silicon Compound
		Thermal.removeFurnaceRecipe(new ItemStack(resItem, 1, 311));//Glowing Silicon Compound
		Thermal.removeFurnaceRecipe(new ItemStack(resItem, 1, 250));//Sandy Coal Compound
		Thermal.removeFurnaceRecipe(new ItemStack(resItem, 1, 251));//Red Iron Compound
		
		Vanilla.removeSmelterRecipe(new ItemStack(resItem, 1, 104));//Electrotine Alloy Ingot
		Vanilla.removeSmelterRecipe(new ItemStack(resItem, 1, 342));//Electro Silicon
		Vanilla.removeSmelterRecipe(new ItemStack(resItem, 1, 320));//Infused Silicon
		Vanilla.removeSmelterRecipe(new ItemStack(resItem, 1, 341));//Energized Silicon
		Vanilla.removeSmelterRecipe(new ItemStack(resItem, 1, 300));//Silicon Boule
		Vanilla.removeSmelterRecipe(new ItemStack(resItem, 1, 103));//Red Alloy Ingot
		
		//These materials no longer have any use
		Vanilla.removeRecipe(PROJECTREDCORE + ":resource/electrotine_silicon_compound");
		Vanilla.removeRecipe(PROJECTREDCORE + ":resource/electrotine_iron_compound");
		Vanilla.removeRecipe(PROJECTREDCORE + ":misc/iron_coil");
		Vanilla.removeRecipe(PROJECTREDCORE + ":misc/gold_coil");
		Vanilla.removeRecipe(PROJECTREDCORE + ":misc/copper_coil");
		Vanilla.removeRecipe(PROJECTREDCORE + ":misc/motor");
		Vanilla.removeRecipe(PROJECTREDCORE + ":tools/draw_plate");
		Vanilla.removeRecipe(PROJECTREDTRANS + ":power_low_load");
		Vanilla.removeRecipe(PROJECTREDTRANS + ":framed/34_framed");
		Vanilla.removeRecipe(PROJECTREDCORE + ":misc/sail");
		Vanilla.removeRecipe(PROJECTREDCORE + ":misc/woven_cloth");
		
		//Remove insulated wire recipes
		for(EnumDyeColor color : EnumDyeColor.values()) {
			String name = Vanilla.getDyeName(color);
			name = "silver".equals(name) ? "light_gray" : name;
			Vanilla.removeRecipe(PROJECTREDTRANS + ":insulated/" + name + "_insulated_wire");
		}
		
		//These will be replaced with Induction Smelter Recipes
		Vanilla.removeRecipe(PROJECTREDCORE + ":resource/red_iron_compound");
		Vanilla.removeRecipe(PROJECTREDCORE + ":resource/sandy_coal_compound");
		Vanilla.removeRecipe(PROJECTREDCORE + ":resource/red_silicon_compound");
		Vanilla.removeRecipe(PROJECTREDCORE + ":resource/glowing_silicon_compound");
		
		Vanilla.removeRecipe(PROJECTREDTRANS + ":red_alloy_wire");
		Vanilla.removeRecipe(PROJECTREDCORE + ":tools/multimeter");
		
		//Remove subItems from Project Red
		CreativeTabs projectRedCoreTab = Util.findCreativeTab("projectred.core");
		
		for(ItemStack stack : getRemoveList()) {
			Vanilla.addContraband(stack, projectRedCoreTab);
		}
		
		//Remove ore dictionary references
		for(String oreName : getRemoveOreList()) {
			NonNullList<ItemStack> oreList = OreDictionary.getOres(oreName);
			for(ItemStack stack : oreList ) {
				if(stack.getItem().equals(resItem)) {
					Vanilla.removeOre(stack, oreName);
					break;
				}
			}
		}
		
	}
	
	@Override
	public void registerRecipes(IForgeRegistry<IRecipe> registry) {
		
		Item resItem = getProjectRedResItem();
		Item wireItem = getProjectRedWireItem();
		
		//ItemStack silverIngot = new ItemStack(Thermal.getThermalFoundationItem("material"), 1, 130);
		ItemStack siliconWafer = new ItemStack(resItem, 1, 301);
		
		Thermal.addSmelterRecipe(8000, new ItemStack(Blocks.COAL_BLOCK), new ItemStack(Blocks.SAND, 8), new ItemStack(resItem, 1, 300), ItemStack.EMPTY, 0);//Silicon Boule
		Thermal.addSmelterRecipe(4000, siliconWafer, new ItemStack(Items.REDSTONE, 4), new ItemStack(resItem, 1, 320), ItemStack.EMPTY, 0);//Infused Silicon
		Thermal.addSmelterRecipe(4000, siliconWafer, new ItemStack(Items.GLOWSTONE_DUST, 4), new ItemStack(resItem, 1, 341), ItemStack.EMPTY, 0);//Energized Silicon
		//Thermal.addSmelterRecipe(2000, silverIngot, new ItemStack(Items.REDSTONE, 4), new ItemStack(resItem, 1, 103), ItemStack.EMPTY, 0);//Red Alloy Ingot
		
		GameRegistry.addShapedRecipe(new ResourceLocation(ModConstants.MODID, "signalum_wire"), null,
			new ItemStack(wireItem, 16, 0),
			"x", "x", "x", 'x',
			new ItemStack(Thermal.getThermalFoundationMaterial(), 1, 165)
		);

		Ingredient oneWire = Ingredient.fromStacks(new ItemStack(wireItem, 1, 0));
		
		for(EnumDyeColor color : EnumDyeColor.values()) {
			GameRegistry.addShapelessRecipe(new ResourceLocation(ModConstants.MODID, "insulated_wire_" + Vanilla.getDyeName(color)), null,
				new ItemStack(wireItem, 3, color.getMetadata() + 1),
				oneWire,
				oneWire,
				oneWire,
				Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, color.getMetadata()))
			);
		}
		
		GameRegistry.addShapedRecipe(new ResourceLocation(ModConstants.MODID, "multimeter"), null,
			new ItemStack(getProjectRedItem("multimeter")),
			"w w",
			"bgr",
			"blr",
			'w', oneWire,
			'b', "dyeBlack",
			'g', "dyeGreen",
			'r', "dyeRed",
			'l', new ItemStack(Items.GLOWSTONE_DUST)
		);
		
	}
	
	@Override
	public void onLoadComplete() {
		Item resPlate = Item.REGISTRY.getObject(new ResourceLocation(PROJECTREDCORE, "drawplate"));
		Vanilla.removeItemStackFromJEI(new ItemStack(resPlate, 1, 0));
	}
	
}
