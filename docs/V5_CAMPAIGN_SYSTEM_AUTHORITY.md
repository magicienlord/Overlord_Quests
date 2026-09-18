# OVERLORD REIGN — V5 CAMPAIGN SYSTEM AUTHORITY

Status: **V5 CAMPAIGN AUTHORITY — IMPLEMENTATION GRADE**

Date: 2026-09-18

Branch of origin: `v5-clean-authority-2026-09-16`

---

## 0. Purpose

This document is the single authority from which the OVERLORD REIGN campaign is built.

It exists so that the implementation pass can construct the complete Overlord Quests campaign **without reopening design and without deviating**. Every visible quest, its activation, its objective, its detector, its consequence, its presenter, its popup reactions and the facts it writes are recorded here. Where this document is silent, the builder asks the Overlord. The builder does not invent.

It works alongside `magicienlord/Overlord_Lore_and_Canon`, which remains the read-only authority for world facts, character voice, franchise evidence and REIGN continuity. This document supplies the campaign; the lore repository supplies the world it happens in and the voices that describe it.

This document is **exempt from the campaign spoiler firewall** established in `reference/29_REIGN_CAMPAIGN_STRUCTURE_DECISIONS.md`. That firewall governed the interview process so the Overlord would not spoil his own campaign during planning. It does not govern the finished authority, which must be complete in order to do its job. This document is written for the builder, not the player.

---

## 1. Authority order

When two statements conflict, the higher entry controls.

1. This document.
2. An explicit later decision by the Overlord.
3. `reference/39_REIGN_QUEST_AUTHORITY_AND_INTENTIONAL_DISCRETION.md` and the lore authorities it orders, for world facts, character voice and REIGN continuity.
4. Verified technical fact established directly from the installed mod, JAR, registry, advancement or Questlog framework. A verified technical fact establishes what *exists*. It never decides how V5 *uses* it.
5. The focused V5 authorities on the clean branch, retained as history.

No authored campaign choice becomes authority through technical convenience, through an older production quest having done it, through a file being named AUTHORITY, or through a choice appearing obvious.

### 1.1 Supersession register

| Superseded | By | Effect |
| --- | --- | --- |
| `V5_RAMBLING_SYSTEM_AUTHORITY.md` §2, §7 audit question, §9 correction rule | Q217 | One advancement has one consumer. Quest use removes an advancement from the Rambling pool. |
| Q121 (six named Lestat powers + cure) | Q238 | Lestat's catalog is 27 state-based milestones plus conditional hunter-side advancements. |
| Q205 (preserve Q121) | Q238, Q250 | Instruction pointed at a superseded rule. |
| Q120 (feed then survive daylight) | Overlord decision, this session | Feeding moved to NightWalker quest 1; quest 2 is a Hunter kill. |
| Q-009 (Grubby unestablished, `Grubbisom Jr` not canon) | Q243 | Grubbison Jr is canon and one of the eight presenters. |
| `reference/32` §4 and `reference/36` §1, Church of Sin as dedicated coverage | Q230, Q248b | Church of Sin receives Ramblings only. |
| `reference/16` roster of ten civilizations | `reference/04`, V5 §12, Q052 | Eleven civilizations. Myrmex is the eleventh. |
| Q148's rejected Cataclysm Rambling restriction | Q217 | Superseded in the opposite direction: the separation stands, the stinginess does not. |

---

## 2. Governing rules in force

### 2.1 Rewards

**The V5 campaign authors no quest rewards.** Every visible quest carries `reward: NONE`. Gameplay rewards the player — native loot, crafted outputs, items obtained during the objective, access gained through play, and the capabilities and state that completion unlocks (Q154, Q241).

State changes are recorded as **completion consequences**, never converted into rewards (Q155). Presenter acknowledgement is authored closure, not a reward (Q156). Q157's preference — a relevant item or material appropriate to difficulty and campaign stage over generic XP or loot-table payment — is **retained authority but unexercised**, because no reward is justified in the campaign as approved.

> **Implementation instruction.** Do not populate the reward column. An empty reward column is the approved state, not an omission. Populating it requires an explicit Overlord decision superseding Q241.

### 2.2 Objective versus order

Every quest carries two distinct text fields.

**`Objective (specification)`** is the machine-checkable condition the quest verifies. It is design data. It is **never shown to the player** and must never ship as quest text.

**`Order (presenter voice)`** is the player-facing line: an **order issued by the quest's presenter**, in that presenter's voice, not a checklist entry or a restatement of the specification. It is authored in the campaign-writing pass from the lore repository and the presenter rules. Every quest in this document reads `TO BE AUTHORED`.

> A quest shipped with the specification text in the order field is a **defect, not a placeholder** (Q242).

### 2.3 Detectors

No authored trigger may be introduced where the Questlog framework already provides an objective type (Q235). The framework supplies `advancement`, `block_interact`, `elixirum_mastery`, `ender_dragon_defeated`, `entity_kill`, `entity_kill_history`, `item_craft_stat`, `item_obtain`, `item_use`, `or`, `read`, `stat`, `visit_dimension_history`, `visit_structure_history`, plus `overlord_reign:nightwalker_power_count`.

`AdvancementObjective` polls `getOrStartProgress(...).isDone()`, so a hidden advancement with no display block is a valid detector. `cataclysm:kill_ender_guardian` and the nine Theurgy detectors rely on this (Q237).

### 2.4 Failure

**No visible quest can fail.** Death and retry covers every objective. Destructive civilization routes are terminal *outcomes*, not failures. A quest whose provider has been destroyed becomes unreachable, and since provider quests carry no Questlog entry there is nothing orphaned to resolve. This aligns with `reference/27` Q-048, which prefers consequence states over fail screens.

### 2.5 No corruption meter

There is no global numeric corruption, morality, domination, destruction or reputation score. Major choices write **explicit named facts**. Later content reasons from those facts (`reference/29`). This is the franchise pattern: Overlord I wrote branch-specific completion state through `SetQuestCompletedEvil` across thirteen quests without any meter.

### 2.6 Sequence breaking

Quest-critical structures exist in the world before activation. Discovery alone resolves nothing. The system recognises durable prior accomplishments — boss kills, one-time discoveries, major advancements — and must never respawn or duplicate a completed boss or world event (Q103, `reference/29`). Every quest in this document records its sequence-break handling.

### 2.7 No soft-locks

Authored choices may permanently destroy NPCs, settlements, services, branches and opportunities. No ordinary destructive choice may make the central campaign impossible to finish. Where a destroyed route would have been the way forward, the campaign provides an alternate, usually harsher path.

---

## 3. Campaign topology

The campaign is **147 visible Questlog quests** across twelve categories, plus **42 provider quests** handled entirely by the Villager Retaliation layer.

| Category | Quests |
| --- | --- |
| Opening | 1 |
| Tower Restoration | 14 |
| Minion Restoration | 3 |
| Bosses'Rise | 15 |
| End Campaign | 2 |
| Post-Credits | 4 |
| Magic | 23 |
| Farming / Automation | 4 |
| Adventure | 48 |
| Ice & Fire | 6 |
| Civilization | 22 |
| Rivalry | 5 |
| **Total** | **147** |

### 3.1 Openness

After the opening and the early Tower and Minion recovery phase the campaign is **semi-open**. Gnarl may recommend priorities; several major arcs run concurrently. Hard sequencing exists only where lore, world state or mod mechanics require it. Internal phases are authoring tools and are never presented as an Act I / Act II chapter menu.

### 3.2 Gates

Central progression depends on **capabilities and story facts**, never on a count of resolved civilizations. Valid gates: Minion progression actually available in the installed mod, equipment and artifacts, magical capability, dimension access, native-mod milestones, restored infrastructure, explicit central-story facts, and completed boss states where the plot genuinely depends on them.

Civilization arcs are **parallel political content**. Resolving all eleven is not required to complete the central campaign.

### 3.3 Activation defaults

| Rule | Effect |
| --- | --- |
| Q149 | The next visible quest activates immediately when the prior one completes. |
| Q150 | Where the completion presentation carries information needed for the next objective, the next quest activates after that presentation finishes. |
| Q151 | An optional Adventure's first quest activates on the first meaningful native discovery or encounter, unless a presenter or provider offers it first. Adventures are not all exposed from campaign start. |
| Q152 | A magic line activates immediately after its Tower room restoration completes. |
| Q153 | All fourteen Tower Restoration quests become available together after the opening Brown quest. |
| Q159 | All five Bosses'Rise locate quests activate together once the silent readiness gate is satisfied. |
| Q160 | The Ender Dragon quest activates only after the End-entry completion presentation has played. |

### 3.4 The silent readiness gate

Bosses'Rise opens when fourteen Tower functions are operational: Throne, Forge, Storage, Armory, Treasury, WayGates, Arena, Jail, Alchemy, Theurgy, Gluttony, Spell Study, Eidolon, Biomancy. Minion Infrastructure is already restored and receives no objective. Dragon Den is excluded and is a later independent development. **There is no visible 'Tower complete' quest or popup**; aggregate readiness is tracked silently (§7.1).

---

## 4. Presenters

Eight registered presenters. The roster is closed; no presenter is added because another NPC exists, provides a quest, or has dialogue in its source mod.

```text
Gnarl · Mortis · Quaver · Historian · Lestat · Gristle · Grubbison Jr · Giblet the Sixth
```

Presenters are non-corporeal presentation characters with one deliberate exception: **the Historian is corporeal**. Ordinary civilization providers are corporeal world actors and never enter the roster — a Dwarven Forger speaks as himself.

Gnarl requires no permanent physical presence. When the Overlord is in the Overworld he oversees from the Netherworld Tower; when the Overlord is in the Netherworld he oversees from the Overworld Tower. That is the in-universe basis for popup presentation (Q-011).

### 4.1 Theme ownership

| Presenter | Owns |
| --- | --- |
| Gnarl | Central campaign, general lore, politics, unspecialized magic, miscellaneous Adventure framing, all four tribe-restoration quests, and any qualifying material with no specialist owner. |
| Gristle | All food-related presentation: Gluttony / Farmer's Spell, cuisine, and the Farming / automation arc because its purpose is food production and estate agriculture. |
| Grubbison Jr | All mining-related presentation. Canon by Overlord decision (Q243). He owns no quests; his entire surface is Ramblings. |
| Giblet the Sixth | All forging-related presentation. |
| Mortis | Death, pet death and resurrection, explicit afterlife and death-system reactions. **Not** Blood magic, **not** Eidolon, **not** every undead encounter (Q096). |
| Quaver | Music, instruments, performance, Tower court reactions, **and the title function** — see §4.3. |
| Historian | Fathoms and evidence-led archaeological investigation in which he is actually involved. Generic world history remains Gnarl's. |
| Lestat | All authored NightWalker and vampirism presentation. |

**Tie-break: event theme beats source domain** (Q212). Tower Forge is Giblet's, not Gnarl's. Tower Gluttony is Gristle's. Fathoms fish butchering is Gristle's, not the Historian's.

### 4.2 Presenter is per popup

Every quest records `Presenter (unlock)` and `Presenter (completion)` separately (Q251). On 146 quests they are the same. On `OR-ADV-NIGH-001` they differ deliberately: Gnarl takes the unlock because the Master has just done something that may kill him, and Lestat takes the completion because he has arrived and is judging how it was done.

### 4.3 Quaver and the Minion Jester are one character

By Overlord decision (Q244b), the Minion Jester and Quaver are the same character. Name unchanged.

This matters because both franchise characters own **achievement-reactive title systems** — `Tower_Titles.8ld` and `Minstrel_Titles.8ld` — and merged they become the court's chronicler rather than a musician who occasionally reacts. Recovered source titles show the register precisely:

```text
alliterative epithet    Gnome Grinder · Spider Squasher · Hippy Humbler · Green Grabber
grandiose style         Savior of the Tower Heart · Ruler of the Stealth Army · Warrior of the Watery Ones
mock-honorific          Kelda's Special Friend · Rescuer of the Distressed Damsel · Minion Harvester
```

Quaver takes the advancements relevant to the merged character — **not every achievement**. The criterion is **a Rambling deserving of a title**: a deed that can carry an epithet, not a step. Plus all music and instrument milestones.

---

## 5. Popups, registers and visual states

### 5.1 Popup structure

Each visible quest produces **two presenter popups**: one on unlock, one on completion. 147 quests, **294 popups**.

Provider quests produce **no presenter popups**. They are bound NPC dialogue with their own `offer / in_progress / ready_to_turn_in / completed` states on the provider entity (Q243).

The runtime supports a fuller lifecycle than two popups — `objective_clarification`, `branch_framing`, `first_reminder`, `repeat_reminders`, `objective_updates`, `warning`, `success`, `failure`, `post_quest`, with per-slot timing. V5 authors **unlock and completion**. The remaining slots are available to the writing pass and are not specified here.

### 5.2 Visual states — five, universal

```text
neutral · approving · amused · displeased · severe
```

Every presenter requires exactly these five assets. They are visual attitudes, not writing modes. They are shared across all eight presenters and the set is closed.

### 5.3 Registers — per presenter

A register is the dominant **speech function** of a line. Registers are per presenter (Q252); visual states are universal. Four vocabularies are recovered from source rather than authored.

**Gnarl — eleven.** `GNARL_WRITING_RULES.md` §10 plus `GRAVE`.

```text
CEREMONIAL · DIRECTIVE · TACTICAL · ADMINISTRATIVE · HISTORICAL · MOCKING
REACTIVE · REWARD · CHOICE · OMINOUS · GRAVE
```

`GRAVE` is restricted: **Gnarl's seriousness is personal rather than tactical** — danger to something he is attached to, not danger the Overlord must handle. It resolves to `severe`. It applies to exactly three beats in the campaign: the two Helvar beats and the vampiric transformation. A fourth use must argue for itself (Q245b).

**Lestat — eleven**, from `reference/40` Register range. Do not lock him to one.

```text
polished social charm · intimate observation · seductive invitation · philosophical provocation
mocking humour · cultivated storytelling · plain emotional speech · vulgar aggression
wounded accusation · theatrical proclamation · musical performance
```

