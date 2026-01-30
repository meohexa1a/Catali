package com.mdt.game.catali.utils;

import arc.math.Mathf;
import mindustry.Vars;

import lombok.experimental.UtilityClass;

import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.gen.Groups;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.Tile;

import java.util.List;

@UtilityClass
public class MindustrySpawnUtils {

    public static void spawnUnitCluster(List<UnitType> units, Team team) {
        if (units == null || units.isEmpty()) return;

        int size = 4;
        int amount = units.size();

        int startX = Mathf.random(Vars.world.width() - size);
        int startY = Mathf.random(Vars.world.height() - size);
        int radius = Mathf.ceil(Mathf.sqrt(amount));

        int placed = 0;
        for (int dx = 0; dx < radius * size && placed < amount; dx += size) {
            for (int dy = 0; dy < radius * size && placed < amount; dy += size) {
                int x = startX + dx;
                int y = startY + dy;

                var tile = Vars.world.tile(x, y);
                if (tile == null || isCantPlace(tile, 2)) continue;

                units.get(placed).spawn(team, x * Vars.tilesize, y * Vars.tilesize);
                placed++;
            }
        }
    }

    public static void spawnBlockCluster(Block block, int amount) {
        if (block == null || amount <= 0) return;

        int size = block.size;
        Team team = Team.crux;

        int startX = Mathf.random(Vars.world.width() - size);
        int startY = Mathf.random(Vars.world.height() - size);
        int radius = Mathf.ceil(Mathf.sqrt(amount));

        int placed = 0;
        for (int dx = 0; dx < radius * size && placed < amount; dx += size) {
            for (int dy = 0; dy < radius * size && placed < amount; dy += size) {
                int x = startX + dx;
                int y = startY + dy;

                var tile = Vars.world.tile(x, y);
                if (tile == null || isCantPlace(tile, size)) continue;

                tile.setNet(block, team, 0);
                placed++;
            }
        }
    }

    private static boolean isCantPlace(Tile tile, int size) {
        int x = tile.x;
        int y = tile.y;

        if (x < 0 || y < 0 || x + size > Vars.world.width() || y + size > Vars.world.height())
            return false;

        for (int dx = 0; dx < size; dx++) {
            for (int dy = 0; dy < size; dy++) {
                var t = Vars.world.tile(x + dx, y + dy);
                if (t == null || t.block() != Blocks.air) return false;
            }
        }

        float wx = x * Vars.tilesize;
        float wy = y * Vars.tilesize;
        float ws = size * Vars.tilesize;

        return !Groups.unit.intersect(wx, wy, ws, ws).any();
    }
}
