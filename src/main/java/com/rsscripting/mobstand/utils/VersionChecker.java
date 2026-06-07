package com.rsscripting.mobstand.utils;

import com.rsscripting.mobstand.RSMobStandPlugin;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class VersionChecker {

    private final RSMobStandPlugin plugin;

    private static String latestVersion =
            "Unknown";

    private static final String VERSION_URL =
            "https://raw.githubusercontent.com/RS-Scripting/RS-MobStand/main/pom.xml";

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
| READ VERSION FROM POM
|--------------------------------------------------------------------------
*/
                                URL url =
                                        new URL(
                                                VERSION_URL
                                        );

                                DocumentBuilderFactory factory =
                                        DocumentBuilderFactory.newInstance();

                                DocumentBuilder builder =
                                        factory.newDocumentBuilder();

                                Document document =
                                        builder.parse(
                                                url.openStream()
                                        );

                                document.getDocumentElement()
                                        .normalize();

                                NodeList versionNodes =
                                        document.getElementsByTagName(
                                                "version"
                                        );

                                if (versionNodes.getLength() > 0) {

                                    latestVersion =
                                            versionNodes.item(0)
                                                    .getTextContent()
                                                    .trim();

                                }

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