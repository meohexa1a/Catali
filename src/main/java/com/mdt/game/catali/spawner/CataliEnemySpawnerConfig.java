package com.mdt.game.catali.spawner;

import com.mdt.game.catali.utils.CataliCommonUtils;

import lombok.experimental.UtilityClass;

import mindustry.game.Team;
import mindustry.type.UnitType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static mindustry.content.UnitTypes.*;

@UtilityClass
public class CataliEnemySpawnerConfig {
    public static final Team ENEMY_TEAM = Team.crux;
    public static final int ENEMY_LIMIT = 400;
    public static final int ENEMY_PER_PLAYER = 15;

    // !--------------------------------------------------------!

    public static List<UnitType> getRandomEnemiesGroup() {
        return CataliCommonUtils.randomUnits(
            ThreadLocalRandom.current().nextInt(1, 5),
            ThreadLocalRandom.current().nextInt(1, 5),
            ThreadLocalRandom.current().nextInt(1, 5),
            ThreadLocalRandom.current().nextInt(1, 5),
            ThreadLocalRandom.current().nextInt(1, 5),
            ThreadLocalRandom.current().nextInt(1, 5));
    }
}