**Mortis — recovered** from `NW_B.8ld`, `NW_TR.8ld`, `Ambient_NW.8ld`. Terse declarative fatalism, two to four word sentences, ritual cadence, deadpan. Source lines: *"Minions. We live. We serve. We die."* / *"Life for death. Death for life."* / *"You bring me Lifeforce, I bring you Minions."* / *"No Blues."* The black velvet and the scythe are his running comic business.

```text
LITURGICAL · CLINICAL · MORBID-COMIC · DECLARATIVE
```

**Quaver — recovered** from `Minstrel_Titles.8ld` and `Tower_Titles.8ld`.

```text
EPITHET · PROCLAMATION · MOCK-HONORIFIC · PERFORMANCE
```

**Gristle, Giblet the Sixth, Grubbison Jr.** Built from the ordinary Minion register (`reference/10` §5: short, emotional, repetitive, action-centred, compressed through enthusiasm, obedience and appetite) raised exactly one notch. **Smarter minions, not smart minions**, each obsessed with his own domain. Short sentences remain; the difference from a Brown is competence in one craft, not articulacy.

```text
ENTHUSIASM · DOMAIN-EXPERTISE · IMPATIENCE · APPETITE
```

**Historian.** Based on the Dredge protagonist, who is also the Collector — in REIGN he is openly both, with no concealed identity and no reveal to author. He is the investigator and the appetite: he catalogues the deep because he wants what is in it, and the Overlord is how he reaches it.

```text
FIELD-NOTE · HYPOTHESIS · INVITATION · UNDERSTATEMENT · DISQUIET
```

### 5.4 How a reaction is chosen

The reaction is to **this presentation** and **this resolution**. It is never a default derived from success (Q244, Q246).

The completion state reflects **how the presenter judges the act**, not whether it succeeded. Gnarl approves of evil, not of completion. Subjugating a polity earns approval; razing one amuses him; leaving one free disappoints him. Three different faces for the same quest.

Gnarl's seriousness is selective and self-interested (Q-051). He does not become solemn because other peoples have suffered. A solemn scene is never evidence of broad compassion. His prejudices are characterization, not world facts (`reference/04`).

### 5.5 Distribution

| | neutral | approving | amused | displeased | severe | branching |
| --- | --- | --- | --- | --- | --- | --- |
| Unlock | 115 | 8 | 10 | 1 | 13 | — |
| Completion | 38 | 69 | 23 | 1 | 4 | 12 |

`displeased` is deliberately scarce. It appears on one unlock — the Piglin anchor, where Gnarl is being made to discuss his own ancestry — and one completion — the forced truce, where he evaluates the Master and is unimpressed. Both are genuinely REACTIVE.

---

## 6. Grounding facts carried into the reactions

These are the REIGN-specific relationships that shape presenter reaction. A builder who does not hold them will write the campaign wrong.

- **Helvar is the Third Overlord** — the Overlord I and Raising Hell player character, and **Gnarl's last Master**. At the end of Raising Hell Gnarl spoke of him with unusual personal attachment and allowed that he might still escape the Infernal Abyss. He has escaped, mad and degraded, and the current Overlord is sent to put him down. This is the campaign's only genuinely personal Gnarl material.
- **Biomancy is Gnarl's own discipline**, developed during the Silence from Solarius' magically overexposed transformation, while he lacked an Overlord to direct the work toward suitably unfortunate subjects (R-039, Q-006). Three `CEREMONIAL` beats sit in that arc against eight in all the rest.
- **Piglins descend from a union between a Minion and an ordinary pig.** It is true in REIGN, and Gnarl regards it as a source of shame he would strongly prefer not to discuss (`reference/24`).
- **Umvuthana subjugation is the humiliation of a living creator-god** forced to acknowledge the Overlord before his worshippers (`reference/25`). Its SUBJUGATED outcome is `amused` where every other civilization's is `approving`.
- **Ribbits are cozy and harmless by design.** The Overlord tone is the contrast. Both SUBJUGATED and DESTROYED complete `amused` (`reference/22`).
- **Gnumus are Halflings transformed by Gluttony magic, and they do not know it** (`reference/19`).
- **The Great Cataclysm was an Elf destroying the previous Overlord's Tower Heart** (R-032). Gnarl carries that grievance. Florian's responsibility is specialist knowledge, not common knowledge.
- **The Dark Tower is a new structure** manifested by the Netherworld, not the surviving original (R-011 rejected, R-012 adapted). Its rooms already hold their intended features — the throne room has its endless pit, the forge room its lava pit. Restoration means installing and activating, never repairing.
- **The Netherworld is Minecraft's Nether** (Q-008), and **the End is the Wasteland rupture the Cataclysm exposed** (R-034).
- **Lestat senses the Overlord himself** through his unusual Akasha-blood sensitivity and travels to investigate. Gnarl does not summon him. He had never met Gnarl or the Minions before this reign (`reference/44` §15, §16). He diagnoses repression before weakness, and distinguishes mastery from mere completion (`reference/40`).
- **Gnarl and Lestat conflict institutionally, not morally.** Gnarl does not object to predation or immortality; he objects to Lestat intruding on his advisory monopoly. Both want the Overlord alive and stronger, and become immediately practical when a real threat appears.
---

## 7. The civilization system

**Eleven civilizations.** Demons are excluded and remain a Netherworld subculture without a disposition state.

```text
Villagers · Illagers · Dwarves · Gnumus · Goblins · Kobolds
Ribbits · Sea Dwellers · Piglins · Umvuthana · Myrmex
```

### 7.1 Two Questlog entries per civilization

Each civilization has exactly **two** visible Questlog quests.

1. **Anchor.** Activates on entering a viable civilization anchor. Objective: place the banner to lock it. Completion locks the anchor, selects and spawns the required quest-role NPCs, and distributes the provider quests.
2. **State.** Follows the anchor quest. Objective is the terminal outcome. Resolves through whichever outcome the provider questlines produce. Incompatible routes lock permanently.

### 7.2 The provider layer

Everything between those two entries is handled by the **Villager Retaliation provider layer**. Provider quests are offered, tracked and turned in by their bound NPC. **They have no Questlog entries and no presenter popups.** Questlog carries only the final civilization state.

A provider quest never sets a terminal state directly. It writes a **named fact**. The civilization-state quest reads those facts. The political meaning comes from the exact provider chain and its consequence, never from a count of completed errands. There is no hidden sidequest score.

**42 provider quests** across eleven civilizations: 10 NEUTRAL, 20 SUBJUGATED, 10 DESTROYED, 2 unrouted. SUBJUGATED carries double because leverage accumulates across several providers before a leader accepts submission.

### 7.3 Legal terminal states differ by civilization

| Civilization | Legal states |
| --- | --- |
| Villagers | NEUTRAL / SUBJUGATED / **HOSTILE**. DESTROYED is a local settlement or anchor outcome, never a civilization state, because human society is decentralized (Q-026). |
| Illagers | NEUTRAL / SUBJUGATED / DESTROYED, reached through an intermediate **COWED** phase after the Bastille is overpowered. COWED is a quest-state phase layered over NEUTRAL, not a fourth global disposition. |
| The other nine | NEUTRAL / SUBJUGATED / DESTROYED. |

`UNRESOLVED` is the absence of a completed terminal route. It is not an outcome. A completed terminal result is permanent for that anchor. Terminal state affects the **authored anchor polity only** — not every procedurally generated settlement of that species.

### 7.4 Anchors

| Civilization | Anchor | Quest starter |
| --- | --- | --- |
| Villagers | Biome-appropriate remnant or successor of a historical human location; Spree is a strong candidate but not fixed | Local providers |
| Illagers | A designated Take a Pillage **Bastille** | Bastille-bound; arbitrary raids and patrols never start the arc |
| Dwarves | A successor hold of the Golden Hills / Golden Halls | Designated **Forge-Thane** |
| Gnumus | The principal Large Gnumus Settlement | Designated **Elder Shaman** |
| Goblins | A major Goblin Camp | Designated `goblins_tyranny:leader_goblin` |
| Kobolds | A designated Kobold Den | One protected **Kobold Captain** |
| Ribbits | A designated Ribbit Village | A Gardener holding the local role of **Elder** |
| Sea Dwellers | A designated Sea Village | A senior trader titled **Sea Elder** |
| Piglins | A designated Nether Village | A Piglin Brute titled **Chieftain** |
| Umvuthana | A Grove centred on one Umvuthi's throne complex | The **Umvuthi** himself; mask recognition permits the first audience |
| Myrmex | The canonical hive | The **Queen**, through native opinion |

Anchors exist in the generated world before activation. Discovery alone resolves nothing.

### 7.5 Provider quest inventory


#### Spree

**`OR-PRV-SPR-001` — Farmer / NEUTRAL**

- Bound provider: provider: bound Farmer of the Spree anchor
- Objective (specification): Cultivate and harvest at least three staple crop types and deliver a mixed local reserve to the bound Farmer, leaving control with Spree
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `SPREE_FARMER_INDEPENDENT`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q053

**`OR-PRV-SPR-002` — Farmer / SUBJUGATED**

- Bound provider: provider: bound Farmer of the Spree anchor
- Objective (specification): Deliver a large mixed reserve of prepared/staple food after the dependency route is revealed, with at least three food classes
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `SPREE_FARMER_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q054

**`OR-PRV-SPR-003` — Miner / NEUTRAL**

- Bound provider: provider: bound Miner of the Spree anchor
- Objective (specification): Use the native Ore Grinder to process representative iron/copper/gold material and complete one advanced native Miner trade, leaving the operation locally controlled
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `SPREE_MINER_INDEPENDENT`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q055

**`OR-PRV-SPR-004` — Miner / SUBJUGATED**

- Bound provider: provider: bound Miner of the Spree anchor
- Objective (specification): Complete the Miner dependency chain per Q056
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `SPREE_MINER_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q056

**`OR-PRV-SPR-005` — Guard / NEUTRAL**

- Bound provider: provider: bound Guard of the Spree anchor
- Objective (specification): Create and equip a local Guard Villager, assign a defensive patrol/checkpoint, and end the route with that guard no longer following the Overlord
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `SPREE_GUARD_INDEPENDENT`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q057

**`OR-PRV-SPR-006` — Guard / SUBJUGATED**

- Bound provider: provider: bound Guard of the Spree anchor
- Objective (specification): Complete the Guard dependency chain per Q058
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `SPREE_GUARD_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q058

**`OR-PRV-SPR-007` — Mayor / DESTROYED**

- Bound provider: provider: bound Mayor of the Spree anchor
- Objective (specification): Kill the Mayor and destroy or remove the bound civic pillars that keep the polity functioning, without requiring the death of every villager
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `SPREE_DESTROYED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q059


#### Dwarves

**`OR-PRV-DWF-001` — Record Keeper / NEUTRAL**

- Bound provider: provider: bound Record Keeper of the Dwarves anchor
- Objective (specification): Receive the surviving historical testimony from the Record Keeper, then prove material continuity by completing one source-backed Dwarven craft sequence combining Dwarven metal with rune craft/forge work in the canonical hold
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `DWARF_CONTINUITY_PROVEN`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q060, Q171

**`OR-PRV-DWF-002` — Forger / SUBJUGATED**

- Bound provider: provider: bound Forger of the Dwarves anchor
- Objective (specification): Complete a high-tier forge commission using scarce material supplied by the Overlord, then convert that dependency into an explicit production/service commitment to the Master
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `DWARF_FORGER_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q061

**`OR-PRV-DWF-003` — Warrior / DESTROYED**

- Bound provider: provider: bound Warrior of the Dwarves anchor
- Objective (specification): Use the Warrior chain to expose the hold's defensive vulnerability, then defeat the Forge-Thane and the bound military authority. Do not require killing every Dwarf
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `DWARF_DESTROYED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q062


#### Gnumus

**`OR-PRV-GNU-001` — Vintage reconstruction / NEUTRAL**

- Bound provider: provider: bound Vintage reconstruction of the Gnumus anchor
- Objective (specification): After the ancestry decision, complete the native Vintage reconstruction progression through Vintage Alloy, one Vintage equipment improvement, and the Vintage Universal Tool milestone, then leave the reconstructed technology under Gnumu control
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `GNUMU_INDEPENDENT`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q174

**`OR-PRV-GNU-002` — Main Merchant / SUBJUGATED**

- Bound provider: provider: bound Main Merchant of the Gnumus anchor
- Objective (specification): Complete a bound Main Merchant trade, then supply the scarce Vintage material needed for one later Vintage reconstruction the settlement has not independently completed, and record the continuing access/service obligation
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `GNUMU_MERCHANT_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q175

**`OR-PRV-GNU-003` — Elder Shaman / DESTROYED**

- Bound provider: provider: bound Elder Shaman of the Gnumus anchor
- Objective (specification): After destructive commitment, require the Elder Shaman's death plus removal of the bound Main Merchant and the bound Hunter/defense authority. Do not require killing ordinary workers or destroying every settlement block
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `GNUMU_DESTROYED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q176


#### Goblins

**`OR-PRV-GOB-001` — Merchant + Bartender / NEUTRAL**

- Bound provider: provider: bound Merchant + Bartender of the Goblins anchor
- Objective (specification): Complete one meaningful native Merchant transaction satisfying the merchant-success surface and one native Bartender/liquor interaction satisfying the liquor-success surface, each with its bound provider
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `GOBLIN_INDEPENDENT`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q177

**`OR-PRV-GOB-002` — Engineer / SUBJUGATED**

- Bound provider: provider: bound Engineer of the Goblins anchor
- Objective (specification): Complete one source-owned Engineer workbench project reaching the native engineer-success / prototype-upgrade surface using Overlord-supplied materials, then record the continuing technology/service obligation
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `GOBLIN_ENGINEER_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q178

**`OR-PRV-GOB-003` — Blacksmith / SUBJUGATED**

- Bound provider: provider: bound Blacksmith of the Goblins anchor
- Objective (specification): Supply the bound Blacksmith with the high-value inputs required by its native advanced service path, including the Netherite/Engineer material bridge, complete one advanced Blacksmith service, then record the arms/production obligation
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `GOBLIN_BLACKSMITH_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q179

