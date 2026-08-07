# Death Log

> `/deaths` — a persistent list of where you last died (coords, dimension, day, cause), kept across restarts. No gravestone block, no map mod: just walk back to your stuff.

## What it does

Every death is appended to your personal log (newest first, last 10 kept):

```
Death Log — your last 3 deaths (newest first):
#1 · day 12 · (210, 64, -88) overworld — Slain by Zombie
#2 · day 9 · (-44, 12, 301) overworld — Fell from a high place
#3 · day 4 · (88, 70, -150) the_nether — Walked into fire
```

- `/deaths` — your own history.
- `/deaths <player>` — op-only.
- Saved via vanilla world data, so it **survives server restarts**.

Sibling to **Death Forensics**: that explains your single last death in depth; this is the running history. They don't overlap and pair well.

## Why

"I lost my stuff and can't find where I died" is endless on Reddit. The recovery compass is single-use; gravestone mods add blocks that bug out with minimaps. This is just a lightweight log.

## Install

Drop `deathlog-<version>.jar` into `mods/`. Server-side. No dependencies.

- Minecraft 1.21.1 · NeoForge · JDK 21

## Scope

Read-only against the world (listens to vanilla death events, stores a small record). No mixin, no config, no blocks/items. 9 languages (machine-baseline; native PRs welcome).

## License

All Rights Reserved — modpack inclusion welcome, no credit required. Source is published so you can read exactly what it does.

Author: KURONAMI
