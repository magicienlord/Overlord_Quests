# OVERLORD QUESTS Villager Provider Integration

Status: TECHNICAL IMPLEMENTATION OF APPROVED CIVILIZATION DESIGN

Date: 2026-09-12

## Authority

`magicienlord/Overlord_Lore_and_Canon` establishes that present human civilization follows Minecraft's decentralized village model and that all Villager professions, including compatible professions added by installed mods, may serve as sidequest-provider roles when a quest fits the profession and location.

This document records the technical provider mapping only. It does not establish a canonical human civilization resource ID, a settlement, a sidequest, a disposition transition, or any provider dialogue.

## Native role matching

The provider `role` field now has a native Villager path in addition to the explicit scoreboard-tag path.

Example:

```json
"provider": {
  "entity_types": ["minecraft:villager"],
  "role": "minecraft:farmer"
}
```

For Villagers, the server reads the entity's registered `VillagerProfession` ID and compares it to the authored role. A production definition should use the namespaced profession ID.

The older generic path remains valid:

```text
overlord_role:<role>
```

That path is still required for custom NPC roles that are not represented by a Villager profession registry entry.

## Why this is preferable to per-entity tags

Profession-specific human sidequests should not require every naturally generated Villager to be manually tagged by commands or world-editing logic.

Using the native profession registry preserves:

- naturally generated Villagers;
- profession changes performed by normal Minecraft mechanics before a quest is accepted;
- compatible modded professions;
- server-authoritative provider eligibility;
- the generalized provider framework without hard-coding a vanilla profession allow-list.

After a provider quest is accepted, ordinary provider-binding rules remain unchanged. A `same_provider` quest is bound to the issuing entity UUID rather than to whichever Villager later happens to have the same profession.

## Installed VillagersPlus professions

The current OVERLORD REIGN modpack contains:

```text
villagersplus-forge-mc1.20.1-4.0.0.jar
```

Inspection of its registered profession surface confirms the following five additional profession paths:

```text
villagersplus:horticulturist
villagersplus:occultist
villagersplus:oceanographer
villagersplus:alchemist
villagersplus:miner
```

These are technical registry targets only. Their presence does not automatically create sidequests or determine how those professions behave in REIGN dialogue.

Production authoring may target them when the hidden campaign or sidequest corpus has a source-supported reason to do so.

## Development validation

`examples/questlog/quests/overlord_provider_profession_dev.json` uses:

```text
minecraft:farmer
```

as a non-canon deterministic fixture.

The runtime acceptance requirement is:

1. a Farmer Villager with no `overlord_role` scoreboard tag exposes the fixture;
2. a non-Farmer Villager with no explicit role tag does not expose it;
3. accepting the fixture still creates the normal durable provider binding;
4. the native role path does not alter ordinary trading or non-sneaking interaction.

The repository validator also guards the source contract that provider role matching retains both the explicit scoreboard-tag bridge and the native Villager profession lookup.

## Production boundary

This implementation removes a technical obstacle to profession-specific human sidequests. It does not resolve the still-content-owned decisions for:

- exact sidequest text;
- which main-quest markers expose which local pools;
- settlement-specific facts;
- civilization disposition transitions;
- tribute/service consequences;
- reconciliation or destruction branches.

Those remain authored campaign content under the existing lore/source authority and spoiler firewall.