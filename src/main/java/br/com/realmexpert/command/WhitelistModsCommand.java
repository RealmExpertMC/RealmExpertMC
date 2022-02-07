package br.com.realmexpert.command;

import br.com.realmexpert.configuration.RealmExpertMCConfig;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class WhitelistModsCommand extends Command {

    private static String list = "";

    public WhitelistModsCommand(String name) {
        super(name);
        this.description = "Command to update, enable or disable the mods whitelist.";
        this.usageMessage = "/whitelistmods [enable|disable|update]";
        this.setPermission("RealmExpertMC.command.whitelistmods");
    }

    private static String makeModList() {
        for (ModContainer mod : Loader.instance().getModList())
            if (!mod.getModId().equals("RealmExpertMC") || !mod.getModId().equals("forge")) {
                list = list + mod.getModId() + "@" + mod.getVersion() + ",";
            }
        return list.substring(0, list.length() - 1);
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "enable":
                RealmExpertMCConfig.setValueRealmExpertMC("forge.modswhitelist.list", makeModList());
                RealmExpertMCConfig.setValueRealmExpertMC("forge.enable_mods_whitelist", true);
                break;
            case "disable":
                RealmExpertMCConfig.setValueRealmExpertMC("forge.enable_mods_whitelist", false);
                break;
            case "update":
                RealmExpertMCConfig.setValueRealmExpertMC("forge.modswhitelist.list", makeModList());
                break;
        }

        return true;
    }
}