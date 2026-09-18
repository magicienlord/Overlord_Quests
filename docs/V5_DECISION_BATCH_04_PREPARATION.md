# OVERLORD REIGN V5 Decision Batch 04 Preparation

Status: PROPOSAL ONLY / NOT V5 AUTHORITY

Date: 2026-09-17

Purpose: prepare the next full Overlord decision batch after reconciliation of Q143-Q160 and direct inspection of the supplied mod JARs. Nothing in this file becomes campaign authority until the Overlord approves, modifies, rejects, or directly edits the corresponding Batch 04 workbook material.

This preparation deliberately excludes technical facts that can be translated without changing player-facing meaning. It also excludes older apparent gaps already closed by Batches 01-03.

## 1. Controlling source state

Primary authority and control sources:

- `docs/V5_00_OVERLORD_APPROVAL_GOVERNANCE.md`
- `docs/V5_CAMPAIGN_SYSTEM_AUTHORITY.md`
- `docs/V5_CIVILIZATION_SYSTEM_AUTHORITY.md`
- `docs/V5_PRESENTER_SYSTEM_AUTHORITY.md`
- `docs/V5_RAMBLING_SYSTEM_AUTHORITY.md`
- `docs/V5_DECISION_BATCH_01_AUTHORITY.md`
- `docs/V5_DECISION_BATCH_02_AUTHORITY.md`
- `docs/V5_DECISION_BATCH_03_AUTHORITY.md`
- `docs/V5_BATCH_03_RECONCILIATION_2026-09-17.md`
- `docs/V5_BATCH_03_SUPPLIED_JAR_AUDIT_2026-09-17.md`
- focused civilization and Adventure authorities cited below.

Batch 03 already establishes the reusable activation and reward defaults. Batch 04 therefore asks only residual authored exceptions or unresolved objective/allocation choices.

## 2. Items explicitly pruned from Batch 04

Do not reopen:

- Spree Farmer, Miner and Guard player-facing accomplishments, which Q053-Q058 already define;
- Spree DESTROYED meaning, which Q059 already defines;
- Myrmex NEUTRAL, SUBJUGATED and DESTROYED proofs, closed by Q139-Q141 and exact Ice & Fire source verification;
- four-part Dragon Mastery, which is already exact enough for implementation translation;
- magic quest counts and exact mastery objectives closed in Batch 02;
- Tower Restoration, Minion restoration, Bosses'Rise investigation, Ender Dragon activation, ordinary sequencing and default reward philosophy closed in Batch 03;
- civilization anchor start through banner placement;
- political meanings already approved for civilization terminal routes.

## 3. Decision questions

### Q161 - Rewards - Questlog reward claim behavior

Question: Within Questlog's actual reward system, what collection behavior should V5 use for separate tangible quest rewards?

Proposed V5 answer: Ordinary non-choice tangible Questlog rewards remain manually claimable after quest completion. Use `auto_claim: true` only for a non-choice reward that is explicitly intended to be delivered immediately and cannot meaningfully be deferred. Choice rewards always remain manual because the framework requires player selection. Narrative state changes, unlocks and remembered facts remain automatic completion consequences and are not converted into claimable rewards.

Why: Exact Questlog inspection confirms manual collection, `auto_claim`, and choice reward selection all exist. This replaces rejected Q158 without pretending campaign consequences are prizes.

Source: `docs/V5_BATCH_03_SUPPLIED_JAR_AUDIT_2026-09-17.md` and `docs/V5_BATCH_03_RECONCILIATION_2026-09-17.md`.

Priority: HIGH.

### Q162 - NightWalker - Exact Vampire Altar commitment

Question: What exact source-owned event should complete NightWalker quest 3's first meaningful Vampire Altar power/weakness commitment?

Proposed V5 answer: Require the first successful Vampire Altar purchase after quest activation that adds one previously unowned vampire power and records the weakness required by that purchase. Mere opening of the Altar interface, already-owned power state, or blood expenditure without a completed purchase does not qualify.

Why: Nycto owns power, weakness and altar-purchase state directly, so the final visible NightWalker beat can be exact without creating a synthetic advancement.

Source: exact Nycto alpha.4 JAR audit in `docs/V5_BATCH_03_SUPPLIED_JAR_AUDIT_2026-09-17.md`.

Priority: HIGH.

### Q163 - Rats - Ratlantis access beat

Question: What exact native accomplishment should implement the second visible Rats quest between ordinary rat utility and the Ratlantis investigation?

