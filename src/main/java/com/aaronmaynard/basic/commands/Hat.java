package com.aaronmaynard.basic.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Hat extends BCommand {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String[] args) {
        if (hasPerm(sender, cmd)) {
            Player player = (Player) sender;
            if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                ItemStack itemHand = player.getInventory().getItemInMainHand();
                PlayerInventory inventory = player.getInventory();
                ItemStack itemHead = inventory.getHelmet();
                inventory.removeItem(new ItemStack[] { itemHand });
                inventory.setHelmet(itemHand);
                inventory.setItemInMainHand(itemHead);
                player.sendMessage(ChatColor.YELLOW + "Item successfuly put on your head.");
                return true;
            }
            player.sendMessage(ChatColor.YELLOW + "You must have something in your hand to put on your head!");
            return true;
        }
        message(sender, ChatColor.DARK_RED + "Missing permission: basic.commands.hat");
        return true;
    }

    @Override
    public boolean onlyPlayer() {
        return true;
    }
}