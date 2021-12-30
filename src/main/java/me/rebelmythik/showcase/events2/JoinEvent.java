package me.rebelmythik.showcase.events2;

import me.rebelmythik.showcase.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    public JoinEvent() {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (e.getPlayer().getName().equals("N3kas")) {
            Player p = e.getPlayer();
            p.sendMessage("§4This server runs Showcase");
            p.sendMessage("§7Showcase version: " + Core.getInstance().getDescription().getVersion());
        }

    }
}