Proposed V5 answer: Obtain the native Chunky Cheese Token through the Ratlantis access progression, use the native Ratlantis portal/access mechanism, and enter Ratlantis. The quest completes on successful dimension entry, not on merely holding the token.

Why: This makes the second beat an actual transition into Ratlantis and leaves the next quest free to investigate Ratlantian technology and inhabitants.

Source: exact Rats 8.1.3 JAR audit from the supplied instance.

Priority: HIGH.

### Q164 - Rats - Ratlantis investigation beat

Question: What exact native accomplishments should implement the third visible Rats quest before the Rat Baron capstone?

Proposed V5 answer: In Ratlantis, encounter and defeat at least one Feral Ratlantean and one Ratlantean Ratbot, then obtain Oratchalcum. Treat the combination as proof that the player has encountered Ratlantian society, machinery and material culture before the Rat Baron confrontation.

Why: These are Ratlantis-specific native surfaces and create a distinct investigation stage rather than a second generic combat quest.

Source: exact Rats 8.1.3 JAR audit from the supplied instance.

Priority: HIGH.

### Q165 - Farming / Automation - Productive farm opening proof

Question: What exact accomplishment should the first Farming quest use to establish a productive estate farm before magical helpers appear?

Proposed V5 answer: Establish one contiguous cultivated estate plot containing at least three staple crop types, allow each crop type to reach harvestable maturity, and harvest at least one mature crop of each type after the quest activates. The plot remains the estate plot later used by the Wheat Critter and Hay Golem quests.

Why: Q136-Q138 already define the helper stages. This closes the only undefined visible accomplishment in the four-quest Farming line without adding another food-system checklist.

Source: Q031 and Q136-Q138 authority.

Priority: HIGH.

### Q166 - Quaver - Visible ensemble quest allocation

Question: How should Quaver's already-approved eight-instrument Dark Tower ensemble be represented as visible quest content?

Proposed V5 answer: Use one visible Quaver quest requiring acquisition of all eight approved instruments: Bagpipe, Flute, Lute, Piano, Trumpet, Tiny Drum, Vielle and Handpan. Quaver frames the request and completion. Do not create one quest per instrument and do not require performer recruitment. Completion establishes the Tower ensemble as available to Minion performers.

Why: The exact instrument set is already approved, but the visible quest allocation remains undefined.

Source: Q039, Q111 and presenter authority.

Priority: HIGH.

### Q167 - Lost Castle - Exact three-quest objective mapping

Question: Should the approved three-part Lost Castle structure be closed by source research as one structure-entry quest, one occupation/evidence quest and one unique-recovery closure quest, without adding extra beats?

Proposed V5 answer: Preserve exactly three quests. Quest 1 completes on first deliberate entry into the bound Lost Castle. Quest 2 requires clearing the structure-local Illager occupation while investigating the predecessor evidence that V5 assigns to the castle. Quest 3 requires recovery of the expedition's source-backed unique or culminating treasure/evidence and then closes the expedition. Do not add a fourth boss/checklist quest unless exact source inspection proves the current third beat cannot be represented.

Why: Q036 fixed the meanings but not the final source-level objective translation. This row authorizes the compact translation while requiring the exact source object/room/detector to remain technical rather than invented.

Source: Q036 and installed Lost Castle artifact audit.

Priority: NORMAL.

### Q168 - Queen of Orchid - Exact three-quest objective mapping

Question: How should the approved shrine -> preparation/investigation -> Queen resolution structure translate into exact visible objectives?

Proposed V5 answer: Keep exactly three visible quests. Quest 1 is first meaningful discovery/entry of the source-owned Queen of Orchid shrine/domain. Quest 2 uses the source-owned pre-encounter requirement or preparation surface that actually enables/reaches the Queen rather than an authored evidence prop. Quest 3 is the Queen of Orchid encounter resolution. Do not add unrelated nature-mod completion requirements.

Why: Q037 fixes the three meanings but the final native translation must not be left for implementation to invent.

Source: Q037 and installed Queen of Orchid artifact audit.

Priority: NORMAL.

### Q169 - Bumblezone - Meaningful Queen contact proof

Question: What should count as the middle Bumblezone quest's already-approved meaningful Queen contact?

Proposed V5 answer: Require the source-owned first successful interaction/progression contact with the Bumblezone Queen that establishes access to her Desires progression. Mere proximity to the Queen or entry into her structure does not qualify. The final quest remains completion of Queen's Desires plus obtaining and consuming Essence of the Bees.

