package com.duzo.fakeplayers.common.entities;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.sensor.HurtBySensor;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.w3c.dom.Attr;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.jar.Attributes;

public class HumanoidEntity extends PathAwareEntity {
    public EntityType<? extends PathAwareEntity> entityType;
    public Inventory inventory;
    public HumanoidEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.entityType = entityType;
        this.setUuid(UUID.randomUUID());
        inventory = new SimpleInventory(36);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(5, new WanderAroundPointOfInterestGoal(this, 0.5D,false));
        this.goalSelector.add(5, new WanderAroundGoal(this,0.5D,120,false));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8f));
        this.targetSelector.add(2, new RevengeGoal(this));
        ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
        this.getNavigation().setCanSwim(true);
        this.setCanPickUpLoot(true);
        this.goalSelector.add(2, new WanderNearTargetGoal(this, 0.5D, 32.0f));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 0.5D,true));
        this.goalSelector.add(2, new SwimGoal(this));
    }

    public static DefaultAttributeContainer getHumanoidAttributes() {
        return DefaultAttributeContainer.builder().add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK).add(EntityAttributes.GENERIC_ATTACK_DAMAGE).add(EntityAttributes.GENERIC_FOLLOW_RANGE).add(EntityAttributes.GENERIC_MAX_HEALTH).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE).add(EntityAttributes.GENERIC_MOVEMENT_SPEED).add(EntityAttributes.GENERIC_ARMOR).add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).build();
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        if (this.inventory != null) {
            for (int i = 0; i < this.inventory.size(); i++) {
                this.dropStack(this.inventory.getStack(i));
            }
        }
        super.onDeath(damageSource);
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
        super.onDamaged(damageSource);
        if (this.inventory != null) {
            boolean healedOnce = false;
            for (int i = 0; i < this.inventory.size(); i++) {
                ItemStack itemStack = this.inventory.getStack(i);
                if (itemStack.getItem().equals(Items.GOLDEN_APPLE) || itemStack.getItem().equals(Items.ENCHANTED_GOLDEN_APPLE)) {
                    inventory.setStack(i, this.eatFood(this.getWorld(), itemStack));
                    if (inventory.getStack(i).isEmpty()) {
                        inventory.setStack(i, ItemStack.EMPTY);
                        healedOnce = true;
                    }
                }
            }
            if (!healedOnce) {
                for (int i = 0; i < this.inventory.size(); i++) {
                    ItemStack itemStack = this.inventory.getStack(i);
                    if (itemStack.isFood()) {
                        int count = itemStack.getCount();
                        for (int x = 1; (x <= count) || (this.getHealth() < this.getMaxHealth()); x++) {
                            inventory.setStack(i, this.eatFood(this.getWorld(), itemStack));
                            this.heal(1);
                            if (inventory.getStack(i).isEmpty()) {
                                inventory.setStack(i, ItemStack.EMPTY);
                                break;
                            }
                        }
                        if (!(this.getHealth() < this.getMaxHealth())) {
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void loot(ItemEntity item) {
        ItemStack itemStack = item.getStack();
        ItemStack itemStack2 = this.tryEquip(itemStack.copy());
        if (!itemStack2.isEmpty()) {
            this.sendPickup(item, itemStack2.getCount());
            itemStack.decrement(itemStack2.getCount());
            if (itemStack.isEmpty()) {
                item.discard();
            }
        } else {
            boolean had_free_stack = false;
            for (int i = 0; i < this.inventory.size(); i++) {
                ItemStack tempStack = this.inventory.getStack(i);
                if (!tempStack.equals(ItemStack.EMPTY)) {
                    inventory.setStack(i, itemStack);
                    had_free_stack = true;
                    break;
                }
            }
            if (had_free_stack) {
                this.sendPickup(item, itemStack.getCount());
                itemStack.decrement(itemStack.getCount());
                if (itemStack.isEmpty()) {
                    item.discard();
                }
            }
        }
    }

    @Override
    public ItemStack tryEquip(ItemStack stack) {
        EquipmentSlot equipmentSlot = getPreferredEquipmentSlot(stack);
        ItemStack itemStack = this.getEquippedStack(equipmentSlot);
        boolean bl = this.prefersNewEquipment(stack, itemStack);
        if (equipmentSlot.isArmorSlot() && !bl) {
            equipmentSlot = EquipmentSlot.MAINHAND;
            itemStack = this.getEquippedStack(equipmentSlot);
            bl = itemStack.isEmpty();
        }

        if (bl && this.canPickupItem(stack)) {
            boolean had_free_stack = false;
            if (!itemStack.isEmpty()) {
                if (this.inventory != null) {
                    for (int i = 0; i < this.inventory.size(); i++) {
                        ItemStack tempStack = this.inventory.getStack(i);
                        if (!tempStack.equals(ItemStack.EMPTY)) {
                            inventory.setStack(i, itemStack);
                            had_free_stack = true;
                            break;
                        }
                    }
                }
                if (!had_free_stack) {
                    this.dropStack(itemStack);
                }
            }

            if (equipmentSlot.isArmorSlot() && stack.getCount() > 1) {
                ItemStack itemStack2 = stack.copyWithCount(1);
                this.equipLootStack(equipmentSlot, itemStack2);
                return itemStack2;
            } else {
                this.equipLootStack(equipmentSlot, stack);
                return stack;
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void sendPickup(Entity item, int count) {
        super.sendPickup(item, count);
    }

    @Override
    public String getUuidAsString() {
        return this.getUuid().toString();
    }
}
