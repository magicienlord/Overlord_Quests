# OVERLORD REIGN V5 Questlog Presenter System Authority

Status: V5 CAMPAIGN AUTHORITY

Date: 2026-09-16

Authority relationship: this document is a focused companion to `docs/V5_CAMPAIGN_SYSTEM_AUTHORITY.md`. `docs/V5_DECISION_BATCH_01_AUTHORITY.md` supersedes earlier presenter proposals where they differ.

## 1. Presenter system scope

Questlog speaking presentation is a generic character system, not a Gnarl-only feature and not a NightWalker-specific feature.

The presenter layer uses deliberately registered character identities. Ordinary corporeal civilization providers do not automatically become PNG presenters merely because they own or provide a quest.

PNG presenters are non-corporeal presentation characters except for one deliberate corporeal exception: the Historian.

The Historian is the sole corporeal PNG-presenter exception in final V5.

Provider ownership and presenter voice remain separate concepts. A local physical provider may own campaign progression while an approved registered presenter supplies Questlog framing without creating duplicate quest state.

## 2. Locked presenter roster

The currently established V5 presenter roster is:

```text
Gnarl
Mortis
Quaver
Historian
Lestat
Gristle
Grubbison Jr
Giblet the Sixth
```

This roster is not to be expanded merely because another NPC exists, provides a quest, or has dialogue available in its source mod.

Any later proposal to add another PNG presenter would be a new authored campaign decision requiring explicit Overlord approval.

## 3. Universal five-state visual contract

Every registered presenter requires exactly five universal visual states:

```text
neutral
approving
amused
displeased
severe
```

These labels supersede both the older Gnarl-specific vocabulary and the interim generic `pleased / assertive / concerned / hostile` vocabulary.

They are visual attitudes, not dialogue-writing modes.

### 3.1 `neutral`

Composed, observational, explanatory, reserved, or emotionally unmarked presentation.

### 3.2 `approving`

Approval, satisfaction, pride, relief, or positive recognition where amusement is not the primary visible attitude.

### 3.3 `amused`

Humor, smug enjoyment, mockery, delight, or another visibly entertained reaction.

### 3.4 `displeased`

Disappointment, irritation, concern, disapproval, distaste, or serious negative attention short of the strongest reaction state.

### 3.5 `severe`

Threat, anger, grave warning, overt hostility, intense condemnation, or another deliberately forceful extreme reaction.

The same five labels apply to every registered presenter. Their character-specific art and writing determine how each presenter expresses the state.

## 4. Presenter ownership and precedence

### 4.1 Default presenter

Gnarl is the default presenter for:

- the central campaign;
- general lore;
- politics;
- unspecialized magic;
- miscellaneous Adventure framing;
- any qualifying Rambling that does not clearly belong to an approved specialist theme.

Gnarl also presents all four Brown / Red / Green / Blue tribe-restoration quests.

### 4.2 Gristle

Gristle owns all food-related campaign presentation and Ramblings.

This includes:

- Gluttony / Farmer's Spell;
- ordinary cuisine where V5 allocates presentation;
- Farming / magical automation because that arc's authored purpose is food production and estate agriculture.

### 4.3 Grubbison Jr

Grubbison Jr owns all mining-related campaign presentation and Ramblings.

### 4.4 Giblet the Sixth

Giblet the Sixth owns all forging-related campaign presentation and Ramblings.

### 4.5 Mortis

Mortis owns:

- death-related presentation;
- pet death and resurrection;
- explicit afterlife and death-system reactions.

Mortis does not automatically own Blood magic, Eidolon, every undead encounter, or unrelated occult material merely because death is present somewhere in the source content.

### 4.6 Quaver

Quaver owns:

- music;
- instruments;
- performance;
- Tower court-music reactions.

Quaver's Tower arc concerns acquiring a curated instrument ensemble for the Dark Tower. Performer recruitment is not required. The fiction assumes Minions become the performers under Quaver's direction.

### 4.7 Historian

The Historian owns:

- Fathoms presentation;
- evidence-led archaeological or historical investigation in which the Historian is actually involved.

Generic world history remains Gnarl's domain.

Fathoms native advancements or milestones outside the authored five-quest investigation may be allocated as Historian Ramblings when they satisfy the approved Rambling eligibility rule.

### 4.8 Lestat

Lestat owns all authored NightWalker / vampirism presentation and vampire-specific Ramblings unless a scene explicitly requires another already established character.

Nycto native advancements or milestones outside the authored NightWalker quest beats may be allocated as Lestat Ramblings when they satisfy the approved Rambling eligibility rule.

## 5. Rambling speaker precedence

When a native achievement, discovery, or milestone has already been approved for Rambling treatment:

1. use the specialist presenter if the event clearly belongs to an approved specialist theme;
2. otherwise use Gnarl;
3. do not create a new presenter merely because an advancement or milestone exists.

This precedence selects the speaker only after the milestone itself has been authorized for Rambling use. Technical existence of an advancement does not authorize a popup.

## 6. Presenter identity versus physical quest provider

The entity that owns, enables, or supplies a quest and the PNG presenter who frames it do not have to be the same character.

Civilization providers remain ordinary corporeal world actors unless an explicit V5 decision says otherwise. Their provider state may be bound to the local civilization anchor without placing them in the PNG presenter roster.

The Historian remains the only approved corporeal exception.

Do not create duplicate quest state merely because provider and presenter differ.

## 7. Asset contract

For every locked presenter, V5 requires five visual assets corresponding to:

```text
neutral
approving
amused
displeased
severe
```

Presenter assets may remain `NOT_GENERATED` during V5 authoring.

The production sequence is:

```text
complete V5 campaign authority
-> verify the locked presenter roster and speaking allocations
-> generate/check all five universal visual states for every presenter
-> register them in the Questlog presentation framework
-> translate the approved campaign into production content
```

## 8. Writing discipline

A character does not receive presenter assets merely because it exists in the world.

World dialogue, environmental storytelling, books, native interactions, and ordinary provider mechanics do not automatically require a PNG presenter.

Presenter theme ownership must be deterministic. Do not reassign a theme merely for speaker variety.

## 9. Production boundary

This document defines V5 campaign presentation authority only.

Generic multi-presenter implementation, art generation, asset registration, and production quest reconciliation belong to the later implementation pass after V5 campaign authority is complete.