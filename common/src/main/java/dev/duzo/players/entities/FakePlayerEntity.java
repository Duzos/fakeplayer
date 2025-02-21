package dev.duzo.players.entities;

import dev.duzo.players.api.SkinGrabber;
import dev.duzo.players.core.FPEntities;
import dev.duzo.players.entities.goal.HumanoidWaterAvoidingRandomStrollGoal;
import dev.duzo.players.entities.goal.MoveTowardsItemsGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class FakePlayerEntity extends PathfinderMob {
	private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(FakePlayerEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<String> SKIN_KEY = SynchedEntityData.defineId(FakePlayerEntity.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Boolean> SLIM = SynchedEntityData.defineId(FakePlayerEntity.class, EntityDataSerializers.BOOLEAN);

	public FakePlayerEntity(EntityType<? extends FakePlayerEntity> type, Level level) {
		super(type, level);
	}

	public FakePlayerEntity(Level level) {
		this(FPEntities.FAKE_PLAYER.get(), level);
	}

	public static AttributeSupplier.Builder getHumanoidAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 25.0D).add(Attributes.MOVEMENT_SPEED, 0.2D).add(Attributes.ATTACK_DAMAGE, 1D);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(5, new MoveTowardsItemsGoal(this, 1.0D, true));
		this.goalSelector.addGoal(4, new HumanoidWaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(3, new OpenDoorGoal(this, true));
		this.goalSelector.addGoal(2, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5D, true));
		this.goalSelector.addGoal(1, new FloatGoal(this));
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		GroundPathNavigation navigator = new GroundPathNavigation(this, level);
		navigator.setCanFloat(true);
		navigator.setCanOpenDoors(true);
		return navigator;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);

		nbt.putBoolean("sitting", this.isSitting());
		nbt.putString("SkinKey", this.getStringKey());
		nbt.putBoolean("Slim", this.isSlim());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);

		this.entityData.set(SITTING, nbt.getBoolean("sitting"));
		this.entityData.set(SKIN_KEY, nbt.getString("SkinKey"));
		this.entityData.set(SLIM, nbt.getBoolean("Slim"));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();

		this.entityData.define(SITTING, false);
		this.entityData.define(SKIN_KEY, "duzo");
		this.entityData.define(SLIM, false);
	}

	public boolean isSitting() {
		return this.entityData.get(SITTING);
	}

	public void setSitting(boolean val) {
		this.entityData.set(SITTING, val);
	}

	protected String getStringKey() {
		String key = this.entityData.get(SKIN_KEY);

		if (key.isBlank()) {
			this.setSkin("duzo");
			return "duzo";
		}

		return key;
	}

	@Override
	public @Nullable Component getCustomName() {
		return Component.literal(this.getStringKey());
	}

	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);

		if (component == null) {
			this.setSkin("duzo");
			return;
		}
		this.setSkin(component.getString());
	}

	public ResourceLocation getSkin() {
		return SkinGrabber.INSTANCE.getSkin(this.getStringKey());
	}

	protected void setSkin(String skin) {
		this.entityData.set(SKIN_KEY, skin);
	}

	public boolean isSlim() {
		return this.entityData.get(SLIM);
	}

	protected void setSlim(boolean val) {
		this.entityData.set(SLIM, val);
	}
}
