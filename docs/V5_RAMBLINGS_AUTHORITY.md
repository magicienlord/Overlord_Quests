# OVERLORD REIGN V5 Ramblings Authority

Status: V5 PRESENTATION AUTHORITY

Purpose: define when native achievements, discoveries and later milestones receive one-time character popup reactions instead of becoming Questlog beats.

Ramblings are contextual character reactions. They are not miniature quests, reminder systems, hidden completion tracks or a second achievement interface.

No final Rambling dialogue is authored here. This file defines ownership, triggering and presentation boundaries.

## 1. Core rule

A native event should become a Rambling only when the event gains meaningful OVERLORD REIGN context from an established speaker.

Good Rambling triggers include:

- first discovery of a historically meaningful structure;
- first defeat of a named or exceptional enemy with established REIGN context;
- a major source-owned achievement deliberately left outside Questlog;
- a significant relic or transformation;
- completion of the opposite native mastery path after a V5 OR closer has already resolved;
- an unusual native accomplishment that gives a known character something specific and worthwhile to say.

A trigger is not sufficient merely because an advancement exists.

## 2. Explicit non-goals

V5 does not create Ramblings for:

- every advancement;
- every mob variant;
- every repeated boss kill;
- every structure copy;
- every ordinary item acquisition;
- recipe unlock spam;
- routine progression already explained adequately by the active Questlog beat;
- periodic reminders about incomplete quests;
- generalized Gnarl nagging;
- hidden percentage or completion scoring.

Ramblings must remain sparse enough that an appearance still feels authored.

## 3. Trigger semantics

Each authored Rambling is normally:

- source-backed;
- one-time per player unless a focused authority explicitly requires otherwise;
- retrospective only when the durable source event can be proven and the context still makes sense;
- non-blocking;
- incapable of completing or reopening a Questlog beat by itself unless a separate focused authority explicitly uses the same source event for both purposes;
- incapable of changing a permanent civilization disposition merely through presentation.

If a native event is already the visible completion of a Questlog beat, an additional Rambling should exist only when it adds distinct character or historical context rather than repeating the quest completion text.

## 4. Speaker selection

Use the character with the strongest established reason to interpret the event.

Default ownership:

- Gnarl: strategic history, old regions, Overlord history, Cataclysm interpretation, campaign significance;
- Mortis: death, undead bodies, ghosts, pet resurrection and closely related occult observations;
- Quaver: music, court culture and entertainment;
- Lestat: vampirism and later NightWalker observations;
- Fathoms Historian: maritime evidence, decoded Fathoms material and source-specific historical investigation;
- civilization speakers: facts local to their canonical polity where direct local interpretation is more appropriate than Gnarl.

Provider origin and presenter voice may differ. A native advancement can be sourced from one mod while the popup is spoken by the REIGN character best qualified to interpret it.

Do not create a new speaking NPC solely because an advancement needs commentary if an established presenter can carry the meaning naturally.

## 5. Required Cataclysm Ramblings

L_Ender's Cataclysm is the strongest mandatory Rambling use case in V5.

The Adventure uses broad Questlog chapters and the native `cataclysm:kill_all_bosses` capstone. Individual structure and boss achievements provide the more precise historical commentary requested by the Overlord.

### 5.1 Ancient Netherworld infrastructure

Required source context:

- `cataclysm:find_soul_black_smith`
- `cataclysm:kill_monstrosity`

Rambling purpose:

- establish the Soul Forge and Netherite Monstrosity as ancient pre-Cataclysm Netherworld industrial / construct infrastructure tied to an older Overlord / Minion era;
- clarify that later Piglin occupation does not make Piglins the builders.

Preferred presenter:

- Gnarl.

### 5.2 Burning Arena and Ignis

Required source context:

- `cataclysm:find_burning_arena`
- `cataclysm:kill_ignis`

Rambling purpose:

- establish the Arena as an ancient Netherworld champion / warrior tradition independent of the Cataclysm;
- explicitly avoid reframing Ignis as a secret Overlord.

Preferred presenter:

- Gnarl.

### 5.3 Ancient Factory and Harbinger

Required source context:

- `cataclysm:find_ancient_factory`
- `cataclysm:kill_harbinger`

Rambling purpose:

- identify the Factory and Harbinger as Glorious Empire military-industrial material;
- state that the site was not created by the Cataclysm.

Preferred presenter:

- Gnarl.

### 5.4 Cursed Pyramid and Ancient Remnant

Required source context:

- `cataclysm:find_cursed_pyramid`
- `cataclysm:kill_remnant`

Rambling purpose:

- identify the Ancient Remnant as a former Ruborian royal beast;
- establish later Cataclysmic resurrection without implying the original creature was Cataclysm-created.

