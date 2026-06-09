# Permissions

RS-MobStand uses permissions to control who can create, manage, and administer Mob Stands.

Server owners can use these permissions to grant access to players, moderators, and administrators.

---

# Player Permissions

## Use RS-MobStand

```text
rsmobstand.use
```

Allows a player to:

* Convert Armor Stands into Mob Stands
* Open Mob Stand menus
* Configure owned Mob Stands
* Use normal plugin functionality

Recommended for:

```text
All Players
```

---

# Administrative Permissions

## Admin Access

```text
rsmobstand.admin
```

Allows a player to:

* Access the Admin Menu
* Bypass ownership restrictions
* Modify Mob Stands owned by other players
* Assist with troubleshooting

Recommended for:

```text
Administrators
Moderators
Trusted Staff
```

---

# Ownership Protection

Even with normal use permissions, players may only modify Mob Stands they own.

Ownership is automatically assigned when a player converts an Armor Stand into a Mob Stand.

Without administrative permissions:

```text
Player A
    └─ Can modify Player A's Mob Stands

Player B
    └─ Cannot modify Player A's Mob Stands
```

With:

```text
rsmobstand.admin
```

ownership restrictions may be bypassed.

---

# Recommended Permission Setup

## Survival Servers

Players:

```text
rsmobstand.use
```

Administrators:

```text
rsmobstand.use
rsmobstand.admin
```

---

## Staff Teams

Moderators:

```text
rsmobstand.use
rsmobstand.admin
```

Administrators:

```text
rsmobstand.use
rsmobstand.admin
```

---

# Permission Plugins

RS-MobStand works with common permission systems including:

* LuckPerms
* PermissionsEx
* GroupManager
* Other Bukkit-compatible permission managers

---

# Troubleshooting

### Players Cannot Create Mob Stands

Verify:

```text
rsmobstand.use
```

has been granted.

---

### Staff Cannot Access Admin Features

Verify:

```text
rsmobstand.admin
```

has been granted.

---

### Player Cannot Modify Their Mob Stand

Verify:

* The player owns the Mob Stand.
* The player has the required permissions.
* The Mob Stand was successfully converted.

---

# Related Pages

* Admin Guide
* Creating Your First Mob Stand
* Troubleshooting
