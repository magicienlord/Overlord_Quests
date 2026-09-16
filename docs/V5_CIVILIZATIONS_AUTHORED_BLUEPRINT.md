# OVERLORD REIGN V5 Civilizations Authored Blueprint

Status: V5 QUEST BLUEPRINT

Purpose: convert the approved eleven-civilization political authorities into buildable Questlog graphs without prematurely choosing low-level detector details.

No final quest names or final dialogue are assigned here. Beat IDs are authoring references.

## 1. Shared Questlog architecture

Each civilization uses one canonical runtime-selected anchor and one persistent civilization Questlog root.

The root is not a detached choice menu. It is the political shell around internal provider-driven content.

Required lifecycle:

```text
qualifying generated settlement / structure
-> deliberate civilization-specific anchor-start action
-> lock this anchor permanently for the playthrough
-> bind or spawn required local quest roles
-> open common contact / orientation material
-> internal provider chains reveal concrete political routes
-> player deliberately completes one terminal route
-> root resolves by OR:
   NEUTRAL
   OR SUBJUGATED
   OR DESTROYED
-> incompatible terminal routes close permanently
```

The root Questlog may display the three possible terminal outcomes as the political shape of the arc, but the player must earn access to the corresponding resolution through the internal authored route.

No terminal outcome is awarded for:

- first contact;
- reaching a reputation threshold by accident;
- completing an arbitrary number of sidequests;
- merely refusing to attack;
- generic kill counts;
- selecting a dialogue button without the required prior route facts.

## 2. Shared beat types

For readability, each civilization generally uses these beat families:

- `ANCHOR`: deliberate runtime anchor lock and initial presentation;
- `COMMON`: shared contact, history, or local-problem material before branching;
- `N`: provider chain and final NEUTRAL resolution;
- `S`: provider chain and final SUBJUGATED resolution;
- `D`: destructive opportunity and final DESTROYED resolution;
- `OPTIONAL`: civilization-internal material that may affect later dialogue or options without becoming a terminal route itself.

Exact item counts, advancement IDs, transaction predicates, entity selectors, and provider-binding mechanics remain technical translation work unless a count itself carries narrative meaning.

## 3. Villagers / present-day Spree

### CIV-SPREE-ANCHOR: Establish present-day Spree

Visible campaign action:

- perform the final source-compatible deliberate anchor-start action inside one qualifying generated Villager settlement.

On completion:

- bind that settlement as present-day Spree;
- bind or establish the Mayor;
- bind Vanilla Farmer, VillagersPlus Miner, and Guard Villager provider roles;
- open the Spree political root.

Political question:

- can Spree remain independently viable, become materially dependent on the Overlord, or be broken as a functioning polity?

### Common civic strand

The player is introduced to the three pillars that make modern Spree viable:

```text
Farmer -> food security
Miner -> specialist material economy
Guard -> organized security
Mayor -> terminal civic authority
```

The Farmer, Miner and Guard each expose different actions depending on which political route the player pursues. They are not three generic errands that silently add reputation.

### NEUTRAL route

`CIV-SPREE-N-010` Farmer: establish a productive local food cycle and reserve that remains under Spree's own control.

`CIV-SPREE-N-020` Miner: demonstrate that local acquisition, processing and circulation of useful materials can function without Overlord ownership.

`CIV-SPREE-N-030` Guard: establish an equipped local defensive force assigned to Spree rather than retained as an Overlord expeditionary force.

`CIV-SPREE-N-040` Mayor resolution: after all three independence facts exist, explicitly recognize Spree as an independent polity.

Writes:

- `NEUTRAL` for canonical Spree.

### SUBJUGATED route

`CIV-SPREE-S-010` Farmer: establish the Overlord as strategic food guarantor through a concrete reserve / supply dependency beyond the Farmer's own independent output.

`CIV-SPREE-S-020` Miner: make the Overlord indispensable to Spree's higher-value material supply and specialist processing economy.

