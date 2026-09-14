# OVERLORD REIGN Illager Bastille Integration

Status: TECHNICAL IMPLEMENTATION OF APPROVED ILLAGER QUEST ARCHITECTURE

## Authority

The OVERLORD REIGN civilization decisions establish a deliberately local Illager arc:

- Illagers are decentralized warbands rather than one universal nation;
- one designated Take a Pillage Bastille is the principal Illager civilization quest anchor;
- unrelated patrols, raids, camps, mansions, outposts and Bastilles do not automatically belong to that polity;
- the opening relationship is hostile;
- the Overlord first establishes authority through force;
- afterward the surviving Bastille can enter a fearful/cowed intermediary state in which peaceful interaction becomes possible;
- later political outcomes remain separate from this initial cowed peace.

The exact survivor used as post-Bastille intermediary is Quest-Maker / technical discretion. It is not an unanswered lore question.

## Installed source authority

The audited Take a Pillage artifact is:

```text
takesapillage-1.0.3-1.20.1.jar
SHA-256 b8ebc7ea467637dc918ffa9c8eaa273f8723e6e81be93cf5f69618f8072baa11
```

It provides the Bastille structure and the native `takesapillage:legioner`, `takesapillage:skirmisher` and `takesapillage:archer` soldiers. No dedicated Bastille-leader entity type or native commander role was found in the installed implementation.

## Hostile opening

World integration selects exactly one native Legioner in the designated Bastille and marks it:

```text
overlord_anchor:illager_bastille_commander
```

The bundled opening quest is:

```text
questlog:campaign/civilizations/illagers/break_the_bastille
```

It uses persistent `questlog:entity_kill_history` for that exact tagged `takesapillage:legioner`, so killing the commander before the journal formally exposes the quest remains recoverable after the definition is loaded.

Completion records:

```text
overlord_reign:civilizations/illagers/authority_established
```

This means the designated Bastille's local command has been broken. The opening does not set civilization disposition; it only establishes the hostile authority milestone.

The native global `takesapillage:bastille` advancement is deliberately not used to identify the designated REIGN Bastille because it does not identify which Bastille was entered. A global structure signal cannot distinguish one generated Bastille from another.

## Fearful/cowed continuation

World integration selects one surviving vanilla Pillager at the same designated Bastille and gives it both tags:

```text
overlord_anchor:illager_bastille_intermediary
overlord_quest_protected
```

The use of `minecraft:pillager` is a technical local representation, not a declaration that Pillagers are a universal Illager diplomatic caste.

After `authority_established`, `IllagerBastilleAudienceBridgeForge` suppresses player targeting and attacks only from this exact protected intermediary. It does not alter other Pillagers, other Illager types, raids, patrols or unrelated Bastilles.

The continuation quest is:

```text
questlog:campaign/civilizations/illagers/the_bastille_bows
```

The peaceful provider interaction records:

```text
overlord_reign:civilizations/illagers/bastille_cowed
```

and sets the designated Illager civilization state to:

```text
overlord_reign:neutral
```

Here NEUTRAL is the generalized runtime disposition under a more specific historical fact: the Bastille is fearful/cowed. It should not be narrated as friendship, alliance or voluntary reconciliation.

The local polity may later move to another established disposition when future authored consequences justify it. This quest does not automatically create a SUBJUGATED outcome merely because force preceded the audience.

## Locality and protection rules

The commander remains legitimately killable during the hostile opening and therefore must not be protected in a way that blocks the required kill.

The post-authority intermediary is deliberately protected because its role is to survive as a continuing provider after the combat phase.

Neither marker applies globally. Ordinary Illager behavior remains native everywhere else.

## Runtime qualification protocol

1. In the designated Bastille, confirm exactly one intended `takesapillage:legioner` has `overlord_anchor:illager_bastille_commander`.
2. Confirm one surviving `minecraft:pillager` has both `overlord_anchor:illager_bastille_intermediary` and `overlord_quest_protected`.
3. Before authority is established, confirm the Bastille remains hostile and the commander can be killed normally.
4. Kill the marked commander and verify `overlord_reign:civilizations/illagers/authority_established` persists.
5. After that fact exists, confirm the marked protected intermediary no longer targets or successfully attacks the player.
6. Confirm an unmarked Pillager in the same or another encounter remains native and is not globally pacified.
7. Interact with the marked intermediary and complete `The Bastille Bows`.
8. Confirm `overlord_reign:civilizations/illagers/bastille_cowed` is persisted and the Illager disposition becomes `overlord_reign:neutral`.
9. Save/reload and verify the local provider remains usable while unrelated Illagers remain unaffected.
10. Confirm the protected intermediary cannot be accidentally killed through ordinary combat.

This validates the designated local Bastille only; it establishes no universal Illager government or global truce.
