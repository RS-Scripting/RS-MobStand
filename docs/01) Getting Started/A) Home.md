# RS-MobStand

## Automated Mob Slaughtering for Paper Servers

**RS-MobStand** converts standard Minecraft Armor Stands into configurable automated Mob Stands.
A Mob Stand manages nearby mobs through an easy-to-use GUI, allowing you to control which mobs are affected, how far the stand scans, how many mobs remain alive, and whether the stand is active.

---

## Getting Started

The basic workflow is simple:

1. Place a standard **Armor Stand**.
2. Convert it into an **RS-MobStand**.
3. Configure the stand through its GUI.
4. Select the adult and/or baby mobs the stand should manage.
5. Set the desired scan radius.
6. Resume the stand when you are ready.
7. Let RS-MobStand handle the configured mobs automatically.

> **New Mob Stands start paused.** 
> This gives you time to configure the stand before it begins managing mobs.

---

## How Mob Stands Work
Each Mob Stand operates independently using its own configuration.

### Mob Filters
Adult and Baby filters work as **whitelists**.
A mob must be configured in the appropriate filter before the Mob Stand will affect it.
- Adult mobs use the **Adult Filters** menu.
- Baby mobs use the **Baby Filters** menu.
- Unconfigured mobs are ignored.

### Scan Radius
The Mob Stand uses a configurable horizontal scan radius.
The vertical scan area is limited to:
- **2 blocks above** the stand's base level
- **2 blocks below** the stand's base level

This keeps the operating area predictable and helps prevent the stand from affecting mobs outside the intended area.

### Leave Alive
The **Leave Alive** setting allows you to keep a configurable number of mobs alive while the stand continues its automated management.
Custom named mobs are completely ignored and do not count toward the "Leave Alive" setting.

### Pause / Resume
Mob Stands can be paused at any time.
A paused stand remains in place and retains its configuration, but does not perform its automated mob management until resumed.

---

## Armor Stand Customization
RS-MobStand also provides several visual customization options for the underlying Armor Stand.

### Display Name
The Mob Stand has an in-world display name that can be shown or hidden from the main menu.

### Arm Poses
If the Armor Stand has arms enabled, the **Arm Poses** menu allows the two arms to be configured independently.
Each hand has five available poses:

| Pose | Description |
|---|---|
| Down | Arm resting downward |
| 1/4 Down | Arm raised slightly from the down position |
| Straight Out | Arm extended horizontally |
| 1/4 Up | Arm raised toward the upward position |
| Straight Up | Arm raised vertically |

The **Main Hand** and **Off Hand** can each use a different pose.

---

## View Radius
The **View Radius** option provides a temporary in-world visualization of the configured scan area.
When activated, a particle outline shows the Mob Stand's operating area for a limited time.
The visualization is only shown to the player who activates it.

---

## Converting Back
RS-MobStand follows the RSScripting conversion standard.
A Mob Stand can always be converted back into a normal vanilla Armor Stand.
Converting back removes the RS-MobStand functionality and returns the object to a standard Armor Stand.

---

## Main Menu
The Mob Stand main menu provides access to the stand's configuration:

| Option | Purpose                                                                                                                                                                                                                        |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Convert Back | Return the Mob Stand to a normal Armor Stand                                                                                                                                                                                   |
| Pause / Resume | Stop or start automated management                                                                                                                                                                                             |
| Scan Radius | Configure the horizontal operating radius                                                                                                                                                                                      |
| Gravity | Configure Armor Stand gravity. This determines wether the stand will fall or stay floating if the block directly below it is broken.                                                                                           |
| Leave Alive | Configure how many mobs should be left alive during a slaughter session. NOTE: Custom named mobs are completely ignored.  Currently Armorstand does not distinguish among variants such as red wool sheep and blue wool sheep. |
| Adult Filters | Configure adult mob whitelist                                                                                                                                                                                                  |
| Baby Filters | Configure baby mob whitelist                                                                                                                                                                                                   |
| Display Name | Show or hide the Mob Stand name                                                                                                                                                                                                |
| Arm Poses | Configure Main Hand and Off Hand poses                                                                                                                                                                                         |
| View Radius | Display the scan area in-world                                                                                                                                                                                                 |

---

## Administration
Operators have access to additional administrative controls through the Mob Stand menu.
The administrative menu provides plugin-level management tools, including configuration reload and plugin version information.

---

## Support
If you encounter a problem or have a feature request, open an issue in the RS-MobStand repository.