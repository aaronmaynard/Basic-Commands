package com.aaronmaynard.basic.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Broadcast extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("/broadcast <message>");
        } else {
            String message = StringUtils.join(args, " ");
            Bukkit.broadcastMessage("[Broadcast] " + ChatColor.translateAlternateColorCodes('&', message));
        }
        return true;
    }

}