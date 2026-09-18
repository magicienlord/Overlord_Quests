# OVERLORD REIGN V5 — Derived Quest Inventory

Status: WORKING INVENTORY. The authority is `V5_CAMPAIGN_SYSTEM_AUTHORITY.md`; this file is the per-quest source it is generated from and is kept in step with it.

Visible Questlog quests: **147**. Presenter popups: **294**. Provider quests: **43**.

Visual states are the five universal states: `COMPOSED`, `PLEASED`, `RELISHING`, `DARKENED`, `STRICKEN` (authority §5.2). Registers are per presenter (§5.3). `Order (presenter voice)` is `TO BE AUTHORED` on every quest by design (Q242). `Reward` is `NONE` on every quest (Q241).


## Opening

### `OR-OPN-001` — opening/a_new_master

- **Role:** Prove the Minion system functions and establish the new reign
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** After the opening Gnarl presentation (closure register)
- **Prerequisite:** —
- **Objective (specification):** Craft the Master's Staff and summon the first Brown in the same quest
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Master's Staff crafted and first Brown summoned
- **Unlock popup:** `PLEASED` · `CEREMONIAL` — the Netherworld has chosen; Gnarl has waited centuries for this (Q-001, Q-006)
- **Completion popup:** `RELISHING` · `MOCKING` — the staff works and a Brown crawls out — relief expressed as mockery, not solemnity
- **Consequence:** Minion system proven functional; unlocks all 14 Tower Restoration quests (Q153)
- **Persistent fact:** overlord_reign:minions/brown_recovered
- **Sequence-break handling:** Recognize a pre-existing Brown if summoned before activation
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** §8.1, Q153


## Tower Restoration

### `OR-TWR-001` — tower/throne

- **Role:** Make the Tower throne operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Throne: Necrolord Chair installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Necrolord Chair installed
- **Unlock popup:** `PLEASED` · `CEREMONIAL` — the seat is bare; the endless pit is already there (Q-011)
- **Completion popup:** `PLEASED` · `CEREMONIAL` — a seated Overlord is the point of the institution
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q008

### `OR-TWR-002` — tower/forge

- **Role:** Make the Tower forge operational
- **Presenter:** Giblet the Sixth
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Forge: Hot Iron Smithing Anvil and Crucible installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Hot Iron Smithing Anvil and Crucible installed
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — the room was shaped for a forge and has the lava pit waiting (Q-011)
- **Completion popup:** `PLEASED` · `REWARD` — Giblet approves of a working anvil
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q002; presenter per forging theme beats source domain (Q212, §4.4)

### `OR-TWR-003` — tower/storage

- **Role:** Make the Tower storage operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Storage: Drawer Controller connected to at least one functional drawer bank
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Drawer Controller connected to at least one functional drawer bank
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — an inventory problem
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — shelves reported as shelves
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q003

### `OR-TWR-004` — tower/armory

- **Role:** Make the Tower armory operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Armory: One armor-display and one weapon-display from distinct supported families
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** One armor-display and one weapon-display from distinct supported families
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — arms belong on display
- **Completion popup:** `RELISHING` · `MOCKING` — Gnarl enjoys the vanity of it
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q004

### `OR-TWR-005` — tower/treasury

- **Role:** Make the Tower treasury operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Treasury: Gold Barrel installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Gold Barrel installed
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — somewhere to put the gold
- **Completion popup:** `RELISHING` · `MOCKING` — the barrel fills; smug
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q008

### `OR-TWR-006` — tower/waygates

- **Role:** Make the Tower waygates operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the WayGates: Tower Waystone placed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Tower Waystone placed
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — a waystone must be found in the world and carried home (Q005)
- **Completion popup:** `PLEASED` · `REWARD` — the Tower is connected
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q005

### `OR-TWR-007` — tower/arena

- **Role:** Make the Tower arena operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Arena: Supplementaries Cage installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Supplementaries Cage installed
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — somewhere to make things fight
- **Completion popup:** `RELISHING` · `MOCKING` — Gnarl looks forward to using it
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q007

### `OR-TWR-008` — tower/jail

- **Role:** Make the Tower jail operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Jail: Big Iron Grate installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Big Iron Grate installed
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — prisoner management, routine
- **Completion popup:** `RELISHING` · `MOCKING` — a working cell pleases him
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q007

### `OR-TWR-009` — tower/alchemy

- **Role:** Make the Tower alchemy operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Alchemy: Ars Elixirum Glass Cauldron installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Ars Elixirum Glass Cauldron installed
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — a workbench for volatile study
- **Completion popup:** `PLEASED` · `REWARD` — the discipline is housed
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006, Q232

### `OR-TWR-010` — tower/theurgy

- **Role:** Make the Tower theurgy operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Theurgy: Theurgy core workstation installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Theurgy core workstation installed
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — a workbench for transmutation
- **Completion popup:** `PLEASED` · `REWARD` — the discipline is housed
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006

### `OR-TWR-011` — tower/gluttony

- **Role:** Make the Tower gluttony operational
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Gluttony: Farmer's Spell core workstation installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Farmer's Spell core workstation installed
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gristle is getting his kitchen
- **Completion popup:** `PLEASED` · `REWARD` — Gristle has his domain
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006; presenter per food theme beats source domain (Q212, §4.2)

### `OR-TWR-012` — tower/spell_study

- **Role:** Make the Tower spell study operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Spell Study: Iron's Spells inscription workstation installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Iron's Spells inscription workstation installed
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — a place to inscribe
- **Completion popup:** `PLEASED` · `REWARD` — the discipline is housed
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006

### `OR-TWR-013` — tower/eidolon

- **Role:** Make the Tower eidolon operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Eidolon: Eidolon core workstation installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Eidolon core workstation installed
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — occult plant, handled as plant (Q096: Eidolon is not Mortis material)
- **Completion popup:** `PLEASED` · `REWARD` — housed and controlled
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006

### `OR-TWR-014` — tower/biomancy

- **Role:** Make the Tower biomancy operational
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available with all 14 immediately after the opening Brown quest (Q153)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Restore the Biomancy: Biomancy core workstation installed
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Biomancy core workstation installed
- **Unlock popup:** `PLEASED` · `CEREMONIAL` — Gnarl's own Silence-era discipline finally gets a chamber (R-039, Q-006)
- **Completion popup:** `PLEASED` · `CEREMONIAL` — what he researched for centuries without a Master is now housed
- **Consequence:** Room counts toward the silent Bosses'Rise readiness gate (§7.1); activates the corresponding magic line (Q152)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an already-installed facility
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q006; Gnarl/Biomancy authorship per R-039 and Q-006


## Minion Restoration

### `OR-MIN-001` — minions/restore_red

- **Role:** Restore the Red tribe
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-OPN-001
- **Objective (specification):** Enter the Netherworld, kill a Blaze, return with a Blaze Rod
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** All feat steps complete
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the Reds can be recovered
- **Completion popup:** `PLEASED` · `REWARD` — fire returns to the Horde
- **Consequence:** Invokes the Minion owner API to unlock the Red tribe (§8); one completion presentation (Q011)
- **Persistent fact:** overlord_reign:minions/red_recovered
- **Sequence-break handling:** Recognize a pre-existing Blaze Rod
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q009, Q010, Q011, §8.2-8.4

### `OR-MIN-002` — minions/restore_green

- **Role:** Restore the Green tribe
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MIN-001
- **Objective (specification):** Brew a Potion of Poison, become poisoned, kill a Witch while poisoned
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** All feat steps complete
- **Unlock popup:** `RELISHING` · `MOCKING` — the feat requires the Master to poison himself on purpose
- **Completion popup:** `RELISHING` · `MOCKING` — it worked, and Gnarl found the method very funny
- **Consequence:** Invokes the Minion owner API to unlock the Green tribe (§8); one completion presentation (Q011)
- **Persistent fact:** overlord_reign:minions/green_recovered
- **Sequence-break handling:** Recognize a prior Witch kill only if poisoned at the time
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q009, Q010, Q011, §8.2-8.4

### `OR-MIN-003` — minions/restore_blue

- **Role:** Restore the Blue tribe
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MIN-002
- **Objective (specification):** Prepare Water Breathing, enter an Ocean Monument, kill an Elder Guardian
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** All feat steps complete
- **Unlock popup:** `COMPOSED` · `TACTICAL` — drowning is a real risk to a thin Horde (§8: depletion is an operational problem)
- **Completion popup:** `PLEASED` · `CEREMONIAL` — the Horde is whole for the first time since the Silence
- **Consequence:** Invokes the Minion owner API to unlock the Blue tribe (§8); one completion presentation (Q011)
- **Persistent fact:** overlord_reign:minions/blue_recovered
- **Sequence-break handling:** Recognize a prior Elder Guardian kill
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q009, Q010, Q011, §8.2-8.4


## Bosses'Rise

### `OR-BOS-001` — bosses_rise/skor/locate

