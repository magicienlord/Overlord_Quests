# V5 Decision Ledger

Status: conversation-authority preservation for the next V5 authoring pass.

This file records explicit user decisions and already-established project authority that materially constrain V5. It does not silently resolve unknowns. Where this ledger conflicts with current `Overlord_Quests` production definitions or older assignment documents, the newer explicit V5 decision controls the design document. Production implementation is to be reconciled later, not during handoff.

## 1. Authoring contract

- V5 is a specification/authoring pass, not a brainstorming dump.
- Do **not** name quests yet. Record objectives/roles/structure only.
- The world will be played fresh and solely by the Overlord.
- Do not shape V5 around multiplayer support, late-install backtracking, or recovery/backfill scenarios.
- Technical facts may be resolved from supplied JARs/source when they do not create new REIGN lore or progression intent.
- Anything needed for V5 that has not already been established must be brought to the Overlord first before writing it as design/canon.
- Distinguish CANON / PLANNED / PROPOSAL / UNKNOWN.
- `magicienlord/Overlord_Lore_and_Canon` is READ-ONLY.

## 2. Quest UI / presentation

The Overlord does not want OVERLORD REIGN turned into “a Quests UI.”

Current presentation rule:

- quest starts/presents objective;
- player completes it;
- Questlog completion screen closes it;
- no repeated quest-trigger/rule popups;
- no periodic/persistent Gnarl reminders about open campaign objectives;
- campaign importance should be referenced naturally by related quest entries when relevant;
- NPC Ramblings are occasional character/world reactions tied primarily to approved native achievements/advancements;
- Ramblings may sometimes remind the player that a trivial achievement is less important than the campaign, but only when contextually appropriate;
- do not turn every Rambling into a quest reminder;
- unused but meaningful native progression may be acknowledged through sparse Ramblings instead of extra quests.

Provider source and presenter voice may differ without creating a second quest state.

## 3. Central campaign spine

High-level campaign architecture is established:

```text
Awakening / Tower summit
→ Master's Staff + first Brown proof in one opening quest
→ semi-open Tower restoration + Red → Green → Blue restoration + side content
→ full defined initial Tower restoration + all four tribes restored
→ five concurrent Bosses'Rise subcampaigns
→ short Gnarl interpretation/preparation transition
→ End / Cataclysm Dimension expedition
→ Ender Dragon
→ Gnarl ending presentation
→ persistent established reign
→ Post-Credits adventures
```

No civilization completion is a general prerequisite for the central campaign unless a specific authored quest explicitly requires one.

The player can naturally access optional/adventure content before the full Tower/tribe gate once the opening state is established.

## 4. Minion progression

### Brown opening

Crafting the Master's Staff and summoning the first Brown belong to the **same opening quest**. Do not split them into two separate quests.

Brown summoning is only a proof/tutorial that the core Minion system functions. It does not create a general rule that Minions participate in quest objectives.

### Red restoration

Approved feat:

- enter the Netherworld as progression naturally allows;
- kill a Blaze;
- obtain/return with a Blaze Rod.

### Green restoration

Approved feat:

- brew a Potion of Poison;
- deliberately become poisoned;
- kill a Witch while poisoned.

### Blue restoration

Approved feat:

- prepare Water Breathing;
- enter an Ocean Monument;
- kill an Elder Guardian.

Order remains Red → Green → Blue.

Actual Minions must not be required to accompany the Overlord, fight targets, perform elemental actions, or enable the recovery feats. Quest completion calls the Minion API to establish the authoritative unlock/recovery state.

## 5. Dark Tower restoration

The Dark Tower progresses gradually. Do not use per-room coordinate/bounds triggers. Tower geography is only the Tower structure/start-region context; room restoration is inferred from functional installation anywhere appropriate in the Tower.

### Defined initial restoration set required before Bosses'Rise

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

Minion Infrastructure is considered already present/restored and receives no restoration objective.

Dragon Den is excluded from this initial gate and remains an independent later Tower development.

No visible standalone “all rooms restored” quest or popup. A silent aggregate fact may gate Bosses'Rise.

### Approved initial anchors

- Throne — **Necrolord Chair** from Fantasy's Furniture.
- Treasury — **Gold Barrel** from Goblins Tyranny.
- Arena — **Supplementaries Cage**.
- Jail — **Big Iron Grate** from Abyssal Decor.

Previously established technical anchors remain:

- Forge — Hot Iron functional forge block(s), notably Smithing Anvil/Crucible as appropriate;
- Storage — Storage Drawers functional controller/drawers;
- Armory — multiple installed display families rather than one rack type;
- WayGates — Waystones implementation; preserve free post-discovery Waystone travel;
- magical rooms — their established functional workstation blocks.

Exact registry IDs should be resolved technically from the instance when implementation resumes.

## 6. Dragon Den / Ice & Fire

Dragon Den belongs in the Tower because Dragon Forges require dragon breath. Architecture remains an authoring/design matter and is not required for the initial Bosses'Rise gate.

Ice & Fire gets one focused dedicated dragon progression arc. Content that belongs elsewhere stays elsewhere:

- Myrmex — Civilization;
- Dragon Forge / Dragon Den — Tower;
- Ashlord — Bosses'Rise;
- miscellaneous creatures — Adventure/ambient/Ramblings as appropriate.

### Approved four-part Ice & Fire progression

1. establish dragon knowledge through the Bestiary/research system;
2. kill a wild adult dragon and harvest meaningful dragon materials;
3. defeat/find a sufficiently ancient female dragon and obtain a dragon egg;
4. hatch the egg, raise the dragon to rideable age, saddle and ride it.

Do not require Dragonsteel/Dragon Forge inside the dedicated dragon-mastery arc; that belongs to Tower development.

Ashlord is not a prerequisite for modern Ice & Fire progression.

## 7. Ashlord chronology

Current canon chronology:

```text
Great Cataclysm
→ Ashlord resurrected
→ during the Glorious Empire, Ashlord's strength and dragon population develop
→ dragons return to the wider world during the Silence
→ present-day Ice & Fire ecology
```

Do not revert to a version where Ashlord only developed after the Empire. The Glorious Empire period explicitly overlaps with his recovery/dragon-population development.

Defeating Ashlord does not need to alter modern dragon ecology. His relationship to present dragons is primarily historical background.

## 8. Bosses'Rise central campaign

All five Bosses'Rise bosses are central Campaign Checkpoints and each gets a dedicated multi-quest subcampaign. Their arcs are concurrent because world exploration order is open.

All five are tied to the Great Cataclysm / dimensional wound, but by boss-specific mechanisms rather than one identical origin model.

- **Skor / Yeti** — creature from Nordberg lands, fed/empowered by Cataclysm rifts.
- **Sirok / Sandworm** — Ruborian/desert giant sandworm that grew larger through Cataclysm-rift energy.
- **Ashlord / Dragon** — first resurrected dragon; historical origin point for later dragon resurgence.
- **Helvar / Third Overlord** — Helvar is the Third Overlord (Overlord I/Raising Hell ruler), escaped/emerged from the Infernal Abyss because the Great Cataclysm destabilized dimensional boundaries; centuries of Abyss corruption plus Cataclysm energy left him a mad, warped remnant guarding a useless throne, not a coherent political claimant.
- **Nerakyss / oceanic boss** — result of centuries-long oceanic Cataclysm contamination/leakage.

Boss kills narratively weaken the dimensional connection but need not produce a mechanical world effect.

After the fifth boss: one short central Gnarl interpretation/preparation transition, no grind, then the End phase.

## 9. End / Cataclysm resolution

Established ending rules:

- End/Cataclysm Dimension persists permanently after the campaign.
- Ender Dragon defeat stabilizes/stops the active dimensional wound/new leakage; it does not erase centuries of consequences.
- Existing dragons, Myrmex, Fathoms corruption, altered ecology and history remain.
- Ender Dragon is both the dominant creature of the Cataclysm Dimension and a living anchor sustaining the active wound.
- Gnarl is principal ending voice.
- Ending acknowledges selected remembered world facts, not completion percentage, ranking, morality score, or sidequest count.
- Post-ending world treats the Overlord as an established active Master whose Tower, Horde and dominion survived the crisis.
- Post-Credits may contain genuinely new major adventures, including remaining End content.

By the End phase, the player should already understand that the Cataclysm Dimension is the source behind the central anomaly network. The End is the payoff/confrontation, not a surprise reveal.

The Great Cataclysm is **not** the universal explanation for every modded phenomenon.

## 10. L_Ender's Cataclysm integration

Use one substantial optional Adventure arc, not eight disconnected questlines. It may begin before the central End campaign when structures are discovered and may continue naturally into Post-Credits if unfinished. Do not artificially lock every Cataclysm dungeon behind the Ender Dragon.

