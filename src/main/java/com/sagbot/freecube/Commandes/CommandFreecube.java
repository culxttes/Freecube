package com.sagbot.freecube.Commandes;

import com.sagbot.freecube.Section;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

public class CommandFreecube implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        String[] neoArgs = new String[args.length];
        for (int i = 1; i < args.length; ++i){
            neoArgs[i - 1] = args[i];
        }
        if (args[0].equals("tp")) {
            return tp(sender, command, label, neoArgs);
        }
        return false;
    }

    public boolean tp(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Please enter a zone.");
            return false;
        }
        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch(NumberFormatException e) {
            sender.sendMessage("Please enter a correct zone.");
            return false;
        }
        Point spawn = Section.getSpawnPoint(id);
        ((Player) sender).teleport(new Location(Bukkit.getWorld("world"), spawn.x, 65, spawn.y));
        return true;
    }
}
