# OVERLORD REIGN Illager Bastille Integration

Status: TECHNICAL IMPLEMENTATION OF APPROVED ILLAGER QUEST ARCHITECTURE

## Authority

The newer OVERLORD REIGN civilization decisions establish:

- Illagers are decentralized warbands rather than one universal nation;
- one designated Take a Pillage Bastille is the principal Illager civilization quest anchor;
- unrelated patrols, raids, camps, mansions, outposts, and Bastilles must not automatically start the main Illager arc;
- the opening relationship is hostile;
- the Overlord establishes authority through force, intimidation, leadership defeat, or a related hostile action;
- the later political outcome remains separate from this initial assertion of authority.

This integration implements only that opening boundary.

## Installed source authority

The audited installed artifact is:

```text
takesapillage-1.0.3-1.20.1.jar
SHA-256 b8ebc7ea467637dc918ffa9c8eaa273f8723e6e81be93cf5f69618f8072baa11
```

The exact installed JAR confirms:

- structure ID `takesapillage:bastille`;
- advancement ID `takesapillage:bastille`, awarded by the vanilla `minecraft:location` trigger while the player is inside a Bastille;
- native entity types `takesapillage:legioner`, `takesapillage:skirmisher`, and `takesapillage:archer`;
- the Bastille `illager` template pool delegates to `takesapillage:mob_feature_soldier`;
- that soldier feature draws from the mod's `BASTILLE_LIST`;
- `takesapillage:legioner` is the strongest weighted custom entry in that list at weight 15, followed by Skirmisher at 12 and Archer at 8, alongside lower-weight vanilla Illager entries.

No dedicated Bastille-leader entity type or native commander role was found in the installed 1.0.3 structure, entity registry, or Bastille spawn-pool implementation.

## Authored commander boundary

Because Take a Pillage does not define a native Bastille leader, OVERLORD REIGN does not pretend that every Legioner is a commander.

World integration selects exactly one Legioner in the designated Bastille and gives it this persistent authored identity tag:

```text
overlord_anchor:illager_bastille_commander
```

That tag means only:

- this existing native Legioner is the local command figure chosen for the authored REIGN Bastille anchor.

It does not mean:

- Legioners are canonically the universal leaders of Illagers;
- other Bastilles use the same commander;
- every Legioner belongs to the designated polity;
- Take a Pillage itself defines this role.

The local command role is an OVERLORD REIGN authoring layer on a source-backed elite Bastille soldier.

## Bastille advancement boundary

The native `takesapillage:bastille` advancement is valid source evidence that a player entered a Bastille. It does not identify which Bastille was entered.

Because REIGN intentionally distinguishes one designated Bastille from unrelated Bastilles, the production main-arc opener does not use that global advancement as proof of the designated anchor. Doing so would let an unrelated Bastille satisfy part of the principal civilization quest and would overstate what the native signal actually proves.

The advancement remains untouched and continues to function as Take a Pillage intended. A later sidequest, optional objective, or world-integration layer may use it where the distinction between Bastilles is irrelevant.

## Sequence-breaking

The commander is intentionally killable before the formal campaign quest becomes visible. Therefore it must not rely on `overlord_quest_protected` during the legitimate hostile opening window.

The production quest uses one exact historical signal:

```text
questlog:entity_kill_history
entity = takesapillage:legioner
scoreboard_tag = overlord_anchor:illager_bastille_commander
```

The scoreboard tag supplies the local identity that the native advancement cannot.

`entity_kill_history` observes the real player-attributed death event from the moment the bundled definition is loaded, even while its parent quest remains locked, and persists the observation. It does not reconstruct kills from before OVERLORD QUESTS was installed or before the definition existed.

This avoids respawning, replacing, or asking the player to kill a second commander merely because the central campaign had not yet formally directed them to the Bastille.

## Production opening quest

Bundled quest:

```text
questlog:campaign/civilizations/illagers/break_the_bastille
```

It requires the initial reign foundation fact and then recognizes the exact marked commander kill. The selected commander's authored identity ties that event to the designated Bastille polity without pretending a global structure advancement can identify one generated instance.

On completion it sets:

```text
overlord_reign:civilizations/illagers/authority_established
```

The fact means that the designated Bastille's local command has been broken by the Overlord.

It does not set civilization disposition. In particular, it does not mean the Bastille is already NEUTRAL, SUBJUGATED, HOSTILE as a resolved political state, or destroyed. The approved fearful/cowed intermediary phase and later resolution remain separate authored work.

## Locality rule

This opening is scoped to one designated Bastille. Unrelated Illagers and other Bastilles remain independent and retain their native behavior unless separately affected by authored content.

No global Illager AI, raid behavior, Villager hostility, or Take a Pillage spawn logic is modified by this integration.
