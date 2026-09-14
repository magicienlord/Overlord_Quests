# Piglin Civilization Test Protocol

Status: TECHNICAL / RUNTIME VALIDATION

This protocol validates the designated local Piglin Brute Chieftain first-contact slice. It does not define the final Piglin political branch or authorize global Piglin behavior changes.

## Scope

The production quest is `campaign/civilizations/piglins/first_contact`.

The exact provider contract is:

- entity type: `minecraft:piglin_brute`;
- local anchor tag: `overlord_anchor:piglin_main_chieftain`;
- protection tag: `overlord_quest_protected`;
- civilization: `overlord_reign:piglins`;
- campaign gate: `overlord_reign:reign/initial_foundation_established`;
- audience access: the testing player wears at least one gold armor piece, or a later authored `neutral` / `subjugated` disposition already exists;
- first-contact result: contact-history fact only, with no disposition assignment.

## Anchor preparation

Use a disposable test world with commands enabled.

1. Select or summon one `minecraft:piglin_brute` to represent the Chieftain of the designated Nether Village.
2. Add `overlord_anchor:piglin_main_chieftain` to that exact Brute.
3. Add `overlord_quest_protected` to that exact Brute.
4. Keep a second untagged Piglin Brute nearby as the native-behavior control.
5. Establish the normal campaign foundation fact through the quest flow or the repository's existing narrative-state test tooling.

Do not tag every Brute in the village. The anchor is one local political figure, not a species-wide role.

## Gold audience gate

1. Approach the protected Chieftain without wearing any gold armor.
2. Confirm native Brute hostility is not suppressed and the Questlog provider menu cannot be opened.
3. Break combat state, equip at least one gold armor piece, then approach the protected Chieftain again.
4. Confirm the designated Chieftain does not acquire the testing player as an ordinary hostile target.
5. Sneak and main-hand interact with the Chieftain.
6. Confirm the first-contact provider entry is available.

Gold is an audience gate only. It must not change the stored Piglin disposition.

## Provider completion

1. Accept `campaign/civilizations/piglins/first_contact` from the exact Chieftain.
2. Confirm the quest binds to that provider.
3. Interact with the same Chieftain again while the gold-audience condition remains valid.
4. Complete the provider turn-in.
5. Confirm `overlord_reign:civilizations/piglins/contact_established` is set.
6. Confirm no Piglin disposition was written by this quest.

## Negative boundary checks

Run each check independently.

- Untagged Piglin Brute: remains native-hostile even while the player wears gold. Questlog must not suppress it.
- Anchor-tagged but unprotected Chieftain: audience access must fail. The special bridge requires `overlord_quest_protected` as well as the anchor identity.
- Protected but untagged Piglin Brute: remains native-hostile. Protection alone must not create a political audience.
- Ordinary Piglins and Piglin Brutes elsewhere: retain vanilla behavior. No global pacification or provider assignment is permitted.
- Before the foundation fact: the production first-contact quest must not become available even if the gold audience bridge prevents the designated Chieftain from targeting the player.
- After first contact with no later political resolution: removing gold must restore the Chieftain's ordinary Brute hostility. Contact is not submission.

## Later political-state compatibility

If a later authored Piglin branch writes `overlord_reign:neutral` or `overlord_reign:subjugated`, repeat the designated-Chieftain audience test without gold. The bridge may sustain peaceful access for that exact protected Chieftain only. This compatibility path does not authorize or implement the later political branch itself.

## Pass criteria

The slice passes only if gold creates a temporary safe political audience with the exact protected Chieftain, the provider quest records contact without a disposition, and every non-anchor Piglin control retains native behavior.