Preferred presenter:

- Gnarl.

### 5.5 Sunken City and Leviathan

Required source context:

- `cataclysm:find_sunken_city`
- `cataclysm:kill_leviathan`

Rambling purpose:

- frame the site and Leviathan through centuries of oceanic Cataclysm leakage;
- connect the pattern to Fathoms and Nerakyss as parallel evidence, not as a single subordinate chain.

Preferred presenter:

- Gnarl after basic historical context is known;
- a Fathoms Historian callback may supplement later if the Fathoms investigation has already completed, but it must not create a second popup for every event by default.

### 5.6 Acropolis and Scylla

Required source context:

- `cataclysm:find_acropolis`
- `cataclysm:kill_scylla`

Rambling purpose:

- establish that Scylla and the Acropolis predate the Cataclysm independently;
- use this contrast to prevent a universal Cataclysm explanation.

Preferred presenter:

- Gnarl.

### 5.7 Frosted Prison and Maledictus

Required source context:

- `cataclysm:find_frosted_prison`
- `cataclysm:kill_maledictus`

Rambling purpose:

- establish Maledictus as the former general appointed to Nordberg by the Fourth Overlord;
- establish that the Fourth Overlord's fall caused or led to his imprisonment;
- preserve as UNKNOWN who imprisoned him, why they did so and the exact mechanism of his transformation.

Preferred presenter:

- Gnarl.

These UNKNOWNs must remain explicit in final dialogue. Dramatic writing is not permission to resolve them.

### 5.8 Ruined Citadel and Ender Guardian

Required source context:

- `cataclysm:find_ruined_citadel`
- `cataclysm:kill_ender_guardian`

Rambling purpose:

- connect the Ruined Citadel and Ender Guardian directly to the transformed Cataclysm Dimension / Wasteland;
- avoid implying they caused unrelated Cataclysm-mod locations.

Preferred presenter:

- Gnarl.

### 5.9 Additional Cataclysm achievements

Achievements such as supporting miniboss, elite or intermediate progression signals may receive Ramblings only when current REIGN authority gives them specific context worth presenting.

Examples from the installed advancement surface include supporting kill milestones such as Clawdian, Revenant or Ender Golem.

They are not automatically required merely because they contribute to native progression.

If no established REIGN interpretation exists, leave the achievement native.

## 6. Magic Ramblings

### Iron's Spells

After `MAG-IRON-050` records the first Holy / Good or Blood / Evil inclination:

- later meaningful completion of the opposite native path may trigger one optional Gnarl Rambling;
- it acknowledges broader mastery without overwriting `GOOD_FIRST` or `EVIL_FIRST`;
- it must not reopen the completed quest.

### Eidolon

After the Sacred / Good versus Wicked / Evil OR closer resolves:

- later meaningful completion of the opposite native path may trigger one optional Gnarl Rambling;
- the first recorded ritual inclination remains unchanged.

### Other magic systems

Unused meaningful native advancements may receive sparse commentary if they represent a real jump in capability not already covered by Questlog.

Do not create one popup for each workstation, spell, serum, ritual, ingredient, material tier or recipe.

## 7. Fathoms Ramblings

The authored Fathoms investigation ends before exhaustive native progression.

Strong post-investigation Rambling candidates include:

- `catch_all_fish`, as exhaustive maritime knowledge rather than a quest requirement;
- `make_a_bad_decision`, because it is a distinctive source event and should receive contextual reaction without being required by V5;
- first Ancient Reservoir entry;
- first Kelpie creation;
- a major Augur / Pylon discovery;
- a late ritual or equipment culmination that materially expands the investigation.

Preferred presenter:

- Fathoms Historian for source-specific maritime interpretation;
- Gnarl where the event directly intersects established Cataclysm history.

These are eligible, not all mandatory.

## 8. NightWalker Ramblings

After the Lestat-led transition arc closes on the first Vampire Altar power plus weakness purchase, later source-owned events may receive sparse Lestat reactions.

Good candidates:

- a major additional power tier;
- a particularly consequential weakness;
- significant cure / removal of vampirism;
- an exceptional Thrall milestone;
- a later state change that materially alters the Overlord's vampiric condition.

Routine feeding, blood changes and every Altar purchase are excluded.

## 9. Dragon and Ice & Fire Ramblings

After the dedicated dragon mastery and Dragon Den arcs, sparse Gnarl or other appropriate reactions may acknowledge:

- an unusually ancient Stage 5 dragon beyond the required progression;
- a meaningful later Dragon Forge achievement;
- a significant Ice & Fire creature not assigned to Civilization, Bosses'Rise or dragon mastery;
- selected compatible spell or cuisine milestones when they matter to an established arc.

