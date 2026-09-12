# OVERLORD QUESTS Umvuthi Audience Integration

Status: TECHNICAL SOURCE AUDIT / PRODUCTION AUDIENCE SUPPORT

Date: 2026-09-12

## Authority

The current OVERLORD REIGN lore authority establishes one designated Umvuthana Grove centered on one local Umvuthi. His native hostility must be preserved before a legitimate audience. The peaceful route reuses the native Umvuthana-mask recognition mechanic. Completing the first audience moves the canonical Grove into NEUTRAL so the Overlord no longer needs to keep wearing a mask merely to avoid ordinary renewed hostility.

The canonical Grove is one local polity. Other Umvuthis and Groves remain independent.

## Installed artifact authority

The supplied target instance contains:

```text
mowziesmobs-1.8.2.jar
```

SHA-256:

```text
e8ce1768cda6f1e1fadd2321c92921b473bd0cee45ba4b8387fb30f8326cb31c
```

Its packaged metadata declares Mowzie's Mobs 1.8.2 for the target Forge/Minecraft generation. The installed JAR is therefore the compatibility authority for this integration.

## Native Umvuthi identity

Bytecode inspection of the exact installed registry confirms:

```text
mowziesmobs:umvuthi
```

The production anchor is narrowed further by the authored scoreboard tag:

```text
overlord_anchor:umvuthana_main_umvuthi
```

Quest-critical protection remains separate:

```text
overlord_quest_protected
```

## Exact native mask rule

`EntityUmvuthi` 1.8.2 registers its player target goal with a predicate that inspects the player's head armor slot. A player wearing an item implementing Mowzie's `UmvuthanaMask` interface is excluded from ordinary player targeting unless that exact player is the Umvuthi's stored misbehaving player.

The same entity exposes:

```text
public boolean canTradeWith(Player)
```

and that method permits native trading only when the player's head-slot item implements `UmvuthanaMask`.

The exact installed mask item IDs are:

```text
mowziesmobs:umvuthana_mask_fury
mowziesmobs:umvuthana_mask_fear
mowziesmobs:umvuthana_mask_rage
mowziesmobs:umvuthana_mask_bliss
mowziesmobs:umvuthana_mask_misery
mowziesmobs:umvuthana_mask_faith
```

OVERLORD QUESTS therefore adds the narrow provider field:

```json
"required_head_items": [
  "mowziesmobs:umvuthana_mask_fury",
  "mowziesmobs:umvuthana_mask_fear",
  "mowziesmobs:umvuthana_mask_rage",
  "mowziesmobs:umvuthana_mask_bliss",
  "mowziesmobs:umvuthana_mask_misery",
  "mowziesmobs:umvuthana_mask_faith"
]
```

At least one listed item must occupy the player's head slot for acceptance and turn-in. This is a generic registry-ID gate and introduces no class-link dependency on Mowzie's Mobs.

## Native misbehaviour authority

The installed Umvuthi exposes the public method:

```text
getMisbehavedPlayerId()
```

Mowzie's own `ServerEventHandler` calls its Umvuthana aggro routine for protected-Grove offences. That routine records the offending player's UUID on nearby Umvuthi/Umvuthana entities when its own conditions are met. Its player-target predicate explicitly allows a stored misbehaving player to be targeted even while wearing a mask.

Questlog must not erase this source-native consequence.

The optional `UmvuthiAudienceBridge` therefore reads `getMisbehavedPlayerId()` reflectively. Mowzie's Mobs remains optional at class-link/load time. If the expected reflection surface cannot be read, the bridge fails closed and does not suppress native hostility.

A player Mowzie has marked as misbehaving is also rejected from the peaceful Questlog provider path on the designated Umvuthi. Putting the mask back on is not a loophole around native offence state.

## Post-audience neutrality

Completing `campaign/civilizations/umvuthana/first_contact` writes:

```text
overlord_reign:civilizations/umvuthana/contact_established
```

and sets the local civilization disposition to:

```text
overlord_reign:neutral
```

That disposition change is source-compatible story state, not a global Mowzie AI rewrite.

Forge's `LivingChangeTargetEvent` is used only when all of the following are true:

- the targeting entity is exactly `mowziesmobs:umvuthi`;
- it carries `overlord_anchor:umvuthana_main_umvuthi`;
- the target is the server player;
- the current Umvuthana disposition is `overlord_reign:neutral` or `overlord_reign:subjugated`;
- the exact Mowzie misbehaviour API was successfully read;
- Mowzie has not marked that player as the Umvuthi's misbehaving player.

When those conditions hold, ordinary target acquisition is canceled. No other Umvuthi, Grove, player target, or Mowzie entity is changed.

This implements the canon rule that the mask is no longer a permanent requirement for basic peaceful presence after the audience while preserving native retaliation when the Overlord gives the Grove cause to become hostile.

## Native boss/destructive route boundary

The integration does not remove Umvuthi's boss behavior, combat abilities, native rewards, or destructive route.

Before the peaceful audience, native hostility remains authoritative except for Mowzie's own mask recognition.

After the audience, native offence tracking can still make the canonical Umvuthi target the offending Overlord. Quest-critical damage protection remains a separate campaign/world-integration mechanism and may be removed only when an authored destructive state explicitly permits the canonical anchor to be killed.

## Production boundary

This audit establishes enough technical support for the first peaceful audience:

- exact installed Mowzie's Mobs 1.8.2 artifact identity is known;
- exact Umvuthi registry ID is known;
- exact mask recognition is verified from installed bytecode;
- exact six installed mask registry IDs are known;
- native trading uses the same mask family;
- exact public misbehaviour UUID method is known;
- native offence handling records player misbehaviour;
- Forge 1.20.1 exposes a cancelable `LivingChangeTargetEvent`, allowing a narrow post-audience target exception without replacing Umvuthi AI.

It does NOT establish or implement:

- final Grove coordinates;
- universal peace with all Umvuthis;
- automatic subjugation;
- removal of the native boss/destructive path;
- permanent immunity after the player provokes the canonical Grove;
- a new Umvuthana mask or duplicate trading system;
- later Grove services, tribute, rewards, or political branches.
