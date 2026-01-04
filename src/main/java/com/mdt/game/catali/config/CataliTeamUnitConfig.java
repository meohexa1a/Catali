package com.mdt.game.catali.config;

import arc.struct.Seq;
import lombok.experimental.UtilityClass;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;

import java.util.*;

import static mindustry.content.StatusEffects.*;
import static mindustry.content.UnitTypes.*;

@UtilityClass
public class CataliTeamUnitConfig {
    private static final Map<UnitType, Set<UnitType>> UNIT_EVOLUTIONS = new HashMap<>();

    private static final Map<UnitType, Integer> UNIT_CATCH_CHANCE = new HashMap<>();
    private static final Map<UnitType, Integer> UNIT_RESPAWN_TIME = new HashMap<>();

    private static final List<UnitType> BUFF_WHITELIST = List.of(
            reign, corvus, toxopid,
            eclipse, oct,
            omura, navanax,
            conquer, collaris, disrupt);

    private static final List<StatusEffect> BUFF_EFFECT_WHITELIST = List.of(
            fast, overdrive, overclock, shielded, boss);

    // !-----------------------------------------------------------------!

    public static Set<UnitType> getUnitEvolutions(UnitType unit) {
        return UNIT_EVOLUTIONS.getOrDefault(unit, Collections.emptySet());
    }

    public static int getUnitRespawnTime(UnitType unit) {
        return UNIT_RESPAWN_TIME.getOrDefault(unit, 0);
    }

    public static int getUnitCatchChance(UnitType unit) {
        return UNIT_CATCH_CHANCE.getOrDefault(unit, 0);
    }


    public static List<Unit> selectUnitsCanBeBuff(Seq<Unit> units) {
        return units.select(u -> BUFF_WHITELIST.contains(u.type)).list();
    }

    public static List<StatusEffect> selectBuffsCanBeApplied(Unit u) {
        var buffs = new ArrayList<>(BUFF_EFFECT_WHITELIST);

        buffs.removeIf(u::hasEffect);
        return buffs;
    }

    // !-----------------------------------------------------------------!

