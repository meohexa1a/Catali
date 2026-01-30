package com.mdt.game.catali.spawner;

import com.mdt.game.catali.config.CataliBlockSpawnerConfig;
import com.mdt.game.catali.utils.MindustrySpawnUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import mindustry.gen.Groups;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class CataliBlockSpawner {

    public void refresh() {
        int existingBlocks = Groups.build.size();

        int targetSpawn = Math.min(
            (int) (existingBlocks * CataliBlockSpawnerConfig.BLOCK_FILL_RATE),
            (int) CataliBlockSpawnerConfig.MAX_BLOCK_SPAWNED_PER_REFRESH);

        if (targetSpawn <= 0) return;
        int spawned = 0;

        while (spawned < targetSpawn) {
            var block = CataliBlockSpawnerConfig.getRandomBlockToSpawn();

            int clusterSize = ThreadLocalRandom.current().nextInt(28, 96);
            clusterSize = Math.min(clusterSize, targetSpawn - spawned);
            clusterSize /= block.size;

            MindustrySpawnUtils.spawnBlockCluster(block, clusterSize);
            spawned += clusterSize;
        }
    }
}