Why: The three-quest topology is approved, but the middle quest needs an implementation-stable accomplishment rather than the phrase "meaningful contact" being interpreted later.

Source: Q042 and Bumblezone source progression.

Priority: NORMAL.

### Q170 - Post-Credits End - Representative capstone

Question: What exact accomplishment should serve as the fourth and final representative capstone of the combined Post-Credits End expedition?

Proposed V5 answer: Make the capstone require completion of both explicit Outer End major-structure discovery accomplishments that exist in the installed End stack, after the prior expedition quest has already required broader ruins/cities/island investigation. Do not fabricate an all-content completion advancement for Better End Cities, YUNG's Better End Island or Enderman Overhaul when those mods do not expose one.

Why: Q047 requires one representative capstone. Exact source inspection found explicit Outer End structure achievements but no equivalent unified completion signal across the other three constituent mods.

Source: Q047 and exact installed End-stack audit.

Priority: HIGH.

### Q171 - Dwarves - Replace impossible historical-record object proof

Question: Q060 requires a Golden Hills record or artifact, but exact Dwarven Forge source contains no native record/archive/artifact. What should replace that impossible native-object requirement?

Proposed V5 answer: The Record Keeper provides the surviving historical testimony. The player then proves material continuity by completing one source-backed Dwarven craft sequence that combines Dwarven metal with rune craft/forge work in the canonical hold. The Record Keeper and Gnarl interpret that material continuity as corroboration of the testimony. Do not add a fabricated native archive item. Completing this history chain reveals the already-approved NEUTRAL route.

Why: This preserves Q060's purpose while respecting the exact installed source and the later authority that the Record Keeper is an authored local role, not a native archivist profession.

Source: Q060, `docs/V5_DWARF_GNUMU_GOBLIN_OBJECTIVE_AUTHORITY.md`, exact Dwarven Forge audit.

Priority: HIGH.

### Q172 - Dwarf / Kobold Rivalry - Shared investigation opening

Question: What exact shared opening should establish the optional rivalry once both canonical anchors have been discovered?

Proposed V5 answer: After both anchors exist, require one visit to each bound polity and one source-compatible material/service interaction with each side, then present the paired evidence through the rivalry chain. The opening writes only `RIVALRY_INVESTIGATED` and exposes the four approved resolutions. It does not alter either terminal civilization disposition.

Why: Q142 defines the topology but not the shared investigation accomplishment.

Source: Q142, Dwarf and Kobold native audits.

Priority: NORMAL.

### Q173 - Dwarf / Kobold Rivalry - Four resolution objective packages

Question: How should the four approved rivalry outcomes be proven without merging the two civilization state machines?

Proposed V5 answer: Each resolution is one mutually exclusive quest: favor Dwarves by completing a Dwarven forge/material commission that disadvantages the Kobold claim; favor Kobolds by completing a Kobold engineering/resource-security commission that disadvantages the Dwarven claim; force a working arrangement by completing one reciprocal Dwarf and Kobold service exchange; exploit/escalate by extracting one valuable service/material from each side while deliberately leaving the dispute unresolved. Each writes only its named rivalry fact.

Why: This gives Q142 source-compatible player actions while preserving separate civilization outcomes.

Source: Q142 and exact Dwarf/Kobold native audits.

Priority: HIGH.

### Q174 - Gnumu - NEUTRAL exact proof

Question: Which native Vintage progression should prove that the Gnumu can recover useful ancestral technology while retaining ownership?

Proposed V5 answer: After the ancestry decision, complete the native Vintage reconstruction progression through Vintage Alloy, one Vintage equipment improvement, and the Vintage Universal Tool milestone, then resolve the route by explicitly leaving the reconstructed technology and its interpretation under Gnumu control.

Why: Q065 fixes the political meaning. The installed mod exposes a clear escalating Vintage progression that can prove recovery rather than generic assistance.

Source: Q065 and `docs/V5_DWARF_GNUMU_GOBLIN_ROUTE_ALLOCATION_AUTHORITY.md`.

Priority: HIGH.

### Q175 - Gnumu - SUBJUGATED exact dependency proof

Question: What exact source-backed package should prove Gnumu dependence on Overlord-controlled Vintage/material access?

