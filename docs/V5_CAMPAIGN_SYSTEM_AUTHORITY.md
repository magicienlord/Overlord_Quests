# OVERLORD REIGN V5 Campaign System Authority

Status: WORKING CAMPAIGN AUTHORITY

Date opened: 2026-09-16

Purpose: define the authored OVERLORD REIGN campaign completely enough that the later `Overlord_Quests` implementation pass can translate this document into production content without reopening campaign design.

This is not a continuation, cleanup, or expansion of the current production quest list. Current `Overlord_Quests` content is implementation evidence and may be retained, rewritten, replaced, or removed later according to this authority.

Quest names and final dialogue are deliberately deferred. V5 defines campaign purpose, structure, progression, objectives, branches, persistent state, content allocation, presentation rules, and technical tracking requirements.

## 1. Authority and source discipline

Use this order while authoring V5:

1. the Overlord's latest explicit decision;
2. current OVERLORD REIGN canon and specialized authority files;
3. this V5 document for campaign decisions already consolidated here;
4. verified franchise source evidence;
5. verified installed-mod, JAR, source, advancement, registry, API, and Questlog technical facts;
6. older campaign and production documents only where they have not been superseded.

`magicienlord/Overlord_Lore_and_Canon` remains READ-ONLY.

The live `gnarl-bootstrap` implementation is technical evidence, not V5 campaign authority.

A current production quest does not survive into V5 merely because it already exists.

### 1.1 Question threshold

Technical facts are resolved directly from source where possible. Examples include registry IDs, advancement IDs, structure IDs, objective availability, native state ownership, and API behavior.

The Overlord must be consulted before V5 establishes an unresolved:

- lore fact;
- campaign purpose;
- player-facing progression requirement;
- terminal branch or civilization outcome;
- historical interpretation;
- persistent consequence;
- major reward or loss;
- ending access rule;
- content-allocation decision;
- material change to an established experience.

Do not invent an answer merely to keep authoring moving.

### 1.2 Status vocabulary

Use:

- CANON
- PLANNED
- PROPOSAL
- UNKNOWN

Technical facts may additionally be marked VERIFIED TECHNICAL FACT.

A proposal is never silently promoted.

## 2. V5 authoring contract

V5 is a specification, not a brainstorming dump.

Rules:

- do not name quests yet;
- define objectives, roles, prerequisites, branches, state, and consequences first;
- assume one fresh single-player world played by the Overlord;
- do not shape campaign design around multiplayer, late-install recovery, or save backfill;
- preserve native mod progression where it is meaningful;
- do not turn every mod into a Questlog tutorial;
- do not create filler because a provider or advancement exists;
- use the smallest authored set of objectives that proves the intended campaign accomplishment;
- leave ordinary sandbox play ordinary;
- preserve a persistent playable world after the ending;
- preserve the spoiler firewall during ordinary project discussion.

## 3. Player presentation contract

OVERLORD REIGN must not become "a Quests UI."

Ordinary flow:

1. a quest or provider presents a meaningful objective;
2. the player performs the objective in the world or native mod system;
3. Questlog records completion;
4. the completion presentation closes the authored beat.

Do not use:

- repeated quest-trigger popups;
- periodic or persistent Gnarl reminders for open campaign objectives;
- constant progress-update commentary;
- generic MMO-style quest-marker saturation;
- Ramblings as disguised reminders.

NPC Ramblings are sparse character or world reactions, primarily for meaningful native achievements or discoveries that do not justify full authored quests.

Unused but meaningful native progression may remain native and receive no V5 Questlog entry at all.

Provider source and presenter voice may differ without creating duplicate quest state.

## 4. Campaign topology

The central campaign is one continuous reign, not a visible rigid Act I / Act II / Act III menu.

Internal phases may exist for authoring and validation.

Established central spine:

```text
Awakening / Tower summit
-> Master's Staff + first Brown proof in one opening quest
-> semi-open Tower restoration + Red -> Green -> Blue restoration + side content
-> full defined initial Tower restoration + all four tribes restored
-> five concurrent Bosses'Rise subcampaigns
-> short Gnarl interpretation/preparation transition
-> End / Cataclysm Dimension expedition
-> Ender Dragon
-> Gnarl ending presentation
-> persistent established reign
-> Post-Credits adventures
```

### 4.1 Campaign openness

After the opening, the world becomes semi-open.

Hard locks exist only for genuine capability, lore, world-state, or native-mod dependencies.

