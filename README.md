# Simple Combat Log (SCL)

Simple Combat Log (SCL) is a Minecraft Spigot plugin that prevents players from escaping combat unfairly. When a player enters combat, they are considered "in combat" for a set duration. If they try to log out or use commands to avoid fighting, the plugin can detect it. Authorized players can toggle the combat log system on or off using the `/combatlog` command, and only those listed in the `allowedPlayers` array in `config.yml` have access to this command.

## Features
- **Combat Log Toggle:** Players listed in the `allowedPlayers` array in `config.yml` can use `/combatlog` to enable or disable combat logging.
- **Easy Permission Control:** Only players added to `allowedPlayers` can use the command.
- **Simple and Lightweight:** No extra features, just combat log management.

## Installation
1. Download the `SCL.jar` file.
2. Place it in your Minecraft server's `plugins` folder.
3. Restart the server or use `/reload`.
4. Edit `config.yml` to add the players who should have access to `/combatlog`.

## Usage
- `/combatlog` - Toggles combat logging on or off (only allowed players can use this command).

## Config Example
```yaml
# List of players allowed to use /combatlog
allowedPlayers:
  - Player1
  - Player2
  - Player3

# Optional: default combat log setting
combatLogEnabled: true