**`OR-PRV-GOB-004` — Merchant / SUBJUGATED**

- Bound provider: provider: bound Merchant of the Goblins anchor
- Objective (specification): After the ordinary trade surface is demonstrated, complete one higher-value patronage transaction supplied by the Overlord and record preferential access/commerce obligation
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `GOBLIN_MERCHANT_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q180

**`OR-PRV-GOB-005` — Leader / DESTROYED**

- Bound provider: provider: bound Leader of the Goblins anchor
- Objective (specification): Complete the Goblin destructive route per Q070
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `GOBLIN_DESTROYED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q070


#### Kobolds

**`OR-PRV-KOB-001` — Captain / SUBJUGATED**

- Bound provider: provider: bound Captain of the Kobolds anchor
- Objective (specification): Complete the Captain resource/logistics proof
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `KOBOLD_CAPTAIN_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q071, Q181

**`OR-PRV-KOB-002` — Engineer / SUBJUGATED**

- Bound provider: provider: bound Engineer of the Kobolds anchor
- Objective (specification): Complete one meaningful infrastructure/engineering commission for the Den rather than a generic item hand-in
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `KOBOLD_ENGINEER_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q072, Q182

**`OR-PRV-KOB-003` — Enchanter / SUBJUGATED**

- Bound provider: provider: bound Enchanter of the Kobolds anchor
- Objective (specification): Complete the Enchanter service
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `KOBOLD_ENCHANTER_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q073, Q183

**`OR-PRV-KOB-004` — Den authority / DESTROYED**

- Bound provider: provider: bound Den authority of the Kobolds anchor
- Objective (specification): Destroy or disable the indispensable Den function identified by Q184
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `KOBOLD_DESTROYED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q074, Q184


#### Ribbits

**`OR-PRV-RIB-001` — — / NEUTRAL**

- Bound provider: provider: bound — of the Ribbits anchor
- Objective (specification): Complete the Ribbit prosperity proof
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `RIBBIT_INDEPENDENT`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q075, Q185

**`OR-PRV-RIB-002` — Merchant / SUBJUGATED**

- Bound provider: provider: bound Merchant of the Ribbits anchor
- Objective (specification): Complete the Merchant commitment
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `RIBBIT_MERCHANT_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q076, Q186

**`OR-PRV-RIB-003` — Fisherman / SUBJUGATED**

- Bound provider: provider: bound Fisherman of the Ribbits anchor
- Objective (specification): Complete the Fisherman commitment
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `RIBBIT_FISHERMAN_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q077, Q187

**`OR-PRV-RIB-004` — Sorcerer / SUBJUGATED**

- Bound provider: provider: bound Sorcerer of the Ribbits anchor
- Objective (specification): Complete the Sorcerer commitment
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `RIBBIT_SORCERER_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q078, Q188

**`OR-PRV-RIB-005` — — / DESTROYED**

- Bound provider: provider: bound — of the Ribbits anchor
- Objective (specification): Complete the Ribbit destructive route
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `RIBBIT_DESTROYED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q079


#### Sea Dwellers

**`OR-PRV-SEA-001` — Blacksmith / SUBJUGATED**

- Bound provider: provider: bound Blacksmith of the Sea Dwellers anchor
- Objective (specification): Complete the Blacksmith commitment
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `SEADWELLER_BLACKSMITH_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q080, Q189

**`OR-PRV-SEA-002` — Collector / SUBJUGATED**

- Bound provider: provider: bound Collector of the Sea Dwellers anchor
- Objective (specification): Complete the Collector commitment
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `SEADWELLER_COLLECTOR_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q081, Q190

**`OR-PRV-SEA-003` — Institutional / SUBJUGATED**

- Bound provider: provider: bound Institutional of the Sea Dwellers anchor
- Objective (specification): Complete the institutional leverage proof
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `SEADWELLER_INSTITUTIONAL_LEVERAGE`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q082, Q191

**`OR-PRV-SEA-004` — Civic core / DESTROYED**

- Bound provider: provider: bound Civic core of the Sea Dwellers anchor
- Objective (specification): Destroy the civic/resource core identified by Q192
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `SEADWELLER_DESTROYED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q083, Q192


#### Piglins

**`OR-PRV-PIG-001` — — / NEUTRAL**

- Bound provider: provider: bound — of the Piglins anchor
- Objective (specification): Complete the Piglin NEUTRAL exact proof
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `PIGLIN_INDEPENDENT`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q085, Q193

**`OR-PRV-PIG-002` — Chieftain / SUBJUGATED**

- Bound provider: provider: bound Chieftain of the Piglins anchor
- Objective (specification): Demonstrate control over valuable gold/barter supply and key village resource infrastructure, then obtain Chieftain submission
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `PIGLIN_CHIEFTAIN_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q086, Q194

**`OR-PRV-PIG-003` — — / DESTROYED**

- Bound provider: provider: bound — of the Piglins anchor
- Objective (specification): Destroy the protected order identified by Q195
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `PIGLIN_DESTROYED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q087, Q195


#### Umvuthana

**`OR-PRV-UMV-001` — Crane / —**

- Bound provider: provider: bound Crane of the Umvuthana anchor
- Objective (specification): Complete the Crane healing/support dependency
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `UMVUTHANA_CRANE`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q088, Q196

**`OR-PRV-UMV-002` — Raptor / —**

- Bound provider: provider: bound Raptor of the Umvuthana anchor
- Objective (specification): Complete the Raptor martial allegiance challenge
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `UMVUTHANA_RAPTOR`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q089, Q197


#### Illagers

**`OR-PRV-ILL-001` — — / NEUTRAL**

- Bound provider: provider: bound — of the Illagers anchor
- Objective (specification): Complete the post-COWED NEUTRAL proof
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `ILLAGER_INDEPENDENT`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q198

**`OR-PRV-ILL-002` — — / SUBJUGATED**

- Bound provider: provider: bound — of the Illagers anchor
- Objective (specification): Complete the post-COWED SUBJUGATED proof
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `ILLAGER_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q199

**`OR-PRV-ILL-003` — — / DESTROYED**

- Bound provider: provider: bound — of the Illagers anchor
- Objective (specification): Complete the post-COWED DESTROYED proof
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `ILLAGER_DESTROYED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q200


#### Myrmex

**`OR-PRV-MYR-001` — Canonical hive / NEUTRAL**

- Bound provider: provider: bound Canonical hive of the Myrmex anchor
- Objective (specification): Give resin to a worker and complete one native trade with the canonical hive, continue native opinion to 50+, then explicitly recognize the Queen and hive as independent
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `MYRMEX_INDEPENDENT`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q090, Q140

**`OR-PRV-MYR-002` — Queen / SUBJUGATED**

- Bound provider: provider: bound Queen of the Myrmex anchor
- Objective (specification): Reach 75+ opinion, obtain and use the Myrmex Staff on the canonical hive to designate one new FOOD or NURSERY room, then complete the Queen-facing submission resolution
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `MYRMEX_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q091, Q141

**`OR-PRV-MYR-003` — Queen / DESTROYED**

