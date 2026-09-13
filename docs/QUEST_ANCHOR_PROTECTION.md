# OVERLORD QUESTS quest-anchor protection

Status: TECHNICAL IMPLEMENTATION CONTRACT / STANDALONE RUNTIME QUALIFIED

This document defines the narrow runtime protection used for deliberately authored quest-critical NPC anchors. It does not establish any new character, settlement, civilization outcome, or campaign event.

## Authority

`magicienlord/Overlord_Lore_and_Canon/reference/16_CIVILIZATION_QUEST_ANCHORS.md` establishes several civilization starters as protected, explicitly marked NPCs at deliberately authored anchor locations. The same source also requires ordinary procedural populations to remain independent from those anchors.

The implementation therefore uses explicit opt-in entity state rather than inferring protection from species, profession, provider role, location type, or civilization.

## Runtime tag

An entity is protected only while it carries this scoreboard tag:

```text
overlord_quest_protected
```

The tag is persistent entity NBT through vanilla scoreboard-tag storage.

When a tagged entity is a `Mob`, OVERLORD QUESTS also calls `setPersistenceRequired()` whenever that entity joins a server level. This prevents ordinary Mob despawn from silently removing a deliberately authored quest anchor.

The Forge runtime cancels the normal `LivingAttackEvent` damage path for protected living entities. The check is server-side and does not alter unmarked entities.

## Standalone runtime qualification

The repository includes a dedicated GitHub Actions workflow named `Quest Anchor Protection Smoke`.

That smoke boots the actual Forge development server and drives the test through authenticated localhost RCON. It creates an explicitly tagged living test anchor, verifies that ordinary `/damage ... minecraft:generic` leaves its health unchanged while `overlord_quest_protected` is present, removes only that protection tag, then verifies the same ordinary damage path can kill the released entity.

This qualifies the intended gameplay boundary in the standalone Quest runtime:

- opt-in protected gameplay damage is canceled server-side;
- removing the protection tag immediately restores the normal damage path;
- the release does not require replacing or globally weakening the protection system.

This smoke does not claim that an operator-level entity removal command such as `/kill`, raw entity discard, or another mod's direct removal API is ordinary gameplay damage. Those are administrative or compatibility removal paths outside the `LivingAttackEvent` protection contract and must not be used as the primary assertion that normal quest-anchor protection works.

Full target-instance validation still matters where another installed mod can directly discard, transform, replace, or otherwise remove a quest-critical entity without passing through normal Forge living-attack damage events.

## What protection does not mean

Protection is not a universal rule that important NPCs can never die.

The lore authority explicitly permits destructive routes and permanent losses. If an authored route intentionally makes an anchor killable, world or campaign integration must remove `overlord_quest_protected` at the exact state transition where that destruction becomes legitimate.

The quest engine does not infer that transition from disposition, quest completion, provider state, or entity type. That would risk making an anchor vulnerable at the wrong time or protecting unrelated procedural entities.

The protection layer also does not attempt to repair bad world placement, teleport lost anchors, respawn a deliberately removed entity, reverse a scripted mod removal, or fabricate a ghost continuation. Those behaviors require explicit authored handling if a specific questline needs them.

## World-integration rule

A quest-critical anchor should normally have two distinct markers:

1. a unique anchor identity tag used by provider selectors or world integration;
2. `overlord_quest_protected` while accidental death/despawn would break the unresolved questline.

For example, a future authored anchor may carry a unique identity tag such as `overlord_anchor_example` plus the protection tag. The example identifier is technical documentation only and is not a production campaign ID.

Provider definitions should continue to use the unique anchor identity tag or a final authored location bound. `overlord_quest_protected` must not be used as a provider identity selector because many unrelated quest anchors may legitimately be protected at the same time.

## Deliberate removal

The existing server-authoritative `questlog:command` reward can remove the protection tag from a loaded, explicitly targeted anchor when a production quest genuinely requires that transition. World integration may also remove it during controlled setup or another purpose-built compatibility hook.

The standalone runtime smoke demonstrates the mechanical release boundary by removing only `overlord_quest_protected` and then proving normal damage resumes. It does not define which production quest should perform that release.

Raw command use is not automatically preferred for every campaign transition. If repeated production content demonstrates the need for a stronger cross-dimension or unloaded-entity protection state API, that should be implemented as a dedicated quest contract rather than hidden inside provider eligibility.

## Failure and sequence-break boundary

Protection exists to prevent accidental pre-resolution loss, not to erase legitimate sequence breaking.

If campaign design intentionally allows an anchor to be killed before a particular quest is formally accepted, the protection tag must not be present during that valid kill window, and the questline must use surviving evidence or an authored fallback path as required by `docs/SEQUENCE_BREAK_TRACKING.md`.

Conversely, when the lore design says a specific starter must survive until an audience, choice, or other explicit transition, protection is appropriate because an accidental early death would be a soft-lock rather than meaningful sequence breaking.

## Multiplayer and authority

OVERLORD REIGN targets local single-player, but protection is enforced on the logical server. The client does not decide whether an anchor can take damage.

No protection state is derived from client UI state, popup state, provider menus, or cached quest snapshots.
