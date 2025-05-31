package com.example.shadow.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public record ShadowItem(Item item, int nbtHash, int count) {
    public static final ShadowItem EMPTY = new ShadowItem(null, 0, 0);

    public static ShadowItem of(ItemStack stack) {
        return new ShadowItem(
                stack.getItem(),
                0,
                stack.getCount());
    }

    public NbtCompound writeNbt() {
        NbtCompound tag = new NbtCompound();
        if (item != null)
            tag.putString("id", Registries.ITEM.getId(item).toString());
        if (nbtHash != 0) tag.putInt("h", nbtHash);
        tag.putByte("c", (byte) count);
        return tag;
    }

    public static ShadowItem readNbt(NbtCompound tag) {
        // Newer Minecraft versions return optionals from NbtCompound accessors.
        String idString = tag.getString("id").orElse("");
        Identifier id = Identifier.tryParse(idString);
        Item i = id != null ? Registries.ITEM.get(id) : null;
        int hash = tag.getInt("h").orElse(0);
        byte count = tag.getByte("c").orElse((byte) 0);
        return new ShadowItem(i, hash, count);
    }
}
