#!/usr/bin/env python3
"""Validate the repository Gnarl popup PNG without third-party dependencies.

This check is intentionally mechanical. It verifies the texture contract needed by
Foundation B, not whether the portrait matches the approved Gnarl design. Visual
acceptance still requires direct review.
"""

from __future__ import annotations

import hashlib
import math
import struct
import sys
import zlib
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
ASSET = ROOT / "common" / "src" / "main" / "resources" / "assets" / "questlog" / "textures" / "gui" / "overlord" / "gnarl_popup.png"
PNG_SIGNATURE = b"\x89PNG\r\n\x1a\n"
EXPECTED_WIDTH = 320
EXPECTED_HEIGHT = 320
MAX_BYTES = 512 * 1024


def scanline_bytes(width: int, bit_depth: int, color_type: int) -> int | None:
    channels = {0: 1, 2: 3, 3: 1, 4: 2, 6: 4}.get(color_type)
    if channels is None:
        return None
    return 1 + math.ceil(width * channels * bit_depth / 8)


def add_partial_idat_diagnostics(
    compressed: bytes,
    ihdr: tuple[int, int, int, int, int, int, int] | None,
    diagnostics: list[str],
) -> None:
    if not compressed:
        return

    try:
        inflater = zlib.decompressobj()
        decompressed = inflater.decompress(compressed)
        diagnostics.append(
            f"partial wrapped-zlib diagnostic: {len(compressed)} compressed bytes yield {len(decompressed)} decompressed bytes; zlib_eof={inflater.eof}"
        )
        report_rows(decompressed, ihdr, diagnostics, "wrapped-zlib")
        return
    except zlib.error as exc:
        diagnostics.append(f"partial wrapped-zlib diagnostic failed: {exc}")

    # If the PNG was truncated after valid deflate data but before the Adler-32
    # trailer, normal zlib decoding can reject the checksum before exposing how
    # much image data survived. Skip the two-byte zlib header and inspect the raw
    # deflate stream only for diagnostics. This never turns a corrupt PNG into a
    # passing asset.
    if len(compressed) > 2:
        try:
            inflater = zlib.decompressobj(-15)
            decompressed = inflater.decompress(compressed[2:])
            diagnostics.append(
                f"partial raw-deflate diagnostic: {len(compressed) - 2} bytes yield {len(decompressed)} decompressed bytes; deflate_eof={inflater.eof}"
            )
            report_rows(decompressed, ihdr, diagnostics, "raw-deflate")
        except zlib.error as exc:
            diagnostics.append(f"partial raw-deflate diagnostic failed: {exc}")


def report_rows(
    decompressed: bytes,
    ihdr: tuple[int, int, int, int, int, int, int] | None,
    diagnostics: list[str],
    label: str,
) -> None:
    if ihdr is None:
        return
    width, height, bit_depth, color_type, *_ = ihdr
    row_bytes = scanline_bytes(width, bit_depth, color_type)
    if row_bytes:
        diagnostics.append(
            f"{label} scanline diagnostic: row_bytes={row_bytes}, complete_rows={len(decompressed) // row_bytes}/{height}, expected_raw_bytes={row_bytes * height}"
        )


