# Villager Civilization Integration

Status: IMPLEMENTATION / WORLD-INTEGRATION CONTRACT

This file implements the authority delegated by the OVERLORD REIGN quest references. It does not select a new canonical historical capital or invent a named human ruler.

## Authority boundary

Villagers are the generalized human civilization layer. The principal quest anchor is one deliberately authored, biome-appropriate remnant or successor settlement with continuity to an older human site. The exact inherited site is a world-fit choice; Spree is a strong historical candidate but is not mandatory.

The production quest therefore records local formal contact only. It does not resolve the settlement to neutral, subjugated or hostile, and it does not make that local representative a universal human leader.

## Production anchor

World integration selects one existing vanilla Villager at the designated settlement and gives it both tags:

```text
overlord_anchor:villager_main
overlord_quest_protected
```

No profession is required. This is deliberate: the main representative may be chosen to fit the final settlement rather than forcing a profession into canon merely for technical convenience.

The production quest is:

```text
questlog:campaign/civilizations/villagers/first_contact
```

Completion records:

```text
overlord_reign:civilizations/villagers/contact_established
```

That fact means the designated local human settlement has formally encountered the current Overlord. It does not establish a global Villager disposition or rewrite ordinary Villager behavior elsewhere.

## World-fit rule

The settlement should visibly read as a historical remnant or successor rather than an arbitrary procedural village relabeled after generation. The final biome, architecture and inherited-site identity belong to world integration and must be reconciled against the canon/world files when the location is placed.

The Quest implementation intentionally stores no fixed coordinates and no historical-site name.

## Runtime qualification protocol

1. In a test copy of the final world, choose the intended settlement representative.
2. Confirm it is `minecraft:villager` and has both required tags.
3. Confirm `overlord_reign:reign/initial_foundation_established` is set.
4. Interact with the representative and accept/turn in `A Village That Remembers`.
5. Confirm `overlord_reign:civilizations/villagers/contact_established` is persisted after save/reload.
6. Confirm no Villager disposition is written by this first contact.
7. Confirm an unrelated Villager in another settlement does not expose this main-entry provider interaction.
8. Confirm the protected anchor cannot be accidentally killed through ordinary combat.

This protocol validates the Quest/world boundary only; it does not canonize the test settlement's coordinates.
