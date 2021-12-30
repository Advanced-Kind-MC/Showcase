package me.rebelmythik.showcase;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class PlayerFile {
    private Plugin plugin = Core.getInstance();
    private File folder;
    private File file;
    public YamlConfiguration config;

    public PlayerFile(OfflinePlayer player) {
        this.folder = new File(this.plugin.getDataFolder() + File.separator + "showcases");
        if (!this.folder.exists()) {
            this.folder.mkdirs();
        }

        this.file = new File(this.folder, player.getUniqueId().toString() + ".yml");
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
                this.config = YamlConfiguration.loadConfiguration(this.file);
                this.config.set("showcase", (Object)null);
                this.config.save(this.file);
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        } else {
            this.config = YamlConfiguration.loadConfiguration(this.file);
        }

    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }
}
