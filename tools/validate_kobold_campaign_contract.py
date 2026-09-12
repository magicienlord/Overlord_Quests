#!/usr/bin/env python3
"""Backward-compatible entry point for civilization campaign contract validation."""
from validate_civilization_campaign_contracts import collect_errors, main

__all__ = ["collect_errors", "main"]

if __name__ == "__main__":
    raise SystemExit(main())