`CIV-SPREE-S-030` Guard: recruit, equip and establish the organized Guard force through source-owned Guard Villagers mechanics so Spree's security materially depends on the Overlord's authority.

`CIV-SPREE-S-040` Mayor resolution: confront the Mayor with the completed food, material and security dependencies and secure formal submission while the Mayor remains civic administrator.

Writes:

- `SUBJUGATED` for canonical Spree.

### DESTROYED route

`CIV-SPREE-D-010`: expose Spree's civic and defensive vulnerabilities through the same known institutions rather than a kill quota.

`CIV-SPREE-D-020`: deliberately eliminate the Mayor and the minimum indispensable civic / defensive functions selected by final source translation.

`CIV-SPREE-D-030`: confirm that present-day Spree no longer functions as a polity.

Writes:

- `DESTROYED` for canonical Spree.

Physical ruins may remain. Other Villager settlements remain ordinary Villager populations.

## 4. Illagers / canonical Bastille at Heaven's Peak

### CIV-ILLAGER-ANCHOR: Select the Bastille

Anchor identity:

- the deliberately selected eligible Bastille / Heaven's Peak historical fortress becomes the canonical Illager polity.

Initial state:

- hostile.

### Common military opening

`CIV-ILLAGER-COMMON-010`: penetrate the Bastille and break its immediate local military authority through the authored confrontation.

Completion writes:

- `COWED` internal fact only.

`COWED` is not a terminal disposition.

Provider / local content then exposes what remains useful, who still commands, and how the fortress can be left alone, exploited, or eliminated.

### NEUTRAL route

`CIV-ILLAGER-N-010`: establish that renewed aggression against the Overlord is untenable while deliberately leaving surviving Illager authority intact.

`CIV-ILLAGER-N-020`: conclude a lasting nonaggression settlement.

Writes:

- `NEUTRAL`.

The Bastille remains an independent Illager polity and ordinary Illager hostility toward Villagers is not erased.

### SUBJUGATED route

`CIV-ILLAGER-S-010`: exploit the broken hierarchy and surviving raid / fortress infrastructure until the local leadership depends on Overlord permission or power to continue functioning.

`CIV-ILLAGER-S-020`: secure formal acknowledgement of the Overlord's supremacy.

Writes:

- `SUBJUGATED`.

The Bastille survives as a useful Overlord-aligned warband.

### DESTROYED route

`CIV-ILLAGER-D-010`: continue beyond the cowed settlement and deliberately remove the Bastille's remaining leadership and indispensable military-political continuity.

`CIV-ILLAGER-D-020`: confirm the Bastille no longer functions as a polity.

Writes:

- `DESTROYED`.

## 5. Dwarves / Golden Hills successor hold

### CIV-DWARF-ANCHOR: Bind the successor hold

On anchor lock:

- bind Forge-Thane as terminal authority;
- bind professionless Dwarf as authored Record Keeper;
- bind a second Dwarven Forger;
- bind Dwarven Warrior provider.

Political question:

- can the diminished successor hold restore enough institutional legitimacy to stand alone, or does that restoration place it under the Overlord's control?

### Common heritage strand

`CIV-DWARF-COMMON-010`: establish the hold's reduced successor status and the historical destruction of Dwarven institutions under Glorious Empire anti-magic persecution.

This history is not attributed to the Silence.

### NEUTRAL route

`CIV-DWARF-N-010`: complete the Record Keeper chain, using surviving testimony plus concrete Dwarven material / rune continuity to reconstruct enough of the Golden Hills legacy to legitimate the modern hold.

`CIV-DWARF-N-020`: strengthen the hold sufficiently that its restored identity does not depend on Overlord ownership.

`CIV-DWARF-N-030`: Forge-Thane accepts an explicit independent settlement.

Writes:

- `NEUTRAL`.

### SUBJUGATED route