Approved mixed REIGN history:

- **Netherite Monstrosity / Soul Forge** — ancient pre-Cataclysm Netherworld industrial infrastructure/construct tied to older Overlord/Minion-era activity; Piglins may occupy comparable old infrastructure but did not build this forge.
- **Ender Guardian / Ruined Citadel** — directly Cataclysmic/native to the transformed End/Wasteland condition.
- **Harbinger / Ancient Factory** — Glorious Empire military-industrial complex/remnant, not Cataclysm-created.
- **Ancient Remnant / Cursed Pyramid** — originally a **Ruborian royal beast**, later resurrected by Cataclysmic magic.
- **Leviathan / Sunken City** — part of the broad oceanic consequences of long-term Cataclysm leakage, parallel to Fathoms/Nerakyss rather than subordinate to them.
- **Scylla / Acropolis** — predates the Great Cataclysm; her history is independent.
- **Maledictus / Frosted Prison** — originally a general appointed to Nordberg by the Fourth Overlord. The **Fourth Overlord's fall** caused/led to the general's imprisonment. He survives into the present as Maledictus. Who imprisoned him, the exact sentence motive and the precise transformation mechanism remain UNKNOWN.
- **Ignis / Burning Arena** — ancient Netherworld champion/warrior tradition, independent of the Great Cataclysm and never secretly an Overlord.

## 11. Optional evidence into central ending

Fathoms and Myrmex may provide optional evidence that strengthens the player's understanding of Cataclysm consequences, but neither is required to understand or finish the central campaign.

The post-five-boss Gnarl preparation dialogue should conditionally acknowledge optional evidence such as completed Fathoms/Myrmex investigations when available. Mandatory Bosses'Rise evidence remains sufficient by itself.

## 12. Twilight Forest

New REIGN canon:

- Twilight Forest is a fae dimension created by magical races during the Glorious Empire's persecution/hunt of magical beings.
- It served as a refuge/protective sanctuary.
- It is **not** a Great Cataclysm creation.
- Present ecology has become rampant/overgrown and there is no longer one distinct ruling people controlling the realm.

It receives a substantial Adventure sub-arc preserving the native boss/progression structure rather than inventing replacement gates.

## 13. Bumblezone

V5 scope is a compact Adventure arc, not a Questlog duplicate of the mod's extensive native advancement/tutorial surface.

Lore:

- naturally occurring magical pocket realm associated with bees;
- independent of the Great Cataclysm and Glorious Empire.

Authored progression should center on discovery, major civilization/Queen content and one meaningful culmination; other native milestones remain native/ambient/Ramblings.

## 14. Outer End

Outer End content is native ecology/structure content of the Cataclysm Dimension. It does not need a separate origin explanation.

Dedicated exploration belongs mainly to Post-Credits, though content encountered during the initial End expedition may be recognized naturally.

## 15. NightWalker

Vampires/NightWalker are completely independent of the Great Cataclysm.

Lestat is a noncorporeal quest/popup presenter and may replace Gnarl as the voice of the NightWalker arc. He has five visual reaction states; exact final labels/assets remain to be resolved from the NightWalker project/source.

## 16. Magic category

Do not turn magic arcs into mod manuals. Each line should demonstrate meaningful mastery and leave unused native progression for ordinary play and sparse Ramblings.

### Iron's Spells 'n Spellbooks

Substantial core spellcasting arc, roughly 4–5 quests worth of progression scope.

Final mastery should be **one quest with an OR closer**, not two separate closing quests:

- Good/Light-mana mastery objective;
- Evil/Dark-mana mastery objective.

Questlog's existing `OrObjective` supports this: the quest completes when either child objective completes.

The first completed side is the recorded inclination/choice. The opposite side remains optional and may later trigger a Rambling/native acknowledgement; it does not reopen or extend the finished Questlog quest.

### Eidolon

Substantial ritual-occultism arc, roughly 4–5 quests worth of progression scope.

Use the same single-quest OR closer model:

- Sacred/Good-mana mastery;
- Wicked/Evil-mana mastery.

The first completed side records the inclination; later mastery of the opposite side remains optional/Rambling material.

### Farmer's Spell / Gluttony

Compact approximately 3-quest progression:

- mundane prepared cuisine foundation;
- Alchemist Pot / magical bridge;
- meaningful Gluttony culmination.

### Theurgy

Substantial approximately 4–5 quest progression focused on material understanding, extracting principles and useful transmutation/reproduction. Do not require every machine.

