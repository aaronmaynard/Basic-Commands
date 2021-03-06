package com.aaronmaynard.basic;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.aaronmaynard.basic.Basic;

public class User {
    private Player base;
    private File folder;
    private File file;

    public String lastAccountName;
    public BigDecimal money = new BigDecimal(100);
    public int numberOfHomes = 0;
    public String nick = "_null_";

    public ArrayList<String> homes = new ArrayList<>();

    public static FileConfiguration user = new YamlConfiguration();

    public User(Player base) {
        this.base = base;
        this.folder = new File(Basic.get().getDataFolder(), "userdata");
        this.file = new File(folder, base.getUniqueId().toString() + ".yml");
        this.lastAccountName = base.getName();
        folder.mkdir();

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Unable to create file: " + folder.getAbsolutePath());
                e.printStackTrace();
            }
            user.set("lastAccountName", base.getName());
            user.set("money", BigDecimal.valueOf(100.0)); // Default
            user.set("numberOfHomes", 0);
            save();
        }

        try {
            user.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        if (file.exists()) {
            lastAccountName = user.getString("lastAccountName");
            try {
                money = new BigDecimal((double) user.get("money"));
            } catch (Exception e) {
                money = BigDecimal.valueOf(Double.valueOf((int) user.get("money")));
            }
            numberOfHomes = user.getInt("numberOfHomes", 0);
            nick = user.getString("nick");
        }
    }


    public Location getHome(String home) {
        if (user.getConfigurationSection("homes." + home) == null) {
            return null;
        }
        World w = Bukkit.getServer().getWorld(user.getString("homes." + home + ".world"));
        double x = user.getDouble("homes." + home + ".x");
        double y = user.getDouble("homes." + home + ".y");
        double z = user.getDouble("homes." + home + ".z");
        return new Location(w, x, y, z);
    }

    private void save() {
        try {
            user.save(file);
            lastAccountName = user.getString("lastAccountName");
            try {
                money = new BigDecimal((double) user.get("money"));
            } catch (Exception e) {
                money = BigDecimal.valueOf(Double.valueOf((int) user.get("money")));
            }
            numberOfHomes = user.getInt("numberOfHomes", 0);
            nick = user.getString("nick");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to write to file: " + folder.getAbsolutePath());
        }
    }

    public BigDecimal getMoney() {
        return money;
    }

    public boolean isAuthorized(String string) {
        return base.hasPermission(string);
    }

    public void setMoney(BigDecimal balance) {
        user.set("money", balance.doubleValue());
        save();
    }

    public void setNick(String n) {
        user.set("nick", n);
        save();
    }

    @SuppressWarnings("deprecation")
    public static User getByName(String name) {
        return new User(Bukkit.getPlayerExact(name));
    }

    public void setHome(String home, Location l) {
        user.set("homes." + home + ".world", l.getWorld().getName());
        user.set("homes." + home + ".x", l.getX());
        user.set("homes." + home + ".y", l.getY());
        user.set("homes." + home + ".z", l.getZ());
        save();
    }

    public void delHome(String home) {
        user.set("homes." + home + ".world", null);
        user.set("homes." + home + ".x", null);
        user.set("homes." + home + ".y", null);
        user.set("homes." + home + ".z", null);
        user.set("homes." + home, null);
        save();
    }
}