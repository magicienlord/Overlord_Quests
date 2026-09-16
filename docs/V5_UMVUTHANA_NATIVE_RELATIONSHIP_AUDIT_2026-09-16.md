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

## 8. Source-backed recurring provider surfaces

The exact JAR does not define a ready-made political council for V5, but it does expose several stable native distinctions that can support authored recurring characters.

### 8.1 Ordinary Umvuthana Minion

`EntityUmvuthanaMinion` is the ordinary source-owned trading Umvuthana type.

Its native `canTradeWith(Player)` requires the player to wear an `UmvuthanaMask` and requires that the Umvuthana currently have a trade to offer.

The entity persists the following relevant state through NBT:

```text
mask
weapon
tradeStore
offeringTrade
timeOffering
HomePosX / HomePosY / HomePosZ
HomeDist
MisbehavedPlayer
```

Its persistence method also returns true for the build's custom-persistence check.

This makes an authored ordinary Umvuthana technically suitable for a recurring provider without requiring V5 to invent a separate NPC species or continuous reputation simulation.

The six exact native mask identities are:

```text
FURY
FEAR
RAGE
BLISS
MISERY
FAITH
```

Mask identity itself is persisted. The packaged item descriptions associate them with Strength, Swiftness, Haste, Jump Boost, Resistance and Health Boost respectively.

The source does not establish those masks as political offices or social castes. V5 must not infer a priesthood, council seat, merchant caste or other institution merely from a mask name.

### 8.2 Umvuthana Crane

`EntityUmvuthanaCrane` is a distinct subclass of `EntityUmvuthanaMinion` with explicit healing behavior and healing-target goals.

Umvuthi's own spawn-followers ability can create Crane healers under its healer/sunblocker path.

The Crane is therefore a strong source-backed specialist identity for a possible recurring authored character.

This technical fact does not by itself establish the Crane as a priest, political official or mandatory submission-route provider.

### 8.3 Umvuthana Raptor

`EntityUmvuthanaRaptor` is a distinct Umvuthana pack leader. On spawn it creates and leads a group of `EntityUmvuthanaFollowerToRaptor` followers.

It inherits the base Umvuthana `isUmvuthiDevoted()` result of true. Player-owned Umvuthana followers explicitly override that result to false.

The Raptor is therefore source-backed as a martial leader who remains technically classified as devoted to Umvuthi.

The source does not prove that every Raptor belongs to the canonical Grove or occupies a formal office under that Grove's Umvuthi. Using one as a canonical Grove authority figure would be an authored V5 decision, not a discovered native fact.

### 8.4 Grove generation does not supply the complete authored cast

The audited `UmvuthanaGrovePieces.Piece` data-marker handling contains an explicit `umvuthi` marker that creates the Grove's Umvuthi.

The same marker handling does not expose equivalent static provider markers for ordinary Umvuthana Minions, Cranes or Raptors.

Umvuthi can create ordinary Minions and Crane healers dynamically through his own ability system, while Raptors use their own separate spawning behavior.

Therefore the canonical Grove cannot be assumed to generate the complete V5 provider cast naturally.

This fits the already-approved civilization anchor rule:

```text
anchor lock
-> reuse appropriate source entities where present
-> bind selected recurring roles
-> spawn missing required authored roles where necessary
```

The later implementation can therefore create the minimum approved Grove cast at anchor lock using the native entity types rather than requiring fixed world placement.

## 9. Implementation consequence

The native build gives V5 strong reusable state:

```text
masked peaceful access
persistent successful Umvuthi trade
Sun's Blessing ownership / replenishment relationship
persistent ordinary Umvuthana trader state
persistent mask identity
native Crane healer identity
native Raptor pack-leader identity
```

It does not provide a native `Umvuthi submitted` state or a native pre-authored Grove political council.

The approved V5 restriction therefore remains appropriate:

- do not intercept Umvuthi's boss health;
- do not synthesize a defeated-but-alive combat state;
- author SUBJUGATED through ordinary quest facts, provider relationships, and a final political resolution while Umvuthi remains alive;
- select the smallest source-compatible provider cast necessary for the authored political mechanism;
- do not promote mask names or source combat classes into invented political institutions without explicit V5 approval.

The exact provider identities remain a campaign-authoring decision and must return to the Overlord before becoming V5 authority.
