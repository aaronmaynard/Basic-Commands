package com.aaronmaynard.basic.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Feed extends BCommand {
    @Override
    public boolean onlyPlayer() {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("feed")) {
            if (args.length == 0) {
                if (hasPerm(player, cmd)) {
                    player.setFoodLevel(20);
                    player.sendMessage(ChatColor.GREEN + "You have been fed!");
                } else sender.sendMessage("No Permission");

                return true;
            } else {
                if (sender.hasPermission("basic.commands.feed.other")) {
                    Player target = Bukkit.getServer().getPlayer(args[0]);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Could not find player!");
                        return false;
                    } else {
                        target.setFoodLevel(20);
                        target.sendMessage(ChatColor.GREEN + "You have been fed!");
                        player.sendMessage(ChatColor.GREEN + target.getName() + " has been fed!");
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