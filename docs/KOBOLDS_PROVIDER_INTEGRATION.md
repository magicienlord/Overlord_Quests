# OVERLORD QUESTS Kobolds Provider Integration

Status: TECHNICAL SOURCE AUDIT / PRODUCTION PROVIDER SUPPORT

Date: 2026-09-12

## Authority

The current OVERLORD REIGN lore authority establishes one designated Kobold Den as the principal local civilization anchor. Its civilization main quest begins through one protected, explicitly marked Kobold Captain. Ordinary Captains, unrelated Dens, and Pirate Kobolds must not automatically become the principal civilization quest starter.

This document records the exact installed Kobolds 2.12.0 behavior relevant to using that Captain as an in-world provider. It does not define later Kobold political branches or disposition outcomes.

## Installed artifact authority

The supplied target instance contains:

```text
Kobolds-2.12.0.jar
```

SHA-256:

```text
f5f5dd31ab42e3bda1a1148b91ae62c07daafb9e52905d263c0bbcfbdf9cbabe
```

Its packaged `META-INF/mods.toml` declares:

```text
modId="kobolds"
version="2.12.0"
minecraft="[1.20.1]"
loaderVersion="[47,)"
```

The installed JAR is therefore the compatibility authority for this integration. The public `Jusey1/Kobolds` repository was populated later and is not used as a substitute for the 2.12.0 binary when behavior differs or cannot be historically proven.

## Captain registry identity

Bytecode inspection of the installed `KoboldsMobs` registration confirms the Captain path:

```text
kobolds:kobold_captain
```

The production civilization provider can therefore target that exact entity type without broadening eligibility to ordinary Kobolds or Pirate Kobolds.

## Native hostility behavior

The installed `KoboldCaptain` registers a `NearestAttackableTargetGoal` using `KoboldAttackSelector`.

For non-Warrior Kobolds, including Captains, that selector recognizes:

- Zombies;
- Silverfish;
- Raiders.

Players are not part of the Captain's normal target-selector set.

This means the source mod supports ordinary non-hostile proximity between a player and a Captain. OVERLORD QUESTS does not need to suppress a native player-hostility rule merely to make first contact possible.

This finding does not make every Kobold permanently peaceful under every circumstance. Revenge behavior and other source-native combat consequences remain intact.

## Native Captain interaction

The installed Captain overrides the normal Kobold interaction path.

When its off hand is empty, several authored item tags can be given to the Captain and its native `KoboldCaptainTradeGoal` later emits loot from Captain-specific loot tables. The base Kobold interaction surface also contains ordinary source interactions such as empty-hand feedback and item-specific behavior.

OVERLORD QUESTS therefore retains its non-invasive provider gesture:

```text
sneak + main-hand entity interaction
```

The provider event is only consumed when the target currently exposes relevant Questlog provider content. Ordinary non-sneaking Kobold interactions and source-native Captain trading remain the responsibility of Kobolds 2.12.0.

For deterministic provider validation, an empty main hand is preferred so the test does not also trigger a source-native item transfer rule.

## Principal Den identity

The lore decision identifies one local Kobold Den and one Captain, not a universal Kobold ruler.

Production content should therefore require an authored anchor tag in addition to the exact Captain entity type. The stable pre-placement tag is:

```text
overlord_anchor:kobold_main_captain
```

Quest-critical protection remains a separate tag:

```text
overlord_quest_protected
```

The separation is mandatory:

- `kobolds:kobold_captain` proves source-native entity class;
- `overlord_anchor:kobold_main_captain` proves membership in the selected principal Den and local political role;
- `overlord_quest_protected` prevents accidental loss before an authored route allows death.

None of these conditions makes other Kobold Dens or Pirate Kobolds subjects of the selected Captain.

## Production boundary

The installed 2.12.0 audit establishes enough technical support for a first-contact provider:

- exact installed version and JAR identity are known;
- exact Captain registry ID is known;
- the Captain does not normally target players;
- source-native Captain interactions are compatible with Questlog's separate sneak-interaction gesture;
- one explicit scoreboard tag can narrow the provider to the designated principal Den before final coordinates exist.

It does NOT establish:

- exact final Den coordinates;
- later Kobold disposition outcomes;
- destruction, subjugation, services, tribute, or branch rewards;
- global political authority over all Kobolds;
- any political relationship between the main Den and Pirate Kobolds beyond the lore rule that the pirate subculture remains independent unless later authored otherwise.

Those remain governed by the newer civilization decisions and later campaign authoring.
