package br.com.realmexpert.network.download;


import br.com.realmexpert.configuration.RealmExpertMCConfigUtil;

public enum DownloadSource {

    RealmExpertMC("https://maven.RealmExpertMC.com/"),
    CHINA("http://s1.devicloud.cn:25119/"),
    GITHUB("https://mavenmirror.RealmExpertMC.com/");

    String url;

    DownloadSource(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public static DownloadSource get(){
        String ds = RealmExpertMCConfigUtil.sRealmExpertMC("libraries_downloadsource", "RealmExpertMC");
        for (DownloadSource me : DownloadSource.values()) {
            if (me.name().equalsIgnoreCase(ds))
                return me;
        }
        return DownloadSource.RealmExpertMC;
    }
}
