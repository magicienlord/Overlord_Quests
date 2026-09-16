# OVERLORD REIGN V5 Batch 02 Technical Follow-up

Status: VERIFIED TECHNICAL FACT / OPEN AUTHORING INPUT

Date: 2026-09-16

Authority relationship:

- `docs/V5_DECISION_BATCH_02_AUTHORITY.md` remains the authored decision authority.
- `docs/V5_BATCH_02_RECONCILIATION_2026-09-16.md` remains the closure overlay.
- This file records technical/source research performed after the six Batch 02 rejections.
- Nothing in this file selects a replacement authored objective or Rambling allocation without explicit Overlord approval.

## 1. Scope

Batch 02 left exactly two research families open:

1. Q106-Q110: replace rejected authored Bosses'Rise Cataclysm evidence props with native boss-linked content or behavior.
2. Q115: replace rejected L_Ender's Cataclysm structure-discovery Ramblings with native milestones that do not duplicate visible quests.

The rejection constraints remain binding.

## 2. Bosses'Rise source boundary

The exact installed target remains:

```text
block_factorys_bosses-2.1.2-forge-1.20.1.jar
```

The supplied local instance archives available to this V5 session do not contain that JAR. Therefore this pass does not claim direct local-JAR verification of new Bosses'Rise item/loot facts.

The existing V5 supplied-mod audit already records the exact five boss entity IDs, five native kill advancements, and five structure IDs from the previously supplied 2.1.2 artifact.

For the rejected investigation replacements, this follow-up uses current 2.1.2 public release documentation and boss-specific reference material only to identify candidate native surfaces. Any candidate that depends on a loot-table detail not already directly verified must be rechecked against the exact 2.1.2 JAR before implementation.

### 2.1 Verified release-level native structure facts

Bosses'Rise 2.0.0 release documentation states that boss structures contain native Vaults and Trial Spawners and added Ancient Vault Keys for those Vaults.

The same release documentation states that Helvar's Underworld dungeon contains a large boss door requiring a special key found inside the dungeon.

Bosses'Rise 2.1.0 release documentation states that Nerakyss's Kraken Ship is guarded by three pirate skeleton variants and that Pirate Sabers are dropped by killed pirates.

Current 2.1.2 item references expose these relevant native identifiers:

```text
block_factorys_bosses:ancient_trial_key
block_factorys_bosses:underworld_arena_key
block_factorys_bosses:dragon_banner
block_factorys_bosses:pirate_saber
block_factorys_bosses:ice_gauntlet
block_factorys_bosses:sandworm_gauntlet
block_factorys_bosses:knight_sword
block_factorys_bosses:dragon_skull
block_factorys_bosses:dragon_bone
block_factorys_bosses:kraken_tooth
```

The named gauntlets, Helvar's Sword, Ashlord remains, and Kraken Teeth are boss-drop or post-boss surfaces and therefore do not satisfy the already-approved pre-kill investigation stage by themselves.

## 3. Q106 Skor candidate surfaces

Authored requirement already approved by Batch 01:

```text
locate Skor's frozen/Nordberg domain
-> establish evidence of Cataclysm-rift influence before the kill
-> Gnarl interprets Skor as a native creature empowered by the wound
-> defeat Skor
```

Rejected in Batch 02: any authored rift scar, residue object, custom evidence prop, or equivalent fabricated investigation object.

### Candidate A: native Trial Spawner / Vault proof

Use the Yeti Hideout's native dungeon progression itself:

```text
inside the bound Yeti Hideout
-> defeat one native Trial Spawner encounter
-> obtain/use the native Ancient Trial Key on a Vault in that same domain
-> Gnarl interprets the domain evidence
```

Strengths:

- entirely native content;
- pre-kill;
- trackable through structure binding plus native key/vault interaction if exact source exposes a sufficiently specific signal;
- no authored evidence object.

Weakness:

- the key is generic Bosses'Rise dungeon equipment, not uniquely Skor-specific.

### Candidate B: native Skor behavior proof

Use a boss-specific pre-death combat phenomenon instead of an item:

```text
engage Skor without killing him
-> witness/receive one qualifying Phase 2 frost or icicle attack event
-> Gnarl interprets the abnormal empowerment
-> later defeat Skor
```

Public 2.0+ release notes explicitly document Skor's frost application, icicle-generating attacks, and second phase.

Strengths:

- uniquely Skor-specific;
- fully native behavior;
- directly demonstrates unusual empowered capability.

Weakness:

- likely requires a narrow source-aware detector because no dedicated packaged advancement has yet been verified for the exact event.

Status: PROPOSAL CANDIDATES ONLY. No candidate is selected.

## 4. Q107 Sirok candidate surfaces

Authored requirement already approved by Batch 01:

```text
locate Sirok's desert/Ruborian domain
-> establish evidence that the worm is unnaturally enlarged or energized before the kill
-> Gnarl interprets the evidence
-> defeat Sirok
```

Rejected in Batch 02: authored residue, custom rift contamination object, or equivalent fabricated evidence prop.

### Candidate A: native Trial Spawner / Vault proof

Use one native Trial Spawner and Vault interaction inside the bound Sandworm Nest, with the same constraints as Skor Candidate A.

Strengths:

- native and pre-kill;
- no fabricated evidence.

Weakness:

- generic across Bosses'Rise structures rather than Sirok-specific.

### Candidate B: native armor-break / poison-blood proof

Bosses'Rise 2.0+ release documentation describes Sirok's native segmented armor-break mechanic: sufficiently damaged segments crack, and striking a cracked segment again causes poisonous blood to spill before the worm dives and heals the damage.

Possible proof:

```text
engage Sirok without killing him
-> crack one segment
-> trigger one poisonous-blood spill from that cracked segment
-> Gnarl interprets the abnormal physiology/energy
-> later defeat Sirok
```

Strengths:

- uniquely Sirok-specific;
- native behavior;
- pre-kill;
- materially different from the final defeat objective.

Weakness:

- requires exact source inspection to identify a durable detector or the least invasive narrow compatibility hook.

Status: PROPOSAL CANDIDATES ONLY. No candidate is selected.

## 5. Q108 Ashlord candidate surfaces

Authored requirement already approved by Batch 01:

```text
locate the Dragon Tower
-> establish enough historical dragon evidence to identify Ashlord as the first resurrected dragon
-> Gnarl interprets the evidence
-> defeat Ashlord
```

Rejected in Batch 02: authored scorch evidence, resurrection relic, custom rift object, or equivalent fabricated prop.

### Candidate A: native Dragon Banner recovery

The native item/block:

```text
block_factorys_bosses:dragon_banner
```

is documented as generating in the Dragon Tower.

Possible proof:

```text
inside the bound Dragon Tower
-> recover one native Dragon Banner from the structure
-> Gnarl recognizes the tower's dragon heraldry/history and interprets Ashlord's identity
```

Strengths:

- native;
- structure-specific;
- pre-kill;
- item-based, matching the Overlord's replacement direction;
- no fabricated lore prop is added.

Limitation:

- the banner itself does not source-own the V5 claim that Ashlord is the first resurrected dragon. That historical interpretation remains authored Gnarl knowledge already approved in V5.

### Candidate B: native Trial Spawner / Vault proof

Use Dragon Tower Trial Spawner and Vault progression as the mechanical evidence trigger, with Gnarl supplying the already-approved historical interpretation.

This is more generic and therefore weaker than Candidate A.

Status: PROPOSAL CANDIDATES ONLY. No candidate is selected.

## 6. Q109 Helvar candidate surfaces

Authored requirement already approved by Batch 01:

```text
locate the Underworld domain
-> establish Helvar's identity as the Third Overlord before the kill
-> Gnarl recognizes/interprets him
-> defeat Helvar
```

