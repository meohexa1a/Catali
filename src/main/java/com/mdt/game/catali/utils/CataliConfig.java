package com.mdt.game.catali.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import arc.struct.Seq;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import static mindustry.content.Blocks.*;
import static mindustry.content.UnitTypes.*;
import static mindustry.content.StatusEffects.*;

import mindustry.world.Block;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.entities.abilities.UnitSpawnAbility;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;
import mindustry.type.UnitType;

@Getter
@UtilityClass
public class CataliConfig {
    private static final Map<Block, Integer> blocksToExperience = new HashMap<>();
    private static final List<Block> weightedBlocks = new ArrayList<>();

    private static final Map<UnitType, Integer> unitCatchChances = new HashMap<>();
    private static final Map<UnitType, Set<UnitType>> unitEvolutions = new HashMap<>();
    private static final Map<UnitType, Integer> unitRespawnTimes = new HashMap<>();

    private static final Map<UnitType, Integer> unitsToExperience = new HashMap<>();
    private static final List<List<UnitType>> weightedUnits = new ArrayList<>();
    private static final List<List<UnitType>> weightedBoss = new ArrayList<>();

    private static final List<String> rankingEmoji = List.of(
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

    private static final List<UnitType> catchWhitelist = List.of(
            dagger, mace, fortress, reign,
            nova, pulsar, quasar, vela,
            atrax, spiroct, arkyid,
            flare, horizon, zenith, antumbra,
            poly, mega, quad,
            risso, minke,
            retusa, oxynoe, cyerce,
            stell, locus,
            merui, cleroi,
            elude, avert, obviate);

    private static final List<UnitType> buffWhitelist = List.of(
            reign, corvus, toxopid,
            eclipse, oct,
            omura, navanax,
            conquer, collaris, disrupt);

    private static final List<StatusEffect> buffEffectWhitelist = List.of(
            fast, overdrive, overclock, shielded, boss);

    public static final float BLOCK_FILL_RATE = 2.5f / 100; // 2.5%
    public static final float MAX_SPAWN_TILES_PER_REFRESH = 50;
    public static final int UNITS_LIMIT = 400;
    public static final int UNIT_PER_PLAYER = 10;
    public static final int BOSS_LIMIT = 50;
    public static final int BOSS_PER_PLAYER = 2;
    public static final Team BLOCK_TEAM = Team.crux;
    public static final Team UNITS_TEAM = Team.crux;
    public static final Team BOSS_TEAM = Team.malis;

    public static final float HEALTH_MULTIPLIER = 0.05f;
    public static final float DAMAGE_MULTIPLIER = 0.05f;
    public static final int HEALING_PER_SEC = 15;
    public static final float EXP_MULTIPLIER = 0.05f;

    public static final int LEADER_TRANSFER_AT = 3 * 60;
    public static final int SPAWN_SPACE_BETWEEN_PLAYER = 60;

    public static final int XP_FROM_IDLE_UNIT = 40;
    public static final List<UnitType> IDLE_UNITS = List.of(oct, quell, disrupt);

    // !-------------------!

    static {
        blocksToExperience.put(copperWall, 32);
        blocksToExperience.put(copperWallLarge, 128);
        blocksToExperience.put(titaniumWall, 44);
        blocksToExperience.put(titaniumWallLarge, 176);
        blocksToExperience.put(berylliumWall, 52);
        blocksToExperience.put(berylliumWallLarge, 208);
        blocksToExperience.put(plastaniumWall, 52);
        blocksToExperience.put(plastaniumWallLarge, 208);
        blocksToExperience.put(tungstenWall, 72);
        blocksToExperience.put(tungstenWallLarge, 288);
        blocksToExperience.put(thoriumWall, 80);
        blocksToExperience.put(thoriumWallLarge, 320);
        blocksToExperience.put(phaseWall, 60);
        blocksToExperience.put(phaseWallLarge, 240);
        blocksToExperience.put(surgeWall, 92);
        blocksToExperience.put(surgeWallLarge, 368);
        blocksToExperience.put(carbideWall, 108);
        blocksToExperience.put(carbideWallLarge, 432);
        blocksToExperience.put(reinforcedSurgeWall, 100);
        blocksToExperience.put(reinforcedSurgeWallLarge, 400);
        blocksToExperience.put(container, 75);
        blocksToExperience.put(vault, 225);
        blocksToExperience.put(reinforcedContainer, 250);
        blocksToExperience.put(reinforcedVault, 750);

        // !--------------------------!

        Map<Block, Integer> blocksToSpawnChance = new HashMap<>();
        blocksToSpawnChance.put(copperWall, 200);
        blocksToSpawnChance.put(copperWallLarge, 50);
        blocksToSpawnChance.put(titaniumWall, 100);
        blocksToSpawnChance.put(titaniumWallLarge, 20);
        blocksToSpawnChance.put(berylliumWall, 67);
        blocksToSpawnChance.put(berylliumWallLarge, 13);
        blocksToSpawnChance.put(plastaniumWall, 57);
        blocksToSpawnChance.put(plastaniumWallLarge, 11);
        blocksToSpawnChance.put(tungstenWall, 50);
        blocksToSpawnChance.put(tungstenWallLarge, 10);
        blocksToSpawnChance.put(thoriumWall, 50);
        blocksToSpawnChance.put(thoriumWallLarge, 9);
        blocksToSpawnChance.put(phaseWall, 40);
        blocksToSpawnChance.put(phaseWallLarge, 8);
        blocksToSpawnChance.put(surgeWall, 25);
        blocksToSpawnChance.put(surgeWallLarge, 5);
        blocksToSpawnChance.put(carbideWall, 20);
        blocksToSpawnChance.put(carbideWallLarge, 4);
        blocksToSpawnChance.put(reinforcedSurgeWall, 17);
        blocksToSpawnChance.put(reinforcedSurgeWallLarge, 3);
        blocksToSpawnChance.put(container, 10);
        blocksToSpawnChance.put(vault, 4);
        blocksToSpawnChance.put(reinforcedContainer, 4);
        blocksToSpawnChance.put(reinforcedVault, 1);
        blocksToSpawnChance.put(thoriumReactor, 1);

        for (Map.Entry<Block, Integer> entry : blocksToSpawnChance.entrySet())
            weightedBlocks.addAll(Collections.nCopies(entry.getValue(), entry.getKey()));
        Collections.shuffle(weightedBlocks);

        // !--------------------------!

        unitsToExperience.put(dagger, 30);
        unitsToExperience.put(nova, 24);
        unitsToExperience.put(flare, 14);
        unitsToExperience.put(poly, 80);
        unitsToExperience.put(mace, 110);
        unitsToExperience.put(pulsar, 64);
        unitsToExperience.put(horizon, 68);
        unitsToExperience.put(mega, 92);
        unitsToExperience.put(risso, 60);
        unitsToExperience.put(retusa, 54);
        unitsToExperience.put(stell, 170);
        unitsToExperience.put(merui, 136);
        unitsToExperience.put(elude, 120);
        unitsToExperience.put(fortress, 180);
        unitsToExperience.put(quasar, 128);
        unitsToExperience.put(zenith, 140);
        unitsToExperience.put(quad, 1200);
        unitsToExperience.put(minke, 120);
        unitsToExperience.put(oxynoe, 112);
        unitsToExperience.put(locus, 420);
        unitsToExperience.put(cleroi, 220);
        unitsToExperience.put(avert, 220);
        unitsToExperience.put(scepter, 1800);
        unitsToExperience.put(vela, 1640);
        unitsToExperience.put(antumbra, 1440);
        unitsToExperience.put(oct, 3000);
        unitsToExperience.put(bryde, 182);
        unitsToExperience.put(cyerce, 174);
        unitsToExperience.put(precept, 1000);
        unitsToExperience.put(anthicus, 580);
        unitsToExperience.put(obviate, 460);
        unitsToExperience.put(reign, 4000);
        unitsToExperience.put(corvus, 3000);
        unitsToExperience.put(eclipse, 4000);
        unitsToExperience.put(sei, 2000);
        unitsToExperience.put(aegires, 2200);
        unitsToExperience.put(vanquish, 2200);
        unitsToExperience.put(tecta, 1460);
        unitsToExperience.put(quell, 1200);
        unitsToExperience.put(omura, 4400);
        unitsToExperience.put(navanax, 4000);
        unitsToExperience.put(conquer, 4400);
        unitsToExperience.put(collaris, 3600);
        unitsToExperience.put(disrupt, 2400);

        // !--------------------------!

        unitEvolutions.put(poly, Set.of(dagger, flare, retusa, nova, mega));
        unitEvolutions.put(dagger, Set.of(mace, atrax, stell));
        unitEvolutions.put(mace, Set.of(fortress));
        unitEvolutions.put(fortress, Set.of(scepter));
        unitEvolutions.put(scepter, Set.of(reign));
        unitEvolutions.put(atrax, Set.of(spiroct));
        unitEvolutions.put(spiroct, Set.of(arkyid));
        unitEvolutions.put(arkyid, Set.of(toxopid));
        unitEvolutions.put(stell, Set.of(locus));
        unitEvolutions.put(locus, Set.of(precept));
        unitEvolutions.put(precept, Set.of(vanquish));
        unitEvolutions.put(vanquish, Set.of(conquer));
        unitEvolutions.put(flare, Set.of(horizon, elude));
        unitEvolutions.put(horizon, Set.of(zenith));
        unitEvolutions.put(zenith, Set.of(antumbra));
        unitEvolutions.put(antumbra, Set.of(eclipse));
        unitEvolutions.put(elude, Set.of(avert));
        unitEvolutions.put(avert, Set.of(obviate));
        unitEvolutions.put(obviate, Set.of(quell));
        unitEvolutions.put(quell, Set.of(disrupt));
        unitEvolutions.put(retusa, Set.of(oxynoe, risso));
        unitEvolutions.put(oxynoe, Set.of(cyerce));
        unitEvolutions.put(cyerce, Set.of(aegires));
        unitEvolutions.put(aegires, Set.of(navanax));
        unitEvolutions.put(risso, Set.of(minke));
        unitEvolutions.put(minke, Set.of(bryde));
        unitEvolutions.put(bryde, Set.of(sei));
        unitEvolutions.put(sei, Set.of(omura));
        unitEvolutions.put(nova, Set.of(pulsar, merui));
        unitEvolutions.put(pulsar, Set.of(quasar));
        unitEvolutions.put(quasar, Set.of(vela));
        unitEvolutions.put(vela, Set.of(corvus));
        unitEvolutions.put(merui, Set.of(cleroi));
        unitEvolutions.put(cleroi, Set.of(anthicus));
        unitEvolutions.put(anthicus, Set.of(tecta));
        unitEvolutions.put(tecta, Set.of(collaris));
        unitEvolutions.put(mega, Set.of(quad));
        unitEvolutions.put(quad, Set.of(oct));

        // !--------------------------!

        unitRespawnTimes.put(dagger, 60);
        unitRespawnTimes.put(nova, 60);
        unitRespawnTimes.put(crawler, 60);
        unitRespawnTimes.put(flare, 60);
        unitRespawnTimes.put(poly, 30);
        unitRespawnTimes.put(risso, 80);
        unitRespawnTimes.put(retusa, 80);
        unitRespawnTimes.put(stell, 80);
        unitRespawnTimes.put(merui, 80);
        unitRespawnTimes.put(mace, 100);
        unitRespawnTimes.put(pulsar, 100);
        unitRespawnTimes.put(atrax, 100);
        unitRespawnTimes.put(horizon, 100);
        unitRespawnTimes.put(minke, 120);
        unitRespawnTimes.put(oxynoe, 120);
        unitRespawnTimes.put(locus, 120);
        unitRespawnTimes.put(cleroi, 120);
        unitRespawnTimes.put(avert, 100);
        unitRespawnTimes.put(fortress, 140);
        unitRespawnTimes.put(quasar, 140);
        unitRespawnTimes.put(spiroct, 140);
        unitRespawnTimes.put(zenith, 140);
        unitRespawnTimes.put(mega, 60);
        unitRespawnTimes.put(bryde, 180);
        unitRespawnTimes.put(cyerce, 180);
        unitRespawnTimes.put(precept, 180);
        unitRespawnTimes.put(anthicus, 180);
        unitRespawnTimes.put(obviate, 140);
        unitRespawnTimes.put(scepter, 200);
        unitRespawnTimes.put(vela, 200);
        unitRespawnTimes.put(arkyid, 200);
        unitRespawnTimes.put(antumbra, 200);
        unitRespawnTimes.put(quad, 160);
        unitRespawnTimes.put(sei, 240);
        unitRespawnTimes.put(aegires, 240);
        unitRespawnTimes.put(vanquish, 240);
        unitRespawnTimes.put(tecta, 240);
        unitRespawnTimes.put(quell, 180);
        unitRespawnTimes.put(reign, 240);
        unitRespawnTimes.put(corvus, 240);
        unitRespawnTimes.put(toxopid, 240);
        unitRespawnTimes.put(eclipse, 240);
        unitRespawnTimes.put(oct, 160);
        unitRespawnTimes.put(omura, 300);
        unitRespawnTimes.put(navanax, 300);
        unitRespawnTimes.put(conquer, 300);
        unitRespawnTimes.put(collaris, 300);
        unitRespawnTimes.put(disrupt, 220);

        // !--------------------------!

        unitCatchChances.put(dagger, 50);
        unitCatchChances.put(nova, 50);
        unitCatchChances.put(crawler, 0);
        unitCatchChances.put(flare, 50);
        unitCatchChances.put(poly, 75);
        unitCatchChances.put(risso, 40);
        unitCatchChances.put(retusa, 50);
        unitCatchChances.put(stell, 40);
        unitCatchChances.put(merui, 40);
        unitCatchChances.put(mace, 40);
        unitCatchChances.put(pulsar, 40);
        unitCatchChances.put(atrax, 40);
        unitCatchChances.put(horizon, 40);
        unitCatchChances.put(minke, 25);
        unitCatchChances.put(oxynoe, 25);
        unitCatchChances.put(locus, 25);
        unitCatchChances.put(cleroi, 25);
        unitCatchChances.put(avert, 30);
        unitCatchChances.put(fortress, 30);
        unitCatchChances.put(quasar, 30);
        unitCatchChances.put(spiroct, 30);
        unitCatchChances.put(zenith, 30);
        unitCatchChances.put(mega, 50);
        unitCatchChances.put(bryde, 15);
        unitCatchChances.put(cyerce, 15);
        unitCatchChances.put(precept, 15);
        unitCatchChances.put(anthicus, 15);
        unitCatchChances.put(obviate, 20);
        unitCatchChances.put(scepter, 20);
        unitCatchChances.put(vela, 20);
        unitCatchChances.put(arkyid, 20);
        unitCatchChances.put(antumbra, 20);
        unitCatchChances.put(quad, 35);
        unitCatchChances.put(sei, 10);
        unitCatchChances.put(aegires, 10);
        unitCatchChances.put(vanquish, 10);
        unitCatchChances.put(tecta, 10);
        unitCatchChances.put(quell, 20);
        unitCatchChances.put(reign, 10);
        unitCatchChances.put(corvus, 10);
        unitCatchChances.put(toxopid, 10);
        unitCatchChances.put(eclipse, 10);
        unitCatchChances.put(oct, 30);
        unitCatchChances.put(omura, 5);
        unitCatchChances.put(navanax, 5);
        unitCatchChances.put(conquer, 5);
        unitCatchChances.put(collaris, 5);
        unitCatchChances.put(disrupt, 15);

        // !---------------------------!

        Map<List<UnitType>, Integer> unitSpawnChances = new HashMap<>();

        unitSpawnChances.put(List.of(dagger, mace, atrax, pulsar, horizon), 10);
        unitSpawnChances.put(List.of(crawler, flare, oxynoe, locus, scepter), 12);
        unitSpawnChances.put(List.of(stell, merui, mega, bryde, scepter), 15);
        unitSpawnChances.put(List.of(risso, retusa, zenith, quad, sei), 18);
        unitSpawnChances.put(List.of(nova, poly, fortress, spiroct, tecta), 20);
        unitSpawnChances.put(List.of(dagger, atrax, vela, oxynoe, tecta), 14);
        unitSpawnChances.put(List.of(risso, minke, cyerce, antumbra, vanquish), 17);
        unitSpawnChances.put(List.of(pulsar, locus, horizon, oxynoe, bryde), 21);
        unitSpawnChances.put(List.of(dagger, flare, stell, oxynoe, merui), 16);
        unitSpawnChances.put(List.of(risso, pulsar, mega, zenith, vanquish), 19);
        unitSpawnChances.put(List.of(nova, fortress, mega, poly, locus), 22);
        unitSpawnChances.put(List.of(stell, retusa, mega, quad, tecta), 23);
        unitSpawnChances.put(List.of(dagger, mace, oxynoe, zenith, horizon), 13);
        unitSpawnChances.put(List.of(minke, oxynoe, retusa, zenith, spiroct), 14);
        unitSpawnChances.put(List.of(crawler, horizon, poly, antumbra, sei), 18);
        unitSpawnChances.put(List.of(stell, mega, oxynoe, fortress, tecta), 20);
        unitSpawnChances.put(List.of(minke, oxynoe, locus, mega, scepter), 21);
        unitSpawnChances.put(List.of(risso, oxynoe, mega, locus, antumbra), 23);
        unitSpawnChances.put(List.of(flare, mace, spiroct, horizon, quell), 17);
        unitSpawnChances.put(List.of(flare, mace, atrax, mega, locus), 16);
        unitSpawnChances.put(List.of(dagger, fortress, minke, spiroct, sei), 20);
        unitSpawnChances.put(List.of(risso, retusa, cyerce, mega, oxynoe), 22);
        unitSpawnChances.put(List.of(pulsar, locus, bryde, minke, quad), 19);
        unitSpawnChances.put(List.of(minke, quasar, spiroct, bryde, oxynoe), 21);
        unitSpawnChances.put(List.of(dagger, oxynoe, locus, mega, quell), 15);
        unitSpawnChances.put(List.of(risso, zenith, mega, tecta, scepter), 23);
        unitSpawnChances.put(List.of(dagger, mace, quasar, spiroct, locus), 20);
        unitSpawnChances.put(List.of(pulsar, oxynoe, mega, quasar, sei), 22);
        unitSpawnChances.put(List.of(stell, mega, oxynoe, tecta, locus), 18);
        unitSpawnChances.put(List.of(dagger, quasar, fortress, minke, poly), 19);
        unitSpawnChances.put(List.of(flare, zenith, mega, spiroct, vanquish), 24);
        unitSpawnChances.put(List.of(crawler, pulsar, retusa, fortress, quell), 17);
        unitSpawnChances.put(List.of(pulsar, oxynoe, spiroct, tecta, mega), 21);
        unitSpawnChances.put(List.of(stell, fortress, horizon, spiroct, tecta), 20);
        unitSpawnChances.put(List.of(dagger, mace, mega, oxynoe, quell), 15);
        unitSpawnChances.put(List.of(minke, mega, fortress, pulsar, spiroct), 22);
        unitSpawnChances.put(List.of(dagger, locus, bryde, zenith, spiroct), 18);
        unitSpawnChances.put(List.of(minke, poly, zenith, fortress, mega), 23);
        unitSpawnChances.put(List.of(flare, mega, zenith, pulsar, quad, tecta), 25);
        unitSpawnChances.put(List.of(dagger, mega, pulsar, bryde, oxynoe, locus), 19);
        unitSpawnChances.put(List.of(flare, mega, quasar, locus, tecta, zenith), 20);
        unitSpawnChances.put(List.of(minke, horizon, fortress, retusa, spiroct), 16);
        unitSpawnChances.put(List.of(minke, locus, fortress, quasar, poly), 21);
        unitSpawnChances.put(List.of(risso, minke, horizon, mega, fortress), 22);
        unitSpawnChances.put(List.of(stell, quasar, zenith, fortress, tecta), 24);
        unitSpawnChances.put(List.of(pulsar, locus, spiroct, retusa, poly), 21);
        unitSpawnChances.put(List.of(dagger, mace, fortress, mega, quasar), 18);
        unitSpawnChances.put(List.of(risso, pulsar, mega, locus, spiroct), 23);
        unitSpawnChances.put(List.of(minke, quasar, fortress, spiroct, bryde), 20);
        unitSpawnChances.put(List.of(stell, mega, retusa, locus, tecta, poly), 17);
        unitSpawnChances.put(List.of(minke, locus, fortress, spiroct, poly), 16);

        for (Map.Entry<List<UnitType>, Integer> e : unitSpawnChances.entrySet())
            weightedUnits.addAll(Collections.nCopies(e.getValue(), e.getKey()));
        Collections.shuffle(weightedUnits);

        // !--------------------------!

        Map<List<UnitType>, Integer> bossSpawnChances = new HashMap<>();

        bossSpawnChances.put(List.of(collaris, collaris, navanax, oct, oct, oct, vela, vela), 1);

        for (Map.Entry<List<UnitType>, Integer> e : bossSpawnChances.entrySet())
            weightedBoss.addAll(Collections.nCopies(e.getValue(), e.getKey()));
        Collections.shuffle(weightedBoss);
    }

    // !--------------------------!

    public static int getExperienceForBlock(Block block) {
        return blocksToExperience.getOrDefault(block, 0);
    }

    public static Block getRandomBlockToSpawn() {
        return weightedBlocks.get(ThreadLocalRandom.current().nextInt(weightedBlocks.size()));
    }

    public static List<UnitType> getRandomUnitGroup() {
        return weightedUnits.isEmpty()
                ? Collections.emptyList()
                : weightedUnits.get(ThreadLocalRandom.current().nextInt(weightedUnits.size()));
    }

    public static List<UnitType> getRandomBossGroup() {
        return weightedBoss.isEmpty()
                ? Collections.emptyList()
                : weightedBoss.get(ThreadLocalRandom.current().nextInt(weightedBoss.size()));
    }

    public static int getExperienceForUnit(UnitType unit) {
        return unitsToExperience.getOrDefault(unit, 0);
    }

    public static Set<UnitType> getUnitEvolutions(UnitType unit) {
        return unitEvolutions.getOrDefault(unit, Collections.emptySet());
    }

    public static int getUnitRespawnTime(UnitType unit) {
        return unitRespawnTimes.getOrDefault(unit, 0);
    }

    public static int getUnitCatchChance(UnitType unit) {
        return unitCatchChances.getOrDefault(unit, 0);
    }

    public String getRankEmoji(long level) {
        return rankingEmoji.get(Math.min((int) level / 10, rankingEmoji.size() - 1));
    }

    public boolean canCatchUnit(UnitType type) {
        return catchWhitelist.contains(type);
    }

    public List<Unit> selectUnitsCanBeBuff(Seq<Unit> units) {
        return units.select(u -> CataliConfig.buffWhitelist.contains(u.type)).list();
    }

    public List<StatusEffect> selectBuffsCanBeApplied(Unit u) {
        var buffs = new ArrayList<>(buffEffectWhitelist);

        buffs.removeIf(buff -> u.hasEffect(buff));
        return buffs;
    }
}
