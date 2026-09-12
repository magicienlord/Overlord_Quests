# Goblins Tyranny Engineer Integration

Status: TECHNICAL / SOURCE-BACKED PRODUCTION INTEGRATION

## Exact installed authority

This integration uses the supplied OVERLORD REIGN Goblins Tyranny 1.2.3 JAR:

```text
SHA-256 aa9d337c58a0bfeb0378ab205fb2c70c82a84c85a6e21e5c5c56ee025ec48174
```

The exact binary registers:

```text
goblins_tyranny:engineer_goblin
goblins_tyranny:engineeress_goblin
```

It also defines native advancement:

```text
goblins_tyranny:engineer_success
```

The installed English advancement description is:

```text
Use the engineer workbench
```

Bytecode inspection confirms `OpenBombGuiProcedure` and `OpenDroneGuiProcedure` award `goblins_tyranny:engineer_success` through Minecraft's normal player advancement state when the corresponding engineering GUI path is opened.

## Production sidequest use

`campaign/civilizations/goblins/engineer_workbench` is offered only by one explicitly authored principal-camp engineer carrying:

```text
overlord_anchor:goblin_main_engineer
```

Either native engineer sex variant may be selected during world integration. The provider rule therefore accepts the two exact installed entity IDs, while the anchor tag decides which actual NPC belongs to the principal camp's authored quest pool.

The objective uses `questlog:advancement` against the native `goblins_tyranny:engineer_success` state. It does not create an alternate workbench, engineering GUI, recipe system, or progression flag.

Because Questlog's advancement objective reads durable `ServerPlayer` advancement progress, an engineer workbench used before sidequest acceptance is recognized retrospectively once the quest becomes active.

## State boundary

Completion records only ordinary Quest completion and provider-specific follow-up. It does not:

- set Goblin disposition;
- create an engineering reputation value;
- duplicate `engineer_success` into a narrative fact;
- imply that every Goblin engineer belongs to the principal camp;
- assign final world coordinates.
