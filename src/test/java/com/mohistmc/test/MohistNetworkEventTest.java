package br.com.realmexpert.test;

import br.com.realmexpert.api.event.RealmExpertMCNetworkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RealmExpertMCNetworkEventTest {

    @SubscribeEvent
    public void test(RealmExpertMCNetworkEvent event) {
        if (event.getUrl().toString().contains("baidu.com")) {
            event.setCancelled(true);
            event.setMsg("Biu Biu Biu ...");
        }
    }
}
