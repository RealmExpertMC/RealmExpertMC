package br.com.realmexpert;

import br.com.realmexpert.common.async.RealmExpertMCThreadBox;
import br.com.realmexpert.configuration.RealmExpertMCConfig;
import br.com.realmexpert.util.i18n.Message;
import java.util.concurrent.TimeUnit;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.spigotmc.TicksPerSecondCommand;


public class WatchRealmExpertMC implements Runnable {

    private static long Time = 0L;
    private static long WarnTime = 0L;

    @Override
    public void run() {
        long curTime = System.currentTimeMillis();
        if (Time > 0L && curTime - Time > 2000L && curTime - WarnTime > 30000L) {
            WarnTime = curTime;
            RealmExpertMC.LOGGER.warn(Message.getString("watchRealmExpertMC.1"));

            double[] tps = Bukkit.getTPS();
            String[] tpsAvg = new String[tps.length];
            for (int i = 0; i < tps.length; i++) {
                tpsAvg[i] = TicksPerSecondCommand.format(tps[i]);
            }

            RealmExpertMC.LOGGER.warn(Message.getFormatString("watchRealmExpertMC.2", new Object[]{String.valueOf(curTime - Time), StringUtils.join(tpsAvg, ", ")}));
            RealmExpertMC.LOGGER.warn(Message.getString("watchRealmExpertMC.3"));
            for (StackTraceElement stack : MinecraftServer.getServerInst().primaryThread.getStackTrace()) {
                RealmExpertMC.LOGGER.warn(Message.getString("watchRealmExpertMC.4") + stack);
            }
            RealmExpertMC.LOGGER.warn(Message.getString("watchRealmExpertMC.1"));
        }
    }

    public static void start() {
        if (isEnable()) {
            RealmExpertMCThreadBox.WatchRealmExpertMC.scheduleAtFixedRate(new WatchRealmExpertMC(), 30000L, 500L, TimeUnit.MILLISECONDS);
        }
    }

    public static void update() {
        if (isEnable()) {
            Time = System.currentTimeMillis();
        }
    }

    public static void stop() {
        if (isEnable()) {
            RealmExpertMCThreadBox.WatchRealmExpertMC.shutdown();
        }
    }

    public static boolean isEnable() {
        return RealmExpertMCConfig.instance.watchdog_RealmExpertMC.getValue();
    }
}