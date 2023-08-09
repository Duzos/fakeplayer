package com.duzo.fakeplayers.common.containers;

import com.duzo.fakeplayers.common.entities.HumanoidEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class HumanoidInventoryContainer extends AbstractContainerMenu {
    private HumanoidEntity humanoid;
    private Inventory inventory;

    public HumanoidInventoryContainer(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, (HumanoidEntity) null);
    }
    public HumanoidInventoryContainer(int id, Inventory inventory, HumanoidEntity entity) {
        super(FPContainers.HUMANOID_INVENTORY_CONTAINER.get(),id);

        if (entity == null) {
            return;
        }

        this.inventory = inventory;
        this.humanoid = entity;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }

//    private void addHumanoidArmour(HumanoidEntity humanoid) {
//        Iterable<ItemStack> items = humanoid.getArmorSlots();
//
//        for (ItemStack itemStack : items) {
//            hu;
//        }
//    }
}