Civilization resolution is not a general central-campaign gate.

Adventure and optional content may be encountered naturally before full Tower and Minion restoration where world access permits.

### 4.2 Capability gates

Prefer real capabilities and explicit central-story facts over arbitrary completion counts.

Valid gates may include:

- Minion tribe restoration;
- restored functional Tower infrastructure;
- dimension access;
- required native-mod progression;
- major equipment or artifacts where the story genuinely depends on them;
- central boss states;
- explicit campaign facts.

Do not require a number of resolved civilizations merely to advance the central story.

## 5. State architecture

V5 uses explicit authored state rather than hidden global morality, corruption, reputation, domination, or friendship points.

Use the smallest durable state needed:

- quest completion for ordinary completed beats;
- persistent facts for historical truths later content must remember;
- civilization disposition for exclusive current political state of the authored anchor polity;
- native mod state where that system already owns the authoritative truth;
- provider binding and eligibility for local sidequest ownership;
- sparse choice state where later content must distinguish outcomes.

Do not create a persistent fact for every objective step.

## 6. Native progression and objective selection

When a mod already owns meaningful progression, V5 frames and selects from it rather than replacing it.

For each authored objective:

1. identify the intended player accomplishment;
2. locate the authoritative native signal if one exists;
3. prefer a durable advancement, statistic, state, structure visit, boss death, item craft/use/equip, effect, or owner API signal;
4. use a narrow compatibility bridge only when the authored objective genuinely requires it and no sufficiently specific signal exists;
5. never substitute vague inventory possession for a specific accomplishment when the item has other acquisition paths.

Technical capability does not by itself justify a quest.

## 7. Dark Tower restoration authority

The new Dark Tower manifests as a purpose-built architectural shell. Restoration means installing, furnishing, activating, reconnecting, and making intended functions operational, not repairing an ancient ruined fortress.

Tower Restoration is one overarching campaign line with semi-open internal branches.

Initial room installation belongs to Tower Restoration. Deeper mastery of the housed system belongs to that system's own progression.

### 7.1 Defined initial functional set required before Bosses'Rise

The silent initial Tower-readiness gate requires:

- Throne;
- Forge;
- Storage;
- Armory;
- Treasury;
- WayGates;
- Arena;
- Jail;
- Alchemy;
- Theurgy;
- Gluttony;
- Spell Study;
- Eidolon;
- Biomancy.

Minion Infrastructure is already present/restored and receives no restoration objective.

Dragon Den is excluded from this initial gate and is an independent later Tower development.

There is no visible standalone "Tower complete" quest or popup. Aggregate readiness may be tracked silently.

### 7.2 Approved initial anchors

Current approved functional anchors include:

- Throne: Necrolord Chair from Fantasy's Furniture;
- Treasury: Gold Barrel from Goblins Tyranny;
- Arena: Supplementaries Cage;
- Jail: Big Iron Grate from Abyssal Decor;
- Forge: appropriate Hot Iron functional forge block(s), including Smithing Anvil / Crucible as technically appropriate;
- Storage: Storage Drawers functional storage substrate;
- Armory: multiple installed display families rather than one mandatory rack type;
- WayGates: Waystones implementation, preserving free post-discovery travel;
- magical rooms: the established functional workstation blocks for their systems.

Exact registry IDs remain technical-resolution work.

## 8. Minion restoration authority

All four traditional tribes exist in REIGN and are owned mechanically by the Minion implementation's authoritative unlock state.

Questlog must not duplicate tribe ownership as ordinary narrative facts.

Actual Minions are not required as controllable actors in restoration objectives.

### 8.1 Brown opening

Craft the Master's Staff and summon the first Brown in the same opening quest.

Brown summoning is proof that the Minion system is functioning, not a precedent requiring Minions to perform future quest objectives.

### 8.2 Red restoration

Approved feat:

- enter the Netherworld as progression naturally allows;
- kill a Blaze;
- obtain / return with a Blaze Rod.

### 8.3 Green restoration

Approved feat:

- brew a Potion of Poison;
- deliberately become poisoned;
- kill a Witch while poisoned.

### 8.4 Blue restoration

Approved feat:

- prepare Water Breathing;
- enter an Ocean Monument;
- kill an Elder Guardian.

Sequence is fixed:

```text
Brown -> Red -> Green -> Blue
```

Quest completion invokes the Minion owner API to establish the authoritative unlock.

