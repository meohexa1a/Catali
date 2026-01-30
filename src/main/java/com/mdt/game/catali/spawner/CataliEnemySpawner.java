package com.mdt.game.catali.spawner;

import com.mdt.game.catali.config.CataliEnemySpawnerConfig;
import com.mdt.game.catali.utils.MindustrySpawnUtils;

import lombok.RequiredArgsConstructor;

import mindustry.gen.Groups;
import mindustry.gen.Player;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class CataliEnemySpawner {

    public void refresh() {
        int maxEnemies = Math.min(CataliEnemySpawnerConfig.ENEMY_LIMIT,
            Groups.player.size() * CataliEnemySpawnerConfig.ENEMY_PER_PLAYER);

        int toSpawn = maxEnemies - Groups.unit.count(u -> u.team == CataliEnemySpawnerConfig.ENEMY_TEAM);
        while (toSpawn > 0) {
            var group = CataliEnemySpawnerConfig.getRandomEnemiesGroup();

            MindustrySpawnUtils.spawnUnitCluster(group, CataliEnemySpawnerConfig.ENEMY_TEAM);
            toSpawn -= group.size();
        }
    }
}
