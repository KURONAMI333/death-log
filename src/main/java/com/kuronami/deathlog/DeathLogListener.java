package com.kuronami.deathlog;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * Appends one compact row per player death. Coords/dimension/day are the
 * load-bearing facts (you walk back to them); the cause is stored as the
 * vanilla death-message string for context. Everything is certain
 * vanilla data — no attribution guessing.
 */
public class DeathLogListener {

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
                || player.getServer() == null) {
            return;
        }
        ServerLevel level = (ServerLevel) player.level();
        ResourceLocation rl = level.dimension().location();
        String dim = "minecraft".equals(rl.getNamespace()) ? rl.getPath() : rl.toString();

        DeathLogData.Entry e = new DeathLogData.Entry(
            player.getBlockX(), player.getBlockY(), player.getBlockZ(),
            dim, level.getDayTime() / 24000L, System.currentTimeMillis(),
            player.getCombatTracker().getDeathMessage().getString());

        DeathLogData.get(player.getServer()).add(player.getUUID(), e);
    }
}
