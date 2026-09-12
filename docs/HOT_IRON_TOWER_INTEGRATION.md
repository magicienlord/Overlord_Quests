# OVERLORD QUESTS Hot Iron Tower Integration

Status: TECHNICAL SOURCE AUDIT / PRODUCTION CAMPAIGN INTEGRATION

Date: 2026-09-12

## Authority

OVERLORD REIGN establishes that the newly manifested Dark Tower begins largely as an architectural shell with purpose-built rooms that are not yet operational. Installed mods should provide the native gameplay progression that makes those facilities useful rather than being bypassed by parallel Questlog mechanics.

The lore authority explicitly names Hot Iron as the example for the Tower's pre-shaped forge chamber.

This document records the verified Hot Iron 1.0.4 progression surface used by the first production Tower quest. It does not define additional Tower rooms or later forge upgrades that have not yet been established.

## Installed source

The current modpack contains:

```text
hot_iron-1.0.4-forge-1.20.1.jar
```

OVERLORD REIGN also contains the already-approved Hot Iron recipe repair used by the target instance. Questlog does not replace or duplicate that recipe work.

## Native smithery milestone

Hot Iron defines:

```text
hot_iron:local_smithery
```

Its advancement description is the native progression statement for acquiring the three core smithing tools:

- `hot_iron:smithing_anvil`
- `hot_iron:smithing_hammer`
- `hot_iron:smithing_tongs`

The advancement is driven by inventory criteria for all three items and is parented from `minecraft:story/smelt_iron`.

Because Minecraft advancement progress is persistent and `questlog:advancement` reads the player's existing advancement state, this milestone is sequence-break safe. Completing the Hot Iron milestone before the corresponding REIGN quest activates is still recognized when the quest later becomes active.

## Production mapping

The first Tower infrastructure quest uses exactly this native advancement.

Its completion records:

```text
overlord_reign:tower/forge_prepared
```

The marker means the Tower forge chamber has reached the first prepared-material milestone represented by possession of Hot Iron's core smithing equipment.

It deliberately does NOT assert that:

- every later Hot Iron mechanic has been completed;
- all final forge workstations are permanently placed at fixed coordinates;
- every forge upgrade is installed;
- the player necessarily performed a specific smithing recipe inside the Tower;
- any additional Tower room has been defined.

Those claims would exceed the currently verified source and REIGN architecture decisions.

## Why the marker is separate from the advancement

Later REIGN content may need to know that the Tower forge preparation milestone has been integrated into the campaign even if Hot Iron's own advancement tree changes in a future compatibility update.

The persistent narrative fact therefore records the REIGN historical meaning of the milestone, while the Hot Iron advancement remains the gameplay authority that earns it.

The dependency is intentionally one-way:

```text
Hot Iron native progression -> REIGN quest completion -> Tower narrative fact
```

Questlog does not grant the Hot Iron advancement, equipment, or recipes as a shortcut.

## Nearby Hot Iron advancement surface

Hot Iron 1.0.4 also contains:

```text
hot_iron:obtain_rock
```

and rewires vanilla story advancement parentage around its progression. Those facts are source evidence only. They are not automatically Tower milestones.

In particular, a vanilla tool acquisition advancement is not sufficient evidence by itself that a specific Tower forge operation occurred, because inventory acquisition may have other sources. No later Tower forge milestone should be authored from that signal unless the actual campaign requirement and Hot Iron mechanics justify it.

## Validation boundary

`tools/validate_campaign_opening_contracts.py` guards the production quest against drifting away from `hot_iron:local_smithery` and guards the automatic `overlord_reign:tower/forge_prepared` fact reward.

`docs/PRODUCTION_OPENING_TEST_PROTOCOL.md` requires a direct target-modpack pass confirming:

- the native advancement completes normally;
- the REIGN quest observes it, including prior completion;
- the Tower fact is written automatically;
- the fact persists across save/reload;
- the associated Gnarl reaction is delivered once.

No additional Hot Iron quest integration should be added merely to fill the quest log. Later hooks require a real campaign need and a source-faithful native signal.