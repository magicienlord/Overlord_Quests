# OVERLORD REIGN V5 — Provider Quest Inventory (Villager Retaliation layer)

Status: DERIVED FROM APPROVED DECISIONS Q050–Q200. Not authority until consolidated and approved.

Total provider quests: **42** across 11 civilizations.

## What these are, and what they are not

These quests are handled entirely by the Villager Retaliation provider layer. **They have no Questlog entries.** Questlog carries only two entries per civilization: the anchor quest and the civilization-state quest. A provider quest is offered, tracked and turned in by its bound NPC (Overlord decision; `NPC_PROVIDER_SYSTEM.md`).

Because they are not visible Questlog quests, they are not bound by the six-field visible-quest schema. Each records instead: its bound provider, its route, the objective specification, the presenter-voice order, the named fact it writes, and its turn-in rule.

## Field contract

**`Objective (specification)`** is the machine-checkable condition. Never shown to the player.

**`Order (presenter voice)`** is the bound provider's own dialogue, authored in the campaign-writing pass. `TO BE AUTHORED` on every row. Note these are **provider** voices, not the eight PNG presenters — ordinary corporeal civilization providers do not enter the presenter roster (`V5_PRESENTER_SYSTEM_AUTHORITY.md` §6).

**`Recorded fact`** is the named world fact the completion writes. Terminal routes read these facts; completing a provider quest never sets NEUTRAL, SUBJUGATED or DESTROYED directly.

**`Reward`** is `NONE` on every row, per Q241.

## Route logic

10 NEUTRAL, 20 SUBJUGATED, 10 DESTROYED, 2 unrouted (Umvuthana Crane and Raptor, which feed the arc rather than a terminal route). SUBJUGATED carries the most because leverage is accumulated across several providers before a leader accepts submission; NEUTRAL and DESTROYED resolve through fewer, heavier beats.


## Spree

### `OR-PRV-SPR-001` — Farmer / NEUTRAL

- **Bound provider:** provider: bound Farmer of the Spree anchor
- **Route:** NEUTRAL
- **Objective (specification):** Cultivate and harvest at least three staple crop types and deliver a mixed local reserve to the bound Farmer, leaving control with Spree
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `SPREE_FARMER_INDEPENDENT`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q053

### `OR-PRV-SPR-002` — Farmer / SUBJUGATED

- **Bound provider:** provider: bound Farmer of the Spree anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Deliver a large mixed reserve of prepared/staple food after the dependency route is revealed, with at least three food classes
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `SPREE_FARMER_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q054

### `OR-PRV-SPR-003` — Miner / NEUTRAL

- **Bound provider:** provider: bound Miner of the Spree anchor
- **Route:** NEUTRAL
- **Objective (specification):** Use the native Ore Grinder to process representative iron/copper/gold material and complete one advanced native Miner trade, leaving the operation locally controlled
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `SPREE_MINER_INDEPENDENT`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q055

### `OR-PRV-SPR-004` — Miner / SUBJUGATED

- **Bound provider:** provider: bound Miner of the Spree anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Complete the Miner dependency chain per Q056
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `SPREE_MINER_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q056

### `OR-PRV-SPR-005` — Guard / NEUTRAL

- **Bound provider:** provider: bound Guard of the Spree anchor
- **Route:** NEUTRAL
- **Objective (specification):** Create and equip a local Guard Villager, assign a defensive patrol/checkpoint, and end the route with that guard no longer following the Overlord
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `SPREE_GUARD_INDEPENDENT`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q057

### `OR-PRV-SPR-006` — Guard / SUBJUGATED

- **Bound provider:** provider: bound Guard of the Spree anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Complete the Guard dependency chain per Q058
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `SPREE_GUARD_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q058

### `OR-PRV-SPR-007` — Mayor / DESTROYED

- **Bound provider:** provider: bound Mayor of the Spree anchor
- **Route:** DESTROYED
- **Objective (specification):** Kill the Mayor and destroy or remove the bound civic pillars that keep the polity functioning, without requiring the death of every villager
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `SPREE_DESTROYED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q059


