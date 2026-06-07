package com.rsscripting.mobstand.utils;

import com.rsscripting.mobstand.RSMobStandPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class VersionChecker {

    private final RSMobStandPlugin plugin;

    private static String latestVersion =
            "Unknown";

    private static final String VERSION_URL =
            "https://raw.githubusercontent.com/russelburgraymond/RS-MobStand/main/version.txt";

    public VersionChecker(
            RSMobStandPlugin plugin
    ) {

        this.plugin = plugin;

    }

    public void checkVersion() {

        plugin.getServer()
                .getScheduler()
                .runTaskAsynchronously(

                        plugin,

                        () -> {

                            try {

                                /*
                                |--------------------------------------------------------------------------
                                | READ VERSION FILE
                                |--------------------------------------------------------------------------
                                */

                                URL url =
                                        new URL(
                                                VERSION_URL
                                        );

                                BufferedReader reader =
                                        new BufferedReader(
                                                new InputStreamReader(
                                                        url.openStream()
                                                )
                                        );

                                latestVersion =
                                        reader.readLine();

                                reader.close();

                                /*
                                |--------------------------------------------------------------------------
                                | CURRENT VERSION
                                |--------------------------------------------------------------------------
                                */

                                String currentVersion =
                                        plugin.getDescription()
                                                .getVersion();

                                /*
                                |--------------------------------------------------------------------------
                                | UPDATE AVAILABLE
                                |--------------------------------------------------------------------------
                                */

                                if (!currentVersion.equalsIgnoreCase(
                                        latestVersion
                                )) {

                                    plugin.getLogger().warning(
                                            "================================================="
                                    );

                                    plugin.getLogger().warning(
                                            "A new version of RS-MobStand is available!"
                                    );

                                    plugin.getLogger().warning(
                                            "Current Version: "
                                                    + currentVersion
                                    );

                                    plugin.getLogger().warning(
                                            "Latest Version: "
                                                    + latestVersion
                                    );

                                    plugin.getLogger().warning(
                                            "================================================="
                                    );

                                }

                                /*
                                |--------------------------------------------------------------------------
                                | UP TO DATE
                                |--------------------------------------------------------------------------
                                */

                                else {

                                    plugin.getLogger().info(
                                            "RS-MobStand is up to date."
                                    );

                                }

                            }

                            /*
                            |--------------------------------------------------------------------------
                            | FAILED
                            |--------------------------------------------------------------------------
                            */

                            catch (Exception exception) {

                                plugin.getLogger().warning(
                                        "Could not check for updates."
                                );

                            }

                        }

                );

    }

    public static String getLatestVersion() {

        return latestVersion;

    }

}