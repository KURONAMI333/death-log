package com.kuronami.deathlog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * Per-player death history, newest first, capped per player. Backed by
 * vanilla {@link SavedData} so it survives restarts (a death log that
 * forgets on restart defeats the point). Lives in
 * {@code world/data/deathlog.dat}.
 */
public class DeathLogData extends SavedData {

    private static final String NAME = "deathlog";
    private static final int MAX_PER_PLAYER = 10;

    private static final Factory<DeathLogData> FACTORY =
        new Factory<>(DeathLogData::new, DeathLogData::load, null);

    /** uuid -> deaths, index 0 = most recent. */
    private final Map<UUID, List<Entry>> deaths = new HashMap<>();

    public record Entry(int x, int y, int z, String dim, long day,
                         long realMs, String cause) {}

    public static DeathLogData get(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(FACTORY, NAME);
    }

    public List<Entry> get(UUID uuid) {
        return deaths.getOrDefault(uuid, List.of());
    }

    public void add(UUID uuid, Entry e) {
        List<Entry> list = deaths.computeIfAbsent(uuid, k -> new ArrayList<>());
        list.add(0, e);
        while (list.size() > MAX_PER_PLAYER) {
            list.remove(list.size() - 1);
        }
        setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        CompoundTag root = new CompoundTag();
        deaths.forEach((uuid, list) -> {
            ListTag lt = new ListTag();
            for (Entry e : list) {
                CompoundTag c = new CompoundTag();
                c.putInt("x", e.x());
                c.putInt("y", e.y());
                c.putInt("z", e.z());
                c.putString("dim", e.dim());
                c.putLong("day", e.day());
                c.putLong("ms", e.realMs());
                c.putString("cause", e.cause());
                lt.add(c);
            }
            root.put(uuid.toString(), lt);
        });
        tag.put(NAME, root);
        return tag;
    }

    private static DeathLogData load(CompoundTag tag, HolderLookup.Provider registries) {
        DeathLogData d = new DeathLogData();
        CompoundTag root = tag.getCompound(NAME);
        for (String key : root.getAllKeys()) {
            ListTag lt = root.getList(key, Tag.TAG_COMPOUND);
            List<Entry> list = new ArrayList<>();
            for (int i = 0; i < lt.size(); i++) {
                CompoundTag c = lt.getCompound(i);
                list.add(new Entry(c.getInt("x"), c.getInt("y"), c.getInt("z"),
                    c.getString("dim"), c.getLong("day"), c.getLong("ms"),
                    c.getString("cause")));
            }
            try {
                d.deaths.put(UUID.fromString(key), list);
            } catch (IllegalArgumentException ignored) {
                // Skip any non-UUID key rather than fail world load.
            }
        }
        return d;
    }
}