`CIV-DWARF-S-010`: complete the second Forger chain and expose how forge output, rune craft, specialist production and resource security underpin the Forge-Thane's authority.

`CIV-DWARF-S-020`: place the critical productive leverage needed for that restored continuity under Overlord control or patronage.

`CIV-DWARF-S-030`: Forge-Thane keeps office but acknowledges that the reborn Golden Hills survives beneath the Overlord.

Writes:

- `SUBJUGATED`.

### DESTROYED route

`CIV-DWARF-D-010`: complete the Warrior chain to expose command and defensive vulnerabilities.

`CIV-DWARF-D-020`: eliminate the Forge-Thane and the minimum indispensable political / defensive center needed to break successor continuity.

Writes:

- `DESTROYED`.

### Optional Dwarf-Kobold rivalry link

See section 14. Rivalry facts may modify leverage or later consequences but never directly resolve the Dwarven state.

## 6. Gnumus / Mellow Hills descendants

### CIV-GNUMU-ANCHOR: Bind the main Gnumu settlement

On anchor lock:

- bind Elder Shaman as terminal authority;
- bind Merchant, non-despawning Hunter, and source-appropriate Vintage / craft contacts as required.

Political question:

- how does the Overlord treat a functioning society whose prosperity and history can be understood without inventing a crisis?

### Common functioning-society strand

Provider content establishes:

- local trade and prosperity;
- hunting / defense;
- shamanic culture;
- surviving Vintage technology;
- partial loss of the knowledge once required to reproduce that technology at its older level.

### Optional internal ancestry investigation

This remains inside the Gnumu civilization Questlog and is not a separate campaign branch.

`CIV-GNUMU-ANCESTRY-010`: recover source-owned Vintage Metal / `Antiquity Shard` evidence.

`CIV-GNUMU-ANCESTRY-020`: reconstruct or obtain a Gnumus Totem linking surviving Gnumu culture to that older material tradition.

`CIV-GNUMU-ANCESTRY-030`: Gnarl recognizes the Mellow Hills / Gluttony historical significance and establishes the already-approved truth that modern Gnumus descend from Gluttony-altered Halflings.

Final remembered choice:

```text
ANCESTRY_REVEALED
OR
ANCESTRY_WITHHELD
```

This choice does not itself select `NEUTRAL`, `SUBJUGATED`, or `DESTROYED`.

### NEUTRAL route

`CIV-GNUMU-N-010`: support Merchant, Hunter, Shamanic and productive strands enough to establish durable self-sufficiency.

`CIV-GNUMU-N-020`: Elder Shaman receives explicit recognition that the settlement remains independent.

Writes:

- `NEUTRAL`.

### SUBJUGATED route

`CIV-GNUMU-S-010`: use specific economic, technological and/or Shamanic dependencies exposed by the provider content rather than a generic reputation score.

`CIV-GNUMU-S-020`: demonstrate that key prosperity or specialist functions now materially rely on Overlord support / access.

`CIV-GNUMU-S-030`: Elder Shaman accepts supremacy.

Writes:

- `SUBJUGATED`.

### DESTROYED route

`CIV-GNUMU-D-010`: use settlement knowledge gathered through ordinary civilization content to expose the actual leadership / defensive structure.

`CIV-GNUMU-D-020`: eliminate the Elder / indispensable local functions required to end the canonical settlement as a polity.

Writes:

- `DESTROYED`.

No generic Gnumu kill count is used.

## 7. Goblins / canonical camp

### CIV-GOBLIN-ANCHOR: Bind the camp

Required cast:

- Leader as terminal authority;
- Merchant and Bartender for coexistence;
- Engineer, Blacksmith and Merchant for submission leverage;
- Champion for destructive route exposure.

Political spine:

- the camp survives because it is useful and profitable to its inhabitants.

### NEUTRAL route