Myrmex political milestones belong to the Myrmex civilization root rather than generic Ice & Fire Ramblings.

Ashlord belongs to Bosses'Rise.

## 10. End / Wasteland Ramblings

The Post-Credits End Adventure deliberately samples outer ecology and one major ruin rather than exhausting the dimension.

Later Ramblings may acknowledge:

- a genuinely exceptional End ruin;
- an important relic or prize;
- a notable elite encounter;
- a discovery that clarifies a known aspect of the Wasteland without inventing builders or history that remain UNKNOWN.

Do not react to every Enderman Overhaul variant, every End City or every repeated ship.

## 11. Civilization Ramblings

Civilization Ramblings are subordinate to each civilization Questlog and permanent terminal state.

Appropriate uses include:

- a later historical callback after a civilization fact has been discovered;
- one reaction to the opposite or unexpected use of a service after terminal resolution;
- one meaningful consequence of the optional Dwarf-Kobold rivalry;
- later acknowledgement of Gnumu ancestry truth only if the reveal / withhold state allows that character to know it.

Ramblings must obey knowledge boundaries.

A local NPC cannot speak as though they know a withheld Gnumu ancestry fact, an undiscovered historical truth or the resolution of another polity unless V5 explicitly establishes that information flow.

## 12. Native-first and popup-only content

The following content groups are eligible for sparse Ramblings but do not receive dedicated V5 questlines solely for coverage:

- Darker Depths;
- Born in Chaos;
- Realm RPG: Imps & Demons;
- Mythic Mounts;
- Companions!;
- Tameable Beasts;
- Domestication Innovation;
- Artifacts / Relics;
- selected major equipment acquisitions;
- Rotten Creatures, with Mortis preferred for meaningful undead observations;
- Enchanting System Overhaul, at most a useful introductory acknowledgement;
- Pale Garden / Creaking discoveries;
- Nether Depths Upgrade milestones;
- LevelUP / RPG Skill Trees, introductory acknowledgement only;
- first genuinely major Small Ships vessel if worthwhile;
- extremely sparse Creeper Overhaul discoveries;
- Spice of Life food milestones.

Eligibility is not a quota. V5 does not require at least one popup from every listed mod.

## 13. Church of Sin and Born in Chaos

### Church of Sin

V5 removed its older dedicated questline.

Allowed treatment:

- one notable discovery Rambling for the Cursed Cathedral when it first matters;
- optionally one culmination reaction if the player meaningfully clears or completes the native location.

Do not recreate the removed questline through a chain of Ramblings.

### Born in Chaos

Allowed treatment:

- selected boss, elite or major discovery reactions;
- no generic ecology tutorial;
- no popup for every hostile creature.

## 14. Ribbit music and other characterization events

Ribbit music is characterization, native play and optional Rambling material.

It is not a civilization objective.

A first meaningful musical event may receive a local-character reaction if final dialogue authoring benefits from it, but no detector should be built solely to force a mandatory music popup.

The same principle applies to ambient cultural behavior elsewhere: Ramblings support character and world texture but do not justify unnecessary tracking architecture.

## 15. Spice of Life treatment

The older assignment ledger proposed popup milestones toward 100 distinct foods tasted and a Minion cook as the recurring voice.

V5 retains the system allocation but does not yet promote the proposed Minion cook into the mandatory presenter roster solely from that older plan.

Recommended treatment:

- use a small number of meaningful native food-count milestones rather than every increment;
- assign the final voice during presenter-roster closure using an already-established suitable character if possible;
- create a dedicated Minion cook presenter only if final campaign dialogue establishes that character as necessary.

This is a presenter-roster decision, not a new questline.

## 16. Rambling data contract for later implementation

Each authored Rambling should eventually specify:

```text
rambling_id
source trigger / durable condition
one-time policy
speaker
presenter visual state
knowledge prerequisites
short contextual purpose
whether retrospective presentation is allowed
```

Final dialogue text is authored later from this contract.

A Rambling must not require a new persistence system when an existing native advancement or owner-state fact can prove one-time eligibility safely.

## 17. Coverage rule

Ramblings are complete when:

- required Cataclysm historical triggers are mapped;
- focused blueprints identify their required contextual reactions;
- popup-only mod groups have eligibility rules;
- excluded repetitive events are explicit;
- each required Rambling can be assigned to an established speaking character during presenter-roster closure.

V5 does not require every eligible Rambling to be individually written before the campaign system authority can be considered structurally complete. Required contextual beats and speaker responsibilities must be fixed; optional flavor copy can be authored during final content writing.

The V5 authority still does not authorize production implementation.
