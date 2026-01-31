package com.mdt.game.catali.spawner;

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

    private static final List<List<UnitType>> WEIGHTED_ENEMIES = new ArrayList<>();

    // !--------------------------------------------------------!

    public static List<UnitType> getRandomEnemiesGroup() {
        return Mindus
        return WEIGHTED_ENEMIES.get(ThreadLocalRandom.current().nextInt(WEIGHTED_ENEMIES.size()));
    }

    // !--------------------------------------------------------!

    static {
        Map<List<UnitType>, Integer> enemiesSpawnChances = new HashMap<>();

        enemiesSpawnChances.put(List.of(dagger, mace, atrax, pulsar, horizon), 10);
        enemiesSpawnChances.put(List.of(crawler, flare, oxynoe, locus, scepter), 12);
        enemiesSpawnChances.put(List.of(stell, merui, mega, bryde, scepter), 15);
        enemiesSpawnChances.put(List.of(risso, retusa, zenith, quad, sei), 18);
        enemiesSpawnChances.put(List.of(nova, poly, fortress, spiroct, tecta), 20);
        enemiesSpawnChances.put(List.of(dagger, atrax, vela, oxynoe, tecta), 14);
        enemiesSpawnChances.put(List.of(risso, minke, cyerce, antumbra, vanquish), 17);
        enemiesSpawnChances.put(List.of(pulsar, locus, horizon, oxynoe, bryde), 21);
        enemiesSpawnChances.put(List.of(dagger, flare, stell, oxynoe, merui), 16);
        enemiesSpawnChances.put(List.of(risso, pulsar, mega, zenith, vanquish), 19);
        enemiesSpawnChances.put(List.of(nova, fortress, mega, poly, locus), 22);
        enemiesSpawnChances.put(List.of(stell, retusa, mega, quad, tecta), 23);
        enemiesSpawnChances.put(List.of(dagger, mace, oxynoe, zenith, horizon), 13);
        enemiesSpawnChances.put(List.of(minke, oxynoe, retusa, zenith, spiroct), 14);
        enemiesSpawnChances.put(List.of(crawler, horizon, poly, antumbra, sei), 18);
        enemiesSpawnChances.put(List.of(stell, mega, oxynoe, fortress, tecta), 20);
        enemiesSpawnChances.put(List.of(minke, oxynoe, locus, mega, scepter), 21);
        enemiesSpawnChances.put(List.of(risso, oxynoe, mega, locus, antumbra), 23);
        enemiesSpawnChances.put(List.of(flare, mace, spiroct, horizon, quell), 17);
        enemiesSpawnChances.put(List.of(flare, mace, atrax, mega, locus), 16);
        enemiesSpawnChances.put(List.of(dagger, fortress, minke, spiroct, sei), 20);
        enemiesSpawnChances.put(List.of(risso, retusa, cyerce, mega, oxynoe), 22);
        enemiesSpawnChances.put(List.of(pulsar, locus, bryde, minke, quad), 19);
        enemiesSpawnChances.put(List.of(minke, quasar, spiroct, bryde, oxynoe), 21);
        enemiesSpawnChances.put(List.of(dagger, oxynoe, locus, mega, quell), 15);
        enemiesSpawnChances.put(List.of(risso, zenith, mega, tecta, scepter), 23);
        enemiesSpawnChances.put(List.of(dagger, mace, quasar, spiroct, locus), 20);
        enemiesSpawnChances.put(List.of(pulsar, oxynoe, mega, quasar, sei), 22);
        enemiesSpawnChances.put(List.of(stell, mega, oxynoe, tecta, locus), 18);
        enemiesSpawnChances.put(List.of(dagger, quasar, fortress, minke, poly), 19);
        enemiesSpawnChances.put(List.of(flare, zenith, mega, spiroct, vanquish), 24);
        enemiesSpawnChances.put(List.of(crawler, pulsar, retusa, fortress, quell), 17);
        enemiesSpawnChances.put(List.of(pulsar, oxynoe, spiroct, tecta, mega), 21);
        enemiesSpawnChances.put(List.of(stell, fortress, horizon, spiroct, tecta), 20);
        enemiesSpawnChances.put(List.of(dagger, mace, mega, oxynoe, quell), 15);
        enemiesSpawnChances.put(List.of(minke, mega, fortress, pulsar, spiroct), 22);
        enemiesSpawnChances.put(List.of(dagger, locus, bryde, zenith, spiroct), 18);
        enemiesSpawnChances.put(List.of(minke, poly, zenith, fortress, mega), 23);
        enemiesSpawnChances.put(List.of(flare, mega, zenith, pulsar, quad, tecta), 25);
        enemiesSpawnChances.put(List.of(dagger, mega, pulsar, bryde, oxynoe, locus), 19);
        enemiesSpawnChances.put(List.of(flare, mega, quasar, locus, tecta, zenith), 20);
        enemiesSpawnChances.put(List.of(minke, horizon, fortress, retusa, spiroct), 16);
        enemiesSpawnChances.put(List.of(minke, locus, fortress, quasar, poly), 21);
        enemiesSpawnChances.put(List.of(risso, minke, horizon, mega, fortress), 22);
        enemiesSpawnChances.put(List.of(stell, quasar, zenith, fortress, tecta), 24);
        enemiesSpawnChances.put(List.of(pulsar, locus, spiroct, retusa, poly), 21);
        enemiesSpawnChances.put(List.of(dagger, mace, fortress, mega, quasar), 18);
        enemiesSpawnChances.put(List.of(risso, pulsar, mega, locus, spiroct), 23);
        enemiesSpawnChances.put(List.of(minke, quasar, fortress, spiroct, bryde), 20);
        enemiesSpawnChances.put(List.of(stell, mega, retusa, locus, tecta, poly), 17);
        enemiesSpawnChances.put(List.of(minke, locus, fortress, spiroct, poly), 16);

        enemiesSpawnChances.put(List.of(collaris, collaris, navanax, oct, oct, oct, vela, vela), 1);
        enemiesSpawnChances.put(List.of(collaris, reign, navanax, reign, oct, oct, toxopid, vela), 1);

        for (Map.Entry<List<UnitType>, Integer> e : enemiesSpawnChances.entrySet())
            WEIGHTED_ENEMIES.addAll(Collections.nCopies(e.getValue(), e.getKey()));
        Collections.shuffle(WEIGHTED_ENEMIES);
    }
}