`CIV-GOBLIN-N-010`: complete meaningful Merchant business establishing that continued exchange with the Overlord is profitable.

`CIV-GOBLIN-N-020`: complete Bartender / tavern content establishing ordinary social-economic coexistence rather than hostility.

`CIV-GOBLIN-N-030`: Leader accepts a formal independent arrangement because peace is materially worthwhile.

Writes:

- `NEUTRAL`.

### SUBJUGATED route

`CIV-GOBLIN-S-010`: Engineer chain makes important technical / prototype opportunity dependent on Overlord resources or patronage.

`CIV-GOBLIN-S-020`: Blacksmith chain places useful arms / production service under Overlord patronage.

`CIV-GOBLIN-S-030`: Merchant chain makes major commerce materially more profitable or secure through the Overlord than without him.

`CIV-GOBLIN-S-040`: Leader submits rather than lose the camp's prosperity and useful institutions.

Writes:

- `SUBJUGATED`.

### DESTROYED route

`CIV-GOBLIN-D-010`: Champion content exposes the camp's military protection and destructive vulnerabilities.

`CIV-GOBLIN-D-020`: use knowledge of leadership, defenses and engineering to dismantle the anchor deliberately.

`CIV-GOBLIN-D-030`: Leader and minimum indispensable camp functions are removed.

Writes:

- `DESTROYED`.

Bombs and combat may be used where source-valid, but no generic Goblin kill quota substitutes for the route.

## 8. Kobolds / canonical Den

### CIV-KOBOLD-ANCHOR: Bind the Den

Required cast:

- Captain as terminal authority;
- Engineer;
- Enchanter;
- Warrior.

Political spine:

- the Den functions disproportionately through a small number of competent specialists.

### NEUTRAL route

`CIV-KOBOLD-N-010`: Captain / treasure relationship establishes ordinary political contact.

`CIV-KOBOLD-N-020`: strengthen the Engineer's practical machinery role without placing it under Overlord ownership.

`CIV-KOBOLD-N-030`: strengthen the Enchanter's magical service role while preserving local autonomy.

`CIV-KOBOLD-N-040`: Captain recognizes the strengthened Den as independent.

Writes:

- `NEUTRAL`.

### SUBJUGATED route

`CIV-KOBOLD-S-010`: secure an explicit Engineer obligation to the Overlord.

`CIV-KOBOLD-S-020`: secure an explicit Enchanter obligation to the Overlord.

`CIV-KOBOLD-S-030`: gain decisive leverage over important treasure / resource flows controlled through the Captain's native exchange role.

`CIV-KOBOLD-S-040`: Captain accepts that the Den's most important specialists and resources now function better beneath Overlord patronage.

Writes:

- `SUBJUGATED`.

### DESTROYED route

`CIV-KOBOLD-D-010`: Warrior chain exposes defenses and command weaknesses.

`CIV-KOBOLD-D-020`: remove Captain and the minimum indispensable specialist / defensive functions required to end the Den as a polity.

Writes:

- `DESTROYED`.

### Optional rivalry link

See section 14.

## 9. Ribbits / canonical village

### CIV-RIBBIT-ANCHOR: Bind the village

Required cast:

- Gardener-Elder as terminal authority;
- Fisherman;
- Merchant;
- Sorcerer.

Political spine:

- Ribbit society is peaceful, functioning and largely non-threatening. The route reveals what the Overlord chooses to do with a polity that offers little reason for conquest beyond power itself.

Music remains characterization / native play / Rambling material and is not a mandatory political objective.

### NEUTRAL route

`CIV-RIBBIT-N-010`: support ordinary Gardener / agricultural life without creating ownership.

`CIV-RIBBIT-N-020`: complete useful Fisherman and Merchant relationships as reciprocal local cooperation.

`CIV-RIBBIT-N-030`: engage the Sorcerer's supportive magical role without converting it into service obligation.

