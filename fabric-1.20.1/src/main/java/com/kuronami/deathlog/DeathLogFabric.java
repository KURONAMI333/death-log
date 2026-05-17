package com.kuronami.deathlog;

import com.kuronami.deathlog.command.DeathLogCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.level.ServerPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Death Log — entry point (Fabric 1.21.1).
 *
 * <p>A persistent {@code /deaths} history (coords + dimension + day +
 * cause), so you can walk back to your stuff without a gravestone block
 * or a map mod. Two Fabric API hooks: {@code AFTER_DEATH} (append a row
 * to the per-player {@code SavedData}) and {@code
 * CommandRegistrationCallback} (the {@code /deaths} command). The log is
 * persistent vanilla {@code SavedData}, so nothing is cleared on stop.
 * No mixin, no config.
 */
public class DeathLogFabric implements ModInitializer {

    public static final String MOD_ID = "deathlog";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Death Log ready — /deaths.");

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (entity instanceof ServerPlayer player) {
                DeathLogListener.onDeath(player);
            }
        });

        CommandRegistrationCallback.EVENT.register(
            (dispatcher, registryAccess, environment) ->
                DeathLogCommand.register(dispatcher));
    }
}
