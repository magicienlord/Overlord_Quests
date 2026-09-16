# OVERLORD REIGN V5 Goblin Civilization Blueprint

Status: WORKING V5 CAMPAIGN AUTHORITY

Date: 2026-09-16

Authority relationship: this document is a focused civilization blueprint under `docs/V5_CAMPAIGN_SYSTEM_AUTHORITY.md` and `docs/V5_CIVILIZATION_SYSTEM_AUTHORITY.md`. It records explicit V5 decisions approved by the Overlord and exact source-backed mechanics from the installed Goblins Tyranny 1.2.3 build.

## 1. Canonical anchor identity

The canonical Goblin polity is one major generated Goblin Camp.

The exact installed source provides the generated structure:

```text
goblins_tyranny:goblinscamp
```

The selected camp is a local polity. It does not represent all Goblins globally.

Goblins continue to occupy and scavenge abandoned human regions elsewhere in the world. Other camps remain independent unless explicitly affected by another authored quest.

## 2. Runtime anchor lock

The deliberate anchor-start action is a Questlog provider interaction with a native Goblin Leader inside an eligible major camp.

Required pattern:

```text
find an eligible goblins_tyranny:goblinscamp
-> deliberately use the Questlog provider interaction on that camp's goblins_tyranny:leader_goblin
-> validate that no Goblin anchor is already locked
-> bind the generated camp as the canonical Goblin anchor
-> bind the interacted Leader as the camp's political authority
-> bind or spawn the minimum recurring service cast required by the blueprint
-> expose the Goblin civilization Questlog
```

Ordinary Goblin Leaders elsewhere remain ordinary leaders and do not become alternate civilization starters.

## 3. Political spine

The approved Goblin political model is opportunism and material self-interest.

The camp is held together because it is useful, profitable, entertaining and materially worthwhile to its inhabitants.

Its civilization arc should therefore expose the camp through the services and people that make Goblin life function rather than through an invented ideological crisis.

The source provides an unusually rich practical cast and progression surface, including:

- Leader;
- Champion;
- Merchant;
- Engineer and Engineeress;
- Blacksmith;
- Shaman;
- Bartender;
- Bard;
- Huntsman and Hunter;
- Knight;
- Wanderer;
- disguise, liquor, engineering, prototype, bomb and commerce systems.

These source roles are ingredients. V5 does not automatically promote all of them into mandatory recurring providers.

## 4. Minimum recurring cast

### 4.1 Goblin Leader

Source basis:

```text
goblins_tyranny:leader_goblin
```

Purpose:

- political starter;
- final authority for independent or submitted resolution;
- protected from accidental loss until a destructive route explicitly permits violence.

### 4.2 Main Merchant

Source basis:

```text
goblins_tyranny:merchant
```

Purpose:

- represents ordinary profit and camp commerce;
- can expose the practical value of peaceful coexistence;
- can become a dependency or patronage lever in SUBJUGATED.

The source-owned `goblins_tyranny:merchant_success` advancement is a durable commerce signal.

### 4.3 Camp Engineer

Source basis:

```text
goblins_tyranny:engineer_goblin
OR
goblins_tyranny:engineeress_goblin
```

Purpose:

- represents engineering, prototypes, explosives and practical infrastructure;
- can expose how much of the camp's useful material power depends on access to resources, patronage and specialist work;
- can become a major leverage point in SUBJUGATED or a vulnerability in DESTROYED.

The source-owned `goblins_tyranny:engineer_success` advancement is a durable native signal for using the Engineer Workbench.

### 4.4 Camp Bartender

Source basis:

```text
goblins_tyranny:bartender_goblin
```

Purpose:

- represents tavern life and the social economy of the camp;
- provides a non-military path for learning how Goblins respond to profit, indulgence and local relationships;
- can support the independent coexistence route without becoming a hidden friendship meter.

The source-owned `goblins_tyranny:liquor_success` advancement records a successful native liquor purchase.

The minimum cast may be expanded only when another native role is genuinely required by a later authored route. V5 must not create filler providers merely because the mod exposes many Goblin classes.

## 5. NEUTRAL route

The independent route is based on mutual profit and practical coexistence.

Approved structure:

