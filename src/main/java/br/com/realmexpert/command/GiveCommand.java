package br.com.realmexpert.command;

import br.com.realmexpert.util.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand {

    public static void info(CommandSender sender, String[] args) {
        if (args.length >= 3) {
            for (Player player0 : Bukkit.getOnlinePlayers()) {
                if (player0.getName().toLowerCase().equals(args[1])) {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player == null) return;
                    String args2 = args[2];
                    String[] s = args2.split(":");
                    String j = args2.contains(":") ? s[1] : String.valueOf(0);
                    // RealmExpertMC give Mgazul 4096 64
                    if (NumberUtils.isInteger(args2)) {
                        int id = Integer.valueOf(args2).intValue();
                        if (Material.byId[id] != null) {
                            int i = args.length == 4 ? Integer.parseInt(args[3]) : 1;
                            ItemStack itemStack = new ItemStack(Material.getMaterial(id), i, Short.parseShort(j));
                            if (itemStack != null) {
                                player.getInventory().addItem(itemStack);
                            }
                        }
                    }
                    // RealmExpertMC give Mgazul 4096:1 64
                    else if (args2.contains(":") && NumberUtils.isInteger(s[0]) && NumberUtils.isInteger(s[1])) {
                        int id = Integer.valueOf(s[0]).intValue();
                        int i = args.length == 4 ? Integer.parseInt(args[3]) : 1;
                        ItemStack itemStack = new ItemStack(Material.getMaterial(id), i, Short.parseShort(s[1]));
                        if (itemStack != null) {
                            player.getInventory().addItem(itemStack);
                        }
                    }
                    // RealmExpertMC give Mgazul ASDASD 64
                    else if (Material.getMaterial(args2) != null) {
                        int i = args.length == 4 ? Integer.parseInt(args[3]) : 1;
                        ItemStack itemStack = new ItemStack(Material.getMaterial(args2), i, Short.parseShort(j));
                        if (itemStack != null) {
                            player.getInventory().addItem(itemStack);
                        }
                    }
                }
            }
        }
    }
}
