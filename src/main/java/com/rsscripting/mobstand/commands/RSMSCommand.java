package com.rsscripting.mobstand.commands;

import com.rsscripting.mobstand.RSMobStandPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RSMSCommand implements CommandExecutor {

    private final RSMobStandPlugin plugin;

    public RSMSCommand(
            RSMobStandPlugin plugin
    ) {

        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!sender.hasPermission(
                "rsmobstand.admin"
        )) {

            sender.sendMessage(
                    ChatColor.RED
                            + "You do not have permission."
            );

            return true;

        }

        if (args.length == 0) {

            sender.sendMessage(
                    ChatColor.YELLOW
                            + "/rsms reload"
            );

            return true;

        }

        if (args[0].equalsIgnoreCase(
                "reload"
        )) {

            plugin.reloadConfig();

            plugin.getConfigManager()
                    .loadConfigs();

            sender.sendMessage(
                    ChatColor.GREEN
                            + "RS MobStand configuration reloaded."
            );

            return true;

        }

        sender.sendMessage(
                ChatColor.RED
                        + "Unknown subcommand."
        );

        return true;

    }

}