- Bound provider: provider: bound Queen of the Myrmex anchor
- Objective (specification): The canonical Queen's deliberate death is sufficient
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `MYRMEX_DESTROYED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q092, Q139

---

## 8. The quest inventory

147 visible Questlog quests. Every quest records the eleven fields required by `V5_QUEST_SPECIFICATION_SCHEMA.md` §1, plus per-popup presenter and register vocabulary.

Reading a quest record:

- **Objective (specification)** is design data and never player-facing.
- **Order (presenter voice)** is `TO BE AUTHORED` on every quest by design (§2.2).
- **Completion condition** is the exact detector. `framework objective type needed` marks the ten quests whose mod ships no advancements and which must use an existing Questlog type.
- **Persistent fact** is `NONE` unless later content must read the outcome. Keys marked `TO BE REGISTERED` require an entry in `NARRATIVE_FACTS.md`.
- **Unlock / Completion popup** give visual state, register and the reason for that reaction.


### Opening

#### `OR-OPN-001` — opening/a_new_master

- **Role:** Prove the Minion system functions and establish the new reign
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** After the opening Gnarl presentation (closure register)
- **Prerequisite:** —
- **Objective (specification):** Craft the Master's Staff and summon the first Brown in the same quest
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Master's Staff crafted and first Brown summoned
- **Unlock popup:** `approving` · `CEREMONIAL` — the Netherworld has chosen; Gnarl has waited centuries for this (Q-001, Q-006)
- **Completion popup:** `amused` · `MOCKING` — the staff works and a Brown crawls out — relief expressed as mockery, not solemnity
- **Consequence:** Minion system proven functional; unlocks all 14 Tower Restoration quests (Q153)
- **Persistent fact:** overlord_reign:minions/brown_recovered
- **Sequence-break handling:** Recognize a pre-existing Brown if summoned before activation
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** §8.1, Q153


### Tower Restoration

#### `OR-TWR-001` — tower/throne

- **Role:** Make the Tower throne operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Throne: Necrolord Chair installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Necrolord Chair installed
- **Unlock popup:** `approving` · `CEREMONIAL` — the seat is bare; the endless pit is already there (Q-011)
- **Completion popup:** `approving` · `CEREMONIAL` — a seated Overlord is the point of the institution
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q008

#### `OR-TWR-002` — tower/forge

- **Role:** Make the Tower forge operational
- **Presenter:** Giblet the Sixth
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Forge: Hot Iron Smithing Anvil and Crucible installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Hot Iron Smithing Anvil and Crucible installed
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — the room was shaped for a forge and has the lava pit waiting (Q-011)
- **Completion popup:** `approving` · `REWARD` — Giblet approves of a working anvil
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q002; presenter per forging theme beats source domain (Q212, §4.4)

#### `OR-TWR-003` — tower/storage

- **Role:** Make the Tower storage operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Storage: Drawer Controller connected to at least one functional drawer bank
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Drawer Controller connected to at least one functional drawer bank
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — an inventory problem
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — shelves reported as shelves
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q003

#### `OR-TWR-004` — tower/armory

- **Role:** Make the Tower armory operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Armory: One armor-display and one weapon-display from distinct supported families
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** One armor-display and one weapon-display from distinct supported families
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — arms belong on display
- **Completion popup:** `amused` · `MOCKING` — Gnarl enjoys the vanity of it
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q004

#### `OR-TWR-005` — tower/treasury

- **Role:** Make the Tower treasury operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Treasury: Gold Barrel installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Gold Barrel installed
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — somewhere to put the gold
- **Completion popup:** `amused` · `MOCKING` — the barrel fills; smug
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q008

#### `OR-TWR-006` — tower/waygates

- **Role:** Make the Tower waygates operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the WayGates: Tower Waystone placed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Tower Waystone placed
- **Unlock popup:** `neutral` · `DIRECTIVE` — a waystone must be found in the world and carried home (Q005)
- **Completion popup:** `approving` · `REWARD` — the Tower is connected
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q005

#### `OR-TWR-007` — tower/arena

- **Role:** Make the Tower arena operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Arena: Supplementaries Cage installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Supplementaries Cage installed
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — somewhere to make things fight
- **Completion popup:** `amused` · `MOCKING` — Gnarl looks forward to using it
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q007

#### `OR-TWR-008` — tower/jail

- **Role:** Make the Tower jail operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Jail: Big Iron Grate installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Big Iron Grate installed
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — prisoner management, routine
- **Completion popup:** `amused` · `MOCKING` — a working cell pleases him
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q007

#### `OR-TWR-009` — tower/alchemy

- **Role:** Make the Tower alchemy operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Alchemy: Ars Elixirum Glass Cauldron installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Ars Elixirum Glass Cauldron installed
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — a workbench for volatile study
- **Completion popup:** `approving` · `REWARD` — the discipline is housed
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006, Q232

#### `OR-TWR-010` — tower/theurgy

- **Role:** Make the Tower theurgy operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Theurgy: Theurgy core workstation installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Theurgy core workstation installed
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — a workbench for transmutation
- **Completion popup:** `approving` · `REWARD` — the discipline is housed
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006

#### `OR-TWR-011` — tower/gluttony

- **Role:** Make the Tower gluttony operational
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Gluttony: Farmer's Spell core workstation installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Farmer's Spell core workstation installed
- **Unlock popup:** `neutral` · `DIRECTIVE` — Gristle is getting his kitchen
- **Completion popup:** `approving` · `REWARD` — Gristle has his domain
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006; presenter per food theme beats source domain (Q212, §4.2)

#### `OR-TWR-012` — tower/spell_study

- **Role:** Make the Tower spell study operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Spell Study: Iron's Spells inscription workstation installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Iron's Spells inscription workstation installed
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — a place to inscribe
- **Completion popup:** `approving` · `REWARD` — the discipline is housed
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006

#### `OR-TWR-013` — tower/eidolon

- **Role:** Make the Tower eidolon operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Eidolon: Eidolon core workstation installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Eidolon core workstation installed
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — occult plant, handled as plant (Q096: Eidolon is not Mortis material)
- **Completion popup:** `approving` · `REWARD` — housed and controlled
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006

#### `OR-TWR-014` — tower/biomancy

- **Role:** Make the Tower biomancy operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Biomancy: Biomancy core workstation installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Biomancy core workstation installed
- **Unlock popup:** `approving` · `CEREMONIAL` — Gnarl's own Silence-era discipline finally gets a chamber (R-039, Q-006)
- **Completion popup:** `approving` · `CEREMONIAL` — what he researched for centuries without a Master is now housed
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006; Gnarl/Biomancy authorship per R-039 and Q-006


### Minion Restoration

#### `OR-MIN-001` — minions/restore_red

- **Role:** Restore the Red tribe
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Enter the Netherworld, kill a Blaze, return with a Blaze Rod
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** All feat steps complete
- **Unlock popup:** `neutral` · `DIRECTIVE` — the Reds can be recovered
- **Completion popup:** `approving` · `REWARD` — fire returns to the Horde
- **Consequence:** Invokes the Minion owner API to unlock the Red tribe (§8); one completion presentation (Q011)
- **Persistent fact:** overlord_reign:minions/red_recovered
- **Sequence-break handling:** Recognize a pre-existing Blaze Rod
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q009, Q010, Q011, §8.2-8.4

#### `OR-MIN-002` — minions/restore_green

- **Role:** Restore the Green tribe
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MIN-001
- **Objective (specification):** Brew a Potion of Poison, become poisoned, kill a Witch while poisoned
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** All feat steps complete
- **Unlock popup:** `amused` · `MOCKING` — the feat requires the Master to poison himself on purpose
- **Completion popup:** `amused` · `MOCKING` — it worked, and Gnarl found the method very funny
- **Consequence:** Invokes the Minion owner API to unlock the Green tribe (§8); one completion presentation (Q011)
- **Persistent fact:** overlord_reign:minions/green_recovered
- **Sequence-break handling:** Recognize a prior Witch kill only if poisoned at the time
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q009, Q010, Q011, §8.2-8.4

#### `OR-MIN-003` — minions/restore_blue

- **Role:** Restore the Blue tribe
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MIN-002
- **Objective (specification):** Prepare Water Breathing, enter an Ocean Monument, kill an Elder Guardian
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** All feat steps complete
- **Unlock popup:** `neutral` · `TACTICAL` — drowning is a real risk to a thin Horde (§8: depletion is an operational problem)
- **Completion popup:** `approving` · `CEREMONIAL` — the Horde is whole for the first time since the Silence
- **Consequence:** Invokes the Minion owner API to unlock the Blue tribe (§8); one completion presentation (Q011)
- **Persistent fact:** overlord_reign:minions/blue_recovered
- **Sequence-break handling:** Recognize a prior Elder Guardian kill
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q009, Q010, Q011, §8.2-8.4


### Bosses'Rise

#### `OR-BOS-001` — bosses_rise/skor/locate

- **Role:** Locate the skor domain
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** All five locate quests activate together when the §7.1 readiness gate is satisfied (Q159)
- **Prerequisite:** Tower readiness gate
- **Objective (specification):** Locate the Nordberg / frozen domain domain / anomaly
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Domain located
- **Unlock popup:** `neutral` · `TACTICAL` — something feeds on the frozen north
- **Completion popup:** `neutral` · `TACTICAL` — the domain is located
- **Consequence:** Opens the investigation stage (Q012)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior discovery of the domain
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q159

#### `OR-BOS-002` — bosses_rise/skor/investigate

- **Role:** Establish what skor is
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-001
- **Objective (specification):** Trigger one qualifying Phase 2 frost / icicle attack event without killing Skor
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Native proof event observed
- **Unlock popup:** `neutral` · `TACTICAL` — a beast empowered by the wound, nothing more
- **Completion popup:** `neutral` · `TACTICAL` — the evidence is understood
- **Consequence:** Gnarl delivers the approved interpretation of Skor / Yeti
- **Persistent fact:** NONE
- **Sequence-break handling:** Native proof may already have occurred
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q143

#### `OR-BOS-003` — bosses_rise/skor/defeat

- **Role:** Destroy skor
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-002
- **Objective (specification):** Defeat Skor / Yeti
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** block_factorys_bosses:kill_yeti
- **Unlock popup:** `neutral` · `DIRECTIVE` — the kill order
- **Completion popup:** `approving` · `REWARD` — the north is quiet
- **Consequence:** Advances the five-subcampaign progression
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an early boss kill; never respawn
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q013-Q017

#### `OR-BOS-004` — bosses_rise/sirok/locate

- **Role:** Locate the sirok domain
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** All five locate quests activate together when the §7.1 readiness gate is satisfied (Q159)
- **Prerequisite:** Tower readiness gate
- **Objective (specification):** Locate the Ruborian desert domain / anomaly
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Domain located
- **Unlock popup:** `neutral` · `TACTICAL` — the desert is moving where it should not
- **Completion popup:** `neutral` · `TACTICAL` — the domain is located
- **Consequence:** Opens the investigation stage (Q012)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior discovery of the domain
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q159

#### `OR-BOS-005` — bosses_rise/sirok/investigate

- **Role:** Establish what sirok is
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-004
- **Objective (specification):** Crack one armored body segment, then strike it again to trigger the native poisonous-blood spill
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Native proof event observed
- **Unlock popup:** `neutral` · `TACTICAL` — the worm was enlarged by rift energy
- **Completion popup:** `neutral` · `TACTICAL` — the evidence is understood
- **Consequence:** Gnarl delivers the approved interpretation of Sirok / Sandworm
- **Persistent fact:** NONE
- **Sequence-break handling:** Native proof may already have occurred
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q144

#### `OR-BOS-006` — bosses_rise/sirok/defeat

- **Role:** Destroy sirok
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-005
- **Objective (specification):** Defeat Sirok / Sandworm
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** block_factorys_bosses:kill_sandworm
- **Unlock popup:** `neutral` · `DIRECTIVE` — the kill order
- **Completion popup:** `approving` · `REWARD` — the desert is emptied
- **Consequence:** Advances the five-subcampaign progression
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an early boss kill; never respawn
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q013-Q017

#### `OR-BOS-007` — bosses_rise/ashlord/locate

- **Role:** Locate the ashlord domain
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** All five locate quests activate together when the §7.1 readiness gate is satisfied (Q159)
- **Prerequisite:** Tower readiness gate
- **Objective (specification):** Locate the Dragon Tower domain / anomaly
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Domain located
- **Unlock popup:** `severe` · `OMINOUS` — a dragon has been raised from the dead
- **Completion popup:** `neutral` · `TACTICAL` — the domain is located
- **Consequence:** Opens the investigation stage (Q012)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior discovery of the domain
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q159

#### `OR-BOS-008` — bosses_rise/ashlord/investigate

- **Role:** Establish what ashlord is
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-007
- **Objective (specification):** Recover one native block_factorys_bosses:dragon_banner from the bound Dragon Tower
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Native proof event observed
- **Unlock popup:** `neutral` · `HISTORICAL` — the first of the resurrected
- **Completion popup:** `neutral` · `TACTICAL` — the evidence is understood
- **Consequence:** Gnarl delivers the approved interpretation of Ashlord
- **Persistent fact:** NONE
- **Sequence-break handling:** Native proof may already have occurred
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q145

#### `OR-BOS-009` — bosses_rise/ashlord/defeat

- **Role:** Destroy ashlord
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-008
- **Objective (specification):** Defeat Ashlord
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** block_factorys_bosses:kill_dragon
- **Unlock popup:** `neutral` · `DIRECTIVE` — the kill order
- **Completion popup:** `approving` · `REWARD` — the resurrection is undone
- **Consequence:** Advances the five-subcampaign progression
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an early boss kill; never respawn
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q013-Q017

#### `OR-BOS-010` — bosses_rise/helvar/locate

- **Role:** Locate the helvar domain
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** All five locate quests activate together when the §7.1 readiness gate is satisfied (Q159)
- **Prerequisite:** Tower readiness gate
- **Objective (specification):** Locate the Underworld domain / anomaly
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Domain located
- **Unlock popup:** `severe` · `GRAVE` — the Third Overlord is loose — Gnarl's last Master, whom he hoped might escape the Infernal Abyss (01 timeline, 28)
- **Completion popup:** `neutral` · `TACTICAL` — the domain is located
- **Consequence:** Opens the investigation stage (Q012)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior discovery of the domain
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q159

#### `OR-BOS-011` — bosses_rise/helvar/investigate

- **Role:** Establish what helvar is
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-010
- **Objective (specification):** Obtain the native block_factorys_bosses:underworld_arena_key and use it to pass the boss-door progression
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Native proof event observed
- **Unlock popup:** `severe` · `GRAVE` — Gnarl identifies the man he served and expressed unusual personal attachment to, now a mad remnant
- **Completion popup:** `neutral` · `TACTICAL` — the evidence is understood
- **Consequence:** Gnarl delivers the approved interpretation of Helvar / Third Overlord
- **Persistent fact:** NONE
- **Sequence-break handling:** Native proof may already have occurred
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q146

#### `OR-BOS-012` — bosses_rise/helvar/defeat

- **Role:** Destroy helvar
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-011
- **Objective (specification):** Defeat Helvar / Third Overlord
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** block_factorys_bosses:kill_underworld_knight
- **Unlock popup:** `neutral` · `DIRECTIVE` — the kill order
- **Completion popup:** `severe` · `GRAVE` — the Master Gnarl waited centuries for is put down by his replacement; no mockery here
- **Consequence:** Advances the five-subcampaign progression
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an early boss kill; never respawn
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q013-Q017

#### `OR-BOS-013` — bosses_rise/nerakyss/locate

- **Role:** Locate the nerakyss domain
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** All five locate quests activate together when the §7.1 readiness gate is satisfied (Q159)
- **Prerequisite:** Tower readiness gate
- **Objective (specification):** Locate the Kraken Ship domain / anomaly
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Domain located
- **Unlock popup:** `neutral` · `TACTICAL` — the sea has leaked for centuries
- **Completion popup:** `neutral` · `TACTICAL` — the domain is located
- **Consequence:** Opens the investigation stage (Q012)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior discovery of the domain
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q159

#### `OR-BOS-014` — bosses_rise/nerakyss/investigate

- **Role:** Establish what nerakyss is
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-013
- **Objective (specification):** Defeat the three native pirate guard variants protecting the encounter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Native proof event observed
- **Unlock popup:** `neutral` · `HISTORICAL` — old contamination, still running
- **Completion popup:** `neutral` · `TACTICAL` — the evidence is understood
- **Consequence:** Gnarl delivers the approved interpretation of Nerakyss
- **Persistent fact:** NONE
- **Sequence-break handling:** Native proof may already have occurred
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q147

#### `OR-BOS-015` — bosses_rise/nerakyss/defeat

- **Role:** Destroy nerakyss
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-014
- **Objective (specification):** Defeat Nerakyss
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** block_factorys_bosses:kill_kraken
- **Unlock popup:** `neutral` · `DIRECTIVE` — the kill order
- **Completion popup:** `approving` · `REWARD` — the ocean threat is cut
- **Consequence:** Advances the five-subcampaign progression
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an early boss kill; never respawn
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q013-Q017


### End Campaign

#### `OR-END-001` — end/expedition

- **Role:** Enter the Cataclysm Dimension
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately after the post-five Gnarl presentation-only transition (Q018)
- **Prerequisite:** All five Bosses'Rise subcampaigns
- **Objective (specification):** Enter the End dimension
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Entering the dimension is the authored threshold (Q019)
- **Unlock popup:** `severe` · `OMINOUS` — the End is the Wasteland rupture the Cataclysm exposed (R-034)
- **Completion popup:** `severe` · `OMINOUS` — the wound is entered
- **Consequence:** Establishes the source-side wound; completion presentation identifies the Ender Dragon as the living anchor (Q160)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior End entry
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q018, Q019

#### `OR-END-002` — end/ender_dragon

- **Role:** Resolve the living anchor
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** After the End-entry completion presentation has played (Q160)
- **Prerequisite:** OR-END-001
- **Objective (specification):** Defeat the Ender Dragon
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Ender Dragon defeated
- **Unlock popup:** `severe` · `OMINOUS` — the living anchor sustaining the wound
- **Completion popup:** `approving` · `CEREMONIAL` — the reign is secure
- **Consequence:** Resolves the living anchor; triggers the one-time Gnarl ending presentation (Q020, Q023)
- **Persistent fact:** overlord_reign:campaign/central_ending (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Handled by the approved early-dragon policy (Q021)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q020, Q160


### Post-Credits

#### `OR-PCR-001` — post_credits/return

- **Role:** Post-ending End expedition
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately after the one-time Gnarl ending presentation (Q023)
- **Prerequisite:** OR-END-002
- **Objective (specification):** Return to the End after the ending
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:visit_dimension_history on minecraft:the_end, evaluated after the ending fact is present
- **Unlock popup:** `neutral` · `CHOICE` — the war is won; curiosity replaces urgency
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the End is changed
- **Consequence:** Advances the expedition
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q047, Q170

#### `OR-PCR-002` — post_credits/ecology

- **Role:** Post-ending End expedition
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-PCR-001
- **Objective (specification):** Explore the altered outer ecology
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill_history — one kill of each of the installed outer-End creature set
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — an altered ecology, catalogued
- **Completion popup:** `amused` · `MOCKING` — Gnarl finds the new End absurd
- **Consequence:** Advances the expedition
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q047, Q170

#### `OR-PCR-003` — post_credits/ruins

- **Role:** Post-ending End expedition
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-PCR-002
- **Objective (specification):** Investigate major ruins, cities and island content
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:visit_structure_history — the installed End city / ruin structure set
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — ruins and cities to pick over
- **Completion popup:** `approving` · `REWARD` — spoils
- **Consequence:** Advances the expedition
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q047, Q170

#### `OR-PCR-004` — post_credits/capstone

- **Role:** Post-ending End expedition
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-PCR-003
- **Objective (specification):** Complete both explicit Outer End major-structure discovery accomplishments and close the expedition
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** AND(outer_end:end/find_catacombs, outer_end:end/find_end_tower)  [Q170: both explicit Outer End structure accomplishments]
- **Unlock popup:** `neutral` · `DIRECTIVE` — one last thing worth seeing
- **Completion popup:** `amused` · `MOCKING` — nothing left to conquer; smug and faintly bored
- **Consequence:** Closes the post-credits expedition
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q047, Q170


### Magic

#### `OR-MAG-IRO-001` — magic/irons/practical_casting

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-012
- **Objective (specification):** Establish practical spellcasting
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** irons_spellbooks:irons_spellbooks/spell_book_equip
- **Unlock popup:** `approving` · `CEREMONIAL` — the discipline opens
- **Completion popup:** `approving` · `REWARD` — power taken and kept
- **Consequence:** Advances the Iron's Spells arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q124

#### `OR-MAG-IRO-002` — magic/irons/shape_capability

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-IRO-001
- **Objective (specification):** Shape / improve spell capability
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** irons_spellbooks:irons_spellbooks/make_arcane_anvil
- **Unlock popup:** `neutral` · `DIRECTIVE` — the next stage
- **Completion popup:** `approving` · `REWARD` — power taken and kept
- **Consequence:** Advances the Iron's Spells arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q125

#### `OR-MAG-IRO-003` — magic/irons/advanced_casting

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-IRO-002
- **Objective (specification):** Affect a hostile target with one offensive spell and use one non-damage utility, defensive or movement spell
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:or of two framework objectives — one offensive spell hit on a hostile mob, one utility/defensive/movement cast (Q236)
- **Unlock popup:** `neutral` · `DIRECTIVE` — the next stage
- **Completion popup:** `approving` · `REWARD` — power taken and kept
- **Consequence:** Advances the Iron's Spells arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q126

#### `OR-MAG-IRO-004` — magic/irons/mastery_or

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-IRO-003
- **Objective (specification):** Final OR mastery using Holy or Blood; records the first inclination
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR(irons_spellbooks:irons_spellbooks/spell_book_dead_king, irons_spellbooks:irons_spellbooks/spell_book_blood)
- **Unlock popup:** `neutral` · `DIRECTIVE` — the next stage
- **Completion popup:** `approving` · `REWARD` — power taken and kept
- **Consequence:** Completes the Iron's Spells arc
- **Persistent fact:** overlord_reign:magic/irons/first_inclination (holy|blood) — Q024 (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q024, Q127

#### `OR-MAG-EID-001` — magic/eidolon/basic_ritual

- **Role:** Eidolon discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-013
- **Objective (specification):** Basic ritual practice
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** eidolon:brazier
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — occult procedure (Q096: not Mortis material)
- **Completion popup:** `approving` · `REWARD` — a path taken
- **Consequence:** Advances the Eidolon arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q128

#### `OR-MAG-EID-002` — magic/eidolon/sacrifice_prep

- **Role:** Eidolon discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-EID-001
- **Objective (specification):** Meaningful sacrifice / material preparation
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** eidolon:sacrifice
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — occult procedure (Q096: not Mortis material)
- **Completion popup:** `approving` · `REWARD` — a path taken
- **Consequence:** Advances the Eidolon arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q129

#### `OR-MAG-EID-003` — magic/eidolon/advanced_occult

- **Role:** Eidolon discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-EID-002
- **Objective (specification):** Advanced ritual / occult capability
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** eidolon:soul_shard
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — occult procedure (Q096: not Mortis material)
- **Completion popup:** `approving` · `REWARD` — a path taken
- **Consequence:** Advances the Eidolon arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q130

#### `OR-MAG-EID-004` — magic/eidolon/mastery_or

- **Role:** Eidolon discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-EID-003
- **Objective (specification):** Final OR mastery between the Sacred and Wicked paths
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR(eidolon:sacred_path, eidolon:wicked_path)
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — occult procedure (Q096: not Mortis material)
- **Completion popup:** `neutral` · `CHOICE` — a path taken
- **Consequence:** Completes the Eidolon arc
- **Persistent fact:** overlord_reign:magic/eidolon/path (sacred|wicked) — Q025 (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q025, Q131

#### `OR-MAG-THE-001` — magic/theurgy/material_principle

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-010
- **Objective (specification):** Understand / extract material principle
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** theurgy:has_liquefaction_cauldron
- **Unlock popup:** `approving` · `CEREMONIAL` — the discipline opens
- **Completion popup:** `approving` · `REWARD` — power taken and kept
- **Consequence:** Advances the Theurgy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q027, Q133

#### `OR-MAG-THE-002` — magic/theurgy/controlled_conversion

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-THE-001
- **Objective (specification):** Perform controlled conversion
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** theurgy:has_basic_rod
- **Unlock popup:** `neutral` · `DIRECTIVE` — the next stage
- **Completion popup:** `approving` · `REWARD` — power taken and kept
- **Consequence:** Advances the Theurgy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q027, Q133

#### `OR-MAG-THE-003` — magic/theurgy/reproduce_material

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-THE-002
- **Objective (specification):** Reproduce a useful material deliberately
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** theurgy:has_t2_rod
- **Unlock popup:** `neutral` · `DIRECTIVE` — the next stage
- **Completion popup:** `approving` · `REWARD` — power taken and kept
- **Consequence:** Advances the Theurgy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q027, Q133

#### `OR-MAG-THE-004` — magic/theurgy/advanced_transmutation

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-THE-003
- **Objective (specification):** Demonstrate advanced repeatable transmutation without requiring every machine
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** theurgy:has_t4_rod
- **Unlock popup:** `neutral` · `DIRECTIVE` — the next stage
- **Completion popup:** `approving` · `REWARD` — power taken and kept
- **Consequence:** Completes the Theurgy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q027, Q133

#### `OR-MAG-BIO-001` — magic/biomancy/organic_matter

- **Role:** Biomancy discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-014
- **Objective (specification):** Acquire / understand organic matter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** biomancy:biomancy/root
- **Unlock popup:** `neutral` · `HISTORICAL` — Gnarl explains where this came from: Solarius, and centuries of boredom (Q-006)
- **Completion popup:** `approving` · `REWARD` — the Master has entered his work
- **Consequence:** Advances the Biomancy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q029, Q135; Gnarl/Biomancy authorship per R-039 and Q-006

#### `OR-MAG-BIO-002` — magic/biomancy/primordial_core

- **Role:** Biomancy discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-BIO-001
- **Objective (specification):** Establish Primordial Core / Cradle capability
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** biomancy:biomancy/cradle
- **Unlock popup:** `neutral` · `DIRECTIVE` — Gnarl walks the Master through his own method
- **Completion popup:** `approving` · `REWARD` — the Cradle lives; he is proprietary about it
- **Consequence:** Advances the Biomancy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q029, Q135; Gnarl/Biomancy authorship per R-039 and Q-006

#### `OR-MAG-BIO-003` — magic/biomancy/living_flesh

- **Role:** Biomancy discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-BIO-002
- **Objective (specification):** Manipulate living flesh
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** biomancy:biomancy/living_flesh
- **Unlock popup:** `approving` · `CEREMONIAL` — the animation of flesh is the whole point of the research
- **Completion popup:** `approving` · `CEREMONIAL` — what he could never do alone is being done
- **Consequence:** Advances the Biomancy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q029, Q135; Gnarl/Biomancy authorship per R-039 and Q-006

#### `OR-MAG-BIO-004` — magic/biomancy/bio_forge

- **Role:** Biomancy discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-BIO-003
- **Objective (specification):** Use the Decomposer / Bio Forge
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** biomancy:biomancy/bio_forge
- **Unlock popup:** `neutral` · `DIRECTIVE` — the industrial stage of his own discipline
- **Completion popup:** `approving` · `REWARD` — the Forge runs
- **Consequence:** Advances the Biomancy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q029, Q135; Gnarl/Biomancy authorship per R-039 and Q-006

#### `OR-MAG-BIO-005` — magic/biomancy/advanced_proof

- **Role:** Biomancy discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-BIO-004
- **Objective (specification):** Complete a serum or advanced bio-engineering proof
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** biomancy:biomancy/bio_lab
- **Unlock popup:** `approving` · `CEREMONIAL` — the thing he never had a Master to attempt
- **Completion popup:** `approving` · `CEREMONIAL` — he finally has an Overlord to direct the work toward suitably unfortunate subjects
- **Consequence:** Completes the Biomancy arc
- **Persistent fact:** overlord_reign:magic/biomancy/discipline_established
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q029, Q135; Gnarl/Biomancy authorship per R-039 and Q-006

#### `OR-MAG-GLU-001` — magic/gluttony/cuisine_foundation

- **Role:** Gluttony discipline stage
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-011
- **Objective (specification):** Prepared cuisine foundation
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** farmers_spell:root
- **Unlock popup:** `neutral` · `DIRECTIVE` — Gristle sets the task
- **Completion popup:** `approving` · `REWARD` — Gristle is pleased with the result
- **Consequence:** Advances the Gluttony / Farmer's Spell arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q026, Q132

#### `OR-MAG-GLU-002` — magic/gluttony/alchemist_pot

- **Role:** Gluttony discipline stage
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-GLU-001
- **Objective (specification):** Alchemist Pot magical bridge
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** farmers_spell:alchemist_pot
- **Unlock popup:** `neutral` · `DIRECTIVE` — Gristle sets the task
- **Completion popup:** `approving` · `REWARD` — Gristle is pleased with the result
- **Consequence:** Advances the Gluttony / Farmer's Spell arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q026, Q132

#### `OR-MAG-GLU-003` — magic/gluttony/culmination

- **Role:** Gluttony discipline stage
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-GLU-002
- **Objective (specification):** Meaningful Gluttony culmination
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** farmers_spell:food_shaman
- **Unlock popup:** `neutral` · `DIRECTIVE` — Gristle sets the task
- **Completion popup:** `approving` · `REWARD` — Gristle is pleased with the result
- **Consequence:** Completes the Gluttony / Farmer's Spell arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q026, Q132

#### `OR-MAG-ELI-001` — magic/elixirum/identify_properties

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-009
- **Objective (specification):** Identify ingredient properties
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** NO ADVANCEMENTS - Elixirum ships none; needs item/stat detector
- **Unlock popup:** `approving` · `CEREMONIAL` — the discipline opens
- **Completion popup:** `approving` · `REWARD` — power taken and kept
- **Consequence:** Advances the Ars Elixirum arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q028, Q134

#### `OR-MAG-ELI-002` — magic/elixirum/formulate

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-ELI-001
- **Objective (specification):** Formulate intentionally in the Glass Cauldron
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** NO ADVANCEMENTS - Elixirum ships none; needs item/stat detector
- **Unlock popup:** `neutral` · `DIRECTIVE` — the next stage
- **Completion popup:** `approving` · `REWARD` — power taken and kept
- **Consequence:** Advances the Ars Elixirum arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q028, Q134

#### `OR-MAG-ELI-003` — magic/elixirum/mastery

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-ELI-002
- **Objective (specification):** Increase Mastery and complete one controlled advanced formulation
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:elixirum_mastery (custom objective type already in the framework)
- **Unlock popup:** `neutral` · `DIRECTIVE` — the next stage
- **Completion popup:** `approving` · `REWARD` — power taken and kept
- **Consequence:** Completes the Ars Elixirum arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q028, Q134


### Farming / Automation

#### `OR-FRM-001` — farming/productive_farm

- **Role:** Estate agriculture stage
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** OPEN - arc activation trigger not decided
- **Prerequisite:** —
- **Objective (specification):** Cultivate at least three staple crop types to maturity and harvest one of each after activation; no location anchor, harvest variables only
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:stat — crop_mined/harvest counters for three staple crop types; no location predicate (Q165)
- **Unlock popup:** `neutral` · `DIRECTIVE` — Gristle wants the estate fed
- **Completion popup:** `approving` · `REWARD` — the estate provides
- **Consequence:** Advances the estate agriculture arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior harvests
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q165

#### `OR-FRM-002` — farming/crop_critters

- **Role:** Estate agriculture stage
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-FRM-001
- **Objective (specification):** Crop Critter capability proof
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** NO ADVANCEMENTS - Crop Critters ships none; needs item/entity detector
- **Unlock popup:** `neutral` · `DIRECTIVE` — Gristle wants the estate fed
- **Completion popup:** `approving` · `REWARD` — the estate provides
- **Consequence:** Advances the estate agriculture arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior harvests
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q031

#### `OR-FRM-003` — farming/hay_golem

- **Role:** Estate agriculture stage
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-FRM-002
- **Objective (specification):** Hay Golem proof
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** NO ADVANCEMENTS - Legendary Farming ships none; needs entity detector
- **Unlock popup:** `neutral` · `DIRECTIVE` — Gristle wants the estate fed
- **Completion popup:** `approving` · `REWARD` — the estate provides
- **Consequence:** Advances the estate agriculture arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior harvests
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q031

#### `OR-FRM-004` — farming/assisted_estate

- **Role:** Estate agriculture stage
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-FRM-003
- **Objective (specification):** Assisted estate operation
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:stat on harvest counters achieved while the Hay Golem entity is present
- **Unlock popup:** `neutral` · `DIRECTIVE` — Gristle wants the estate fed
- **Completion popup:** `approving` · `REWARD` — the estate provides
- **Consequence:** Advances the estate agriculture arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior harvests
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q031


### Adventure

#### `OR-ADV-TWIL-001` — adventures/twilight/ch1

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Enter Twilight Forest, defeat the Naga, defeat the Lich
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** twilightforest:progress_lich
- **Unlock popup:** `neutral` · `HISTORICAL` — an unfamiliar realm; a fae refuge from Imperial persecution, not Cataclysm-made
- **Completion popup:** `approving` · `REWARD` — the first tier falls
- **Consequence:** Advances the Twilight Forest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q112

#### `OR-ADV-TWIL-002` — adventures/twilight/ch2

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-TWIL-001
- **Objective (specification):** Labyrinth / Minoshroom, then the Hydra
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** twilightforest:progress_hydra
- **Unlock popup:** `neutral` · `DIRECTIVE` — deeper tiers
- **Completion popup:** `approving` · `REWARD` — progress
- **Consequence:** Advances the Twilight Forest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q112

#### `OR-ADV-TWIL-003` — adventures/twilight/ch3

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-TWIL-002
- **Objective (specification):** Trophy Pedestal, Knight Phantoms, Ghast Trap, Ur-Ghast
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** twilightforest:progress_ur_ghast
- **Unlock popup:** `neutral` · `TACTICAL` — the forest resists
- **Completion popup:** `approving` · `REWARD` — the towers fall
- **Consequence:** Advances the Twilight Forest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q112

#### `OR-ADV-TWIL-004` — adventures/twilight/ch4

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-TWIL-003
- **Objective (specification):** Alpha Yeti, then the Snow Queen
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** twilightforest:progress_glacier
- **Unlock popup:** `neutral` · `TACTICAL` — ice and its queen
- **Completion popup:** `approving` · `REWARD` — the glacier is taken
- **Consequence:** Advances the Twilight Forest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q112

#### `OR-ADV-TWIL-005` — adventures/twilight/ch5

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-TWIL-004
- **Objective (specification):** Highlands merge gate, Troll Caves, Beanstalk / Giants, Lamp of Cinders
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** twilightforest:progression_end
- **Unlock popup:** `amused` · `MOCKING` — giants and a beanstalk
- **Completion popup:** `amused` · `MOCKING` — the realm is finished and he enjoyed the ending
- **Consequence:** Completes the Twilight Forest arc
- **Persistent fact:** overlord_reign:adventure/twilight_completed (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q112

#### `OR-ADV-RATS-001` — adventures/rats/utility

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Tame one rat, configure it with the Cheese Staff, complete one successful item transfer
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:or [ questlog:item_use on rats:cheese_staff | questlog:stat on rat transfer counter ]
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — rats, as a workforce
- **Completion popup:** `amused` · `MOCKING` — vermin made useful
- **Consequence:** Advances the Rats / Ratlantis arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q113

#### `OR-ADV-RATS-002` — adventures/rats/access

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-RATS-001
- **Objective (specification):** Ratlantis access beat
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** ratlantis:ratlantis
- **Unlock popup:** `amused` · `MOCKING` — an empire beneath the cheese
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the way in is open
- **Consequence:** Advances the Rats / Ratlantis arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q163

#### `OR-ADV-RATS-003` — adventures/rats/investigate

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-RATS-002
- **Objective (specification):** Ratlantis investigation beat
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on ratlantis:gem_of_ratlantis
- **Unlock popup:** `neutral` · `HISTORICAL` — a civilization older than the Overlords and unrelated to them
- **Completion popup:** `neutral` · `HISTORICAL` — the empire is understood
- **Consequence:** Advances the Rats / Ratlantis arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q164

#### `OR-ADV-RATS-004` — adventures/rats/capstone

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-RATS-003
- **Objective (specification):** Defeat the Rat Baron
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** ratlantis:defeat_rat_baron
- **Unlock popup:** `neutral` · `TACTICAL` — the Rat Baron rules here
- **Completion popup:** `amused` · `MOCKING` — a rat monarch deposed
- **Consequence:** Advances the Rats / Ratlantis arc
- **Persistent fact:** overlord_reign:adventure/ratlantis_completed (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q114

#### `OR-ADV-RATS-005` — adventures/rats/closure

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-RATS-004
- **Objective (specification):** Return and close the expedition
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:visit_dimension_history — return from Ratlantis to the Overworld after the capstone fact
- **Unlock popup:** `neutral` · `DIRECTIVE` — the expedition ends
- **Completion popup:** `amused` · `MOCKING` — an absurd conquest, completed
- **Consequence:** Completes the Rats / Ratlantis arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q114

#### `OR-ADV-FATH-001` — adventures/fathoms/aberration

- **Role:** Adventure arc stage
- **Presenter:** Historian
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Catch an Aberration
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** fathoms:nautical/catch_aberration
- **Unlock popup:** `neutral` · `DIRECTIVE` — the Historian withholds judgement
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — a specimen, recorded
- **Consequence:** Advances the Fathoms / Overlord Depths arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q118

#### `OR-ADV-FATH-002` — adventures/fathoms/bottle

- **Role:** Adventure arc stage
- **Presenter:** Historian
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-FATH-001
- **Objective (specification):** Open a Message in a Bottle; triggers the Historian investigation presentation
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** fathoms:nautical/open_message_in_a_bottle
- **Unlock popup:** `neutral` · `HISTORICAL` — a message, and a question
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the investigation begins
- **Consequence:** Advances the Fathoms / Overlord Depths arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q118

#### `OR-ADV-FATH-003` — adventures/fathoms/scrawls

- **Role:** Adventure arc stage
- **Presenter:** Historian
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-FATH-002
- **Objective (specification):** Obtain all Sunken Scrawls and have the Historian decode the evidence
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** fathoms:nautical/obtain_all_sunken_scrawls
- **Unlock popup:** `neutral` · `DIRECTIVE` — evidence to be decoded
- **Completion popup:** `approving` · `REWARD` — a complete set pleases the Historian
- **Consequence:** Advances the Fathoms / Overlord Depths arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q118

#### `OR-ADV-FATH-004` — adventures/fathoms/rocky_waters

- **Role:** Adventure arc stage
- **Presenter:** Historian
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-FATH-003
- **Objective (specification):** Enter Rocky Waters
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** fathoms:nautical/enter_rocky_waters
- **Unlock popup:** `neutral` · `TACTICAL` — the expedition is hazardous
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the waters are surveyed
- **Consequence:** Advances the Fathoms / Overlord Depths arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q118

#### `OR-ADV-FATH-005` — adventures/fathoms/ritual

- **Role:** Adventure arc stage
- **Presenter:** Historian
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-FATH-004
- **Objective (specification):** Perform one native ritual proving the corruption can be manipulated
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** fathoms:nautical/perform_ritual
- **Unlock popup:** `neutral` · `HISTORICAL` — the corruption may be manipulable
- **Completion popup:** `approving` · `REWARD` — the hypothesis is confirmed
- **Consequence:** Completes the Fathoms / Overlord Depths arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q118

#### `OR-ADV-KNIG-001` — adventures/knightquest/chalice

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Craft and place the Great Chalice
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** NO ADVANCEMENTS - KnightQuest ships one root only; needs block/item detector
- **Unlock popup:** `neutral` · `HISTORICAL` — a rival faith and its relic; the Netherman belongs to the Forgotten God, not the Cataclysm
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the chalice stands
- **Consequence:** Advances the Knight Quest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q117

#### `OR-ADV-KNIG-002` — adventures/knightquest/essence

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-KNIG-001
- **Objective (specification):** Obtain and offer four Great Essence to fill the Chalice
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** NO ADVANCEMENTS - needs item detector
- **Unlock popup:** `neutral` · `DIRECTIVE` — the chalice must be filled
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — filled
- **Consequence:** Advances the Knight Quest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q117

#### `OR-ADV-KNIG-003` — adventures/knightquest/radiant

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-KNIG-002
- **Objective (specification):** Create Radiant Essence and offer it to the filled Chalice to summon Netherman
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** NO ADVANCEMENTS - needs item detector
- **Unlock popup:** `neutral` · `TACTICAL` — the summoning invites something
- **Completion popup:** `severe` · `OMINOUS` — the Netherman comes
- **Consequence:** Advances the Knight Quest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q117

#### `OR-ADV-KNIG-004` — adventures/knightquest/netherman

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-KNIG-003
- **Objective (specification):** Defeat Netherman, the Architect of Chaos
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill on the Netherman entity
- **Unlock popup:** `severe` · `OMINOUS` — the Architect of Chaos
- **Completion popup:** `approving` · `REWARD` — a rival power destroyed
- **Consequence:** Completes the Knight Quest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q117

#### `OR-ADV-GRAV-001` — adventures/graveyard/ruins

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Investigate the ruins
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** graveyard:graveyard/medium_graveyard
- **Unlock popup:** `neutral` · `HISTORICAL` — the dead here do not stay buried
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the ruins are surveyed
- **Consequence:** Advances the The Graveyard arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q034

#### `OR-ADV-GRAV-002` — adventures/graveyard/fragments

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-GRAV-001
- **Objective (specification):** Recover all three Ominous Bone Staff fragments
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** AND(graveyard:graveyard/lower_bone_staff, middle_bone_staff, upper_bone_staff)
- **Unlock popup:** `neutral` · `DIRECTIVE` — three pieces of a staff
- **Completion popup:** `approving` · `REWARD` — the staff is whole
- **Consequence:** Advances the The Graveyard arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q034

#### `OR-ADV-GRAV-003` — adventures/graveyard/summon

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-GRAV-002
- **Objective (specification):** Find the Lich Altar, fill the Blood Vial, perform the native nighttime summoning
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** graveyard:graveyard/summon_lich
- **Unlock popup:** `amused` · `MOCKING` — the Master will call it up on purpose
- **Completion popup:** `severe` · `OMINOUS` — the Lich answers
- **Consequence:** Advances the The Graveyard arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q116

#### `OR-ADV-GRAV-004` — adventures/graveyard/lich

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-GRAV-003
- **Objective (specification):** Defeat the Corrupted Champion / Lich
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill on the Lich entity
- **Unlock popup:** `neutral` · `TACTICAL` — the Corrupted Champion
- **Completion popup:** `approving` · `REWARD` — put back down
- **Consequence:** Completes the The Graveyard arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q034, Q116

#### `OR-ADV-BUMB-001` — adventures/bumblezone/enter

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Discover and enter the realm
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** the_bumblezone:root
- **Unlock popup:** `amused` · `MOCKING` — a realm of bees, of all things
- **Completion popup:** `amused` · `MOCKING` — the Master is in the hive
- **Consequence:** Advances the The Bumblezone arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q042

#### `OR-ADV-BUMB-002` — adventures/bumblezone/queen

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-BUMB-001
- **Objective (specification):** Establish meaningful Queen contact
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** the_bumblezone:beehemoth/queen_beehemoth
- **Unlock popup:** `neutral` · `DIRECTIVE` — the Queen must be dealt with
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the Queen receives him
- **Consequence:** Advances the The Bumblezone arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q169

#### `OR-ADV-BUMB-003` — adventures/bumblezone/essence

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-BUMB-002
- **Objective (specification):** Complete Queen's Desires and obtain / consume Essence of the Bees
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** the_bumblezone:essence/bee_essence_infusion
- **Unlock popup:** `amused` · `MOCKING` — the Queen has demands
- **Completion popup:** `amused` · `MOCKING` — the Overlord ran errands for a bee
- **Consequence:** Completes the The Bumblezone arc
- **Persistent fact:** overlord_reign:adventure/bumblezone_completed (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q042

#### `OR-ADV-LOST-001` — adventures/lost_castle/discover

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Discover and enter the castle
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:visit_structure_history on tlc:lost_castle
- **Unlock popup:** `neutral` · `HISTORICAL` — an obscure predecessor civilization left this standing
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the castle is found
- **Consequence:** Advances the The Lost Castle arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q036, Q167

#### `OR-ADV-LOST-002` — adventures/lost_castle/clear

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-LOST-001
- **Objective (specification):** Investigate predecessor evidence and clear the Illager occupation
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill on the Illager occupants within tlc:lost_castle
- **Unlock popup:** `neutral` · `TACTICAL` — Illagers are squatting in it
- **Completion popup:** `approving` · `REWARD` — the squatters are cleared
- **Consequence:** Advances the The Lost Castle arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q036, Q167

#### `OR-ADV-LOST-003` — adventures/lost_castle/treasure

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-LOST-002
- **Objective (specification):** Recover the expedition's meaningful treasure / evidence and close the arc
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on an item from the tlc:chests/throne or tlc:chests/treasure loot table
- **Unlock popup:** `neutral` · `DIRECTIVE` — whatever they were guarding
- **Completion popup:** `approving` · `REWARD` — a predecessor's hoard, taken
- **Consequence:** Completes the The Lost Castle arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q036, Q167

#### `OR-ADV-ORCH-001` — adventures/orchid/shrine

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Discover the shrine / local cult context
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:visit_structure_history on oddities:orchid_shrine
- **Unlock popup:** `neutral` · `TACTICAL` — something in the wood is killing and it is not ours
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the shrine is found
- **Consequence:** Advances the Oddities / Queen of Orchid arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q037, Q168

#### `OR-ADV-ORCH-002` — adventures/orchid/prepare

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-ORCH-001
- **Objective (specification):** Investigate or prepare for the nature-spirit threat
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on oddities:orchid_heart
- **Unlock popup:** `neutral` · `DIRECTIVE` — the shrine wants a heart
- **Completion popup:** `amused` · `MOCKING` — Gnarl approves of the price
- **Consequence:** Advances the Oddities / Queen of Orchid arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q037, Q168

#### `OR-ADV-ORCH-003` — adventures/orchid/queen

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-ORCH-002
- **Objective (specification):** Defeat / resolve the Queen of Orchid encounter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill on oddities:queen_of_orchid
- **Unlock popup:** `neutral` · `TACTICAL` — a local nature-spirit power, independent of every major history
- **Completion popup:** `approving` · `REWARD` — the wood is quiet
- **Consequence:** Completes the Oddities / Queen of Orchid arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q037, Q168

#### `OR-ADV-PET_-001` — adventures/pet_cemetery/collar

- **Role:** Adventure arc stage
- **Presenter:** Mortis
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Pet death and collar recovery
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on pet_cemetery pet collar, gated on the pet-death fact
- **Unlock popup:** `neutral` · `ADMINISTRATIVE` — Mortis attends a death as Spawning Pit business, black velvet and scythe
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the collar is recovered
- **Consequence:** Advances the Pet Cemetery arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q038

#### `OR-ADV-PET_-002` — adventures/pet_cemetery/respawn

- **Role:** Adventure arc stage
- **Presenter:** Mortis
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-PET_-001
- **Objective (specification):** Charged Respawn Anchor resurrection into zombie-pet state
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** pet_cemetery:nether/respawn_pet
- **Unlock popup:** `neutral` · `DIRECTIVE` — Mortis explains what the Pit can do
- **Completion popup:** `amused` · `MOCKING` — it came back wrong; Mortis is morbidly unbothered
- **Consequence:** Advances the Pet Cemetery arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q038

#### `OR-ADV-PET_-003` — adventures/pet_cemetery/cure

- **Role:** Adventure arc stage
- **Presenter:** Mortis
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-PET_-002
- **Objective (specification):** Cure the same pet back to life
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** pet_cemetery:nether/cured_zombie_pet
- **Unlock popup:** `neutral` · `DIRECTIVE` — it can be made right
- **Completion popup:** `approving` · `REWARD` — a death properly reversed — his actual office
- **Consequence:** Completes the Pet Cemetery arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q038

#### `OR-ADV-NIGH-001` — adventures/nightwalker/transform

- **Role:** Take first blood; Lestat arrives and judges how it was taken
- **Presenter:** Gnarl (unlock) / Lestat (completion)
- **Register vocabulary:** reference/40 Lestat range
- **Activation:** On becoming a vampire through the NightWalker / Nycto system — `nycto:become_vampire` is the arc activation trigger, not a quest objective (Q045, reference/37)
- **Prerequisite:** —
- **Objective (specification):** Take first blood. Branch-recording OR: (A) feed from a living victim, or (B) drink a Blood Bottle. Both complete the quest; the arm taken is recorded.
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:or — arm A: first successful feed via VampireFeedingEvents; arm B: consume_item on nycto:blood_bottle. The completing arm is written as a named fact.
- **Unlock popup:** `severe` · `GRAVE` — Gnarl alone. The Master has done something that may kill him or unfit him to rule (37 §3, Q-051 Overlord clause). Lestat has not arrived yet.
- **Completion popup:** `PER-BRANCH` · `PER-BRANCH` — Lestat's first appearance, reacting to the arm taken. Arm A (living victim) — register CEREMONIAL, visual `approving`: intimate observation; the Overlord has stopped denying what he is, which is the thing Lestat came for (40 §4). Arm B (Blood Bottle) — register REACTIVE, visual `displeased`: philosophical provocation; unnecessary restraint revealing fear of identity (40 §20). He diagnoses repression before weakness and names the gap between what the Overlord is and what he will do under pressure (40 §4).
- **Consequence:** Lestat, who sensed the new presence, arrives at the Dark Tower (44 §15) and reacts to the arm taken. He carries the remainder of the arc. Branch facts: `overlord_reign:nightwalker/first_blood_victim` or `overlord_reign:nightwalker/first_blood_bottled` (TO BE REGISTERED in NARRATIVE_FACTS.md).
- **Persistent fact:** overlord_reign:nightwalker/first_blood_victim OR overlord_reign:nightwalker/first_blood_bottled — branch-specific, one is written
- **Sequence-break handling:** Recognize a feed that occurred before the arc activated
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q045; Overlord decisions this session (supersede Q120); branch reactions per 40 §4 and §20; Lestat register range per 40 (not the Gnarl ten)

#### `OR-ADV-NIGH-002` — adventures/nightwalker/survive

- **Role:** Prove the Overlord can survive what comes for vampires
- **Presenter:** Lestat
- **Register vocabulary:** reference/40 Lestat range
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-NIGH-001
- **Objective (specification):** Take down a Vampire Hunter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill on nycto:hunter
- **Unlock popup:** `neutral` · `DIRECTIVE` — Lestat warns that being what he is attracts attention
- **Completion popup:** `approving` · `REWARD` — mastery, not mere completion (40 §20) — a hunter is a real test
- **Consequence:** The Overlord proves he can survive being hunted; Lestat is impressed
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize a prior hunter kill made while a vampire
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q120; Overlord decisions this session (supersede Q120)

#### `OR-ADV-NIGH-003` — adventures/nightwalker/altar

- **Role:** Adventure arc stage
- **Presenter:** Lestat
- **Register vocabulary:** reference/40 Lestat range
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-NIGH-002
- **Objective (specification):** First successful Vampire Altar purchase adding one previously unowned power and recording its weakness
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** overlord_reign:nightwalker_power_count
- **Unlock popup:** `neutral` · `CHOICE` — power has a price and Lestat approves of paying it
- **Completion popup:** `amused` · `MOCKING` — Lestat is pleased by the bargain struck
- **Consequence:** Completes the NightWalker arc
- **Persistent fact:** overlord_reign:nightwalker/altar_commitment (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q162

#### `OR-ADV-QUAV-001` — adventures/quaver/ensemble

- **Role:** Adventure arc stage
- **Presenter:** Quaver
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Acquire all eight approved instruments: Bagpipe, Flute, Lute, Piano, Trumpet, Tiny Drum, Vielle, Handpan
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** NO ADVANCEMENTS - Immersive Melodies ships none; needs an 8-item AND detector
- **Unlock popup:** `neutral` · `DIRECTIVE` — Quaver wants his instruments
- **Completion popup:** `amused` · `MOCKING` — the Tower has a band and he will not stop composing about it
- **Consequence:** Completes the Quaver's Tower Band arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q111, Q166

#### `OR-CAT-001` — adventures/cataclysm/harbinger

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Discover the bound structure (find_ancient_factory) and defeat The Harbinger
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_harbinger
- **Unlock popup:** `severe` · `OMINOUS` — the Cataclysm is an Elf blowing up an Overlord Tower Heart; Gnarl carries that grievance (R-032, 01)
- **Completion popup:** `approving` · `REWARD` — one of eight
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

#### `OR-CAT-002` — adventures/cataclysm/remnant

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-001
- **Objective (specification):** Discover the bound structure (find_cursed_pyramid) and defeat Ancient Remnant
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_remnant
- **Unlock popup:** `severe` · `OMINOUS` — a pyramid that should have stayed shut
- **Completion popup:** `approving` · `REWARD` — put back down
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

#### `OR-CAT-003` — adventures/cataclysm/maledictus

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-002
- **Objective (specification):** Discover the bound structure (find_frosted_prison) and defeat Maledictus
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_maledictus
- **Unlock popup:** `neutral` · `TACTICAL` — a prison built to hold something
- **Completion popup:** `approving` · `REWARD` — the curse is broken
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

#### `OR-CAT-004` — adventures/cataclysm/monstrosity

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-003
- **Objective (specification):** Discover the bound structure (find_soul_black_smith) and defeat Netherite Monstrosity
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_monstrosity
- **Unlock popup:** `severe` · `OMINOUS` — a forge that makes monstrous things
- **Completion popup:** `approving` · `REWARD` — the warmachine is stopped
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

#### `OR-CAT-005` — adventures/cataclysm/leviathan

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-004
- **Objective (specification):** Discover the bound structure (find_sunken_city) and defeat The Leviathan
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_leviathan
- **Unlock popup:** `severe` · `OMINOUS` — a predator in the sunken city
- **Completion popup:** `approving` · `REWARD` — the sea is safer
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

#### `OR-CAT-006` — adventures/cataclysm/ignis

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-005
- **Objective (specification):** Discover the bound structure (find_burning_arena) and defeat Ignis; the Ignited Revenant gates the encounter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_ignis
- **Unlock popup:** `severe` · `OMINOUS` — an arena that still burns
- **Completion popup:** `approving` · `REWARD` — the fire is extinguished
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

#### `OR-CAT-007` — adventures/cataclysm/scylla

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-006
- **Objective (specification):** Discover the bound structure (find_acropolis) and defeat Scylla; the Clawdian gates the encounter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_scylla
- **Unlock popup:** `neutral` · `TACTICAL` — an acropolis where it has no business being
- **Completion popup:** `approving` · `REWARD` — the storm passes
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

#### `OR-CAT-008` — adventures/cataclysm/ender_guardian

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-007
- **Objective (specification):** Discover the bound structure (find_ruined_citadel) and defeat Ender Guardian; the Ender Golem gates the encounter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_ender_guardian
- **Unlock popup:** `severe` · `OMINOUS` — a guardian behind the citadel
- **Completion popup:** `approving` · `REWARD` — the last of the eight
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

#### `OR-CAT-009` — adventures/cataclysm/capstone

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-008
- **Objective (specification):** Defeat every major Cataclysm boss
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_all_bosses
- **Unlock popup:** `neutral` · `DIRECTIVE` — one kill remains
- **Completion popup:** `amused` · `MOCKING` — every major power the Cataclysm left behind is dead; the Elf is avenged by accident
- **Consequence:** Completes the Cataclysm Adventure; eligible major-Adventure ending fact (Q123)
- **Persistent fact:** overlord_reign:adventure/cataclysm_capstone_completed (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q123


### Ice & Fire

#### `OR-ICF-001` — ice_and_fire/dragon_mastery/bestiary

- **Role:** Dragon Mastery stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** OPEN - arc activation trigger not decided
- **Prerequisite:** —
- **Objective (specification):** Bestiary / research
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** iceandfire:iceandfire/bestiary
- **Unlock popup:** `neutral` · `HISTORICAL` — dragons studied before they are taken
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the research is done
- **Consequence:** Advances Dragon Mastery
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q048

#### `OR-ICF-002` — ice_and_fire/dragon_mastery/harvest

- **Role:** Dragon Mastery stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ICF-001
- **Objective (specification):** Kill a wild adult dragon and harvest materials
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** iceandfire:iceandfire/kill_if_dragon
- **Unlock popup:** `neutral` · `TACTICAL` — a wild adult must be killed
- **Completion popup:** `approving` · `REWARD` — materials taken
- **Consequence:** Advances Dragon Mastery
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize a prior dragon kill
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q048

#### `OR-ICF-003` — ice_and_fire/dragon_mastery/egg

- **Role:** Dragon Mastery stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ICF-002
- **Objective (specification):** Obtain an egg from a sufficiently ancient female
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** iceandfire:iceandfire/dragon_egg
- **Unlock popup:** `neutral` · `DIRECTIVE` — an egg, stolen from something ancient
- **Completion popup:** `amused` · `MOCKING` — theft from a dragon delights him
- **Consequence:** Advances Dragon Mastery
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q048

#### `OR-ICF-004` — ice_and_fire/dragon_mastery/bond

- **Role:** Dragon Mastery capstone
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ICF-003
- **Objective (specification):** Hatch, raise and bond with a dragon, ending with taking flight on it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:stat on time ridden while mounted on a tamed iceandfire dragon
- **Unlock popup:** `neutral` · `DIRECTIVE` — a dragon of the Master's own
- **Completion popup:** `approving` · `CEREMONIAL` — the Overlord flies
- **Consequence:** Completes Dragon Mastery; eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:ice_and_fire/dragon_bonded (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q048

#### `OR-DDN-001` — tower/dragon_den/install

- **Role:** Dragon Den installation
- **Presenter:** Giblet the Sixth
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** OPEN - independent later Tower development (§7.1)
- **Prerequisite:** —
- **Objective (specification):** Install the Dragon Den / Dragon Forge infrastructure
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** iceandfire:iceandfire/dragon_forge_core
- **Unlock popup:** `neutral` · `DIRECTIVE` — Giblet is getting a dragon forge
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — installed, untested
- **Consequence:** Dragon Den operational
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q049, §7.1

#### `OR-DDN-002` — tower/dragon_den/operate

- **Role:** Dragon Den activation
- **Presenter:** Giblet the Sixth
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-DDN-001
- **Objective (specification):** Prove the Forge is operational using actual dragon breath
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on iceandfire:dragonsteel_ingot (only producible by an operational Dragon Forge)
- **Unlock popup:** `neutral` · `DIRECTIVE` — the forge must be proven
- **Completion popup:** `approving` · `REWARD` — Giblet approves of a forge that works
- **Consequence:** Dragon Den operational
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q049, §7.1


### Civilization

#### `OR-CIV-001A` — civilizations/villagers/anchor

- **Role:** Lock the villagers anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Spree / Villagers anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `neutral` · `DIRECTIVE` — a human settlement, suspicious rather than hostile (Q-023)
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-001B` — civilizations/villagers/state

