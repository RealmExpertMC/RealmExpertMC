package br.com.realmexpert.command;

import br.com.realmexpert.RealmExpertMCThreadCost;
import br.com.realmexpert.api.PlayerAPI;
import br.com.realmexpert.api.ServerAPI;
import br.com.realmexpert.configuration.RealmExpertMCConfig;
import br.com.realmexpert.util.i18n.Message;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RealmExpertMCCommand extends Command {

    private List<String> params = Arrays.asList("mods", "playermods", "printthreadcost", "lang", "item", "reload", "give");

    public RealmExpertMCCommand(String name) {
        super(name);
        this.description = "RealmExpertMC related commands";
        this.usageMessage = "/RealmExpertMC [mods|playermods|printthreadcost|lang|item|reload|give]";
        this.setPermission("RealmExpertMC.command.RealmExpertMC");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> list = new ArrayList<>();
        if (args.length == 1 && (sender.isOp() || testPermission(sender))) {
            for (String param : params) {
                if (param.toLowerCase().startsWith(args[0].toLowerCase())) {
                    list.add(param);
                }
            }
        }
        if (args.length >= 3 && args[0].equals("give")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                list.add(p.getName());
            }
        }

        if (args.length == 2 && args[0].equals("item")) {
            list.add("info");
        }

        return list;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!testPermission(sender)) return true;

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
            return false;
        }

        switch (args[0].toLowerCase(Locale.ENGLISH)) {
            case "mods":
                // Not recommended for use in games, only test output
                sender.sendMessage(ChatColor.GREEN + "" + ServerAPI.getModSize() + " " + ServerAPI.getModList());
                break;
            case "playermods":
                // Not recommended for use in games, only test output
                if (args.length == 1) {
                    sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
                    return false;
                }
                Player player = Bukkit.getPlayer(args[1]);
                if (player != null) {
                    sender.sendMessage(ChatColor.GREEN + "" + PlayerAPI.getModSize(player) + " " + PlayerAPI.getModlist(player));
                } else {
                    sender.sendMessage(ChatColor.RED + "The player [" + args[1] + "] is not online.");
                }
                break;
            case "printthreadcost":
                RealmExpertMCThreadCost.dumpThreadCpuTime();
                break;
            case "lang":
                sender.sendMessage(ChatColor.GREEN + Message.getLocale());
                break;
            case "item":
                if (args.length == 1) {
                    sender.sendMessage(ChatColor.RED + "Usage: /RealmExpertMC item info");
                    return false;
                }
                if ("info".equals(args[1].toLowerCase(Locale.ENGLISH))) {
                    ItemCommand.info(sender);
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /RealmExpertMC item info");
                }
                break;
            case "reload":
                RealmExpertMCConfig.instance.load();
                sender.sendMessage(ChatColor.GREEN + "RealmExpertMC.yml reload complete.");
                break;
            case "give":
                GiveCommand.info(sender, args);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
                return false;
        }


        return true;
    }
}
