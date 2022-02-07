package br.com.realmexpert.network;

import br.com.realmexpert.RealmExpertMC;
import br.com.realmexpert.api.event.RealmExpertMCNetworkEvent;
import br.com.realmexpert.configuration.RealmExpertMCConfig;
import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.realmexpert.util.IOUtil;
import org.bukkit.Bukkit;

public class RealmExpertMCProxySelector extends ProxySelector {

    private final ProxySelector defaultSelector;
    private List<String> intercepts = new ArrayList<>();

    public RealmExpertMCProxySelector(ProxySelector defaultSelector) {
        this.defaultSelector = defaultSelector;
    }

    @Override
    public List<Proxy> select(URI uri) {
        if (RealmExpertMCConfig.getBoolean0("RealmExpertMC.networkmanager.debug", false)) {
            RealmExpertMC.LOGGER.error(uri.toString());
        }

        String uriString = uri.toString();
        String defaultMsg = "[NetworkManager] Network protection and blocked by network rules!";
        boolean intercept = false;

        /*
        if (uriString.startsWith("socket")) {
            return this.defaultSelector.select(uri);
        }
         */
        if (intercepts.isEmpty()) {
            intercepts = RealmExpertMCConfig.getStringList0("RealmExpertMC.networkmanager.intercept", new ArrayList<>());
        }
        for (String config_uri : intercepts) {
            if (uriString.contains(config_uri)) {
                intercept = true;
            }
        }
        if (Bukkit.getServer() != null && Bukkit.getServer().isPrimaryThread()) {
            RealmExpertMCNetworkEvent event = new RealmExpertMCNetworkEvent(uri, defaultMsg);
            Bukkit.getPluginManager().callEvent(event);
            event.setCancelled(intercept);
            if (event.isCancelled()) {
                intercept = true;
            }
        }
        if (intercept) {
            try {
                IOUtil.throwException(new IOException(defaultMsg));
            } catch (Throwable throwable) {
                RealmExpertMC.LOGGER.error(throwable.getMessage());
            }
        } else {
            return this.defaultSelector.select(uri);
        }
        return null;
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        this.defaultSelector.connectFailed(uri, sa, ioe);
    }

    public ProxySelector getDefaultSelector() {
        return this.defaultSelector;
    }
}
