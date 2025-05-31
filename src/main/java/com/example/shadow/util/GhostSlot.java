package com.example.shadow.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class GhostSlot extends Slot {
    public GhostSlot(Inventory inv, int index, int x, int y) {
        super(inv, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        return false;
    }
}