`CIV-RIBBIT-N-040`: deliberately leave the Gardener-Elder and village independent.

Writes:

- `NEUTRAL`.

### SUBJUGATED route

`CIV-RIBBIT-S-010`: transform a specific Merchant relationship into a continuing tribute / commercial obligation.

`CIV-RIBBIT-S-020`: transform a specific Fisherman relationship into a supply obligation.

`CIV-RIBBIT-S-030`: transform the Sorcerer's support into a magical-service commitment beneath the Overlord.

`CIV-RIBBIT-S-040`: Gardener-Elder formally acknowledges that those obligations place the village under the Overlord.

Writes:

- `SUBJUGATED`.

The Ribbits understand the political result even if their presentation remains cheerful and simple.

### DESTROYED route

`CIV-RIBBIT-D-010`: deliberately choose violence against the harmless polity rather than having destruction emerge from a fabricated crisis.

`CIV-RIBBIT-D-020`: eliminate the Gardener-Elder and the minimum indispensable village functions needed to end the local polity.

Writes:

- `DESTROYED`.

The cruelty is intentional in the contrast with their lack of serious provocation.

## 10. Sea Dwellers / canonical Sea Village

### CIV-SEA-ANCHOR: Bind the Sea Village

Required cast:

- Sea Elder as terminal authority;
- Blacksmith;
- Collector.

Political spine:

- local sovereignty is expressed through underwater resources, professions, protected civic property and trade customs.

### Common sovereignty strand

The player learns:

- Aquamarine / fish / Nautilus barter;
- profession-changing Seashells;
- Depth metallurgy;
- profession-specific caskets;
- native protected-property taboo around Sea Lanterns.

### NEUTRAL route

`CIV-SEA-N-010`: respect protected property customs while establishing several meaningful native barter relationships.

`CIV-SEA-N-020`: demonstrate understanding of profession and resource systems without placing them under Overlord ownership.

`CIV-SEA-N-030`: conclude an explicit independent trade settlement with the Sea Elder.

Writes:

- `NEUTRAL`.

### SUBJUGATED route

`CIV-SEA-S-010`: secure the Blacksmith's explicit production / metallurgy obligation.

`CIV-SEA-S-020`: secure the Collector's explicit resource / acquisition obligation.

`CIV-SEA-S-030`: use profession Seashell, Aquamarine commerce and Depth-resource leverage to establish broader institutional dependency without inventing a third mandatory provider.

`CIV-SEA-S-040`: Sea Elder retains office but accepts Overlord supremacy.

Writes:

- `SUBJUGATED`.

### DESTROYED route

`CIV-SEA-D-010`: deliberately desecrate protected civic property, with an authored Sea Lantern violation preferred because the native system already produces prolonged Mermorph Rage.

`CIV-SEA-D-020`: survive / overcome the resulting hostility and dismantle the Sea Elder's authority plus the minimum indispensable local functions.

Writes:

- `DESTROYED`.

Possible Elven ancestry remains historical characterization. It does not become a separate questline or disposition prerequisite.

## 11. Piglins / canonical Nether Village

### CIV-PIGLIN-ANCHOR: Bind the Piglin-built village

Required authority:

- selected native Piglin Brute receives the local Chieftain role.

Political spine:

- territorial inheritance versus the return of the Master to a Netherworld polity that became independent during the Silence.

The village is genuinely Piglin-built. Ancient Minion-pig ancestry does not make automatic ownership a valid resolution.

### Common territorial strand

The player establishes:

- Piglin gold / barter culture;
- native property retaliation;
- village forge, mine, storage, farm and tower infrastructure;
- the historical Minion kinship that Gnarl recognizes with embarrassment.

### NEUTRAL route

`CIV-PIGLIN-N-010`: learn and respect local barter / property customs.

`CIV-PIGLIN-N-020`: establish useful exchange while deliberately recognizing that this village was built and possessed by the Piglins themselves.

