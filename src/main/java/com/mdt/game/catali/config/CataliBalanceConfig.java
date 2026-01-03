package com.mdt.game.catali.config;

import mindustry.Vars;
import mindustry.content.UnitTypes;
import mindustry.game.Team;

public class CataliBalanceConfig {

    // !-------------------------------------------!

    public static void onModeLoaded() {
        Vars.content.units().forEach(u -> u.flying = u.naval || u.flying);

        UnitTypes.omura.weapons.get(0).bullet.damage /= 2;
        UnitTypes.collaris.speed /= 2;
    }

    public static void onModeUnload() {
        Vars.content.units().forEach(u -> u.flying = !u.naval && u.flying);

        for (var t : Team.all) {
            t.rules().unitHealthMultiplier = 1;
            t.rules().unitDamageMultiplier = 1;
        }

        UnitTypes.omura.weapons.get(0).bullet.damage *= 2;
        UnitTypes.collaris.speed *= 2;
    }
}
