package com.aaronmaynard.basic.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.aaronmaynard.basic.User;

public class SetHome extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Usage: /sethome <name>");
            return false;
        }
        
        String home = args[0];
        Player p = (Player) sender;
        
        if (User.getByName(p.getName()).homes.size() <= 3){
        	User.getByName(p.getName()).setHome(home, p.getLocation());
        	sender.sendMessage(ChatColor.AQUA + "Home " + args[0] + "set!");
        	return true;
        } else {
        	sender.sendMessage(ChatColor.AQUA + "You cannot set anymore homes! Try deleting one first...");
        	return true;
        }
    }

    @Override
    public boolean onlyPlayer() {
        return true;
    }
}