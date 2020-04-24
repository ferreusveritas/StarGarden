package com.ferreusveritas.stargarden.world;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldProvider;

public abstract class StarWorldProvider extends WorldProvider {

	public static Vec3d convColor(int c) {
		int r = (c >> 16) & 0xff;
		int g = (c >> 8) & 0xff;
		int b = (c >> 0) & 0xff;
		return new Vec3d(r / 255.0, g / 255.0, b / 255.0);
	}
	
}
