package com.example.shadow.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public record ShadowItem(Item item, int nbtHash, int count) {
    public static final ShadowItem EMPTY = new ShadowItem(null, 0, 0);

    public static ShadowItem of(ItemStack stack) {
        // ItemStack's NBT accessors were updated in newer Minecraft
        // versions to return Optional values instead of nullable
        // compounds. Query the optional and fall back to 0 when the
        // stack has no tag.
        return new ShadowItem(
                stack.getItem(),
                stack.getNbt().map(NbtCompound::hashCode).orElse(0),
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
        // Nbt accessors now return Optional values.
        Item i = Registries.ITEM.get(Identifier.of(tag.getString("id").orElse("")));
        return new ShadowItem(i, tag.getInt("h").orElse(0), tag.getByte("c"));
    }
}
