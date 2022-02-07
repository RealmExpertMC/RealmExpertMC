package br.com.realmexpert.forge;

import br.com.realmexpert.configuration.RealmExpertMCConfig;

/**
 * @author Mgazul
 * @date 2020/4/15 0:30
 */
public class ModCompatibleFixUtils {

    /**
     * Fix NPE caused by persistent transmission when dimension does not exist
     * Adding the dimension id to RealmExpertMC.yml/world.dimensionsNotLoaded triggers the error
     *
     * @param dim
     */
    public static void fixPortalEnter(int dim) {
        for (Integer dimyml : RealmExpertMCConfig.instance.dimensionsNotLoaded) {
            if (dimyml.intValue() == dim) {
                return;
            }
        }
    }
}
