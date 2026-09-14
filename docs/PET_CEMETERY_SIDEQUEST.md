# Pet Cemetery Conditional Sidequest

Status: IMPLEMENTED / SOURCE-BACKED

Authority: `reference/36_REIGN_MOD_QUESTLINE_ASSIGNMENTS_FINAL.md`

The Pet Cemetery assignment is a small conditional resurrection arc. It must remain absent from the normal quest list until one of the player's supported pets actually dies.

## Implementation

The opening quest uses the OVERLORD REIGN extension objective `overlord_reign:owned_tame_death` as a prerequisite. The objective listens for an actual tameable-entity death, verifies that the dead tameable belongs to the current Questlog player, and then applies the authored entity matcher.

The production definition restricts that matcher to `#pet_cemetery:drops_pet_collar`. This is the native Pet Cemetery entity-type tag for pets that participate in its collar-resurrection system. A different player's pet death cannot open the quest, and merely obtaining or creating a Pet Collar cannot open it.

Once unlocked:

1. `A Collar Left Behind` requires the player to recover `pet_cemetery:pet_collar`.
2. `Return from the Grave` observes `pet_cemetery:nether/respawn_pet`.

The finale does not reproduce Pet Cemetery's resurrection mechanics. The installed mod fires its `pet_cemetery:respawn_pet` advancement trigger from the real charged-Respawn-Anchor resurrection path, so Questlog records that native success signal.

## Boundary

This arc teaches resurrection only for entities Pet Cemetery itself supports. It does not establish routine resurrection for ordinary sapients or change the project's broader rules for souls, ghosts, undeath, or ritual necromancy.

The objective type is implemented generically in Questlog and has no compile-time dependency on Pet Cemetery. The quest definition supplies the Pet Cemetery tag and advancement IDs.
