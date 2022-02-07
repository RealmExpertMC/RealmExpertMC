package org.bukkit.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.plugin.Plugin;
import br.com.realmexpert.RealmExpertMC;

public class PluginCommandYamlParser {

    public static List<Command> parse(Plugin plugin) {
        List<Command> pluginCmds = new ArrayList<>();

        Map<String, Map<String, Object>> map = plugin.getDescription().getCommands();

        if (map == null) {
            return pluginCmds;
        }

        for (Entry<String, Map<String, Object>> entry : map.entrySet()) {
            if (entry.getKey().contains(":")) {
                RealmExpertMC.LOGGER.error("Could not load command " + entry.getKey() + " for plugin " + plugin.getName() + ": Illegal Characters");
                continue;
            }
            Command newCmd = new PluginCommand(entry.getKey(), plugin);
            Object description = entry.getValue().get("description");
            Object usage = entry.getValue().get("usage");
            Object aliases = entry.getValue().get("aliases");
            Object permission = entry.getValue().get("permission");
            Object permissionMessage = entry.getValue().get("permission-message");

            if (description != null) {
                newCmd.setDescription(description.toString());
            }

            if (usage != null) {
                newCmd.setUsage(usage.toString());
            }

            if (aliases != null) {
                List<String> aliasList = new ArrayList<>();

                if (aliases instanceof List) {
                    for (Object o : (List<?>) aliases) {
                        if (o.toString().contains(":")) {
                            RealmExpertMC.LOGGER.error("Could not load alias " + o.toString() + " for plugin " + plugin.getName() + ": Illegal Characters");
                            continue;
                        }
                        aliasList.add(o.toString());
                    }
                } else {
                    if (aliases.toString().contains(":")) {
                        RealmExpertMC.LOGGER.error("Could not load alias " + aliases.toString() + " for plugin " + plugin.getName() + ": Illegal Characters");
                    } else {
                        aliasList.add(aliases.toString());
                    }
                }

                newCmd.setAliases(aliasList);
            }

            if (permission != null) {
                newCmd.setPermission(permission.toString());
            }

            if (permissionMessage != null) {
                newCmd.setPermissionMessage(permissionMessage.toString());
            }

            pluginCmds.add(newCmd);
        }
        return pluginCmds;
    }
}
