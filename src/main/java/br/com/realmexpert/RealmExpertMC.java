package br.com.realmexpert;

import br.com.realmexpert.bukkit.AutoDeletePlugins;
import br.com.realmexpert.configuration.RealmExpertMCConfigUtil;
import br.com.realmexpert.forge.AutoDeleteMods;
import br.com.realmexpert.forge.FastWorkBenchConf;
import br.com.realmexpert.libraries.CustomLibraries;
import br.com.realmexpert.libraries.DefaultLibraries;
import br.com.realmexpert.network.download.DownloadJava;
import br.com.realmexpert.network.download.UpdateUtils;
import br.com.realmexpert.util.EulaUtil;
import br.com.realmexpert.util.i18n.Message;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.apache.logging.log4j.Logger;

public class RealmExpertMC {
    public static final String NAME = "RealmExpertMC";
    public static Logger LOGGER;
    public static ArrayList<String> mainArgs = null;

    public static String getVersion() {
        return (RealmExpertMC.class.getPackage().getImplementationVersion() != null) ? RealmExpertMC.class.getPackage().getImplementationVersion() : "unknown";
    }

    public static void main(String[] args) throws Throwable {
        mainArgs = new ArrayList<>(Arrays.asList(args));
        RealmExpertMCConfigUtil.copyRealmExpertMCConfig();
        if (Float.parseFloat(System.getProperty("java.class.version")) != 52.0 || RealmExpertMCConfigUtil.bRealmExpertMC("use_custom_java8", "false"))
            DownloadJava.run();
        if (System.getProperty("log4j.configurationFile") == null) {
            System.setProperty("log4j.configurationFile", "log4j2_RealmExpertMC.xml");
        }

        if (RealmExpertMCConfigUtil.bRealmExpertMC("check_libraries")) DefaultLibraries.run();
        DefaultLibraries.loadDefaultLibs();
        CustomLibraries.loadCustomLibs();

        if (RealmExpertMCConfigUtil.bRealmExpertMC("check_update")) UpdateUtils.versionCheck();

        if(mainArgs.contains("-noserver"))
			System.exit(0); //-noserver -> Do not run the Minecraft server, only let the installation running.

        Class.forName("com.google.gson.internal.bind.TypeAdapters$EnumTypeAdapter").getClassLoader();

        if (!EulaUtil.hasAcceptedEULA()) {
            System.out.println(Message.getString("eula"));
            while (!"true".equals(new Scanner(System.in).next()));
            EulaUtil.writeInfos();
        }

        if (!RealmExpertMCConfigUtil.bRealmExpertMC("disable_plugins_blacklist", "false")) AutoDeletePlugins.jar();
        if (!RealmExpertMCConfigUtil.bRealmExpertMC("disable_mods_blacklist", "false")) AutoDeleteMods.jar();
        FastWorkBenchConf.changeConf();
        Class.forName("net.minecraftforge.fml.relauncher.ServerLaunchWrapper").getDeclaredMethod("main", String[].class).invoke(null, new Object[]{args});
    }
}
