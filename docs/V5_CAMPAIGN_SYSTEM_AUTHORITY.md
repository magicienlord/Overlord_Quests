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

> **Do not confuse this with the quest JSON.** `reward: NONE` means no player-facing prize. It does **not** mean an empty `rewards` array: Questlog implements narrative-fact writing as the reward type `questlog:set_fact`, so a quest that writes a fact carries one while remaining `reward: NONE` in V5 terms. Shipping an empty array would silently break all 90 narrative facts. See `V5_IMPLEMENTATION_SPECS.md` §1.

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

The campaign is **147 visible Questlog quests** across twelve categories, plus **43 provider quests** handled entirely by the Villager Retaliation layer.

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

Each visible quest produces **two presenter popups**: one on unlock, one on completion. 147 quests, **294 popups**. Plus **537 Rambling popups**, one each. **831 in total.**

Provider quests produce no presenter popups. They are bound NPC dialogue with their own `offer / in_progress / ready_to_turn_in / completed` states on the provider entity (Q243).

The runtime supports a fuller lifecycle — `objective_clarification`, `branch_framing`, `first_reminder`, `repeat_reminders`, `objective_updates`, `warning`, `success`, `failure`, `post_quest`, with per-slot timing. V5 authors **unlock and completion**. The rest belongs to the writing pass.

### 5.2 Visual states — five, universal

Every presenter requires exactly five visual assets. The set is closed and shared by all eight.

The five are **not an approval scale**. They are a grid of two axes that every character can wear:
**valence** — is the reaction positive or negative — and **control** — is the presenter holding
himself, or is the mask off.

| State | Valence | Control | What it is |
| --- | --- | --- | --- |
| **COMPOSED** | neutral | — | Baseline. Explanatory, observational, instructional, professionally unmarked. |
| **PLEASED** | positive | held | Approval, satisfaction, pride, recognition, quiet respect. |
| **RELISHING** | positive | slipped | Delight, mockery, smugness, glee, cruelty enjoyed openly. |
| **DARKENED** | negative | held | Disapproval, contempt, unease, embarrassment, warning, threat. |
| **STRICKEN** | negative | slipped | The mask gone. Grief, dread, fury, undisguised sincerity. |

This grid replaces an earlier five-state set — `neutral / approving / amused / displeased / severe` —
which was an approval scale built around Gnarl. It failed for the other seven presenters, because
Mortis does not evaluate, Gristle never disapproves, and Quaver mocks rather than disapproving. Two of
its five states were nearly unused across 831 popups, and the state the campaign most needed did not
exist at all.

Because the grid describes **how much of the presenter is showing** rather than whether he approves,
every character can wear all five honestly:

- Mortis `COMPOSED` is liturgical calm; Mortis `STRICKEN` is a death that actually reached him.
- Quaver `RELISHING` is his working register; Quaver `STRICKEN` is the song he cannot finish.
- Lestat `DARKENED` is diagnosing your denial; Lestat `STRICKEN` is wounded accusation, which
  `reference/40` names explicitly.
- Gristle `DARKENED` is not moral disapproval but disgust at waste.
- The Historian `DARKENED` is professional unease; `STRICKEN` is the deep looking back.

**`STRICKEN` is deliberately rare.** It appears on **three popups in the entire campaign**: Gnarl
learning that the Third Overlord is loose, Gnarl standing over his body, and Gnarl watching the Master
become something that may unfit him to rule. A face the player sees three times is one he remembers.

`DARKENED` absorbs both warning and disapproval, because they are the same thing worn at different
targets: control held, valence negative. Gnarl warning about a Cataclysm lair and Gnarl unimpressed by
a forced truce are the same face.

### 5.2.1 Asset contract

Five assets per presenter, eight presenters, **forty assets**. `NOT_GENERATED` is an acceptable state
during authoring. Quaver's set must account for the Jester merge (Q244b).


### 5.3 Registers — per presenter

A register is the dominant **speech function** of a line. Registers are per presenter (Q252); visual states are universal. Four vocabularies are recovered from source rather than authored.

**Gnarl — eleven.** `GNARL_WRITING_RULES.md` §10 plus `STRICKEN`.

```text
CEREMONIAL · DIRECTIVE · TACTICAL · ADMINISTRATIVE · HISTORICAL · MOCKING
REACTIVE · REWARD · CHOICE · OMINOUS · STRICKEN
```

`STRICKEN` as a register carries the same meaning as the visual state: Gnarl's seriousness being **personal rather than tactical** — danger to something he is attached to, not danger the Overlord must handle. Three popups use it. `OMINOUS` remains the tactical warning and resolves to `DARKENED`.

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

**Gristle, Giblet the Sixth, Grubbison Jr.** Built from the ordinary Minion register (`reference/10` §5) raised exactly one notch. **Smarter minions, not smart minions**, each obsessed with his own domain. Short sentences remain; the difference from a Brown is competence in one craft, not articulacy.

```text
ENTHUSIASM · DOMAIN-EXPERTISE · IMPATIENCE · APPETITE
```

**Historian.** Based on the Dredge protagonist, who is also the Collector — in REIGN he is openly both, with no concealed identity. He is the investigator and the appetite: he catalogues the deep because he wants what is in it, and the Overlord is how he reaches it.

```text
FIELD-NOTE · HYPOTHESIS · INVITATION · UNDERSTATEMENT · DISQUIET
```

### 5.4 How a reaction is chosen

The reaction is to **this presentation** and **this resolution**, never a default derived from success (Q244, Q246). The completion state reflects **how the presenter judges the act**, not whether it succeeded. Gnarl approves of evil, not of completion.

Gnarl's seriousness is selective and self-interested (Q-051). He does not become solemn because other peoples have suffered. His prejudices are characterization, not world facts (`reference/04`).

### 5.5 Distribution across all 831 popups

| | COMPOSED | PLEASED | RELISHING | DARKENED | STRICKEN | branching |
| --- | --- | --- | --- | --- | --- | --- |
| Quest unlock | 115 | 8 | 10 | 12 | 2 | — |
| Quest completion | 38 | 69 | 23 | 4 | 1 | 12 |
| Ramblings | 189 | 170 | 168 | 10 | 0 | — |
| **Total** | **342** | **247** | **201** | **26** | **3** | **12** |

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

**43 provider quests** across eleven civilizations: 10 NEUTRAL, 20 SUBJUGATED, 10 DESTROYED, 3 gateway or unrouted. SUBJUGATED carries double because leverage accumulates across several providers before a leader accepts submission.

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

**`OR-PRV-ILL-001` — Bastille commander / — (gateway)**

- Bound provider: provider: the designated Bastille commander of the Illager anchor
- Objective (specification): Overpower the designated Bastille: defeat its authored commander encounter to break local command, then complete the fearful audience that follows. Ordinary raids, patrols, outposts, mansions and unrelated Bastilles do not satisfy this.
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `ILLAGER_AUTHORITY_ESTABLISHED then ILLAGER_BASTILLE_COWED — overlord_reign:civilizations/illagers/authority_established and .../bastille_cowed`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: reference/17 Illagers: initially hostile, authority established through force, overpowering the Bastille creates a fearful/cowed non-hostile phase after which peaceful interaction opens. Gateway for Q198-Q200, which are all post-COWED proofs. Overlord decision 2026-09-18.

**`OR-PRV-ILL-002` — — / NEUTRAL**

- Bound provider: provider: bound — of the Illagers anchor
- Objective (specification): Requires `overlord_reign:civilizations/illagers/bastille_cowed`. Complete the post-COWED NEUTRAL proof
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `ILLAGER_INDEPENDENT`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q198

**`OR-PRV-ILL-003` — — / SUBJUGATED**

- Bound provider: provider: bound — of the Illagers anchor
- Objective (specification): Requires `overlord_reign:civilizations/illagers/bastille_cowed`. Complete the post-COWED SUBJUGATED proof
- Order (provider voice): TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- Recorded fact: `ILLAGER_OBLIGATED`
- Turn-in: same_provider
- Reward: NONE — V5 authors no quest rewards (Q241)
- Source: Q199

**`OR-PRV-ILL-004` — — / DESTROYED**

- Bound provider: provider: bound — of the Illagers anchor
- Objective (specification): Requires `overlord_reign:civilizations/illagers/bastille_cowed`. Complete the post-COWED DESTROYED proof
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
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** After the opening Gnarl presentation (closure register)
- **Prerequisite:** —
- **Objective (specification):** Craft the Master's Staff and summon the first Brown in the same quest
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Master's Staff crafted and first Brown summoned
- **Unlock popup:** `PLEASED` · `CEREMONIAL` — the Netherworld has chosen; Gnarl has waited centuries for this (Q-001, Q-006)
- **Completion popup:** `RELISHING` · `MOCKING` — the staff works and a Brown crawls out — relief expressed as mockery, not solemnity
- **Consequence:** Minion system proven functional; unlocks all 14 Tower Restoration quests (Q153)
- **Persistent fact:** overlord_reign:minions/brown_recovered
- **Sequence-break handling:** Recognize a pre-existing Brown if summoned before activation
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** §8.1, Q153


### Tower Restoration

#### `OR-TWR-001` — tower/throne

- **Role:** Make the Tower throne operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Throne: Necrolord Chair installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Necrolord Chair installed
- **Unlock popup:** `PLEASED` · `CEREMONIAL` — the seat is bare; the endless pit is already there (Q-011)
- **Completion popup:** `PLEASED` · `CEREMONIAL` — a seated Overlord is the point of the institution
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
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — the room was shaped for a forge and has the lava pit waiting (Q-011)
- **Completion popup:** `PLEASED` · `REWARD` — Giblet approves of a working anvil
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q002; presenter per forging theme beats source domain (Q212, §4.4)

#### `OR-TWR-003` — tower/storage

- **Role:** Make the Tower storage operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Storage: Drawer Controller connected to at least one functional drawer bank
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Drawer Controller connected to at least one functional drawer bank
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — an inventory problem
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — shelves reported as shelves
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q003

#### `OR-TWR-004` — tower/armory

- **Role:** Make the Tower armory operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Armory: One armor-display and one weapon-display from distinct supported families
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** One armor-display and one weapon-display from distinct supported families
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — arms belong on display
- **Completion popup:** `RELISHING` · `MOCKING` — Gnarl enjoys the vanity of it
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q004

#### `OR-TWR-005` — tower/treasury

- **Role:** Make the Tower treasury operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Treasury: Gold Barrel installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Gold Barrel installed
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — somewhere to put the gold
- **Completion popup:** `RELISHING` · `MOCKING` — the barrel fills; smug
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q008

#### `OR-TWR-006` — tower/waygates

- **Role:** Make the Tower waygates operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the WayGates: Tower Waystone placed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Tower Waystone placed
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — a waystone must be found in the world and carried home (Q005)
- **Completion popup:** `PLEASED` · `REWARD` — the Tower is connected
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q005

#### `OR-TWR-007` — tower/arena

- **Role:** Make the Tower arena operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Arena: Supplementaries Cage installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Supplementaries Cage installed
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — somewhere to make things fight
- **Completion popup:** `RELISHING` · `MOCKING` — Gnarl looks forward to using it
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q007

#### `OR-TWR-008` — tower/jail

- **Role:** Make the Tower jail operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Jail: Big Iron Grate installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Big Iron Grate installed
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — prisoner management, routine
- **Completion popup:** `RELISHING` · `MOCKING` — a working cell pleases him
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q007

#### `OR-TWR-009` — tower/alchemy

- **Role:** Make the Tower alchemy operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Alchemy: Ars Elixirum Glass Cauldron installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Ars Elixirum Glass Cauldron installed
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — a workbench for volatile study
- **Completion popup:** `PLEASED` · `REWARD` — the discipline is housed
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006, Q232

#### `OR-TWR-010` — tower/theurgy

- **Role:** Make the Tower theurgy operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Theurgy: Theurgy core workstation installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Theurgy core workstation installed
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — a workbench for transmutation
- **Completion popup:** `PLEASED` · `REWARD` — the discipline is housed
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
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gristle is getting his kitchen
- **Completion popup:** `PLEASED` · `REWARD` — Gristle has his domain
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006; presenter per food theme beats source domain (Q212, §4.2)

#### `OR-TWR-012` — tower/spell_study

- **Role:** Make the Tower spell study operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Spell Study: Iron's Spells inscription workstation installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Iron's Spells inscription workstation installed
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — a place to inscribe
- **Completion popup:** `PLEASED` · `REWARD` — the discipline is housed
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006

#### `OR-TWR-013` — tower/eidolon

- **Role:** Make the Tower eidolon operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Eidolon: Eidolon core workstation installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Eidolon core workstation installed
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — occult plant, handled as plant (Q096: Eidolon is not Mortis material)
- **Completion popup:** `PLEASED` · `REWARD` — housed and controlled
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006

#### `OR-TWR-014` — tower/biomancy

- **Role:** Make the Tower biomancy operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Biomancy: Biomancy core workstation installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Biomancy core workstation installed
- **Unlock popup:** `PLEASED` · `CEREMONIAL` — Gnarl's own Silence-era discipline finally gets a chamber (R-039, Q-006)
- **Completion popup:** `PLEASED` · `CEREMONIAL` — what he researched for centuries without a Master is now housed
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006; Gnarl/Biomancy authorship per R-039 and Q-006


### Minion Restoration

#### `OR-MIN-001` — minions/restore_red

- **Role:** Restore the Red tribe
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Enter the Netherworld, kill a Blaze, return with a Blaze Rod
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** All feat steps complete
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the Reds can be recovered
- **Completion popup:** `PLEASED` · `REWARD` — fire returns to the Horde
- **Consequence:** Invokes the Minion owner API to unlock the Red tribe (§8); one completion presentation (Q011)
- **Persistent fact:** overlord_reign:minions/red_recovered
- **Sequence-break handling:** Recognize a pre-existing Blaze Rod
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q009, Q010, Q011, §8.2-8.4

#### `OR-MIN-002` — minions/restore_green

- **Role:** Restore the Green tribe
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MIN-001
- **Objective (specification):** Brew a Potion of Poison, become poisoned, kill a Witch while poisoned
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** All feat steps complete
- **Unlock popup:** `RELISHING` · `MOCKING` — the feat requires the Master to poison himself on purpose
- **Completion popup:** `RELISHING` · `MOCKING` — it worked, and Gnarl found the method very funny
- **Consequence:** Invokes the Minion owner API to unlock the Green tribe (§8); one completion presentation (Q011)
- **Persistent fact:** overlord_reign:minions/green_recovered
- **Sequence-break handling:** Recognize a prior Witch kill only if poisoned at the time
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q009, Q010, Q011, §8.2-8.4

#### `OR-MIN-003` — minions/restore_blue

- **Role:** Restore the Blue tribe
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MIN-002
- **Objective (specification):** Prepare Water Breathing, enter an Ocean Monument, kill an Elder Guardian
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** All feat steps complete
- **Unlock popup:** `COMPOSED` · `TACTICAL` — drowning is a real risk to a thin Horde (§8: depletion is an operational problem)
- **Completion popup:** `PLEASED` · `CEREMONIAL` — the Horde is whole for the first time since the Silence
- **Consequence:** Invokes the Minion owner API to unlock the Blue tribe (§8); one completion presentation (Q011)
- **Persistent fact:** overlord_reign:minions/blue_recovered
- **Sequence-break handling:** Recognize a prior Elder Guardian kill
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q009, Q010, Q011, §8.2-8.4


### Bosses'Rise

#### `OR-BOS-001` — bosses_rise/skor/locate

- **Role:** Locate the skor domain
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** All five locate quests activate together when the §7.1 readiness gate is satisfied (Q159)
- **Prerequisite:** Tower readiness gate
- **Objective (specification):** Locate the Nordberg / frozen domain domain / anomaly
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Domain located
- **Unlock popup:** `COMPOSED` · `TACTICAL` — something feeds on the frozen north
- **Completion popup:** `COMPOSED` · `TACTICAL` — the domain is located
- **Consequence:** Opens the investigation stage (Q012)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior discovery of the domain
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q159

#### `OR-BOS-002` — bosses_rise/skor/investigate

