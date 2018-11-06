package com.aaronmaynard.basic.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Weaken extends BCommand {
    @Override
    public boolean onlyPlayer() {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("weaken")) {
            if (args.length == 0) {
                if (hasPerm(player, cmd)) {
                    player.setHealth(1);
                    player.setFoodLevel(1);
                    player.sendMessage(ChatColor.GREEN + "You have been weakened!");
                    return true;
                } else {
                    sender.sendMessage("No Permission");
                    return false;
                }
            } else {
                if (sender.hasPermission("basic.commands.weaken.other")) {
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Could not find player!");
                        return false;
                    } else {
                        target.setHealth(1);
                        target.setFoodLevel(1);
                        target.sendMessage(ChatColor.GREEN + "You have been weakened!");
                        player.sendMessage(ChatColor.GREEN + target.getName() + " has been weakened!");
                        return true;
                    }
                } else {
                    sender.sendMessage("No Permission");
                    return false;
                }
            }
        }

        return false;
    }
}