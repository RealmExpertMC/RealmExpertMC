package br.com.realmexpert.command;

import br.com.realmexpert.util.pluginmanager.PluginManagers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class PluginCommand extends Command {

    private final List<String> params = Arrays.asList("load", "unload", "reload");

    public PluginCommand(String name) {
        super(name);
        this.description = "Plugin manager";
        this.usageMessage = "/plugin [load|unload|reload] [name]";
        this.setPermission("RealmExpertMC.command.plugin");
    }

    private boolean checkparam(String args) {
        //TODO
        for (String param : params) {
            if (args.equalsIgnoreCase(param)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!testPermission(sender)) return true;
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
            return false;
        }

        switch (args[0].toLowerCase(Locale.ENGLISH)) {
            case "load":
                PluginManagers.loadPluginCommand(sender, commandLabel, args);
                break;
            case "unload":
                PluginManagers.unloadPluginCommand(sender, commandLabel, args);
                break;
            case "reload":
                PluginManagers.reloadPluginCommand(sender, commandLabel, args);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
                return false;
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> tabs = new ArrayList<>();
        if (args.length == 1 && (sender.isOp() || testPermission(sender))) {
            for (String param : params) {
                if (param.toLowerCase().startsWith(args[0].toLowerCase())) {
                    tabs.add(param);
                }
            }
        }
        if (args.length == 2 && (sender.isOp() || testPermission(sender))) {
            if (checkparam(args[0])) {
                for (Plugin pl : Bukkit.getServer().getPluginManager().getPlugins()) {
                    String plname = pl.getDescription().getName();
                    if (plname.toLowerCase().startsWith(args[1].toLowerCase())) {
                        tabs.add(plname);
                    }
                }
            }
        }
        return tabs;
    }
}