- **Role:** Resolve the villagers anchor. Legal states: NEUTRAL / SUBJUGATED / HOSTILE (Q-026: DESTROYED is a local anchor outcome, not a civilization state)
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-001A
- **Objective (specification):** Resolve the Spree / Villagers civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `neutral` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/approving · HOSTILE TACTICAL/neutral · NEUTRAL REACTIVE/displeased
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/villagers/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / HOSTILE (Q-026: DESTROYED is a local anchor outcome, not a civilization state)
- **Source:** Q053-Q059

#### `OR-CIV-002A` — civilizations/illagers/anchor

- **Role:** Lock the illagers anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Illagers anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `neutral` · `TACTICAL` — initially hostile; authority is established by force (17)
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-002B` — civilizations/illagers/state

- **Role:** Resolve the illagers anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED, reached through an intermediate COWED phase (17)
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-002A
- **Objective (specification):** Resolve the Illagers civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `neutral` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/approving · DESTROYED MOCKING/amused · NEUTRAL REACTIVE/displeased
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/illagers/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED, reached through an intermediate COWED phase (17)
- **Source:** Q198-Q200

#### `OR-CIV-003A` — civilizations/dwarves/anchor

- **Role:** Lock the dwarves anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Dwarves anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `neutral` · `HISTORICAL` — a successor hold of the Golden Hills, reduced by Imperial anti-magic persecution (18)
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-003B` — civilizations/dwarves/state

