package br.com.realmexpert.command;

import br.com.realmexpert.configuration.RealmExpertMCConfig;
import static br.com.realmexpert.configuration.RealmExpertMCConfigUtil.bRealmExpertMC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class UpdateRealmExpertMCCommand extends Command {

    public UpdateRealmExpertMCCommand(String name) {
        super(name);
        this.description = "Update RealmExpertMC to the latest build.";
        this.usageMessage = "/updateRealmExpertMC";
        this.setPermission("RealmExpertMC.command.updateRealmExpertMC");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if(sender.isOp()) {
            boolean val = bRealmExpertMC("check_update_auto_download");
            RealmExpertMCConfig.setValueRealmExpertMC("RealmExpertMC.check_update_auto_download", !val);
            if(!val) System.out.println("[RealmExpertMC] Auto update is now enabled. To update RealmExpertMC, you need to restart the server.");
            else System.out.println("[RealmExpertMC] Auto update is now disabled.");
        }
        return true;
    }
}
