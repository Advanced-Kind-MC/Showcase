package me.rebelmythik.showcase.commands;

import me.rebelmythik.showcase.Core;
import me.rebelmythik.showcase.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Iterator;

public class Showcase implements CommandExecutor, Listener {
    public Showcase() {
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String command = e.getMessage();
        if (command.startsWith("/showcase")) {
            Player p = e.getPlayer();
            if (command.contains("-about")) {
                e.setCancelled(true);
                p.sendMessage("§cSHOWCASE> §4This server is Running Showcase");
                p.sendMessage("§4Author: §7N3kas");
                p.sendMessage("§4Downloaded by:  §7https://www.spigotmc.org/members/" + Core.USER + "/");
                p.sendMessage("§4Plugin version: §7" + Core.getInstance().getDescription().getVersion());
            } else if (command.contains("-reload")) {
                e.setCancelled(true);
                if (!p.hasPermission("showcase.admin")) {
                    p.sendMessage("§cSHOWCASE> §4No permission.");
                } else {
                    Core.getInstance().reloadConfig();
                    Core.Reload();
                    p.sendMessage("§cSHOWCASE> §4Configuration has been reloaded.");
                }
            } else {
                if (command.contains("-admin")) {
                    e.setCancelled(true);
                    if (!p.hasPermission("showcase.admin")) {
                        p.sendMessage("§cSHOWCASE> §4No permission.");
                        return;
                    }

                    OfflinePlayer target = Bukkit.getOfflinePlayer(command.split(" ")[1]);
                    Utils.openShowcaseForAdmin(target, p);
                    p.sendMessage("§4---------------------------------------");
                    p.sendMessage("§cSHOWCASE> §4NOTE: §cTo remove an item, just click on it.");
                    p.sendMessage("§4---------------------------------------");
                }

            }
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (args.length == 0) {
                if (player.hasPermission(Core.PERM)) {
                    Utils.openShowcase(player, player);

                    try {
                        player.playSound(player.getLocation(), Sound.valueOf(Core.SOUND_OPENED), 10.0F, 1.0F);
                    } catch (Exception var9) {
                        Core.getInstance().getLogger().info("Failed to retrieve sound " + Core.SOUND_OPENED);
                    }
                } else {
                    Iterator var7 = Core.getInstance().getConfig().getStringList("showcase.command.no-permission-message").iterator();

                    while(var7.hasNext()) {
                        String s = (String)var7.next();
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
                    }
                }
            }

            if (args.length == 1) {
                if (player.hasPermission("showcase.open.others")) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    Utils.openShowcase(target, player);

                    try {
                        player.playSound(player.getLocation(), Sound.valueOf(Core.SOUND_OPENED), 10.0F, 1.0F);
                    } catch (Exception var8) {
                        Core.getInstance().getLogger().info("Failed to retrieve sound " + Core.SOUND_OPENED);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission");
                }
            }
        }

        return false;
    }
}
