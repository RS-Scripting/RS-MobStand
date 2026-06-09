# Filters

Filters determine which mobs a Mob Stand will manage.

Using filters allows you to target specific mob types while ignoring others.

Each Mob Stand maintains separate filter lists for:

* Adult Mobs
* Baby Mobs

This provides precise control over farm behavior and mob management.

---

# Adult Filters

Adult Filters control which adult mobs may be processed.

Only enabled adult mob types are considered by the Mob Stand.

Example:

```text id="6kiz54"
Cow      ✓ Enabled
Pig      ✓ Enabled
Chicken  ✗ Disabled
```

Result:

```text id="mgj55p"
Cows and Pigs may be processed.
Chickens are ignored.
```

---

# Baby Filters

Baby Filters control which baby mobs may be processed.

This allows baby mobs to be treated differently from adults.

Example:

```text id="gqz7ir"
Baby Cow      ✗ Disabled
Adult Cow     ✓ Enabled
```

Result:

```text id="8jlwmr"
Adult cows may be processed.
Baby cows are ignored.
```

This is particularly useful for breeding operations.

---

# Why Use Filters?

Filters provide flexibility and precision.

Common uses include:

* Separating breeding stock
* Managing specific farm animals
* Protecting decorative mobs
* Controlling resource production
* Supporting specialized farm layouts

---

# Opening Filters

Open:

```text id="ybxbik"
Main Menu
    ↓
Adult Filters
```

or

```text id="7p2qcz"
Main Menu
    ↓
Baby Filters
```

to access the desired filter menu.

---

# Enabling a Filter

When a mob filter is enabled:

```text id="0rmr5g"
Enabled
```

the Mob Stand is allowed to manage that mob type.

---

# Disabling a Filter

When a mob filter is disabled:

```text id="v6knu8"
Disabled
```

the Mob Stand completely ignores that mob type.

---

# Example Farm Setup

### Breeding Farm

Adult Filters:

```text id="16mubg"
Cow ✓
Pig ✓
```

Baby Filters:

```text id="w1cgrh"
Cow ✗
Pig ✗
```

Result:

```text id="ikccfc"
Adults may be managed.
Babies are protected until they mature.
```

---

### Single Species Farm

Adult Filters:

```text id="akd7zm"
Cow ✓
Pig ✗
Chicken ✗
Sheep ✗
```

Result:

```text id="5of87d"
Only cows are managed.
```

---

# Tips

### Use Leave Alive Together With Filters

Combining filters with Leave Alive settings provides maximum control.

### Separate Different Farms

Use filters to prevent one Mob Stand from affecting unrelated mobs.

### Protect Breeding Stock

Disable baby filters when maintaining breeding populations.

---

# Related Pages

* Main Menu
* Radius Settings
* Leave Alive Settings
* Gravity Settings
* Pause and Resume