```text
anchor locked
-> establish useful relationships with the camp's ordinary service economy
-> complete meaningful merchant and tavern/local-business content
-> demonstrate that peaceful coexistence is more profitable than conflict
-> final negotiation with the Goblin Leader
-> deliberately settle on continued independent trade and nonaggression
-> NEUTRAL
```

The camp remains politically independent.

The route is not friendship through accumulated sidequests. The final settlement is an explicit authored political agreement.

## 6. SUBJUGATED route

Subjugation makes key parts of the camp materially dependent on the Overlord.

Approved structure:

```text
anchor locked
-> identify the providers and services that make the camp prosperous
-> complete specific Engineer, Merchant and other required provider chains
-> place critical resources, patronage, protection or opportunities under Overlord control
-> write explicit provider-specific dependency or obligation facts
-> demonstrate that important camp institutions function better through Overlord patronage than through independence
-> confront the Goblin Leader with the practical cost of refusing
-> Leader accepts Overlord supremacy while retaining local office
-> SUBJUGATED
```

The motivation is greed, security and practical advantage rather than ideology or religious humiliation.

The route may structurally resemble another provider-leverage campaign, but its characterization and reasons must remain distinctly Goblin.

Established low-overhead consequences may include:

- safe use of the canonical camp;
- merchants, engineers, craftsmen, shamans, tavern and other appropriate services;
- improved prices where source mechanics support them cleanly;
- tribute;
- continued provider content.

No simulated Goblin army, worker economy or production network is required.

## 7. DESTROYED route

The destructive route uses knowledge gained from the same camp services and infrastructure to dismantle the canonical polity deliberately.

Approved meaning:

```text
learn the camp's leadership, engineering and defensive vulnerabilities
-> choose the destructive route
-> exploit those known vulnerabilities through source-backed objectives
-> remove the camp's governing and functional continuity
-> DESTROYED
```

Native bombs, engineering systems and combat content may participate where they genuinely fit.

The route must not reduce to a generic Goblin kill quota.

Destroying the canonical camp does not make Goblins extinct elsewhere.

## 8. Source-owned objective surfaces available for detailed authoring

The exact installed Goblins Tyranny build exposes useful durable signals including:

```text
goblins_tyranny:goblins_encounter_success
goblins_tyranny:goblins_disguise_success
goblins_tyranny:merchant_success
goblins_tyranny:liquor_success
goblins_tyranny:wanderer_success
goblins_tyranny:engineer_success
goblins_tyranny:prototype_success
goblins_tyranny:upgrade_success
goblins_tyranny:wake_up_success
goblins_tyranny:glitteron_success
goblins_tyranny:bomb_success
goblins_tyranny:fire_success
goblins_tyranny:poison_success
goblins_tyranny:goblins_slayer_success
```

The source also supplies the Engineer Workbench, multiple bomb types, Goblin Prototype progression, merchant and bartender purchase systems, disguise content, Glitteron and numerous specialist NPC classes.

These are not a checklist. Detailed V5 objectives should select only those that express an approved political or characterization purpose.

## 9. Goblin and Minion characterization

Goblins and Minions may continue to view one another as something like distant cousins.

This remains a cultural belief rather than established biological or historical truth.

The civilization blueprint may use that perception for characterization or dialogue but must not canonize shared ancestry without a later explicit decision.

## 10. Presenter use

The Leader, Merchant, Engineer, Bartender and any later approved recurring provider enter the final presenter roster if V5 assigns them direct Questlog speech.

They use the universal five-state PNG vocabulary:

```text
neutral
pleased
assertive
concerned
hostile
```

## 11. Remaining authoring boundary

The political spine and minimum provider categories are approved at the campaign level.

Still unresolved are the exact gameplay actions that establish:

- which merchant/tavern accomplishments are necessary for the NEUTRAL settlement;
- the specific Engineer and Merchant dependencies required by SUBJUGATED;
- whether another source-native specialist is required as a third submission pillar;
- the exact source-backed sabotage or combat sequence that constitutes DESTROYED.

Those questions must be narrowed through the installed mod and existing REIGN lore. Any choice that creates new political meaning must return to the Overlord before becoming V5 authority.

## 12. Production boundary

This blueprint is V5 campaign authority only.

Do not reconcile current production quests, provider bindings, NPC spawning, objective definitions, camp protection or disposition code until the V5 campaign authority pass is complete and approved.