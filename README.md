# BarkaHud

A racing-style HUD for Minecraft boats, designed for ice boat racing.
Shows speed, drift angle, acceleration, and ping — with configurable units and camera control.

## Features

- Speed bar (4 profiles: Packed, Mixed, Blue Ice, Progressive)
- Speed display in m/s, km/h, mph, or knots
- Drift angle indicator
- Acceleration (g-force) meter
- Ping icon
- Input key overlay (forward/backward/left/right)
- Compact and extended HUD modes
- Speed-based camera look-ahead
- Configurable via ModMenu (requires [YACL])

## Dependencies

| Dependency    | Required | Note          |
|---------------|----------|---------------|
| Fabric Loader | Yes      |               |
| Fabric API    | Yes      |               |
| [YACL]        | Yes      | Configuration |
| [ModMenu]     | Optional | In-game config screen |

## Supported Minecraft Versions

Currently targeting Fabric for Minecraft 26.1.

Multi-version support (1.21.x) is planned via the Stonecutter build system.

## Configuration

Config is stored in `config/barkahud.json5` (JSON5 format).

If you previously used `barkahud.properties`, your settings will be migrated automatically on first launch.

## Building

```bash
./gradlew build
```

Output jar is in `versions/<mc-version>/build/libs/`.

## License

MIT — see [LICENSE](LICENSE).

[YACL]: https://modrinth.com/mod/yacl
[ModMenu]: https://modrinth.com/mod/modmenu
