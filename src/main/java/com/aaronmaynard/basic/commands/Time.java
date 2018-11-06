package com.aaronmaynard.basic.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Time extends BCommand {

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
        if (l.equalsIgnoreCase("morning")) {
            p.getWorld().setTime(23000);
            message(sender, "Set time to morning in " + p.getWorld().getName());
            return true;
        }

        if (l.equalsIgnoreCase("day")) {
            p.getWorld().setTime(5000);
            message(sender, "Set time to DAY in " + p.getWorld().getName());
            return true;
        }

        if (l.equalsIgnoreCase("evening")) {
            p.getWorld().setTime(10000);
            message(sender, "Set time to EVENING in " + p.getWorld().getName());
            return true;
        }
        
        if (l.equalsIgnoreCase("night")) {
            p.getWorld().setTime(13000);
            message(sender, "Set time to NIGHT in " + p.getWorld().getName());
            return true;
        }

        if (args.length < 1) {
            message(sender, ChatColor.GOLD + "The time is " + p.getWorld().getTime() + " ticks in " + p.getWorld().getName());
            return true;
        }
        World w = p.getWorld();
        if (args.length > 1) {
            w = Bukkit.getWorld(args[1]);
        }

        if (args[0].equalsIgnoreCase("morning")) {
        	p.getWorld().setTime(23000);
            message(sender, "Set time to MORNING in " + w.getName());
            return true;
        }

        if (args[0].equalsIgnoreCase("day")) {
        	p.getWorld().setTime(5000);
            message(sender, "Set time to DAY in " + w.getName());
            return true;
        }

        if (args[0].equalsIgnoreCase("evening")) {
        	p.getWorld().setTime(12000);
            message(sender, "Set time to EVENING in " + w.getName());
            return true;
        }

        if (args[0].equalsIgnoreCase("night")) {
        	p.getWorld().setTime(15000);
            message(sender, "Set time to NIGHT in " + w.getName());
            return true;
        }
        
        else {
        	message(sender, ChatColor.DARK_RED + "Usage: /time [morning|day|evening|night]");
        }

        return true;
    }
}