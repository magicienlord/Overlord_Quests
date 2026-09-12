# NPC provider sidequest system

Status: TECHNICAL / IMPLEMENTATION SCAFFOLD / NOT STORY CANON

This document records the current OVERLORD QUESTS NPC-provider architecture. It does not define canonical civilizations, sidequest text, chronology, rewards, named quest givers, settlements, or final interaction presentation.

## Purpose

The provider layer adapts the useful generalized quest-giver concept identified in the Villager Retaliation reference without importing its reputation system as the governing progression model.

Main-quest progression can gate sidequests through explicit quest markers and world narrative facts. Civilization outcomes are represented by authored disposition states rather than a numeric reputation meter. The provider system remains server-authoritative even though OVERLORD REIGN targets local single-player.

## Definition surface

A quest becomes provider-bound by adding a top-level `provider` object.

Supported fields currently include:

- `entity_types`: exact provider entity IDs;
- `entity_type_tags`: provider entity tags;
- `scoreboard_tags`: additional required entity scoreboard tags;
- `role`: optional logical role. It matches an explicit `overlord_role:<role>` entity scoreboard tag and, for Villagers, can also match the Villager's registered profession directly;
- `dimensions`: optional dimension allow-list;
- `location`: optional inclusive block-coordinate bounds using `min` and `max` three-integer arrays;
- `unlock_quests`: quest IDs that must already be complete before this sidequest may be accepted;
- `required_facts`: explicit world narrative facts that must already be present;
- `forbidden_facts`: explicit world narrative facts that must still be absent;
- `required_dispositions`: map of civilization IDs to one or more allowed authored disposition-state IDs;
- `dialogue`: optional authored state-specific NPC lines for `offer`, `in_progress`, `ready_to_turn_in`, and `failed`;
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

`required_facts` and `forbidden_facts` use world-scoped explicit narrative facts described in `docs/NARRATIVE_FACTS.md`. A rule cannot require and forbid the same fact. These fields are for sparse authored consequences, not a replacement reputation score.

`pool` is currently parsed and retained as definition metadata only. There is no random, weighted, rotating, daily, cooldown, or limited-capacity pool scheduler yet. Production quest design must not assume those behaviors until they are explicitly implemented.

## Provider roles and Villager professions

The `role` field has two compatible matching paths.

For a generic provider entity, a role such as:

```json
"role": "smith"
```

matches an entity carrying:

```text
overlord_role:smith
```

This preserves the explicit scoreboard-tag bridge for custom NPCs, manually authored providers, and entities whose source mod exposes no stable profession registry.

For a `minecraft:villager`, the same field also checks the Villager's registered profession. Production definitions should prefer the full namespaced profession ID:

```json
"role": "minecraft:farmer"
```

A bare path such as `farmer` is accepted for compatibility, but namespaced IDs are preferred because modded profession paths may collide.

The lookup uses Minecraft's Villager profession registry rather than a hard-coded vanilla profession list. Compatible modded professions can therefore be targeted through their actual registered IDs without assigning an `overlord_role` tag to every Villager instance.

The native profession path is additive. An explicit `overlord_role:<role>` tag still matches first, so authored NPC roles are not replaced by Villager profession semantics.

This implements the approved REIGN rule that Villager professions, including compatible modded professions, may serve as sidequest-provider roles when the quest fits the profession and location. It does not automatically generate quests for every profession.

## Production civilization anchor scope

The lore authority defines civilization questlines around deliberately authored anchor populations. Ordinary procedural settlements, ordinary profession matches, and unrelated members of the same species must not become civilization quest starters merely because they satisfy a broad entity selector.

The repository validator therefore applies an additional rule to bundled production definitions that use `provider.civilization`:

- the provider must include at least one non-empty `scoreboard_tags` selector or an authored `location` bound;
- entity type, entity tag, profession/role, dimension, disposition, and campaign gates may narrow eligibility further, but none of them alone proves membership in the canonical anchor population;
- development fixtures are exempt so synthetic provider mechanics can be tested without creating fake world anchors.

A scoreboard tag is the preferred pre-placement bridge when the lore establishes the anchor identity but final world coordinates remain UNKNOWN. For example, world integration may mark the intended anchor NPC or anchor population with an explicit authored tag while leaving all naturally generated peers untouched.

A location bound is appropriate only once world integration has a stable authored area. Coordinates in provider definitions remain implementation data and do not become canonical geography merely because the runtime can match them.

This guard implements the anchor rule from `Overlord_Lore_and_Canon/reference/16_CIVILIZATION_QUEST_ANCHORS.md`. It is deliberately conservative: production civilization content must identify the intended local polity rather than accidentally applying to every matching NPC in generated terrain.

## Authored dialogue and decline flow

The temporary provider screen now separates selecting an offered sidequest from accepting it. Selecting an `AVAILABLE` entry opens its neutral detail view. The player must then choose `Accept` or `Decline`; decline is intentionally non-persistent and simply returns to the provider's list. It does not create a hidden rejection score, cooldown, mood, or reputation fact.

The optional `dialogue` object is definition-owned content. Each supported phase may be a single non-empty string or a non-empty list of strings:

```json
"dialogue": {
  "offer": "Authored offer text.",
  "in_progress": ["Authored reminder line one.", "Authored reminder line two."],
  "ready_to_turn_in": "Authored completion hand-in text.",
  "failed": "Authored failure response."
}
```

The engine does not generate missing dialogue and does not treat the quest journal description as spoken NPC text. If a phase has no authored dialogue, the neutral scaffold simply shows the quest state and controls without inventing speech. This preserves the distinction between provider/source identity and narrative text authored for that provider interaction.

