package com.mdt.game.catali.config;

import mindustry.content.Items;
import mindustry.type.UnitType;

import java.util.List;

import static mindustry.content.UnitTypes.*;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CataliGeneralConfig {
    public static final int BEGINNER_LEVEL_LEAST_THAN = 30;
    public static final int BEGINNER_EXP_MULTIPLIER = 4;

    public static final int EXP_FROM_IDLE_UNIT = 40;
    public static final List<UnitType> IDLE_UNITS = List.of(oct, quell, disrupt);

    private static final List<String> RANKING_EMOJI = List.of(
        Items.copper.emoji(),
        Items.lead.emoji(),
        Items.graphite.emoji(),
        Items.metaglass.emoji(),
        Items.silicon.emoji(),
        Items.titanium.emoji(),
        Items.beryllium.emoji(),
        Items.tungsten.emoji(),
        Items.plastanium.emoji(),
        Items.oxide.emoji(),
        Items.thorium.emoji(),
        Items.carbide.emoji(),
        Items.phaseFabric.emoji(),
        Items.surgeAlloy.emoji(),
        Items.pyratite.emoji(),
        Items.blastCompound.emoji());

    // !-------------------------------------------!

    public static String getRankEmoji(long level) {
        return RANKING_EMOJI.get(Math.min(Math.max((int) level / 10, 0), RANKING_EMOJI.size() - 1));
    }
}
