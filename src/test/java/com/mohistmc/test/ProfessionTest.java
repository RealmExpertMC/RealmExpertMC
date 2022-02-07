package br.com.realmexpert.test;

import br.com.realmexpert.api.EntityAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;

public class ProfessionTest {

    public static void killMad_Scientist(Entity entity) {
        if (entity instanceof Villager) {
            if (((Villager) entity).getProfession().name().equals("MATTEROVERDRIVE.MAD_SCIENTIST")) {
                entity.remove();
            }
        }
    }

    public static void killALlModVillager(Entity entity) {
        if (EntityAPI.isForgeVillager(entity)) {
            entity.remove();
        }
    }
}
