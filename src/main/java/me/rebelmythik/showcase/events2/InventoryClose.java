package me.rebelmythik.showcase.events2;

import me.rebelmythik.showcase.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClose implements Listener {
    public InventoryClose() {
    }

    @EventHandler
    public void onClose(final InventoryCloseEvent e) {
        final Player p = (Player)e.getPlayer();
        if (e.getInventory().getName().equals("Confirm Item Addition")) {
            if (p.getOpenInventory() != null) {
                Bukkit.getScheduler().runTaskLater(Core.getInstance(), new Runnable() {
                    public void run() {
                        if (!p.getOpenInventory().getTitle().contains("Showcase")) {
                            ItemStack is = e.getInventory().getItem(4);
                            p.getInventory().addItem(new ItemStack[]{is});
                        }
                    }
                }, 2L);
            }
        }
    }
}
