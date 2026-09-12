#!/usr/bin/env python3
"""Backward-compatible entry point for civilization campaign contract validation."""
import validate_civilization_campaign_contracts as civilization
import validate_goblin_sidequest_contracts as goblin_sidequest


def collect_errors() -> list[str]:
    return civilization.collect_errors() + goblin_sidequest.collect_errors()


def main() -> int:
    errors = collect_errors()
    if errors:
        print(f"Civilization production contract validation failed with {len(errors)} error(s):")
        for error in errors:
            print(f"  * {error}")
        return 1
    print("Civilization production contracts: PASS")
    print("first-contact slices and Goblin merchant sidequest are source-backed and guarded")
    return 0


__all__ = ["collect_errors", "main"]

if __name__ == "__main__":
    raise SystemExit(main())