### Ars Elixirum

Compact approximately 3-quest mastery progression:

- discover ingredient properties;
- deliberately formulate potion(s) in Glass Cauldron;
- increase Mastery / produce controlled advanced formulation.

### Biomancy

Substantial approximately 4–5 quest progression. Gnarl is the natural principal presenter because Biomancy is canonically his flesh-magic discipline developed during the Silence, inspired by Solarius's flesh alteration.

Native progression can move through organic matter, Primordial Core/Cradle, living flesh, Decomposer/Bio Forge, Bio Lab, serums and advanced biological engineering without turning every machine into a quest.

## 17. Farming / magical automation category

Create a separate farming/automation category rather than a Farmer's Delight-only category.

Layers:

- **Farmer's Delight** — mundane agricultural/culinary foundation;
- **Crop Critters** — supernatural/magical agricultural labor;
- **Golem Overhaul** — constructed farm/utility automation.

The intended compact progression is roughly:

- establish a productive farm;
- engage with Crop Critters / supernatural labor;
- construct/use a Hay Golem as agricultural protection;
- culminate in a functioning magically/construct-assisted estate farm.

Do not require combat-oriented Golem Overhaul variants or exhaustive Farmer's Delight recipe completion. Unused native milestones can support Ramblings.

## 18. Adventure allocation

### KnightQuest

Dedicated Adventure progression arc preserving Great Chalice / essence / Netherman progression.

New lore tie:

- Netherman is connected to the **Forgotten God**.
- Do not connect Netherman to the Great Cataclysm merely because of the word “Chaos.”

### The Graveyard

Compact Adventure investigation/boss arc:

- investigate relevant ruins;
- recover the three Ominous Bone Staff fragments;
- prepare/enable the confrontation;
- defeat the Corrupted Champion/Lich.

Preserve the Corrupted Champion's ambiguous/unknown original identity rather than assigning him to the Empire, Cataclysm or an Overlord without a later decision.

### Church of Sin

Later V5 decision supersedes the older full-questline assignment:

- no dedicated questline;
- atmospheric/self-contained Adventure discovery/dungeon;
- contextual Rambling/reference only when appropriate.

### Born in Chaos

- no dedicated questline;
- broad dangerous modern ecology;
- selected native achievements/boss encounters may produce sparse contextual Ramblings;
- individual content may be absorbed into another authored quest where it genuinely fits.

### Mowzie's Mobs / standalone encounters

Do not create a generic “kill all modded bosses” checklist.

- Umvuthi belongs to Umvuthana civilization;
- The Conjurer belongs in Illager/Heaven's Peak material;
- Frostmaw, Ferrous Wroughtnaut, Sculptor and similar standalone encounters remain Adventure discoveries/native achievement/Rambling material unless a later quest genuinely needs them;
- no synthetic Beastmaster questline for Mythic Mounts/tameable-creature mods.

### Rats / Ratlantis

Rats remains a dedicated full Adventure questline under the older assignment authority.

Approved REIGN framing:

- Ratlantis is independent of the Great Cataclysm, Glorious Empire and established civilization history;
- it is an absurdly old, self-contained rat civilization with its own culture/technology;
- tone should land like the Diablo “cow level” idea: the bizarre premise is played straight enough for the absurdity to work;
- ordinary Overworld rat automation can form the opening layer before escalation into Ratlantis.

Do not silently promote Ratlantis into the generalized civilization-disposition framework unless the Overlord explicitly decides that later.

### The Lost Castle

Dedicated finite expedition questline remains approved.

Lore framing:

- genuinely obscure/unknown predecessor civilization;
- not automatically a Glorious Empire stronghold;
- Illagers are later occupants/squatters/treasure-seekers.

Do not assume the Overlord formally claims the castle as territory after completion without an explicit decision.

### Oddities / Queen of Orchid

Compact questline remains approved.

Lore framing:

- Queen of Orchid is an ancient Overworld nature-spirit / magical predator;
- independent of Great Cataclysm and major historical powers;
- shrine is local forgotten worship/containment, not evidence of another civilization-scale mystery.

### Pet Cemetery

Small conditional arc appears only after one of the Overlord's pets dies.

- Mortis is the principal presenter;
- the arc introduces the supported resurrection system;
- it does not imply routine resurrection of ordinary sapients.

Exact final closer beyond the basic resurrection introduction remains to be written only after confirming what has already been established/asking if needed.

