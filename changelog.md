# Unreleased

- Fix command description
- Fix default koth create scoreboard. A real configuration will now be created
- Fixed Discord Webhook. The configuration loading and sending are now correct
- Fixed checking distance between different worlds

# 3.1.0

- Add ``enableEverySecondsCooldownMessage``, Periodically displays a message when the koth is launched
- Fixed the koth default type when creating, the type will now be CAPTURE
- Add ``%captureProgress%`` and `%capturePercent%` placeholder 

# 3.0.9

- Update HuskTown API
- Fix scoreboard title with PlaceholderApi
- Minor fixes

# 3.0.8

- Fix nms and 1.21

# 3.0.7

- Add Ultimate Clan support
- Fix errors with Placeholders

# 3.0.6

- Some fixs
- Fix /koth stop auto-completion
- Fix ``noKoth`` configuration

# 3.0.5

- Add Gang+ support

# 3.0.4

- Add random command on koth win
- Fix error with discord webhook on stop
- Fix problem with placeholder on koth spawn

# 3.0.3

- Fix NMS
- Fix ``/koth stop``
- Create placeholder: ``%zkoth_capture_progress_bar%``
- Create placeholder: ``%zkoth_progress_bar_score_points_<position>%``
- Create placeholder: ``%zkoth_progress_bar_score%``
- Change auto-completion for /koth stop

# 3.0.2

- Create placeholder: ``%zkoth_capture_max_seconds%``
- Create placeholder: ``%zkoth_capture_max_formats%``
- Create placeholder: ``%zkoth_score``
- Create placeholder: ``%zkoth_score_player_<position>``
- Create placeholder: ``%zkoth_score_points_<position>``
- Create placeholder: ``%zkoth_score_team_id_<position>``
- Create placeholder: ``%zkoth_score_team_name_<position>``
- Create placeholder: ``%zkoth_score_team_leader_<position>``
- Create koth option: ``blacklistTeamId``
- Change TeamKoth interface to use OfflinePlayer instance of Player

# 3.0.1

- Add SaberFaction and FactionUUID support
- Add command ``/koth addcommand <koth name> <start/win> <command>``
- Add comment in file koth-example.yml
- Add placeholders:
1. ``%zkoth_team_id%`` - Current koth capture player team id
2. ``%zkoth_team_leader%`` - Current koth capture player team leader name
3. ``%zkoth_active_<koth name>%`` - Lets you know if a koth is active
4. ``%zkoth_cooldown_<koth name>%`` - Lets you know if a koth is in cooldown
5. ``%zkoth_start_<koth name>%`` - Lets you know if a koth is start
- Fix scoreboard and hologram update

# 3.0.0

New version completely recoded