package com.mdt.game.catali.config;

import lombok.experimental.UtilityClass;
import mindustry.game.Team;
import mindustry.type.UnitType;

import java.util.*;

import static mindustry.content.UnitTypes.antumbra;
import static mindustry.content.UnitTypes.atrax;
import static mindustry.content.UnitTypes.bryde;
import static mindustry.content.UnitTypes.crawler;
import static mindustry.content.UnitTypes.cyerce;
import static mindustry.content.UnitTypes.dagger;
import static mindustry.content.UnitTypes.flare;
import static mindustry.content.UnitTypes.fortress;
import static mindustry.content.UnitTypes.horizon;
import static mindustry.content.UnitTypes.locus;
import static mindustry.content.UnitTypes.mace;
import static mindustry.content.UnitTypes.mega;
import static mindustry.content.UnitTypes.merui;
import static mindustry.content.UnitTypes.minke;
import static mindustry.content.UnitTypes.nova;
import static mindustry.content.UnitTypes.oxynoe;
import static mindustry.content.UnitTypes.poly;
import static mindustry.content.UnitTypes.pulsar;
import static mindustry.content.UnitTypes.quad;
import static mindustry.content.UnitTypes.quasar;
import static mindustry.content.UnitTypes.quell;
import static mindustry.content.UnitTypes.retusa;
import static mindustry.content.UnitTypes.risso;
import static mindustry.content.UnitTypes.scepter;
import static mindustry.content.UnitTypes.sei;
import static mindustry.content.UnitTypes.spiroct;
import static mindustry.content.UnitTypes.stell;
import static mindustry.content.UnitTypes.tecta;
import static mindustry.content.UnitTypes.vanquish;
import static mindustry.content.UnitTypes.vela;
import static mindustry.content.UnitTypes.zenith;

@UtilityClass
public class CataliEnemySpawnerConfig {
    public static final Team ENEMY_TEAM = Team.crux;
    public static final int ENEMY_LIMIT = 400;
    public static final int ENEMY_PER_PLAYER = 15;

    public static final Team BOSS_TEAM = Team.crux;
    public static final int BOSS_LIMIT = 30;
    public static final int BOSS_PER_PLAYER = 2;

    // !--------------------------------------------------------!

    private static final List<List<UnitType>> WEIGHTED_ENEMIES_TEAM = new ArrayList<>();
    private static final List<List<UnitType>> WEIGHTED_BOSSES = new ArrayList<>();

    // !--------------------------------------------------------!

