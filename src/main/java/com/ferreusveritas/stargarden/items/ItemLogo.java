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
	private HashMap<CreativeTabs, HashSet<ItemStack>> additions = new HashMap<>();
	
	public void addContraband(ItemStack stack, CreativeTabs tab) {
		contraband.computeIfAbsent(tab, k -> new HashSet<ItemStack>()).add(stack);
	}
	
	public void addItem(ItemStack stack, CreativeTabs tab) {
		additions.computeIfAbsent(tab, k -> new HashSet<ItemStack>()).add(stack);
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
			HashSet<ItemStack> relocation = contraband.get(tab);//Get the set of itemStacks for this tab
			relocation.forEach( i -> itemSet.add(i.getItem()) );//Create a set of items from the itemStacks
			itemSet.forEach(i -> items.addAll(getFilteredItemStacks(i, null, relocation)));//Add all of the ItemStacks that are not contraband
		}

		if(additions.containsKey(tab)) {
			items.addAll(additions.get(tab));
		}
		
		if(tab.equals(CreativeTabs.MISC)) {
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