- **Role:** Resolve the dwarves anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-003A
- **Objective (specification):** Resolve the Dwarves civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `neutral` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/approving · DESTROYED MOCKING/amused · NEUTRAL REACTIVE/displeased
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/dwarves/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q171, Q174-

#### `OR-CIV-004A` — civilizations/gnumus/anchor

- **Role:** Lock the gnumus anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Gnumus anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `neutral` · `HISTORICAL` — Halflings transformed by Gluttony magic who no longer know it (19)
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-004B` — civilizations/gnumus/state

- **Role:** Resolve the gnumus anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-004A
- **Objective (specification):** Resolve the Gnumus civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `neutral` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/approving · DESTROYED MOCKING/amused · NEUTRAL REACTIVE/displeased
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/gnumus/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q174-Q176

#### `OR-CIV-005A` — civilizations/goblins/anchor

- **Role:** Lock the goblins anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Goblins anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `amused` · `MOCKING` — Goblins and Minions call each other cousins; the claim is cultural, not established (20)
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-005B` — civilizations/goblins/state

- **Role:** Resolve the goblins anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-005A
- **Objective (specification):** Resolve the Goblins civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `neutral` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/approving · DESTROYED MOCKING/amused · NEUTRAL REACTIVE/displeased
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/goblins/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q177-Q180

