package com.ferreusveritas.stargarden.util;

import java.lang.reflect.Field;

import com.ferreusveritas.stargarden.StarGarden;

import net.minecraft.creativetab.CreativeTabs;

public class Util {
	
	public static CreativeTabs findCreativeTab(String label) {
		return StarGarden.proxy.findCreativeTab(label);
	}
	
	public static Object getRestrictedObject(Class clazz, Object from, String ... objNames) {
		for(String objName: objNames) {
			try {
				Field field = clazz.getDeclaredField(objName);
				field.setAccessible(true);
				return field.get(from);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) { }
		}
		
		return null;
	}
	
	public static void setRestrictedObject(Class clazz, Object from, Object value, String ... objNames) {
		for(String objName: objNames) {
			try {
				Field field = clazz.getDeclaredField(objName);
				field.setAccessible(true);
				field.set(from, value);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) { }
		}
		
	}
	
}