## Dwarves

### `OR-PRV-DWF-001` — Record Keeper / NEUTRAL

- **Bound provider:** provider: bound Record Keeper of the Dwarves anchor
- **Route:** NEUTRAL
- **Objective (specification):** Receive the surviving historical testimony from the Record Keeper, then prove material continuity by completing one source-backed Dwarven craft sequence combining Dwarven metal with rune craft/forge work in the canonical hold
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `DWARF_CONTINUITY_PROVEN`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q060, Q171

### `OR-PRV-DWF-002` — Forger / SUBJUGATED

- **Bound provider:** provider: bound Forger of the Dwarves anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Complete a high-tier forge commission using scarce material supplied by the Overlord, then convert that dependency into an explicit production/service commitment to the Master
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `DWARF_FORGER_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q061

### `OR-PRV-DWF-003` — Warrior / DESTROYED

- **Bound provider:** provider: bound Warrior of the Dwarves anchor
- **Route:** DESTROYED
- **Objective (specification):** Use the Warrior chain to expose the hold's defensive vulnerability, then defeat the Forge-Thane and the bound military authority. Do not require killing every Dwarf
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `DWARF_DESTROYED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q062


## Gnumus

### `OR-PRV-GNU-001` — Vintage reconstruction / NEUTRAL

- **Bound provider:** provider: bound Vintage reconstruction of the Gnumus anchor
- **Route:** NEUTRAL
- **Objective (specification):** After the ancestry decision, complete the native Vintage reconstruction progression through Vintage Alloy, one Vintage equipment improvement, and the Vintage Universal Tool milestone, then leave the reconstructed technology under Gnumu control
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `GNUMU_INDEPENDENT`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q174

### `OR-PRV-GNU-002` — Main Merchant / SUBJUGATED

- **Bound provider:** provider: bound Main Merchant of the Gnumus anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Complete a bound Main Merchant trade, then supply the scarce Vintage material needed for one later Vintage reconstruction the settlement has not independently completed, and record the continuing access/service obligation
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `GNUMU_MERCHANT_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q175

### `OR-PRV-GNU-003` — Elder Shaman / DESTROYED

- **Bound provider:** provider: bound Elder Shaman of the Gnumus anchor
- **Route:** DESTROYED
- **Objective (specification):** After destructive commitment, require the Elder Shaman's death plus removal of the bound Main Merchant and the bound Hunter/defense authority. Do not require killing ordinary workers or destroying every settlement block
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `GNUMU_DESTROYED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q176


## Goblins

### `OR-PRV-GOB-001` — Merchant + Bartender / NEUTRAL

- **Bound provider:** provider: bound Merchant + Bartender of the Goblins anchor
- **Route:** NEUTRAL
- **Objective (specification):** Complete one meaningful native Merchant transaction satisfying the merchant-success surface and one native Bartender/liquor interaction satisfying the liquor-success surface, each with its bound provider
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `GOBLIN_INDEPENDENT`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q177

### `OR-PRV-GOB-002` — Engineer / SUBJUGATED

- **Bound provider:** provider: bound Engineer of the Goblins anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Complete one source-owned Engineer workbench project reaching the native engineer-success / prototype-upgrade surface using Overlord-supplied materials, then record the continuing technology/service obligation
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `GOBLIN_ENGINEER_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q178

### `OR-PRV-GOB-003` — Blacksmith / SUBJUGATED

- **Bound provider:** provider: bound Blacksmith of the Goblins anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Supply the bound Blacksmith with the high-value inputs required by its native advanced service path, including the Netherite/Engineer material bridge, complete one advanced Blacksmith service, then record the arms/production obligation
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `GOBLIN_BLACKSMITH_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q179

### `OR-PRV-GOB-004` — Merchant / SUBJUGATED

