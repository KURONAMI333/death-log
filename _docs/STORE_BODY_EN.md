# Death Log

> `/deaths` — a persistent list of where you last died (coords, dimension, day, cause), kept across restarts. No gravestone block, no map mod: just walk back to your stuff.

"I lost my stuff and can't find where I died" is endless on r/feedthebeast. The recovery compass is single-use; gravestone mods add blocks that bug out with minimaps. Death Log is just a lightweight server-side history you can check any time.

- 📓 `/deaths` — your last 10 deaths, newest first
- 🧭 Coordinates + dimension + in-world day + cause per entry
- 💾 Persists across server restarts (vanilla world data)
- 🤝 `/deaths <player>` for ops

## What it does / Usage

```
Death Log — your last 3 deaths (newest first):
#1 · day 12 · (210, 64, -88) overworld — Slain by Zombie
#2 · day 9 · (-44, 12, 301) overworld — Fell from a high place
#3 · day 4 · (88, 70, -150) the_nether — Walked into fire
```

`/deaths` shows your own history; `/deaths <player>` is op-only.

Sibling to **Death Forensics** (which explains your single last death in depth) — this is the running list. They don't overlap and pair well.

## Supported loaders / versions

| Minecraft | NeoForge | Forge | Fabric |
|---|:---:|:---:|:---:|
| 1.21.1 | ✅ | planned | planned |

Forge / Fabric / 1.20.1 ports planned; this release is NeoForge 1.21.1.

## Dependencies

None.

## Compatibility & scope

Server-side, read-only against the world (listens to vanilla death events, stores a small record). No mixin, no config, no blocks/items — can't conflict with other mods.

## Known limitations

Keeps the last 10 deaths per player (older ones roll off). Cause text is the vanilla death message captured at death time.

## Install

1. Install NeoForge for Minecraft 1.21.1.
2. Drop `deathlog-0.1.0.jar` into `mods/`. Server-side.

- Minecraft 1.21.1 · NeoForge · JDK 21

## Languages

Output localized in 9 languages (machine-baseline; native-speaker PRs welcome).

## License

MIT — modpack inclusion welcome, no credit required.

Author: KURONAMI