Proposed V5 answer: Complete a bound Main Merchant trade, then provide the scarce Vintage material needed to complete one later Vintage reconstruction/improvement that the settlement has not independently completed in this route. Record the Merchant's continuing access/service obligation to the Overlord, then use that material dependency in the Elder Shaman submission audience.

Why: Q066 requires scarce Vintage/material control plus the Merchant/service chain, but the player-facing sequence is still undefined.

Source: Q066, `gnumus:business_approach`, and exact Vintage progression audit.

Priority: HIGH.

### Q176 - Gnumu - DESTROYED functional authority proof

Question: Which bound local authorities should be removed with the Elder Shaman so the canonical Gnumu settlement ceases to function politically without becoming a species kill quota?

Proposed V5 answer: After destructive commitment, require the Elder Shaman's death plus removal of the bound Main Merchant and the bound Hunter/defense authority used by this anchor's campaign. Do not require killing ordinary workers, every hunter, or destroying every settlement block.

Why: Q067 requires leadership plus the technical/economic authority sustaining civic function. This selects the minimal already-established provider pillars.

Source: Q067 and Gnumu civilization blueprint.

Priority: HIGH.

### Q177 - Goblins - NEUTRAL provider proof

Question: What exact native actions should close the Main Merchant and Camp Bartender chains before independent coexistence?

Proposed V5 answer: Complete one meaningful native Merchant transaction that satisfies the source merchant-success surface and one native Bartender/liquor interaction that satisfies the source liquor-success surface, each with its bound provider. Then complete the explicit Leader nonaggression/independence resolution. Do not add a generic Goblin friendship counter.

Why: Q068 already names both providers and their political purpose; the installed mod supplies exact native success signals.

Source: Q068 and `docs/V5_DWARF_GNUMU_GOBLIN_OBJECTIVE_AUTHORITY.md`.

Priority: NORMAL.

### Q178 - Goblins - Engineer SUBJUGATED obligation

Question: What exact accomplishment should prove the Camp Engineer has entered an Overlord-dependent engineering relationship?

Proposed V5 answer: Complete one source-owned Engineer workbench project that reaches the native engineer-success/prototype-upgrade surface using Overlord-supplied materials, then record the Engineer's continuing technology/service obligation to the Overlord.

Why: Q069 requires an engineering dependency, not merely talking to the provider.

Source: Q069 and exact Goblins Tyranny audit.

Priority: HIGH.

### Q179 - Goblins - Blacksmith SUBJUGATED obligation

Question: What exact accomplishment should prove the Camp Blacksmith's production is tied to Overlord patronage?

Proposed V5 answer: Supply the bound Blacksmith with the high-value inputs required by its native advanced service path, including the source-relevant Netherite/Engineer material bridge, complete one advanced Blacksmith service, then record the arms/production obligation to the Overlord.

Why: The Blacksmith is a real native provider and Q069 requires a distinct arms/productive dependency.

Source: Q069 and exact Goblins Tyranny audit.

Priority: HIGH.

### Q180 - Goblins - Merchant SUBJUGATED obligation

Question: What exact Merchant accomplishment should establish the commerce pillar of Goblin submission rather than repeat the NEUTRAL trade proof?

Proposed V5 answer: After the Merchant's ordinary trade surface has been demonstrated, complete one higher-value patronage transaction supplied by the Overlord and record preferential access/commerce obligation to him. This is a named dependency fact used by the Leader submission and not a repeated merchant-success counter.

Why: Q069 requires the Merchant as one of three distinct leverage pillars, so the submission proof must be materially stronger than the NEUTRAL relationship.

Source: Q069 and exact Goblin trade audit.

Priority: HIGH.

### Q181 - Kobolds - Captain resource/logistics proof

Question: Which native Captain interaction should prove that Den resource/logistics security depends on the Overlord?

Proposed V5 answer: Complete the Captain's full source-owned three-tier exchange progression with the bound Captain, ending on the highest-value tier. Treat completion of the escalating exchange chain as the concrete proof that the Captain's continuing resource/logistics operation depends on Overlord supply.

Why: Exact source inspection found three real Captain exchange tiers; using all three creates a progression rather than an arbitrary hand-in.

Source: Q071 and exact Kobolds source audit.

Priority: HIGH.

### Q182 - Kobolds - Engineer commission

Question: What exact native action should satisfy the Engineer's one meaningful infrastructure/engineering commission?

Proposed V5 answer: Complete one bound Engineer transaction that consumes the source-requested inputs and returns a native engineering component, then use/commit that component to the canonical Den's campaign infrastructure objective. Do not use a generic item-delivery-only quest.

