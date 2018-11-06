package com.aaronmaynard.basic.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aaronmaynard.basic.User;

public class Nick extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length < 1) {
            message(sender, ChatColor.DARK_RED + "Usage: /nick <nickname>");
            return false;
        }

        User user = User.getByName(sender.getName());
        if (args[0].equalsIgnoreCase("off")) {
        	user.setNick(sender.getName());
        	((Player) sender).setDisplayName(sender.getName());
        	message(sender, "Nickname reset to: " + sender.getName());
        	return true;
        }
        String nick = ChatColor.translateAlternateColorCodes('&', args[0].replaceAll("[SPACECHAR]", " "));
        user.setNick(nick);
        ((Player) sender).setDisplayName(nick);
        message(sender, "Nickname set to: " + nick);
        return true;
    }

}