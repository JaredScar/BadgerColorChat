package com.core.wolfbadger.chat.colour.enjoy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: MayoDwarf
 * Date: 6/28/14
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main extends JavaPlugin implements Listener {
    private HashMap<UUID, String> cManager;
    public void onEnable() {
        this.cManager = new HashMap<UUID, String>();
        this.getServer().getPluginManager().registerEvents(this, this);
    }
    public void onDisable() {}

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        switch (args.length) {
            case 0:
                if(sender instanceof Player) {
                    Player p = (Player) sender;
                    p.sendMessage(ChatColor.RED+"Usage: /color <color code>");
                }
                break;
            case 1:
                if(cmd.getName().equalsIgnoreCase("color")) {
                    if(sender instanceof Player) {
                        if(args[0].toCharArray().length == 2) {
                        Player p = (Player) sender;
                            if(p.hasPermission("chatcolor.change")) {
                                String s = args[0];
                    cManager.put(p.getUniqueId(), s);
                            }
                        } else {
                            Player p = (Player) sender;
                            p.sendMessage(ChatColor.RED+"The color you put must be a &<color id> such as &7 or &3! &r resets the player's color!");
                        }
                    }
                }
                break;
            case 2:
                if(cmd.getName().equalsIgnoreCase("color")) {
                    if(sender instanceof Player) {
                        Player p = (Player) sender;
                        if(p.hasPermission("chatcolor.others")) {
                        if(args[1].toCharArray().length == 2) {
                        for(Player players : Bukkit.getOnlinePlayers()) {
                            if(args[0].equalsIgnoreCase(players.getDisplayName())) {
                                String s = args[1];
                                cManager.put(players.getUniqueId(), s);
                                break;
                                }
                            }
                        } else {
                            p.sendMessage(ChatColor.RED+"The color you put must be a &<color id> such as &7 or &3! &r resets the player's color!");
                            }
                        }
                    }
                }
                break;
        }
        return true;
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();
        if(cManager.containsKey(p.getUniqueId())) {
            e.setMessage(ChatColor.translateAlternateColorCodes('&', cManager.get(p.getUniqueId()))+""+e.getMessage());
        }
    }
}
