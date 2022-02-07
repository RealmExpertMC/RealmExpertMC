package br.com.realmexpert.test;

import br.com.realmexpert.api.CooldownAPI;

public class CooldownTest {

    public static void main(String[] args) {

        CooldownAPI cooldown = new CooldownAPI("RealmExpertMC", "cd", 15);
        if (!CooldownAPI.isInCooldown("RealmExpertMC", "cd")) {
            cooldown.start();
        } else {
            System.out.println("Remaining cooldown: " + CooldownAPI.getTimeLeft("RealmExpertMC", "cd") + "s");
        }
    }
}
