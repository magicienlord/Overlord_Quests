# Civilization Sidequest Integration Status

Status: TECHNICAL / IMPLEMENTATION STATUS

This file records which civilization main-entry and sidequest pools currently have source-backed production activities and which remain intentionally unfilled. It is not a source of new setting canon.

Generalized civilization main-entry coverage is currently 9/10. Villagers are the only civilization without a production main-entry quest because the principal settlement/provider remains unresolved in lore authority.

Illagers have their hostile Bastille opening but not the later fearful/cowed provider phase. That later provider identity remains unresolved.

Demons are explicitly outside the generalized civilization framework.

See `CIVILIZATION_NATIVE_PROGRESSION_AUDIT.md` for the exact Dwarven Forge, Ribbits, and Kobolds artifact identities and durable-signal analysis.

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
  - native signal: `goblins_tyranny:merchant_success`.
- `campaign/civilizations/goblins/engineer_workbench`
  - provider: designated principal-camp `goblins_tyranny:engineer_goblin` or `goblins_tyranny:engineeress_goblin`;
  - native signal: `goblins_tyranny:engineer_success`.
- `campaign/civilizations/goblins/tavern_business`
  - provider: designated principal-camp `goblins_tyranny:bartender_goblin`;
  - native signal: `goblins_tyranny:liquor_success`.

Additional Goblin roles remain eligible for later authored content, but no further production sidequest should be added merely to fill a role slot without a defensible objective or consequence.

## Gnumus

First contact: IMPLEMENTED.

Current source-backed post-contact pool:

- `campaign/civilizations/gnumus/merchant_business`
  - provider: designated principal-settlement `gnumus:gnumus_merchant`;
  - native signal: `gnumus:business_approach`;
  - activity owner: native Gnumu Merchant interaction using Gnumus Doubloons.

Other Gnumus milestones may support later sidequests, but assigning them to settlement providers or political consequences requires authored justification rather than automatic promotion from advancement names.

The hidden Halfling ancestry remains outside ordinary Gnumu cultural sidequests unless a later approved reveal explicitly uses it.

## Sea Dwellers

First contact: IMPLEMENTED.

Current source-backed post-contact pool:

- `campaign/civilizations/sea_dwellers/aquamarine_barter`
  - provider: designated principal-village Mermorph trader;
  - native signal: `seadwellers:adv_barter_aquamarine`;
  - activity owner: Sea Dwellers native Mermorph barter system.

Rejected signals:

- `seadwellers:adv_barter_nautilus` is not a production milestone because the exact installed binary audit found no award reference;
- `seadwellers:adv_barter_fish` belongs to the wild Mermorph barter path and is not used as the principal Sea Village commerce milestone.

No Ocean Dragon content belongs to OVERLORD REIGN.

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

Post-contact pool: AUTHORING UNRESOLVED / TECHNICAL SIGNAL AVAILABLE.

The exact Dwarven Forge audit found recipe-unlock advancements, but recipe entries are not proof of crafting, trade, or civilization service.

A source check established that Dwarven rune smithing can be tracked through persistent exact-item `Stats.ITEM_CRAFTED`, and Questlog already has `questlog:item_craft_stat`. No production Dwarven sidequest is added solely because that signal exists. The exact craft, provider, political meaning, and reward still require authored justification.

## Ribbits

First contact: IMPLEMENTED.

Post-contact pool: TECHNICALLY UNRESOLVED.

The installed Ribbits advancements are recipe unlocks, not records of Ribbit trade, gardening, fishing, music, or Sorcerer activity.

Ribbits uses the vanilla `Merchant` menu, so successful trades increment `minecraft:traded_with_villager`. That statistic cannot distinguish a Ribbit trade from another merchant trade and is too broad for a Ribbit-specific production accomplishment.

If a later authored Ribbit sidequest requires proof of native trade completion, use a narrow filtered bridge rather than inventory possession or the global merchant statistic.

## Kobolds

First contact: IMPLEMENTED.

Post-contact pool: TECHNICALLY UNRESOLVED.

The installed Kobolds artifact exposes no advancement definitions. Captain and specialist trade behavior is implemented through native Kobold AI goals, and the audited path does not award a durable player statistic or advancement when it emits native trade output.

If an authored Kobold sidequest later requires proof of a Captain or specialist transaction, use the smallest source-specific completion bridge that observes the native action without replacing Kobold AI or reward logic.

## Villagers

Civilization main entry: AUTHORITY BLOCKED / UNRESOLVED.

The provider architecture already supports vanilla and modded Villager professions, but the principal settlement/provider for the human main arc is not selected in lore authority. Do not invent one merely to reach 10/10 coverage.

Profession sidequests can be authored later when tied to an actual selected settlement context and campaign state.

## Illagers

Civilization hostile opening: IMPLEMENTED.

Current source-backed opening:

- `campaign/civilizations/illagers/break_the_bastille`
  - target: one exact `takesapillage:legioner` selected as the designated Bastille's local command figure;
  - authored local identity: `overlord_anchor:illager_bastille_commander`;
  - objective: persistent player-attributed kill history for that exact tagged Legioner;
  - result: `overlord_reign:civilizations/illagers/authority_established`;
  - political disposition: intentionally unresolved.

The marked Legioner is an authored local REIGN command figure layered onto a source-backed elite Bastille soldier, not a universal Illager leader class.

Post-authority fearful/cowed provider phase: AUTHORITY BLOCKED / UNRESOLVED.

Lore permits peaceful interaction after the designated Bastille is overpowered, but no surviving Bastille NPC has been selected as the first fearful provider. Do not arbitrarily promote Legioner, Archer, Skirmisher, or another Illager subtype into that role.

## Piglins

First contact: IMPLEMENTED.

Production entry:

- `campaign/civilizations/piglins/first_contact`
  - provider: exact protected `minecraft:piglin_brute` tagged `overlord_anchor:piglin_main_chieftain`;
  - locality: the designated authored Nether Village only;
  - access bridge: at least one worn gold armor piece grants temporary restraint for the initial audience;
  - completion fact: `overlord_reign:civilizations/piglins/contact_established`;
  - political disposition: intentionally unresolved.

The gold bridge suppresses player targeting only for the designated protected Chieftain. Ordinary Piglins and Piglin Brutes remain native elsewhere. Gold establishes an audience opportunity, not obedience, neutrality, alliance, or subjugation.

A later explicitly authored `overlord_reign:neutral` or `overlord_reign:subjugated` state may sustain peaceful access to that same designated Chieftain without gold, but the first-contact quest itself does not write either state.

Post-contact sidequest pool: NOT YET AUTHORED.

No generic Piglin sidequests should be created merely because first contact now exists. Later content still requires a source-backed activity and an authored political/service fit.

## Validation rule

Every production civilization sidequest added to the bundled manifest should receive:

- exact installed-mod/source evidence where it depends on a native system;
- a runtime validation protocol;
- a narrow provider anchor identity;
- a static production contract wired into normal CI;
- explicit boundaries preventing accidental disposition, lore, geography, or cross-polity expansion.