def main() -> int:
    errors: list[str] = []
    diagnostics: list[str] = []

    try:
        raw = ASSET.read_bytes()
    except OSError as exc:
        print(f"GNARL POPUP ASSET CHECK FAILED: cannot read {ASSET}: {exc}", file=sys.stderr)
        return 1

    if len(raw) > MAX_BYTES:
        errors.append(f"file is {len(raw)} bytes, above the {MAX_BYTES}-byte repository limit")

    if not raw.startswith(PNG_SIGNATURE):
        errors.append("file does not have a valid PNG signature")
        return finish(raw, None, None, None, False, errors, diagnostics=diagnostics)

    pos = len(PNG_SIGNATURE)
    ihdr: tuple[int, int, int, int, int, int, int] | None = None
    has_trns = False
    trns_has_transparency = False
    saw_iend = False
    chunk_count = 0
    idat_payload = bytearray()

    while pos + 12 <= len(raw):
        length = struct.unpack(">I", raw[pos:pos + 4])[0]
        chunk_type = raw[pos + 4:pos + 8]
        data_start = pos + 8
        data_end = data_start + length
        crc_end = data_end + 4

        if crc_end > len(raw):
            available = max(0, len(raw) - data_start)
            errors.append(
                f"truncated PNG chunk {chunk_type!r}: declared {length} data bytes but only {available} bytes remain before EOF"
            )
            if chunk_type == b"IDAT" and available:
                idat_payload.extend(raw[data_start:])
                add_partial_idat_diagnostics(bytes(idat_payload), ihdr, diagnostics)
            break

        data = raw[data_start:data_end]
        expected_crc = struct.unpack(">I", raw[data_end:crc_end])[0]
        actual_crc = zlib.crc32(chunk_type)
        actual_crc = zlib.crc32(data, actual_crc) & 0xFFFFFFFF
        if expected_crc != actual_crc:
            errors.append(f"CRC mismatch in chunk {chunk_type.decode('latin1', errors='replace')}")

        chunk_count += 1

        if chunk_type == b"IHDR":
            if length != 13:
                errors.append(f"IHDR length is {length}, expected 13")
            else:
                ihdr = struct.unpack(">IIBBBBB", data)
        elif chunk_type == b"tRNS":
            has_trns = True
            trns_has_transparency = any(alpha < 255 for alpha in data)
        elif chunk_type == b"IDAT":
            idat_payload.extend(data)
        elif chunk_type == b"IEND":
            saw_iend = True
            pos = crc_end
            break

        pos = crc_end

    if ihdr is None:
        errors.append("PNG has no valid IHDR chunk")
        return finish(raw, None, None, None, False, errors, diagnostics=diagnostics)

    width, height, bit_depth, color_type, compression, filter_method, interlace = ihdr

    if width != EXPECTED_WIDTH or height != EXPECTED_HEIGHT:
        errors.append(
            f"dimensions are {width}x{height}, expected {EXPECTED_WIDTH}x{EXPECTED_HEIGHT}"
        )

    if compression != 0:
        errors.append(f"unsupported PNG compression method {compression}")
    if filter_method != 0:
        errors.append(f"unsupported PNG filter method {filter_method}")
    if interlace not in (0, 1):
        errors.append(f"invalid PNG interlace method {interlace}")
    if not saw_iend:
        errors.append("PNG has no IEND chunk")

    # PNG color types 4 and 6 contain an explicit alpha channel. Palette or
    # grayscale/RGB images can instead carry transparency through tRNS.
    alpha_capable = color_type in (4, 6) or has_trns
    if not alpha_capable:
        errors.append(
            f"PNG color type {color_type} has no alpha channel or tRNS transparency"
        )
    elif has_trns and color_type not in (4, 6) and not trns_has_transparency:
        errors.append("tRNS chunk exists but contains no transparent alpha value")

    if saw_iend and idat_payload:
        try:
            decompressed = zlib.decompress(bytes(idat_payload))
            row_bytes = scanline_bytes(width, bit_depth, color_type)
            if interlace == 0 and row_bytes is not None:
                expected = row_bytes * height
                if len(decompressed) != expected:
                    errors.append(
                        f"decompressed pixel stream is {len(decompressed)} bytes, expected {expected} for a non-interlaced image"
                    )
        except zlib.error as exc:
            errors.append(f"IDAT zlib stream is invalid: {exc}")

    return finish(
        raw,
        width,
        height,
        bit_depth,
        alpha_capable,
        errors,
        diagnostics=diagnostics,
        color_type=color_type,
        chunk_count=chunk_count,
    )


def finish(
    raw: bytes,
    width: int | None,
    height: int | None,
    bit_depth: int | None,
    alpha_capable: bool,
    errors: list[str],
    *,
    diagnostics: list[str],
    color_type: int | None = None,
    chunk_count: int | None = None,
) -> int:
    digest = hashlib.sha256(raw).hexdigest()

    print("GNARL POPUP ASSET REPORT")
    print("status: mechanical PNG contract only; visual approval is separate")
    print(f"path: {ASSET.relative_to(ROOT)}")
    print(f"bytes: {len(raw)}")
    print(f"sha256: {digest}")
    if width is not None and height is not None:
        print(f"dimensions: {width} x {height}")
    if bit_depth is not None:
        print(f"bit depth: {bit_depth}")
    if color_type is not None:
        print(f"PNG color type: {color_type}")
    print(f"alpha-capable: {'yes' if alpha_capable else 'no'}")
    if chunk_count is not None:
        print(f"complete chunks parsed: {chunk_count}")
    for diagnostic in diagnostics:
        print(diagnostic)

    if errors:
        print("GNARL POPUP ASSET CHECK FAILED:", file=sys.stderr)
        for error in errors:
            print(f"  * {error}", file=sys.stderr)
        return 1

    print("GNARL POPUP ASSET CHECK: PASS")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