Rejected in Batch 02: authored insignia, custom identity relic, rift scar, or equivalent fabricated evidence prop.

### Candidate A: native Underworld Arena Key

The native item:

```text
block_factorys_bosses:underworld_arena_key
```

is tied to the Underworld dungeon. Official 2.0.0 release notes state that the boss door requires a special key found within that dungeon.

Possible proof:

```text
explore the bound Underworld dungeon
-> obtain the native arena key
-> reach/unlock the boss door
-> Gnarl recognizes the arena/occupant context and identifies Helvar before the fight
```

Strengths:

- native;
- domain-specific;
- pre-kill;
- already part of the boss access flow;
- does not invent an evidence object.

Limitation:

- the key does not source-own Helvar's Third Overlord identity. The identity recognition remains the already-approved V5 historical interpretation supplied by Gnarl.

### Candidate B: native Trial Spawner / Vault proof

Use native Underworld dungeon Trial Spawner/Vault progression before the boss door. This is valid native content but less identity-specific than Candidate A.

Status: PROPOSAL CANDIDATES ONLY. No candidate is selected.

## 7. Q110 Nerakyss candidate surfaces

Authored requirement already approved by Batch 01:

```text
locate the oceanic/Kraken domain
-> establish long-term contamination/leakage context before the kill
-> Gnarl interprets Nerakyss as one result
-> defeat Nerakyss
```

Rejected in Batch 02: authored contaminated seawater samples, custom corruption objects, or equivalent fabricated props.

### Candidate A: native pirate-guard sequence

Bosses'Rise 2.1.0 documentation states that the Kraken Ship is guarded by three pirate skeleton variants and that clearing those guards precedes Nerakyss's appearance.

Possible proof:

```text
enter the bound Kraken Ship
-> defeat the native Pirate Captain
-> defeat the native Pirate Rook
-> defeat the native Crossbow Pirate
-> Nerakyss's source-owned appearance sequence begins
-> Gnarl interprets the corrupted/occupied oceanic domain
```

Strengths:

- entirely native;
- uniquely tied to the Kraken Ship encounter;
- pre-kill;
- materially different from killing Nerakyss.

Weakness:

- exact 2.1.2 detector availability for the three guard deaths still requires direct JAR inspection.

### Candidate B: native Pirate Saber recovery

Public 2.1.0 documentation states that Pirate Sabers are dropped by killed pirates.

Possible proof:

```text
inside the bound Kraken Ship
-> recover one native Pirate Saber from its pirate defenders
-> Gnarl interprets the ship/crew evidence
-> later confront Nerakyss
```

Strengths:

- native item;
- pre-kill;
- directly tied to the boss domain.

Weakness:

- it is crew-specific rather than Nerakyss-specific, and possession alone must be bound to acquisition inside the selected Kraken Ship to avoid false positives.

Status: PROPOSAL CANDIDATES ONLY. No candidate is selected.

## 8. Bosses'Rise research conclusion

The Batch 02 rejection can be honored without authored evidence props.

Current strongest native candidates, subject to Overlord approval, are:

```text
Skor      -> Skor frost/icicle behavior, or Yeti Hideout Trial/Vault proof
Sirok     -> armor-break poison-blood behavior, or Sandworm Nest Trial/Vault proof
Ashlord   -> Dragon Banner from Dragon Tower
Helvar    -> Underworld Arena Key / boss-door progression
Nerakyss  -> three pirate-guard sequence, optionally Pirate Saber recovery
```

The Ashlord, Helvar, and Nerakyss candidates have comparatively strong native domain identity.

Skor and Sirok do not currently have equally strong verified pre-kill boss-specific items. Their boss-drop gauntlets are invalid as investigation proofs because they occur after the kill. For those two, native behavior is the cleaner boss-specific alternative unless exact 2.1.2 JAR inspection reveals a better pre-kill item.

No replacement is authorized by this file.