#### `OR-CIV-006A` — civilizations/kobolds/anchor

- **Role:** Lock the kobolds anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Kobolds anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `amused` · `MOCKING` — a Den of generally dim underground engineers (21)
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-006B` — civilizations/kobolds/state

- **Role:** Resolve the kobolds anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-006A
- **Objective (specification):** Resolve the Kobolds civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `neutral` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/approving · DESTROYED MOCKING/amused · NEUTRAL REACTIVE/displeased
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/kobolds/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q181-Q184

#### `OR-CIV-007A` — civilizations/ribbits/anchor

- **Role:** Lock the ribbits anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Ribbits anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `amused` · `MOCKING` — a cozy musical village that has never threatened anyone (22)
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-007B` — civilizations/ribbits/state

- **Role:** Resolve the ribbits anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-007A
- **Objective (specification):** Resolve the Ribbits civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `neutral` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED MOCKING/amused · DESTROYED MOCKING/amused — the contrast is the point (22) · NEUTRAL REACTIVE/displeased
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/ribbits/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q185-Q188

#### `OR-CIV-008A` — civilizations/sea_dwellers/anchor

- **Role:** Lock the sea_dwellers anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Sea Dwellers anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `neutral` · `DIRECTIVE` — an independent trading village under the water (23)
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-008B` — civilizations/sea_dwellers/state

- **Role:** Resolve the sea_dwellers anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-008A
- **Objective (specification):** Resolve the Sea Dwellers civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `neutral` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/approving · DESTROYED MOCKING/amused · NEUTRAL REACTIVE/displeased
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/sea_dwellers/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q189-Q192

#### `OR-CIV-009A` — civilizations/piglins/anchor

- **Role:** Lock the piglins anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Piglins anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `displeased` · `REACTIVE` — Piglins descend from a Minion and a pig; it is true and Gnarl would rather not discuss it (24)
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-009B` — civilizations/piglins/state

