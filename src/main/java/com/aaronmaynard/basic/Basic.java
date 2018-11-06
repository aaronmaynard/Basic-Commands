package com.aaronmaynard.basic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.aaronmaynard.basic.commands.Afk;
import com.aaronmaynard.basic.commands.BCommand;
import com.aaronmaynard.basic.commands.Balance;
import com.aaronmaynard.basic.commands.Broadcast;
import com.aaronmaynard.basic.commands.DelHome;
import com.aaronmaynard.basic.commands.DelWarp;
import com.aaronmaynard.basic.commands.Fly;
import com.aaronmaynard.basic.commands.Gamemode;
import com.aaronmaynard.basic.commands.Hat;
import com.aaronmaynard.basic.commands.Heal;
import com.aaronmaynard.basic.commands.Home;
import com.aaronmaynard.basic.commands.Nick;
import com.aaronmaynard.basic.commands.Pay;
import com.aaronmaynard.basic.commands.Pm;
import com.aaronmaynard.basic.commands.SetHome;
import com.aaronmaynard.basic.commands.SetSpawn;
import com.aaronmaynard.basic.commands.SetWarp;
import com.aaronmaynard.basic.commands.Spawn;
import com.aaronmaynard.basic.commands.SpawnMob;
import com.aaronmaynard.basic.commands.Time;
import com.aaronmaynard.basic.commands.Warp;
import com.aaronmaynard.basic.commands.Weather;
import com.aaronmaynard.basic.commands.Weaken;
import com.aaronmaynard.basic.commands.Feed;
import com.aaronmaynard.basic.listener.PlayerJoin;

public class Basic extends JavaPlugin {
    private static Basic i;
    public static File warpdir;
    public static File spawn;
    public boolean debug = false;

    @Override
    public void onEnable() {
        i = this;

        warpdir = new File(Basic.get().getDataFolder(), "warps");
        warpdir.mkdirs();

        spawn = new File(Basic.get().getDataFolder(), "spawn.yml");
        try {
            spawn.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().severe("Failed to generate spawn.yml!");
        }

        register(
                new Warp(),
                new Time(),
                new SetWarp(),
                new Broadcast(),
                new SetSpawn(),
                new Spawn(),
                new Fly(),
                new Pm(),
                new Gamemode(),
                new Heal(),
                new SpawnMob(),
                new Hat(),
                new Weather(),
                new Balance(),
                new Pay(),
                new Afk(),
                new Nick(),
                new Home(),
                new SetHome(),
                new DelHome(),
                new DelWarp(),
                new Weaken(),
                new Feed()
                );
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);

    }

    public void register(String name, BCommand base) {
        getCommand(name).setExecutor(base);
    }

    public void register(BCommand... bases) {
        for (BCommand bcmd : bases) register(bcmd.getClass().getSimpleName().toLowerCase(Locale.ENGLISH), bcmd);
    }

    public static Basic get() {
        return i;
    }

    public boolean hasPerm(CommandSender p, Command cmd) {
        String c = cmd.getName();
        return (p.isOp() || p.hasPermission("basic.command." + c) || p.hasPermission("essentials." + c) || p.hasPermission("basic.command.*"));
    }

    public boolean teleportPlayerToWarp(Player sender, String warpname) throws NumberFormatException, IOException {
        // TODO: Proper YAML parser
        Location l = sender.getLocation();
        for (String line : Files.readAllLines(Basic.spawn.toPath())) {
            if (line.startsWith("world")) l.setWorld(Bukkit.getWorld(line.substring("world: ".length())));

            if (line.startsWith("x:")) l.setX(Double.valueOf(line.substring(3)));

            if (line.startsWith("y:")) l.setY(Double.valueOf(line.substring(3)));

            if (line.startsWith("z:")) l.setZ(Double.valueOf(line.substring(3)));

            if (line.startsWith("pitch")) l.setPitch(new Float(Double.valueOf(line.substring("pitch: ".length()))));

            if (line.startsWith("yaw")) l.setYaw(new Float(Double.valueOf(line.substring("yaw: ".length()))));
        }
        return sender.teleport(l);
    }

    public boolean isSpawnSet() {
        try {
            return Files.readAllLines(spawn.toPath()).size() < 2;
        } catch (IOException e) {
            return false;
        }
    }
}