- **Role:** Locate the skor domain
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** All five locate quests activate together when the §7.1 readiness gate is satisfied (Q159)
- **Prerequisite:** Tower readiness gate
- **Objective (specification):** Locate the Nordberg / frozen domain domain / anomaly
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Domain located
- **Unlock popup:** `COMPOSED` · `TACTICAL` — something feeds on the frozen north
- **Completion popup:** `COMPOSED` · `TACTICAL` — the domain is located
- **Consequence:** Opens the investigation stage (Q012)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior discovery of the domain
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q159

### `OR-BOS-002` — bosses_rise/skor/investigate

- **Role:** Establish what skor is
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-001
- **Objective (specification):** Trigger one qualifying Phase 2 frost / icicle attack event without killing Skor
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Native proof event observed
- **Unlock popup:** `COMPOSED` · `TACTICAL` — a beast empowered by the wound, nothing more
- **Completion popup:** `COMPOSED` · `TACTICAL` — the evidence is understood
- **Consequence:** Gnarl delivers the approved interpretation of Skor / Yeti
- **Persistent fact:** NONE
- **Sequence-break handling:** Native proof may already have occurred
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q143

### `OR-BOS-003` — bosses_rise/skor/defeat

- **Role:** Destroy skor
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-002
- **Objective (specification):** Defeat Skor / Yeti
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** block_factorys_bosses:kill_yeti
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the kill order
- **Completion popup:** `PLEASED` · `REWARD` — the north is quiet
- **Consequence:** Advances the five-subcampaign progression
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an early boss kill; never respawn
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q013-Q017

### `OR-BOS-004` — bosses_rise/sirok/locate

- **Role:** Locate the sirok domain
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** All five locate quests activate together when the §7.1 readiness gate is satisfied (Q159)
- **Prerequisite:** Tower readiness gate
- **Objective (specification):** Locate the Ruborian desert domain / anomaly
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Domain located
- **Unlock popup:** `COMPOSED` · `TACTICAL` — the desert is moving where it should not
- **Completion popup:** `COMPOSED` · `TACTICAL` — the domain is located
- **Consequence:** Opens the investigation stage (Q012)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior discovery of the domain
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q159

### `OR-BOS-005` — bosses_rise/sirok/investigate

- **Role:** Establish what sirok is
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-004
- **Objective (specification):** Crack one armored body segment, then strike it again to trigger the native poisonous-blood spill
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Native proof event observed
- **Unlock popup:** `COMPOSED` · `TACTICAL` — the worm was enlarged by rift energy
- **Completion popup:** `COMPOSED` · `TACTICAL` — the evidence is understood
- **Consequence:** Gnarl delivers the approved interpretation of Sirok / Sandworm
- **Persistent fact:** NONE
- **Sequence-break handling:** Native proof may already have occurred
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q144

### `OR-BOS-006` — bosses_rise/sirok/defeat

- **Role:** Destroy sirok
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-005
- **Objective (specification):** Defeat Sirok / Sandworm
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** block_factorys_bosses:kill_sandworm
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the kill order
- **Completion popup:** `PLEASED` · `REWARD` — the desert is emptied
- **Consequence:** Advances the five-subcampaign progression
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an early boss kill; never respawn
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q013-Q017

### `OR-BOS-007` — bosses_rise/ashlord/locate

- **Role:** Locate the ashlord domain
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** All five locate quests activate together when the §7.1 readiness gate is satisfied (Q159)
- **Prerequisite:** Tower readiness gate
- **Objective (specification):** Locate the Dragon Tower domain / anomaly
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Domain located
- **Unlock popup:** `DARKENED` · `OMINOUS` — a dragon has been raised from the dead
- **Completion popup:** `COMPOSED` · `TACTICAL` — the domain is located
- **Consequence:** Opens the investigation stage (Q012)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior discovery of the domain
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q159

### `OR-BOS-008` — bosses_rise/ashlord/investigate

- **Role:** Establish what ashlord is
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-007
- **Objective (specification):** Recover one native block_factorys_bosses:dragon_banner from the bound Dragon Tower
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Native proof event observed
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — the first of the resurrected
- **Completion popup:** `COMPOSED` · `TACTICAL` — the evidence is understood
- **Consequence:** Gnarl delivers the approved interpretation of Ashlord
- **Persistent fact:** NONE
- **Sequence-break handling:** Native proof may already have occurred
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q145

### `OR-BOS-009` — bosses_rise/ashlord/defeat

- **Role:** Destroy ashlord
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-008
- **Objective (specification):** Defeat Ashlord
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** block_factorys_bosses:kill_dragon
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the kill order
- **Completion popup:** `PLEASED` · `REWARD` — the resurrection is undone
- **Consequence:** Advances the five-subcampaign progression
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an early boss kill; never respawn
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q013-Q017

### `OR-BOS-010` — bosses_rise/helvar/locate

- **Role:** Locate the helvar domain
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** All five locate quests activate together when the §7.1 readiness gate is satisfied (Q159)
- **Prerequisite:** Tower readiness gate
- **Objective (specification):** Locate the Underworld domain / anomaly
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Domain located
- **Unlock popup:** `STRICKEN` · `STRICKEN` — the Third Overlord is loose: Gnarl's last Master, to whom he expressed unusual personal attachment and whose escape from the Infernal Abyss he allowed might still happen. The news lands before he can compose himself.
- **Completion popup:** `COMPOSED` · `TACTICAL` — the domain is located
- **Consequence:** Opens the investigation stage (Q012)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior discovery of the domain
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q159

### `OR-BOS-011` — bosses_rise/helvar/investigate

- **Role:** Establish what helvar is
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-010
- **Objective (specification):** Obtain the native block_factorys_bosses:underworld_arena_key and use it to pass the boss-door progression
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Native proof event observed
- **Unlock popup:** `DARKENED` · `OMINOUS` — Gnarl confirms the identity while holding himself together — control kept, valence negative. The break comes when it is done, not while he is still naming it.
- **Completion popup:** `COMPOSED` · `TACTICAL` — the evidence is understood
- **Consequence:** Gnarl delivers the approved interpretation of Helvar / Third Overlord
- **Persistent fact:** NONE
- **Sequence-break handling:** Native proof may already have occurred
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q146

### `OR-BOS-012` — bosses_rise/helvar/defeat

- **Role:** Destroy helvar
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-011
- **Objective (specification):** Defeat Helvar / Third Overlord
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** block_factorys_bosses:kill_underworld_knight
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the kill order
- **Completion popup:** `STRICKEN` · `STRICKEN` — the Master Gnarl waited centuries for is dead by his replacement's hand. The mask is gone. No mockery anywhere in this line.
- **Consequence:** Advances the five-subcampaign progression
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an early boss kill; never respawn
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q013-Q017

### `OR-BOS-013` — bosses_rise/nerakyss/locate

- **Role:** Locate the nerakyss domain
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** All five locate quests activate together when the §7.1 readiness gate is satisfied (Q159)
- **Prerequisite:** Tower readiness gate
- **Objective (specification):** Locate the Kraken Ship domain / anomaly
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Domain located
- **Unlock popup:** `COMPOSED` · `TACTICAL` — the sea has leaked for centuries
- **Completion popup:** `COMPOSED` · `TACTICAL` — the domain is located
- **Consequence:** Opens the investigation stage (Q012)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior discovery of the domain
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q159

### `OR-BOS-014` — bosses_rise/nerakyss/investigate

- **Role:** Establish what nerakyss is
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-013
- **Objective (specification):** Defeat the three native pirate guard variants protecting the encounter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Native proof event observed
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — old contamination, still running
- **Completion popup:** `COMPOSED` · `TACTICAL` — the evidence is understood
- **Consequence:** Gnarl delivers the approved interpretation of Nerakyss
- **Persistent fact:** NONE
- **Sequence-break handling:** Native proof may already have occurred
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q147

### `OR-BOS-015` — bosses_rise/nerakyss/defeat

- **Role:** Destroy nerakyss
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-BOS-014
- **Objective (specification):** Defeat Nerakyss
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** block_factorys_bosses:kill_kraken
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the kill order
- **Completion popup:** `PLEASED` · `REWARD` — the ocean threat is cut
- **Consequence:** Advances the five-subcampaign progression
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize an early boss kill; never respawn
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q012, Q013-Q017


## End Campaign

### `OR-END-001` — end/expedition

- **Role:** Enter the Cataclysm Dimension
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately after the post-five Gnarl presentation-only transition (Q018)
- **Prerequisite:** All five Bosses'Rise subcampaigns
- **Objective (specification):** Enter the End dimension
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Entering the dimension is the authored threshold (Q019)
- **Unlock popup:** `DARKENED` · `OMINOUS` — the End is the Wasteland rupture the Cataclysm exposed (R-034)
- **Completion popup:** `DARKENED` · `OMINOUS` — the wound is entered
- **Consequence:** Establishes the source-side wound; completion presentation identifies the Ender Dragon as the living anchor (Q160)
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior End entry
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q018, Q019

