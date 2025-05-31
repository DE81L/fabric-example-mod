package com.example.shadow;

import com.example.shadow.block.ShadowCrafterBlock;
import com.example.shadow.block.ShadowCrafterBlockEntity;
import com.example.shadow.command.ShadowCommand;
import com.example.shadow.screen.ShadowCrafterScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.world.flag.FeatureFlags;

public class ShadowMod implements ModInitializer {
    public static final String MOD_ID = "modid";

    public static Block SHADOW_CRAFTER_BLOCK;
    public static BlockEntityType<ShadowCrafterBlockEntity> SHADOW_CRAFTER_BE;
    public static ScreenHandlerType<ShadowCrafterScreenHandler> SHADOW_CRAFTER_HANDLER;

    @Override
    public void onInitialize() {
        SHADOW_CRAFTER_BLOCK = Registry.register(Registries.BLOCK,
                Identifier.of(MOD_ID, "shadow_crafter"),
                new ShadowCrafterBlock(Block.Settings.create()));

        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "shadow_crafter"),
                new BlockItem(SHADOW_CRAFTER_BLOCK, new Item.Settings()));

        SHADOW_CRAFTER_BE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MOD_ID, "shadow_crafter"),
                FabricBlockEntityTypeBuilder.create(ShadowCrafterBlockEntity::new, SHADOW_CRAFTER_BLOCK).build());

        SHADOW_CRAFTER_HANDLER = Registry.register(
                Registries.SCREEN_HANDLER,
                Identifier.of(MOD_ID, "shadow_crafter"),
                new ScreenHandlerType<>(ShadowCrafterScreenHandler::new, FeatureFlags.VANILLA_SET));

        ShadowCommand.register();
    }
}
