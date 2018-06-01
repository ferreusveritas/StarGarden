package com.ferreusveritas.stargarden;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemLogo extends Item {
	
	private HashMap<CreativeTabs, HashSet<ItemStack>> contraband = new HashMap<>();
	
	public void addContraband(ItemStack stack, CreativeTabs tab) {
		contraband.computeIfAbsent(tab, k -> new HashSet<ItemStack>()).add(stack);
	}
	
	public ItemLogo() {
		setRegistryName("logo");
		setUnlocalizedName("logo");
		
		setHasSubtypes(true);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		
		if(contraband.containsKey(tab)) {
			HashSet<Item> itemSet = new HashSet<Item>();
			HashSet<ItemStack> relocation = contraband.get(tab);
			relocation.forEach( i -> itemSet.add(i.getItem()) );
			itemSet.forEach(i -> items.addAll(getFilteredItemStacks(i, null, relocation)));
		}
		
		/*
		Item resItem = Item.REGISTRY.getObject(new ResourceLocation("projectred-core:resource_item"));
		List<ItemStack> removeList = ProjectRed.getRemoveList();
		 
		if(tab.equals(projectRedCoreTab)) {
			items.addAll(getFilteredItemStacks(resItem, null, removeList));
		}
		*/
		
		if(tab.equals(StarGarden.starGardenTab)) {
			items.add(new ItemStack(this, 1, 0));
		}
	}
	
	/**
	 * Gets a list of subItems from {@link Item} "item" that belong in parameter {@link CreativeTabs} "tab" and removes from it the items in
	 * parameter "remList".
	 * 
	 * @param item
	 * @param tab
	 * @param remList
	 * @return
	 */
	public static NonNullList<ItemStack> getFilteredItemStacks(Item item, CreativeTabs tab, Collection<ItemStack> remList) {
		NonNullList<ItemStack> itemList = NonNullList.<ItemStack>create();
		item.getSubItems(tab, itemList);
		
		Iterator<ItemStack> it = itemList.iterator();
		while (it.hasNext()) {
			ItemStack stack = it.next();
			for(ItemStack rem : remList) {
				if(ItemStack.areItemsEqual(stack, rem)) {
					it.remove();
				}
			}
		}
		return itemList;
	}
	
}
