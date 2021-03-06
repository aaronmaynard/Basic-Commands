package com.aaronmaynard.basic.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Weather extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args) {
        if (!(sender instanceof Player)) {
            message(sender, "Player only command.");
        }
        if (!(hasPerm(sender, cmd))) {
            message(sender, ChatColor.RED + "No permission for command.");
            return false;
        }

        Player p = (Player) sender;
        if (l.equalsIgnoreCase("clear")) {
            p.getWorld().setStorm(false);
            message(sender, "Set weather to CLEAR in " + p.getWorld().getName());
            return true;
        }
        if (l.equalsIgnoreCase("rain")) {
            p.getWorld().setStorm(true);
            p.getWorld().setThundering(false);
            message(sender, "Set weather to RAIN in " + p.getWorld().getName());
            return true;
        }
        if (l.equalsIgnoreCase("storm")) {
            p.getWorld().setStorm(true);
            p.getWorld().setThundering(true);
            message(sender, "Set weather to STORM in " + p.getWorld().getName());
            return true;
        }

        if (args.length < 1) {
            message(sender, ChatColor.DARK_RED + "Usage: /weather <sun|rain|thunder> [world]");
            return true;
        }
        World w = p.getWorld();
        if (args.length > 1) {
            w = Bukkit.getWorld(args[1]);
        }

        if (args[0].equalsIgnoreCase("clear")) {
            w.setStorm(false);
            message(sender, "Set weather to CLEAR in " + w.getName());
            return true;
        }
        if (args[0].equalsIgnoreCase("rain")) {
            w.setStorm(true);
            w.setThundering(false);
            message(sender, "Set weather to RAIN in " + w.getName());
            return true;
        }
        if (args[0].equalsIgnoreCase("storm")) {
            w.setStorm(true);
            w.setThundering(true);
            message(sender, "Set weather to STORM in " + w.getName());
            return true;
        }
        return true;
    }
}