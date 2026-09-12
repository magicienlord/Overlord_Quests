# Civilization Sidequest Integration Status

Status: TECHNICAL / IMPLEMENTATION STATUS

This file records which civilization sidequest pools currently have source-backed production activities and which remain intentionally unfilled. It is not a source of new setting canon.

## Authoring threshold

A civilization contact quest does not automatically justify generic sidequests.

Production sidequest content should normally have at least one of the following:

- a durable native advancement or owner-state signal tied to a real installed-mod activity;
- a concrete authored item/world requirement justified by approved civilization design;
- an explicit later political consequence or service requirement already established by lore authority.

Do not create filler merely because a provider entity exists. Recipe-unlock advancements are not evidence that a player performed a civilization service, trade, or historical act.

Ordinary Quest completion is sufficient persistence for low-level sidequests unless later content materially needs a separate semantic narrative fact.

## Goblins

First contact: IMPLEMENTED.

Current source-backed post-contact pool:

- `campaign/civilizations/goblins/merchant_business`
  - provider: designated principal-camp `goblins_tyranny:merchant`;
  - native signal: `goblins_tyranny:merchant_success`;
  - activity owner: Goblins Tyranny native merchant purchase system.
- `campaign/civilizations/goblins/engineer_workbench`
  - provider: designated principal-camp `goblins_tyranny:engineer_goblin` or `goblins_tyranny:engineeress_goblin`;
  - native signal: `goblins_tyranny:engineer_success`;
  - activity owner: Goblins Tyranny engineer workbench/UI path.
- `campaign/civilizations/goblins/tavern_business`
  - provider: designated principal-camp `goblins_tyranny:bartender_goblin`;
  - native signal: `goblins_tyranny:liquor_success`;
  - activity owner: Goblins Tyranny bartender/liquor purchase procedures.

Additional Goblin roles remain eligible for later authored content, but no further production sidequest should be added merely to fill a role slot without an equally defensible objective or consequence.

## Gnumus

First contact: IMPLEMENTED.

Current source-backed post-contact pool:

- `campaign/civilizations/gnumus/merchant_business`
  - provider: designated principal-settlement `gnumus:gnumus_merchant`;
  - native signal: `gnumus:business_approach`;
  - activity owner: native Gnumu Merchant interaction using Gnumus Doubloons.

The exact Gnumus 1.0 JAR exposes other durable milestones for food, equipment, creatures, containers and combat. Those may support later sidequests, but assigning them to settlement providers or political consequences requires an authored fit rather than automatic promotion from advancement names.

The hidden Halfling ancestry remains outside ordinary Gnumu cultural sidequests unless a later approved reveal explicitly uses it.

## Sea Dwellers

First contact: IMPLEMENTED.

Current source-backed post-contact pool:

- `campaign/civilizations/sea_dwellers/aquamarine_barter`
  - provider: designated principal-village Mermorph trader;
  - native signal: `seadwellers:adv_barter_aquamarine`;
  - activity owner: Sea Dwellers native Mermorph barter system.

Rejected signals:

- `seadwellers:adv_barter_nautilus` exists with an impossible criterion but the exact 2.9.9 binary audit found no award reference. It is not a production milestone.
- `seadwellers:adv_barter_fish` belongs to the wild Mermorph barter path and is not used as the principal Sea Village commerce milestone.

No Ocean Dragon content belongs to OVERLORD REIGN. The earlier Ocean Dragon assumption was removed from lore authority and must not return through sidequest authoring.

## Umvuthana

First audience: IMPLEMENTED.

Current source-backed neutral-Grove pool:

- `campaign/civilizations/umvuthana/suns_blessing`
  - provider: the exact designated Grove `mowziesmobs:umvuthi`;
  - required state: formal audience plus current `overlord_reign:neutral` disposition;
  - native signal: `mowziesmobs:suns_blessing`;
  - activity owner: Mowzie's native Umvuthi desire/trade/blessing flow.

The native `mowziesmobs:kill_umvuthi` path remains a separate destructive route and must never satisfy or substitute for the blessing sidequest.

## Dwarves

First contact: IMPLEMENTED.

Post-contact pool: TECHNICALLY UNRESOLVED.

The exact Dwarven Forge 1.0.0 audit found recipe-unlock advancements, not durable records of Dwarven trade or service usage. Those recipe entries must not be misrepresented as civilization accomplishments.

Future Dwarven sidequests should wait for a concrete authored item/service requirement or a verified runtime state that accurately represents the intended Dwarven activity.

## Ribbits

First contact: IMPLEMENTED.

Post-contact pool: TECHNICALLY UNRESOLVED.

Ribbits 3.0.5 exposes native professions and trade machinery, but its packaged advancements are recipe unlocks only. No durable native sidequest accomplishment signal has yet been identified.

Do not convert mere profession interaction into hidden Questlog progress without an explicit authored objective or persistent source signal.

## Kobolds

First contact: IMPLEMENTED.

Post-contact pool: TECHNICALLY UNRESOLVED.

The exact installed Kobolds 2.12.0 JAR exposes no advancement definitions. Its Captain has native interaction/trade behavior, but no durable source-owned accomplishment signal has yet been established for production sidequest tracking.

## Villagers

Civilization main entry: UNRESOLVED.

The broader sidequest-provider architecture already supports vanilla and modded Villager professions. However, the principal civilization anchor/provider for the human main arc is not yet canonically selected. Profession sidequests can be authored later when tied to an actual local settlement context and campaign state.

## Illagers

Civilization arc opening: PLANNED / HOSTILE TRANSITION UNRESOLVED.

The exact Take a Pillage integration provides the designated Bastille structure/discovery signal but no unique source-native ruler class. The lore requires authority to be established through force before peaceful providers open.

Do not promote Legioner, Archer, Skirmisher, or another arbitrary subtype into the canonical Bastille ruler without an authored decision.

## Piglins

Civilization anchor: PLANNED.

The designated local Chieftain is an explicitly marked `minecraft:piglin_brute`, but the initial Brute-hostility to political-audience transition is not yet mechanically authored. Post-contact sidequests should not be created before that boundary is resolved.

## Validation rule

Every production civilization sidequest added to the bundled manifest should receive:

- exact installed-mod/source evidence where it depends on a native system;
- a runtime validation protocol;
- a narrow provider anchor identity;
- a static production contract wired into normal CI;
- explicit boundaries preventing accidental disposition, lore, geography, or cross-polity expansion.
