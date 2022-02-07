package br.com.realmexpert.forge;

import br.com.realmexpert.configuration.RealmExpertMCConfig;
import java.util.Arrays;

public class RealmExpertMCForgeUtils {

    // Some mods such as Twilight Forest listen for specific events as their WorldProvider loads to hotload its dimension. This prevents this from happening so MV can create worlds using the same provider without issue.
    public static boolean craftWorldLoading = false;

    public static boolean modsblacklist(String modslist) {
        String[] clientMods = modslist.split(",");
        if (RealmExpertMCConfig.instance.modsblacklistenable.getValue() && !RealmExpertMCConfig.instance.modswhitelistenable.getValue()) {
            return Arrays.asList(clientMods).containsAll(Arrays.asList(RealmExpertMCConfig.instance.modsblacklist.getValue().split(",")));
        }
        return false;
    }

    public static boolean modswhittelist(String modslist) {
        String[] clientMods = modslist.split(",");
        if (!RealmExpertMCConfig.instance.modsblacklistenable.getValue() && RealmExpertMCConfig.instance.modswhitelistenable.getValue()) {
            if (RealmExpertMCConfig.instance.modsnumber.getValue() >= 0)
                return Arrays.asList(clientMods).containsAll(Arrays.asList(RealmExpertMCConfig.instance.modswhitelist.getValue().split(","))) && clientMods.length == RealmExpertMCConfig.instance.modsnumber.getValue();
            else
                return Arrays.asList(clientMods).containsAll(Arrays.asList(RealmExpertMCConfig.instance.modswhitelist.getValue().split(",")));
        }
        return true;
    }
}