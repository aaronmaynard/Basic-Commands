package com.aaronmaynard.basic.commands;

import java.util.Locale;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Gamemode extends BCommand {

	String noticeA = ChatColor.GOLD + "Gamemode set to Adventure";
	String noticeC = ChatColor.GOLD + "Gamemode set to Creative";
	String noticeS = ChatColor.GOLD + "Gamemode set to Survival";
	String noticeSp = ChatColor.GOLD + "Gamemode set to Spectator";
	
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {

        if (args.length <= 0) {
            message(sender, "Usage: /gamemode <mode> [player]");
            return false;
        }
        int a = args.length;

        if (a > 0) {
            Player p = (Player) sender;
            String g = args[0];
            if (g.length() == 1) {
                char c = g.charAt(0);
                switch (c) {
                case '0':
                    p.setGameMode(GameMode.SURVIVAL);
                    message(sender, noticeS);
                    break;
                case '1':
                    p.setGameMode(GameMode.CREATIVE);
                    message(sender, noticeC);
                    break;
                case '2':
                    p.setGameMode(GameMode.ADVENTURE);
                    message(sender, noticeA);
                    break;
                case '3':
                    p.setGameMode(GameMode.SPECTATOR);
                    message(sender, noticeSp);
                    break;
                case 's':
                    p.setGameMode(GameMode.SURVIVAL);
                    message(sender, noticeS);
                    break;
                case 'c':
                    p.setGameMode(GameMode.CREATIVE);
                    message(sender, noticeC);
                    break;
                case 'a':
                    p.setGameMode(GameMode.ADVENTURE);
                    message(sender, noticeA);
                    break;
                }
                return true;
            }
            if (g.length() == 2) {
                p.setGameMode(GameMode.SPECTATOR);
                message(sender, noticeSp);
                return true;
            }
            p.setGameMode(GameMode.valueOf(g.toUpperCase(Locale.ENGLISH)));
        }
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (onlyPlayer()) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Player only command!");
                return false;
            }
        }
        if (!(hasPerm(sender, cmd))) {
            message(sender, ChatColor.RED + "No permission for command.");
            return false;
        }

        boolean sh = (string.length() < 5);
        if (sh) {
            return onShortCommand((Player) sender, string);
        }

        return onCommand(sender, cmd, args);
    }

    public boolean onShortCommand(Player sender, String g) {
        if (g.equalsIgnoreCase("gmc")) {
            sender.setGameMode(GameMode.CREATIVE);
            message(sender, noticeC);
        }
        if (g.equalsIgnoreCase("gms")) {
            sender.setGameMode(GameMode.SURVIVAL);
            message(sender, noticeS);
        }
        if (g.equalsIgnoreCase("gmsp")) {
            sender.setGameMode(GameMode.SPECTATOR);
            message(sender, noticeSp);
        }
        if (g.equalsIgnoreCase("gma")) {
            sender.setGameMode(GameMode.ADVENTURE);
            message(sender, noticeA);
        }
        return true;
    }
}