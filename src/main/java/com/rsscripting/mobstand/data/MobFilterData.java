package com.rsscripting.mobstand.data;

import org.bukkit.Material;

public class MobFilterData {

    private final Material icon;

    private final boolean enabledByDefault;

    private final boolean killAll;

    public MobFilterData(
            Material icon,
            boolean enabledByDefault,
            boolean killAll
    ) {

        this.icon = icon;

        this.enabledByDefault = enabledByDefault;

        this.killAll = killAll;

    }

    public Material getIcon() {

        return icon;

    }

    public boolean isKillAll() {

        return killAll;

    }

    public boolean isEnabledByDefault() {

        return enabledByDefault;

    }

}