#!/usr/bin/env python3
"""Guard ownership of Questlog's static private event bus.

The integrated logical client and server share Questlog.EVENTS in one JVM. Server
objectives register their listeners there; client Quest instances do not. Therefore
client logout must never globally clear this bus. Server shutdown remains the owner
of final removeAllListeners cleanup after QuestManagers have been deactivated.
"""

from __future__ import annotations

import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
JAVA = ROOT / "common" / "src" / "main" / "java" / "org" / "infernalstudios" / "questlog"
CLIENT_EVENTS = JAVA / "QuestlogClientEvents.java"
SERVER_EVENTS = JAVA / "QuestlogEvents.java"


def main() -> int:
    errors: list[str] = []
    client = CLIENT_EVENTS.read_text(encoding="utf-8")
    server = SERVER_EVENTS.read_text(encoding="utf-8")

    if "Questlog.EVENTS.removeAllListeners();" in client:
        errors.append("client logout path must not globally clear Questlog.EVENTS")

    server_clear = "Questlog.EVENTS.removeAllListeners();"
    if server_clear not in server:
        errors.append("server shutdown must retain final Questlog.EVENTS cleanup")

    shutdown_pos = server.find("ServerPlayerManager.INSTANCE.shutdown();")
    clear_pos = server.find(server_clear)
    if shutdown_pos == -1 or clear_pos == -1 or clear_pos < shutdown_pos:
        errors.append("server event-bus cleanup must occur after QuestManager shutdown")

    if errors:
        print("Event-bus lifecycle validation failed:", file=sys.stderr)
        for error in errors:
            print(f" - {error}", file=sys.stderr)
        return 1

    print("Questlog event-bus lifecycle ownership is intact.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