- **Role:** Establish what skor is
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-001
- **Objective (specification):** Trigger one qualifying Phase 2 frost / icicle attack event without killing Skor
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Native proof event observed
- **Unlock popup:** `COMPOSED` · `TACTICAL` — a beast empowered by the wound, nothing more
- **Completion popup:** `COMPOSED` · `TACTICAL` — the evidence is understood
- **Consequence:** Gnarl delivers the approved interpretation of Skor / Yeti
- **Persistent fact:** NONE
- **Sequence-break handling:** Native proof may already have occurred
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q143

#### `OR-BOS-003` — bosses_rise/skor/defeat

- **Role:** Destroy skor
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-002
- **Objective (specification):** Defeat Skor / Yeti
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** block_factorys_bosses:kill_yeti
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the kill order
- **Completion popup:** `PLEASED` · `REWARD` — the north is quiet
- **Consequence:** Advances the five-subcampaign progression
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an early boss kill; never respawn
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q013-Q017

#### `OR-BOS-004` — bosses_rise/sirok/locate

- **Role:** Locate the sirok domain
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** All five locate quests activate together when the §7.1 readiness gate is satisfied (Q159)
- **Prerequisite:** Tower readiness gate
- **Objective (specification):** Locate the Ruborian desert domain / anomaly
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Domain located
- **Unlock popup:** `COMPOSED` · `TACTICAL` — the desert is moving where it should not
- **Completion popup:** `COMPOSED` · `TACTICAL` — the domain is located
- **Consequence:** Opens the investigation stage (Q012)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior discovery of the domain
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q159

#### `OR-BOS-005` — bosses_rise/sirok/investigate

- **Role:** Establish what sirok is
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-004
- **Objective (specification):** Crack one armored body segment, then strike it again to trigger the native poisonous-blood spill
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Native proof event observed
- **Unlock popup:** `COMPOSED` · `TACTICAL` — the worm was enlarged by rift energy
- **Completion popup:** `COMPOSED` · `TACTICAL` — the evidence is understood
- **Consequence:** Gnarl delivers the approved interpretation of Sirok / Sandworm
- **Persistent fact:** NONE
- **Sequence-break handling:** Native proof may already have occurred
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q144

#### `OR-BOS-006` — bosses_rise/sirok/defeat

- **Role:** Destroy sirok
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-005
- **Objective (specification):** Defeat Sirok / Sandworm
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** block_factorys_bosses:kill_sandworm
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the kill order
- **Completion popup:** `PLEASED` · `REWARD` — the desert is emptied
- **Consequence:** Advances the five-subcampaign progression
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an early boss kill; never respawn
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q013-Q017

#### `OR-BOS-007` — bosses_rise/ashlord/locate

- **Role:** Locate the ashlord domain
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** All five locate quests activate together when the §7.1 readiness gate is satisfied (Q159)
- **Prerequisite:** Tower readiness gate
- **Objective (specification):** Locate the Dragon Tower domain / anomaly
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Domain located
- **Unlock popup:** `DARKENED` · `OMINOUS` — a dragon has been raised from the dead
- **Completion popup:** `COMPOSED` · `TACTICAL` — the domain is located
- **Consequence:** Opens the investigation stage (Q012)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior discovery of the domain
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q159

#### `OR-BOS-008` — bosses_rise/ashlord/investigate

- **Role:** Establish what ashlord is
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-007
- **Objective (specification):** Recover one native block_factorys_bosses:dragon_banner from the bound Dragon Tower
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Native proof event observed
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — the first of the resurrected
- **Completion popup:** `COMPOSED` · `TACTICAL` — the evidence is understood
- **Consequence:** Gnarl delivers the approved interpretation of Ashlord
- **Persistent fact:** NONE
- **Sequence-break handling:** Native proof may already have occurred
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q145

#### `OR-BOS-009` — bosses_rise/ashlord/defeat

- **Role:** Destroy ashlord
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-008
- **Objective (specification):** Defeat Ashlord
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** block_factorys_bosses:kill_dragon
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the kill order
- **Completion popup:** `PLEASED` · `REWARD` — the resurrection is undone
- **Consequence:** Advances the five-subcampaign progression
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an early boss kill; never respawn
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q013-Q017

#### `OR-BOS-010` — bosses_rise/helvar/locate

- **Role:** Locate the helvar domain
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** All five locate quests activate together when the §7.1 readiness gate is satisfied (Q159)
- **Prerequisite:** Tower readiness gate
- **Objective (specification):** Locate the Underworld domain / anomaly
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Domain located
- **Unlock popup:** `STRICKEN` · `STRICKEN` — the Third Overlord is loose: Gnarl's last Master, to whom he expressed unusual personal attachment and whose escape from the Infernal Abyss he allowed might still happen. The news lands before he can compose himself.
- **Completion popup:** `COMPOSED` · `TACTICAL` — the domain is located
- **Consequence:** Opens the investigation stage (Q012)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior discovery of the domain
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q159

#### `OR-BOS-011` — bosses_rise/helvar/investigate

- **Role:** Establish what helvar is
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-010
- **Objective (specification):** Obtain the native block_factorys_bosses:underworld_arena_key and use it to pass the boss-door progression
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Native proof event observed
- **Unlock popup:** `DARKENED` · `OMINOUS` — Gnarl confirms the identity while holding himself together — control kept, valence negative. The break comes when it is done, not while he is still naming it.
- **Completion popup:** `COMPOSED` · `TACTICAL` — the evidence is understood
- **Consequence:** Gnarl delivers the approved interpretation of Helvar / Third Overlord
- **Persistent fact:** NONE
- **Sequence-break handling:** Native proof may already have occurred
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q146

#### `OR-BOS-012` — bosses_rise/helvar/defeat

- **Role:** Destroy helvar
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-011
- **Objective (specification):** Defeat Helvar / Third Overlord
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** block_factorys_bosses:kill_underworld_knight
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the kill order
- **Completion popup:** `STRICKEN` · `STRICKEN` — the Master Gnarl waited centuries for is dead by his replacement's hand. The mask is gone. No mockery anywhere in this line.
- **Consequence:** Advances the five-subcampaign progression
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an early boss kill; never respawn
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q013-Q017

#### `OR-BOS-013` — bosses_rise/nerakyss/locate

- **Role:** Locate the nerakyss domain
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** All five locate quests activate together when the §7.1 readiness gate is satisfied (Q159)
- **Prerequisite:** Tower readiness gate
- **Objective (specification):** Locate the Kraken Ship domain / anomaly
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Domain located
- **Unlock popup:** `COMPOSED` · `TACTICAL` — the sea has leaked for centuries
- **Completion popup:** `COMPOSED` · `TACTICAL` — the domain is located
- **Consequence:** Opens the investigation stage (Q012)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior discovery of the domain
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q159

#### `OR-BOS-014` — bosses_rise/nerakyss/investigate

- **Role:** Establish what nerakyss is
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-013
- **Objective (specification):** Defeat the three native pirate guard variants protecting the encounter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Native proof event observed
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — old contamination, still running
- **Completion popup:** `COMPOSED` · `TACTICAL` — the evidence is understood
- **Consequence:** Gnarl delivers the approved interpretation of Nerakyss
- **Persistent fact:** NONE
- **Sequence-break handling:** Native proof may already have occurred
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q147

#### `OR-BOS-015` — bosses_rise/nerakyss/defeat

- **Role:** Destroy nerakyss
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-014
- **Objective (specification):** Defeat Nerakyss
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** block_factorys_bosses:kill_kraken
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the kill order
- **Completion popup:** `PLEASED` · `REWARD` — the ocean threat is cut
- **Consequence:** Advances the five-subcampaign progression
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an early boss kill; never respawn
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q013-Q017


### End Campaign

#### `OR-END-001` — end/expedition

- **Role:** Enter the Cataclysm Dimension
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately after the post-five Gnarl presentation-only transition (Q018)
- **Prerequisite:** All five Bosses'Rise subcampaigns
- **Objective (specification):** Enter the End dimension
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Entering the dimension is the authored threshold (Q019)
- **Unlock popup:** `DARKENED` · `OMINOUS` — the End is the Wasteland rupture the Cataclysm exposed (R-034)
- **Completion popup:** `DARKENED` · `OMINOUS` — the wound is entered
- **Consequence:** Establishes the source-side wound; completion presentation identifies the Ender Dragon as the living anchor (Q160)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior End entry
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q018, Q019

#### `OR-END-002` — end/ender_dragon

