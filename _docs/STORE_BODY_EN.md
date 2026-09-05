A persistent list of where you last died — coordinates, dimension, in-world day, and cause — kept across restarts. No gravestone block, no map mod: run `/deaths` and walk back to your stuff.

The recovery compass is single-use, and gravestone mods add blocks that can clash with minimaps. Death Log is a lightweight server-side history instead: it records each death and lets you look it up any time.

```
Death Log — your last 3 deaths (newest first):
#1 · day 12 · (210, 64, -88) overworld — Slain by Zombie
#2 · day 9 · (-44, 12, 301) overworld — Fell from a high place
#3 · day 4 · (88, 70, -150) the_nether — Walked into fire
```

`/deaths` shows your own history (last 10, newest first); `/deaths <player>` is op-only and resolves currently-online players. Cause text is the vanilla death message captured at the time of death, stored in vanilla world data so it survives restarts.

It's read-only and server-side — no mixin, no config, no blocks or items — so it can't conflict with other mods. Pairs with the sibling mod Death Forensics, which explains a single death in depth.

Bugs and questions: comment on the CurseForge page, or DM @kuronami333 on X.

All Rights Reserved. Modpack inclusion is allowed without permission or credit. Source: https://github.com/KURONAMI333/death-log
