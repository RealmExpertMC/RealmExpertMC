package br.com.realmexpert.bukkit.nms;

import br.com.realmexpert.RealmExpertMC;
import br.com.realmexpert.bukkit.nms.utils.Decoder;
import br.com.realmexpert.bukkit.nms.utils.Downloader;
import br.com.realmexpert.util.i18n.Message;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Mgazul
 * @date 2020/6/5 0:44
 */
public class MappingFix {
    private static int percentage = 0;
    private static File lib = new File("./libraries/com/RealmExpertMC/mappings/nms.srg");

    public static void init() throws Exception {
        //specify the dir
        File old = new File("./libraries/red/RealmExpertMC/mappings/nms.srg");
        if (old.exists() && !lib.exists()) old.renameTo(lib);

        if (!lib.exists() || lib.length() < 4000000) {
            File joined = new File("joined.srg");
            //start download
            new Downloader().execute();
            if (lib.getParentFile().mkdirs())
                System.out.println(Message.getString("mappingfix.created.mappings"));
            if (lib.createNewFile())
                System.out.println(Message.getString("mappingfix.created.nms"));
            //start decode
            System.out.println(Message.getString("mappingfix.decoding.start"));
            System.out.println("#################################################\n" +
                    "                 Powered by MCP                  \n" +
                    "             http://modcoderpack.com             \n" +
                    "     by: Searge, ProfMobius, R4wk, ZeuX          \n" +
                    "     Fesh0r, IngisKahn, bspkrs, LexManos         \n" +
                    "#################################################");
            System.out.println(Message.getString("mappingfix.decoding.info"));
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (percentage != Math.round((float) lib.length() / 4000000 * 100))
                        System.out.println(Message.getString("mapping.decoding.progress") + " - " + percentage + "%");
                    percentage = Math.round((float) lib.length() / 4000000 * 100);
                }
            }, 3000, 3000);
            long startTime = System.currentTimeMillis();
            new Decoder().Decode(joined, lib);
            t.cancel();
            System.out.println(Message.getFormatString("mappingfix.decoding.end", new Object[]{(System.currentTimeMillis() - startTime) / 1000}));
            System.gc();
            Thread.sleep(100);
            joined.delete();
        }
    }

    public static void copyMappings() {
        try {
            if (!lib.exists()) {
                lib.mkdirs();
                Files.copy(RealmExpertMC.class.getClassLoader().getResourceAsStream("mappings/nms.srg"), lib.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            System.out.println("Failed to copy nms file !");
        }
    }
}