## 9. Bosses'Rise central campaign

All five Bosses'Rise bosses are central Campaign Checkpoints.

Each receives a dedicated multi-quest subcampaign.

The five subcampaigns become concurrent after the Tower / tribe readiness gate because exploration order is open.

All five are connected to the Great Cataclysm or dimensional wound through distinct mechanisms rather than one universal origin model.

Established identities:

- Skor / Yeti: Nordberg creature fed or empowered by Cataclysm rifts;
- Sirok / Sandworm: Ruborian/desert giant sandworm enlarged through Cataclysm-rift energy;
- Ashlord / Dragon: first resurrected dragon and historical origin point for later dragon resurgence;
- Helvar / Third Overlord: Third Overlord escaped/emerged from the Infernal Abyss through Cataclysmic dimensional destabilization and now survives as a corrupted mad remnant, not a coherent political claimant;
- Nerakyss / oceanic boss: result of centuries-long oceanic Cataclysm contamination/leakage.

Boss kills narratively weaken the dimensional connection but do not require a mechanical global world effect.

After the fifth checkpoint, use one short Gnarl interpretation/preparation transition with no added grind before the End phase.

## 10. End / Cataclysm Dimension resolution

The End is the Cataclysm Dimension and the payoff to already-understood anomaly evidence, not a last-minute surprise explanation.

Established ending rules:

- the dimension persists permanently after the campaign;
- the Ender Dragon is the dominant creature of the dimension and a living anchor sustaining the active wound;
- defeating it stabilizes / stops active new leakage;
- defeating it does not erase centuries of consequences;
- existing dragons, Myrmex, Fathoms corruption, transformed ecology, ruins, and history remain;
- Gnarl is principal ending voice;
- the ending may acknowledge selected remembered world facts;
- do not compute a completion percentage, morality rank, sidequest score, or universal ending score;
- the post-ending world treats the player as an established active Master whose Tower, Horde, and dominion survived the crisis;
- unresolved side content remains playable;
- Post-Credits may contain genuinely new major adventures.

The Great Cataclysm is not the universal explanation for every modded phenomenon.

## 11. Civilization system authority

V5 civilization content uses authored local anchor polities, not species-wide global state.

Current V5 roster:

1. Villagers;
2. Illagers;
3. Dwarves;
4. Gnumus;
5. Goblins;
6. Kobolds;
7. Ribbits;
8. Sea Dwellers;
9. Piglins;
10. Umvuthana;
11. Myrmex.

Demons remain outside the generalized civilization-disposition system.

### 11.1 Civilization questline architecture

Each civilization uses one coherent internal civilization-state questline rather than a collection of unrelated political questlines.

The general architecture is:

```text
anchor / contact
-> local problem, authority or cultural progression
-> selected provider sidequests and leverage where justified
-> authored internal state-resolution branch
-> permanent terminal consequence for that canonical anchor
```

Provider sidequests are components of the civilization framework. They must serve the civilization's authored progression, characterization, leverage, local economy, information, or branch setup rather than existing merely because provider infrastructure can generate quests.

A civilization's terminal political state is the result of an authored inner civilization questline. It is not a detached menu choice, background drift, or automatic consequence of first contact.

### 11.2 Terminal state rule

`UNRESOLVED` is absence of a final authored resolution, not a political outcome.

Where legal for that civilization, `NEUTRAL` is a genuine terminal result just as `SUBJUGATED`, `HOSTILE`, `DESTROYED`, or another explicitly authorized state may be.

Choosing not to conquer a polity must therefore still be represented through authored action and resolution inside that civilization's questline.

Once a canonical anchor reaches its authored final resolution, that terminal result is permanent unless a later explicit V5 decision creates a post-resolution exception.

Older production behavior that writes NEUTRAL during first contact or an early audience does not control V5 where it conflicts with this rule.

### 11.3 Locality

A terminal result affects the deliberately authored anchor polity, not every procedural member of that species or culture across the world.

Generated settlements remain independent unless separately authored.

### 11.4 Gnumu ancestry integration

Established fact: the regional Gnumus descend from Gluttony-altered Halflings of the Mellow Hills / Halfling lands. Gnarl can know this history while the local Gnumus may not.

V5 will include the ancestry discovery and reveal decision inside the single Gnumu civilization-state questline rather than as an unrelated parallel questline.

The questline may establish a remembered internal outcome in which the Overlord either:

