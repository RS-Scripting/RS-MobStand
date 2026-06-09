# Leave Alive Settings

The Leave Alive setting determines how many matching mobs should remain alive within a Mob Stand's operating area.

This allows players to automate mob management while still maintaining a breeding population, display mobs, or farm infrastructure.

---

# What Leave Alive Does

When a Mob Stand processes matching mobs, it will stop once the configured number of mobs remains.

Example:

```text
Leave Alive: 5
```

Current matching mobs:

```text
15 Cows
```

Result:

```text
10 Cows Processed
5 Cows Remain Alive
```

---

# Why Use Leave Alive?

Leave Alive gives you greater control over your farms.

Common uses include:

* Maintaining breeding stock
* Preventing complete farm depletion
* Keeping decorative mobs alive
* Controlling resource production rates

---

# Setting Leave Alive

Open:

```text
Main Menu
    ↓
Leave Alive
```

Adjust the value to the desired amount.

The Mob Stand will automatically respect this limit during operation.

---

# Example Configurations

## Leave Alive: 0

```text
All matching mobs may be processed.
```

Best for:

* Resource-only farms
* Maximum production

---

## Leave Alive: 2

```text
Always leave two matching mobs alive.
```

Best for:

* Basic breeding farms
* Simple population maintenance

---

## Leave Alive: 10

```text
Maintain a larger breeding population.
```

Best for:

* Large animal farms
* Decorative enclosures
* High-growth breeding systems

---

# Tips

### Breeding Farms

A Leave Alive value of 2 or more is recommended.

### Decorative Areas

Use a higher Leave Alive value to maintain the desired appearance.

### Resource Farms

Use a lower value if maximum production is the goal.

---

# Important Notes

Leave Alive only applies to mobs that match the current filter settings.

If a mob is not included in the filters, it is ignored entirely by the Mob Stand.

---

# Related Pages

* Main Menu
* Radius Settings
* Filters
* Pause and Resume