`CIV-PIGLIN-N-030`: Chieftain recognizes the returning Overlord as a major Netherworld power but not as his sovereign.

Writes:

- `NEUTRAL`.

### SUBJUGATED route

`CIV-PIGLIN-S-010`: demonstrate decisive control / access over gold and barter wealth important to the village.

`CIV-PIGLIN-S-020`: establish leverage over village resource infrastructure without inventing professions the source does not have.

`CIV-PIGLIN-S-030`: Chieftain accepts that the returned Master stands above the canonical Nether Village.

Writes:

- `SUBJUGATED`.

Required terminal gameplay consequence for the canonical village:

- no local gold-armor requirement;
- no retaliation for opening local containers;
- no retaliation for mining local gold;
- continued barter / services;
- authored tribute where implemented.

### DESTROYED route

`CIV-PIGLIN-D-010`: deliberately reject Piglin territorial ownership by violating protected gold / property order.

`CIV-PIGLIN-D-020`: defeat the Chieftain and minimum local defensive authority.

`CIV-PIGLIN-D-030`: end the village as a functioning polity.

Writes:

- `DESTROYED`.

## 12. Umvuthana / canonical Grove

### CIV-UMVU-ANCHOR: Establish legitimate audience

Required start action:

```text
wear Umvuthana mask
-> deliberately initiate legitimate Umvuthi interaction inside eligible Grove
-> lock Grove
```

Writes:

- `AUDIENCE_ESTABLISHED` internal fact only.

Bind / spawn as needed:

- Crane as Grove Healer;
- one canonical Raptor as local martial / pack authority.

The Raptor role is specific to the canonical Grove and does not establish that all Raptors are Grove generals.

### NEUTRAL route

`CIV-UMVU-N-010`: complete Umvuthi's native seven-Gold-Block peaceful exchange and receive Sun's Blessing.

`CIV-UMVU-N-020`: complete a short Grove relationship sequence demonstrating durable peaceful recognition.

`CIV-UMVU-N-030`: explicitly recognize the Grove as independent beneath Umvuthi alone.

Writes:

- `NEUTRAL`.

### SUBJUGATED route

`CIV-UMVU-S-010`: Crane chain makes the Overlord materially indispensable to Grove healing / support, ending in explicit commitment of that function to his service.

`CIV-UMVU-S-020`: Raptor chain demonstrates superior command, ending in transfer of the Raptor's martial allegiance beneath the Overlord.

`CIV-UMVU-S-030`: with both support and martial pillars compromised, unlock final audience with the living Umvuthi.

`CIV-UMVU-S-040`: Umvuthi chooses preservation of himself and the Grove and publicly acknowledges the Overlord as superior temporal ruler.

Writes:

- `SUBJUGATED`.

No boss-health interception, fake surrender combat state, or nonlethal boss rewrite is authorized.

### DESTROYED route

`CIV-UMVU-D-010`: reject accommodation and defeat / kill the canonical Umvuthi through the native encounter.

Preferred proof:

- native Umvuthi kill advancement plus source-owned Sol Visage consequence where useful.

Writes:

- `DESTROYED`.

## 13. Myrmex / canonical hive

### CIV-MYRMEX-ANCHOR: Bind one hive

Anchor scope:

- one selected Ice & Fire Myrmex hive only.

Political substrate:

- native per-hive opinion remains Ice & Fire-owned;
- Questlog owns the political resolution.

### Common hive-learning strand

`CIV-MYRMEX-COMMON-010`: establish understanding of hive castes / behavior and begin deliberate relations with the selected Queen's colony.

Native thresholds may open access but do not themselves resolve politics.

### NEUTRAL route

`CIV-MYRMEX-N-010`: raise native opinion to 50+ through source-backed colony actions.

`CIV-MYRMEX-N-020`: use the newly available trade access in a meaningful authored hive relationship sequence.

