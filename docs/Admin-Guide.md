# Admin Guide

The Admin Menu provides server operators with administrative controls and diagnostic information for RS-MobStand.

This menu is intended for server staff and administrators.

---

# Accessing the Admin Menu

The Admin button appears within the Main Menu for authorized users.

Open:

```text id="u56k0l"
Main Menu
    ↓
Admin
```

to access administrative functions.

---

# Administrative Features

Depending on your server configuration and plugin version, the Admin Menu may contain various management and diagnostic tools.

---

# Reload Configuration

The Reload Configuration option allows administrators to reload the plugin configuration without restarting the server.

This is useful when:

* Adjusting plugin settings
* Testing configuration changes
* Applying updates to configuration files

### When To Use Reload

After editing:

```text id="k7y2gj"
config.yml
```

you can reload the plugin to apply changes immediately.

---

# Plugin Version Information

The Admin Menu displays plugin version information.

Information may include:

* Installed Version
* Latest Available Version
* Update Status

Possible status values:

```text id="4m7j1f"
Up To Date
```

The installed version matches the latest available version.

```text id="awpjol"
Update Available
```

A newer version is available.

```text id="hrsd1g"
Unable To Check
```

The plugin could not reach the update source.

This may occur when:

* Internet access is unavailable
* The update source is inaccessible
* The repository is private

---

# Ownership Bypass

Administrators may bypass normal ownership restrictions.

This allows staff to:

* Inspect Mob Stands
* Modify configurations
* Troubleshoot player issues

without needing ownership of the Mob Stand.

---

# Troubleshooting Player Issues

When assisting players:

### Verify Ownership

Ensure the player owns the Mob Stand or has appropriate permissions.

### Verify Filters

Incorrect filter settings are a common cause of unexpected behavior.

### Verify Radius

Confirm the Mob Stand is scanning the intended area.

### Verify Pause Status

A paused Mob Stand will not process mobs.

---

# Best Practices

### Reload Sparingly

Frequent reloads are generally unnecessary.

### Test Configuration Changes

Verify changes on a test stand before making server-wide adjustments.

### Keep Backups

Maintain backups of configuration files before making major changes.

### Monitor Updates

Review plugin updates periodically for bug fixes and new features.

---

# Security

Administrative access should only be granted to trusted staff members.

Administrative permissions may allow:

* Bypassing ownership protection
* Accessing administrative controls
* Modifying player Mob Stands

---

# Related Pages

* Permissions
* Troubleshooting
* Main Menu
* Installation
