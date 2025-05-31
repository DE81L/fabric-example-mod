package com.example.shadow.block;

import com.example.shadow.ShadowMod;
import com.example.shadow.screen.ShadowCrafterScreenHandler;
import com.example.shadow.util.ShadowItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class ShadowCrafterBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, Inventory {
    private final DefaultedList<ShadowItem> ghostSlots = DefaultedList.ofSize(9, ShadowItem.EMPTY);

    public ShadowCrafterBlockEntity(BlockPos pos, BlockState state) {
        super(ShadowMod.SHADOW_CRAFTER_BE, pos, state);
    }

    // ---------- serialization ----------
    @Override
    public void writeNbt(NbtCompound nbt) {
        NbtList list = new NbtList();
        for (ShadowItem si : ghostSlots) list.add(si.writeNbt());
        nbt.put("Ghosts", list);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        NbtList list = nbt.getList("Ghosts", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < ghostSlots.size(); i++)
            ghostSlots.set(i, ShadowItem.readNbt(list.getCompound(i)));
    }

    // ---------- GUI ----------
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ShadowCrafterScreenHandler(syncId, inv, this);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.shadow.crafter");
    }

    // ---------- public API for command ----------
    public void setGhost(int slot, ItemStack stack) {
        ghostSlots.set(slot, ShadowItem.of(stack));
        markDirty();
        if (getWorld() != null)
            getWorld().updateListeners(getPos(), getCachedState(), getCachedState(), 3);
    }

    public void clearGhosts() {
        for (int i = 0; i < ghostSlots.size(); i++) ghostSlots.set(i, ShadowItem.EMPTY);
        markDirty();
        if (getWorld() != null)
            getWorld().updateListeners(getPos(), getCachedState(), getCachedState(), 3);
    }

    public List<ShadowItem> ghosts() { return ghostSlots; }

    // ---------- Inventory impl for GhostSlot ----------
    @Override
    public int size() { return ghostSlots.size(); }

    @Override
    public boolean isEmpty() {
        for (ShadowItem s : ghostSlots) if (s != ShadowItem.EMPTY) return false;
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        ShadowItem si = ghostSlots.get(slot);
        if (si == ShadowItem.EMPTY) return ItemStack.EMPTY;
        ItemStack stack = new ItemStack(si.item().value());
        stack.setCount(si.count());
        return stack;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) { return ItemStack.EMPTY; }

    @Override
    public ItemStack removeStack(int slot) { return ItemStack.EMPTY; }

    @Override
    public void setStack(int slot, ItemStack stack) {
        setGhost(slot, stack);
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) { return true; }

    @Override
    public void clear() {
        clearGhosts();
    }
}
