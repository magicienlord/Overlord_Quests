# OVERLORD REIGN V5 Questlog Presenter System Authority

Status: WORKING V5 CAMPAIGN AUTHORITY

Date: 2026-09-16

Authority relationship: this document is a focused companion to `docs/V5_CAMPAIGN_SYSTEM_AUTHORITY.md`. It records the presenter-system clarification supplied by the Overlord during V5 authoring.

## 1. Presenter system scope

Questlog speaking presentation is a generic character system, not a Gnarl-only feature and not a NightWalker-specific feature.

Whenever a Questlog entry requires a character to speak directly, that character may be shown through the standard presenter visual layer.

Gnarl is currently the only presenter explicitly described by the existing framework. That does not give Gnarl unique technical ownership of the presentation system.

Lestat uses the same generic presenter concept for NightWalker material when he is the authored speaking character.

The same rule applies to Mortis, Quaver, civilization figures, or any later character whom V5 assigns direct Questlog speech.

## 2. Five-state visual contract

Each registered speaking presenter requires five visual states for use alongside Questlog entries.

The exact five state labels or identifiers have not been recovered from the current `Overlord_Quests` repository, supplied instance archives, or current NightWalker source during this V5 audit.

Therefore:

- the count of five visual states is established;
- the generic presenter requirement is established;
- exact state names remain `UNKNOWN` until recovered from an older source or explicitly defined;
- V5 must not invent replacement names merely to keep authoring moving.

## 3. Presenter identity versus quest provider

The character who causes or provides a quest and the character who speaks in the Questlog presentation do not have to be the same entity.

A local physical NPC may own the quest while Gnarl, Lestat, Mortis, Quaver, or another appropriate presenter supplies the Questlog framing.

Conversely, when the local provider itself speaks through the Questlog, that provider identity becomes part of the presenter roster and requires the corresponding visual set.

Do not create duplicate quest state merely because provider and presenter differ.

## 4. V5 presenter roster requirement

V5 campaign authoring must record presenter identity wherever a quest entry contains direct character speech.

The final V5 campaign authority must produce one consolidated presenter roster before production asset generation begins.

The roster exists to answer exactly which character visual sets are required by the authored campaign. It must be derived from the finished campaign specification rather than guessed from the current production quest list.

For every presenter, V5 should eventually record:

```text
presenter_id
character_or_role
campaign_sections_used
physical_or_noncorporeal_presentation
five_state_visual_set_required
asset_status
```

`asset_status` may remain `NOT_GENERATED` during V5 authoring.

## 5. Asset timing

Presenter visuals do not need to be generated while V5 is still changing the campaign and speaker roster.

The correct order is:

```text
complete V5 campaign structure
-> finalize speaking-character roster
-> define or recover the five exact visual-state identifiers
-> generate the necessary presenter assets
-> register them in the Questlog presentation framework
-> implement authored quest content
```

This avoids generating assets for characters later removed from the campaign or discovering after implementation that required speakers were omitted.

## 6. Writing discipline

A character does not need a presenter asset merely because that character exists in the world.

A presenter asset is required when V5 actually assigns that character direct Questlog speech.

Prefer the smallest useful presenter roster consistent with the authored campaign. Local world dialogue, environmental storytelling, books, ordinary native interactions, and non-speaking provider mechanics do not automatically require a Questlog presenter.

## 7. Production boundary

The current production framework's Gnarl-only description is incomplete for V5 but is not to be expanded in production yet.

Generic multi-presenter implementation and asset registration belong to the later implementation reconciliation pass after V5 authority and the final presenter roster are approved.
