package me.rebelmythik.showcase.events2;

import me.rebelmythik.showcase.PlayerFile;
import me.rebelmythik.showcase.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InventoryClick implements Listener {
    public InventoryClick() {
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            if (!e.getCurrentItem().getType().equals(Material.AIR)) {
                Player p = (Player)e.getWhoClicked();
                ItemStack item = e.getCurrentItem();
                String openInv = ChatColor.stripColor(p.getOpenInventory().getTitle());
                if (openInv.contains("Showcase") && openInv.contains("[A]")) {
                    InventoryView inv = e.getWhoClicked().getOpenInventory();
                    if (!inv.getTopInventory().contains(e.getCurrentItem())) {
                        e.setCancelled(true);
                        return;
                    }

                    OfflinePlayer bf = Bukkit.getOfflinePlayer(openInv.replace("'s Showcase [A]", ""));
                    e.setCancelled(true);
                    PlayerFile pf = new PlayerFile(bf);
                    List<ItemStack> showcaseItems = pf.config.getList("showcase");
                    if (showcaseItems.contains(item)) {
                        showcaseItems.remove(item);
                    }

                    p.getInventory().addItem(new ItemStack[]{item});
                    pf.config.set("showcase", showcaseItems);
                    pf.save();
                    p.closeInventory();
                    Utils.openShowcaseForAdmin(bf, p);
                }

            }
        }
    }
}
