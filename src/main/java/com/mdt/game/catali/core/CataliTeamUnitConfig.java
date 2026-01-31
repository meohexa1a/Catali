package com.mdt.game.catali.core;

import arc.struct.Seq;

import com.mdt.game.catali.utils.CataliCommonUtils;
import com.mdt.game.catali.enums.UnitTier;

import lombok.experimental.UtilityClass;

import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;

import java.util.*;

import static com.mdt.game.catali.utils.CataliCommonUtils.UNIT_TIER;
import static mindustry.content.StatusEffects.*;

@UtilityClass
public class CataliTeamUnitConfig {
    public static final int RESPAWN_START_SLOT = 3;

    private static final int RESPAWN_PER_TIER = 60;
    private static final float BASE_CATCH_CHANCE = 0.5f;

    private static final Set<UnitType> BUFF_WHITELIST = new HashSet<>() {{
        addAll(UNIT_TIER.get(UnitTier.Tier5));
        addAll(UNIT_TIER.get(UnitTier.OverPowerTier5));
    }};

    public static final List<StatusEffect> BUFF_EFFECT_WHITELIST = List.of(
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
        var tier = CataliCommonUtils.getUnitTier(unit);
        if (tier == null) return 0;

        return (tier.ordinal() + 1) * RESPAWN_PER_TIER;
    }

    public static float getUnitCatchChance(UnitType unit) {
        var tier = CataliCommonUtils.getUnitTier(unit);
        if (tier == null) return 0;

        return BASE_CATCH_CHANCE / (1 + tier.ordinal());
    }
}
