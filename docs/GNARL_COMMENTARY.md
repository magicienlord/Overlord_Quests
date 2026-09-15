# Gnarl Lifecycle Commentary

Status: IMPLEMENTED

Authority: `magicienlord/Overlord_Lore_and_Canon` at `649bae2fe49f9da210bcf6d7400316f3e4413464`, especially `reference/GNARL_WRITING_RULES.md` and the final quest-assignment architecture.

## Purpose

Gnarl's Ramblings is a commentary layer around existing Questlog progression. It does not create a second quest engine, a hidden morality system or new narrative facts merely to remember that dialogue was shown.

## Runtime model

`GnarlCommentaryEngine` observes existing server-authoritative quest state and sends commentary through the dedicated `gnarl_commentary` packet. Presentation history is stored in world-scoped `overlord_quests_gnarl_commentary` SavedData by player UUID.

Existing saves are migration-safe: quests that were already active before the commentary runtime first sees a player are baselined rather than replaying historical introductions in bulk.

The server tick path is bounded to once-per-second commentary polling. Reminder pools are finite and stop after the authored variants are exhausted.

## Authored lifecycle surface

A major quest entry may define:

- `register`: concise situation framing;
- `delivery_direction`: what the Overlord is being directed toward;
- `objective_clarification`: practical explanation of the current objective;
- `branch_framing`: commentary specific to the branch quest that actually exists;
- `first_reminder` and a finite `repeat_reminders` pool;
- `objective_updates`: comments when objective completion count changes;
- `warning`: a bounded warning at the authored point;
- `success`: completion reaction;
- `failure`: failure/retreat reaction where the quest can fail;
- `post_quest`: delayed world-state commentary after completion.

Not every minor quest needs every surface. The production catalogue concentrates commentary on major campaign transitions and assignments where it materially improves guidance or character presence.

## Deliberate coverage

The guarded catalogue includes:

- major opening/Tower/campaign transitions;
- all six core magic families: Iron's Spells 'n Spellbooks, Farmer's Spell / Gluttony, Theurgy, Ars Elixirum, Biomancy and Eidolon: Repraised;
- all nine dedicated adventure families: Twilight Forest, The Bumblezone, L_Ender's Cataclysm, The Graveyard, Knight Quest, The Lost Castle, Rats / Ratlantis, Church of Sin and Oddities / Orchid Shrine;
- the central End campaign;
- the Biomancy Tower activation introduced by the current authority reconciliation.

`tools/validate_gnarl_commentary_contract.py` guards both lifecycle breadth and the runtime wiring.

## Separation from system reactions

`SYSTEM_REACTIONS` remains a different feature. It acknowledges sparse installed-system events for assignments that intentionally do not become formal questlines. Gnarl lifecycle commentary follows active authored quests; system reactions acknowledge external mechanics. The two persistence channels remain separate.