- **Role:** Resolve the living anchor
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** After the End-entry completion presentation has played (Q160)
- **Prerequisite:** OR-END-001
- **Objective (specification):** Defeat the Ender Dragon
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Ender Dragon defeated
- **Unlock popup:** `DARKENED` · `OMINOUS` — the living anchor sustaining the wound
- **Completion popup:** `PLEASED` · `CEREMONIAL` — the reign is secure
- **Consequence:** Resolves the living anchor; triggers the one-time Gnarl ending presentation (Q020, Q023)
- **Persistent fact:** overlord_reign:campaign/central_ending (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Handled by the approved early-dragon policy (Q021)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q020, Q160


### Post-Credits

#### `OR-PCR-001` — post_credits/return

- **Role:** Post-ending End expedition
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately after the one-time Gnarl ending presentation (Q023)
- **Prerequisite:** OR-END-002
- **Objective (specification):** Return to the End after the ending
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:visit_dimension_history on minecraft:the_end, evaluated after the ending fact is present
- **Unlock popup:** `COMPOSED` · `CHOICE` — the war is won; curiosity replaces urgency
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the End is changed
- **Consequence:** Advances the expedition
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q047, Q170

#### `OR-PCR-002` — post_credits/ecology

- **Role:** Post-ending End expedition
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-PCR-001
- **Objective (specification):** Explore the altered outer ecology
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill_history — one kill of each of the installed outer-End creature set
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — an altered ecology, catalogued
- **Completion popup:** `RELISHING` · `MOCKING` — Gnarl finds the new End absurd
- **Consequence:** Advances the expedition
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q047, Q170

#### `OR-PCR-003` — post_credits/ruins

- **Role:** Post-ending End expedition
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-PCR-002
- **Objective (specification):** Investigate major ruins, cities and island content
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:visit_structure_history — the installed End city / ruin structure set
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — ruins and cities to pick over
- **Completion popup:** `PLEASED` · `REWARD` — spoils
- **Consequence:** Advances the expedition
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q047, Q170

#### `OR-PCR-004` — post_credits/capstone

- **Role:** Post-ending End expedition
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-PCR-003
- **Objective (specification):** Complete both explicit Outer End major-structure discovery accomplishments and close the expedition
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** AND(outer_end:end/find_catacombs, outer_end:end/find_end_tower)  [Q170: both explicit Outer End structure accomplishments]
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — one last thing worth seeing
- **Completion popup:** `RELISHING` · `MOCKING` — nothing left to conquer; smug and faintly bored
- **Consequence:** Closes the post-credits expedition
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q047, Q170


### Magic

#### `OR-MAG-IRO-001` — magic/irons/practical_casting

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-012
- **Objective (specification):** Establish practical spellcasting
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** irons_spellbooks:irons_spellbooks/spell_book_equip
- **Unlock popup:** `PLEASED` · `CEREMONIAL` — the discipline opens
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Advances the Iron's Spells arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q124

#### `OR-MAG-IRO-002` — magic/irons/shape_capability

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-IRO-001
- **Objective (specification):** Shape / improve spell capability
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** irons_spellbooks:irons_spellbooks/make_arcane_anvil
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the next stage
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Advances the Iron's Spells arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q125

#### `OR-MAG-IRO-003` — magic/irons/advanced_casting

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-IRO-002
- **Objective (specification):** Affect a hostile target with one offensive spell and use one non-damage utility, defensive or movement spell
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:or of two framework objectives — one offensive spell hit on a hostile mob, one utility/defensive/movement cast (Q236)
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the next stage
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Advances the Iron's Spells arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q126

#### `OR-MAG-IRO-004` — magic/irons/mastery_or

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-IRO-003
- **Objective (specification):** Final OR mastery using Holy or Blood; records the first inclination
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR(irons_spellbooks:irons_spellbooks/spell_book_dead_king, irons_spellbooks:irons_spellbooks/spell_book_blood)
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the next stage
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Completes the Iron's Spells arc
- **Persistent fact:** overlord_reign:magic/irons/first_inclination (holy|blood) — Q024 (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q024, Q127

#### `OR-MAG-EID-001` — magic/eidolon/basic_ritual

- **Role:** Eidolon discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-013
- **Objective (specification):** Basic ritual practice
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** eidolon:brazier
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — occult procedure (Q096: not Mortis material)
- **Completion popup:** `PLEASED` · `REWARD` — a path taken
- **Consequence:** Advances the Eidolon arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q128

#### `OR-MAG-EID-002` — magic/eidolon/sacrifice_prep

- **Role:** Eidolon discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-EID-001
- **Objective (specification):** Meaningful sacrifice / material preparation
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** eidolon:sacrifice
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — occult procedure (Q096: not Mortis material)
- **Completion popup:** `PLEASED` · `REWARD` — a path taken
- **Consequence:** Advances the Eidolon arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q129

#### `OR-MAG-EID-003` — magic/eidolon/advanced_occult

- **Role:** Eidolon discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-EID-002
- **Objective (specification):** Advanced ritual / occult capability
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** eidolon:soul_shard
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — occult procedure (Q096: not Mortis material)
- **Completion popup:** `PLEASED` · `REWARD` — a path taken
- **Consequence:** Advances the Eidolon arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q130

#### `OR-MAG-EID-004` — magic/eidolon/mastery_or

- **Role:** Eidolon discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-EID-003
- **Objective (specification):** Final OR mastery between the Sacred and Wicked paths
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR(eidolon:sacred_path, eidolon:wicked_path)
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — occult procedure (Q096: not Mortis material)
- **Completion popup:** `COMPOSED` · `CHOICE` — a path taken
- **Consequence:** Completes the Eidolon arc
- **Persistent fact:** overlord_reign:magic/eidolon/path (sacred|wicked) — Q025 (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q025, Q131

#### `OR-MAG-THE-001` — magic/theurgy/material_principle

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-010
- **Objective (specification):** Understand / extract material principle
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** theurgy:has_liquefaction_cauldron
- **Unlock popup:** `PLEASED` · `CEREMONIAL` — the discipline opens
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Advances the Theurgy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q027, Q133

#### `OR-MAG-THE-002` — magic/theurgy/controlled_conversion

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-THE-001
- **Objective (specification):** Perform controlled conversion
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** theurgy:has_basic_rod
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the next stage
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Advances the Theurgy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q027, Q133

#### `OR-MAG-THE-003` — magic/theurgy/reproduce_material

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-THE-002
- **Objective (specification):** Reproduce a useful material deliberately
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** theurgy:has_t2_rod
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the next stage
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Advances the Theurgy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q027, Q133

#### `OR-MAG-THE-004` — magic/theurgy/advanced_transmutation

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-THE-003
- **Objective (specification):** Demonstrate advanced repeatable transmutation without requiring every machine
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** theurgy:has_t4_rod
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the next stage
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Completes the Theurgy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q027, Q133

#### `OR-MAG-BIO-001` — magic/biomancy/organic_matter

- **Role:** Biomancy discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-014
- **Objective (specification):** Acquire / understand organic matter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** biomancy:biomancy/root
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — Gnarl explains where this came from: Solarius, and centuries of boredom (Q-006)
- **Completion popup:** `PLEASED` · `REWARD` — the Master has entered his work
- **Consequence:** Advances the Biomancy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q029, Q135; Gnarl/Biomancy authorship per R-039 and Q-006

#### `OR-MAG-BIO-002` — magic/biomancy/primordial_core

- **Role:** Biomancy discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-BIO-001
- **Objective (specification):** Establish Primordial Core / Cradle capability
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** biomancy:biomancy/cradle
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gnarl walks the Master through his own method
- **Completion popup:** `PLEASED` · `REWARD` — the Cradle lives; he is proprietary about it
- **Consequence:** Advances the Biomancy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q029, Q135; Gnarl/Biomancy authorship per R-039 and Q-006

#### `OR-MAG-BIO-003` — magic/biomancy/living_flesh

- **Role:** Biomancy discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-BIO-002
- **Objective (specification):** Manipulate living flesh
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** biomancy:biomancy/living_flesh
- **Unlock popup:** `PLEASED` · `CEREMONIAL` — the animation of flesh is the whole point of the research
- **Completion popup:** `PLEASED` · `CEREMONIAL` — what he could never do alone is being done
- **Consequence:** Advances the Biomancy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q029, Q135; Gnarl/Biomancy authorship per R-039 and Q-006

#### `OR-MAG-BIO-004` — magic/biomancy/bio_forge

- **Role:** Biomancy discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-BIO-003
- **Objective (specification):** Use the Decomposer / Bio Forge
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** biomancy:biomancy/bio_forge
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the industrial stage of his own discipline
- **Completion popup:** `PLEASED` · `REWARD` — the Forge runs
- **Consequence:** Advances the Biomancy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q029, Q135; Gnarl/Biomancy authorship per R-039 and Q-006

#### `OR-MAG-BIO-005` — magic/biomancy/advanced_proof

- **Role:** Biomancy discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-BIO-004
- **Objective (specification):** Complete a serum or advanced bio-engineering proof
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** biomancy:biomancy/bio_lab
- **Unlock popup:** `PLEASED` · `CEREMONIAL` — the thing he never had a Master to attempt
- **Completion popup:** `PLEASED` · `CEREMONIAL` — he finally has an Overlord to direct the work toward suitably unfortunate subjects
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
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gristle sets the task
- **Completion popup:** `PLEASED` · `REWARD` — Gristle is pleased with the result
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
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gristle sets the task
- **Completion popup:** `PLEASED` · `REWARD` — Gristle is pleased with the result
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
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gristle sets the task
- **Completion popup:** `PLEASED` · `REWARD` — Gristle is pleased with the result
- **Consequence:** Completes the Gluttony / Farmer's Spell arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q026, Q132

#### `OR-MAG-ELI-001` — magic/elixirum/identify_properties

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-009
- **Objective (specification):** Identify ingredient properties
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:elixirum_mastery — first mastery threshold (framework type already implemented)
- **Unlock popup:** `PLEASED` · `CEREMONIAL` — the discipline opens
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Advances the Ars Elixirum arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q028, Q134

#### `OR-MAG-ELI-002` — magic/elixirum/formulate

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-ELI-001
- **Objective (specification):** Formulate intentionally in the Glass Cauldron
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on any elixirum elixir produced in the Glass Cauldron
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the next stage
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Advances the Ars Elixirum arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q028, Q134

#### `OR-MAG-ELI-003` — magic/elixirum/mastery

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-ELI-002
- **Objective (specification):** Increase Mastery and complete one controlled advanced formulation
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:elixirum_mastery (custom objective type already in the framework)
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the next stage
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
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
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gristle wants the estate fed
- **Completion popup:** `PLEASED` · `REWARD` — the estate provides
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
- **Completion condition:** questlog:entity_kill_history is wrong here; use a presence check on cropcritters:wheat_critter — capability, not a kill (Q031)
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gristle wants the estate fed
- **Completion popup:** `PLEASED` · `REWARD` — the estate provides
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
- **Objective (specification):** Prove assisted estate labour: after the Crop Critter stage, reach an authored harvest threshold that ordinary hand-farming would not produce. The Hay Golem is the intended means; the proof is the output it enables, not the golem itself.
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:stat on crop-harvest counters, threshold reached after OR-FRM-002 completes
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gristle wants the estate fed
- **Completion popup:** `PLEASED` · `REWARD` — the estate provides
- **Consequence:** Advances the estate agriculture arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior harvests
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q031; REWORDED 2026-09-18. The original objective named the golem, which no framework objective type can see: golemoverhaul builds it from an in-world multiblock pattern and its only advancement fires on `recipe_unlocked`, which a hay block in the inventory can trigger. The stage's approved purpose under Q031/Q032 is estate agriculture, so the proof is measured on output. **Caveat: this does not verify a Hay Golem exists.** A player reaching the threshold another way satisfies it.

#### `OR-FRM-004` — farming/assisted_estate

- **Role:** Estate agriculture stage
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-FRM-003
- **Objective (specification):** Assisted estate operation
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:stat on harvest counters achieved while the Hay Golem entity is present
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gristle wants the estate fed
- **Completion popup:** `PLEASED` · `REWARD` — the estate provides
- **Consequence:** Advances the estate agriculture arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior harvests
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q031


### Adventure

#### `OR-ADV-TWIL-001` — adventures/twilight/ch1

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Enter Twilight Forest, defeat the Naga, defeat the Lich
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** twilightforest:progress_lich
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — an unfamiliar realm; a fae refuge from Imperial persecution, not Cataclysm-made
- **Completion popup:** `PLEASED` · `REWARD` — the first tier falls
- **Consequence:** Advances the Twilight Forest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q112

#### `OR-ADV-TWIL-002` — adventures/twilight/ch2

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-TWIL-001
- **Objective (specification):** Labyrinth / Minoshroom, then the Hydra
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** twilightforest:progress_hydra
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — deeper tiers
- **Completion popup:** `PLEASED` · `REWARD` — progress
- **Consequence:** Advances the Twilight Forest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q112

#### `OR-ADV-TWIL-003` — adventures/twilight/ch3

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-TWIL-002
- **Objective (specification):** Trophy Pedestal, Knight Phantoms, Ghast Trap, Ur-Ghast
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** twilightforest:progress_ur_ghast
- **Unlock popup:** `COMPOSED` · `TACTICAL` — the forest resists
- **Completion popup:** `PLEASED` · `REWARD` — the towers fall
- **Consequence:** Advances the Twilight Forest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q112

#### `OR-ADV-TWIL-004` — adventures/twilight/ch4

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-TWIL-003
- **Objective (specification):** Alpha Yeti, then the Snow Queen
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** twilightforest:progress_glacier
- **Unlock popup:** `COMPOSED` · `TACTICAL` — ice and its queen
- **Completion popup:** `PLEASED` · `REWARD` — the glacier is taken
- **Consequence:** Advances the Twilight Forest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q112

#### `OR-ADV-TWIL-005` — adventures/twilight/ch5

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-TWIL-004
- **Objective (specification):** Highlands merge gate, Troll Caves, Beanstalk / Giants, Lamp of Cinders
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** twilightforest:progression_end
- **Unlock popup:** `RELISHING` · `MOCKING` — giants and a beanstalk
- **Completion popup:** `RELISHING` · `MOCKING` — the realm is finished and he enjoyed the ending
- **Consequence:** Completes the Twilight Forest arc
- **Persistent fact:** overlord_reign:adventure/twilight_completed (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q112

#### `OR-ADV-RATS-001` — adventures/rats/utility

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Tame one rat, configure it with the Cheese Staff, complete one successful item transfer
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:or [ questlog:item_use on rats:cheese_staff | questlog:stat on rat transfer counter ]
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — rats, as a workforce
- **Completion popup:** `RELISHING` · `MOCKING` — vermin made useful
- **Consequence:** Advances the Rats / Ratlantis arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q113

#### `OR-ADV-RATS-002` — adventures/rats/access

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-RATS-001
- **Objective (specification):** Ratlantis access beat
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** ratlantis:ratlantis
- **Unlock popup:** `RELISHING` · `MOCKING` — an empire beneath the cheese
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the way in is open
- **Consequence:** Advances the Rats / Ratlantis arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q163

#### `OR-ADV-RATS-003` — adventures/rats/investigate

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-RATS-002
- **Objective (specification):** Ratlantis investigation beat
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on ratlantis:gem_of_ratlantis
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — a civilization older than the Overlords and unrelated to them
- **Completion popup:** `COMPOSED` · `HISTORICAL` — the empire is understood
- **Consequence:** Advances the Rats / Ratlantis arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q164

#### `OR-ADV-RATS-004` — adventures/rats/capstone

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-RATS-003
- **Objective (specification):** Defeat the Rat Baron
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** ratlantis:defeat_rat_baron
- **Unlock popup:** `COMPOSED` · `TACTICAL` — the Rat Baron rules here
- **Completion popup:** `RELISHING` · `MOCKING` — a rat monarch deposed
- **Consequence:** Advances the Rats / Ratlantis arc
- **Persistent fact:** overlord_reign:adventure/ratlantis_completed (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q114

#### `OR-ADV-RATS-005` — adventures/rats/closure

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-RATS-004
- **Objective (specification):** Return and close the expedition
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:visit_dimension_history — return from Ratlantis to the Overworld after the capstone fact
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the expedition ends
- **Completion popup:** `RELISHING` · `MOCKING` — an absurd conquest, completed
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
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the Historian withholds judgement
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — a specimen, recorded
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
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — a message, and a question
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the investigation begins
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
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — evidence to be decoded
- **Completion popup:** `PLEASED` · `REWARD` — a complete set pleases the Historian
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
- **Unlock popup:** `COMPOSED` · `TACTICAL` — the expedition is hazardous
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the waters are surveyed
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
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — the corruption may be manipulable
- **Completion popup:** `PLEASED` · `REWARD` — the hypothesis is confirmed
- **Consequence:** Completes the Fathoms / Overlord Depths arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q118

#### `OR-ADV-KNIG-001` — adventures/knightquest/chalice

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Craft and place the Great Chalice
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:block_interact on the placed Great Chalice block
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — a rival faith and its relic; the Netherman belongs to the Forgotten God, not the Cataclysm
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the chalice stands
- **Consequence:** Advances the Knight Quest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q117

#### `OR-ADV-KNIG-002` — adventures/knightquest/essence

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-KNIG-001
- **Objective (specification):** Obtain and offer four Great Essence to fill the Chalice
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain x4 on knightquest:great_essence
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the chalice must be filled
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — filled
- **Consequence:** Advances the Knight Quest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q117

#### `OR-ADV-KNIG-003` — adventures/knightquest/radiant

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-KNIG-002
- **Objective (specification):** Create Radiant Essence and offer it to the filled Chalice to summon Netherman
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on knightquest:radiant_essence
- **Unlock popup:** `COMPOSED` · `TACTICAL` — the summoning invites something
- **Completion popup:** `DARKENED` · `OMINOUS` — the Netherman comes
- **Consequence:** Advances the Knight Quest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q117

#### `OR-ADV-KNIG-004` — adventures/knightquest/netherman

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-KNIG-003
- **Objective (specification):** Defeat Netherman, the Architect of Chaos
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill on the Netherman entity
- **Unlock popup:** `DARKENED` · `OMINOUS` — the Architect of Chaos
- **Completion popup:** `PLEASED` · `REWARD` — a rival power destroyed
- **Consequence:** Completes the Knight Quest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q117

#### `OR-ADV-GRAV-001` — adventures/graveyard/ruins

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Investigate the ruins
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** graveyard:graveyard/medium_graveyard
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — the dead here do not stay buried
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the ruins are surveyed
- **Consequence:** Advances the The Graveyard arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q034

#### `OR-ADV-GRAV-002` — adventures/graveyard/fragments

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-GRAV-001
- **Objective (specification):** Recover all three Ominous Bone Staff fragments
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** AND(graveyard:graveyard/lower_bone_staff, middle_bone_staff, upper_bone_staff)
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — three pieces of a staff
- **Completion popup:** `PLEASED` · `REWARD` — the staff is whole
- **Consequence:** Advances the The Graveyard arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q034

#### `OR-ADV-GRAV-003` — adventures/graveyard/summon

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-GRAV-002
- **Objective (specification):** Find the Lich Altar, fill the Blood Vial, perform the native nighttime summoning
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** graveyard:graveyard/summon_lich
- **Unlock popup:** `RELISHING` · `MOCKING` — the Master will call it up on purpose
- **Completion popup:** `DARKENED` · `OMINOUS` — the Lich answers
- **Consequence:** Advances the The Graveyard arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q116

#### `OR-ADV-GRAV-004` — adventures/graveyard/lich

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-GRAV-003
- **Objective (specification):** Defeat the Corrupted Champion / Lich
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill on the Lich entity
- **Unlock popup:** `COMPOSED` · `TACTICAL` — the Corrupted Champion
- **Completion popup:** `PLEASED` · `REWARD` — put back down
- **Consequence:** Completes the The Graveyard arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q034, Q116

#### `OR-ADV-BUMB-001` — adventures/bumblezone/enter

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Discover and enter the realm
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** the_bumblezone:root
- **Unlock popup:** `RELISHING` · `MOCKING` — a realm of bees, of all things
- **Completion popup:** `RELISHING` · `MOCKING` — the Master is in the hive
- **Consequence:** Advances the The Bumblezone arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q042

#### `OR-ADV-BUMB-002` — adventures/bumblezone/queen

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-BUMB-001
- **Objective (specification):** Establish meaningful Queen contact
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** the_bumblezone:beehemoth/queen_beehemoth
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the Queen must be dealt with
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the Queen receives him
- **Consequence:** Advances the The Bumblezone arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q169

#### `OR-ADV-BUMB-003` — adventures/bumblezone/essence

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-BUMB-002
- **Objective (specification):** Complete Queen's Desires and obtain / consume Essence of the Bees
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** the_bumblezone:essence/bee_essence_infusion
- **Unlock popup:** `RELISHING` · `MOCKING` — the Queen has demands
- **Completion popup:** `RELISHING` · `MOCKING` — the Overlord ran errands for a bee
- **Consequence:** Completes the The Bumblezone arc
- **Persistent fact:** overlord_reign:adventure/bumblezone_completed (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q042

#### `OR-ADV-LOST-001` — adventures/lost_castle/discover

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Discover and enter the castle
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:visit_structure_history on tlc:lost_castle
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — an obscure predecessor civilization left this standing
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the castle is found
- **Consequence:** Advances the The Lost Castle arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q036, Q167

#### `OR-ADV-LOST-002` — adventures/lost_castle/clear

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-LOST-001
- **Objective (specification):** Investigate predecessor evidence and clear the Illager occupation
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill on the Illager occupants within tlc:lost_castle
- **Unlock popup:** `COMPOSED` · `TACTICAL` — Illagers are squatting in it
- **Completion popup:** `PLEASED` · `REWARD` — the squatters are cleared
- **Consequence:** Advances the The Lost Castle arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q036, Q167

#### `OR-ADV-LOST-003` — adventures/lost_castle/treasure

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-LOST-002
- **Objective (specification):** Recover the expedition's meaningful treasure / evidence and close the arc
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on an item from the tlc:chests/throne or tlc:chests/treasure loot table
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — whatever they were guarding
- **Completion popup:** `PLEASED` · `REWARD` — a predecessor's hoard, taken
- **Consequence:** Completes the The Lost Castle arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q036, Q167

#### `OR-ADV-ORCH-001` — adventures/orchid/shrine

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Discover the shrine / local cult context
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:visit_structure_history on oddities:orchid_shrine
- **Unlock popup:** `COMPOSED` · `TACTICAL` — something in the wood is killing and it is not ours
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the shrine is found
- **Consequence:** Advances the Oddities / Queen of Orchid arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q037, Q168

#### `OR-ADV-ORCH-002` — adventures/orchid/prepare

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-ORCH-001
- **Objective (specification):** Investigate or prepare for the nature-spirit threat
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on oddities:orchid_heart
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the shrine wants a heart
- **Completion popup:** `RELISHING` · `MOCKING` — Gnarl approves of the price
- **Consequence:** Advances the Oddities / Queen of Orchid arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q037, Q168

#### `OR-ADV-ORCH-003` — adventures/orchid/queen

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-ORCH-002
- **Objective (specification):** Defeat / resolve the Queen of Orchid encounter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill on oddities:queen_of_orchid
- **Unlock popup:** `COMPOSED` · `TACTICAL` — a local nature-spirit power, independent of every major history
- **Completion popup:** `PLEASED` · `REWARD` — the wood is quiet
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
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — Mortis attends a death as Spawning Pit business, black velvet and scythe
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the collar is recovered
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
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Mortis explains what the Pit can do
- **Completion popup:** `RELISHING` · `MOCKING` — it came back wrong; Mortis is morbidly unbothered
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
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — it can be made right
- **Completion popup:** `PLEASED` · `REWARD` — a death properly reversed — his actual office
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
- **Unlock popup:** `STRICKEN` · `STRICKEN` — Gnarl alone. The Master has done something that may kill him or unfit him to rule (37 §3, Q-051 Overlord clause). Lestat has not arrived yet.
- **Completion popup:** `PER-BRANCH` · `PER-BRANCH` — Lestat's first appearance, reacting to the arm taken. Arm A (living victim) — register CEREMONIAL, visual `PLEASED`: intimate observation; the Overlord has stopped denying what he is, which is the thing Lestat came for (40 §4). Arm B (Blood Bottle) — register REACTIVE, visual `DARKENED`: philosophical provocation; unnecessary restraint revealing fear of identity (40 §20). He diagnoses repression before weakness and names the gap between what the Overlord is and what he will do under pressure (40 §4).
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
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Lestat warns that being what he is attracts attention
- **Completion popup:** `PLEASED` · `REWARD` — mastery, not mere completion (40 §20) — a hunter is a real test
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
- **Unlock popup:** `COMPOSED` · `CHOICE` — power has a price and Lestat approves of paying it
- **Completion popup:** `RELISHING` · `MOCKING` — Lestat is pleased by the bargain struck
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
- **Completion condition:** questlog:item_obtain x8 on immersive_melodies:{bagpipe,flute,lute,piano,trumpet,tiny_drum,vielle,handpan} — all eight confirmed present
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Quaver wants his instruments
- **Completion popup:** `RELISHING` · `MOCKING` — the Tower has a band and he will not stop composing about it
- **Consequence:** Completes the Quaver's Tower Band arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q111, Q166

#### `OR-CAT-001` — adventures/cataclysm/harbinger

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Discover the bound structure (find_ancient_factory) and defeat The Harbinger
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_harbinger
- **Unlock popup:** `DARKENED` · `OMINOUS` — the Cataclysm is an Elf blowing up an Overlord Tower Heart; Gnarl carries that grievance (R-032, 01)
- **Completion popup:** `PLEASED` · `REWARD` — one of eight
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

#### `OR-CAT-002` — adventures/cataclysm/remnant

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-001
- **Objective (specification):** Discover the bound structure (find_cursed_pyramid) and defeat Ancient Remnant
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_remnant
- **Unlock popup:** `DARKENED` · `OMINOUS` — a pyramid that should have stayed shut
- **Completion popup:** `PLEASED` · `REWARD` — put back down
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

#### `OR-CAT-003` — adventures/cataclysm/maledictus

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-002
- **Objective (specification):** Discover the bound structure (find_frosted_prison) and defeat Maledictus
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_maledictus
- **Unlock popup:** `COMPOSED` · `TACTICAL` — a prison built to hold something
- **Completion popup:** `PLEASED` · `REWARD` — the curse is broken
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

#### `OR-CAT-004` — adventures/cataclysm/monstrosity

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-003
- **Objective (specification):** Discover the bound structure (find_soul_black_smith) and defeat Netherite Monstrosity
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_monstrosity
- **Unlock popup:** `DARKENED` · `OMINOUS` — a forge that makes monstrous things
- **Completion popup:** `PLEASED` · `REWARD` — the warmachine is stopped
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

#### `OR-CAT-005` — adventures/cataclysm/leviathan

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-004
- **Objective (specification):** Discover the bound structure (find_sunken_city) and defeat The Leviathan
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_leviathan
- **Unlock popup:** `DARKENED` · `OMINOUS` — a predator in the sunken city
- **Completion popup:** `PLEASED` · `REWARD` — the sea is safer
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

#### `OR-CAT-006` — adventures/cataclysm/ignis

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-005
- **Objective (specification):** Discover the bound structure (find_burning_arena) and defeat Ignis; the Ignited Revenant gates the encounter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_ignis
- **Unlock popup:** `DARKENED` · `OMINOUS` — an arena that still burns
- **Completion popup:** `PLEASED` · `REWARD` — the fire is extinguished
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

#### `OR-CAT-007` — adventures/cataclysm/scylla

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-006
- **Objective (specification):** Discover the bound structure (find_acropolis) and defeat Scylla; the Clawdian gates the encounter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_scylla
- **Unlock popup:** `COMPOSED` · `TACTICAL` — an acropolis where it has no business being
- **Completion popup:** `PLEASED` · `REWARD` — the storm passes
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

#### `OR-CAT-008` — adventures/cataclysm/ender_guardian

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-007
- **Objective (specification):** Discover the bound structure (find_ruined_citadel) and defeat Ender Guardian; the Ender Golem gates the encounter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_ender_guardian
- **Unlock popup:** `DARKENED` · `OMINOUS` — a guardian behind the citadel
- **Completion popup:** `PLEASED` · `REWARD` — the last of the eight
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

#### `OR-CAT-009` — adventures/cataclysm/capstone

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-008
- **Objective (specification):** Defeat every major Cataclysm boss
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_all_bosses
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — one kill remains
- **Completion popup:** `RELISHING` · `MOCKING` — every major power the Cataclysm left behind is dead; the Elf is avenged by accident
- **Consequence:** Completes the Cataclysm Adventure; eligible major-Adventure ending fact (Q123)
- **Persistent fact:** overlord_reign:adventure/cataclysm_capstone_completed (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q123


### Ice & Fire

#### `OR-ICF-001` — ice_and_fire/dragon_mastery/bestiary

- **Role:** Dragon Mastery stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** OPEN - arc activation trigger not decided
- **Prerequisite:** —
- **Objective (specification):** Bestiary / research
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** iceandfire:iceandfire/bestiary
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — dragons studied before they are taken
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the research is done
- **Consequence:** Advances Dragon Mastery
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q048

#### `OR-ICF-002` — ice_and_fire/dragon_mastery/harvest

- **Role:** Dragon Mastery stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ICF-001
- **Objective (specification):** Kill a wild adult dragon and harvest materials
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** iceandfire:iceandfire/kill_if_dragon
- **Unlock popup:** `COMPOSED` · `TACTICAL` — a wild adult must be killed
- **Completion popup:** `PLEASED` · `REWARD` — materials taken
- **Consequence:** Advances Dragon Mastery
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize a prior dragon kill
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q048

#### `OR-ICF-003` — ice_and_fire/dragon_mastery/egg

- **Role:** Dragon Mastery stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ICF-002
- **Objective (specification):** Obtain an egg from a sufficiently ancient female
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** iceandfire:iceandfire/dragon_egg
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — an egg, stolen from something ancient
- **Completion popup:** `RELISHING` · `MOCKING` — theft from a dragon delights him
- **Consequence:** Advances Dragon Mastery
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q048

#### `OR-ICF-004` — ice_and_fire/dragon_mastery/bond

- **Role:** Dragon Mastery capstone
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ICF-003
- **Objective (specification):** Hatch, raise and bond with a dragon, ending with taking flight on it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:stat on time ridden while mounted on a tamed iceandfire dragon
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — a dragon of the Master's own
- **Completion popup:** `PLEASED` · `CEREMONIAL` — the Overlord flies
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
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Giblet is getting a dragon forge
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — installed, untested
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
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the forge must be proven
- **Completion popup:** `PLEASED` · `REWARD` — Giblet approves of a forge that works
- **Consequence:** Dragon Den operational
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q049, §7.1


### Civilization

#### `OR-CIV-001A` — civilizations/villagers/anchor

- **Role:** Lock the villagers anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Spree / Villagers anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — a human settlement, suspicious rather than hostile (Q-023)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-001B` — civilizations/villagers/state

- **Role:** Resolve the villagers anchor. Legal states: NEUTRAL / SUBJUGATED / HOSTILE (Q-026: DESTROYED is a local anchor outcome, not a civilization state)
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-001A
- **Objective (specification):** Resolve the Spree / Villagers civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED · HOSTILE TACTICAL/COMPOSED · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/villagers/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / HOSTILE (Q-026: DESTROYED is a local anchor outcome, not a civilization state)
- **Source:** Q053-Q059

#### `OR-CIV-002A` — civilizations/illagers/anchor

- **Role:** Lock the illagers anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Illagers anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `COMPOSED` · `TACTICAL` — initially hostile; authority is established by force (17)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-002B` — civilizations/illagers/state

- **Role:** Resolve the illagers anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED, reached through an intermediate COWED phase (17)
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-002A
- **Objective (specification):** Resolve the Illagers civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED · DESTROYED MOCKING/RELISHING · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/illagers/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED, reached through an intermediate COWED phase (17)
- **Source:** Q198-Q200

#### `OR-CIV-003A` — civilizations/dwarves/anchor

- **Role:** Lock the dwarves anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Dwarves anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — a successor hold of the Golden Hills, reduced by Imperial anti-magic persecution (18)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-003B` — civilizations/dwarves/state

- **Role:** Resolve the dwarves anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-003A
- **Objective (specification):** Resolve the Dwarves civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED · DESTROYED MOCKING/RELISHING · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/dwarves/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q171, Q174-

#### `OR-CIV-004A` — civilizations/gnumus/anchor

- **Role:** Lock the gnumus anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Gnumus anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — Halflings transformed by Gluttony magic who no longer know it (19)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-004B` — civilizations/gnumus/state

- **Role:** Resolve the gnumus anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-004A
- **Objective (specification):** Resolve the Gnumus civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED · DESTROYED MOCKING/RELISHING · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/gnumus/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q174-Q176

#### `OR-CIV-005A` — civilizations/goblins/anchor

- **Role:** Lock the goblins anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Goblins anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `RELISHING` · `MOCKING` — Goblins and Minions call each other cousins; the claim is cultural, not established (20)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-005B` — civilizations/goblins/state

- **Role:** Resolve the goblins anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-005A
- **Objective (specification):** Resolve the Goblins civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED · DESTROYED MOCKING/RELISHING · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/goblins/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q177-Q180

#### `OR-CIV-006A` — civilizations/kobolds/anchor

- **Role:** Lock the kobolds anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Kobolds anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `RELISHING` · `MOCKING` — a Den of generally dim underground engineers (21)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-006B` — civilizations/kobolds/state

- **Role:** Resolve the kobolds anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-006A
- **Objective (specification):** Resolve the Kobolds civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED · DESTROYED MOCKING/RELISHING · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/kobolds/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q181-Q184

#### `OR-CIV-007A` — civilizations/ribbits/anchor

- **Role:** Lock the ribbits anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Ribbits anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `RELISHING` · `MOCKING` — a cozy musical village that has never threatened anyone (22)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-007B` — civilizations/ribbits/state

- **Role:** Resolve the ribbits anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-007A
- **Objective (specification):** Resolve the Ribbits civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED MOCKING/RELISHING · DESTROYED MOCKING/RELISHING — the contrast is the point (22) · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/ribbits/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q185-Q188

#### `OR-CIV-008A` — civilizations/sea_dwellers/anchor

- **Role:** Lock the sea_dwellers anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Sea Dwellers anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — an independent trading village under the water (23)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-008B` — civilizations/sea_dwellers/state

- **Role:** Resolve the sea_dwellers anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-008A
- **Objective (specification):** Resolve the Sea Dwellers civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED · DESTROYED MOCKING/RELISHING · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/sea_dwellers/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q189-Q192

#### `OR-CIV-009A` — civilizations/piglins/anchor

- **Role:** Lock the piglins anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Piglins anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `DARKENED` · `REACTIVE` — Piglins descend from a Minion and a pig; it is true and Gnarl would rather not discuss it (24)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-009B` — civilizations/piglins/state

- **Role:** Resolve the piglins anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-009A
- **Objective (specification):** Resolve the Piglins civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED — supremacy restored in his own realm · DESTROYED MOCKING/RELISHING · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/piglins/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q193-Q195

#### `OR-CIV-010A` — civilizations/umvuthana/anchor

- **Role:** Lock the umvuthana anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Umvuthana anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `COMPOSED` · `TACTICAL` — a living creator-god and his Grove, risen in the space the Empire made by killing Elves (25)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

#### `OR-CIV-010B` — civilizations/umvuthana/state

- **Role:** Resolve the umvuthana anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-010A
- **Objective (specification):** Resolve the Umvuthana civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED MOCKING/RELISHING — a god humiliated before his worshippers (25) · DESTROYED REWARD/PLEASED · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/umvuthana/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q196-Q197

#### `OR-CIV-011A` — civilizations/myrmex/anchor

- **Role:** Lock the myrmex anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Myrmex anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — a hive polity with a Queen and a measurable opinion of the Overlord
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the provider quests (Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q052, §2, §12

#### `OR-CIV-011B` — civilizations/myrmex/state

- **Role:** Resolve the myrmex anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-011A
- **Objective (specification):** Resolve the Myrmex civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED · DESTROYED MOCKING/RELISHING · NEUTRAL REACTIVE/DARKENED
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
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** After both the Dwarven and Kobold anchors exist
- **Prerequisite:** OR-CIV-003A + OR-CIV-006A
- **Objective (specification):** Visit each bound polity and complete one source-compatible material/service interaction with each side, then present the paired evidence
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Both interactions complete
- **Unlock popup:** `RELISHING` · `MOCKING` — two polities that can be played against each other
- **Completion popup:** `RELISHING` · `MOCKING` — the rivalry is exploitable
- **Consequence:** Writes RIVALRY_INVESTIGATED and exposes the four resolution routes
- **Persistent fact:** RIVALRY_INVESTIGATED
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q172

#### `OR-RIV-002` — rivalry/dwarf_kobold/favor_dwarves

- **Role:** Dwarf/Kobold rivalry resolution
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available after RIVALRY_INVESTIGATED; mutually exclusive with the other three (Q173)
- **Prerequisite:** OR-RIV-001
- **Objective (specification):** Complete a Dwarven forge/material commission that disadvantages the Kobold claim
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on dwarven_forge:dwarven_metal_ingot + dwarven_forge:rune as the commission output
- **Unlock popup:** `COMPOSED` · `CHOICE` — a side to back
- **Completion popup:** `RELISHING` · `MOCKING` — one claim ruined for the other
- **Consequence:** Writes a named rivalry fact that later civilization routes may inspect; does not set NEUTRAL/SUBJUGATED/DESTROYED directly (Q173)
- **Persistent fact:** overlord_reign:rivalry/favoured_dwarves (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q173

#### `OR-RIV-003` — rivalry/dwarf_kobold/favor_kobolds

- **Role:** Dwarf/Kobold rivalry resolution
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available after RIVALRY_INVESTIGATED; mutually exclusive with the other three (Q173)
- **Prerequisite:** OR-RIV-001
- **Objective (specification):** Complete a Kobold resource-security commission that disadvantages the Dwarven claim: craft and supply Kobold-pattern iron tooling to the Den.
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_craft_stat on kobolds:kobold_iron_pickaxe (and/or the other kobold_iron_* tools)
- **Unlock popup:** `COMPOSED` · `CHOICE` — a side to back
- **Completion popup:** `RELISHING` · `MOCKING` — one claim ruined for the other
- **Consequence:** Writes a named rivalry fact that later civilization routes may inspect; does not set NEUTRAL/SUBJUGATED/DESTROYED directly (Q173)
- **Persistent fact:** overlord_reign:rivalry/favoured_kobolds (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q173; REWORDED 2026-09-18. The original named an Engineer commission, but the Engineer returns vanilla redstone and every Kobold provider role is already consumed by the civilization arc (Q181-Q184); the Prospector book in particular is claimed by Q183. The reword moves the proof to the supply side, which suits the branch: the Overlord creating dependency rather than receiving a favour. Kobold-pattern tooling is mod-specific, craftable, and claimed by nothing else.

#### `OR-RIV-004` — rivalry/dwarf_kobold/truce

- **Role:** Dwarf/Kobold rivalry resolution
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available after RIVALRY_INVESTIGATED; mutually exclusive with the other three (Q173)
- **Prerequisite:** OR-RIV-001
- **Objective (specification):** Impose a forced working arrangement / truce
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:or of the two commission item turn-ins, one to each polity — forced arrangement proved by serving both
- **Unlock popup:** `COMPOSED` · `CHOICE` — the dull option
- **Completion popup:** `DARKENED` · `REACTIVE` — both sides intact; Gnarl evaluates the Master and is unimpressed
- **Consequence:** Writes a named rivalry fact that later civilization routes may inspect; does not set NEUTRAL/SUBJUGATED/DESTROYED directly (Q173)
- **Persistent fact:** overlord_reign:rivalry/forced_truce (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q173

#### `OR-RIV-005` — rivalry/dwarf_kobold/exploit

- **Role:** Dwarf/Kobold rivalry resolution
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available after RIVALRY_INVESTIGATED; mutually exclusive with the other three (Q173)
- **Prerequisite:** OR-RIV-001
- **Objective (specification):** Exploit or escalate the dispute for Overlord advantage
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain — take the disputed material from both sides without resolving the claim
- **Unlock popup:** `COMPOSED` · `CHOICE` — the dispute is worth more unresolved
- **Completion popup:** `PLEASED` · `REWARD` — turned entirely to the Master's advantage
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

Provider quests write a further 43 facts, one per quest, listed in §7.5.

---

## 10. Deliberately out of scope

These are **not** omissions. A builder must not fill them by inference.

### 10.1 Player-facing text

Quest names, titles, descriptions and every `Order (presenter voice)` line are authored in the campaign-writing pass, from `Overlord_Lore_and_Canon`, the presenter register vocabularies in §5.3 and the reactions recorded per quest. Names are decided by their presenter — a Gnarl quest is named as Gnarl would name it (Q242, Overlord decision).

### 10.2 Ramblings — CLOSED

**The Rambling catalog is closed and approved: 537 Ramblings across the eight presenters.**

Derived from a surface of **767 advancements across 39 advancement-bearing sources**, including 57
hidden advancements, which are in scope because hidden usually means secret rather than technical.
230 rows were excluded: 85 single-item acquisition ticks, 55 claimed by a V5 quest detector under the
one-consumer rule, 34 reserved by approved objectives, 37 repetitive family members, 12 mechanic
explanations, 7 trivial or detector-only.

#### 10.2.1 Writing rule — Ramblings are not all isolated thoughts

**Overlord decision, 2026-09-18.**

A Rambling should reference the campaign, its beats and quests, or a previous Rambling **when
appropriate**. Most will still be isolated thoughts, because most events need no context. The rule is
not that every Rambling must call back; it is that a Rambling which *would* be better for knowing what
has already happened should be written that way rather than as a standalone remark.

This makes the presenter feel like a character who has been present for the reign rather than a
commentary function firing on triggers.

#### 10.2.2 Technical consequence

**Fact predicates on Rambling definitions.** A Rambling that references campaign state must be able to
read it. Rambling definitions therefore require `required_facts` and `forbidden_facts`, the same
surface the NPC provider system already exposes. The registry exists: 31 quest facts (§9) plus 43
provider facts (§7.5).

**Per-Rambling shown-state.** A Rambling that references a previous Rambling must know whether that
earlier one fired. `GnarlCommentaryState` tracks `successShown` and `postShown` **per quest**; no
equivalent exists for Ramblings. Rambling state must record which Ramblings have been delivered, per
player, durably.

Without both, a contextual Rambling cannot be written.


#### 10.2.3 The approved Rambling catalog

All **537** approved Ramblings, grouped by presenter and sorted by source mod. Each row is a native advancement that no quest detector claims.

**Visual state is assigned per row** (Q247b), grounded in the presenter's own character and register vocabulary rather than derived from one rule.

**No Rambling uses `STRICKEN`.** That state is for the mask coming off, and a Rambling reacts to something the Overlord has already done and survived — there is nothing left in it to break him. It appears on three quest popups only (§5.2).

Rambling **text** remains unauthored and belongs to the writing pass, under §10.2.1.

| Presenter | Ramblings | COMPOSED | PLEASED | RELISHING | DARKENED |
| --- | --- | --- | --- | --- | --- |
| Gnarl | 265 | 160 | 59 | 46 | 0 |
| Gristle | 111 | 0 | 89 | 22 | 0 |
| Quaver | 74 | 0 | 6 | 68 | 0 |
| Historian | 21 | 16 | 4 | 0 | 1 |
| Lestat | 19 | 0 | 3 | 7 | 9 |
| Grubbison Jr | 17 | 0 | 3 | 14 | 0 |
| Mortis | 15 | 13 | 0 | 2 | 0 |
| Giblet the Sixth | 15 | 0 | 6 | 9 | 0 |
| **Total** | **537** | 189 | 170 | 168 | 10 |

##### Gnarl — 265

| Advancement | Native title | State | Why that state |
| --- | --- | --- | --- |
| `display_case:collapse` | The collapse | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `cataclysm:kill_clawdian` | Not so Shrimple Now | `COMPOSED` | administrative acknowledgement |
| `cataclysm:kill_ender_golem` | Palette Swap | `COMPOSED` | administrative acknowledgement |
| `cataclysm:kill_revenant` | Imperfect thing | `COMPOSED` | administrative acknowledgement |
| `cataclysm:root` | Cataclysm | `COMPOSED` | administrative acknowledgement |
| `rats:ball_of_filth` | Cleanliness is Close to Godliness! | `COMPOSED` | administrative acknowledgement |
| `rats:black_death` | Dance Macabre! | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `rats:piper` | Pay the Piper | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `rats:plague` | Down with the Sickness! | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `rats:plague_cure` | The Medieval Treatment | `COMPOSED` | observational; a place noted |
| `rats:plague_doctor` | I AM THE CURE! | `COMPOSED` | observational; a place noted |
| `rats:rat_cage` | Despite All My Rage... | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `rats:rat_cage_decoration` | A Gilded Cage | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `rats:rat_crafting_table` | Rat Auto-Crafting | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `rats:rat_upgrade_crafting` | Crafty Rats! | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `rats:rat_upgrade_flight` | Essentially Pigeons | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `rats:rat_upgrade_god` | Whats a Man to a King? | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `rats:rat_upgrade_idol` | False Prophet | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `rats:rat_upgrade_pickpocket` | Talk of the Town | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `rats:rat_upgrade_platter` | Carry Weight 64 | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `rats:rat_upgrade_warrior` | Rat Warrior Clan | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `rats:ratbow_essence` | In Stunning Technicolor! | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `rats:root` | Rats | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `graveyard:graveyard/black_bone_staff` | Staff of Nortoq'gul | `COMPOSED` | administrative acknowledgement |
| `graveyard:graveyard/crypt` | Into Darkness | `COMPOSED` | observational; a place noted |
| `graveyard:graveyard/cyan_bone_staff` | Staff of Mynakta'gul | `COMPOSED` | administrative acknowledgement |
| `graveyard:graveyard/dead_tree` | Hanging Tree | `COMPOSED` | observational; a place noted |
| `graveyard:graveyard/giant_mushroom` | Corrupting the Paradise | `COMPOSED` | observational; a place noted |
| `graveyard:graveyard/haunted_house` | Invitation to the Tea Party | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `graveyard:graveyard/kill_acolyte_bone_dagger` | Turning the tables | `COMPOSED` | administrative acknowledgement |
| `graveyard:graveyard/kill_while_blinded` | Peekaboo, I (still) see you! | `COMPOSED` | administrative acknowledgement |
| `graveyard:graveyard/lich_prison` | Cursed Island | `COMPOSED` | observational; a place noted |
| `graveyard:graveyard/purple_bone_staff` | Staff of Xaphnok'gul | `COMPOSED` | administrative acknowledgement |
| `graveyard:graveyard/red_bone_staff` | Staff of Azrata'gul | `COMPOSED` | administrative acknowledgement |
| `graveyard:graveyard/white_bone_staff` | Staff of Zatraq'gul | `COMPOSED` | administrative acknowledgement |
| `graveyard:graveyard/wraith_dim_light` | Who turned the lights off? | `COMPOSED` | administrative acknowledgement |
| `betterdungeons:all_dungeons` | Professional Dungeoneer | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `betterdungeons:root` | YUNG's Better Dungeons | `COMPOSED` | administrative acknowledgement |
| `betterdungeons:skeleton_dungeon` | A Bone to Pick | `COMPOSED` | observational; a place noted |
| `betterdungeons:small_dungeon` | Quite the Renovation | `COMPOSED` | observational; a place noted |
| `betterdungeons:zombie_dungeon` | When in Rome | `COMPOSED` | administrative acknowledgement |
| `minecraft:nether/find_fortress` | advancements.nether.find_fortress.title | `COMPOSED` | administrative acknowledgement |
| `artifacts:chest_slayer` | Chest Slayer | `COMPOSED` | administrative acknowledgement |
| `biomancy:biomancy/bio_injector` | Injections | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `biomancy:biomancy/craft_primal_core` | Primal Crafting | `COMPOSED` | administrative acknowledgement |
| `biomancy:biomancy/decomposer` | Munch & Crunch | `COMPOSED` | administrative acknowledgement |
| `biomancy:biomancy/exotic_compounds` | Exotic Bio-Alchemy | `COMPOSED` | administrative acknowledgement |
| `biomancy:biomancy/genetic_compounds` | Genetic Bio-Alchemy | `COMPOSED` | administrative acknowledgement |
| `biomancy:biomancy/healing_activator_sacrifice` | Healing Activator | `COMPOSED` | administrative acknowledgement |
| `biomancy:biomancy/nether_star_sacrifice` | Superior Sacrifice | `RELISHING` | grotesque activity treated as routine management (09 §3) |
| `biomancy:biomancy/organ_trader` | Organ Trader | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `biomancy:biomancy/organic_compounds` | Organic Bio-Alchemy | `COMPOSED` | administrative acknowledgement |
| `biomancy:biomancy/poacher` | Rare Animal Poacher | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `biomancy:biomancy/primal_vision` | Primal Vision | `COMPOSED` | administrative acknowledgement |
| `biomancy:biomancy/raw_meat_sacrifice` | Tartar Delight | `COMPOSED` | administrative acknowledgement |
| `block_factorys_bosses:die_boss` | Bosses' Rise | `PLEASED` | a rival power destroyed |
| `born_in_chaos_v1:bonk` | Bonk! | `COMPOSED` | administrative acknowledgement |
| `born_in_chaos_v1:cultural_attribute` | Cultural Attribute | `COMPOSED` | administrative acknowledgement |
| `born_in_chaos_v1:explosive_temper` | Explosive Temper | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `born_in_chaos_v1:good_demoman` | Good Demoman | `PLEASED` | a rival power destroyed |
| `born_in_chaos_v1:guidetothenextworld` | The day of the Dead | `PLEASED` | a rival power destroyed |
| `born_in_chaos_v1:hat_for_dark_deeds` | Hat for dark affairs | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `born_in_chaos_v1:infernal_hatman` | Infernal Musketeer | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `born_in_chaos_v1:infernal_medicine` | Infernal Medicine | `COMPOSED` | administrative acknowledgement |
| `born_in_chaos_v1:junior_summoner` | Junior Summoner | `PLEASED` | a rival power destroyed |
| `born_in_chaos_v1:lifeturnedouttobeacomedy` | Life turned out to be a comedy | `PLEASED` | a rival power destroyed |
| `born_in_chaos_v1:mr_decoction` | Mr. Decoction | `COMPOSED` | administrative acknowledgement |
| `born_in_chaos_v1:my_size` | My Size! | `COMPOSED` | administrative acknowledgement |
| `born_in_chaos_v1:naughty_child` | Naughty Child | `COMPOSED` | administrative acknowledgement |
| `born_in_chaos_v1:one_explosion_is_good_two_is_better` | Double Bang | `COMPOSED` | administrative acknowledgement |
| `born_in_chaos_v1:protectedby_darkness` | Protected by Darkness | `COMPOSED` | administrative acknowledgement |
| `born_in_chaos_v1:shakeand_mix` | Shake and Mix | `COMPOSED` | administrative acknowledgement |
| `born_in_chaos_v1:spider_warlord` | Spider Warlord | `COMPOSED` | administrative acknowledgement |
| `born_in_chaos_v1:sweet_battle` | Sweet Battle | `COMPOSED` | administrative acknowledgement |
| `born_in_chaos_v1:the_wind_darkening` | The Wind Darkening | `PLEASED` | a rival power destroyed |
| `born_in_chaos_v1:transmutalogy` | Transmutalogy | `COMPOSED` | administrative acknowledgement |
| `born_in_chaos_v1:yoursatietywillbe_eternal` | Your satiety will be Eternal! | `COMPOSED` | administrative acknowledgement |
| `eidolon:cure_zombie` | Curse Lifted | `COMPOSED` | administrative acknowledgement |
| `eidolon:enthrall_undead` | A New Servant | `COMPOSED` | administrative acknowledgement |
| `eidolon:flame_spell` | Chant of Flames | `COMPOSED` | administrative acknowledgement |
| `eidolon:frost_spell` | Call of Winter | `COMPOSED` | administrative acknowledgement |
| `eidolon:holy_symbol` | Blessed with Light | `COMPOSED` | administrative acknowledgement |
| `eidolon:incense` | A Humble Offering | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `eidolon:lay_on_hands` | Healing Touch | `COMPOSED` | administrative acknowledgement |
| `eidolon:root` | Your Guide to the Supernatural | `COMPOSED` | administrative acknowledgement |
| `eidolon:smite_undead` | The light's Fury | `COMPOSED` | administrative acknowledgement |
| `eidolon:unholy_symbol` | Marked with Darkness | `COMPOSED` | administrative acknowledgement |
| `eidolon:villager_sacrifice` | A Greater Sacrifice | `RELISHING` | grotesque activity treated as routine management (09 §3) |
| `eidolon:zombify` | A New Recruit for the Dark | `COMPOSED` | administrative acknowledgement |
| `dungeonsdelight:main/break_bubblegunk` | ...And I'm All Out of Gum | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `dungeonsdelight:main/eat_bloody_mary` | Bloody Mary Challenge | `COMPOSED` | administrative acknowledgement |
| `dungeonsdelight:main/feed_wormouth` | Symbiosis | `COMPOSED` | administrative acknowledgement |
| `dungeonsdelight:main/get_sculk_polyp` | Apple of the Earth | `COMPOSED` | administrative acknowledgement |
| `dungeonsdelight:main/get_slime_noodles` | Creepy Pasta | `COMPOSED` | administrative acknowledgement |
| `dungeonsdelight:main/get_stained_scrap` | Heavy Metal | `COMPOSED` | administrative acknowledgement |
| `dungeonsdelight:main/get_stained_weapon` | A Slice of Life | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `dungeonsdelight:main/obtain_exudation` | Evil Up | `RELISHING` | grotesque activity treated as routine management (09 §3) |
| `dungeonsdelight:main/place_embedded_eggs` | Won’t Take a Century | `COMPOSED` | administrative acknowledgement |
| `dungeonsdelight:main/place_monster_pot` | Delicious in a Dungeon | `COMPOSED` | administrative acknowledgement |
| `dungeonsdelight:main/place_rotbulb_crop` | Corpsebloom | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `dungeonsdelight:main/root` | Dungeon's Delight | `COMPOSED` | administrative acknowledgement |
| `dungeonsdelight:main/use_cleaver` | Heaven Pierce Her | `COMPOSED` | administrative acknowledgement |
| `dungeonsdelight:main/use_gunk_arrow` | Air Pollution | `COMPOSED` | administrative acknowledgement |
| `dungeonsdelight:main/use_gunk_arrow_on_monster_yam` | Septic Tank | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `gnumus:fatty_demon` | Fatty Demon | `COMPOSED` | administrative acknowledgement |
| `gnumus:vintage_rifleman` | Vintage Rifleman | `COMPOSED` | administrative acknowledgement |
| `gnumus:wellfed_warrior` | Well-fed Warrior | `COMPOSED` | administrative acknowledgement |
| `goblins_tyranny:bag_success` | Mystery Bag | `COMPOSED` | administrative acknowledgement |
| `goblins_tyranny:barrel_success` | Kinda Sus | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `goblins_tyranny:glitteron_armor_success` | Everyone should Wear an Armor | `COMPOSED` | administrative acknowledgement |
| `goblins_tyranny:skybound_success` | To the Sky | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `minecraft:adventure/recruit_guard` | We Want You, as a New Recruit! | `COMPOSED` | administrative acknowledgement |
| `iceandfire:iceandfire/dragonbone_flaming_sword` | A Flaming Sword | `COMPOSED` | administrative acknowledgement |
| `iceandfire:iceandfire/dragonsteel_weapon` | Draconic Evolution | `COMPOSED` | administrative acknowledgement |
| `iceandfire:iceandfire/kill_cyclops` | My Name is Nobody | `PLEASED` | a rival power destroyed |
| `iceandfire:iceandfire/kill_ghost` | Who You Gonna Call? | `PLEASED` | a rival power destroyed |
| `iceandfire:iceandfire/kill_hydra` | The Twelve Labors of Hercules | `PLEASED` | a rival power destroyed |
| `iceandfire:iceandfire/kill_sea_serpent` | Sea? Not a Problem | `PLEASED` | a rival power destroyed |
| `iceandfire:iceandfire/pixie_wand` | Where Is My Mind? | `COMPOSED` | administrative acknowledgement |
| `iceandfire:iceandfire/root` | Ice and Fire | `COMPOSED` | observational; a place noted |
| `iceandfire:iceandfire/tame_amphithere` | Jungle Fliers | `COMPOSED` | administrative acknowledgement |
| `iceandfire:iceandfire/tame_cockatrice` | Stay Out of my Peripheral! | `COMPOSED` | administrative acknowledgement |
| `iceandfire:iceandfire/tame_hippocampus` | Free Willy | `COMPOSED` | administrative acknowledgement |
| `iceandfire:iceandfire/tame_hippogryph` | Buck Beak | `COMPOSED` | administrative acknowledgement |
| `iceandfire:iceandfire/tame_pixie` | Here Comes Your Man | `COMPOSED` | administrative acknowledgement |
| `iceandfire:iceandfire/use_tide_trident` | Poseidon, God of the Seas | `COMPOSED` | administrative acknowledgement |
| `irons_spellbooks:irons_spellbooks/enter_catacombs` | Dungeon Delving | `COMPOSED` | observational; a place noted |
| `irons_spellbooks:irons_spellbooks/ink_legendary` | Premium! | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `irons_spellbooks:irons_spellbooks/ink_root` | Tools of the Trade | `COMPOSED` | administrative acknowledgement |
| `irons_spellbooks:irons_spellbooks/make_wayward_compass` | And so a Quest Begins! | `COMPOSED` | observational; a place noted |
| `irons_spellbooks:irons_spellbooks/root` | Iron's Spells 'n Spellbooks | `COMPOSED` | administrative acknowledgement |
| `irons_spellbooks:irons_spellbooks/spell_book_dragon` | Elder Power | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `irons_spellbooks:irons_spellbooks/spell_book_evoker` | Grimoire of Evokation | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `irons_spellbooks:irons_spellbooks/spell_book_frostbranded` | Cold to the Touch | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `irons_spellbooks:irons_spellbooks/staff_artificer` | Quite the Walking Stick | `COMPOSED` | administrative acknowledgement |
| `irons_spellbooks:irons_spellbooks/staff_blood_staff` | Staff of Blood | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `irons_spellbooks:irons_spellbooks/staff_ice` | Mournful Magic | `COMPOSED` | administrative acknowledgement |
| `irons_spellbooks:irons_spellbooks/staff_lightning_rod` | Shocking Discovery | `COMPOSED` | administrative acknowledgement |
| `irons_spellbooks:irons_spellbooks/staff_root` | No Longer Short-Staffed | `COMPOSED` | administrative acknowledgement |
| `irons_spellbooks:irons_spellbooks/steal_from_wizard` | A Fool's Folly | `COMPOSED` | administrative acknowledgement |
| `knightquest:knightquest` | Knight Quest | `COMPOSED` | administrative acknowledgement |
| `minecraft:adventure/adventuring_time` | advancements.adventure.adventuring_time.title | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `mowziesmobs:root` | Mowzie's Mobs | `COMPOSED` | administrative acknowledgement |
| `mowziesmobs:sculptor_failure` | No One Jumps for the Beef | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `mowziesmobs:sneak_grove` | Birds of a Feather | `COMPOSED` | administrative acknowledgement |
| `mowziesmobs:steal_ice_crystal` | Big Brain™ Time | `COMPOSED` | administrative acknowledgement |
| `mowziesmobs:suns_blessing` | Heliomancer | `COMPOSED` | administrative acknowledgement |
| `netherdepthsupgrade:main/filled_soulsucker_bucket` | Cleaning! | `COMPOSED` | administrative acknowledgement |
| `netherdepthsupgrade:main/root` | Nether Depths Upgrade | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `netherdepthsupgrade:main/turn_wet_lava_sponge` | Dry Lava Sponge? | `COMPOSED` | administrative acknowledgement |
| `rottencreatures:kill_all_rot` | Purification! | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `rottencreatures:root` | Rotten to the Core | `RELISHING` | grotesque activity treated as routine management (09 §3) |
| `savage_and_ravage:adventure/mask_of_dishonesty` | A Little Tricking | `COMPOSED` | administrative acknowledgement |
| `supplementaries:adventure/globe` | You Spin Me Right Round Baby | `COMPOSED` | administrative acknowledgement |
| `supplementaries:adventure/way_sign` | This Must be a Sign! | `COMPOSED` | observational; a place noted |
| `supplementaries:husbandry/turn_table` | How the Turn Tables | `COMPOSED` | administrative acknowledgement |
| `supplementaries:husbandry/wrench` | Engineer Gaming | `COMPOSED` | administrative acknowledgement |
| `supplementaries:story/unenchanter` | Why Would You do That!? | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `takesapillage:bastille` | Eh-eh-oh, eh-oh | `COMPOSED` | observational; a place noted |
| `takesapillage:root` | It Takes A Pillage | `COMPOSED` | observational; a place noted |
| `tameablebeasts:asphalt` | Lets Build Some Roads | `COMPOSED` | administrative acknowledgement |
| `tameablebeasts:beetle_elytra` | Why Not Have Both? | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `tameablebeasts:big_bird_bait` | Lets Bait Some Birds | `RELISHING` | grotesque activity treated as routine management (09 §3) |
| `tameablebeasts:big_iron_hoe` | It's Not For Gardening | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `tameablebeasts:biker_helmet` | Wear Protection | `COMPOSED` | administrative acknowledgement |
| `tameablebeasts:brush_a_beast` | Was Specting Ticks This Is Far Better | `COMPOSED` | administrative acknowledgement |
| `tameablebeasts:chikote` | Not A Chocobo | `COMPOSED` | administrative acknowledgement |
| `tameablebeasts:egg_rests` | Not Making An Omelette With This One | `COMPOSED` | administrative acknowledgement |
| `tameablebeasts:flying_helmet` | Got A Bug In My Eye | `COMPOSED` | administrative acknowledgement |
| `tameablebeasts:fur_golem` | Easter Is Saved | `COMPOSED` | administrative acknowledgement |
| `tameablebeasts:metal_beetle` | Chrome Beetle | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `tameablebeasts:purple_allay` | Different Kind Of Allay | `COMPOSED` | observational; a place noted |
| `tameablebeasts:quetzal_stand` | Public Transport | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `tameablebeasts:root` | Tameable Beasts | `COMPOSED` | administrative acknowledgement |
| `conjurer_illager:find_theatre` | It's Showtime | `COMPOSED` | observational; a place noted |
| `the_bumblezone:armor/bumble_bee_chestplate_max_flight` | It's a Bird! It's a Plane! No it's... | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:armor/carpenter_bee_boots_mined_blocks` | Power Thighs! | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:armor/carpenter_bee_boots_wall_hanging` | Floor is lava! | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:armor/craft_flower_headwear` | BE A FLOWER! | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:armor/flower_headwear_wrath_structure` | Beeing Curious | `COMPOSED` | observational; a place noted |
| `the_bumblezone:armor/stingless_bee_helmet_super_sight` | Compound Eyes | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `the_bumblezone:armor/warrior_bee` | The Bee Warrior Rises! | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `the_bumblezone:beds/sleep_in_bed` | Sweeter Dreams! | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:bees/variant_bee_name` | A Shiny Found! | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:candles/obtain_all_colored_super_candles` | Over the Rainbow! | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `the_bumblezone:candles/projectile_light_instant_potion_candle` | Activated my trap candle! | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:combs_and_wax/carve_ancient_wax` | Perfecting Design | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:combs_and_wax/carve_carvable_wax` | Bold and Brash! | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `the_bumblezone:combs_and_wax/carve_luminescent_wax` | Proper Orientation | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:combs_and_wax/comb_cutter_extra_drops` | Collecting More Rent From Bees | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:crystalline_flower/enchant_crystalline_flower` | Choosing Wisely | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:crystalline_flower/grow_crystalline_flower` | CONSUME CONSUME CONSUME | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:decor/extend_string_curtains` | Bee-Blocking Walls | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:decor/obtain_all_string_curtains` | Rainbow Curtains!!! | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `the_bumblezone:dirt_pellet/dirt_pellet_kill_phantom` | Sky Smackdown | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:dirt_pellet/dirt_pellet_return_to_sender` | A Dirty Return | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:effects/bee_hit_wrath_of_the_hive` | NOT THE BEEEEEES!!! | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:effects/getting_protection` | Winning Favors | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:effects/protection_of_the_hive_defense` | BEHOLD! My Bees! | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:honey_bucket/drink_royal_jelly_bottle` | Gaining Royal Powers | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:honey_bucket/honey_bucket_bee_grow` | Quick To Grow Up! | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:honey_bucket/royal_jelly_block_piston` | PULL! NO PUSH! | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:pollen_pile/brush_pollen_pollen_suspicious` | Strange Artifacts... | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:pollen_pile/hide_in_pollen_pile` | Tactical Pollen | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:pollen_puff/bee_drop_pollen_puff` | Bee Clean! | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:pollen_puff/pollen_puff_fireball` | Unbeelievably Ineffective! | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:pollen_puff/pollen_puff_mooshroom` | Mushrooms, flowers, same thing | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:pollen_puff/pollen_puff_panda` | Bless You Panda! | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:pollen_puff/pollen_puff_pollinate_bee` | A Beeball Homerun! | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:pollen_puff/pollen_puff_pollinate_tall_flower` | Beetastic Pollinator | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:rootmin/rootmin_flower_swap` | Floral Sharing | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:rootmin/rootmin_rainbow_name` | WHAT DOES IT MEAN?! | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:sentry_watcher/sentry_watcher_use_egg` | Personal Guardian | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:stinger_spear/obtain_stinger_spear` | A weapon to surpass... | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:stinger_spear/stinger_spear_long_range_kill` | A Worthy Javelin Throw | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:stinger_spear/stinger_spear_paralyze_boss` | Getting Favorable Odds | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:stinger_spear/stinger_spear_paralyzing` | Stopped in its Tracks! | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:stinger_spear/stinger_spear_poisoning` | Being the Bee! | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:structures/enter_cell_maze` | Lost in the Cells | `COMPOSED` | observational; a place noted |
| `the_bumblezone:structures/enter_garden` | The Gardener and the Bees | `COMPOSED` | observational; a place noted |
| `the_bumblezone:structures/enter_sempiternal_sanctum` | Ancient Origins | `COMPOSED` | observational; a place noted |
| `the_bumblezone:structures/enter_throne_pillar` | Royal Presence | `COMPOSED` | observational; a place noted |
| `the_bumblezone:sugar_water/consume_sugar_water_bottle` | Sugar Rush!!! | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:sugar_water/sugar_water_next_to_sugar_cane` | Soon... It'll All Be Sugar Canes! | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `the_bumblezone:teleportation/is_near_beehive` | Dimension of Bees awaits! | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:teleportation/teleport_out_of_bumblezone` | Dive Bombing Exit | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:teleportation/teleport_to_bumblezone_pearl` | Beenderman Intrusion! | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:teleportation/teleport_to_bumblezone_piston` | Honeydraulic Press | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:the_queens_desire/back_in_action` | Back in Action | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `the_bumblezone:the_queens_desire/crazy_trader` | Crazy Trader | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `the_bumblezone:the_queens_desire/flooding_the_housing_market` | Flooding the Housing Market | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `the_bumblezone:the_queens_desire/love_bees` | Love bees | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `the_bumblezone:the_queens_desire/poison_warfare` | Poison Warfare | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `the_bumblezone:the_queens_desire/pollen_fight` | Pollen Fight | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `the_bumblezone:the_queens_desire/the_crazy_florist` | The Crazy Florist | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `the_bumblezone:the_queens_desire/the_great_dragon_slayer` | The Great Dragon Slayer | `RELISHING` | the Overlord killed the Ender Dragon dressed as a bee; Gnarl will not let this go |
| `the_bumblezone:tools/bee_cannon_full` | You better beehave now! | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:tools/bee_stinger_paralysis` | Endless Stinging | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:tools/buzzing_briefcase_full` | What's this?... | `COMPOSED` | administrative acknowledgement |
| `the_bumblezone:tools/buzzing_briefcase_heal` | The Doctor is IN! | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `the_bumblezone:tools/buzzing_briefcase_release` | A Hive Superhero! | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `the_bumblezone:tools/cystal_cannon_full` | Extreme Crowd Control | `COMPOSED` | administrative acknowledgement |
| `twilightforest:arctic_armor_dyed` | Getting in Fashion | `COMPOSED` | administrative acknowledgement |
| `twilightforest:beanstalk` | Jack and the Beanstalk | `COMPOSED` | administrative acknowledgement |
| `twilightforest:break_glass_sword` | One Hit Wonder | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `twilightforest:experiment_115` | Mystery Meat? | `COMPOSED` | administrative acknowledgement |
| `twilightforest:experiment_115_self_replenishing` | Making a note: Huge Success! | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `twilightforest:fiery_set` | Gallons of Blood and Tears | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `twilightforest:ghast_trap` | Something Strange in Towerwood | `PLEASED` | a rival power destroyed |
| `twilightforest:hedge` | Bug Stomper | `PLEASED` | a rival power destroyed |
| `twilightforest:hill2` | What Was That Noise? | `PLEASED` | a rival power destroyed |
| `twilightforest:hill3` | I See Right Through You | `PLEASED` | a rival power destroyed |
| `twilightforest:hydra_chop` | Hydra Chop, Baby! | `COMPOSED` | administrative acknowledgement |
| `twilightforest:kill_cicada` | Shut | `COMPOSED` | administrative acknowledgement |
| `twilightforest:maze_map` | And Now, to Find the Exit | `COMPOSED` | observational; a place noted |
| `twilightforest:naga_armors` | Naga Armorer | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `twilightforest:ore_map` | How Can That Be Worth It? | `PLEASED` | pursued completion is worth acknowledging (Rambling authority §4) |
| `twilightforest:progress_troll` | I Wish For More Burning | `RELISHING` | contempt or delight at the situation (§6 humour patterns) |
| `twilightforest:progress_trophy_pedestal` | Trophied Champion | `COMPOSED` | administrative acknowledgement |
| `twilightforest:progress_yeti` | Alpha Fur | `COMPOSED` | administrative acknowledgement |
| `twilightforest:quest_ram` | Consummate Baaahs | `COMPOSED` | administrative acknowledgement |
| `twilightforest:root` | Twilight Forest | `COMPOSED` | observational; a place noted |
| `twilightforest:troll` | We Do a Little Trolling | `COMPOSED` | observational; a place noted |
| `twilightforest:twilight_hunter` | The Silence of the Forest | `COMPOSED` | administrative acknowledgement |
| `twilightforest:uncraft_uncrafting_table` | A Step too Far | `COMPOSED` | administrative acknowledgement |

##### Gristle — 111

| Advancement | Native title | State | Why that state |
| --- | --- | --- | --- |
| `farmersdelight:main/eat_nourishing_food` | Nourishing! | `PLEASED` | the estate is fed |
| `farmersdelight:main/get_fd_seed` | Crops of the Wild | `PLEASED` | the estate is fed |
| `farmersdelight:main/get_ham` | Wild Butcher | `RELISHING` | Gristle is not squeamish about ingredients |
| `farmersdelight:main/get_mushroom_colony` | Fungus Among Us | `PLEASED` | the estate is fed |
| `farmersdelight:main/get_rich_soil` | Plant Food | `PLEASED` | the estate is fed |
| `farmersdelight:main/harvest_ropelogged_tomato` | Tall-mato | `PLEASED` | the estate is fed |
| `farmersdelight:main/harvest_straw` | Grasping at Straws | `PLEASED` | the estate is fed |
| `farmersdelight:main/hit_raider_with_rotten_tomato` | Boo! Hiss! | `RELISHING` | Gristle is not squeamish about ingredients |
| `farmersdelight:main/master_chef` | Master Chef | `PLEASED` | a culinary pursuit completed |
| `farmersdelight:main/place_cooking_pot` | Dinner's Served! | `PLEASED` | the estate is fed |
| `farmersdelight:main/place_feast` | A Glorious Feast | `PLEASED` | the estate is fed |
| `farmersdelight:main/place_skillet` | Sizzling Hot! | `PLEASED` | the estate is fed |
| `farmersdelight:main/plant_all_crops` | Crop Rotation | `PLEASED` | a culinary pursuit completed |
| `farmersdelight:main/plant_rice` | Dipping Your Roots | `PLEASED` | the estate is fed |
| `farmersdelight:main/root` | Farmer's Delight | `PLEASED` | the estate is fed |
| `farmersdelight:main/use_cutting_board` | Watch Your Fingers | `PLEASED` | the estate is fed |
| `farmersdelight:main/use_skillet` | Portable Cooking | `PLEASED` | the estate is fed |
| `rats:cheese` | We've Forgotten the Crackers! | `PLEASED` | the estate is fed |
| `rats:contaminated_food` | Stop that Health Inspector! | `PLEASED` | the estate is fed |
| `rats:milk_cauldron` | Curdling Time | `PLEASED` | the estate is fed |
| `rats:rat_cooking` | Anyone Can Cook | `PLEASED` | a culinary pursuit completed |
| `rats:rat_upgrade_chef` | The Rat is the Cook! | `PLEASED` | the estate is fed |
| `rats:tame_rat` | An Unlikely Alliance | `PLEASED` | the estate is fed |
| `biomancy:biomancy/cooked_meat_sacrifice` | Cooked Meat Disrelish | `PLEASED` | the estate is fed |
| `biomancy:biomancy/digester` | Yummy Paste | `PLEASED` | a culinary pursuit completed |
| `biomancy:biomancy/greedy_butcher` | Greedy Butcher | `RELISHING` | Gristle is not squeamish about ingredients |
| `biomancy:biomancy/malignant_growth` | Malignant Growth | `RELISHING` | Gristle is not squeamish about ingredients |
| `biomancy:biomancy/primal_orifice` | Trypophobia? | `PLEASED` | a culinary pursuit completed |
| `born_in_chaos_v1:disgusting_lootbox` | Disgusting Lootbox | `PLEASED` | the estate is fed |
| `born_in_chaos_v1:horrorofthe_depths` | Horror of the Depths | `PLEASED` | the estate is fed |
| `farmers_spell:arcane_cocoa` | Condensed Cream | `RELISHING` | appetite satisfied; smug |
| `farmers_spell:blaze_scroll` | Pyromancer Career Path | `PLEASED` | the estate is fed |
| `farmers_spell:blood_tofu` | Coagulated Blood... | `RELISHING` | Gristle is not squeamish about ingredients |
| `farmers_spell:butter_hit` | Not a Corn Thrower | `PLEASED` | the estate is fed |
| `farmers_spell:drink_butterbeer` | Hogsmeade Buzz | `PLEASED` | the estate is fed |
| `farmers_spell:icebreaker_bread` | Titantic Bread | `PLEASED` | a culinary pursuit completed |
| `farmers_spell:ink_beer` | The Taste of Magic Truth? | `RELISHING` | appetite satisfied; smug |
| `farmers_spell:phantom_loot` | Wait, That's Important... | `PLEASED` | the estate is fed |
| `dungeonsdelight:main/all_dungeonsdelight_foods` | Meal of Champions | `PLEASED` | a culinary pursuit completed |
| `dungeonsdelight:main/eat_monster_food` | Ah, Dungeon Food | `PLEASED` | the estate is fed |
| `dungeonsdelight:main/eat_sniffer_food` | Is It Worth It? | `RELISHING` | appetite satisfied; smug |
| `gnumus:cheese_treat` | Cheese Treat | `PLEASED` | the estate is fed |
| `goblins_tyranny:blazing_liquor_success` | This is Fine ! | `RELISHING` | appetite satisfied; smug |
| `goblins_tyranny:deadly_liquor_success` | I Don't Feel so Good... | `RELISHING` | appetite satisfied; smug |
| `goblins_tyranny:goblin_liquor_success` | Sip by Sip | `RELISHING` | appetite satisfied; smug |
| `goblins_tyranny:goblins_meat_success` | Goblin Eater | `RELISHING` | appetite satisfied; smug |
| `goblins_tyranny:mushroom_success` | Seeing a Rainbow Road ? | `PLEASED` | a culinary pursuit completed |
| `ice_and_fire_delight:almost_4_elements` | Almost 4 elements | `RELISHING` | appetite satisfied; smug |
| `ice_and_fire_delight:center_of_weakness_adv` | Center of Weakness | `PLEASED` | the estate is fed |
| `ice_and_fire_delight:delicacy_adv` | Delicacy! | `PLEASED` | the estate is fed |
| `ice_and_fire_delight:dragon_minced_meat_adv` | Not so great beast | `RELISHING` | Gristle is not squeamish about ingredients |
| `ice_and_fire_delight:feel_like_a_dragon_adv` | Feel like a dragon! | `PLEASED` | the estate is fed |
| `ice_and_fire_delight:feel_spicy` | Feel spicy | `RELISHING` | appetite satisfied; smug |
| `ice_and_fire_delight:feel_the_power_adv` | Feel the power | `RELISHING` | appetite satisfied; smug |
| `ice_and_fire_delight:kill_pixie` | You are a monster! | `PLEASED` | the estate is fed |
| `ice_and_fire_delight:power_of_three_dragons_adv` | The Power of Three Dragons | `PLEASED` | the estate is fed |
| `ice_and_fire_delight:rip_dragon_adv` | Rest in peace Dragon | `PLEASED` | the estate is fed |
| `ice_and_fire_delight:root` | Ice and Fire Delight | `PLEASED` | the estate is fed |
| `ice_and_fire_delight:too_much_power` | Too Much Power | `PLEASED` | a culinary pursuit completed |
| `ice_and_fire_delight:you_have_been_spotted` | You've been spotted | `PLEASED` | the estate is fed |
| `iceandfire:iceandfire/dragon_meal` | Dragon Growth Hormone | `PLEASED` | the estate is fed |
| `iceandfire:iceandfire/jar_pixie` | Surfer Rosa | `PLEASED` | the estate is fed |
| `netherdepthsupgrade:main/lava_fishing_master` | Lava Fishing Master | `PLEASED` | a culinary pursuit completed |
| `fathoms:nautical/catch_all_fish` | Ol' Mariner | `PLEASED` | a culinary pursuit completed |
| `fathoms:nautical/cut_fish` | Off With Your Head! | `RELISHING` | Gristle is not squeamish about ingredients |
| `fathoms:nautical/obtain_nautilus_siphon` | Aquatic Automation | `PLEASED` | the estate is fed |
| `fathoms:nautical/wax_fish` | Mummification | `PLEASED` | the estate is fed |
| `rottencreatures:mr_freeze` | Damn you, Batman, For forcing my hand | `PLEASED` | the estate is fed |
| `supplementaries:husbandry/soap` | Tide Pod Challenge | `RELISHING` | appetite satisfied; smug |
| `supplementaries:husbandry/sus_stew` | When the Stew is Sus | `RELISHING` | appetite satisfied; smug |
| `supplementaries:nether/goblet` | Consume the Concoction Chalice | `RELISHING` | appetite satisfied; smug |
| `tameablebeasts:bird_bait_arrow` | Seed Bullet | `PLEASED` | the estate is fed |
| `tameablebeasts:frozen_fish` | Why is the fish not moving anymore? | `PLEASED` | the estate is fed |
| `tameablebeasts:grapteranodon` | Hey Just Like In Ark | `PLEASED` | the estate is fed |
| `tameablebeasts:ground_beetle` | Armored Fella | `PLEASED` | the estate is fed |
| `tameablebeasts:penguin` | Ice Fish Bucket Challenge | `RELISHING` | appetite satisfied; smug |
| `tameablebeasts:ptera_meal_arrow` | Meat Bullet | `PLEASED` | the estate is fed |
| `tameablebeasts:pteranodon_meal` | You Sure You Want That Kind Of Attention? | `PLEASED` | the estate is fed |
| `tameablebeasts:quetzalcoatlus` | Is That A Cloud? | `PLEASED` | the estate is fed |
| `tameablebeasts:racoon` | Remember That Time You Stole My Food? | `PLEASED` | the estate is fed |
| `tameablebeasts:shiny_beetle` | Flying Fella | `PLEASED` | the estate is fed |
| `the_bumblezone:armor/honey_bee_leggings_flower_pollen` | It's the Bee's Knees! | `PLEASED` | the estate is fed |
| `the_bumblezone:armor/honey_bee_leggings_pollen_removal` | Bathing time! | `PLEASED` | the estate is fed |
| `the_bumblezone:beehemoth/tamed_beehemoth` | Bee Movie But It's 300% Larger | `PLEASED` | the estate is fed |
| `the_bumblezone:effects/beenergized_maxed` | Speedy Buzzy Bees! | `PLEASED` | the estate is fed |
| `the_bumblezone:effects/brew_bee_soup` | Brewing Danger | `PLEASED` | the estate is fed |
| `the_bumblezone:effects/brew_bee_stinger` | Concentrated Toxins | `PLEASED` | the estate is fed |
| `the_bumblezone:effects/brew_glistering_honey_crystal_block` | Cheating Chances! | `PLEASED` | the estate is fed |
| `the_bumblezone:effects/consume_bee_soup` | A Stingy Delicacy | `PLEASED` | the estate is fed |
| `the_bumblezone:effects/food_removed_wrath_of_the_hive` | A Honey Offering Of Peace | `PLEASED` | the estate is fed |
| `the_bumblezone:honey_bucket/honey_bucket_bee_love` | Love Is A-Buzzing! | `PLEASED` | the estate is fed |
| `the_bumblezone:honey_bucket/honey_bucket_brood` | Bee Farmer | `PLEASED` | the estate is fed |
| `the_bumblezone:honey_crystal/cleanup_honey_web` | Clean Freak! | `PLEASED` | the estate is fed |
| `the_bumblezone:honey_crystal/cleanup_sticky_honey_residue` | A Stickier Situation! | `PLEASED` | the estate is fed |
| `the_bumblezone:honey_crystal/consume_honey_crystal_shards` | Teeth Shattering Snacks | `PLEASED` | the estate is fed |
| `the_bumblezone:honey_crystal/obtain_honey_crystal_block` | A Beeutiful Crystal! | `PLEASED` | the estate is fed |
| `the_bumblezone:honey_crystal/obtain_sticky_honey_redstone` | Thinking With Honey! | `PLEASED` | the estate is fed |
| `the_bumblezone:honey_crystal/obtain_sticky_honey_residue` | Honey Recycler | `PLEASED` | the estate is fed |
| `the_bumblezone:honey_crystal/trigger_redstone_honey_web` | The Power Of Sticky! | `PLEASED` | the estate is fed |
| `the_bumblezone:honey_crystal_shield/honey_crystal_shield_block_ineffectively` | Unblockable Offense Won! | `PLEASED` | the estate is fed |
| `the_bumblezone:honey_slime/honey_slime_creation` | The Honey Is Alive! | `PLEASED` | the estate is fed |
| `the_bumblezone:honey_slime/honey_slime_harvest` | Who Needs Bees??? | `PLEASED` | the estate is fed |
| `the_bumblezone:the_queens_desire/honey_drunk` | Honey Drunk | `PLEASED` | a culinary pursuit completed |
| `the_bumblezone:the_queens_desire/hungry_hungry_bees` | Hungry Hungry Bees | `PLEASED` | a culinary pursuit completed |
| `the_bumblezone:the_queens_desire/slimy_mitosis` | Slimy Mitosis | `PLEASED` | a culinary pursuit completed |
| `the_bumblezone:tools/craft_honey_compass` | Is this even still a compass..? | `PLEASED` | the estate is fed |
| `the_bumblezone:tools/fish_rare_loot` | Beesistent Fishing | `PLEASED` | a culinary pursuit completed |
| `the_bumblezone:tools/honey_compass_use` | Dora the Beexplorer | `PLEASED` | the estate is fed |
| `twilightforest:full_mettle_alchemist` | Full Mettle Alchemist | `PLEASED` | a culinary pursuit completed |
| `twilightforest:progress_labyrinth` | Mighty Stroganoff | `RELISHING` | appetite satisfied; smug |
| `twilightforest:twilight_dinner` | We Dine At Eternal Sundown | `PLEASED` | a culinary pursuit completed |

##### Quaver — 74

| Advancement | Native title | State | Why that state |
| --- | --- | --- | --- |
| `rats:all_hats` | Grand Hat Collector | `PLEASED` | PROCLAMATION — a deed grand enough for a formal style, sung straight |
| `rats:defeat_black_death` | Do Not Go Quietly Into the Night | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `rats:defeat_rat_king` | King of the World! | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `rats:rat_music_disc` | Rat Tunes | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `biomancy:biomancy/cat_killer` | Kitty Cat Killer | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `biomancy:biomancy/predator_killer` | Predator Killer | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `biomancy:biomancy/raw_meat_collection` | Collector of Raw Meats | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `block_factorys_bosses:kill_boss_under_minute` | Overclocker | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `block_factorys_bosses:no_hit_kraken` | Wraith of the Tide | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `block_factorys_bosses:no_hit_sandworm` | Desert Power | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `block_factorys_bosses:no_hit_underworld_knight` | Beyond Death | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `block_factorys_bosses:no_hit_yeti` | King of the Hill | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:arachnophobes_nightmare` | Arachnophobe's Nightmare | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:big_boy` | Big Boy | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:chaos_knight` | Chaos Knight | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:charm_collector` | Charm Collector | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:dark_creator` | Dark Creator | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:dismantledto_bones` | Senior Summoner | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:excessive_fly_swatter` | Excessive Fly Swatter | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:exorcism` | Exorcism | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:fighting_nightmares` | Fighting Nightmares | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:hatman` | Hatman | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:horror_underthe_mantle` | Horror Under the Mantle | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:pumpkin_lord` | Last Night | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:pumpkin_sir` | Pumpkin Sir | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:shell_warrior` | Shell Warrior | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:spruce_cowboyinthe_moonlight` | Spruce Cowboy In The Moonlight | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:squireofthe_dark_lord` | Squire of the Dark Lord | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:symphonyof_chaos` | Symphony of Chaos | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:trickor_treat` | Trick or Treat | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:unitywith_darknessachievement` | Unity with Darkness | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `born_in_chaos_v1:wrong_santa` | Wrong Santa | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `dungeonsdelight:main/all_knife_mob_drops` | Gastrocryptozoologist | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `dungeonsdelight:main/all_monster_effects` | Monsters Smashed | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `dungeonsdelight:main/all_monster_foods` | The Privilege of The Living | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `dungeonsdelight:main/eat_horse` | How Hungry...? | `PLEASED` | PROCLAMATION — a deed grand enough for a formal style, sung straight |
| `gnumus:sack_collector` | Pouch Collector | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `irons_spellbooks:irons_spellbooks/spell_book_diamond` | Master Enchanter | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `mowziesmobs:kill_ferrous_wroughtnaut` | Just a Flesh Wound | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `mowziesmobs:kill_frostmaw` | Rude Awakening | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `mowziesmobs:sculptor_challenge` | You Must Believe | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `rottencreatures:he_is_a_pirate` | He's a Pirate! | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `rottencreatures:shocking_encounter` | High Tension | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `savage_and_ravage:adventure/griefer_armor` | Dynamite with a Laser Beam | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `takesapillage:pillager_camp` | Campfire Song Song | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `tameablebeasts:argentavis` | Are There Hawks Too? | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `tameablebeasts:crested_gecko` | Now I Have To Build A Terrarium | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `tameablebeasts:grasshopper` | Ant Bully | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `tameablebeasts:ice_armor` | Bring Me A Warden! | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `tameablebeasts:roly_poly` | Ball | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `tameablebeasts:scarecrow_allay` | Allay In Disguise | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `conjurer_illager:kill_conjurer` | What a Performance! | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `the_bumblezone:biomes/discover_all_biomes` | Beeography | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `the_bumblezone:decor/obtain_all_banner_patterns` | Fashion Fad! | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `the_bumblezone:essence/essence_calming` | Peace and Love | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `the_bumblezone:essence/essence_continuity` | Reality Glitch | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `the_bumblezone:essence/essence_knowing` | The All-Seeing | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `the_bumblezone:essence/essence_life` | Blessings of Life | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `the_bumblezone:essence/essence_radiance` | Solar Powered | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `the_bumblezone:essence/essence_raging` | Unstoppable Anger | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `the_bumblezone:music_discs/obtain_all_music_discs` | Gotta Hive a Dance! | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `the_bumblezone:pollen_pile/fall_onto_pollen_pile` | Stuffy Nose & No Broken Knees! | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `the_bumblezone:structures/enter_all_structures` | Sightbee-er | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `the_bumblezone:the_queens_desire/fighting_the_swarm` | Fighting the Swarm | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `the_bumblezone:the_queens_desire/journeys_end` | Journey's End | `PLEASED` | PROCLAMATION — a deed grand enough for a formal style, sung straight |
| `the_bumblezone:the_queens_desire/otherworldly_mites` | Otherworldly Mites | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `the_bumblezone:the_queens_desire/peak_inefficiency` | Peak Inefficiency | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `the_bumblezone:the_queens_desire/the_beginning` | The Queen's Desire | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `twilightforest:arborist` | Maniacal Dendrologist | `PLEASED` | PROCLAMATION — a deed grand enough for a formal style, sung straight |
| `twilightforest:experiment_115_115` | Eating 115 Everyday, 115 Years, Forever | `PLEASED` | PROCLAMATION — a deed grand enough for a formal style, sung straight |
| `twilightforest:giants` | I'm on Cloud Nine | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `twilightforest:lich_scepters` | By Our Powers Combined! | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |
| `twilightforest:progress_merge` | Ultimate Showdown | `PLEASED` | PROCLAMATION — a deed grand enough for a formal style, sung straight |
| `twilightforest:progress_naga` | Time To Even The Scales | `RELISHING` | EPITHET / MOCK-HONORIFIC — the deed earns a name and the name needles |

##### Historian — 21

| Advancement | Native title | State | Why that state |
| --- | --- | --- | --- |
| `artifacts:adventurous_eater` | Adventurous Eater | `COMPOSED` | FIELD-NOTE — recorded, not celebrated |
| `artifacts:amateur_archaeologist` | Amateur Archaeologist | `PLEASED` | INVITATION — he wants the Overlord deeper |
| `irons_spellbooks:irons_spellbooks/spell_book_netherite` | Ancient Knowledge | `COMPOSED` | FIELD-NOTE — recorded, not celebrated |
| `irons_spellbooks:make_inscription_table` | Quaint Scribe | `COMPOSED` | FIELD-NOTE — recorded, not celebrated |
| `netherdepthsupgrade:main/abyssal_flora_expert` | Abyssal Flora Expert | `PLEASED` | a complete set pleases him |
| `fathoms:nautical/absorb_pylon_shock` | Electric Insulation | `PLEASED` | a complete set pleases him |
| `fathoms:nautical/activate_conduit` | Moskstraumen | `COMPOSED` | FIELD-NOTE — recorded, not celebrated |
| `fathoms:nautical/all_max_level_rituals` | Gargantua's Devout | `COMPOSED` | FIELD-NOTE — recorded, not celebrated |
| `fathoms:nautical/calcify_augur` | Rapid Calcification | `COMPOSED` | FIELD-NOTE — recorded, not celebrated |
| `fathoms:nautical/create_kelpie` | Transmogrification | `COMPOSED` | FIELD-NOTE — recorded, not celebrated |
| `fathoms:nautical/cut_aberration` | A Lot To Unpack Here | `DARKENED` | DISQUIET — the mask slips |
| `fathoms:nautical/enter_ancient_reservoir` | Buried Alive | `COMPOSED` | FIELD-NOTE — recorded, not celebrated |
| `fathoms:nautical/feed_baby_turtle` | Save the Turtles | `COMPOSED` | FIELD-NOTE — recorded, not celebrated |
| `fathoms:nautical/make_a_bad_decision` | Heart of Oil | `COMPOSED` | FIELD-NOTE — recorded, not celebrated |
| `fathoms:nautical/obtain_pylon` | Bomb Squad | `COMPOSED` | FIELD-NOTE — recorded, not celebrated |
| `fathoms:nautical/obtain_turtle_shell` | Turtle Power | `COMPOSED` | FIELD-NOTE — recorded, not celebrated |
| `fathoms:nautical/root` | Nautical | `COMPOSED` | FIELD-NOTE — recorded, not celebrated |
| `fathoms:nautical/use_bait` | Hook, Line, and Sinker | `COMPOSED` | FIELD-NOTE — recorded, not celebrated |
| `fathoms:nautical/whiplash_lightning` | System Overload | `COMPOSED` | FIELD-NOTE — recorded, not celebrated |
| `minecraft:husbandry/fishy_business` | advancements.husbandry.fishy_business.title | `COMPOSED` | FIELD-NOTE — recorded, not celebrated |
| `savage_and_ravage:adventure/relic_collector` | Relic Collector | `PLEASED` | a complete set pleases him |

##### Lestat — 19

| Advancement | Native title | State | Why that state |
| --- | --- | --- | --- |
| `graveyard:graveyard/equip_ghouling_coffin` | The dead shall serve | `RELISHING` | intimate observation; he names what was done |
| `graveyard:graveyard/kill_wraith` | Taking from the Dead | `RELISHING` | intimate observation; he names what was done |
| `block_factorys_bosses:no_hit_dragon` | Dragon Hunter | `PLEASED` | mastery, not mere completion (40 §20) |
| `born_in_chaos_v1:unlucky_hunter` | Unlucky Hunter | `DARKENED` | restraint or hunting revealing fear of identity (40 §20) |
| `nycto:nycto/brew_garlic_brew` | Holy Water | `DARKENED` | restraint or hunting revealing fear of identity (40 §20) |
| `nycto:nycto/buy_hunter_contract` | Contract Killer | `DARKENED` | restraint or hunting revealing fear of identity (40 §20) |
| `nycto:nycto/complete_vampire` | Taste of Eternity | `PLEASED` | mastery, not mere completion (40 §20) |
| `nycto:nycto/extract_blood_bottle` | Blood Drive | `RELISHING` | intimate observation; he names what was done |
| `nycto:nycto/kill_vampire` | Not So Immortal | `RELISHING` | aesthetic judgment on a kill |
| `nycto:nycto/kill_vampire_with_wooden_stake` | Shot Through The Heart | `DARKENED` | restraint or hunting revealing fear of identity (40 §20) |
| `nycto:nycto/obtain_garlic` | Tasty Aromatic Vegetable | `DARKENED` | restraint or hunting revealing fear of identity (40 §20) |
| `nycto:nycto/obtain_garlic_coated_halberd` | Seasoned Warrior | `DARKENED` | restraint or hunting revealing fear of identity (40 §20) |
| `nycto:nycto/obtain_garlic_wreath` | It's Called Aura | `DARKENED` | restraint or hunting revealing fear of identity (40 §20) |
| `nycto:nycto/obtain_vampiric_dagger` | Sharp Wits | `RELISHING` | intimate observation; he names what was done |
| `nycto:nycto/root` | Nycto | `RELISHING` | intimate observation; he names what was done |
| `nycto:nycto/sleep_in_coffin` | Rest in Peace | `RELISHING` | intimate observation; he names what was done |
| `nycto:nycto/use_garlic_brew_on_vampire` | Night of the Living | `DARKENED` | restraint or hunting revealing fear of identity (40 §20) |
| `nycto:nycto/wear_vampire_hunter_armor` | Van Helsing | `PLEASED` | mastery, not mere completion (40 §20) |
| `fathoms:nautical/feed_dolphin` | Treasure Hunter | `DARKENED` | restraint or hunting revealing fear of identity (40 §20) |

##### Grubbison Jr — 17

| Advancement | Native title | State | Why that state |
| --- | --- | --- | --- |
| `darkerdepths:activities/elytra_boosted_by_geyser` | Inverse Rocketry | `RELISHING` | ENTHUSIASM — a minion delighted by rock |
| `darkerdepths:activities/insert_diamond_into_crystal_husk` | Back From the Dead! | `RELISHING` | ENTHUSIASM — a minion delighted by rock |
| `darkerdepths:activities/use_crystal_melon` | Feel the POWAH! | `RELISHING` | ENTHUSIASM — a minion delighted by rock |
| `darkerdepths:story/root` | Darker Depths | `RELISHING` | ENTHUSIASM — a minion delighted by rock |
| `darkerdepths:visited/glowshroom_forest` | Luminous Depths | `RELISHING` | ENTHUSIASM — a minion delighted by rock |
| `darkerdepths:visited/molten_cavern` | It's Getting Hot in Here! | `RELISHING` | ENTHUSIASM — a minion delighted by rock |
| `darkerdepths:visited/sandy_catacombs` | Desert but Underground? | `RELISHING` | ENTHUSIASM — a minion delighted by rock |
| `betterdungeons:spider_dungeon` | Cobweb Entanglement | `RELISHING` | ENTHUSIASM — a minion delighted by rock |
| `dungeonsdelight:main/get_candied_sucker` | Sweet Revenge! | `RELISHING` | ENTHUSIASM — a minion delighted by rock |
| `mowziesmobs:kill_grottol_fortune` | Double Or Nothing! | `RELISHING` | ENTHUSIASM — a minion delighted by rock |
| `mowziesmobs:kill_grottol_silk_touch` | You're Coming With Me | `RELISHING` | ENTHUSIASM — a minion delighted by rock |
| `the_bumblezone:combs_and_wax/honey_cocoon_silk_touch` | A Delicate Touch | `RELISHING` | ENTHUSIASM — a minion delighted by rock |
| `the_bumblezone:effects/honey_permission` | No more Wrath! Only friendship! | `RELISHING` | ENTHUSIASM — a minion delighted by rock |
| `the_bumblezone:the_queens_desire/terror_fangs` | Terror Fangs | `PLEASED` | DOMAIN-EXPERTISE — the seam worked properly |
| `the_bumblezone:the_queens_desire/too_many_legs` | Too Many Legs | `PLEASED` | DOMAIN-EXPERTISE — the seam worked properly |
| `twilightforest:hill1` | The Boots Are Mine! | `RELISHING` | ENTHUSIASM — a minion delighted by rock |
| `twilightforest:mazebreaker` | Breaking the Maze | `PLEASED` | DOMAIN-EXPERTISE — the seam worked properly |

##### Mortis — 15

| Advancement | Native title | State | Why that state |
| --- | --- | --- | --- |
| `darkerdepths:activities/bottle_void_soul` | Bottle O'Void Souls | `COMPOSED` | CLINICAL — Spawning Pit business |
| `darkerdepths:activities/set_death_anchor` | Soul Pact | `RELISHING` | MORBID-COMIC — the scythe and the black velvet |
| `darkerdepths:visited/structure/visited_catacombs` | The Lost Souls | `COMPOSED` | CLINICAL — Spawning Pit business |
| `rats:rat_upgrade_sculked` | One with the Souls | `RELISHING` | MORBID-COMIC — the scythe and the black velvet |
| `graveyard:graveyard/desert_graveyard` | Dust, Sand and Bones | `COMPOSED` | CLINICAL — Spawning Pit business |
| `graveyard:graveyard/kill_graveyard_mob` | Stay dead | `COMPOSED` | CLINICAL — Spawning Pit business |
| `graveyard:graveyard/kill_horde` | Walking Dead | `COMPOSED` | CLINICAL — Spawning Pit business |
| `graveyard:graveyard/large_graveyard` | Disturbing the Dead II | `COMPOSED` | CLINICAL — Spawning Pit business |
| `graveyard:graveyard/root` | Gravedigger | `COMPOSED` | CLINICAL — Spawning Pit business |
| `born_in_chaos_v1:bury_them_all` | Bury Them All | `COMPOSED` | LITURGICAL — completion recorded without ceremony |
| `born_in_chaos_v1:compact_necromancy` | Compact Necromancy | `COMPOSED` | CLINICAL — Spawning Pit business |
| `born_in_chaos_v1:rampage` | RAMPAGE! | `COMPOSED` | CLINICAL — Spawning Pit business |
| `born_in_chaos_v1:soul_eater` | Soul Eater | `COMPOSED` | LITURGICAL — completion recorded without ceremony |
| `pet_cemetery:nether/respawn_zombie_pet` | Grim Reaper | `COMPOSED` | CLINICAL — Spawning Pit business |
| `twilightforest:progress_knights` | Carminite Acclimation | `COMPOSED` | CLINICAL — Spawning Pit business |

##### Giblet the Sixth — 15

| Advancement | Native title | State | Why that state |
| --- | --- | --- | --- |
| `farmersdelight:main/obtain_netherite_knife` | If You Can't Take the Heat... | `PLEASED` | DOMAIN-EXPERTISE — rare praise from him |
| `born_in_chaos_v1:dark_blacksmith` | Dark Blacksmith | `RELISHING` | ENTHUSIASM — a minion delighted by metal |
| `born_in_chaos_v1:dark_forging` | Dark Forging | `RELISHING` | ENTHUSIASM — a minion delighted by metal |
| `dungeonsdelight:main/get_netherite_cleaver` | Cutlery of Apostasy | `PLEASED` | DOMAIN-EXPERTISE — rare praise from him |
| `minecraft:story/iron_tools` | advancements.story.iron_tools.title | `RELISHING` | ENTHUSIASM — a minion delighted by metal |
| `minecraft:story/upgrade_tools` | advancements.story.upgrade_tools.title | `RELISHING` | ENTHUSIASM — a minion delighted by metal |
| `iceandfire:iceandfire/dragon_forge_brick` | Another Brick in the Wall | `RELISHING` | ENTHUSIASM — a minion delighted by metal |
| `iceandfire:iceandfire/dragonsteel` | Forged in Flame | `RELISHING` | ENTHUSIASM — a minion delighted by metal |
| `irons_spellbooks:irons_spellbooks/make_scroll_forge` | New Age Librarian | `RELISHING` | ENTHUSIASM — a minion delighted by metal |
| `irons_spellbooks:irons_spellbooks/staff_pyrium` | Ancient Beckonings | `PLEASED` | DOMAIN-EXPERTISE — rare praise from him |
| `fathoms:nautical/full_tell_tale_heart_trim_material` | Oilman | `PLEASED` | DOMAIN-EXPERTISE — rare praise from him |
| `fathoms:nautical/upgrade_ornate_tool` | Former Glory | `RELISHING` | ENTHUSIASM — a minion delighted by metal |
| `tameablebeasts:beetle_armor` | Giving Netherite A Run For Its Money | `PLEASED` | DOMAIN-EXPERTISE — rare praise from him |
| `tameablebeasts:beetle_gem` | Crystal Chimera | `RELISHING` | ENTHUSIASM — a minion delighted by metal |
| `the_bumblezone:honey_crystal_shield/obtain_maxed_honey_crystal_shield` | Grand Hive Defender | `PLEASED` | DOMAIN-EXPERTISE — rare praise from him |

### 10.3 Presenter art

Five visual assets per presenter, eight presenters, forty assets. `NOT_GENERATED` is an acceptable state during authoring. Quaver's presentation must account for the Jester merge (Q244b).

### 10.4 Lifecycle text beyond two popups

The runtime supports clarification, branch framing, reminders, warnings and post-quest commentary. V5 authors unlock and completion only. The remaining slots belong to the writing pass.

---

## 11. Implementation queue

1. ~~Register the pending narrative fact keys.~~ **Done 2026-09-18.** `NARRATIVE_FACTS.md` reconciled: 45 legacy facts remapped onto V5 writers, 45 new facts registered, 3 retired. One item was left open — `overlord_reign:tower/restoration_complete` — and is now **specified in `V5_IMPLEMENTATION_SPECS.md` §2** as a hidden aggregate quest using the already-registered `questlog:fact` and `questlog:set_fact` types. It requires the Arena, Jail and Biomancy room facts to be added to `NARRATIVE_FACTS.md`.
2. Resolve the ten quests marked `framework objective type needed` onto existing Questlog types. No new objective type may be authored where an existing one fits (Q235).
3. Build the one code bridge: `VampireFeedingEvents` for the living-victim arm of `OR-ADV-NIGH-001`. Without it the branch collapses and only the Blood Bottle arm can fire. **Specified in `V5_IMPLEMENTATION_SPECS.md` §3.**
4. Author the `Order (presenter voice)` line and name for all 147 quests and 43 provider quests.
5. Generate the forty presenter assets.
6. Close the Rambling catalog per §10.2.
7. Re-supply `DramaticDoors-QuiFabrge-1.20.1-3.3.3.jar`, which is not a readable zip in the current instance archive and is therefore unaudited. Q215 rejected re-scanning it; this is recorded so the omission stays deliberate.

---

## 12. Closing rule

Where this document specifies, the builder implements. Where it is silent, the builder **asks**.

An authored choice that cannot be traced to this document or to an explicit Overlord decision is not authority. Technical convenience does not promote design into authority. A mod exposing an advancement, the framework supporting an objective type, an older production quest already existing, or a choice appearing obvious are none of them approval.
