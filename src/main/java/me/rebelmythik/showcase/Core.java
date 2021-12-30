package me.rebelmythik.showcase;

import me.rebelmythik.showcase.commands.Showcase;
import me.rebelmythik.showcase.events.InventoryClicks;
import me.rebelmythik.showcase.events2.InventoryClick;
import me.rebelmythik.showcase.events2.InventoryClose;
import me.rebelmythik.showcase.events2.JoinEvent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
    public static Core plugin;
    public static String USER = "%%__USER__%%";
    public static String PERM;
    public static String SOUND_CLICK;
    public static String ADD_ACCEPT_ITEM;
    public static String SOUND_OPENED;
    public static String ADD_ACCEPT_NAME;
    public static String ADD_ACCEPT_LORE;
    public static String ADD_DENY_ITEM;
    public static String ADD_DENY_NAME;
    public static String ADD_DENY_LORE;
    public static String RETRIEVE_ACCEPT_ITEM;
    public static String RETRIEVE_ACCEPT_NAME;
    public static String RETRIEVE_ACCEPT_LORE;
    public static String RETRIEVE_DENY_ITEM;
    public static String RETRIEVE_DENY_NAME;
    public static String RETRIEVE_DENY_LORE;
    public static int ADD_ACCEPT_ID;
    public static int RETRIEVE_DENY_ID;
    public static int ADD_DENY_ID;
    public static int RETRIEVE_ACCEPT_ID;
    private static Economy econ = null;

    public Core() {
    }

    public static Economy getEcononomy() {
        return econ;
    }

    private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        } else {
            RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                return false;
            } else {
                econ = (Economy)rsp.getProvider();
                return econ != null;
            }
        }
    }

    public void onEnable() {
        this.saveDefaultConfig();
        plugin = this;
        if (this.setupEconomy()) {
            this.getLogger().info("Economy plugin (Vault) has been found, money manipulation is now available.");
        }

        PERM = this.getConfig().getString("showcase.command.permission");
        ADD_ACCEPT_ITEM = getInstance().getConfig().getString("inventory.confirm-add.accept.item");
        ADD_ACCEPT_ID = getInstance().getConfig().getInt("inventory.confirm-add.accept.id");
        ADD_ACCEPT_NAME = getInstance().getConfig().getString("inventory.confirm-add.accept.name");
        ADD_ACCEPT_LORE = getInstance().getConfig().getString("inventory.confirm-add.accept.lore");
        ADD_DENY_ITEM = getInstance().getConfig().getString("inventory.confirm-add.deny.item");
        ADD_DENY_ID = getInstance().getConfig().getInt("inventory.confirm-add.deny.id");
        ADD_DENY_NAME = getInstance().getConfig().getString("inventory.confirm-add.deny.name");
        ADD_DENY_LORE = getInstance().getConfig().getString("inventory.confirm-add.deny.lore");
        RETRIEVE_ACCEPT_ITEM = getInstance().getConfig().getString("inventory.confirm-remove.accept.item");
        RETRIEVE_ACCEPT_ID = getInstance().getConfig().getInt("inventory.confirm-remove.accept.id");
        RETRIEVE_ACCEPT_NAME = getInstance().getConfig().getString("inventory.confirm-remove.accept.name");
        RETRIEVE_ACCEPT_LORE = getInstance().getConfig().getString("inventory.confirm-remove.accept.lore");
        RETRIEVE_DENY_ITEM = getInstance().getConfig().getString("inventory.confirm-remove.deny.item");
        RETRIEVE_DENY_ID = getInstance().getConfig().getInt("inventory.confirm-remove.deny.id");
        RETRIEVE_DENY_NAME = getInstance().getConfig().getString("inventory.confirm-remove.deny.name");
        RETRIEVE_DENY_LORE = getInstance().getConfig().getString("inventory.confirm-remove.deny.lore");
        SOUND_CLICK = getInstance().getConfig().getString("showcase.sounds.item-clicked");
        SOUND_OPENED = getInstance().getConfig().getString("showcase.sounds.inventory-opened");
        new InventoryClicks(this);
        this.getCommand("showcase").setExecutor(new Showcase());
        Bukkit.getPluginManager().registerEvents(new Showcase(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClose(), this);
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
    }

    public static Plugin getInstance() {
        return plugin;
    }

    public static void Reload() {
        PERM = getInstance().getConfig().getString("showcase.command.permission");
        ADD_ACCEPT_ITEM = getInstance().getConfig().getString("inventory.confirm-add.accept.item");
        ADD_ACCEPT_ID = getInstance().getConfig().getInt("inventory.confirm-add.accept.id");
        ADD_ACCEPT_NAME = getInstance().getConfig().getString("inventory.confirm-add.accept.name");
        ADD_ACCEPT_LORE = getInstance().getConfig().getString("inventory.confirm-add.accept.lore");
        ADD_DENY_ITEM = getInstance().getConfig().getString("inventory.confirm-add.deny.item");
        ADD_DENY_ID = getInstance().getConfig().getInt("inventory.confirm-add.deny.id");
        ADD_DENY_NAME = getInstance().getConfig().getString("inventory.confirm-add.deny.name");
        ADD_DENY_LORE = getInstance().getConfig().getString("inventory.confirm-add.deny.lore");
        RETRIEVE_ACCEPT_ITEM = getInstance().getConfig().getString("inventory.confirm-remove.accept.item");
        RETRIEVE_ACCEPT_ID = getInstance().getConfig().getInt("inventory.confirm-remove.accept.id");
        RETRIEVE_ACCEPT_NAME = getInstance().getConfig().getString("inventory.confirm-remove.accept.name");
        RETRIEVE_ACCEPT_LORE = getInstance().getConfig().getString("inventory.confirm-remove.accept.lore");
        RETRIEVE_DENY_ITEM = getInstance().getConfig().getString("inventory.confirm-remove.deny.item");
        RETRIEVE_DENY_ID = getInstance().getConfig().getInt("inventory.confirm-remove.deny.id");
        RETRIEVE_DENY_NAME = getInstance().getConfig().getString("inventory.confirm-remove.deny.name");
        RETRIEVE_DENY_LORE = getInstance().getConfig().getString("inventory.confirm-remove.deny.lore");
        SOUND_CLICK = getInstance().getConfig().getString("showcase.sounds.item-clicked");
        SOUND_OPENED = getInstance().getConfig().getString("showcase.sounds.inventory-opened");
    }
}
