package com.example.shadow.screen;

import com.example.shadow.block.ShadowCrafterBlockEntity;
import com.example.shadow.util.GhostSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;

public class ShadowCrafterScreenHandler extends ScreenHandler {
    public final PropertyDelegate delegate;
    private final ShadowCrafterBlockEntity be;

    public ShadowCrafterScreenHandler(int syncId, PlayerInventory inv, ShadowCrafterBlockEntity be) {
        super(com.example.shadow.ShadowMod.SHADOW_CRAFTER_HANDLER, syncId);
        this.be = be;
        this.delegate = new net.minecraft.screen.ArrayPropertyDelegate(1);
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new GhostSlot(be, i, 8 + i * 18, 20));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        return ItemStack.EMPTY;
    }
}