## 9. L_Ender's Cataclysm exact JAR audit

The supplied instance contains:

```text
L_Enders_Cataclysm-3.31.jar
SHA-256 c29dcf940168208517b72f2d9965828b106a6b94cbea55d5001558c69a27b362
```

This follow-up directly inspected the packaged advancement JSON and English localization from that exact JAR.

### 9.1 Structure discoveries already consumed by visible quests

The exact JAR contains eight structure-discovery advancements:

```text
cataclysm:find_acropolis
cataclysm:find_ancient_factory
cataclysm:find_burning_arena
cataclysm:find_cursed_pyramid
cataclysm:find_frosted_prison
cataclysm:find_ruined_citadel
cataclysm:find_soul_black_smith
cataclysm:find_sunken_city
```

Q115 correctly rejects using those same first-discovery achievements as Ramblings because the V5 Cataclysm Adventure already uses the structures as visible boss-quest discovery/progression material.

### 9.2 Major boss kills are visible quest completions

The exact native all-boss advancement requires these eight bosses:

```text
cataclysm:ignis
cataclysm:the_leviathan
cataclysm:the_harbinger
cataclysm:ender_guardian
cataclysm:maledictus
cataclysm:ancient_remnant
cataclysm:netherite_monstrosity
cataclysm:scylla
```

Batch 01 already assigns one visible quest per major Cataclysm boss and the native all-boss completion as the Adventure capstone.

Therefore the eight individual major-boss kill advancements and `cataclysm:kill_all_bosses` are not clean Rambling triggers because they are already visible quest/capstone completions.

This conclusion follows the general V5 anti-duplication rule, not the rejected Q115 sentence that attempted to exclude boss-kill Ramblings categorically.

### 9.3 Clean non-major native advancement candidates

The exact 3.31 JAR exposes three additional first-kill advancements that are not part of the eight-boss `kill_all_bosses` criterion set:

```text
cataclysm:kill_ender_golem
  title: Palette Swap
  description: Kill an Ender Golem
  parent: cataclysm:find_ruined_citadel

cataclysm:kill_revenant
  title: Imperfect thing
  description: Kill an Ignited Revenant
  parent: cataclysm:find_burning_arena

cataclysm:kill_clawdian
  title: Not so Shrimple Now
  description: Kill an Clawdian
  parent: cataclysm:find_acropolis
```

These are the strongest technically clean Q115 replacement candidates because they are:

- native one-time advancements;
- not structure-discovery triggers;
- not members of the eight-major-boss capstone set;
- naturally contextual to Cataclysm domains;
- separable from the visible major-boss objectives.

Possible authoring choices for a later decision batch:

```text
A. Use all three as sparse one-time Gnarl Ramblings.
B. Use a selected subset if three reactions are too dense.
C. Use none and leave Cataclysm without extra Ramblings beyond its visible Adventure line.
```

No choice is authorized here.

### 9.4 Speaker

Under the locked presenter authority, Cataclysm does not have a dedicated specialist presenter.

If any of these milestones are later approved for Rambling treatment, Gnarl is the default speaker unless a later explicit V5 decision assigns an already-approved specialist for a specific reason.

No new presenter is created.

## 10. Batch 03 input produced by this research

The next decision batch can now ask narrowly rather than reopening broad design.

Required authored decisions:

1. select the native investigation proof for each of Q106-Q110 from source-compatible options after any final 2.1.2 detector/JAR check;
2. decide whether Cataclysm should use all, some, or none of the three clean non-major advancement Ramblings;
3. continue the already-prepared trigger/reward policy decisions.

The technical pass must still verify exact detector hooks for whichever Bosses'Rise option is selected.

## 11. Production boundary

This file does not authorize production quest edits, new authored evidence objects, Rambling registration, detector implementation, or Bosses'Rise compatibility code.

It exists only to convert the Batch 02 rejections into source-backed options for explicit V5 authoring decisions.