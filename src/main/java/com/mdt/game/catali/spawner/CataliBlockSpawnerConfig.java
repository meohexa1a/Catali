package com.mdt.game.catali.spawner;

import lombok.experimental.UtilityClass;
import mindustry.game.Team;
import mindustry.world.Block;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static mindustry.content.Blocks.*;

@UtilityClass
public class CataliBlockSpawnerConfig {
    public static final Team BLOCK_TEAM = Team.crux;
    public static final float BLOCK_FILL_RATE = 2.5f / 100; // 2.5%
    public static final float MAX_BLOCK_SPAWNED_PER_REFRESH = 100;

    private static final List<Block> WEIGHTED_BLOCKS = new ArrayList<>();

    // !-------------------------------------------------------------!

    public static Block getRandomBlockToSpawn() {
        return WEIGHTED_BLOCKS.get(ThreadLocalRandom.current().nextInt(WEIGHTED_BLOCKS.size()));
    }

    // !-------------------------------------------------------------!

    static {
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

        for (var entry : blocksToSpawnChance.entrySet()) {
            entry.getKey().update = true;
            WEIGHTED_BLOCKS.addAll(Collections.nCopies(entry.getValue(), entry.getKey()));
        }

        Collections.shuffle(WEIGHTED_BLOCKS);
    }
}
