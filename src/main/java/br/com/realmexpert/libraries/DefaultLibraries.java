package br.com.realmexpert.libraries;

import br.com.realmexpert.configuration.RealmExpertMCConfigUtil;
import static br.com.realmexpert.configuration.RealmExpertMCConfigUtil.bRealmExpertMC;

import br.com.realmexpert.network.download.DownloadSource;
import br.com.realmexpert.network.download.UpdateUtils;
import br.com.realmexpert.util.JarLoader;
import br.com.realmexpert.util.JarTool;
import br.com.realmexpert.util.MD5Util;
import br.com.realmexpert.util.i18n.Message;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultLibraries {
    public static HashMap<String, String> fail = new HashMap<>();

    public static void run() throws Exception {
        System.out.println(Message.getString("libraries.checking.start"));
        String url = DownloadSource.get().getUrl();
        LinkedHashMap<File, String> libs = getDefaultLibs();
		AtomicLong currentSize = new AtomicLong();
		Set<File> defaultLibs = new LinkedHashSet<>();
		for (File lib : getDefaultLibs().keySet()) {
			if(lib.exists() && RealmExpertMCConfigUtil.getString(RealmExpertMCConfigUtil.RealmExpertMCyml, "libraries_black_list:", "xxxxx").contains(lib.getName())) {
				continue;
			}
			if(lib.getName().contains("launchwrapper")) {
				File customLaunchwrapper = new File(JarTool.getJarDir() + "/libraries/customize_libraries/launchwrapper-fccb-1.12.jar");
				if(bRealmExpertMC("forge_can_call_bukkit")) {
					if(lib.exists()) lib.delete();
					if(!customLaunchwrapper.exists() || !MD5Util.md5CheckSum(customLaunchwrapper, "8f121345f96b77620fcfa69a4330947a"))
						defaultLibs.add(customLaunchwrapper);
					continue;
				} else if(customLaunchwrapper.exists()) customLaunchwrapper.delete();
			}
			if(lib.exists() && MD5Util.md5CheckSum(lib, libs.get(lib))){
				currentSize.addAndGet(lib.length());
				continue;
			}
			defaultLibs.add(lib);
		}

        for (File lib : defaultLibs) {
                lib.getParentFile().mkdirs();
                String u = url + "libraries/" + lib.getAbsolutePath().replaceAll("\\\\", "/").split("/libraries/")[1];
				System.out.println(Message.getString("libraries.global.percentage") + Math.round(currentSize.get() * 100 / 70000000d) + "%"); //Global percentage

                try {
                    UpdateUtils.downloadFile(u, lib);
					currentSize.addAndGet(lib.length());
                    fail.remove(u.replace(url, ""));
                } catch (Exception e) {
                    System.out.println(Message.getFormatString("file.download.nook", new Object[]{u}));
                    lib.delete();
					fail.put(u.replace(url, ""), lib.getAbsolutePath());
                }
        }
        /*FINISHED | RECHECK IF A FILE FAILED*/
        if (!fail.isEmpty()) {
			run();
        } else System.out.println(Message.getString("libraries.checking.end"));
    }

    public static LinkedHashMap<File, String> getDefaultLibs() throws Exception {
        LinkedHashMap<File, String> temp = new LinkedHashMap<>();
        BufferedReader b = new BufferedReader(new InputStreamReader(DefaultLibraries.class.getClassLoader().getResourceAsStream("RealmExpertMC_libraries.txt")));
        String str;
        while ((str = b.readLine()) != null) {
            String[] s = str.split("\\|");
            temp.put(new File(JarTool.getJarDir() + "/" + s[0]), s[1]);
        }
        b.close();
        return temp;
    }

    public static void loadDefaultLibs() throws Exception {
        for (File lib : getDefaultLibs().keySet())
            if(lib.exists() && !RealmExpertMCConfigUtil.getString(RealmExpertMCConfigUtil.RealmExpertMCyml, "libraries_black_list:", "xxxxx").contains(lib.getName()))
                JarLoader.loadjar(lib.getAbsolutePath());
    }
}