`CIV-MYRMEX-N-030`: complete an explicit Queen-facing recognition of independent hive sovereignty.

Writes:

- `NEUTRAL`;
- native opinion floor protected at 50 afterward.

### SUBJUGATED route

`CIV-MYRMEX-S-010`: continue source-backed relations until native opinion reaches 75+.

`CIV-MYRMEX-S-020`: gain legitimate Myrmex Staff command access for the existing canonical hive.

`CIV-MYRMEX-S-030`: perform at least one meaningful non-destructive command affecting that existing hive.

`CIV-MYRMEX-S-040`: complete explicit Queen-facing submission.

Writes:

- `SUBJUGATED`;
- native opinion floor protected at 75 afterward.

V5 deliberately does not use opinion 100 because that threshold belongs to the player-founded Queen colony path.

### DESTROYED route

`CIV-MYRMEX-D-010`: deliberately turn against the canonical hive.

`CIV-MYRMEX-D-020`: kill the canonical Queen and any additional colony function only if final source analysis proves that function indispensable to the polity.

Writes:

- `DESTROYED`.

## 14. Optional Dwarf-Kobold rivalry sequence

Availability:

- canonical Dwarven hold discovered / locked;
- canonical Kobold Den discovered / locked.

Placement:

- this is cross-linked internal civilization content, not a twelfth civilization and not a new top-level campaign category.

### RIVALRY-010: Establish the current dispute

Visible purpose:

- hear / establish both sides' remembered rivalry and its current practical point of friction.

Writes:

- shared rivalry-known fact.

### RIVALRY-020: Choose how to exploit the dispute

Authoring directions already approved in principle include:

- favor one side;
- force an arrangement;
- extort both;
- worsen the dispute for Overlord advantage.

This beat may branch into small explicit facts representing what leverage or damage was created.

### RIVALRY-030: Apply the consequence

Completion:

- writes only rivalry facts consumed later by the relevant Dwarf / Kobold route;
- may unlock, strengthen, weaken or alter one later resolution option / consequence;
- never writes `NEUTRAL`, `SUBJUGATED`, or `DESTROYED` for either polity directly.

No continuous synchronization between civilization state machines is required.

## 15. Civilization root completion rule

For each canonical anchor, the civilization Questlog root completes only when exactly one terminal marker exists:

```text
NEUTRAL
OR
SUBJUGATED
OR
DESTROYED
```

Implementation must enforce mutual exclusivity and permanence.

The completion presentation should identify the resolved local polity and consequence. It must not imply that every member of the species worldwide now shares that state.

## 16. Presenter-roster implication

Speaking roles created by this blueprint must be collected into the final V5 presenter roster before PNG generation.

Likely recurring speaking roles include, subject to final dialogue allocation:

- Mayor of Spree;
- Dwarven Record Keeper;
- Forge-Thane;
- Elder Shaman;
- Goblin Leader;
- Kobold Captain;
- Gardener-Elder;
- Sea Elder;
- Piglin Chieftain;
- Umvuthi;
- Umvuthana Crane;
- canonical Umvuthana Raptor;
- Myrmex Queen only if V5 presentation elects to personify Queen-facing resolution through direct speech rather than narrator / provider framing.

Provider existence does not automatically mean every provider needs PNG dialogue. Final dialogue authoring should keep the roster only as large as needed.

## 17. Remaining authoring boundary

The civilization political graphs are now fixed at quest-beat level.

Remaining work before production translation is principally:

- final source-backed visible objective actions inside each provider beat;
- exact anchor-start action for civilizations where not already fixed;
- exact destructive indispensable targets where several source-compatible choices exist;
- exact optional rivalry facts and consequences;
- final presenter assignment and dialogue content.

These should be resolved from source and existing V5 meaning first. Return to the Overlord only where the available actions would materially alter the approved political experience rather than merely select a detector or item count.

The V5 blueprint does not authorize production changes yet.