    static {
        UNIT_EVOLUTIONS.put(poly, Set.of(dagger, flare, retusa, nova, mega));
        UNIT_EVOLUTIONS.put(mega, Set.of(quad));
        UNIT_EVOLUTIONS.put(quad, Set.of(oct));

        UNIT_EVOLUTIONS.put(dagger, Set.of(mace, atrax, stell));
        UNIT_EVOLUTIONS.put(mace, Set.of(fortress));
        UNIT_EVOLUTIONS.put(fortress, Set.of(scepter));
        UNIT_EVOLUTIONS.put(scepter, Set.of(reign));

        UNIT_EVOLUTIONS.put(atrax, Set.of(spiroct));
        UNIT_EVOLUTIONS.put(spiroct, Set.of(arkyid));
        UNIT_EVOLUTIONS.put(arkyid, Set.of(toxopid));

        UNIT_EVOLUTIONS.put(stell, Set.of(locus));
        UNIT_EVOLUTIONS.put(locus, Set.of(precept));
        UNIT_EVOLUTIONS.put(precept, Set.of(vanquish));
        UNIT_EVOLUTIONS.put(vanquish, Set.of(conquer));

        UNIT_EVOLUTIONS.put(flare, Set.of(horizon, elude));
        UNIT_EVOLUTIONS.put(horizon, Set.of(zenith));
        UNIT_EVOLUTIONS.put(zenith, Set.of(antumbra));
        UNIT_EVOLUTIONS.put(antumbra, Set.of(eclipse));

        UNIT_EVOLUTIONS.put(elude, Set.of(avert));
        UNIT_EVOLUTIONS.put(avert, Set.of(obviate));
        UNIT_EVOLUTIONS.put(obviate, Set.of(quell));
        UNIT_EVOLUTIONS.put(quell, Set.of(disrupt));

        UNIT_EVOLUTIONS.put(retusa, Set.of(oxynoe, risso));
        UNIT_EVOLUTIONS.put(oxynoe, Set.of(cyerce));
        UNIT_EVOLUTIONS.put(cyerce, Set.of(aegires));
        UNIT_EVOLUTIONS.put(aegires, Set.of(navanax));

        UNIT_EVOLUTIONS.put(risso, Set.of(minke));
        UNIT_EVOLUTIONS.put(minke, Set.of(bryde));
        UNIT_EVOLUTIONS.put(bryde, Set.of(sei));
        UNIT_EVOLUTIONS.put(sei, Set.of(omura));

        UNIT_EVOLUTIONS.put(nova, Set.of(pulsar, merui));
        UNIT_EVOLUTIONS.put(pulsar, Set.of(quasar));
        UNIT_EVOLUTIONS.put(quasar, Set.of(vela));
        UNIT_EVOLUTIONS.put(vela, Set.of(corvus));

        UNIT_EVOLUTIONS.put(merui, Set.of(cleroi));
        UNIT_EVOLUTIONS.put(cleroi, Set.of(anthicus));
        UNIT_EVOLUTIONS.put(anthicus, Set.of(tecta));
        UNIT_EVOLUTIONS.put(tecta, Set.of(collaris));


        UNIT_RESPAWN_TIME.put(dagger, 60);
        UNIT_RESPAWN_TIME.put(nova, 60);
        UNIT_RESPAWN_TIME.put(crawler, 60);
        UNIT_RESPAWN_TIME.put(flare, 60);
        UNIT_RESPAWN_TIME.put(poly, 30);
        UNIT_RESPAWN_TIME.put(risso, 80);
        UNIT_RESPAWN_TIME.put(retusa, 80);
        UNIT_RESPAWN_TIME.put(stell, 80);
        UNIT_RESPAWN_TIME.put(merui, 80);
        UNIT_RESPAWN_TIME.put(mace, 100);
        UNIT_RESPAWN_TIME.put(pulsar, 100);
        UNIT_RESPAWN_TIME.put(atrax, 100);
        UNIT_RESPAWN_TIME.put(horizon, 100);
        UNIT_RESPAWN_TIME.put(minke, 120);
        UNIT_RESPAWN_TIME.put(oxynoe, 120);
        UNIT_RESPAWN_TIME.put(locus, 120);
        UNIT_RESPAWN_TIME.put(cleroi, 120);
        UNIT_RESPAWN_TIME.put(avert, 100);
        UNIT_RESPAWN_TIME.put(fortress, 140);
        UNIT_RESPAWN_TIME.put(quasar, 140);
        UNIT_RESPAWN_TIME.put(spiroct, 140);
        UNIT_RESPAWN_TIME.put(zenith, 140);
        UNIT_RESPAWN_TIME.put(mega, 60);
        UNIT_RESPAWN_TIME.put(bryde, 180);
        UNIT_RESPAWN_TIME.put(cyerce, 180);
        UNIT_RESPAWN_TIME.put(precept, 180);
        UNIT_RESPAWN_TIME.put(anthicus, 180);
        UNIT_RESPAWN_TIME.put(obviate, 140);
        UNIT_RESPAWN_TIME.put(scepter, 200);
        UNIT_RESPAWN_TIME.put(vela, 200);
        UNIT_RESPAWN_TIME.put(arkyid, 200);
        UNIT_RESPAWN_TIME.put(antumbra, 200);
        UNIT_RESPAWN_TIME.put(quad, 160);
        UNIT_RESPAWN_TIME.put(sei, 240);
        UNIT_RESPAWN_TIME.put(aegires, 240);
        UNIT_RESPAWN_TIME.put(vanquish, 240);
        UNIT_RESPAWN_TIME.put(tecta, 240);
        UNIT_RESPAWN_TIME.put(quell, 180);
        UNIT_RESPAWN_TIME.put(reign, 240);
        UNIT_RESPAWN_TIME.put(corvus, 240);
        UNIT_RESPAWN_TIME.put(toxopid, 240);
        UNIT_RESPAWN_TIME.put(eclipse, 240);
        UNIT_RESPAWN_TIME.put(oct, 160);
        UNIT_RESPAWN_TIME.put(omura, 300);
        UNIT_RESPAWN_TIME.put(navanax, 300);
        UNIT_RESPAWN_TIME.put(conquer, 300);
        UNIT_RESPAWN_TIME.put(collaris, 300);
        UNIT_RESPAWN_TIME.put(disrupt, 220);


        UNIT_CATCH_CHANCE.put(dagger, 50);
        UNIT_CATCH_CHANCE.put(nova, 50);
        UNIT_CATCH_CHANCE.put(crawler, 0);
        UNIT_CATCH_CHANCE.put(flare, 50);
        UNIT_CATCH_CHANCE.put(poly, 75);
        UNIT_CATCH_CHANCE.put(risso, 40);
        UNIT_CATCH_CHANCE.put(retusa, 50);
        UNIT_CATCH_CHANCE.put(stell, 40);
        UNIT_CATCH_CHANCE.put(merui, 40);
        UNIT_CATCH_CHANCE.put(mace, 40);
        UNIT_CATCH_CHANCE.put(pulsar, 40);
        UNIT_CATCH_CHANCE.put(atrax, 40);
        UNIT_CATCH_CHANCE.put(horizon, 40);
        UNIT_CATCH_CHANCE.put(minke, 25);
        UNIT_CATCH_CHANCE.put(oxynoe, 25);
        UNIT_CATCH_CHANCE.put(locus, 25);
        UNIT_CATCH_CHANCE.put(cleroi, 25);
        UNIT_CATCH_CHANCE.put(avert, 30);
        UNIT_CATCH_CHANCE.put(fortress, 30);
        UNIT_CATCH_CHANCE.put(quasar, 30);
        UNIT_CATCH_CHANCE.put(spiroct, 30);
        UNIT_CATCH_CHANCE.put(zenith, 30);
        UNIT_CATCH_CHANCE.put(mega, 50);
        UNIT_CATCH_CHANCE.put(bryde, 15);
        UNIT_CATCH_CHANCE.put(cyerce, 15);
        UNIT_CATCH_CHANCE.put(precept, 15);
        UNIT_CATCH_CHANCE.put(anthicus, 15);
        UNIT_CATCH_CHANCE.put(obviate, 20);
        UNIT_CATCH_CHANCE.put(scepter, 20);
        UNIT_CATCH_CHANCE.put(vela, 20);
        UNIT_CATCH_CHANCE.put(arkyid, 20);
        UNIT_CATCH_CHANCE.put(antumbra, 20);
        UNIT_CATCH_CHANCE.put(quad, 35);
        UNIT_CATCH_CHANCE.put(sei, 5);
        UNIT_CATCH_CHANCE.put(aegires, 10);
        UNIT_CATCH_CHANCE.put(vanquish, 10);
        UNIT_CATCH_CHANCE.put(tecta, 5);
        UNIT_CATCH_CHANCE.put(quell, 20);
        UNIT_CATCH_CHANCE.put(reign, 10);
        UNIT_CATCH_CHANCE.put(corvus, 10);
        UNIT_CATCH_CHANCE.put(toxopid, 10);
        UNIT_CATCH_CHANCE.put(eclipse, 10);
        UNIT_CATCH_CHANCE.put(oct, 30);
        UNIT_CATCH_CHANCE.put(omura, 5);
        UNIT_CATCH_CHANCE.put(navanax, 5);
        UNIT_CATCH_CHANCE.put(conquer, 5);
        UNIT_CATCH_CHANCE.put(collaris, 5);
        UNIT_CATCH_CHANCE.put(disrupt, 15);
    }
}