    static {
        Map<List<UnitType>, Integer> enemiesTeamSpawnChances = new HashMap<>();

        enemiesTeamSpawnChances.put(List.of(dagger, mace, atrax, pulsar, horizon), 10);
        enemiesTeamSpawnChances.put(List.of(crawler, flare, oxynoe, locus, scepter), 12);
        enemiesTeamSpawnChances.put(List.of(stell, merui, mega, bryde, scepter), 15);
        enemiesTeamSpawnChances.put(List.of(risso, retusa, zenith, quad, sei), 18);
        enemiesTeamSpawnChances.put(List.of(nova, poly, fortress, spiroct, tecta), 20);
        enemiesTeamSpawnChances.put(List.of(dagger, atrax, vela, oxynoe, tecta), 14);
        enemiesTeamSpawnChances.put(List.of(risso, minke, cyerce, antumbra, vanquish), 17);
        enemiesTeamSpawnChances.put(List.of(pulsar, locus, horizon, oxynoe, bryde), 21);
        enemiesTeamSpawnChances.put(List.of(dagger, flare, stell, oxynoe, merui), 16);
        enemiesTeamSpawnChances.put(List.of(risso, pulsar, mega, zenith, vanquish), 19);
        enemiesTeamSpawnChances.put(List.of(nova, fortress, mega, poly, locus), 22);
        enemiesTeamSpawnChances.put(List.of(stell, retusa, mega, quad, tecta), 23);
        enemiesTeamSpawnChances.put(List.of(dagger, mace, oxynoe, zenith, horizon), 13);
        enemiesTeamSpawnChances.put(List.of(minke, oxynoe, retusa, zenith, spiroct), 14);
        enemiesTeamSpawnChances.put(List.of(crawler, horizon, poly, antumbra, sei), 18);
        enemiesTeamSpawnChances.put(List.of(stell, mega, oxynoe, fortress, tecta), 20);
        enemiesTeamSpawnChances.put(List.of(minke, oxynoe, locus, mega, scepter), 21);
        enemiesTeamSpawnChances.put(List.of(risso, oxynoe, mega, locus, antumbra), 23);
        enemiesTeamSpawnChances.put(List.of(flare, mace, spiroct, horizon, quell), 17);
        enemiesTeamSpawnChances.put(List.of(flare, mace, atrax, mega, locus), 16);
        enemiesTeamSpawnChances.put(List.of(dagger, fortress, minke, spiroct, sei), 20);
        enemiesTeamSpawnChances.put(List.of(risso, retusa, cyerce, mega, oxynoe), 22);
        enemiesTeamSpawnChances.put(List.of(pulsar, locus, bryde, minke, quad), 19);
        enemiesTeamSpawnChances.put(List.of(minke, quasar, spiroct, bryde, oxynoe), 21);
        enemiesTeamSpawnChances.put(List.of(dagger, oxynoe, locus, mega, quell), 15);
        enemiesTeamSpawnChances.put(List.of(risso, zenith, mega, tecta, scepter), 23);
        enemiesTeamSpawnChances.put(List.of(dagger, mace, quasar, spiroct, locus), 20);
        enemiesTeamSpawnChances.put(List.of(pulsar, oxynoe, mega, quasar, sei), 22);
        enemiesTeamSpawnChances.put(List.of(stell, mega, oxynoe, tecta, locus), 18);
        enemiesTeamSpawnChances.put(List.of(dagger, quasar, fortress, minke, poly), 19);
        enemiesTeamSpawnChances.put(List.of(flare, zenith, mega, spiroct, vanquish), 24);
        enemiesTeamSpawnChances.put(List.of(crawler, pulsar, retusa, fortress, quell), 17);
        enemiesTeamSpawnChances.put(List.of(pulsar, oxynoe, spiroct, tecta, mega), 21);
        enemiesTeamSpawnChances.put(List.of(stell, fortress, horizon, spiroct, tecta), 20);
        enemiesTeamSpawnChances.put(List.of(dagger, mace, mega, oxynoe, quell), 15);
        enemiesTeamSpawnChances.put(List.of(minke, mega, fortress, pulsar, spiroct), 22);
        enemiesTeamSpawnChances.put(List.of(dagger, locus, bryde, zenith, spiroct), 18);
        enemiesTeamSpawnChances.put(List.of(minke, poly, zenith, fortress, mega), 23);
        enemiesTeamSpawnChances.put(List.of(flare, mega, zenith, pulsar, quad, tecta), 25);
        enemiesTeamSpawnChances.put(List.of(dagger, mega, pulsar, bryde, oxynoe, locus), 19);
        enemiesTeamSpawnChances.put(List.of(flare, mega, quasar, locus, tecta, zenith), 20);
        enemiesTeamSpawnChances.put(List.of(minke, horizon, fortress, retusa, spiroct), 16);
        enemiesTeamSpawnChances.put(List.of(minke, locus, fortress, quasar, poly), 21);
        enemiesTeamSpawnChances.put(List.of(risso, minke, horizon, mega, fortress), 22);
        enemiesTeamSpawnChances.put(List.of(stell, quasar, zenith, fortress, tecta), 24);
        enemiesTeamSpawnChances.put(List.of(pulsar, locus, spiroct, retusa, poly), 21);
        enemiesTeamSpawnChances.put(List.of(dagger, mace, fortress, mega, quasar), 18);
        enemiesTeamSpawnChances.put(List.of(risso, pulsar, mega, locus, spiroct), 23);
        enemiesTeamSpawnChances.put(List.of(minke, quasar, fortress, spiroct, bryde), 20);
        enemiesTeamSpawnChances.put(List.of(stell, mega, retusa, locus, tecta, poly), 17);
        enemiesTeamSpawnChances.put(List.of(minke, locus, fortress, spiroct, poly), 16);

        for (Map.Entry<List<UnitType>, Integer> e : enemiesTeamSpawnChances.entrySet())
            WEIGHTED_ENEMIES_TEAM.addAll(Collections.nCopies(e.getValue(), e.getKey()));
        Collections.shuffle(WEIGHTED_ENEMIES_TEAM);
    }



}
