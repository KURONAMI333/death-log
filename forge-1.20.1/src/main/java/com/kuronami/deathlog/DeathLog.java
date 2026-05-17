package com.kuronami.deathlog;

import com.kuronami.deathlog.command.DeathLogCommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Death Log — entry point (Forge 1.20.1).
 *
 * <p>A persistent {@code /deaths} history (coords + dimension + day +
 * cause), so you can walk back to your stuff without a gravestone block
 * or a map mod. A death listener writes to {@link SavedData}; one
 * command reads it. No mixin, no config.
 *
 * <p>Forge 47.x (1.20.1) uses a no-arg {@code @Mod} constructor; only
 * the game event bus is needed here.
 */
@Mod(DeathLog.MOD_ID)
public class DeathLog {

    public static final String MOD_ID = "deathlog";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public DeathLog() {
        LOGGER.info("Death Log ready — /deaths.");
        MinecraftForge.EVENT_BUS.register(new DeathLogListener());
        MinecraftForge.EVENT_BUS.register(new DeathLogCommand());
    }
}
