#!/usr/bin/env python3
"""Minimal Minecraft RCON client used only by repository runtime-smoke workflows."""
from __future__ import annotations

import argparse
import socket
import struct
import sys

AUTH_TYPE = 3
COMMAND_TYPE = 2


def recv_exact(sock: socket.socket, size: int) -> bytes:
    data = bytearray()
    while len(data) < size:
        chunk = sock.recv(size - len(data))
        if not chunk:
            raise EOFError("RCON connection closed before a complete packet was received")
        data.extend(chunk)
    return bytes(data)


def send_packet(sock: socket.socket, request_id: int, packet_type: int, body: str) -> None:
    payload = struct.pack("<ii", request_id, packet_type) + body.encode("utf-8") + b"\x00\x00"
    sock.sendall(struct.pack("<i", len(payload)) + payload)


def receive_packet(sock: socket.socket) -> tuple[int, int, str]:
    length = struct.unpack("<i", recv_exact(sock, 4))[0]
    if length < 10 or length > 4 * 1024 * 1024:
        raise RuntimeError(f"invalid RCON packet length: {length}")
    payload = recv_exact(sock, length)
    request_id, packet_type = struct.unpack("<ii", payload[:8])
    if payload[-2:] != b"\x00\x00":
        raise RuntimeError("invalid RCON packet terminator")
    return request_id, packet_type, payload[8:-2].decode("utf-8", errors="replace")


def execute(host: str, port: int, password: str, command: str) -> str:
    with socket.create_connection((host, port), timeout=10.0) as sock:
        sock.settimeout(10.0)

        auth_id = 0x51A7
        send_packet(sock, auth_id, AUTH_TYPE, password)
        response_id, _, _ = receive_packet(sock)
        if response_id == -1:
            raise RuntimeError("RCON authentication failed")
        if response_id != auth_id:
            raise RuntimeError(f"unexpected RCON authentication response id: {response_id}")

        command_id = 0x51A8
        send_packet(sock, command_id, COMMAND_TYPE, command)
        response_id, _, response = receive_packet(sock)
        if response_id != command_id:
            raise RuntimeError(f"unexpected RCON command response id: {response_id}")
        return response


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("command")
    parser.add_argument("--host", default="127.0.0.1")
    parser.add_argument("--port", type=int, default=25575)
    parser.add_argument("--password", required=True)
    args = parser.parse_args()

    try:
        print(execute(args.host, args.port, args.password, args.command))
    except (OSError, EOFError, RuntimeError) as exc:
        print(f"RCON error: {exc}", file=sys.stderr)
        return 1
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
