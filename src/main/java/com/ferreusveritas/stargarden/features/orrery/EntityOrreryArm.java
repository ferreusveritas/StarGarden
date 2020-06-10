package com.ferreusveritas.stargarden.features.orrery;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.Rotations;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

/**
 * 
 * @author ferreusveritas
 *
 */
public class EntityOrreryArm extends Entity {
	
	private static final Rotations DEFAULT_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
	private static final Float DEFAULT_SCALE = new Float(1.0F);
	private static final Float DEFAULT_RADIUS = new Float(1.0F);
	private static final Float DEFAULT_HEIGHT = new Float(1.0F);
	private static final Float DEFAULT_ANGLE = new Float(0.0F);

		
	public static final DataParameter<Rotations> ROTATION = EntityDataManager.<Rotations>createKey(EntityOrreryArm.class, DataSerializers.ROTATIONS);
	public static final DataParameter<Float> RADIUS = EntityDataManager.<Float>createKey(EntityOrreryArm.class, DataSerializers.FLOAT);
	public static final DataParameter<Float> HEIGHT = EntityDataManager.<Float>createKey(EntityOrreryArm.class, DataSerializers.FLOAT);
	public static final DataParameter<Float> SCALE = EntityDataManager.<Float>createKey(EntityOrreryArm.class, DataSerializers.FLOAT);
	public static final DataParameter<Float> ANGLE = EntityDataManager.<Float>createKey(EntityOrreryArm.class, DataSerializers.FLOAT);

	
	private Rotations rotation;
	private Float scale;
	private Float radius;
	private Float height;
	private Float angle;
	
	public EntityOrreryArm(World worldIn) {
		super(worldIn);
		rotation = DEFAULT_ROTATION;
		scale = DEFAULT_SCALE;
		radius = DEFAULT_RADIUS;
		height = DEFAULT_HEIGHT;
		angle = DEFAULT_ANGLE;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		setSize(radius * 2, height);
		
		Rotations rotation = (Rotations)dataManager.get(ROTATION);
		
		if (!this.rotation.equals(rotation)) {
			setRotation(rotation);
		}
		
		Float scale = (Float)dataManager.get(SCALE);
		
		if (!this.scale.equals(scale)) {
			setScale(scale);
		}
		
		Float radius = (Float)dataManager.get(RADIUS);
		
		if(!this.radius.equals(radius)) {
			setRadius(radius);
		}
		
		Float height = (Float)dataManager.get(HEIGHT);
		
		if(!this.height.equals(height)) {
			setHeight(height);
		}
		
		Float angle = (Float)dataManager.get(ANGLE);
		
		if(!this.angle.equals(angle)) {
			setAngle(angle);
		}
		
	}
	
	@Override
	public void onEntityUpdate() { }
	
	public void setRotation(Rotations vec) {
		rotation = vec;
		dataManager.set(ROTATION, vec);
	}
	
	public Rotations getRotation() {
		return rotation;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
		dataManager.set(SCALE, scale);
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
		dataManager.set(RADIUS, radius);
	}
	
	public float getRadius() {
		return radius;
	}

	public void setHeight(float height) {
		this.height = height;
		dataManager.set(HEIGHT, height);
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
		dataManager.set(ANGLE, angle);
	}
	
	public float getAngle() {
		return angle;
	}
	
	@Override
	protected void entityInit() { 
		dataManager.register(ROTATION, DEFAULT_ROTATION);
		dataManager.register(SCALE, DEFAULT_SCALE);
		dataManager.register(RADIUS, DEFAULT_RADIUS);
		dataManager.register(HEIGHT, DEFAULT_HEIGHT);
		dataManager.register(ANGLE, DEFAULT_ANGLE);
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		
		// /summon mcf:item_display 0.5 130 5.5 {item:{id:"minecraft:redstone",Count:1b},scale:4.0f}
		// /summon mcf:item_display ~5 ~1 ~ {item:{id:"thermalexpansion:frame",Count:1b},scale:4.0f,rotation:[35f,0f,45f]}
		// /summon mcf:item_display ~ ~ ~ {item:{id:"minecraft:redstone",Count:1b}}
		// /summon mcf:item_display 2608 65.5 27 {item:{id:"cathedral:cathedral_gargoyle_demon_stone",Count:1b},scale:4.0f,rotation:[0f,0f,0f]}
		// /kill @e[type=mcf:item_display]
		
		NBTTagList nbttaglist1 = compound.getTagList("rotation", NBT.TAG_FLOAT);
		setRotation(nbttaglist1.hasNoTags() ? DEFAULT_ROTATION : new Rotations(nbttaglist1));
		
		Float scale = compound.getFloat("scale");
		setScale(scale == 0.0f ? DEFAULT_SCALE : scale);
		
		Float radius = compound.getFloat("radius");
		setRadius(radius == 0.0f ? DEFAULT_RADIUS : radius);
				
		Float height = compound.getFloat("height");
		setHeight(height == 0.0f ? DEFAULT_HEIGHT : height);

		Float angle = compound.getFloat("angle");
		setAngle(angle == 0.0f ? DEFAULT_ANGLE : angle);
		
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		
		if (!DEFAULT_ROTATION.equals(rotation)) {
			compound.setTag("rotation", rotation.writeToNBT());
		}
		
		if (!DEFAULT_SCALE.equals(scale)) {
			compound.setFloat("scale", scale);
		}
		
		if (!DEFAULT_RADIUS.equals(radius)) {
			compound.setFloat("radius", radius);
		}
		
		if (!DEFAULT_HEIGHT.equals(height)) {
			compound.setFloat("height", height);
		}
		
		if (!DEFAULT_ANGLE.equals(angle)) {
			compound.setFloat("angle", angle);
		}
		
	}
	
}