- **Bound provider:** provider: bound Merchant of the Goblins anchor
- **Route:** SUBJUGATED
- **Objective (specification):** After the ordinary trade surface is demonstrated, complete one higher-value patronage transaction supplied by the Overlord and record preferential access/commerce obligation
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `GOBLIN_MERCHANT_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q180

### `OR-PRV-GOB-005` — Leader / DESTROYED

- **Bound provider:** provider: bound Leader of the Goblins anchor
- **Route:** DESTROYED
- **Objective (specification):** Complete the Goblin destructive route per Q070
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `GOBLIN_DESTROYED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q070


## Kobolds

### `OR-PRV-KOB-001` — Captain / SUBJUGATED

- **Bound provider:** provider: bound Captain of the Kobolds anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Complete the Captain resource/logistics proof
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `KOBOLD_CAPTAIN_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q071, Q181

### `OR-PRV-KOB-002` — Engineer / SUBJUGATED

- **Bound provider:** provider: bound Engineer of the Kobolds anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Complete one meaningful infrastructure/engineering commission for the Den rather than a generic item hand-in
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `KOBOLD_ENGINEER_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q072, Q182

### `OR-PRV-KOB-003` — Enchanter / SUBJUGATED

- **Bound provider:** provider: bound Enchanter of the Kobolds anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Complete the Enchanter service
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `KOBOLD_ENCHANTER_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q073, Q183

### `OR-PRV-KOB-004` — Den authority / DESTROYED

- **Bound provider:** provider: bound Den authority of the Kobolds anchor
- **Route:** DESTROYED
- **Objective (specification):** Destroy or disable the indispensable Den function identified by Q184
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `KOBOLD_DESTROYED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q074, Q184


## Ribbits

### `OR-PRV-RIB-001` — — / NEUTRAL

- **Bound provider:** provider: bound — of the Ribbits anchor
- **Route:** NEUTRAL
- **Objective (specification):** Complete the Ribbit prosperity proof
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `RIBBIT_INDEPENDENT`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q075, Q185

### `OR-PRV-RIB-002` — Merchant / SUBJUGATED

- **Bound provider:** provider: bound Merchant of the Ribbits anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Complete the Merchant commitment
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `RIBBIT_MERCHANT_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q076, Q186

### `OR-PRV-RIB-003` — Fisherman / SUBJUGATED

- **Bound provider:** provider: bound Fisherman of the Ribbits anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Complete the Fisherman commitment
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `RIBBIT_FISHERMAN_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q077, Q187

### `OR-PRV-RIB-004` — Sorcerer / SUBJUGATED

- **Bound provider:** provider: bound Sorcerer of the Ribbits anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Complete the Sorcerer commitment
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `RIBBIT_SORCERER_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q078, Q188

### `OR-PRV-RIB-005` — — / DESTROYED

- **Bound provider:** provider: bound — of the Ribbits anchor
- **Route:** DESTROYED
- **Objective (specification):** Complete the Ribbit destructive route
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `RIBBIT_DESTROYED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q079


## Sea Dwellers

### `OR-PRV-SEA-001` — Blacksmith / SUBJUGATED

- **Bound provider:** provider: bound Blacksmith of the Sea Dwellers anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Complete the Blacksmith commitment
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `SEADWELLER_BLACKSMITH_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q080, Q189

### `OR-PRV-SEA-002` — Collector / SUBJUGATED

- **Bound provider:** provider: bound Collector of the Sea Dwellers anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Complete the Collector commitment
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `SEADWELLER_COLLECTOR_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q081, Q190

### `OR-PRV-SEA-003` — Institutional / SUBJUGATED

- **Bound provider:** provider: bound Institutional of the Sea Dwellers anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Complete the institutional leverage proof
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `SEADWELLER_INSTITUTIONAL_LEVERAGE`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q082, Q191

### `OR-PRV-SEA-004` — Civic core / DESTROYED

