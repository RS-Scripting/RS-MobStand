# Troubleshooting

This guide covers the most common issues encountered when using RS-MobStand and provides steps to resolve them.

---

# Mob Stand Will Not Convert

### Symptoms

* Sneak-right-clicking an Armor Stand does nothing.
* The conversion does not occur.

### Check

Verify that:

* You are holding a Stick.
* You are sneaking.
* You have the required permission.
* The target is a normal Armor Stand.

### Solution

Ensure you are:

```text id="6rj03f"
Sneaking
+
Holding a Stick
+
Right-Clicking an Armor Stand
```

---

# Cannot Open Mob Stand Menu

### Symptoms

* Right-clicking a Mob Stand does nothing.
* The menu does not appear.

### Check

Verify that:

* The stand is already converted.
* You own the Mob Stand.
* You have permission to use Mob Stands.

### Solution

Try interacting with a Mob Stand that you own.

Administrators can bypass ownership restrictions using:

```text id="jhy0k0"
rsmobstand.admin
```

---

# "You Do Not Own This Mob Stand"

### Symptoms

You receive an ownership error message.

### Cause

The Mob Stand belongs to another player.

### Solution

* Use your own Mob Stand.
* Ask the owner for assistance.
* Have an administrator assist you.

Administrators with:

```text id="3u4r6l"
rsmobstand.admin
```

may bypass ownership restrictions.

---

# Mob Stand Is Not Processing Mobs

### Check Pause Status

Verify the Mob Stand is not paused.

Open:

```text id="50s1d7"
Main Menu
    ↓
Pause / Resume
```

and ensure processing is enabled.

---

### Check Radius

Verify the configured radius is large enough to reach the intended mobs.

Open:

```text id="kce0j6"
Main Menu
    ↓
Radius
```

and review the current value.

---

### Check Filters

Verify the mob type is enabled in the appropriate filter menu.

Review:

* Adult Filters
* Baby Filters

If a mob is disabled in filters, it will be ignored.

---

### Check Leave Alive

Verify the Leave Alive setting is not preventing additional processing.

Example:

```text id="j11jv0"
Leave Alive: 10
```

If only ten matching mobs exist, no additional processing will occur.

---

# Mobs Are Being Ignored

### Cause

The mob type may be disabled in filters.

### Solution

Open:

```text id="yzr39t"
Adult Filters
```

or

```text id="jq4z32"
Baby Filters
```

and verify the desired mob type is enabled.

---

# Gravity Is Not Working As Expected

### Cause

Gravity may be disabled.

### Solution

Open:

```text id="r5g7t1"
Main Menu
    ↓
Gravity
```

and verify the current setting.

---

# Configuration Changes Are Not Taking Effect

### Cause

The configuration may not have been reloaded.

### Solution

Administrators can use:

```text id="db0vtg"
Admin Menu
    ↓
Reload Config
```

after making configuration changes.

---

# Version Status Shows "Unable To Check"

### Cause

The plugin could not contact the update source.

Possible reasons:

* No internet access
* GitHub unavailable
* Repository is private

### Solution

This does not affect plugin operation.

RS-MobStand will continue functioning normally.

---

# Server Performance Concerns

### Recommendations

* Avoid excessively large radius values.
* Use filters to limit processing.
* Only create Mob Stands where needed.
* Review server performance regularly.

---

# Still Need Help?

Before requesting support, gather:

* Minecraft Version
* Paper Version
* RS-MobStand Version
* Screenshots (if applicable)
* Console Errors (if applicable)

Providing this information helps resolve issues more quickly.

---

# Related Pages

* Installation
* Permissions
* Admin Guide
* Main Menu
