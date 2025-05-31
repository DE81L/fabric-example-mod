package com.example.shadow.command;

import com.example.shadow.block.ShadowCrafterBlockEntity;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;

import static net.minecraft.server.command.CommandManager.literal;

public class ShadowCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, env) ->
                dispatcher.register(literal("item-shadow")
                        .requires(src -> src.hasPermissionLevel(2))
                        .then(literal("set").executes(ctx -> setGhost(ctx.getSource())))
                        .then(literal("clear").executes(ctx -> clearGhost(ctx.getSource())))
                        .then(literal("dump").executes(ctx -> dumpGhost(ctx.getSource())))));
    }

    private static ShadowCrafterBlockEntity targetBlock(ServerPlayerEntity player) throws CommandSyntaxException {
        BlockHitResult hit = (BlockHitResult) player.raycast(5, 0, false);
        ShadowCrafterBlockEntity be = (ShadowCrafterBlockEntity) player.getWorld().getBlockEntity(hit.getBlockPos());
        if (be != null) return be;
        throw new com.mojang.brigadier.exceptions.SimpleCommandExceptionType(Text.literal("No Shadow Crafter in sight")).create();
    }

    private static int setGhost(ServerCommandSource src) throws CommandSyntaxException {
        ServerPlayerEntity player = src.getPlayer();
        targetBlock(player).setGhost(0, player.getMainHandStack());
        src.sendFeedback(() -> Text.literal("Set ghost"), false);
        return 1;
    }
    private static int clearGhost(ServerCommandSource src) throws CommandSyntaxException {
        targetBlock(src.getPlayer()).clearGhosts();
        src.sendFeedback(() -> Text.literal("Ghosts cleared"), false);
        return 1;
    }
    private static int dumpGhost(ServerCommandSource src) throws CommandSyntaxException {
        ShadowCrafterBlockEntity be = targetBlock(src.getPlayer());
        for (int i = 0; i < be.ghosts().size(); i++) {
            src.sendFeedback(() -> Text.literal(i + ": " + be.ghosts().get(i).toString()), false);
        }
        return 1;
    }
}