- **Bound provider:** provider: bound Civic core of the Sea Dwellers anchor
- **Route:** DESTROYED
- **Objective (specification):** Destroy the civic/resource core identified by Q192
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `SEADWELLER_DESTROYED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q083, Q192


## Piglins

### `OR-PRV-PIG-001` — — / NEUTRAL

- **Bound provider:** provider: bound — of the Piglins anchor
- **Route:** NEUTRAL
- **Objective (specification):** Complete the Piglin NEUTRAL exact proof
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `PIGLIN_INDEPENDENT`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q085, Q193

### `OR-PRV-PIG-002` — Chieftain / SUBJUGATED

- **Bound provider:** provider: bound Chieftain of the Piglins anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Demonstrate control over valuable gold/barter supply and key village resource infrastructure, then obtain Chieftain submission
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `PIGLIN_CHIEFTAIN_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q086, Q194

### `OR-PRV-PIG-003` — — / DESTROYED

- **Bound provider:** provider: bound — of the Piglins anchor
- **Route:** DESTROYED
- **Objective (specification):** Destroy the protected order identified by Q195
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `PIGLIN_DESTROYED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q087, Q195


## Umvuthana

### `OR-PRV-UMV-001` — Crane / —

- **Bound provider:** provider: bound Crane of the Umvuthana anchor
- **Route:** —
- **Objective (specification):** Complete the Crane healing/support dependency
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `UMVUTHANA_CRANE`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q088, Q196

### `OR-PRV-UMV-002` — Raptor / —

- **Bound provider:** provider: bound Raptor of the Umvuthana anchor
- **Route:** —
- **Objective (specification):** Complete the Raptor martial allegiance challenge
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `UMVUTHANA_RAPTOR`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q089, Q197


## Illagers

### `OR-PRV-ILL-001` — — / NEUTRAL

- **Bound provider:** provider: bound — of the Illagers anchor
- **Route:** NEUTRAL
- **Objective (specification):** Complete the post-COWED NEUTRAL proof
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `ILLAGER_INDEPENDENT`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q198

### `OR-PRV-ILL-002` — — / SUBJUGATED

- **Bound provider:** provider: bound — of the Illagers anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Complete the post-COWED SUBJUGATED proof
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `ILLAGER_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q199

### `OR-PRV-ILL-003` — — / DESTROYED

- **Bound provider:** provider: bound — of the Illagers anchor
- **Route:** DESTROYED
- **Objective (specification):** Complete the post-COWED DESTROYED proof
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `ILLAGER_DESTROYED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q200


## Myrmex

### `OR-PRV-MYR-001` — Canonical hive / NEUTRAL

- **Bound provider:** provider: bound Canonical hive of the Myrmex anchor
- **Route:** NEUTRAL
- **Objective (specification):** Give resin to a worker and complete one native trade with the canonical hive, continue native opinion to 50+, then explicitly recognize the Queen and hive as independent
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `MYRMEX_INDEPENDENT`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q090, Q140

### `OR-PRV-MYR-002` — Queen / SUBJUGATED

- **Bound provider:** provider: bound Queen of the Myrmex anchor
- **Route:** SUBJUGATED
- **Objective (specification):** Reach 75+ opinion, obtain and use the Myrmex Staff on the canonical hive to designate one new FOOD or NURSERY room, then complete the Queen-facing submission resolution
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `MYRMEX_OBLIGATED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q091, Q141

### `OR-PRV-MYR-003` — Queen / DESTROYED

- **Bound provider:** provider: bound Queen of the Myrmex anchor
- **Route:** DESTROYED
- **Objective (specification):** The canonical Queen's deliberate death is sufficient
- **Order (presenter voice):** TO BE AUTHORED — bound provider dialogue, campaign-writing pass
- **Recorded fact:** `MYRMEX_DESTROYED`
- **Turn-in:** same_provider
- **Reward:** NONE — V5 authors no quest rewards (Q241)
- **Source:** Q092, Q139