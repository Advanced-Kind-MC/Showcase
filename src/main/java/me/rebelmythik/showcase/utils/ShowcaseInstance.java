package me.rebelmythik.showcase.utils;

import me.rebelmythik.showcase.Core;
import me.rebelmythik.showcase.PlayerFile;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ShowcaseInstance {
    OfflinePlayer player;

    public ShowcaseInstance(OfflinePlayer p) {
        this.player = p;
    }

    public int getInventorySize() {
        int inv_size = 1;
        int inv_size2 = 1;
        if (this.player.isOnline()) {
            Player p = this.player.getPlayer();
            if (p.hasPermission(Core.PERM + ".6")) {
                inv_size = 6;
            } else if (p.hasPermission(Core.PERM + ".5")) {
                inv_size = 5;
            } else if (p.hasPermission(Core.PERM + ".4")) {
                inv_size = 4;
            } else if (p.hasPermission(Core.PERM + ".3")) {
                inv_size = 3;
            } else if (p.hasPermission(Core.PERM + ".2")) {
                inv_size = 2;
            }
        }

        PlayerFile pfa = new PlayerFile(this.player);
        if (pfa.config.getList("showcase") == null) {
            return inv_size;
        } else {
            int pf = pfa.config.getList("showcase").size();
            if (pf > 9) {
                inv_size2 = 2;
            } else if (pf > 18) {
                inv_size2 = 3;
            } else if (pf > 27) {
                inv_size2 = 4;
            } else if (pf > 36) {
                inv_size2 = 5;
            } else if (pf > 45) {
                inv_size2 = 6;
            }

            if (inv_size < inv_size2) {
                inv_size = inv_size2;
            }

            return inv_size;
        }
    }
}