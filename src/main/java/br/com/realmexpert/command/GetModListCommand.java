package br.com.realmexpert.command;

import br.com.realmexpert.util.HasteUtils;
import java.io.IOException;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class GetModListCommand extends Command {
    private static String sendToHaste = "";

    public GetModListCommand(String name) {
        super(name);
        this.description = "Paste the list of your mods on hastebin and get the link.";
        this.usageMessage = "/getmodlist";
        this.setPermission("RealmExpertMC.command.getmodlist");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;

        for (ModContainer mod : Loader.instance().getModList()) {
            sendToHaste = sendToHaste + "\nName : " + mod.getName() + "\nModID : " + mod.getModId() + "\nVersion : " + mod.getVersion() + "\n---------";
        }
        try {
            sender.sendMessage("Link of the list of your mods : " + HasteUtils.pasteRealmExpertMC(sendToHaste));
        } catch (IOException e) {
            System.out.println("Unable to paste the list of your mods.");
        }

        return true;
    }
}
