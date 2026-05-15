package com.kuronami.deathlog;

import com.kuronami.deathlog.command.DeathLogCommand;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Death Log — entry point.
 *
 * <p>A persistent {@code /deaths} history (coords + dimension + day +
 * cause), so you can walk back to your stuff without a gravestone block
 * or a map mod. Sibling to Death Forensics: that explains the single
 * last death in depth; this is the running list. A death listener writes
 * to {@link SavedData}; one command reads it. No mixin, no config.
 */
@Mod(DeathLog.MOD_ID)
public class DeathLog {

    public static final String MOD_ID = "deathlog";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public DeathLog(IEventBus modBus, ModContainer container) {
        LOGGER.info("Death Log ready — /deaths.");
        NeoForge.EVENT_BUS.register(new DeathLogListener());
        NeoForge.EVENT_BUS.register(new DeathLogCommand());
    }
}