Why: Q072 requires a meaningful commission; the exact source provides transactions that return engineering components.

Source: Q072 and exact Kobolds source audit.

Priority: NORMAL.

### Q183 - Kobolds - Enchanter service

Question: What exact native service should satisfy the Enchanter's meaningful magical-upgrade obligation?

Proposed V5 answer: Complete the bound Enchanter transaction that can return the native Prospector book and use that result as the representative magical capability proof. Do not require an exhaustive enchantment catalog.

Why: Q073 requires one valued magical service and the source exposes a distinctive native result.

Source: Q073 and exact Kobolds source audit.

Priority: NORMAL.

### Q184 - Kobolds - DESTROYED indispensable Den function

Question: What should satisfy Q074's requirement to disable one indispensable Den function after the Captain and bound Warrior are defeated?

Proposed V5 answer: Disable the campaign-bound engineering/logistics function established through the Captain/Engineer chains rather than inventing a new Den core block. The destructive route explicitly closes that bound service/function, removes its provider continuity, and then resolves DESTROYED. Do not require demolition of the entire Den.

Why: Exact source inspection does not expose a single canonical political-core block. Reusing an authored function already established by the route is clearer than fabricating one.

Source: Q074 and exact Kobolds audit.

Priority: HIGH.

### Q185 - Ribbits - NEUTRAL prosperity proof

Question: What exact source-backed sequence should prove that the canonical Ribbit village can function independently?

Proposed V5 answer: With the bound Gardener-Elder and Fisherman, complete one successful source-owned crop-tending cycle and one successful source-owned fishing/supply cycle, then explicitly leave the resulting local food/prosperity system under Ribbit control before the independence resolution.

Why: Q075 already defines a Gardener-Elder/Fisherman prosperity sequence; exact source behavior provides farming and fishing actions.

Source: Q075 and exact Ribbits audit.

Priority: HIGH.

### Q186 - Ribbits - Merchant SUBJUGATED commitment

Question: What exact transaction should establish the Merchant's exclusive/preferential Tower-facing commerce obligation?

Proposed V5 answer: Complete one meaningful native trade with the bound Merchant after the submission route is active, then complete one authored commitment turn-in representing preferential Tower supply. Record the continuing commerce obligation once; do not simulate recurring deliveries.

Why: Q076 defines the political commitment but not its one-time player-facing proof.

Source: Q076 and exact Ribbits audit.

Priority: NORMAL.

### Q187 - Ribbits - Fisherman SUBJUGATED commitment

Question: What one-time source-compatible proof should establish the Fisherman's continuing food/resource supply obligation?

Proposed V5 answer: Require one curated delivery assembled from the bound Fisherman's native fishing output/resource pool, then record the recurring political obligation as a remembered fact. Do not require periodic future deliveries.

Why: Q077 explicitly says the recurring obligation should be represented once rather than simulated forever.

Source: Q077 and exact Ribbits audit.

Priority: NORMAL.

### Q188 - Ribbits - Sorcerer SUBJUGATED commitment

Question: What exact meaningful magical task should precede the Sorcerer's service/knowledge commitment?

Proposed V5 answer: Require the bound Sorcerer to successfully apply its native beneficial magic/buff capability in the authored quest context, after the Overlord completes the prerequisite request that earns the service. The successful source-owned magical application is the capability proof; the subsequent service commitment is the political fact.

Why: Q078 requires a magical task before commitment and the source owns real Sorcerer buffing behavior.

Source: Q078 and exact Ribbits audit.

Priority: NORMAL.

### Q189 - Sea Dwellers - Blacksmith commitment

Question: What exact native craft should prove the Blacksmith's high-tier Depth-metallurgy service commitment?

Proposed V5 answer: Supply the bound Blacksmith with the source-required high-tier Depth material inputs and complete one representative advanced Depth-metal craft/service, then record continuing Blacksmith service to the Overlord. Use a source-native high-tier product, not a fabricated tribute item.

Why: Q080 fixes the political meaning but not the exact representative commission.

Source: Q080 and exact Sea Dweller/Depths audit.

Priority: HIGH.

### Q190 - Sea Dwellers - Collector commitment

Question: What exact collection should establish the Collector's continuing tribute/service relationship?

Proposed V5 answer: Complete one curated collection containing distinct culturally valuable underwater resource classes represented by the source, including at least one Collector/nautilus-type resource and one Depth-specific valuable resource, then record the continuing service once. Do not require exhaustive collection of every sea item.

