package com.mdt.game.catali.spawner;

import com.mdt.game.catali.utils.CataliCommonUtils;

import lombok.RequiredArgsConstructor;

import mindustry.gen.Groups;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class CataliBlockSpawner {

    public void refresh() {
        int targetSpawn = Math.min(
            (int) (Groups.build.size() * CataliBlockSpawnerConfig.BLOCK_FILL_RATE),
            (int) CataliBlockSpawnerConfig.MAX_BLOCK_SPAWNED_PER_REFRESH);

        int used = 0;
        while (used < targetSpawn) {
            var block = CataliBlockSpawnerConfig.getRandomBlockToSpawn();
            int cluster = ThreadLocalRandom.current().nextInt(28, 96) / block.size;

            CataliCommonUtils.spawnBlockCluster(block, CataliBlockSpawnerConfig.BLOCK_TEAM, cluster);
            used += cluster * block.size;
        }
    }
}
