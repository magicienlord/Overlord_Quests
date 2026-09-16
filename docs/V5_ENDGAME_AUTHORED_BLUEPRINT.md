# OVERLORD REIGN V5 Endgame Authored Blueprint

Status: V5 QUEST BLUEPRINT

Purpose: define the central campaign transition from the completed Bosses'Rise phase into the Cataclysm Dimension expedition, Ender Dragon resolution, one-time Gnarl ending presentation, and persistent post-credits state.

No final quest names or final dialogue are assigned here. Beat IDs are authoring references.

## 1. Authority summary

The End is the surviving Cataclysm Dimension / dimensional Wasteland associated with the Great Cataclysm.

The dimension persists after the central campaign.

The Ender Dragon is the dominant creature and living anchor sustaining the still-active wound. Its defeat stabilizes the wound and stops the active source of new leakage. It does not erase historical consequences already present in the world.

The central campaign does not require civilization resolution, optional Adventure completion, Outer End completion, Fathoms completion, or Myrmex completion before this sequence.

## 2. Entry condition

The Endgame sequence becomes authored-central content only after `BR-SYNTHESIS` completes.

Native access to the End must not be artificially disabled before that point. A player who reaches or defeats the Ender Dragon early is sequence-breaking legitimate Minecraft progression and must be reconciled rather than punished.

## 3. END-010: Enter the Cataclysm Dimension

Status: PLANNED

Prerequisite:

- `BR-SYNTHESIS` complete.

Campaign purpose:

- move from inference about the dimensional wound to direct source-side investigation;
- make clear before the mechanical climax that the End is the Cataclysm Dimension / dimensional Wasteland rather than an unrelated late-game realm;
- identify the Ender Dragon as the living anchor sustaining the active wound.

Visible player accomplishment:

- enter `minecraft:the_end`.

Authoritative completion signal:

- Questlog history-aware End dimension visit signal.

Presentation:

- Gnarl remains the principal campaign voice;
- entry confirms that the dimension reached is the source-side Cataclysmic wound already inferred from Bosses'Rise;
- the Dragon's role as the living anchor is established before the player is asked to defeat it;
- the campaign does not claim that all End ecology, every End structure, or every End creature exists solely because of the Dragon.

Completion state:

- arm the central ending presentation through the server-authoritative campaign state;
- mark the End expedition as source-side contact established.

Unlocks:

- `END-020`.

Sequence breaking:

- if the player entered the End before this beat became active, the history-aware dimension signal satisfies the accomplishment retrospectively;
- the one-time source-side interpretation presentation still occurs so that the campaign meaning is not skipped.

Native progression deliberately not duplicated:

- Eyes of Ender;
- Stronghold search;
- portal activation;
- ordinary End survival;
- End crystal tactics.

Those remain Minecraft progression rather than Questlog tutorial steps.

## 4. END-020: Break the living anchor

Status: PLANNED

Prerequisite:

- `END-010` complete or retrospectively reconciled.

Campaign purpose:

- deliver the central campaign's final mechanical resolution through Minecraft's Ender Dragon fight;
- stabilize the Great Cataclysm's still-active dimensional wound.

Visible player accomplishment:

- defeat the Ender Dragon.

Authoritative completion signal:

- `questlog:ender_dragon_defeated`, reading the world's persistent `EndDragonFight.hasPreviouslyKilledDragon()` state.

Completion state:

- record the central campaign as mechanically complete;
- record that the active wound has been stabilized and new source-side leakage has stopped;
- do not record the End as destroyed, sealed, or inaccessible.

World consequence:

- the Cataclysm Dimension persists;
- existing dragons persist;
- Myrmex persist;
- Fathoms corruption and other oceanic contamination persist;
- altered ecology and historical ruins persist;
- Bosses'Rise consequences already present in the world remain part of history;
- civilization arcs and optional Adventures remain available.

Sequence breaking:

- if Minecraft reports that the Ender Dragon was already legitimately defeated before this authored stage, the beat completes from world history;
- the Dragon is not respawned or re-killed merely for Questlog;
- the same ending presentation becomes eligible after the authored prerequisites are reconciled.

## 5. END-PRESENTATION: Gnarl's central ending

Status: PLANNED PRESENTATION, NOT A SEPARATE GRIND QUEST

Trigger:

- `END-020` becomes complete;
- server-authoritative ending state is armed;
- the one-time ending presentation has not already been shown.

Purpose:

- unmistakably close the central campaign;
- interpret the Dragon's defeat in OVERLORD REIGN terms;
- establish that the Overlord's reign continues in the same persistent world;
- transition the campaign into Post-Credits rather than an ending reload or New Game+.

Principal speaker:

- Gnarl.

Required conclusions:

- the Ender Dragon is dead;
- the living anchor sustaining the active wound has been broken;
- new active leakage has been stopped / the wound has been stabilized;
- the Cataclysm Dimension remains accessible;
- centuries of consequences are not magically undone;
- the current Overlord remains active and established rather than retiring or disappearing;
- unfinished civilizations, Adventures, magic, Tower development, exploration, and sandbox goals remain valid.

Remembered-fact policy:

- the ending may acknowledge selected established facts the player actually caused or discovered;
- it must never report a completion percentage, morality score, alignment rating, civilization count, sidequest score, or overall judgment of the player's reign;
- optional evidence must be acknowledged only when its fact is actually present;
- exact remembered facts and their presentation lines will be finalized after the full V5 authored campaign roster is complete so the ending does not privilege arcs merely because they were authored earlier.

Presentation transport:

- reuse the existing one-time OVERLORD ending-screen transport;
- the current production text is only a scaffold and is not V5 final narration;
- preserving Minecraft's normal continuation callback remains mandatory.

## 6. Post-credits campaign state

After the ending presentation:

- the same world continues;
- the Overlord remains the established active Master;
- unresolved civilization state questlines remain available;
- unresolved Adventures remain available;
- Tower development continues;
- Dragon Den / Dragon Forge progression may continue if unfinished;
- magic and farming progression may continue if unfinished;
- the Outer End becomes a natural major Post-Credits exploration space, while content already encountered there before the ending remains recognized;
- new major Post-Credits Adventures may exist without pretending the central campaign was incomplete.

No permanent global `game over` state should suppress ordinary authored content merely because the central campaign is complete.

## 7. Outer End boundary

The Outer End and related End extension content belong to the native ecology and structures of the Cataclysm Dimension.

V5 does not invent a second origin explanation for them.

Their dedicated authored exploration is mainly Post-Credits, but the dimension remains physically accessible through normal Minecraft rules. Early discovery is therefore sequence-break valid and may be acknowledged later.

The central ending does not require:

- visiting every End biome;
- clearing every End structure;
- completing End City progression;
- acquiring Elytra;
- exhausting The Outer End content.

Those are separate exploration or Post-Credits concerns.

## 8. Technical inheritance

V5 retains these already-implemented technical capabilities:

- history-aware End dimension entry;
- world-level prior Dragon defeat observation;
- server-authoritative ending-arm state;
- one-time presentation latch separate from narrative canon;
- replacement of the vanilla End-poem transition with the OVERLORD presentation in supported single-player context;
- direct presentation if the Dragon was defeated before the authored ending became ready.

V5 does not inherit the older production prerequisite chain merely because those transport systems were first implemented around it. The authored prerequisite is now the Bosses'Rise convergence and `BR-SYNTHESIS` defined by V5.
