# Umvuthana Civilization First-Audience Test Protocol

Status: PRODUCTION CONTENT RUNTIME VALIDATION PROTOCOL

Scope: the peaceful first audience with the designated canonical Umvuthi only.

This protocol validates `campaign/civilizations/umvuthana/first_contact`, the mask-gated provider condition, native misbehaviour preservation, and the narrow post-audience target exception against the exact installed Mowzie's Mobs 1.8.2 behavior. It does not validate later subjugation, destruction, Grove services, tribute, or final-world placement.

## Preconditions

Use the target OVERLORD REIGN Forge 1.20.1 instance with the validated OVERLORD QUESTS artifact and `mowziesmobs-1.8.2.jar` installed.

Use an unpublished local single-player test world with commands available. Back up the world or use a disposable validation world.

Select the Umvuthi belonging to the Grove intended as the canonical civilization anchor and apply:

```text
/tag @e[type=mowziesmobs:umvuthi,sort=nearest,limit=1,distance=..12] add overlord_anchor:umvuthana_main_umvuthi
/tag @e[type=mowziesmobs:umvuthi,sort=nearest,limit=1,distance=..12] add overlord_quest_protected
```

Do not apply the anchor tag to unrelated Umvuthis.

## Clean narrative state

```text
/questlog narrative fact clear overlord_reign:civilizations/umvuthana/contact_established
/questlog narrative disposition clear overlord_reign:umvuthana
/questlog narrative fact set overlord_reign:reign/initial_foundation_established
```

Expected disposition before the audience:

```text
questlog:unresolved
```

## Check 1: ordinary unmasked approach remains hostile

Approach the designated Umvuthi without an Umvuthana mask and without creative/spectator immunity.

Expected result:

- native Umvuthi hostility remains active;
- the peaceful Questlog audience is not available;
- Questlog does not globally neutralize the entity.

This must also remain true for unrelated Umvuthis regardless of later canonical-Grove state.

## Check 2: exact native mask gate

Equip any one of the installed Umvuthana masks in the head slot, for example:

```text
mowziesmobs:umvuthana_mask_faith
```

Approach again.

Expected result:

- Mowzie's own ordinary player-target predicate does not select the masked non-offending player;
- sneak + main-hand interaction exposes `Audience of the Sun`;
- the same mask remains valid for native Umvuthi trade eligibility where the source interaction is otherwise available.

Move the mask out of the head slot and retry.

Expected result: the Questlog audience is unavailable. Merely carrying a mask is insufficient.

Repeat with at least one other installed mask variant to confirm the registry-ID set is not accidentally pinned to one mask.

## Check 3: unrelated Umvuthi rejection

While masked, interact with an Umvuthi that does not carry:

```text
overlord_anchor:umvuthana_main_umvuthi
```

Expected result: the production audience quest is not offered.

## Check 4: campaign foundation gate

With the mask equipped and the correct anchor selected, clear the foundation fact:

```text
/questlog narrative fact clear overlord_reign:reign/initial_foundation_established
```

Expected result: the audience is unavailable.

Restore it:

```text
/questlog narrative fact set overlord_reign:reign/initial_foundation_established
```

Expected result: the audience becomes available again.

## Check 5: peaceful provider flow

With a valid mask equipped, sneak + main-hand interact with the designated Umvuthi and select `Audience of the Sun`.

Expected result before acceptance:

- authored offer dialogue appears;
- Accept and Decline are available;
- Decline writes no contact fact or disposition.

Accept.

Expected result:

- because this is a conversation-only milestone, it becomes ready for turn-in immediately;
- no artificial journal-read or gameplay objective intervenes;
- removing the mask before reopening the provider prevents turn-in;
- re-equipping a valid mask restores turn-in availability from the exact issuing Umvuthi.

Turn it in while masked.

Verify:

```text
/questlog narrative fact get overlord_reign:civilizations/umvuthana/contact_established
/questlog narrative disposition get overlord_reign:umvuthana
```

Expected results:

```text
true
overlord_reign:neutral
```

## Check 6: mask no longer required for basic peaceful presence

After successful audience completion, remove the mask and remain near the designated Umvuthi.

Expected result:

- the designated Umvuthi does not acquire the Overlord as an ordinary player target merely because the mask was removed;
- completed provider follow-up remains available without re-equipping the mask;
- unrelated Umvuthis remain source-native and may still target the unmasked player.

This is the required local post-audience neutrality behavior.

## Check 7: source-native misbehaviour still overrides peace and provider dialogue

Use a disposable validation world for this check.

After the Grove is NEUTRAL and the mask is removed, deliberately perform an action that the exact Mowzie 1.8.2 implementation treats as Umvuthana misbehaviour, such as attacking an Umvuthana-related protected entity within the source mod's aggro conditions.

Expected result:

- Mowzie records the player as the relevant Umvuthi's misbehaving player;
- Questlog no longer suppresses that Umvuthi acquiring the offender as a combat target;
- sneak + main-hand interaction with that same canonical Umvuthi does not open the Questlog provider menu for the stored offender;
- completed peaceful follow-up dialogue is therefore inaccessible to that stored offender while Mowzie still identifies them as the offender;
- putting an Umvuthana mask back on does not restore the peaceful Questlog audience/provider path for that stored offender;
- quest-anchor damage protection, if still present, remains a separate mechanism and does not erase native hostility or native offender state.

This proves the post-audience bridge is not permanent immunity and that Questlog does not present peaceful conversation while Mowzie's authoritative relationship state is hostile.

## Check 8: save/reload persistence

Complete the peaceful audience without provoking the Grove, then save and quit. Reopen the same world without wearing a mask.

Expected result:

- the contact fact remains true;
- disposition remains `overlord_reign:neutral`;
- the designated Umvuthi remains non-hostile to the non-offending Overlord;
- the quest remains completed;
- completed provider follow-up remains available from the exact issuing Umvuthi;
- unrelated Umvuthis remain unaffected.

## Check 9: optional-dependency failure boundary

Static/CI validation must confirm the Questlog Java sources contain no direct `com.bobmowzie` import or class reference. Mowzie compatibility is selected by registry ID and the public misbehaviour method is invoked only through reflection.

The integration is invalid if Questlog becomes unable to load merely because Mowzie's Mobs is absent.

## Pass criteria

The first-audience slice is runtime-valid only when all of the following are directly observed:

- unmasked pre-audience hostility remains native;
- any supported native Umvuthana mask in the head slot permits the legitimate audience;
- carrying the mask outside the head slot does not satisfy the provider gate;
- unrelated Umvuthis cannot start the civilization quest;
- the campaign foundation gate works;
- same-provider turn-in works and still requires the mask before the first audience completes;
- completion records contact and changes only the canonical Grove's campaign disposition to NEUTRAL;
- the mask is no longer needed for basic peaceful presence after completion;
- unrelated Umvuthis remain unchanged;
- Mowzie-native misbehaviour overrides the local peace exception for the offending player;
- the same offender cannot reopen Questlog's peaceful provider menu or completed follow-up while Mowzie still marks them as the offender;
- state survives save/reload;
- Mowzie's Mobs remains an optional class-link dependency for Questlog.
