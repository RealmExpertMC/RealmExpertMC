package br.com.realmexpert.util.i18n;

import br.com.realmexpert.configuration.RealmExpertMCConfigUtil;
import java.io.File;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class Message {
    public static ResourceBundle rb = ResourceBundle.getBundle("assets.RealmExpertMC.lang.message", new Locale(getLanguage(), getCountry()), new UTF8Control());

    public static String getString(String key) {
        return rb.getString(key);
    }

    public static String getFormatString(String key, Object[] f) {
        return new MessageFormat(getString(key)).format(f);
    }

    public static String getLocale(int key) {
        File f = new File("RealmExpertMC-config", "RealmExpertMC.yml");
        String locale = RealmExpertMCConfigUtil.getString(f, "lang:", "xx_XX");
        if (locale.length() == 5) {
            String language = locale.substring(0, 2);
            String country = locale.substring(3, 5);
            if (key == 1) return language;
            if (key == 2) return country;
        }
        return "xx_XX";
    }

    public static String getLanguage() {
        return getLocale(1);
    }

    public static String getCountry() {
        return getLocale(2);
    }

    public static String getLocale() {
        return Message.rb.getLocale().toString();
    }

    public static boolean isCN() {
        TimeZone timeZone = TimeZone.getDefault();
        return timeZone.getID().equals("Asia/Shanghai") || Message.getLocale().contains("CN") || Message.getCountry().contains("CN");
    }
}