- reveals the Halfling ancestry to the Elder Shaman / canonical settlement; or
- withholds the truth, leaving the local Gnumus ignorant.

This remembered truth decision is distinct from the terminal political disposition but structurally contained within the same civilization questline.

Exact objectives and reveal timing remain to be authored from source evidence.

### 11.5 Dwarf / Kobold rivalry integration

Both civilizations remember their rivalry and it remains active in the present.

V5 will use one optional shared cross-civilization chain after both the designated Golden Hills / Golden Halls successor hold and designated Kobold Den have been discovered.

The chain may allow authored directions such as favoring one side, imposing an arrangement, exploiting both, or worsening the dispute for advantage, subject to the final source-backed authoring.

Rules:

- it is optional;
- it does not replace either civilization's own state questline;
- it does not merge Dwarf and Kobold dispositions;
- consequences may feed the internal state progression of one or both civilizations where explicitly authored;
- it must not become a third unrelated political system layered over the two civilization arcs.

## 12. Gnarl historical-recognition rule

Gnarl is institutional memory, not omniscient.

At established historical regions such as Spree, Heaven's Peak, Mellow Hills, Everlight, Evernight, and Golden Hills, he normally recognizes and interprets the old location.

Local peoples may preserve only partial, distorted, or culturally transformed knowledge.

New phenomena may remain genuinely unknown to Gnarl until evidence is found.

## 13. Magic category authority

Magic arcs demonstrate meaningful mastery. They are not mod manuals.

### 13.1 Iron's Spells 'n Spellbooks

Substantial core spellcasting progression, approximately 4 to 5 authored quest beats.

Final mastery is one quest with an OR closer:

- Good / Light-mana mastery; or
- Evil / Dark-mana mastery.

The first completed side records the inclination. Opposite-side mastery remains optional native progression and may receive later acknowledgement without reopening the completed quest.

### 13.2 Eidolon

Substantial ritual-occultism progression, approximately 4 to 5 authored quest beats.

Use the same single-quest OR closer model:

- Sacred / Good-mana mastery; or
- Wicked / Evil-mana mastery.

### 13.3 Farmer's Spell / Gluttony

Compact approximately 3-beat progression:

- mundane prepared cuisine foundation;
- Alchemist Pot / magical bridge;
- meaningful Gluttony culmination.

### 13.4 Theurgy

Substantial approximately 4 to 5-beat progression focused on material understanding, extracting principles, and useful transmutation / reproduction.

Do not require every machine.

### 13.5 Ars Elixirum

Compact approximately 3-beat progression:

- discover ingredient properties;
- deliberately formulate potion(s) in the Glass Cauldron;
- increase Mastery / produce controlled advanced formulation.

### 13.6 Biomancy

Substantial approximately 4 to 5-beat progression.

Gnarl is the natural principal presenter because Biomancy is canonically his flesh-magic discipline developed during the Silence.

Progression may use organic matter, Primordial Core / Cradle, living flesh, Decomposer / Bio Forge, Bio Lab, serums, and advanced biological engineering without requiring every machine.

## 14. Farming / magical automation category

Use one integrated farming / automation category rather than a Farmer's Delight-only questline.

Layers:

- Farmer's Delight: mundane agriculture and culinary foundation;
- Crop Critters: supernatural / magical agricultural labor;
- Golem Overhaul: constructed farm / utility automation.

Compact intended progression:

- establish a productive farm;
- engage with Crop Critters / supernatural labor;
- construct / use a Hay Golem as agricultural protection;
- culminate in a functioning magically or construct-assisted estate farm.

Do not require combat-oriented golem variants or exhaustive Farmer's Delight recipe completion.

## 15. Adventure allocation

A mod receives authored Adventure treatment only when V5 assigns one. Mod size alone is irrelevant.

Current V5 allocations include:

- Twilight Forest: substantial Adventure arc preserving native boss / progression structure; fae refuge dimension created during Glorious Empire persecution, not Cataclysm-created;
- Bumblezone: compact Adventure arc around discovery, major Queen / civilization content and one meaningful culmination; leave most native advancement/tutorial progression native;
- L_Ender's Cataclysm: one substantial optional Adventure arc across its mixed historical origins, not eight independent questlines;
- KnightQuest: dedicated arc preserving Great Chalice / essence / Netherman progression; Netherman is connected to the Forgotten God, not the Cataclysm;
- The Graveyard: compact investigation / boss arc around ruins, three Ominous Bone Staff fragments, preparation, and Corrupted Champion confrontation;
- Rats / Ratlantis: dedicated full Adventure arc; self-contained absurdly old rat civilization independent of Cataclysm and Glorious Empire;
- The Lost Castle: finite expedition arc around an obscure predecessor civilization, with Illagers as later occupants;
- Oddities / Queen of Orchid: compact local nature-spirit / predator arc independent of major historical powers;
- Pet Cemetery: small conditional arc triggered only after one of the Overlord's pets dies, presented principally by Mortis;
- Quaver's Tower Band: small Tower-personnel arc, not Tower-restoration completion;
- NightWalker: conditional Lestat-led vampire-transition arc; vampires are independent of the Great Cataclysm;
- Fathoms / Overlord Depths: dedicated investigation arc when implemented, with optional evidence relevant to Cataclysm understanding but not required for central completion.

No dedicated line for:

- Church of Sin, which remains atmospheric / self-contained Adventure discovery with contextual acknowledgement;
- Born in Chaos, which remains dangerous modern ecology with sparse selected Ramblings;
- a generic Mowzie's Mobs boss checklist;
- a generic Beastmaster line for tameable-creature mods.

## 16. Ice & Fire allocation

Ice & Fire content is deliberately split by campaign role rather than treated as one monolithic mod questline.

- Myrmex: Civilization;
- Dragon Forge / Dragon Den: Tower development;
- Ashlord: Bosses'Rise;
- miscellaneous creatures: Adventure / ambient / Ramblings where justified.

Dedicated dragon-mastery progression:

1. establish dragon knowledge through the Bestiary / research system;
2. kill a wild adult dragon and harvest meaningful dragon materials;
3. defeat or find a sufficiently ancient female dragon and obtain a dragon egg;
4. hatch the egg, raise the dragon to rideable age, saddle it, and ride it.

Do not require Dragonsteel / Dragon Forge inside this dragon-mastery arc. That belongs to Tower development.

Ashlord is not a prerequisite for modern dragon progression.

## 17. Optional evidence into central ending

Optional arcs may deepen the player's understanding without becoming hidden mandatory gates.

Fathoms and Myrmex may provide optional evidence about Cataclysm consequences.

The post-five-boss Gnarl transition may conditionally acknowledge such evidence when present.

Mandatory Bosses'Rise evidence must remain sufficient by itself to proceed.

## 18. Post-Credits authority

Post-Credits is not merely cleanup.

It may contain genuinely new major adventures after the central ending while preserving the established reign and persistent world.

Outer End dedicated exploration belongs mainly here, although naturally encountered End content during the central expedition may be acknowledged earlier.

Unfinished optional Adventure, civilization, Tower, magic, and sandbox content also remains available.

## 19. Technical translation boundary

V5 defines what must happen. The later implementation pass determines the least invasive verified mechanism that makes it happen.

Known Questlog technical primitives include block interaction / mine / place, entity approach / breed / death / kill / tame / owned-tame-death, item craft / craft-stat / drop / equip / obtain / use, logic objectives `AndObjective`, `OrObjective`, `NotObjective`, advancements, effects, enchantment, Ender Dragon defeat, quest completion, statistics, and biome / dimension / position / structure visits.

There is no generic entity-interact objective. Provider acceptance and turn-in handle provider interaction; visible objectives should be concrete tracked actions.

`OrObjective` completes when any child objective completes and is the intended primitive for the Iron's and Eidolon two-path mastery closers.

## 20. Supersession register

V5 explicitly supersedes older production or assignment assumptions where conflicts exist, including:

- generalized Gnarl reminder behavior;
- the old seven-room Tower gate;
- old Red / Green / Blue simple recovery anchors;
- the 10-civilization production roster;
- Church of Sin as a dedicated questline;
- Bumblezone as a full duplicate of native progression;
- visible standalone Tower completion presentation;
- older NightWalker alpha.3 assumptions;
- early disposition writes that conflict with the V5 terminal-state rule.

Further supersessions will be added as source reconciliation proceeds.

## 21. Open-question discipline

This document does not maintain speculative filler.

During continuing audits, any unresolved question that materially changes lore, campaign structure, player progression, terminal outcome, branch meaning, content allocation, or persistent consequence must be brought to the Overlord before the affected section is finalized.

Technical uncertainties should be investigated from source first.

A section may remain explicitly incomplete while that question is pending.