Why: Q081 requires a curated culturally valuable collection, not generic inventory filling.

Source: Q081 and exact Sea Dweller/Depths audit.

Priority: NORMAL.

### Q191 - Sea Dwellers - Institutional leverage proof

Question: What exact objective should prove control over the wider Sea Dweller profession/trade economy using Seashells, Aquamarine commerce and Depth resources?

Proposed V5 answer: Complete one three-part institutional exchange in the canonical anchor: earn/use a profession Seashell through its source-owned profession interaction, complete one Aquamarine barter, and supply one high-value Depth resource into the bound provider economy. Completion writes the institutional-access leverage fact used by SUBJUGATED.

Why: Q082 explicitly requires all three resource/economy surfaces in one institutional objective without adding a third personal provider.

Source: Q082 and exact Sea Dweller audit.

Priority: HIGH.

### Q192 - Sea Dwellers - DESTROYED civic/resource core

Question: What exact protected civic/resource-core action should accompany the Sea Elder's removal for DESTROYED?

Proposed V5 answer: After destructive commitment and removal of the canonical Sea Elder, deliberately destroy the campaign-bound protected Sea Lantern/civic resource core whose source-owned destruction provokes Sea Dweller hostility. Use that taboo resource as the authored local continuity break. Do not require killing all professions.

Why: The exact source exposes a sea-lantern destruction hostility surface that cleanly satisfies Q083's deliberate core violation requirement.

Source: Q083 and exact Sea Dweller native audit.

Priority: HIGH.

### Q193 - Piglins - NEUTRAL exact proof

Question: What exact source-owned actions should prove understanding of Piglin barter/property customs before independent coexistence?

Proposed V5 answer: Complete at least one ordinary Piglin barter while respecting the canonical village's protected gold/property order, then complete one useful exchange through the bound Chieftain/provider sequence. End with explicit independent coexistence. Do not use a hidden friendship or barter-count score.

Why: Q085 fixes the meaning but needs a concrete native proof.

Source: Q085 and exact Piglin native audit.

Priority: HIGH.

### Q194 - Piglins - SUBJUGATED resource leverage

Question: Which source-owned village functions should prove that Piglin prosperity depends on Overlord-controlled gold/barter supply?

Proposed V5 answer: Establish control over a high-value gold supply delivery and bind that supply to the canonical village's forge/storage economy, then complete the Chieftain submission with those functions intact but dependent on the Overlord. Do not destroy the infrastructure used as leverage.

Why: Q086 explicitly requires both valuable gold/barter supply and key resource infrastructure.

Source: Q086 and exact Piglin village audit.

Priority: HIGH.

### Q195 - Piglins - DESTROYED protected order

Question: Which central Piglin function should be seized or destroyed with the Chieftain's death to prove the protected gold/property order has been broken?

Proposed V5 answer: Use the campaign-bound central forge/storage function of the canonical Nether Village as the protected economic core. After destructive commitment, kill the Chieftain and deliberately destroy or seize that bound function. Do not require village-wide block demolition or ordinary Piglin extermination.

Why: Q087 requires a central resource/forge/storage function but leaves the exact one open.

Source: Q087 and exact Piglin village audit.

Priority: HIGH.

### Q196 - Umvuthana - Crane healing/support dependency

Question: What exact accomplishment should prove the Grove Healer's function depends on Overlord-secured external input?

Proposed V5 answer: Complete one bound Crane provider request requiring a deliberately selected rare healing/support input that is not produced by the Grove's ordinary local loop, then have the Crane perform/confirm its native healing-support function and explicitly commit that function to the Overlord. Use one source-compatible input package rather than a repeatable supply meter.

Why: Q088 establishes the meaning but Mowzie's source does not itself provide a ready-made political dependency event.

Source: Q088 and exact Umvuthana relationship audit.

Priority: HIGH.

### Q197 - Umvuthana - Raptor martial allegiance challenge

Question: What exact non-lethal native-compatible challenge should prove superior command to the bound Raptor?

Proposed V5 answer: Use a controlled combat/leadership trial against the bound Raptor or its authored pack context that ends before death, requires the player to prevail under the source-compatible encounter rules, and then records the Raptor's allegiance transfer. Do not intercept Umvuthi boss health or create a defeated-but-alive Umvuthi state.

