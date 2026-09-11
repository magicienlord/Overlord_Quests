# NPC provider sidequest system

Status: TECHNICAL / IMPLEMENTATION SCAFFOLD / NOT STORY CANON

This document records the current OVERLORD QUESTS NPC-provider architecture. It does not define canonical civilizations, sidequest text, chronology, rewards, named quest givers, settlements, or final interaction presentation.

## Purpose

The provider layer adapts the useful generalized quest-giver concept identified in the Villager Retaliation reference without importing its reputation system as the governing progression model.

Main-quest progression can gate sidequests through explicit quest markers. Civilization outcomes are represented by authored disposition states rather than a numeric reputation meter. The provider system remains server-authoritative even though OVERLORD REIGN targets local single-player.

## Definition surface

A quest becomes provider-bound by adding a top-level `provider` object.

Supported fields currently include:

- `entity_types`: exact provider entity IDs;
- `entity_type_tags`: provider entity tags;
- `scoreboard_tags`: additional required entity scoreboard tags;
- `role`: optional logical role, matched through an `overlord_role:<role>` entity scoreboard tag;
- `dimensions`: optional dimension allow-list;
- `location`: optional inclusive block-coordinate bounds using `min` and `max` three-integer arrays;
- `unlock_quests`: quest IDs that must already be complete before this sidequest may be accepted;
- `required_dispositions`: map of civilization IDs to one or more allowed authored disposition-state IDs;
- `civilization`: optional provider/civilization metadata stored in the provider binding;
- `pool`: optional authored pool identifier reserved as metadata;
- `lock_to_provider`: whether the accepted quest should remain visibly associated with the issuing provider;
- `turn_in`: `none`, `same_provider`, or `any_eligible`.

At least one `entity_types` or `entity_type_tags` selector is required.

Location bounds are purely authoring data. They do not establish any canonical settlement coordinates. A rule such as the following means only that an eligible provider entity must currently stand inside that inclusive box:

```json
"location": {
  "min": [-32, 48, -32],
  "max": [32, 128, 32]
}
```

The runtime normalizes reversed coordinate pairs, so `min` and `max` describe the two opposite corners rather than requiring authors to pre-sort every axis. `dimensions` remains a separate selector and should be used with `location` when the same coordinate box must not match another dimension.

`unlock_quests` is definition gating, not quest auto-start. Repository-controlled definitions should use explicit namespaced quest IDs. External definitions with a bare unlock quest ID are normalized into the retained `questlog` namespace rather than accidentally becoming `minecraft:<id>`.

`pool` is currently parsed and retained as definition metadata only. There is no random, weighted, rotating, daily, cooldown, or limited-capacity pool scheduler yet. Production quest design must not assume those behaviors until they are explicitly implemented.

## Runtime authority

Provider eligibility is calculated on the logical server.

Acceptance checks:

- active server QuestManager;
- matching provider entity selector;
- allowed dimension, if defined;
- authored location bounds, if defined;
- required scoreboard tags and logical role, if defined;
- completed ordinary prerequisites;
- completed `unlock_quests` markers;
- required authored civilization dispositions;
- quest not already provider-bound;
- quest not failed.

Accepting a quest records a durable provider binding containing the provider UUID, entity type, dimension, block position, display name, optional civilization metadata, and role metadata. The binding is stored in quest NBT and survives save/load and definition-manager reloads when the quest remains compatible.

Provider turn-in is also server-authoritative. `same_provider` requires the exact stored UUID. `any_eligible` requires a currently eligible provider entity matching the provider rule. `none` means the provider does not gate final quest completion after the objectives are complete.

Quest reset commands delegate to the provider-aware `Quest.resetProgress()` contract. This clears provider binding and turn-in state along with ordinary objectives, prerequisites, failures, and rewards. The administrative `/questlog trigger` command deliberately refuses to bypass an unaccepted provider binding.

## Interaction protocol

The current temporary Forge interaction is intentionally non-invasive:

- sneak;
- main-hand entity interaction;
- only opens the provider menu when the target currently exposes at least one relevant provider quest.

Ordinary non-sneaking interaction is left untouched so vanilla trading and unrelated mod interactions are not replaced by the scaffold.

The server sends a bounded provider-menu snapshot. The client cannot decide whether a quest is eligible. Client actions contain the provider runtime entity ID, provider UUID, quest ID, and requested action. Before accepting either action the server re-resolves the entity and verifies UUID, alive state, distance, active quest manager, quest existence, and current accept/turn-in eligibility.

The temporary menu supports four server-derived states:

- `AVAILABLE`;
- `IN_PROGRESS`;
- `READY_TO_TURN_IN`;
- `FAILED`.

Provider snapshots are capped at 256 entries. The temporary client presentation paginates seven entries at a time, preserves the current page when the same provider refreshes, clears the pending-action lock after a server refresh, and closes automatically if the provider disappears, dies, changes identity, or moves outside the interaction boundary.

## Civilization disposition bridge

OVERLORD QUESTS stores civilization disposition as explicit world-scoped resource IDs. This intentionally avoids recreating Villager Retaliation's generalized numeric reputation meter.

The engine currently exposes:

- `questlog:disposition` objective;
- `questlog:set_disposition` reward;
- provider `required_dispositions` gating.

The actual civilization IDs and legal state IDs are authored content. The engine does not define canonical states beyond its technical unresolved fallback.

Permission-gated development/admin commands expose the stored world fact directly:

```text
/questlog narrative disposition get <civilization>
/questlog narrative disposition set <civilization> <state>
/questlog narrative disposition clear <civilization>
```

`set` and `clear` immediately re-synchronize active quest state so disposition objectives and provider eligibility do not wait for an unrelated quest event. These commands are technical authoring/validation tools. They do not define which civilizations or states are canon.

This permits future quest branches to settle a civilization into authored outcomes such as hostility, neutrality, or a domination/gift-giving state without requiring a continuously varying reputation score. Those concrete outcomes remain content decisions rather than generic engine constants.

## Deliberately excluded systems

The provider layer does not currently implement:

- autonomous random quest generation;
- numeric reputation accumulation;
- dialogue trees;
- procedural quest text;
- automatic civilization identification from lore assumptions;
- daily or timed quest rotation;
- provider inventory/economy systems;
- generated rewards;
- final NPC quest-marker art or dialogue presentation.

Those systems must not be inferred merely because the reference mod contained broader quest-provider or reputation behavior.

## Development fixtures

`examples/questlog/quests/overlord_provider_dev.json` is an implementation-only fixture using a vanilla villager and a debug-stick objective. It exercises provider acceptance, persistence, same-provider turn-in, refresh behavior, and interaction safety.

`examples/questlog/quests/overlord_provider_disposition_dev.json` is an implementation-only fixture gated by the synthetic IDs `questlog:dev_civilization` and `questlog:dev_open`. Those identifiers exist only to validate the disposition bridge and establish no setting canon.

Neither fixture is bundled as production quest content.

Manual validation is defined in `docs/NPC_PROVIDER_TEST_PROTOCOL.md`.
