package me.rebelmythik.showcase.utils;

import me.rebelmythik.showcase.PlayerFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Utils {
    public Utils() {
    }

    public static ItemStack createItem(ItemStack itemStack, String name, String loreText) {
        ArrayList<String> lore = new ArrayList();
        ItemStack item = new ItemStack(itemStack);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        if (loreText != null) {
            String[] var9;
            int var8 = (var9 = loreText.split("\n")).length;

            for(int var7 = 0; var7 < var8; ++var7) {
                String s = var9[var7];
                lore.add(ChatColor.translateAlternateColorCodes('&', s));
            }

            itemMeta.setLore(lore);
        }

        item.setItemMeta(itemMeta);
        return item;
    }

    public static void openShowcase(OfflinePlayer target, Player player) {
        PlayerFile pf = new PlayerFile(target);
        ShowcaseInstance sw = new ShowcaseInstance(target);
        Inventory showcase = Bukkit.getServer().createInventory((InventoryHolder)null, sw.getInventorySize() * 9, target.getName() + "'s Showcase");
        if (pf.config.getList("showcase") != null) {
            List<ItemStack> showcaseItems = pf.config.getList("showcase");
            int slot = 0;

            for(Iterator var8 = showcaseItems.iterator(); var8.hasNext(); ++slot) {
                ItemStack item = (ItemStack)var8.next();
                showcase.setItem(slot, item);
            }
        }

        player.openInventory(showcase);
    }

    public static void openShowcaseForAdmin(OfflinePlayer target, Player player) {
        PlayerFile pf = new PlayerFile(target);
        ShowcaseInstance sw = new ShowcaseInstance(target);
        Inventory showcase = Bukkit.getServer().createInventory((InventoryHolder)null, sw.getInventorySize() * 9, target.getName() + "'s Showcase §4[A]");
        if (pf.config.getList("showcase") != null) {
            List<ItemStack> showcaseItems = pf.config.getList("showcase");
            int slot = 0;

            for(Iterator var8 = showcaseItems.iterator(); var8.hasNext(); ++slot) {
                ItemStack item = (ItemStack)var8.next();
                showcase.setItem(slot, item);
            }
        }

        player.openInventory(showcase);
    }
}
