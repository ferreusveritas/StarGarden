package com.ferreusveritas.stargarden;

import java.util.ArrayList;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class ProjectRed implements IFeature {
	
	public ArrayList<String> getRemoveOreList() {
		ArrayList<String> list = new ArrayList<String>();
		for(String oreName : new String[]{ "gemRuby", "gemSapphire", "gemPeridot", "ingotCopper", "ingotTin", "ingotSilver" }) {
			list.add(oreName);	
		}
		return list;
	}
	
	public static ArrayList<ItemStack> getRemoveList() {
		
		ArrayList<ItemStack> list = new ArrayList<>();
		
		Item resItem = Item.REGISTRY.getObject(new ResourceLocation("projectred-core:resource_item"));
		Item resPlate = Item.REGISTRY.getObject(new ResourceLocation("projectred-core:drawplate"));
		
		int resMetas[] = {
				100, //Copper Ingot
				101, //Tin Ingot
				102, //Silver Ingot
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
	
	public void preInit() {
	}
	
	public void init() {
	}
	
	public void postInit() {
		Item resItem = Item.REGISTRY.getObject(new ResourceLocation("projectred-core:resource_item"));
		Item resPlate = Item.REGISTRY.getObject(new ResourceLocation("projectred-core:drawplate"));
		
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
		Vanilla.removeRecipe("projectred-core:resource/electrotine_silicon_compound");
		Vanilla.removeRecipe("projectred-core:resource/electrotine_iron_compound");
		Vanilla.removeRecipe("projectred-core:misc/iron_coil");
		Vanilla.removeRecipe("projectred-core:misc/gold_coil");
		Vanilla.removeRecipe("projectred-core:misc/copper_coil");
		Vanilla.removeRecipe("projectred-core:misc/motor");
		Vanilla.removeRecipe("projectred-core:tools/draw_plate");
		Vanilla.removeRecipe("projectred-transmission:power_low_load");
		Vanilla.removeRecipe("projectred-transmission:framed/34_framed");
		Vanilla.removeRecipe("projectred-core:misc/sail");
		Vanilla.removeRecipe("projectred-core:misc/woven_cloth");
	
		//These will be replaced with Induction Smelter Recipes
		Vanilla.removeRecipe("projectred-core:resource/red_iron_compound");
		Vanilla.removeRecipe("projectred-core:resource/sandy_coal_compound");
		Vanilla.removeRecipe("projectred-core:resource/red_silicon_compound");
		Vanilla.removeRecipe("projectred-core:resource/glowing_silicon_compound");
		
		//Remove subItems from Project Red
		CreativeTabs projectRedCoreTab = Vanilla.findCreativeTab("projectred.core");
		
		for(ItemStack stack : getRemoveList()) {
			((ItemLogo) StarGarden.logo).addContraband(stack, projectRedCoreTab);
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

		Item resItem = Item.REGISTRY.getObject(new ResourceLocation("projectred-core:resource_item"));
		
		ItemStack silverIngot = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("thermalfoundation", "material")), 1, 130);
		ItemStack siliconWafer = new ItemStack(resItem, 1, 301);
		
		Thermal.addSmelterRecipe(8000, new ItemStack(Blocks.COAL_BLOCK), new ItemStack(Blocks.SAND, 8), new ItemStack(resItem, 1, 300), ItemStack.EMPTY, 0);//Silicon Boule
		Thermal.addSmelterRecipe(4000, siliconWafer, new ItemStack(Items.REDSTONE, 4), new ItemStack(resItem, 1, 320), ItemStack.EMPTY, 0);//Infused Silicon
		Thermal.addSmelterRecipe(4000, siliconWafer, new ItemStack(Items.GLOWSTONE_DUST, 4), new ItemStack(resItem, 1, 341), ItemStack.EMPTY, 0);//Energized Silicon
		Thermal.addSmelterRecipe(2000, silverIngot, new ItemStack(Items.REDSTONE, 4), new ItemStack(resItem, 1, 103), ItemStack.EMPTY, 0);//Red Alloy Ingot
	}
	
	@Override
	public void onLoadComplete() {
		Item resPlate = Item.REGISTRY.getObject(new ResourceLocation("projectred-core:drawplate"));
		Vanilla.removeItemStackFromJEI(new ItemStack(resPlate, 1, 0));
	}
	
}
