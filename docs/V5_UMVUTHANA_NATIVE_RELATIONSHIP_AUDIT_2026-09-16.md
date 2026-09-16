# OVERLORD REIGN V5 Umvuthana Native Relationship Audit

Status: VERIFIED TECHNICAL FACT / V5 SUPPORTING AUDIT

Date: 2026-09-16

Purpose: record the exact peaceful Umvuthi relationship surface in the supplied Mowzie's Mobs build and identify what V5 can reuse without custom boss-state intervention.

## 1. Audited artifact

The supplied instance contains:

```text
mowziesmobs-1.8.2.jar
```

The active supplied configuration contains:

```text
mowziesmobs-common.toml
```

This audit is technical support. It does not by itself establish an authored political route.

## 2. Peaceful access is mask-gated

`EntityUmvuthi.canTradeWith(Player)` checks the player's helmet slot and permits the peaceful trade interaction when the worn item implements the native `UmvuthanaMask` type.

The interaction opens Umvuthi's trade GUI only while he is alive, not already trading, and not actively targeting the player.

V5 consequence:

- a real native masked-audience mechanic exists;
- the mask can support deliberate noncombat contact with the canonical Grove;
- V5 does not need to invent a generic fake interaction merely to make Umvuthi approachable.

## 3. Exact configured first payment

The supplied configuration states:

```text
[mobs.umvuthi]
trade_which_item = "minecraft:gold_block"
trade_how_many = 7
```

The configured peaceful exchange therefore requires seven Gold Blocks for the first successful trade.

## 4. Umvuthi remembers the player

Umvuthi owns persistent per-player trade state.

The source exposes:

```text
hasTradedWith(Player)
rememberTrade(Player)
```

Successful first payment records the player's UUID in Umvuthi's persistent `TRADED_PLAYERS` data.

This is durable native relationship state owned by Mowzie's Mobs rather than Questlog.

V5 should query or bridge this source-owned fact where useful instead of duplicating the native trade-completion fact as an unrelated inventory proxy.

## 5. Sun's Blessing

On successful trade, Umvuthi grants the native `SUNS_BLESSING` effect and performs his blessing ability presentation.

The packaged advancement is:

```text
mowziesmobs:suns_blessing
```

The packaged description is `Receive the Sun's Blessing`.

The trade interface also tells an already recognized player to return later to replenish for free. The source supports this behavior: once `hasTradedWith(Player)` is true, the trade handler no longer requires the first payment before granting the blessing again.

Therefore the native peaceful interaction is not merely a one-time item exchange. It creates a durable recognized player relationship with repeat access to the blessing.

## 6. Destruction has a direct native signal

The packaged advancement:

```text
mowziesmobs:kill_umvuthi
```

uses `minecraft:player_killed_entity` for `mowziesmobs:umvuthi`.

The Umvuthi loot table gives the player `mowziesmobs:sol_visage` when Umvuthi is killed by a player.

This remains a strong native basis for the local DESTROYED route.

## 7. Sol Visage cannot support a living-Umvuthi submission route

The Sol Visage has a native player-follower mechanic: while wearing it, the player can use Umvuthana masks to summon player-owned Umvuthana followers.

However, the audited native acquisition path for the Sol Visage is the player-kill loot pool of Umvuthi.

Therefore V5 must not use Sol Visage ownership or player-summoned Umvuthana followers as the normal prerequisite for a SUBJUGATED route that explicitly leaves the canonical Umvuthi alive.

Doing so would make the peaceful submission route depend on the destructive outcome it is meant to exclude.

## 8. Implementation consequence

The native build gives V5 three strong pieces of reusable state:

```text
masked peaceful access
persistent successful Umvuthi trade
Sun's Blessing ownership / replenishment relationship
```

It does not provide a native `Umvuthi submitted` state.

The approved V5 restriction therefore remains appropriate:

- do not intercept Umvuthi's boss health;
- do not synthesize a defeated-but-alive combat state;
- author SUBJUGATED through ordinary quest facts, provider relationships, and a final political resolution while Umvuthi remains alive.

The exact political leverage for that route remains a V5 authoring decision and must not be inferred from the technical trade system alone.
