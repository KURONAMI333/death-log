package com.kuronami.deathlog.command;

import com.kuronami.deathlog.DeathLogData;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * {@code /deaths} lists your recent deaths (newest first) with the
 * coords you need to walk back. {@code /deaths <player>} is op-only.
 */
public class DeathLogCommand {

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> d = event.getDispatcher();
        d.register(Commands.literal("deaths")
            .executes(this::self)
            .then(Commands.argument("player", EntityArgument.player())
                .requires(s -> s.hasPermission(2))
                .executes(this::other)));
    }

    private int self(CommandContext<CommandSourceStack> ctx) {
        CommandSourceStack src = ctx.getSource();
        if (!(src.getEntity() instanceof ServerPlayer player)) {
            src.sendFailure(Component.translatable("deathlog.playeronly"));
            return 0;
        }
        return list(src, DeathLogData.get(src.getServer()).get(player.getUUID()));
    }

    private int other(CommandContext<CommandSourceStack> ctx) {
        try {
            ServerPlayer t = EntityArgument.getPlayer(ctx, "player");
            return list(ctx.getSource(),
                DeathLogData.get(ctx.getSource().getServer()).get(t.getUUID()));
        } catch (com.mojang.brigadier.exceptions.CommandSyntaxException e) {
            ctx.getSource().sendFailure(Component.translatable("deathlog.playeronly"));
            return 0;
        }
    }

    private int list(CommandSourceStack src, List<DeathLogData.Entry> entries) {
        if (entries.isEmpty()) {
            src.sendSuccess(() -> Component.translatable("deathlog.none")
                .withStyle(ChatFormatting.GRAY), false);
            return Command.SINGLE_SUCCESS;
        }
        src.sendSuccess(() -> Component.translatable("deathlog.title", entries.size())
            .withStyle(ChatFormatting.GOLD), false);
        for (int i = 0; i < entries.size(); i++) {
            DeathLogData.Entry e = entries.get(i);
            int idx = i + 1;
            boolean newest = i == 0;
            src.sendSuccess(() -> Component.translatable("deathlog.entry",
                idx, e.day(), e.x(), e.y(), e.z(), e.dim(), e.cause())
                .withStyle(newest ? ChatFormatting.YELLOW : ChatFormatting.GRAY),
                false);
        }
        return Command.SINGLE_SUCCESS;
    }
}
