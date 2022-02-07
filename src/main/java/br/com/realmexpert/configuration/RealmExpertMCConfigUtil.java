package br.com.realmexpert.configuration;

import br.com.realmexpert.RealmExpertMC;
import br.com.realmexpert.util.NumberUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

/**
 * Only used before the libraries file is loaded, yaml should be used later
 */
public class RealmExpertMCConfigUtil {

    public static File RealmExpertMCyml = new File("RealmExpertMC-config", "RealmExpertMC.yml");
    private static HashMap<String, String> argsConfig = new HashMap<>();

    public static String getString(String s, String key, String defaultreturn) {
    	String _key = key.replace(":", "");
    	if(argsConfig.containsKey(_key)) return argsConfig.get(_key);
        if (s.contains(key)) {
            String string = s.substring(s.indexOf(key));
            String s1 = (string.substring(string.indexOf(": ") + 2));
            String[] ss = s1.split("\n");
            return ss[0].trim().replace("'", "").replace("\"", "");
        } else return defaultreturn;
    }

    public static String getString(File f, String key, String defaultreturn) {
        try {
            StringBuilder s = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String l;
                while ((l = br.readLine()) != null) if (!l.startsWith("#"))
                    s.append(l).append("\n");
            }
            return getString(s.toString(), key, defaultreturn);
        } catch (IOException e) {
            return defaultreturn;
        }
    }

    public static boolean getBoolean(File f, String key) {
        return Boolean.parseBoolean(getString(f, key, "true"));
    }

    public static boolean getBoolean(File f, String key, String b) {
        return Boolean.parseBoolean(getString(f, key, b));
    }

    public static int getInt(File f, String key, String defaultreturn) {
        String s = getString(f, key, defaultreturn);
        if (NumberUtils.isInteger(s)) return Integer.parseInt(s);
        return Integer.parseInt(defaultreturn);
    }

    public static void copyRealmExpertMCConfig() {
        try {
            if (!RealmExpertMCyml.exists()) {
                RealmExpertMCyml.mkdirs();
                Files.copy(RealmExpertMC.class.getClassLoader().getResourceAsStream("configurations/RealmExpertMC.yml"), RealmExpertMCyml.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            System.out.println("Failed to copy RealmExpertMC config file !");
        }

        for(String arg : RealmExpertMC.mainArgs)
        	if(arg.contains("=")) {
        		String[] spl = arg.split("=");
				argsConfig.put(spl[0], spl[1]);
			}
    }

    public static boolean bRealmExpertMC(String key) {
        return getBoolean(RealmExpertMCyml, key + ":");
    }

    public static boolean bRealmExpertMC(String key, String defaultReturn) {
        return getBoolean(RealmExpertMCyml, key + ":", defaultReturn);
    }

    public static String sRealmExpertMC(String key, String defaultReturn) {
        return getString(RealmExpertMCyml, key, defaultReturn);
    }
}
