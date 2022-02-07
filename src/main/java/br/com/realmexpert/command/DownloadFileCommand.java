package br.com.realmexpert.command;

import static br.com.realmexpert.configuration.RealmExpertMCConfigUtil.bRealmExpertMC;
import static br.com.realmexpert.network.download.UpdateUtils.downloadFile;
import java.io.File;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class DownloadFileCommand extends Command {

    public DownloadFileCommand(String name) {
        super(name);
        this.description = "Download a file from url directly in the choosed folder.";
        this.usageMessage = "/downloadfile";
        this.setPermission("RealmExpertMC.command.downloadfile");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;

        if (bRealmExpertMC("downloadfile_command_enabled")) {
            if (args.length == 1) {
                sender.sendMessage("[RealmExpertMC] - You need to specify the path of the file.");
                return false;
            }
            if (args.length == 2) {
                sender.sendMessage("[RealmExpertMC] - You need to specify the link of the file.");
                return false;
            }
            if (args.length == 3) {
                try {
                    downloadFile(args[2], new File(args[1] + "/" + args[0]));
                    return true;
                } catch (Exception e) {
                    sender.sendMessage("[RealmExpertMC] - Failed to download the file.");
                    e.printStackTrace();
                    return false;
                }
            }
        } else sender.sendMessage("[RealmExpertMC] To use this command, you need to set downloadfile_command_enabled to true in RealmExpertMC-config/RealmExpertMC.yml.");
        return true;
    }
}