### Quaver

Quaver's Tower Band remains a small Tower-personnel arc using Immersive Melodies as implementation substrate. It is character/court restoration, not a generic instrument tutorial, and is not required for formal Tower restoration.

## 19. Civilization roster and framework

Current roster is eleven:

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

Demons are excluded from generalized civilization disposition.

General framework:

- generated settlements are not automatically authored/canonical;
- quest-critical anchor is deliberately selected and bound;
- provider service selects eligible NPCs associated with that anchor;
- sidequests begin through selected providers and Questlog state;
- objective tracking uses concrete Questlog/native events, not invented generic entity-interaction objectives;
- disposition is an authored output/world state, not a universal numeric reputation meter;
- civilization architecture should visibly progress through anchor/contact → local problem/authority arc → provider sidequests/leverage → explicit resolution branch → terminal consequence;
- main campaign does not generally require civilization resolution.

### Villagers

Principal historical anchor: Spree. Current villagers there may be direct descendants of old Spree inhabitants.

### Illagers

Designated Take a Pillage Bastille is Heaven's Peak / historical fortress-city/remains. Illagers inherited/occupied it but lost the true history; Gnarl recognizes the location.

### Dwarves

Successor hold tied to Golden Hills/Golden Halls. Their decline was caused by Glorious Empire anti-magic persecution, not the Silence.

### Gnumus

Principal settlement occupies/descends old Mellow Hills/Halfling lands. They descend from Gluttony-altered Halflings. Gnarl knows the ancestry; local Gnumu knowledge may remain limited.

### Goblins

Scavenger race occupying old abandoned human regions.

### Kobolds

Designated Den. Dwarves and Kobolds both remember their rivalry, and it remains current.

### Ribbits

Originated in Evernight after Oberon was liberated; revived/proliferating life became whimsical during the long absence of an active Overlord. Gnarl recognizes Evernight immediately.

### Sea Dwellers

May descend from Elves and remember that ancestry, but are not tied to a named old Overlord location or Everlight coast/Empire Harbor by default.

### Piglins

Own unique built settlements but also squat/occupy older Nether structures. Canonically descend from an ancient Minion + pig union; Gnarl is embarrassed by this. Designated Nether Village is its own polity, not abandoned Minion infrastructure.

### Umvuthana

Associated with the recovered/repopulated Everlight ecological sphere. Native mask/audience mechanics gate peaceful interaction; subjugation/destruction remain authored branches.

### Myrmex

Myrmex are a civilization category based on one selected Ice & Fire hive anchor, not a universal species-wide state.

Native hive-local reputation is allowed as the mod's local mechanic and is not a reintroduction of global Reputation!.

Established threshold structure:

- hostile/fight below 25;
- peace threshold 25;
- trade at 50;
- command at 75;
- Queen kill can support local DESTROYED resolution.

On first encounter Gnarl should **not** immediately identify their Cataclysm origin. He first recognizes that something is wrong/anomalous. Later evidence may establish likely Cataclysmic emergence. Resolving the hive is optional to the main campaign.

## 20. Historical recognition rule

Gnarl is institutional memory, not omniscient.

At established historical regions such as Spree, Heaven's Peak, Mellow Hills, Everlight, Evernight and Golden Hills, Gnarl normally recognizes/interprets the old location. Local peoples may have partial/distorted/culture-specific knowledge.

New phenomena can remain genuinely unknown to him until evidence is found.

## 21. Questlog technical facts relevant to V5

Current framework includes:

- block interact/mine/place objectives;
- entity approach/breed/death/died/kill/history/kill-stat/tame/owned-tame-death;
- item craft/craft-stat/drop/equip/obtain/use;
- logic objectives `AndObjective`, `OrObjective`, `NotObjective`;
- Advancement / EffectAdded / Enchant / EnderDragonDefeated / Origin / QuestComplete / Read / Statistic / Trample / Unobtainable / visit biome/dimension/position/structure objectives;
- persistent narrative facts and dispositions;
- Minion API unlock rewards/objectives;
- provider infrastructure.

There is **no generic entity-interact objective**. Provider acceptance/turn-in handles interaction; visible quest objectives should be concrete tracked actions.

`OrObjective` is verified in live source and completes when any child objective completes. This is the intended implementation primitive for the Iron's/Eidolon two-path mastery closers.

## 22. Current immediate unresolved design questions

Do not infer answers. See `NEXT_QUESTIONS.md`.