Authored dialogue is wrapped to the temporary detail view. If the wrapped content exceeds the available vertical region, the client now exposes neutral `Up` and `Down` controls and mouse-wheel scrolling instead of silently dropping the remaining lines. Scroll position is presentation-only state. It resets when the player selects a different offer, returns to the provider list, or the selected quest changes server-derived interaction state. Provider eligibility and quest state remain authoritative on the server.

This is not a branching dialogue-tree engine. It is the minimal state-aware presentation surface required by the planned NPC offer, accept/decline, dialogue, and turn-in flow. More elaborate conversation structures should be added only if approved quest design actually requires them.

## Runtime authority

Provider eligibility is calculated on the logical server.

Acceptance checks:

- active server QuestManager;
- matching provider entity selector;
- allowed dimension, if defined;
- authored location bounds, if defined;
- required scoreboard tags and logical role, if defined;
- for Villagers, registered profession matching when the authored role names that profession;
- completed ordinary prerequisites;
- completed `unlock_quests` markers;
- all `required_facts` present;
- every `forbidden_facts` entry absent;
- required authored civilization dispositions;
- quest not already provider-bound;
- quest not failed.

Accepting a quest records a durable provider binding containing the provider UUID, entity type, dimension, block position, display name, optional civilization metadata, and role metadata. The binding is stored in quest NBT and survives save/load and definition-manager reloads when the quest remains compatible.

Provider turn-in is also server-authoritative. `same_provider` requires the exact stored UUID. `any_eligible` requires a currently eligible provider entity matching the provider rule. `none` means the provider does not gate final quest completion after the objectives are complete.

Narrative requirements such as `required_dispositions`, `unlock_quests`, `required_facts`, and `forbidden_facts` gate acceptance. They are not re-applied to an already accepted quest during turn-in. This avoids silently orphaning an in-progress sidequest if later world progression changes narrative state. If a later fact should invalidate or redirect an active quest, that consequence must be authored explicitly. Entity, dimension, location, role/tag, and provider-identity rules still apply where required by the selected turn-in mode.

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

Provider snapshots are capped at 256 entries. The temporary client presentation paginates seven entries at a time, preserves the current list page when the same provider refreshes, clears the pending-action lock after a server refresh, provides bounded scrolling for overflowing authored dialogue, and closes automatically if the provider disappears, dies, changes identity, or moves outside the interaction boundary.

## Civilization disposition bridge

OVERLORD QUESTS stores civilization disposition as explicit world-scoped resource IDs. This intentionally avoids recreating Villager Retaliation's generalized numeric reputation meter.

The engine currently exposes:

- `questlog:disposition` objective;
- `questlog:set_disposition` reward;
- provider `required_dispositions` gating.

The actual civilization IDs and legal state IDs are authored content. The engine does not define canonical states beyond its technical unresolved fallback.

Permission-gated development/admin commands expose the stored world state directly:

```text
/questlog narrative disposition get <civilization>
/questlog narrative disposition set <civilization> <state>
/questlog narrative disposition clear <civilization>
```

`set` and `clear` immediately re-synchronize active quest state so disposition objectives and provider eligibility do not wait for an unrelated quest event. These commands are technical authoring/validation tools. They do not define which civilizations or states are canon.

This permits future quest branches to settle a civilization into authored outcomes such as hostility, neutrality, or a domination/gift-giving state without requiring a continuously varying reputation score. Those concrete outcomes remain content decisions rather than generic engine constants.

## Explicit narrative facts

The same world-scoped narrative storage now also supports monotonic named facts for event history and sparse conditional consequences:

- `questlog:fact` objective;
- `questlog:set_fact` reward;
- provider `required_facts` gating;
- provider `forbidden_facts` gating.

Facts are not a second reputation system. They are boolean named statements that later authored content may inspect. Production quest rewards only add them. An administrative clear command exists for testing and authoring recovery, not as normal campaign behavior.

See `docs/NARRATIVE_FACTS.md` for the full authoring and persistence contract.

## Deliberately excluded systems

The provider layer does not currently implement:

- autonomous random quest generation;
- numeric reputation accumulation;
- branching dialogue trees;
- procedural quest text;
- automatic civilization identification from lore assumptions;
- daily or timed quest rotation;
- provider inventory/economy systems;
- generated rewards;
- final NPC quest-marker art or final dialogue presentation styling.

Those systems must not be inferred merely because the reference mod contained broader quest-provider or reputation behavior.

## Development fixtures

`examples/questlog/quests/overlord_provider_dev.json` is an implementation-only fixture using a vanilla villager and a debug-stick objective. It exercises provider acceptance, persistence, same-provider turn-in, refresh behavior, authored dialogue overflow scrolling, and interaction safety. Its deliberately long `[DEV]` offer dialogue ends with a sentinel line used only to prove that overflow content remains reachable.

`examples/questlog/quests/overlord_provider_profession_dev.json` is an implementation-only Farmer Villager fixture. It verifies that `role: minecraft:farmer` can match the Villager's native registered profession without requiring an `overlord_role:minecraft:farmer` scoreboard tag.

`examples/questlog/quests/overlord_provider_disposition_dev.json` is an implementation-only fixture gated by the synthetic IDs `questlog:dev_civilization` and `questlog:dev_open`. Those identifiers exist only to validate the disposition bridge and establish no setting canon.

`examples/questlog/quests/overlord_provider_fact_dev.json` is an implementation-only fixture gated by synthetic required and forbidden narrative facts. `examples/questlog/quests/overlord_narrative_fact_dev.json` separately exercises a fact prerequisite and an auto-claimed fact reward.

None of these fixtures is bundled as production quest content.

Manual provider validation is defined in `docs/NPC_PROVIDER_TEST_PROTOCOL.md`.