- **Role:** Resolve the piglins anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-009A
- **Objective (specification):** Resolve the Piglins civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `neutral` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/approving — supremacy restored in his own realm · DESTROYED MOCKING/amused · NEUTRAL REACTIVE/displeased
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/piglins/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q193-Q195

#### `OR-CIV-010A` — civilizations/umvuthana/anchor

- **Role:** Lock the umvuthana anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Umvuthana anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `neutral` · `TACTICAL` — a living creator-god and his Grove, risen in the space the Empire made by killing Elves (25)
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-010B` — civilizations/umvuthana/state

- **Role:** Resolve the umvuthana anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-010A
- **Objective (specification):** Resolve the Umvuthana civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `neutral` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED MOCKING/amused — a god humiliated before his worshippers (25) · DESTROYED REWARD/approving · NEUTRAL REACTIVE/displeased
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/umvuthana/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q196-Q197

#### `OR-CIV-011A` — civilizations/myrmex/anchor

- **Role:** Lock the myrmex anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Myrmex anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `neutral` · `DIRECTIVE` — a hive polity with a Queen and a measurable opinion of the Overlord
- **Completion popup:** `neutral` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the provider quests (Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q052, §2, §12

#### `OR-CIV-011B` — civilizations/myrmex/state

- **Role:** Resolve the myrmex anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-011A
- **Objective (specification):** Resolve the Myrmex civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `neutral` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/approving · DESTROYED MOCKING/amused · NEUTRAL REACTIVE/displeased
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §12); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/myrmex/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q090-Q093, Q139-Q141, §12


### Rivalry

#### `OR-RIV-001` — rivalry/dwarf_kobold/investigate

- **Role:** Dwarf/Kobold rivalry resolution
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** After both the Dwarven and Kobold anchors exist
- **Prerequisite:** OR-CIV-003A + OR-CIV-006A
- **Objective (specification):** Visit each bound polity and complete one source-compatible material/service interaction with each side, then present the paired evidence
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Both interactions complete
- **Unlock popup:** `amused` · `MOCKING` — two polities that can be played against each other
- **Completion popup:** `amused` · `MOCKING` — the rivalry is exploitable
- **Consequence:** Writes RIVALRY_INVESTIGATED and exposes the four resolution routes
- **Persistent fact:** RIVALRY_INVESTIGATED
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q172

#### `OR-RIV-002` — rivalry/dwarf_kobold/favor_dwarves

- **Role:** Dwarf/Kobold rivalry resolution
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Available after RIVALRY_INVESTIGATED; mutually exclusive with the other three (Q173)
- **Prerequisite:** OR-RIV-001
- **Objective (specification):** Complete a Dwarven forge/material commission that disadvantages the Kobold claim
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** NO ADVANCEMENTS - Dwarven Forge ships none; needs item/commission detector
- **Unlock popup:** `neutral` · `CHOICE` — a side to back
- **Completion popup:** `amused` · `MOCKING` — one claim ruined for the other
- **Consequence:** Writes a named rivalry fact that later civilization routes may inspect; does not set NEUTRAL/SUBJUGATED/DESTROYED directly (Q173)
- **Persistent fact:** overlord_reign:rivalry/favoured_dwarves (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q173

#### `OR-RIV-003` — rivalry/dwarf_kobold/favor_kobolds

- **Role:** Dwarf/Kobold rivalry resolution
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Available after RIVALRY_INVESTIGATED; mutually exclusive with the other three (Q173)
- **Prerequisite:** OR-RIV-001
- **Objective (specification):** Complete a Kobold engineering/resource-security commission that disadvantages the Dwarven claim
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** NO ADVANCEMENTS - Kobolds ships none; needs item/commission detector
- **Unlock popup:** `neutral` · `CHOICE` — a side to back
- **Completion popup:** `amused` · `MOCKING` — one claim ruined for the other
- **Consequence:** Writes a named rivalry fact that later civilization routes may inspect; does not set NEUTRAL/SUBJUGATED/DESTROYED directly (Q173)
- **Persistent fact:** overlord_reign:rivalry/favoured_kobolds (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q173

#### `OR-RIV-004` — rivalry/dwarf_kobold/truce

- **Role:** Dwarf/Kobold rivalry resolution
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Available after RIVALRY_INVESTIGATED; mutually exclusive with the other three (Q173)
- **Prerequisite:** OR-RIV-001
- **Objective (specification):** Impose a forced working arrangement / truce
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:or of the two commission item turn-ins, one to each polity — forced arrangement proved by serving both
- **Unlock popup:** `neutral` · `CHOICE` — the dull option
- **Completion popup:** `displeased` · `REACTIVE` — both sides intact; Gnarl evaluates the Master and is unimpressed
- **Consequence:** Writes a named rivalry fact that later civilization routes may inspect; does not set NEUTRAL/SUBJUGATED/DESTROYED directly (Q173)
- **Persistent fact:** overlord_reign:rivalry/forced_truce (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q173

#### `OR-RIV-005` — rivalry/dwarf_kobold/exploit

- **Role:** Dwarf/Kobold rivalry resolution
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + GRAVE (11)
- **Activation:** Available after RIVALRY_INVESTIGATED; mutually exclusive with the other three (Q173)
- **Prerequisite:** OR-RIV-001
- **Objective (specification):** Exploit or escalate the dispute for Overlord advantage
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain — take the disputed material from both sides without resolving the claim
- **Unlock popup:** `neutral` · `CHOICE` — the dispute is worth more unresolved
- **Completion popup:** `approving` · `REWARD` — turned entirely to the Master's advantage
- **Consequence:** Writes a named rivalry fact that later civilization routes may inspect; does not set NEUTRAL/SUBJUGATED/DESTROYED directly (Q173)
- **Persistent fact:** overlord_reign:rivalry/exploited (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q173

---

## 9. Narrative fact registry

**31 quests write a persistent fact.** A fact is used only where later content materially benefits from a reusable campaign truth. Ordinary sequential quests write nothing. Facts are world-scoped `ResourceLocation` keys stored by `OverlordNarrativeState`; absent means the statement has not been recorded, present means it has become true. A fact is not a civilization disposition and never duplicates state another mod already owns.

Keys marked `TO BE REGISTERED` must be added to `NARRATIVE_FACTS.md` and will be validated by `tools/validate_narrative_fact_documentation.py`.

| Quest | Fact |
| --- | --- |
| `OR-OPN-001` opening/a_new_master | overlord_reign:minions/brown_recovered |
| `OR-MIN-001` minions/restore_red | overlord_reign:minions/red_recovered |
| `OR-MIN-002` minions/restore_green | overlord_reign:minions/green_recovered |
| `OR-MIN-003` minions/restore_blue | overlord_reign:minions/blue_recovered |
| `OR-END-002` end/ender_dragon | overlord_reign:campaign/central_ending (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-MAG-IRO-004` magic/irons/mastery_or | overlord_reign:magic/irons/first_inclination (holy|blood) — Q024 (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-MAG-EID-004` magic/eidolon/mastery_or | overlord_reign:magic/eidolon/path (sacred|wicked) — Q025 (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-MAG-BIO-005` magic/biomancy/advanced_proof | overlord_reign:magic/biomancy/discipline_established |
| `OR-ADV-TWIL-005` adventures/twilight/ch5 | overlord_reign:adventure/twilight_completed (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-ADV-RATS-004` adventures/rats/capstone | overlord_reign:adventure/ratlantis_completed (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-ADV-BUMB-003` adventures/bumblezone/essence | overlord_reign:adventure/bumblezone_completed (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-ADV-NIGH-001` adventures/nightwalker/transform | overlord_reign:nightwalker/first_blood_victim OR overlord_reign:nightwalker/first_blood_bottled — branch-specific, one is written |
| `OR-ADV-NIGH-003` adventures/nightwalker/altar | overlord_reign:nightwalker/altar_commitment (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-CAT-009` adventures/cataclysm/capstone | overlord_reign:adventure/cataclysm_capstone_completed (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-ICF-004` ice_and_fire/dragon_mastery/bond | overlord_reign:ice_and_fire/dragon_bonded (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-CIV-001B` civilizations/villagers/state | overlord_reign:civ/villagers/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-CIV-002B` civilizations/illagers/state | overlord_reign:civ/illagers/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-CIV-003B` civilizations/dwarves/state | overlord_reign:civ/dwarves/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-CIV-004B` civilizations/gnumus/state | overlord_reign:civ/gnumus/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-CIV-005B` civilizations/goblins/state | overlord_reign:civ/goblins/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-CIV-006B` civilizations/kobolds/state | overlord_reign:civ/kobolds/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-CIV-007B` civilizations/ribbits/state | overlord_reign:civ/ribbits/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-CIV-008B` civilizations/sea_dwellers/state | overlord_reign:civ/sea_dwellers/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-CIV-009B` civilizations/piglins/state | overlord_reign:civ/piglins/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-CIV-010B` civilizations/umvuthana/state | overlord_reign:civ/umvuthana/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-CIV-011B` civilizations/myrmex/state | overlord_reign:civ/myrmex/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-RIV-001` rivalry/dwarf_kobold/investigate | RIVALRY_INVESTIGATED |
| `OR-RIV-002` rivalry/dwarf_kobold/favor_dwarves | overlord_reign:rivalry/favoured_dwarves (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-RIV-003` rivalry/dwarf_kobold/favor_kobolds | overlord_reign:rivalry/favoured_kobolds (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-RIV-004` rivalry/dwarf_kobold/truce | overlord_reign:rivalry/forced_truce (TO BE REGISTERED in NARRATIVE_FACTS.md) |
| `OR-RIV-005` rivalry/dwarf_kobold/exploit | overlord_reign:rivalry/exploited (TO BE REGISTERED in NARRATIVE_FACTS.md) |

Provider quests write a further 42 facts, one per quest, listed in §7.5.

---

## 10. Deliberately out of scope

These are **not** omissions. A builder must not fill them by inference.

### 10.1 Player-facing text

Quest names, titles, descriptions and every `Order (presenter voice)` line are authored in the campaign-writing pass, from `Overlord_Lore_and_Canon`, the presenter register vocabularies in §5.3 and the reactions recorded per quest. Names are decided by their presenter — a Gnarl quest is named as Gnarl would name it (Q242, Overlord decision).

### 10.2 Ramblings

Rambling allocation is **parked until the campaign closes**, by Overlord decision. The reason is structural: under the one-consumer rule (§2.1 of the Rambling authority as superseded by Q217) every advancement a quest claims is removed from the Rambling pool, so the catalog cannot be frozen until every detector is pinned.

The audit surface is built and preserved: **767 advancements across 39 advancement-bearing sources**, including 57 hidden advancements which are in scope because hidden usually means secret, not technical. When the campaign closes, subtract the claimed detectors from that surface and what remains is the Rambling catalog.

Order of operations:

```text
close the quest inventory
pin every objective to its exact signal
subtract the claimed set from the 767-row surface
freeze what remains as the Rambling catalog
```

### 10.3 Presenter art

Five visual assets per presenter, eight presenters, forty assets. `NOT_GENERATED` is an acceptable state during authoring. Quaver's presentation must account for the Jester merge (Q244b).

### 10.4 Lifecycle text beyond two popups

The runtime supports clarification, branch framing, reminders, warnings and post-quest commentary. V5 authors unlock and completion only. The remaining slots belong to the writing pass.

---

## 11. Implementation queue

1. Register the 24 pending narrative fact keys in `NARRATIVE_FACTS.md`.
2. Resolve the ten quests marked `framework objective type needed` onto existing Questlog types. No new objective type may be authored where an existing one fits (Q235).
3. Build the one code bridge: `VampireFeedingEvents` for the living-victim arm of `OR-ADV-NIGH-001`. Without it the branch collapses and only the Blood Bottle arm can fire.
4. Author the `Order (presenter voice)` line and name for all 147 quests and 42 provider quests.
5. Generate the forty presenter assets.
6. Close the Rambling catalog per §10.2.
7. Re-supply `DramaticDoors-QuiFabrge-1.20.1-3.3.3.jar`, which is not a readable zip in the current instance archive and is therefore unaudited. Q215 rejected re-scanning it; this is recorded so the omission stays deliberate.

---

## 12. Closing rule

Where this document specifies, the builder implements. Where it is silent, the builder **asks**.

An authored choice that cannot be traced to this document or to an explicit Overlord decision is not authority. Technical convenience does not promote design into authority. A mod exposing an advancement, the framework supporting an objective type, an older production quest already existing, or a choice appearing obvious are none of them approval.