### `OR-END-002` — end/ender_dragon

- **Role:** Resolve the living anchor
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** After the End-entry completion presentation has played (Q160)
- **Prerequisite:** OR-END-001
- **Objective (specification):** Defeat the Ender Dragon
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Ender Dragon defeated
- **Unlock popup:** `DARKENED` · `OMINOUS` — the living anchor sustaining the wound
- **Completion popup:** `PLEASED` · `CEREMONIAL` — the reign is secure
- **Consequence:** Resolves the living anchor; triggers the one-time Gnarl ending presentation (Q020, Q023)
- **Persistent fact:** overlord_reign:campaign/central_ending (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Handled by the approved early-dragon policy (Q021)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q020, Q160


## Post-Credits

### `OR-PCR-001` — post_credits/return

- **Role:** Post-ending End expedition
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately after the one-time Gnarl ending presentation (Q023)
- **Prerequisite:** OR-END-002
- **Objective (specification):** Return to the End after the ending
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:visit_dimension_history on minecraft:the_end, evaluated after the ending fact is present
- **Unlock popup:** `COMPOSED` · `CHOICE` — the war is won; curiosity replaces urgency
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the End is changed
- **Consequence:** Advances the expedition
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q047, Q170

### `OR-PCR-002` — post_credits/ecology

- **Role:** Post-ending End expedition
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-PCR-001
- **Objective (specification):** Explore the altered outer ecology
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill_history — one kill of each of the installed outer-End creature set
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — an altered ecology, catalogued
- **Completion popup:** `RELISHING` · `MOCKING` — Gnarl finds the new End absurd
- **Consequence:** Advances the expedition
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q047, Q170

### `OR-PCR-003` — post_credits/ruins

- **Role:** Post-ending End expedition
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-PCR-002
- **Objective (specification):** Investigate major ruins, cities and island content
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:visit_structure_history — the installed End city / ruin structure set
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — ruins and cities to pick over
- **Completion popup:** `PLEASED` · `REWARD` — spoils
- **Consequence:** Advances the expedition
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q047, Q170

### `OR-PCR-004` — post_credits/capstone

- **Role:** Post-ending End expedition
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-PCR-003
- **Objective (specification):** Complete both explicit Outer End major-structure discovery accomplishments and close the expedition
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** AND(outer_end:end/find_catacombs, outer_end:end/find_end_tower)  [Q170: both explicit Outer End structure accomplishments]
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — one last thing worth seeing
- **Completion popup:** `RELISHING` · `MOCKING` — nothing left to conquer; smug and faintly bored
- **Consequence:** Closes the post-credits expedition
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q047, Q170


## Magic

### `OR-MAG-IRO-001` — magic/irons/practical_casting

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-012
- **Objective (specification):** Establish practical spellcasting
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** irons_spellbooks:irons_spellbooks/spell_book_equip
- **Unlock popup:** `PLEASED` · `CEREMONIAL` — the discipline opens
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Advances the Iron's Spells arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q124

### `OR-MAG-IRO-002` — magic/irons/shape_capability

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-IRO-001
- **Objective (specification):** Shape / improve spell capability
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** irons_spellbooks:irons_spellbooks/make_arcane_anvil
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the next stage
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Advances the Iron's Spells arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q125

### `OR-MAG-IRO-003` — magic/irons/advanced_casting

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-IRO-002
- **Objective (specification):** Affect a hostile target with one offensive spell and use one non-damage utility, defensive or movement spell
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:or of two framework objectives — one offensive spell hit on a hostile mob, one utility/defensive/movement cast (Q236)
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the next stage
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Advances the Iron's Spells arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q126

### `OR-MAG-IRO-004` — magic/irons/mastery_or

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-IRO-003
- **Objective (specification):** Final OR mastery using Holy or Blood; records the first inclination
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR(irons_spellbooks:irons_spellbooks/spell_book_dead_king, irons_spellbooks:irons_spellbooks/spell_book_blood)
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the next stage
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Completes the Iron's Spells arc
- **Persistent fact:** overlord_reign:magic/irons/first_inclination (holy|blood) — Q024 (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q024, Q127

### `OR-MAG-EID-001` — magic/eidolon/basic_ritual

- **Role:** Eidolon discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-013
- **Objective (specification):** Basic ritual practice
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** eidolon:brazier
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — occult procedure (Q096: not Mortis material)
- **Completion popup:** `PLEASED` · `REWARD` — a path taken
- **Consequence:** Advances the Eidolon arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q128

### `OR-MAG-EID-002` — magic/eidolon/sacrifice_prep

- **Role:** Eidolon discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-EID-001
- **Objective (specification):** Meaningful sacrifice / material preparation
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** eidolon:sacrifice
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — occult procedure (Q096: not Mortis material)
- **Completion popup:** `PLEASED` · `REWARD` — a path taken
- **Consequence:** Advances the Eidolon arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q129

### `OR-MAG-EID-003` — magic/eidolon/advanced_occult

- **Role:** Eidolon discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-EID-002
- **Objective (specification):** Advanced ritual / occult capability
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** eidolon:soul_shard
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — occult procedure (Q096: not Mortis material)
- **Completion popup:** `PLEASED` · `REWARD` — a path taken
- **Consequence:** Advances the Eidolon arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q130

### `OR-MAG-EID-004` — magic/eidolon/mastery_or

- **Role:** Eidolon discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-EID-003
- **Objective (specification):** Final OR mastery between the Sacred and Wicked paths
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR(eidolon:sacred_path, eidolon:wicked_path)
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — occult procedure (Q096: not Mortis material)
- **Completion popup:** `COMPOSED` · `CHOICE` — a path taken
- **Consequence:** Completes the Eidolon arc
- **Persistent fact:** overlord_reign:magic/eidolon/path (sacred|wicked) — Q025 (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q025, Q131

### `OR-MAG-THE-001` — magic/theurgy/material_principle

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-010
- **Objective (specification):** Understand / extract material principle
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** theurgy:has_liquefaction_cauldron
- **Unlock popup:** `PLEASED` · `CEREMONIAL` — the discipline opens
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Advances the Theurgy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q027, Q133

### `OR-MAG-THE-002` — magic/theurgy/controlled_conversion

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-THE-001
- **Objective (specification):** Perform controlled conversion
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** theurgy:has_basic_rod
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the next stage
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Advances the Theurgy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q027, Q133

### `OR-MAG-THE-003` — magic/theurgy/reproduce_material

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-THE-002
- **Objective (specification):** Reproduce a useful material deliberately
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** theurgy:has_t2_rod
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the next stage
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Advances the Theurgy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q027, Q133

### `OR-MAG-THE-004` — magic/theurgy/advanced_transmutation

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-THE-003
- **Objective (specification):** Demonstrate advanced repeatable transmutation without requiring every machine
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** theurgy:has_t4_rod
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the next stage
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Completes the Theurgy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q027, Q133

### `OR-MAG-BIO-001` — magic/biomancy/organic_matter

- **Role:** Biomancy discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-014
- **Objective (specification):** Acquire / understand organic matter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** biomancy:biomancy/root
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — Gnarl explains where this came from: Solarius, and centuries of boredom (Q-006)
- **Completion popup:** `PLEASED` · `REWARD` — the Master has entered his work
- **Consequence:** Advances the Biomancy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q029, Q135; Gnarl/Biomancy authorship per R-039 and Q-006

### `OR-MAG-BIO-002` — magic/biomancy/primordial_core

- **Role:** Biomancy discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-BIO-001
- **Objective (specification):** Establish Primordial Core / Cradle capability
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** biomancy:biomancy/cradle
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gnarl walks the Master through his own method
- **Completion popup:** `PLEASED` · `REWARD` — the Cradle lives; he is proprietary about it
- **Consequence:** Advances the Biomancy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q029, Q135; Gnarl/Biomancy authorship per R-039 and Q-006

### `OR-MAG-BIO-003` — magic/biomancy/living_flesh

- **Role:** Biomancy discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-BIO-002
- **Objective (specification):** Manipulate living flesh
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** biomancy:biomancy/living_flesh
- **Unlock popup:** `PLEASED` · `CEREMONIAL` — the animation of flesh is the whole point of the research
- **Completion popup:** `PLEASED` · `CEREMONIAL` — what he could never do alone is being done
- **Consequence:** Advances the Biomancy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q029, Q135; Gnarl/Biomancy authorship per R-039 and Q-006

### `OR-MAG-BIO-004` — magic/biomancy/bio_forge

- **Role:** Biomancy discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-BIO-003
- **Objective (specification):** Use the Decomposer / Bio Forge
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** biomancy:biomancy/bio_forge
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the industrial stage of his own discipline
- **Completion popup:** `PLEASED` · `REWARD` — the Forge runs
- **Consequence:** Advances the Biomancy arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q029, Q135; Gnarl/Biomancy authorship per R-039 and Q-006

### `OR-MAG-BIO-005` — magic/biomancy/advanced_proof

- **Role:** Biomancy discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-BIO-004
- **Objective (specification):** Complete a serum or advanced bio-engineering proof
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** biomancy:biomancy/bio_lab
- **Unlock popup:** `PLEASED` · `CEREMONIAL` — the thing he never had a Master to attempt
- **Completion popup:** `PLEASED` · `CEREMONIAL` — he finally has an Overlord to direct the work toward suitably unfortunate subjects
- **Consequence:** Completes the Biomancy arc
- **Persistent fact:** overlord_reign:magic/biomancy/discipline_established
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q029, Q135; Gnarl/Biomancy authorship per R-039 and Q-006

### `OR-MAG-GLU-001` — magic/gluttony/cuisine_foundation

- **Role:** Gluttony discipline stage
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-011
- **Objective (specification):** Prepared cuisine foundation
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** farmers_spell:root
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gristle sets the task
- **Completion popup:** `PLEASED` · `REWARD` — Gristle is pleased with the result
- **Consequence:** Advances the Gluttony / Farmer's Spell arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q026, Q132

### `OR-MAG-GLU-002` — magic/gluttony/alchemist_pot

- **Role:** Gluttony discipline stage
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-GLU-001
- **Objective (specification):** Alchemist Pot magical bridge
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** farmers_spell:alchemist_pot
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gristle sets the task
- **Completion popup:** `PLEASED` · `REWARD` — Gristle is pleased with the result
- **Consequence:** Advances the Gluttony / Farmer's Spell arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q026, Q132

### `OR-MAG-GLU-003` — magic/gluttony/culmination

- **Role:** Gluttony discipline stage
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-GLU-002
- **Objective (specification):** Meaningful Gluttony culmination
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** farmers_spell:food_shaman
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gristle sets the task
- **Completion popup:** `PLEASED` · `REWARD` — Gristle is pleased with the result
- **Consequence:** Completes the Gluttony / Farmer's Spell arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q026, Q132

### `OR-MAG-ELI-001` — magic/elixirum/identify_properties

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately after the corresponding Tower room restoration completes (Q152)
- **Prerequisite:** OR-TWR-009
- **Objective (specification):** Identify ingredient properties
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:elixirum_mastery — first mastery threshold (framework type already implemented)
- **Unlock popup:** `PLEASED` · `CEREMONIAL` — the discipline opens
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Advances the Ars Elixirum arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q028, Q134

### `OR-MAG-ELI-002` — magic/elixirum/formulate

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-ELI-001
- **Objective (specification):** Formulate intentionally in the Glass Cauldron
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on any elixirum elixir produced in the Glass Cauldron
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the next stage
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Advances the Ars Elixirum arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q028, Q134

### `OR-MAG-ELI-003` — magic/elixirum/mastery

- **Role:** Magic discipline stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-MAG-ELI-002
- **Objective (specification):** Increase Mastery and complete one controlled advanced formulation
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:elixirum_mastery (custom objective type already in the framework)
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the next stage
- **Completion popup:** `PLEASED` · `REWARD` — power taken and kept
- **Consequence:** Completes the Ars Elixirum arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q028, Q134


## Farming / Automation

### `OR-FRM-001` — farming/productive_farm

- **Role:** Estate agriculture stage
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** OPEN - arc activation trigger not decided
- **Prerequisite:** —
- **Objective (specification):** Cultivate at least three staple crop types to maturity and harvest one of each after activation; no location anchor, harvest variables only
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:stat — crop_mined/harvest counters for three staple crop types; no location predicate (Q165)
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gristle wants the estate fed
- **Completion popup:** `PLEASED` · `REWARD` — the estate provides
- **Consequence:** Advances the estate agriculture arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior harvests
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q165

### `OR-FRM-002` — farming/crop_critters

- **Role:** Estate agriculture stage
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-FRM-001
- **Objective (specification):** Crop Critter capability proof
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill_history is wrong here; use a presence check on cropcritters:wheat_critter — capability, not a kill (Q031)
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gristle wants the estate fed
- **Completion popup:** `PLEASED` · `REWARD` — the estate provides
- **Consequence:** Advances the estate agriculture arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior harvests
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q031

### `OR-FRM-003` — farming/hay_golem

- **Role:** Estate agriculture stage
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-FRM-002
- **Objective (specification):** Prove assisted estate labour: after the Crop Critter stage, reach an authored harvest threshold that ordinary hand-farming would not produce. The Hay Golem is the intended means; the proof is the output it enables, not the golem itself.
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:stat on crop-harvest counters, threshold reached after OR-FRM-002 completes
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gristle wants the estate fed
- **Completion popup:** `PLEASED` · `REWARD` — the estate provides
- **Consequence:** Advances the estate agriculture arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior harvests
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q031; REWORDED 2026-09-18. The original objective named the golem, which no framework objective type can see: golemoverhaul builds it from an in-world multiblock pattern and its only advancement fires on `recipe_unlocked`, which a hay block in the inventory can trigger. The stage's approved purpose under Q031/Q032 is estate agriculture, so the proof is measured on output. **Caveat: this does not verify a Hay Golem exists.** A player reaching the threshold another way satisfies it.

### `OR-FRM-004` — farming/assisted_estate

- **Role:** Estate agriculture stage
- **Presenter:** Gristle
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:**  Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-FRM-003
- **Objective (specification):** Assisted estate operation
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:stat on harvest counters achieved while the Hay Golem entity is present
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Gristle wants the estate fed
- **Completion popup:** `PLEASED` · `REWARD` — the estate provides
- **Consequence:** Advances the estate agriculture arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior harvests
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q031


## Adventure

### `OR-ADV-TWIL-001` — adventures/twilight/ch1

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Enter Twilight Forest, defeat the Naga, defeat the Lich
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** twilightforest:progress_lich
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — an unfamiliar realm; a fae refuge from Imperial persecution, not Cataclysm-made
- **Completion popup:** `PLEASED` · `REWARD` — the first tier falls
- **Consequence:** Advances the Twilight Forest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q112

### `OR-ADV-TWIL-002` — adventures/twilight/ch2

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-TWIL-001
- **Objective (specification):** Labyrinth / Minoshroom, then the Hydra
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** twilightforest:progress_hydra
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — deeper tiers
- **Completion popup:** `PLEASED` · `REWARD` — progress
- **Consequence:** Advances the Twilight Forest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q112

### `OR-ADV-TWIL-003` — adventures/twilight/ch3

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-TWIL-002
- **Objective (specification):** Trophy Pedestal, Knight Phantoms, Ghast Trap, Ur-Ghast
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** twilightforest:progress_ur_ghast
- **Unlock popup:** `COMPOSED` · `TACTICAL` — the forest resists
- **Completion popup:** `PLEASED` · `REWARD` — the towers fall
- **Consequence:** Advances the Twilight Forest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q112

### `OR-ADV-TWIL-004` — adventures/twilight/ch4

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-TWIL-003
- **Objective (specification):** Alpha Yeti, then the Snow Queen
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** twilightforest:progress_glacier
- **Unlock popup:** `COMPOSED` · `TACTICAL` — ice and its queen
- **Completion popup:** `PLEASED` · `REWARD` — the glacier is taken
- **Consequence:** Advances the Twilight Forest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q112

### `OR-ADV-TWIL-005` — adventures/twilight/ch5

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-TWIL-004
- **Objective (specification):** Highlands merge gate, Troll Caves, Beanstalk / Giants, Lamp of Cinders
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** twilightforest:progression_end
- **Unlock popup:** `RELISHING` · `MOCKING` — giants and a beanstalk
- **Completion popup:** `RELISHING` · `MOCKING` — the realm is finished and he enjoyed the ending
- **Consequence:** Completes the Twilight Forest arc
- **Persistent fact:** overlord_reign:adventure/twilight_completed (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q112

### `OR-ADV-RATS-001` — adventures/rats/utility

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Tame one rat, configure it with the Cheese Staff, complete one successful item transfer
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:or [ questlog:item_use on rats:cheese_staff | questlog:stat on rat transfer counter ]
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — rats, as a workforce
- **Completion popup:** `RELISHING` · `MOCKING` — vermin made useful
- **Consequence:** Advances the Rats / Ratlantis arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q113

### `OR-ADV-RATS-002` — adventures/rats/access

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-RATS-001
- **Objective (specification):** Ratlantis access beat
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** ratlantis:ratlantis
- **Unlock popup:** `RELISHING` · `MOCKING` — an empire beneath the cheese
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the way in is open
- **Consequence:** Advances the Rats / Ratlantis arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q163

### `OR-ADV-RATS-003` — adventures/rats/investigate

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-RATS-002
- **Objective (specification):** Ratlantis investigation beat
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on ratlantis:gem_of_ratlantis
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — a civilization older than the Overlords and unrelated to them
- **Completion popup:** `COMPOSED` · `HISTORICAL` — the empire is understood
- **Consequence:** Advances the Rats / Ratlantis arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q164

### `OR-ADV-RATS-004` — adventures/rats/capstone

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-RATS-003
- **Objective (specification):** Defeat the Rat Baron
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** ratlantis:defeat_rat_baron
- **Unlock popup:** `COMPOSED` · `TACTICAL` — the Rat Baron rules here
- **Completion popup:** `RELISHING` · `MOCKING` — a rat monarch deposed
- **Consequence:** Advances the Rats / Ratlantis arc
- **Persistent fact:** overlord_reign:adventure/ratlantis_completed (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q114

### `OR-ADV-RATS-005` — adventures/rats/closure

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-RATS-004
- **Objective (specification):** Return and close the expedition
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:visit_dimension_history — return from Ratlantis to the Overworld after the capstone fact
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the expedition ends
- **Completion popup:** `RELISHING` · `MOCKING` — an absurd conquest, completed
- **Consequence:** Completes the Rats / Ratlantis arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q114

### `OR-ADV-FATH-001` — adventures/fathoms/aberration

- **Role:** Adventure arc stage
- **Presenter:** Historian
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Catch an Aberration
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** fathoms:nautical/catch_aberration
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the Historian withholds judgement
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — a specimen, recorded
- **Consequence:** Advances the Fathoms / Overlord Depths arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q118

### `OR-ADV-FATH-002` — adventures/fathoms/bottle

- **Role:** Adventure arc stage
- **Presenter:** Historian
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-FATH-001
- **Objective (specification):** Open a Message in a Bottle; triggers the Historian investigation presentation
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** fathoms:nautical/open_message_in_a_bottle
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — a message, and a question
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the investigation begins
- **Consequence:** Advances the Fathoms / Overlord Depths arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q118

### `OR-ADV-FATH-003` — adventures/fathoms/scrawls

- **Role:** Adventure arc stage
- **Presenter:** Historian
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-FATH-002
- **Objective (specification):** Obtain all Sunken Scrawls and have the Historian decode the evidence
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** fathoms:nautical/obtain_all_sunken_scrawls
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — evidence to be decoded
- **Completion popup:** `PLEASED` · `REWARD` — a complete set pleases the Historian
- **Consequence:** Advances the Fathoms / Overlord Depths arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q118

### `OR-ADV-FATH-004` — adventures/fathoms/rocky_waters

- **Role:** Adventure arc stage
- **Presenter:** Historian
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-FATH-003
- **Objective (specification):** Enter Rocky Waters
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** fathoms:nautical/enter_rocky_waters
- **Unlock popup:** `COMPOSED` · `TACTICAL` — the expedition is hazardous
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the waters are surveyed
- **Consequence:** Advances the Fathoms / Overlord Depths arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q118

### `OR-ADV-FATH-005` — adventures/fathoms/ritual

- **Role:** Adventure arc stage
- **Presenter:** Historian
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-FATH-004
- **Objective (specification):** Perform one native ritual proving the corruption can be manipulated
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** fathoms:nautical/perform_ritual
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — the corruption may be manipulable
- **Completion popup:** `PLEASED` · `REWARD` — the hypothesis is confirmed
- **Consequence:** Completes the Fathoms / Overlord Depths arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q118

### `OR-ADV-KNIG-001` — adventures/knightquest/chalice

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Craft and place the Great Chalice
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:block_interact on the placed Great Chalice block
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — a rival faith and its relic; the Netherman belongs to the Forgotten God, not the Cataclysm
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the chalice stands
- **Consequence:** Advances the Knight Quest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q117

### `OR-ADV-KNIG-002` — adventures/knightquest/essence

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-KNIG-001
- **Objective (specification):** Obtain and offer four Great Essence to fill the Chalice
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain x4 on knightquest:great_essence
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the chalice must be filled
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — filled
- **Consequence:** Advances the Knight Quest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q117

### `OR-ADV-KNIG-003` — adventures/knightquest/radiant

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-KNIG-002
- **Objective (specification):** Create Radiant Essence and offer it to the filled Chalice to summon Netherman
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on knightquest:radiant_essence
- **Unlock popup:** `COMPOSED` · `TACTICAL` — the summoning invites something
- **Completion popup:** `DARKENED` · `OMINOUS` — the Netherman comes
- **Consequence:** Advances the Knight Quest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q117

### `OR-ADV-KNIG-004` — adventures/knightquest/netherman

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-KNIG-003
- **Objective (specification):** Defeat Netherman, the Architect of Chaos
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill on the Netherman entity
- **Unlock popup:** `DARKENED` · `OMINOUS` — the Architect of Chaos
- **Completion popup:** `PLEASED` · `REWARD` — a rival power destroyed
- **Consequence:** Completes the Knight Quest arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q117

### `OR-ADV-GRAV-001` — adventures/graveyard/ruins

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Investigate the ruins
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** graveyard:graveyard/medium_graveyard
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — the dead here do not stay buried
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the ruins are surveyed
- **Consequence:** Advances the The Graveyard arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q034

### `OR-ADV-GRAV-002` — adventures/graveyard/fragments

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-GRAV-001
- **Objective (specification):** Recover all three Ominous Bone Staff fragments
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** AND(graveyard:graveyard/lower_bone_staff, middle_bone_staff, upper_bone_staff)
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — three pieces of a staff
- **Completion popup:** `PLEASED` · `REWARD` — the staff is whole
- **Consequence:** Advances the The Graveyard arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q034

### `OR-ADV-GRAV-003` — adventures/graveyard/summon

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-GRAV-002
- **Objective (specification):** Find the Lich Altar, fill the Blood Vial, perform the native nighttime summoning
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** graveyard:graveyard/summon_lich
- **Unlock popup:** `RELISHING` · `MOCKING` — the Master will call it up on purpose
- **Completion popup:** `DARKENED` · `OMINOUS` — the Lich answers
- **Consequence:** Advances the The Graveyard arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q116

### `OR-ADV-GRAV-004` — adventures/graveyard/lich

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-GRAV-003
- **Objective (specification):** Defeat the Corrupted Champion / Lich
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill on the Lich entity
- **Unlock popup:** `COMPOSED` · `TACTICAL` — the Corrupted Champion
- **Completion popup:** `PLEASED` · `REWARD` — put back down
- **Consequence:** Completes the The Graveyard arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q034, Q116

### `OR-ADV-BUMB-001` — adventures/bumblezone/enter

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Discover and enter the realm
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** the_bumblezone:root
- **Unlock popup:** `RELISHING` · `MOCKING` — a realm of bees, of all things
- **Completion popup:** `RELISHING` · `MOCKING` — the Master is in the hive
- **Consequence:** Advances the The Bumblezone arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q042

### `OR-ADV-BUMB-002` — adventures/bumblezone/queen

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-BUMB-001
- **Objective (specification):** Establish meaningful Queen contact
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** the_bumblezone:beehemoth/queen_beehemoth
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the Queen must be dealt with
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the Queen receives him
- **Consequence:** Advances the The Bumblezone arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q169

### `OR-ADV-BUMB-003` — adventures/bumblezone/essence

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-BUMB-002
- **Objective (specification):** Complete Queen's Desires and obtain / consume Essence of the Bees
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** the_bumblezone:essence/bee_essence_infusion
- **Unlock popup:** `RELISHING` · `MOCKING` — the Queen has demands
- **Completion popup:** `RELISHING` · `MOCKING` — the Overlord ran errands for a bee
- **Consequence:** Completes the The Bumblezone arc
- **Persistent fact:** overlord_reign:adventure/bumblezone_completed (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q042

### `OR-ADV-LOST-001` — adventures/lost_castle/discover

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Discover and enter the castle
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:visit_structure_history on tlc:lost_castle
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — an obscure predecessor civilization left this standing
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the castle is found
- **Consequence:** Advances the The Lost Castle arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q036, Q167

### `OR-ADV-LOST-002` — adventures/lost_castle/clear

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-LOST-001
- **Objective (specification):** Investigate predecessor evidence and clear the Illager occupation
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill on the Illager occupants within tlc:lost_castle
- **Unlock popup:** `COMPOSED` · `TACTICAL` — Illagers are squatting in it
- **Completion popup:** `PLEASED` · `REWARD` — the squatters are cleared
- **Consequence:** Advances the The Lost Castle arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q036, Q167

### `OR-ADV-LOST-003` — adventures/lost_castle/treasure

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-LOST-002
- **Objective (specification):** Recover the expedition's meaningful treasure / evidence and close the arc
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on an item from the tlc:chests/throne or tlc:chests/treasure loot table
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — whatever they were guarding
- **Completion popup:** `PLEASED` · `REWARD` — a predecessor's hoard, taken
- **Consequence:** Completes the The Lost Castle arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q036, Q167

### `OR-ADV-ORCH-001` — adventures/orchid/shrine

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Discover the shrine / local cult context
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:visit_structure_history on oddities:orchid_shrine
- **Unlock popup:** `COMPOSED` · `TACTICAL` — something in the wood is killing and it is not ours
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the shrine is found
- **Consequence:** Advances the Oddities / Queen of Orchid arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q037, Q168

### `OR-ADV-ORCH-002` — adventures/orchid/prepare

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-ORCH-001
- **Objective (specification):** Investigate or prepare for the nature-spirit threat
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on oddities:orchid_heart
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the shrine wants a heart
- **Completion popup:** `RELISHING` · `MOCKING` — Gnarl approves of the price
- **Consequence:** Advances the Oddities / Queen of Orchid arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q037, Q168

### `OR-ADV-ORCH-003` — adventures/orchid/queen

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-ORCH-002
- **Objective (specification):** Defeat / resolve the Queen of Orchid encounter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill on oddities:queen_of_orchid
- **Unlock popup:** `COMPOSED` · `TACTICAL` — a local nature-spirit power, independent of every major history
- **Completion popup:** `PLEASED` · `REWARD` — the wood is quiet
- **Consequence:** Completes the Oddities / Queen of Orchid arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q037, Q168

### `OR-ADV-PET_-001` — adventures/pet_cemetery/collar

- **Role:** Adventure arc stage
- **Presenter:** Mortis
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Pet death and collar recovery
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on pet_cemetery pet collar, gated on the pet-death fact
- **Unlock popup:** `COMPOSED` · `ADMINISTRATIVE` — Mortis attends a death as Spawning Pit business, black velvet and scythe
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the collar is recovered
- **Consequence:** Advances the Pet Cemetery arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q038

### `OR-ADV-PET_-002` — adventures/pet_cemetery/respawn

- **Role:** Adventure arc stage
- **Presenter:** Mortis
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-PET_-001
- **Objective (specification):** Charged Respawn Anchor resurrection into zombie-pet state
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** pet_cemetery:nether/respawn_pet
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Mortis explains what the Pit can do
- **Completion popup:** `RELISHING` · `MOCKING` — it came back wrong; Mortis is morbidly unbothered
- **Consequence:** Advances the Pet Cemetery arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q038

### `OR-ADV-PET_-003` — adventures/pet_cemetery/cure

- **Role:** Adventure arc stage
- **Presenter:** Mortis
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-PET_-002
- **Objective (specification):** Cure the same pet back to life
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** pet_cemetery:nether/cured_zombie_pet
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — it can be made right
- **Completion popup:** `PLEASED` · `REWARD` — a death properly reversed — his actual office
- **Consequence:** Completes the Pet Cemetery arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q038

### `OR-ADV-NIGH-001` — adventures/nightwalker/transform

- **Role:** Take first blood; Lestat arrives and judges how it was taken
- **Presenter:** Gnarl (unlock) / Lestat (completion)
- **Register vocabulary:** reference/40 Lestat range
- **Activation:** On becoming a vampire through the NightWalker / Nycto system — `nycto:become_vampire` is the arc activation trigger, not a quest objective (Q045, reference/37)
- **Prerequisite:** —
- **Objective (specification):** Take first blood. Branch-recording OR: (A) feed from a living victim, or (B) drink a Blood Bottle. Both complete the quest; the arm taken is recorded.
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:or — arm A: first successful feed via VampireFeedingEvents; arm B: consume_item on nycto:blood_bottle. The completing arm is written as a named fact.
- **Unlock popup:** `STRICKEN` · `STRICKEN` — Gnarl alone. The Master has done something that may kill him or unfit him to rule (37 §3, Q-051 Overlord clause). Lestat has not arrived yet.
- **Completion popup:** `PER-BRANCH` · `PER-BRANCH` — Lestat's first appearance, reacting to the arm taken. Arm A (living victim) — register CEREMONIAL, visual `PLEASED`: intimate observation; the Overlord has stopped denying what he is, which is the thing Lestat came for (40 §4). Arm B (Blood Bottle) — register REACTIVE, visual `DARKENED`: philosophical provocation; unnecessary restraint revealing fear of identity (40 §20). He diagnoses repression before weakness and names the gap between what the Overlord is and what he will do under pressure (40 §4).
- **Consequence:** Lestat, who sensed the new presence, arrives at the Dark Tower (44 §15) and reacts to the arm taken. He carries the remainder of the arc. Branch facts: `overlord_reign:nightwalker/first_blood_victim` or `overlord_reign:nightwalker/first_blood_bottled` (TO BE REGISTERED in NARRATIVE_FACTS.md).
- **Persistent fact:** overlord_reign:nightwalker/first_blood_victim OR overlord_reign:nightwalker/first_blood_bottled — branch-specific, one is written
- **Sequence-break handling:** Recognize a feed that occurred before the arc activated
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q045; Overlord decisions this session (supersede Q120); branch reactions per 40 §4 and §20; Lestat register range per 40 (not the Gnarl ten)

### `OR-ADV-NIGH-002` — adventures/nightwalker/survive

- **Role:** Prove the Overlord can survive what comes for vampires
- **Presenter:** Lestat
- **Register vocabulary:** reference/40 Lestat range
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-NIGH-001
- **Objective (specification):** Take down a Vampire Hunter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:entity_kill on nycto:hunter
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Lestat warns that being what he is attracts attention
- **Completion popup:** `PLEASED` · `REWARD` — mastery, not mere completion (40 §20) — a hunter is a real test
- **Consequence:** The Overlord proves he can survive being hunted; Lestat is impressed
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize a prior hunter kill made while a vampire
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q120; Overlord decisions this session (supersede Q120)

### `OR-ADV-NIGH-003` — adventures/nightwalker/altar

- **Role:** Adventure arc stage
- **Presenter:** Lestat
- **Register vocabulary:** reference/40 Lestat range
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ADV-NIGH-002
- **Objective (specification):** First successful Vampire Altar purchase adding one previously unowned power and recording its weakness
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** overlord_reign:nightwalker_power_count
- **Unlock popup:** `COMPOSED` · `CHOICE` — power has a price and Lestat approves of paying it
- **Completion popup:** `RELISHING` · `MOCKING` — Lestat is pleased by the bargain struck
- **Consequence:** Completes the NightWalker arc
- **Persistent fact:** overlord_reign:nightwalker/altar_commitment (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q162

### `OR-ADV-QUAV-001` — adventures/quaver/ensemble

- **Role:** Adventure arc stage
- **Presenter:** Quaver
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Acquire all eight approved instruments: Bagpipe, Flute, Lute, Piano, Trumpet, Tiny Drum, Vielle, Handpan
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain x8 on immersive_melodies:{bagpipe,flute,lute,piano,trumpet,tiny_drum,vielle,handpan} — all eight confirmed present
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Quaver wants his instruments
- **Completion popup:** `RELISHING` · `MOCKING` — the Tower has a band and he will not stop composing about it
- **Consequence:** Completes the Quaver's Tower Band arc
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q111, Q166

### `OR-CAT-001` — adventures/cataclysm/harbinger

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On the first meaningful native discovery / encounter for this Adventure (Q151)
- **Prerequisite:** —
- **Objective (specification):** Discover the bound structure (find_ancient_factory) and defeat The Harbinger
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_harbinger
- **Unlock popup:** `DARKENED` · `OMINOUS` — the Cataclysm is an Elf blowing up an Overlord Tower Heart; Gnarl carries that grievance (R-032, 01)
- **Completion popup:** `PLEASED` · `REWARD` — one of eight
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

### `OR-CAT-002` — adventures/cataclysm/remnant

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-001
- **Objective (specification):** Discover the bound structure (find_cursed_pyramid) and defeat Ancient Remnant
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_remnant
- **Unlock popup:** `DARKENED` · `OMINOUS` — a pyramid that should have stayed shut
- **Completion popup:** `PLEASED` · `REWARD` — put back down
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

### `OR-CAT-003` — adventures/cataclysm/maledictus

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-002
- **Objective (specification):** Discover the bound structure (find_frosted_prison) and defeat Maledictus
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_maledictus
- **Unlock popup:** `COMPOSED` · `TACTICAL` — a prison built to hold something
- **Completion popup:** `PLEASED` · `REWARD` — the curse is broken
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

### `OR-CAT-004` — adventures/cataclysm/monstrosity

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-003
- **Objective (specification):** Discover the bound structure (find_soul_black_smith) and defeat Netherite Monstrosity
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_monstrosity
- **Unlock popup:** `DARKENED` · `OMINOUS` — a forge that makes monstrous things
- **Completion popup:** `PLEASED` · `REWARD` — the warmachine is stopped
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

### `OR-CAT-005` — adventures/cataclysm/leviathan

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-004
- **Objective (specification):** Discover the bound structure (find_sunken_city) and defeat The Leviathan
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_leviathan
- **Unlock popup:** `DARKENED` · `OMINOUS` — a predator in the sunken city
- **Completion popup:** `PLEASED` · `REWARD` — the sea is safer
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

### `OR-CAT-006` — adventures/cataclysm/ignis

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-005
- **Objective (specification):** Discover the bound structure (find_burning_arena) and defeat Ignis; the Ignited Revenant gates the encounter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_ignis
- **Unlock popup:** `DARKENED` · `OMINOUS` — an arena that still burns
- **Completion popup:** `PLEASED` · `REWARD` — the fire is extinguished
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

### `OR-CAT-007` — adventures/cataclysm/scylla

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-006
- **Objective (specification):** Discover the bound structure (find_acropolis) and defeat Scylla; the Clawdian gates the encounter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_scylla
- **Unlock popup:** `COMPOSED` · `TACTICAL` — an acropolis where it has no business being
- **Completion popup:** `PLEASED` · `REWARD` — the storm passes
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

### `OR-CAT-008` — adventures/cataclysm/ender_guardian

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-007
- **Objective (specification):** Discover the bound structure (find_ruined_citadel) and defeat Ender Guardian; the Ender Golem gates the encounter
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_ender_guardian
- **Unlock popup:** `DARKENED` · `OMINOUS` — a guardian behind the citadel
- **Completion popup:** `PLEASED` · `REWARD` — the last of the eight
- **Consequence:** Advances the Cataclysm Adventure line
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q115

### `OR-CAT-009` — adventures/cataclysm/capstone

- **Role:** Adventure arc stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-CAT-008
- **Objective (specification):** Defeat every major Cataclysm boss
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** cataclysm:kill_all_bosses
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — one kill remains
- **Completion popup:** `RELISHING` · `MOCKING` — every major power the Cataclysm left behind is dead; the Elf is avenged by accident
- **Consequence:** Completes the Cataclysm Adventure; eligible major-Adventure ending fact (Q123)
- **Persistent fact:** overlord_reign:adventure/cataclysm_capstone_completed (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Recognize prior native progression
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q043, Q123


## Ice & Fire

### `OR-ICF-001` — ice_and_fire/dragon_mastery/bestiary

- **Role:** Dragon Mastery stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** OPEN - arc activation trigger not decided
- **Prerequisite:** —
- **Objective (specification):** Bestiary / research
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** iceandfire:iceandfire/bestiary
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — dragons studied before they are taken
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the research is done
- **Consequence:** Advances Dragon Mastery
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q048

### `OR-ICF-002` — ice_and_fire/dragon_mastery/harvest

- **Role:** Dragon Mastery stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ICF-001
- **Objective (specification):** Kill a wild adult dragon and harvest materials
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** iceandfire:iceandfire/kill_if_dragon
- **Unlock popup:** `COMPOSED` · `TACTICAL` — a wild adult must be killed
- **Completion popup:** `PLEASED` · `REWARD` — materials taken
- **Consequence:** Advances Dragon Mastery
- **Persistent fact:** NONE
- **Sequence-break handling:** Recognize a prior dragon kill
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q048

### `OR-ICF-003` — ice_and_fire/dragon_mastery/egg

- **Role:** Dragon Mastery stage
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ICF-002
- **Objective (specification):** Obtain an egg from a sufficiently ancient female
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** iceandfire:iceandfire/dragon_egg
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — an egg, stolen from something ancient
- **Completion popup:** `RELISHING` · `MOCKING` — theft from a dragon delights him
- **Consequence:** Advances Dragon Mastery
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q048

### `OR-ICF-004` — ice_and_fire/dragon_mastery/bond

- **Role:** Dragon Mastery capstone
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-ICF-003
- **Objective (specification):** Hatch, raise and bond with a dragon, ending with taking flight on it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:stat on time ridden while mounted on a tamed iceandfire dragon
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — a dragon of the Master's own
- **Completion popup:** `PLEASED` · `CEREMONIAL` — the Overlord flies
- **Consequence:** Completes Dragon Mastery; eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:ice_and_fire/dragon_bonded (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q048

### `OR-DDN-001` — tower/dragon_den/install

- **Role:** Dragon Den installation
- **Presenter:** Giblet the Sixth
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** OPEN - independent later Tower development (§7.1)
- **Prerequisite:** —
- **Objective (specification):** Install the Dragon Den / Dragon Forge infrastructure
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** iceandfire:iceandfire/dragon_forge_core
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — Giblet is getting a dragon forge
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — installed, untested
- **Consequence:** Dragon Den operational
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q049, §7.1

### `OR-DDN-002` — tower/dragon_den/operate

- **Role:** Dragon Den activation
- **Presenter:** Giblet the Sixth
- **Register vocabulary:** authored vocabulary pending — no source corpus for this presenter
- **Activation:** Immediately on completion of the prior visible quest (Q149)
- **Prerequisite:** OR-DDN-001
- **Objective (specification):** Prove the Forge is operational using actual dragon breath
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on iceandfire:dragonsteel_ingot (only producible by an operational Dragon Forge)
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — the forge must be proven
- **Completion popup:** `PLEASED` · `REWARD` — Giblet approves of a forge that works
- **Consequence:** Dragon Den operational
- **Persistent fact:** NONE
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q049, §7.1


## Civilization

### `OR-CIV-001A` — civilizations/villagers/anchor

- **Role:** Lock the villagers anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Spree / Villagers anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — a human settlement, suspicious rather than hostile (Q-023)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

### `OR-CIV-001B` — civilizations/villagers/state

- **Role:** Resolve the villagers anchor. Legal states: NEUTRAL / SUBJUGATED / HOSTILE (Q-026: DESTROYED is a local anchor outcome, not a civilization state)
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-001A
- **Objective (specification):** Resolve the Spree / Villagers civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED · HOSTILE TACTICAL/COMPOSED · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/villagers/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / HOSTILE (Q-026: DESTROYED is a local anchor outcome, not a civilization state)
- **Source:** Q053-Q059

### `OR-CIV-002A` — civilizations/illagers/anchor

- **Role:** Lock the illagers anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Illagers anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `COMPOSED` · `TACTICAL` — initially hostile; authority is established by force (17)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

### `OR-CIV-002B` — civilizations/illagers/state

- **Role:** Resolve the illagers anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED, reached through an intermediate COWED phase (17)
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-002A
- **Objective (specification):** Resolve the Illagers civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED · DESTROYED MOCKING/RELISHING · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/illagers/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED, reached through an intermediate COWED phase (17)
- **Source:** Q198-Q200

### `OR-CIV-003A` — civilizations/dwarves/anchor

- **Role:** Lock the dwarves anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Dwarves anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — a successor hold of the Golden Hills, reduced by Imperial anti-magic persecution (18)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

### `OR-CIV-003B` — civilizations/dwarves/state

- **Role:** Resolve the dwarves anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-003A
- **Objective (specification):** Resolve the Dwarves civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED · DESTROYED MOCKING/RELISHING · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/dwarves/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q171, Q174-

### `OR-CIV-004A` — civilizations/gnumus/anchor

- **Role:** Lock the gnumus anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Gnumus anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `COMPOSED` · `HISTORICAL` — Halflings transformed by Gluttony magic who no longer know it (19)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

### `OR-CIV-004B` — civilizations/gnumus/state

- **Role:** Resolve the gnumus anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-004A
- **Objective (specification):** Resolve the Gnumus civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED · DESTROYED MOCKING/RELISHING · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/gnumus/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q174-Q176

### `OR-CIV-005A` — civilizations/goblins/anchor

- **Role:** Lock the goblins anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Goblins anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `RELISHING` · `MOCKING` — Goblins and Minions call each other cousins; the claim is cultural, not established (20)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

### `OR-CIV-005B` — civilizations/goblins/state

- **Role:** Resolve the goblins anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-005A
- **Objective (specification):** Resolve the Goblins civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED · DESTROYED MOCKING/RELISHING · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/goblins/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q177-Q180

### `OR-CIV-006A` — civilizations/kobolds/anchor

- **Role:** Lock the kobolds anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Kobolds anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `RELISHING` · `MOCKING` — a Den of generally dim underground engineers (21)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

### `OR-CIV-006B` — civilizations/kobolds/state

- **Role:** Resolve the kobolds anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-006A
- **Objective (specification):** Resolve the Kobolds civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED · DESTROYED MOCKING/RELISHING · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/kobolds/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q181-Q184

### `OR-CIV-007A` — civilizations/ribbits/anchor

- **Role:** Lock the ribbits anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Ribbits anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `RELISHING` · `MOCKING` — a cozy musical village that has never threatened anyone (22)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

### `OR-CIV-007B` — civilizations/ribbits/state

- **Role:** Resolve the ribbits anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-007A
- **Objective (specification):** Resolve the Ribbits civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED MOCKING/RELISHING · DESTROYED MOCKING/RELISHING — the contrast is the point (22) · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/ribbits/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q185-Q188

### `OR-CIV-008A` — civilizations/sea_dwellers/anchor

- **Role:** Lock the sea_dwellers anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Sea Dwellers anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — an independent trading village under the water (23)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

### `OR-CIV-008B` — civilizations/sea_dwellers/state

- **Role:** Resolve the sea_dwellers anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-008A
- **Objective (specification):** Resolve the Sea Dwellers civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED · DESTROYED MOCKING/RELISHING · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/sea_dwellers/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q189-Q192

### `OR-CIV-009A` — civilizations/piglins/anchor

- **Role:** Lock the piglins anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Piglins anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `DARKENED` · `REACTIVE` — Piglins descend from a Minion and a pig; it is true and Gnarl would rather not discuss it (24)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

### `OR-CIV-009B` — civilizations/piglins/state

- **Role:** Resolve the piglins anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-009A
- **Objective (specification):** Resolve the Piglins civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED — supremacy restored in his own realm · DESTROYED MOCKING/RELISHING · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/piglins/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q193-Q195

### `OR-CIV-010A` — civilizations/umvuthana/anchor

- **Role:** Lock the umvuthana anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Umvuthana anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `COMPOSED` · `TACTICAL` — a living creator-god and his Grove, risen in the space the Empire made by killing Elves (25)
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the Villager Retaliation provider quests (Q050-Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q050-Q052, §2, Overlord ruling

### `OR-CIV-010B` — civilizations/umvuthana/state

- **Role:** Resolve the umvuthana anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-010A
- **Objective (specification):** Resolve the Umvuthana civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED MOCKING/RELISHING — a god humiliated before his worshippers (25) · DESTROYED REWARD/PLEASED · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §3); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/umvuthana/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q196-Q197

### `OR-CIV-011A` — civilizations/myrmex/anchor

- **Role:** Lock the myrmex anchor and distribute its provider quests
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** On entering a viable civilization anchor
- **Prerequisite:** —
- **Objective (specification):** Place the banner in the Myrmex anchor to lock it
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Banner placed in a viable anchor
- **Unlock popup:** `COMPOSED` · `DIRECTIVE` — a hive polity with a Queen and a measurable opinion of the Overlord
- **Completion popup:** `COMPOSED` · `ADMINISTRATIVE` — the anchor is locked and the polity becomes the Master's problem
- **Consequence:** Locks the anchor, selects and spawns the required quest-role NPCs, and distributes the provider quests (Q052, §2)
- **Persistent fact:** NONE
- **Sequence-break handling:** The anchor may be discovered before activation; discovery alone resolves nothing (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q052, §2, §12

### `OR-CIV-011B` — civilizations/myrmex/state

- **Role:** Resolve the myrmex anchor. Legal states: NEUTRAL / SUBJUGATED / DESTROYED
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Immediately on completion of the anchor quest (Q149)
- **Prerequisite:** OR-CIV-011A
- **Objective (specification):** Resolve the Myrmex civilization to NEUTRAL, SUBJUGATED or DESTROYED
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** OR outcome, per the approved terminal proofs
- **Unlock popup:** `COMPOSED` · `CHOICE` — the political arc opens; nothing is prejudged
- **Completion popup:** `PER-OUTCOME` · `PER-OUTCOME` — SUBJUGATED REWARD/PLEASED · DESTROYED MOCKING/RELISHING · NEUTRAL REACTIVE/DARKENED
- **Consequence:** Permanent terminal state for that anchor; incompatible routes lock permanently (§11.2, §12); eligible ending fact (Q022)
- **Persistent fact:** overlord_reign:civ/myrmex/<outcome> (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Prior destruction of providers may foreclose routes; no soft-lock (29)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Legal terminal states:** NEUTRAL / SUBJUGATED / DESTROYED
- **Source:** Q090-Q093, Q139-Q141, §12


## Rivalry

### `OR-RIV-001` — rivalry/dwarf_kobold/investigate

- **Role:** Dwarf/Kobold rivalry resolution
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** After both the Dwarven and Kobold anchors exist
- **Prerequisite:** OR-CIV-003A + OR-CIV-006A
- **Objective (specification):** Visit each bound polity and complete one source-compatible material/service interaction with each side, then present the paired evidence
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** Both interactions complete
- **Unlock popup:** `RELISHING` · `MOCKING` — two polities that can be played against each other
- **Completion popup:** `RELISHING` · `MOCKING` — the rivalry is exploitable
- **Consequence:** Writes RIVALRY_INVESTIGATED and exposes the four resolution routes
- **Persistent fact:** RIVALRY_INVESTIGATED
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q172

### `OR-RIV-002` — rivalry/dwarf_kobold/favor_dwarves

- **Role:** Dwarf/Kobold rivalry resolution
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available after RIVALRY_INVESTIGATED; mutually exclusive with the other three (Q173)
- **Prerequisite:** OR-RIV-001
- **Objective (specification):** Complete a Dwarven forge/material commission that disadvantages the Kobold claim
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain on dwarven_forge:dwarven_metal_ingot + dwarven_forge:rune as the commission output
- **Unlock popup:** `COMPOSED` · `CHOICE` — a side to back
- **Completion popup:** `RELISHING` · `MOCKING` — one claim ruined for the other
- **Consequence:** Writes a named rivalry fact that later civilization routes may inspect; does not set NEUTRAL/SUBJUGATED/DESTROYED directly (Q173)
- **Persistent fact:** overlord_reign:rivalry/favoured_dwarves (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q173

### `OR-RIV-003` — rivalry/dwarf_kobold/favor_kobolds

- **Role:** Dwarf/Kobold rivalry resolution
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available after RIVALRY_INVESTIGATED; mutually exclusive with the other three (Q173)
- **Prerequisite:** OR-RIV-001
- **Objective (specification):** Complete a Kobold resource-security commission that disadvantages the Dwarven claim: craft and supply Kobold-pattern iron tooling to the Den.
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_craft_stat on kobolds:kobold_iron_pickaxe (and/or the other kobold_iron_* tools)
- **Unlock popup:** `COMPOSED` · `CHOICE` — a side to back
- **Completion popup:** `RELISHING` · `MOCKING` — one claim ruined for the other
- **Consequence:** Writes a named rivalry fact that later civilization routes may inspect; does not set NEUTRAL/SUBJUGATED/DESTROYED directly (Q173)
- **Persistent fact:** overlord_reign:rivalry/favoured_kobolds (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q173; REWORDED 2026-09-18. The original named an Engineer commission, but the Engineer returns vanilla redstone and every Kobold provider role is already consumed by the civilization arc (Q181-Q184); the Prospector book in particular is claimed by Q183. The reword moves the proof to the supply side, which suits the branch: the Overlord creating dependency rather than receiving a favour. Kobold-pattern tooling is mod-specific, craftable, and claimed by nothing else.

### `OR-RIV-004` — rivalry/dwarf_kobold/truce

- **Role:** Dwarf/Kobold rivalry resolution
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available after RIVALRY_INVESTIGATED; mutually exclusive with the other three (Q173)
- **Prerequisite:** OR-RIV-001
- **Objective (specification):** Impose a forced working arrangement / truce
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:or of the two commission item turn-ins, one to each polity — forced arrangement proved by serving both
- **Unlock popup:** `COMPOSED` · `CHOICE` — the dull option
- **Completion popup:** `DARKENED` · `REACTIVE` — both sides intact; Gnarl evaluates the Master and is unimpressed
- **Consequence:** Writes a named rivalry fact that later civilization routes may inspect; does not set NEUTRAL/SUBJUGATED/DESTROYED directly (Q173)
- **Persistent fact:** overlord_reign:rivalry/forced_truce (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q173

### `OR-RIV-005` — rivalry/dwarf_kobold/exploit

- **Role:** Dwarf/Kobold rivalry resolution
- **Presenter:** Gnarl
- **Register vocabulary:** GNARL_WRITING_RULES §10 + STRICKEN (11)
- **Activation:** Available after RIVALRY_INVESTIGATED; mutually exclusive with the other three (Q173)
- **Prerequisite:** OR-RIV-001
- **Objective (specification):** Exploit or escalate the dispute for Overlord advantage
- **Order (presenter voice):** TO BE AUTHORED — presenter voice, campaign-writing pass
- **Completion condition:** questlog:item_obtain — take the disputed material from both sides without resolving the claim
- **Unlock popup:** `COMPOSED` · `CHOICE` — the dispute is worth more unresolved
- **Completion popup:** `PLEASED` · `REWARD` — turned entirely to the Master's advantage
- **Consequence:** Writes a named rivalry fact that later civilization routes may inspect; does not set NEUTRAL/SUBJUGATED/DESTROYED directly (Q173)
- **Persistent fact:** overlord_reign:rivalry/exploited (TO BE REGISTERED in NARRATIVE_FACTS.md)
- **Sequence-break handling:** Default: recognize durable prior accomplishments (Q103)
- **Reward:** NONE — V5 authors no quest rewards (Q154; locked by Q241)
- **Source:** Q173
