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

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static mindustry.content.UnitTypes.*;

@UtilityClass
public class CataliCommonUtils {

    public static final EnumMap<UnitTier, Set<UnitType>> UNIT_TIER = new EnumMap<>(UnitTier.class) {{
        put(UnitTier.Tier1, Set.of(dagger, nova, flare, poly));
        put(UnitTier.Tier2, Set.of(mace, pulsar, atrax, horizon, risso, retusa, stell, merui, elude));
        put(UnitTier.Tier3, Set.of(fortress, quasar, spiroct, zenith, mega, minke, oxynoe, locus, cleroi, avert));
        put(UnitTier.Tier4, Set.of(scepter, vela, arkyid, antumbra, quad, bryde, cyerce, precept, anthicus, obviate));
        put(UnitTier.Tier5, Set.of(reign, corvus, toxopid, eclipse, oct, sei, aegires, vanquish, tecta, quell));
        put(UnitTier.OverPowerTier5, Set.of(omura, navanax, conquer, collaris, disrupt));
    }};

    public static final EnumMap<UnitTier, List<UnitType>> UNIT_TIER_LIST = new EnumMap<>(UnitTier.class) {{
        put(UnitTier.Tier1, List.of(dagger, nova, flare, poly));
        put(UnitTier.Tier2, List.of(mace, pulsar, atrax, horizon, risso, retusa, stell, merui, elude));
        put(UnitTier.Tier3, List.of(fortress, quasar, spiroct, zenith, mega, minke, oxynoe, locus, cleroi, avert));
        put(UnitTier.Tier4, List.of(scepter, vela, arkyid, antumbra, quad, bryde, cyerce, precept, anthicus, obviate));
        put(UnitTier.Tier5, List.of(reign, corvus, toxopid, eclipse, oct, sei, aegires, vanquish, tecta, quell));
        put(UnitTier.OverPowerTier5, List.of(omura, navanax, conquer, collaris, disrupt));
    }};

    // !----------------------------------------------------------!

    public static List<UnitType> randomUnits(int t1, int t2, int t3, int t4, int t5, int ot5) {
        var result = new ArrayList<UnitType>();

        pickRandomUnit(result, UnitTier.Tier1, t1);
        pickRandomUnit(result, UnitTier.Tier2, t2);
        pickRandomUnit(result, UnitTier.Tier3, t3);
        pickRandomUnit(result, UnitTier.Tier4, t4);
        pickRandomUnit(result, UnitTier.Tier5, t5);
        pickRandomUnit(result, UnitTier.OverPowerTier5, ot5);

        return result;
    }

    private static void pickRandomUnit(List<UnitType> out, UnitTier tier, int amount) {
        var tierList = UNIT_TIER_LIST.get(tier);
        for (int i = 0; i < amount; i++)
            out.add(tierList.get(ThreadLocalRandom.current().nextInt(tierList.size())));
    }

    public static UnitTier getUnitTier(UnitType unit) {
        for (var entry : UNIT_TIER.entrySet())
            if (entry.getValue().contains(unit)) return entry.getKey();

        return null;
    }

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
