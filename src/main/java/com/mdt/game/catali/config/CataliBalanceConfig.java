package com.mdt.game.catali.config;

import mindustry.Vars;
import mindustry.content.UnitTypes;
import mindustry.game.Team;
import mindustry.type.UnitType;

import java.util.List;

import static mindustry.content.UnitTypes.*;

public class CataliBalanceConfig {

    // ! World Config - General

    public static final int CHUNK_SIZE = 50;

    // ! World Config - Block Spawner Config

    public static final Team BLOCK_TEAM = Team.crux;
    public static final float BLOCK_FILL_RATE = 2.5f / 100; // 2.5%
    public static final float MAX_BLOCK_SPAWNED_PER_REFRESH = 50;

    // ! World Config - Unit Spawner Config

    public static final Team ENEMY_TEAM = Team.crux;
    public static final Team BOSS_TEAM = Team.crux;
    public static final int ENEMY_LIMIT = 400;
    public static final int ENEMY_PER_PLAYER = 15;
    public static final int BOSS_LIMIT = 50;
    public static final int BOSS_PER_PLAYER = 2;

    // ! Team Config

    public static final float HEALTH_MULTIPLIER = 0.05f;
    public static final float DAMAGE_MULTIPLIER = 0.05f;
    public static final int HEALING_PER_SEC = 8;
    public static final float EXP_MULTIPLIER = 0.05f;

    public static final int BEGINNER_LEVEL_LEAST_THAN = 25;
    public static final int BEGINNER_EXP_MULTIPLIER = 4;

    public static final int EXP_FROM_IDLE_UNIT_PER_REFRESH = 40;
    public static final List<UnitType> IDLE_UNITS = List.of(oct, quell, disrupt);

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
