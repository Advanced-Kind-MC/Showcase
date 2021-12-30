package me.rebelmythik.showcase.events;

import me.rebelmythik.showcase.Core;
import me.rebelmythik.showcase.PlayerFile;
import me.rebelmythik.showcase.utils.ExperienceManager;
import me.rebelmythik.showcase.utils.ShowcaseInstance;
import me.rebelmythik.showcase.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InventoryClicks implements Listener {
    public InventoryClicks(Core instance) {
        instance.getServer().getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void Close(InventoryCloseEvent e) {
        if (!e.getInventory().getName().equalsIgnoreCase("Confirm Item Addition") || !e.getInventory().getItem(4).getType().equals(Material.AIR)) {
            ;
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            if (!e.getCurrentItem().getType().equals(Material.AIR)) {
                Player player = (Player)e.getWhoClicked();
                ItemStack item = e.getCurrentItem();
                String itemName = "";
                if (e.getCurrentItem().hasItemMeta()) {
                    if (item.getItemMeta().hasDisplayName() && ChatColor.stripColor(item.getItemMeta().getDisplayName()).length() == item.getItemMeta().getDisplayName().length()) {
                        itemName = item.getItemMeta().getDisplayName();
                    } else if (item.getItemMeta().hasDisplayName()) {
                        itemName = item.getItemMeta().getDisplayName().replace("§", "&");
                    } else {
                        itemName = item.getType().toString().replace("_", "");
                    }
                }

                String openInv = ChatColor.stripColor(player.getOpenInventory().getTitle());
                int money_price;
                if (openInv.contains("Showcase") && !openInv.contains("[A]")) {
                    e.setCancelled(true);
                    if (openInv.replaceAll("'s Showcase", "").equalsIgnoreCase(player.getName())) {
                        Inventory destruction;
                        if (e.getRawSlot() > 8) {
                            destruction = Bukkit.createInventory((InventoryHolder)null, 9, "Confirm Item Addition");

                            for(money_price = 0; money_price < 4; ++money_price) {
                                destruction.setItem(money_price, Utils.createItem(new ItemStack(Material.getMaterial(Core.ADD_ACCEPT_ITEM), 1, (short)Core.ADD_ACCEPT_ID), Core.ADD_ACCEPT_NAME, Core.ADD_ACCEPT_LORE));
                            }

                            for(money_price = 5; money_price < 9; ++money_price) {
                                destruction.setItem(money_price, Utils.createItem(new ItemStack(Material.getMaterial(Core.ADD_DENY_ITEM), 1, (short)Core.ADD_DENY_ID), Core.ADD_DENY_NAME, Core.ADD_DENY_LORE));
                            }

                            destruction.setItem(4, item);
                            player.getOpenInventory().getBottomInventory().setItem(e.getSlot(), new ItemStack(Material.AIR));
                            player.openInventory(destruction);

                            try {
                                player.playSound(player.getLocation(), Sound.valueOf(Core.SOUND_CLICK), 10.0F, 1.0F);
                            } catch (Exception var15) {
                                Core.getInstance().getLogger().info("Failed to retrieve sound " + Core.SOUND_CLICK);
                            }

                            try {
                                player.playSound(player.getLocation(), Sound.valueOf(Core.SOUND_CLICK), 10.0F, 1.0F);
                            } catch (Exception var14) {
                                Core.getInstance().getLogger().info("Failed to retrieve sound " + Core.SOUND_CLICK);
                            }
                        } else if (Core.getInstance().getConfig().getBoolean("showcase.retrieve-items")) {
                            destruction = Bukkit.createInventory((InventoryHolder)null, 9, "Confirm Retrieve Item");

                            for(money_price = 0; money_price < 4; ++money_price) {
                                destruction.setItem(money_price, Utils.createItem(new ItemStack(Material.getMaterial(Core.RETRIEVE_ACCEPT_ITEM), 1, (short)Core.RETRIEVE_ACCEPT_ID), Core.RETRIEVE_ACCEPT_NAME, Core.RETRIEVE_ACCEPT_LORE));
                            }

                            for(money_price = 5; money_price < 9; ++money_price) {
                                destruction.setItem(money_price, Utils.createItem(new ItemStack(Material.getMaterial(Core.RETRIEVE_ACCEPT_ITEM), 1, (short)Core.RETRIEVE_DENY_ID), Core.RETRIEVE_DENY_NAME, Core.RETRIEVE_DENY_LORE));
                            }

                            destruction.setItem(4, item);
                            player.openInventory(destruction);
                        } else {
                            destruction = Bukkit.createInventory((InventoryHolder)null, 9, "Confirm Destruct Item");

                            for(money_price = 0; money_price < 4; ++money_price) {
                                destruction.setItem(money_price, Utils.createItem(new ItemStack(Material.getMaterial(Core.RETRIEVE_ACCEPT_ITEM), 1, (short)Core.RETRIEVE_ACCEPT_ID), Core.RETRIEVE_ACCEPT_NAME, Core.RETRIEVE_ACCEPT_LORE));
                            }

                            for(money_price = 5; money_price < 9; ++money_price) {
                                destruction.setItem(money_price, Utils.createItem(new ItemStack(Material.getMaterial(Core.RETRIEVE_ACCEPT_ITEM), 1, (short)Core.RETRIEVE_DENY_ID), Core.RETRIEVE_DENY_NAME, Core.RETRIEVE_DENY_LORE));
                            }

                            destruction.setItem(4, item);
                            player.openInventory(destruction);
                        }
                    }
                }

                PlayerFile pf;
                if (openInv.equalsIgnoreCase("Confirm Item Addition")) {
                    e.setCancelled(true);
                    pf = new PlayerFile(player);
                    if (item.getItemMeta().hasDisplayName()) {
                        if (itemName.equalsIgnoreCase(Core.ADD_ACCEPT_NAME)) {
                            Object showcaseItems;
                            if (pf.config.getList("showcase") != null) {
                                showcaseItems = pf.config.getList("showcase");
                            } else {
                                showcaseItems = new ArrayList();
                            }

                            ItemStack i = player.getOpenInventory().getItem(4);
                            if (((List)showcaseItems).size() >= (new ShowcaseInstance(player)).getInventorySize() * 9) {
                                player.sendMessage("§cYou cannot add more items.");
                                return;
                            }

                            ((List)showcaseItems).add(i);
                            pf.config.set("showcase", showcaseItems);
                            pf.save();
                            player.getOpenInventory().getItem(4).setType(Material.AIR);
                            Utils.openShowcase(player, player);

                            try {
                                player.playSound(player.getLocation(), Sound.valueOf(Core.SOUND_CLICK), 10.0F, 1.0F);
                            } catch (Exception var13) {
                                Core.getInstance().getLogger().info("Failed to retrieve sound " + Core.SOUND_CLICK);
                            }
                        }

                        if (itemName.equalsIgnoreCase(Core.ADD_DENY_NAME)) {
                            player.getInventory().addItem(new ItemStack[]{player.getOpenInventory().getTopInventory().getItem(4)});
                            Utils.openShowcase(player, player);
                        }
                    }
                }

                List showcaseItems;
                if (openInv.equalsIgnoreCase("Confirm Destruct Item")) {
                    e.setCancelled(true);
                    pf = new PlayerFile(player);
                    if (item.getItemMeta().hasDisplayName()) {
                        if (itemName.equalsIgnoreCase(Core.RETRIEVE_ACCEPT_NAME) && !Core.getInstance().getConfig().getBoolean("showcase.retrieve-items")) {
                            try {
                                player.playSound(player.getLocation(), Sound.valueOf(Core.SOUND_CLICK), 10.0F, 1.0F);
                            } catch (Exception var12) {
                                Core.getInstance().getLogger().info("Failed to retrieve sound " + Core.SOUND_CLICK);
                            }

                            showcaseItems = pf.config.getList("showcase");
                            if (showcaseItems.contains(player.getOpenInventory().getItem(4))) {
                                showcaseItems.remove(player.getOpenInventory().getItem(4));
                            }

                            pf.config.set("showcase", showcaseItems);
                            pf.save();
                            Utils.openShowcase(player, player);
                        }

                        if (itemName.equalsIgnoreCase(Core.RETRIEVE_DENY_NAME)) {
                            player.closeInventory();
                        }
                    }
                }

                if (openInv.equalsIgnoreCase("Confirm Retrieve Item")) {
                    e.setCancelled(true);
                    pf = new PlayerFile(player);
                    if (item.getItemMeta().hasDisplayName()) {
                        if (itemName.equalsIgnoreCase(Core.RETRIEVE_ACCEPT_NAME) && Core.getInstance().getConfig().getBoolean("showcase.retrieve-items")) {
                            if (Core.getInstance().getConfig().getInt("showcase.costs.exp") > 0) {
                                money_price = Core.getInstance().getConfig().getInt("showcase.costs.exp");
                                ExperienceManager experienceManager = new ExperienceManager(player);
                                if (experienceManager.getCurrentExp() < money_price) {
                                    Iterator var10 = Core.getInstance().getConfig().getStringList("showcase.costs.message").iterator();

                                    while(var10.hasNext()) {
                                        String s = (String)var10.next();
                                        player.sendMessage(s.replace("&", "§"));
                                    }

                                    return;
                                }

                                experienceManager.changeExp(experienceManager.getCurrentExp() - money_price);
                            }

                            if (Core.getInstance().getConfig().getInt("showcase.costs.money") > 0) {
                                money_price = Core.getInstance().getConfig().getInt("showcase.costs.money");
                                Iterator var9;
                                String s;
                                if (!(Core.getEcononomy().getBalance(player.getName()) >= (double)money_price)) {
                                    var9 = Core.getInstance().getConfig().getStringList("showcase.costs.message-not-enough-money").iterator();

                                    while(var9.hasNext()) {
                                        s = (String)var9.next();
                                        player.sendMessage(s.replace("&", "§"));
                                    }

                                    return;
                                }

                                Core.getEcononomy().withdrawPlayer(player.getName(), (double)money_price);
                                var9 = Core.getInstance().getConfig().getStringList("showcase.costs.message-successfull").iterator();

                                while(var9.hasNext()) {
                                    s = (String)var9.next();
                                    player.sendMessage(s.replace("&", "§").replace("{money}", String.valueOf(money_price)));
                                }
                            }

                            try {
                                player.playSound(player.getLocation(), Sound.valueOf(Core.SOUND_CLICK), 10.0F, 1.0F);
                            } catch (Exception var11) {
                                Core.getInstance().getLogger().info("Failed to retrieve sound " + Core.SOUND_CLICK);
                            }

                            showcaseItems = pf.config.getList("showcase");
                            if (showcaseItems.contains(player.getOpenInventory().getItem(4))) {
                                showcaseItems.remove(player.getOpenInventory().getItem(4));
                            }

                            pf.config.set("showcase", showcaseItems);
                            pf.save();
                            player.getInventory().addItem(new ItemStack[]{player.getOpenInventory().getItem(4)});
                            Utils.openShowcase(player, player);
                            player.updateInventory();
                        }

                        if (itemName.equalsIgnoreCase(Core.RETRIEVE_DENY_NAME)) {
                            player.closeInventory();
                        }
                    }
                }

            }
        }
    }
}