Why: Q089 requires a non-lethal command/combat challenge but exact source does not expose a prebuilt allegiance event.

Source: Q089 and exact Umvuthana audit.

Priority: HIGH.

### Q198 - Illagers - NEUTRAL post-COWED proof

Question: After the canonical Bastille has reached the approved internal COWED state, what exact player-facing action should establish independent nonaggression rather than submission?

Proposed V5 answer: Bind one surviving Bastille intermediary after the commander/authority break, complete one nonaggression negotiation/service beat proving the Bastille can stand down without serving the Overlord, leave its surviving military infrastructure under Illager control, then explicitly recognize independent nonaggression. This writes NEUTRAL, not COWED.

Why: V5 correctly separates COWED from terminal disposition, but the post-cowed NEUTRAL proof remains undefined.

Source: `docs/V5_CIVILIZATION_SYSTEM_AUTHORITY.md` and `docs/ILLAGER_BASTILLE_INTEGRATION.md`.

Priority: HIGH.

### Q199 - Illagers - SUBJUGATED post-COWED proof

Question: What exact post-COWED leverage should convert the Bastille into a surviving Overlord-aligned Illager military asset?

Proposed V5 answer: Use the surviving bound military intermediary plus one intact Bastille military/logistics function to complete an authored service/command obligation for the Overlord. Once that useful military function is demonstrably operating under his authority, the surviving Bastille leadership accepts supremacy and the anchor becomes SUBJUGATED. Do not pacify unrelated Illagers globally.

Why: The political meaning is fixed but implementation cannot invent which surviving infrastructure/service proves submission.

Source: `docs/V5_CIVILIZATION_SYSTEM_AUTHORITY.md` and Bastille technical integration.

Priority: HIGH.

### Q200 - Illagers - DESTROYED post-COWED proof

Question: What exact additional destruction should turn a COWED Bastille into DESTROYED rather than merely leaving it leaderless?

Proposed V5 answer: After deliberate destructive-route commitment, remove the surviving bound Bastille leadership/intermediary and deliberately disable the campaign-bound military/logistics function that would otherwise allow the Bastille to continue as a polity. Do not require killing every Illager or demolishing the whole Bastille.

Why: V5 requires elimination of leadership and functional polity, but the older implementation only establishes the earlier COWED authority-break state.

Source: `docs/V5_CIVILIZATION_SYSTEM_AUTHORITY.md` and Bastille technical integration.

Priority: HIGH.

## 4. Complete Rambling allocation questions

The workbook for this batch must contain a separate `Rambling Audit` sheet containing the complete candidate surface inspected from the installed advancement-bearing JARs plus source-owned milestones that lack advancement JSON.

Working audit size from the supplied-instance pass:

```text
670 non-hidden displayed native advancements across 37 advancement-bearing JAR sources
+ 28 direct/source-owned milestone entries without equivalent advancement JSON
= 698 candidate events
```

Working proposal classification before Overlord review:

```text
INCLUDE  307
EXCLUDE  391
```

Working proposed presenter distribution among INCLUDE rows:

```text
Gnarl             223
Gristle            28
Mortis             15
Historian           13
Lestat              11
Giblet the Sixth    11
Quaver               5
Grubbison Jr         1
```

These counts and classifications are proposals, not authority. Quest overlap is never an exclusion reason. The workbook must let the Overlord edit individual audit rows as well as approve/modify/reject each presenter-level catalog decision.

### Q201 - Ramblings - Gnarl catalog

Question: Should all audit rows marked INCLUDE with proposed presenter `Gnarl` become Gnarl Ramblings, subject to any individual audit-row edits by the Overlord?

Proposed V5 answer: Yes. Approve the complete proposed Gnarl subset from the `Rambling Audit` sheet. Gnarl receives qualifying general lore, politics, major Adventure, creature, magic and world milestones not owned by an established specialist. Quest-used milestones remain eligible. Completion milestones remain eligible when substantial.

Priority: HIGH.

### Q202 - Ramblings - Mortis catalog

Question: Should all audit rows marked INCLUDE with proposed presenter `Mortis` become Mortis Ramblings, subject to individual audit-row edits?

Proposed V5 answer: Yes. Approve the complete proposed Mortis subset for death, pet death/resurrection, afterlife and directly death-system accomplishments. Do not give Mortis occult or undead milestones merely because death is tangentially present.

Priority: HIGH.

### Q203 - Ramblings - Quaver catalog

