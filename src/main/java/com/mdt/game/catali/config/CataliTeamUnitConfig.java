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
    public enum UnitTier {
        Tier1,
        Tier2,
        Tier3,
        Tier4,
        Tier5,
        OverPowerTier5;
    }

    private static final EnumMap<UnitTier, Set<UnitType>> UNIT_TIER = new EnumMap<>(UnitTier.class) {{
        put(UnitTier.Tier1, Set.of(dagger, nova, flare, poly));
        put(UnitTier.Tier2, Set.of(mace, pulsar, atrax, horizon, risso, retusa, stell, merui, elude));
        put(UnitTier.Tier3, Set.of(fortress, quasar, spiroct, zenith, mega, minke, oxynoe, locus, cleroi, avert));
        put(UnitTier.Tier4, Set.of(scepter, vela, arkyid, antumbra, quad,  bryde, cyerce, precept, anthicus, obviate));
        put(UnitTier.Tier5, Set.of(reign, corvus, toxopid, eclipse, oct, sei, aegires, vanquish, tecta, quell));
        put(UnitTier.OverPowerTier5, Set.of(omura, navanax, conquer, collaris, disrupt));
    }};

    private static final Map<UnitType, Integer> UNIT_CATCH_CHANCE = new HashMap<>();
    private static final Map<UnitType, Integer> UNIT_RESPAWN_TIME = new HashMap<>();

    private static final Set<UnitType> BUFF_WHITELIST = Set.copyOf(
        UNIT_TIER.get(UnitTier.OverPowerTier5),
        UNIT_TIER.get(UnitTier.Tier5)
    );

    private static final List<StatusEffect> BUFF_EFFECT_WHITELIST = List.of(
        fast, overdrive, overclock, shielded, boss);

    // !-----------------------------------------------------------------!

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
