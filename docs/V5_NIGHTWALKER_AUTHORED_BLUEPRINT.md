# OVERLORD REIGN V5 NightWalker Authored Blueprint

Status: V5 QUEST BLUEPRINT

Purpose: define the conditional Lestat-led vampire-transition arc against the current Nycto alpha.4 source without duplicating Nycto's broader vampire progression.

No final quest names or final dialogue are assigned here. Beat IDs are authoring references.

## 1. Campaign role

NightWalker is an optional personal-transition arc.

Established V5 meaning:

- vampires are independent of the Great Cataclysm;
- Nycto owns operative vampire mechanics and persistent player state;
- Lestat is an OVERLORD REIGN presenter, not a Nycto runtime NPC requirement;
- Lestat may take over principal Questlog presentation for this arc without replacing Gnarl elsewhere;
- the arc teaches the immediate lived consequences of becoming a vampire and the first deliberate use of the Vampire Altar;
- deeper powers, weaknesses, cure, hunger management, combat techniques, coffin use, thralls and other native systems remain Nycto-owned play unless separately adopted by V5.

The current technical authority is Nycto alpha.4, not the older alpha.3 production wrapper.

## 2. Unlock rule

The arc does not begin from possession of vampire blood, a temporary transformation effect, or proximity to vampire content.

It becomes available only after the source-owned player state confirms a completed vampire transformation.

Early legitimate completion must be recognized from the authoritative Nycto player state where possible.

## 3. Authored sequence

### NW-010: Lestat acknowledges the transformation

Trigger:

- source-owned Nycto state confirms the Overlord is a vampire.

Presentation:

- Lestat is principal presenter;
- this is contextual Questlog presentation, not a requirement to spawn a physical Lestat entity.

Visible accomplishment:

- acknowledge / complete the introductory Lestat presentation after the real transformation has occurred.

Purpose:

- establish Lestat's advisory presence only after the condition he is responding to actually exists;
- frame vampirism as a new personal condition rather than a Cataclysm consequence.

Persistent consequence:

- record that Lestat has joined the Tower's advisory sphere only if later campaign content needs that historical fact;
- do not create a second vampire-state flag because Nycto owns that truth.

### NW-020: Feed deliberately

Prerequisite:

- `NW-010` complete.

Visible accomplishment:

- deliberately consume / use one valid Nycto blood source through the native feeding system.

Purpose:

- make the first practical lesson about hunger and blood an action rather than exposition;
- establish that the transformed state has ongoing physical requirements.

Boundary:

- this is not a blood-management tutorial;
- no recurring hunger quest, blood quota, or permanent reminder UI is created.

Technical translation:

- use source-owned blood/use state or a narrow supported item-use signal rather than generic possession.

### NW-030: Approach the Vampire Altar

Prerequisite:

- `NW-020` complete.

Visible accomplishment:

- deliberately use the native Vampire Altar while in completed vampire state.

Purpose:

- introduce the system through which deliberate vampiric powers are acquired;
- prepare the player for the source-owned exchange between capability and weakness.

Technical fact:

- alpha.4 rejects non-vampires at the Altar and opens the native Altar progression only for a real vampire.

### NW-040: Accept the first price

Prerequisite:

- `NW-030` complete.

Visible accomplishment:

- complete the first successful native Vampire Altar purchase;
- acquire one chosen vampiric power;
- accept the corresponding first chosen weakness required by the alpha.4 Altar progression.

Purpose:

- close Lestat's introductory guidance on a meaningful player decision rather than on a generic power-count threshold;
- establish the defining NightWalker principle that increased capability carries an authored source-owned cost.

Technical fact:

- in alpha.4, the Altar's first purchase occurs at zero owned powers and therefore requires a weakness together with the selected power;
- the purchase writes both through Nycto's authoritative persistent player state.

Completion state:

- the NightWalker introductory transition arc is complete;
- Questlog may remember that Lestat guided the first deliberate Altar choice if later presentation needs that historical fact;
- Questlog does not duplicate which power or weakness the player owns because Nycto already owns those states.

## 4. Native remainder

The following remain ordinary NightWalker progression after the authored introduction unless later V5 authority adopts a specific event:

- additional Altar purchases;
- the remaining power catalog;
- the remaining weakness catalog;
- blood optimization;
- advanced combat powers;
- Dark Form and other transformations;
- coffin mechanics;
- hunter interactions;
- cure / removal of vampirism;
- Vampiric Thrall mechanics;
- equipment and decorative content.

Meaningful later native events may receive sparse Lestat Ramblings where they genuinely merit character reaction. They must not become a duplicate Nycto progression checklist.

## 5. Presenter requirement

Lestat is a required V5 presenter because this blueprint assigns him direct Questlog speech.

He uses the universal V5 presenter visual states:

```text
neutral
pleased
assertive
concerned
hostile
```

His five visual assets remain `NOT_GENERATED` until the final V5 presenter roster is closed and the art pass begins.

## 6. Technical boundary

Implementation should read the current alpha.4 owner state directly or through the narrowest compatibility bridge.

Known authoritative source surfaces include:

- completed vampire state;
- blood state;
- owned powers;
- owned weaknesses;
- Vampire Altar purchases;
- cure state.

The V5 blueprint does not authorize production changes yet.