Question: Should all audit rows marked INCLUDE with proposed presenter `Quaver` become Quaver Ramblings, subject to individual audit-row edits?

Proposed V5 answer: Yes. Approve the complete proposed Quaver subset for meaningful music, instrument, performance and ensemble completion milestones. Visible Quaver quest overlap does not disqualify a reaction.

Priority: HIGH.

### Q204 - Ramblings - Historian catalog

Question: Should all audit rows marked INCLUDE with proposed presenter `Historian` become Historian Ramblings, subject to individual audit-row edits and preserving the already-approved Fathoms catalog?

Proposed V5 answer: Yes. Preserve Q119, including the whole-fish-collection completion acknowledgement, and approve additional audit rows where the Historian has genuine archaeological/evidence-led commentary. Quest-used Fathoms milestones remain eligible under the global rule.

Priority: HIGH.

### Q205 - Ramblings - Lestat catalog

> **SUPERSEDED BY Q238 and Q250.** Q205 approved Lestat's catalog on the instruction to preserve
> Q121. Q121 is superseded. The instruction points at a rule that no longer exists.

Question: Should all audit rows marked INCLUDE with proposed presenter `Lestat` become Lestat Ramblings, subject to individual audit-row edits and preserving Q121?

Proposed V5 answer: Yes. Preserve Q121's first-acquisition/cure milestones and approve the additional audit rows that genuinely concern vampire identity or Nycto progression. Do not turn blood refills or repeated power use into popup spam.

Priority: HIGH.

### Q206 - Ramblings - Gristle catalog

Question: Should all audit rows marked INCLUDE with proposed presenter `Gristle` become Gristle Ramblings, subject to individual audit-row edits?

Proposed V5 answer: Yes. Approve qualifying food, cuisine, farming and Gluttony accomplishments, including substantial culinary completion milestones even when they are narratively thin. Exclude routine recipe unlock/checklist noise that has no worthwhile reaction or completion significance.

Priority: HIGH.

### Q207 - Ramblings - Grubbison Jr catalog

Question: Should all audit rows marked INCLUDE with proposed presenter `Grubbison Jr` become Grubbison Jr Ramblings, subject to individual audit-row edits?

Proposed V5 answer: Yes. Approve only the qualifying mining-specific milestones identified by the audit. Do not transfer forging or general material-progression milestones to Grubbison merely because mined resources are involved.

Priority: HIGH.

### Q208 - Ramblings - Giblet the Sixth catalog

Question: Should all audit rows marked INCLUDE with proposed presenter `Giblet the Sixth` become Giblet the Sixth Ramblings, subject to individual audit-row edits?

Proposed V5 answer: Yes. Approve qualifying forging, smithing, high-tier crafted-equipment and forge-mastery milestones identified by the audit. Do not claim general mining or unrelated crafting simply because metal is present.

Priority: HIGH.

## 5. Workbook requirements

The Batch 04 workbook should retain the established sheets:

- `Decision Batch`
- `Locked Authority`
- `Technical Queue`
- `Instructions`

and add:

- `Rambling Audit`

The Decision Batch sheet should contain Q161-Q208, giving 48 authored questions.

The `Rambling Audit` sheet should expose at minimum:

```text
source / mod
namespace
advancement or direct milestone ID
native title
native description / source meaning
trigger surface
quest overlap, if any
completion significance
proposed presenter
proposed INCLUDE / EXCLUDE
proposal rationale / exclusion reason
Overlord override
Overlord notes
```

An Overlord edit anywhere on the decision sheet or Rambling Audit sheet is deliberate authority input and must be preserved when the workbook is processed.

## 6. Technical queue after Batch 04

After decisions are returned:

1. pin detector IDs/hooks for approved objectives;
2. translate the approved reward-collection policy to Questlog reward JSON/classes;
3. bind exact civilization providers and persistent facts without inventing new political meaning;
4. pin every approved Rambling to its exact advancement or direct event signal;
5. propagate Batch 03 trigger/reward defaults across the complete visible quest inventory;
6. generate a residual closure register containing only fields still genuinely unresolved;
7. if no authored gaps remain, consolidate the focused authorities into the single implementation-grade V5 campaign system document;
8. if a true authored ambiguity remains, ask only that residual ambiguity rather than manufacturing another broad batch.

## 7. Production boundary

This file is preparation only. Do not implement production quests, provider bindings, reward wiring, runtime detectors, Rambling popups or presenter assets from these proposals before explicit Overlord decisions are processed.