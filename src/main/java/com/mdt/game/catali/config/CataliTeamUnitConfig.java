package com.mdt.game.catali.config;

import arc.struct.Seq;

import lombok.experimental.UtilityClass;

import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static mindustry.content.StatusEffects.*;
import static mindustry.content.UnitTypes.*;

@UtilityClass
public class CataliTeamUnitConfig {
    private static final int RESPAWN_PER_TIER = 60;
    private static final float BASE_CATCH_CHANCE = 0.5f;

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
        put(UnitTier.Tier4, Set.of(scepter, vela, arkyid, antumbra, quad, bryde, cyerce, precept, anthicus, obviate));
        put(UnitTier.Tier5, Set.of(reign, corvus, toxopid, eclipse, oct, sei, aegires, vanquish, tecta, quell));
        put(UnitTier.OverPowerTier5, Set.of(omura, navanax, conquer, collaris, disrupt));
    }};

    private static final EnumMap<UnitTier, List<UnitType>> UNIT_TIER_LIST = new EnumMap<>(UnitTier.class) {{
        put(UnitTier.Tier1, List.of(dagger, nova, flare, poly));
        put(UnitTier.Tier2, List.of(mace, pulsar, atrax, horizon, risso, retusa, stell, merui, elude));
        put(UnitTier.Tier3, List.of(fortress, quasar, spiroct, zenith, mega, minke, oxynoe, locus, cleroi, avert));
        put(UnitTier.Tier4, List.of(scepter, vela, arkyid, antumbra, quad, bryde, cyerce, precept, anthicus, obviate));
        put(UnitTier.Tier5, List.of(reign, corvus, toxopid, eclipse, oct, sei, aegires, vanquish, tecta, quell));
        put(UnitTier.OverPowerTier5, List.of(omura, navanax, conquer, collaris, disrupt));
    }};

    private static final Set<UnitType> BUFF_WHITELIST = new HashSet<>() {{
        addAll(UNIT_TIER.get(UnitTier.Tier5));
        addAll(UNIT_TIER.get(UnitTier.OverPowerTier5));
    }};

    private static final List<StatusEffect> BUFF_EFFECT_WHITELIST = List.of(
        fast, overdrive, overclock, shielded, boss);

    // !-----------------------------------------------------------------!

    public static List<Unit> selectUnitsCanBeBuff(Seq<Unit> units) {
        return units.select(u -> {
            if (!BUFF_WHITELIST.contains(u.type)) return false;
            for (var effect : BUFF_EFFECT_WHITELIST) if (u.hasEffect(effect)) return false;
            return true;
        }).list();
    }

    public static int getUnitRespawnTime(UnitType unit) {
        var tier = getTier(unit);
        if (tier == null) return 0;

        return (tier.ordinal() + 1) * RESPAWN_PER_TIER;
    }

    public static float getUnitCatchChance(UnitType unit) {
        var tier = getTier(unit);
        if (tier == null) return 0;

        return BASE_CATCH_CHANCE / (1 + tier.ordinal());
    }

    public static List<UnitType> randomUnits(int t1, int t2, int t3, int t4, int t5, int ot5) {
        var result = new ArrayList<UnitType>();

        pick(result, UnitTier.Tier1, t1);
        pick(result, UnitTier.Tier2, t2);
        pick(result, UnitTier.Tier3, t3);
        pick(result, UnitTier.Tier4, t4);
        pick(result, UnitTier.Tier5, t5);
        pick(result, UnitTier.OverPowerTier5, ot5);

        return result;
    }

    private static void pick(List<UnitType> out, UnitTier tier, int amount) {
        var tierList = UNIT_TIER_LIST.get(tier);
        for (int i = 0; i < amount; i++)
            out.add(tierList.get(ThreadLocalRandom.current().nextInt(tierList.size())));
    }

    private static UnitTier getTier(UnitType unit) {
        for (var entry : UNIT_TIER.entrySet())
            if (entry.getValue().contains(unit)) return entry.getKey();

        return null;
    }

}
