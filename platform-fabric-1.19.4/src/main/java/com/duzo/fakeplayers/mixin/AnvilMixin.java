package com.duzo.fakeplayers.mixin;

import com.duzo.fakeplayers.Fakeplayers;
import com.duzo.fakeplayers.common.ItemInit;
import com.duzo.fakeplayers.util.SkinGrabber;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public class AnvilMixin  {

    @Inject(method = "setNewItemName", at = @At("TAIL"))
    public void onNewItemName(String newItemName, CallbackInfo ci) {
        ItemStack itemStack = ((AnvilScreenHandler)(Object)this).getSlot(0).getStack();
        if (itemStack.getItem().equals(ItemInit.FAKE_PLAYER_SPAWN_EGG) || itemStack.getItem().equals(ItemInit.FAKE_PLAYER_SLIM_SPAWN_EGG)) {

            if (!Fakeplayers.urlsToDownload.contains(newItemName)) {
                Fakeplayers.urlsToDownload.add(newItemName);
                Fakeplayers.sendURLToClients(SkinGrabber.URL + newItemName);
            }


        }

    }

    @Inject(method = "onTakeOutput", at = @At("TAIL"))
    public void onTakeOutput(PlayerEntity player, ItemStack itemStack, CallbackInfo ci) {
        if (itemStack.getItem().equals(ItemInit.FAKE_PLAYER_SPAWN_EGG) || itemStack.getItem().equals(ItemInit.FAKE_PLAYER_SLIM_SPAWN_EGG)) {
            if (!Fakeplayers.urlsToDownload.contains(itemStack.getName().getString())) {
                Fakeplayers.urlsToDownload.add(itemStack.getName().getString());
                Fakeplayers.sendURLToClients(SkinGrabber.URL + itemStack.getName().getString());
            }


        }
    }

}
