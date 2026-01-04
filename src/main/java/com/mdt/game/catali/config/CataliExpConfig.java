package com.mdt.game.catali.config;

import lombok.experimental.UtilityClass;
import mindustry.type.UnitType;
import mindustry.world.Block;

import java.util.HashMap;
import java.util.Map;

import static mindustry.content.Blocks.*;
import static mindustry.content.UnitTypes.*;

@UtilityClass
public class CataliExpConfig {
    private static final Map<Block, Integer> BLOCKS_TO_EXPERIENCE = new HashMap<>();
    private static final Map<UnitType, Integer> UNITS_TO_EXPERIENCE = new HashMap<>();

    // !--------------------------------------------------------------------!

    public static int getExperienceForBlock(Block block) {
        return BLOCKS_TO_EXPERIENCE.getOrDefault(block, 0);
    }

    public static int getExperienceForUnit(UnitType unit) {
        return UNITS_TO_EXPERIENCE.getOrDefault(unit, 0);
    }

    // !--------------------------------------------------------------------!

    static {
        BLOCKS_TO_EXPERIENCE.put(copperWall, 32);
        BLOCKS_TO_EXPERIENCE.put(copperWallLarge, 128);
        BLOCKS_TO_EXPERIENCE.put(titaniumWall, 44);
        BLOCKS_TO_EXPERIENCE.put(titaniumWallLarge, 176);
        BLOCKS_TO_EXPERIENCE.put(berylliumWall, 52);
        BLOCKS_TO_EXPERIENCE.put(berylliumWallLarge, 208);
        BLOCKS_TO_EXPERIENCE.put(plastaniumWall, 52);
        BLOCKS_TO_EXPERIENCE.put(plastaniumWallLarge, 208);
        BLOCKS_TO_EXPERIENCE.put(tungstenWall, 72);
        BLOCKS_TO_EXPERIENCE.put(tungstenWallLarge, 288);
        BLOCKS_TO_EXPERIENCE.put(thoriumWall, 80);
        BLOCKS_TO_EXPERIENCE.put(thoriumWallLarge, 320);
        BLOCKS_TO_EXPERIENCE.put(phaseWall, 60);
        BLOCKS_TO_EXPERIENCE.put(phaseWallLarge, 240);
        BLOCKS_TO_EXPERIENCE.put(surgeWall, 92);
        BLOCKS_TO_EXPERIENCE.put(surgeWallLarge, 368);
        BLOCKS_TO_EXPERIENCE.put(carbideWall, 108);
        BLOCKS_TO_EXPERIENCE.put(carbideWallLarge, 432);
        BLOCKS_TO_EXPERIENCE.put(reinforcedSurgeWall, 100);
        BLOCKS_TO_EXPERIENCE.put(reinforcedSurgeWallLarge, 400);
        BLOCKS_TO_EXPERIENCE.put(container, 75);
        BLOCKS_TO_EXPERIENCE.put(vault, 225);
        BLOCKS_TO_EXPERIENCE.put(reinforcedContainer, 250);
        BLOCKS_TO_EXPERIENCE.put(reinforcedVault, 750);


        UNITS_TO_EXPERIENCE.put(dagger, 30);
        UNITS_TO_EXPERIENCE.put(nova, 24);
        UNITS_TO_EXPERIENCE.put(flare, 14);
        UNITS_TO_EXPERIENCE.put(poly, 80);
        UNITS_TO_EXPERIENCE.put(mace, 110);
        UNITS_TO_EXPERIENCE.put(pulsar, 64);
        UNITS_TO_EXPERIENCE.put(horizon, 68);
        UNITS_TO_EXPERIENCE.put(mega, 92);
        UNITS_TO_EXPERIENCE.put(risso, 60);
        UNITS_TO_EXPERIENCE.put(retusa, 54);
        UNITS_TO_EXPERIENCE.put(stell, 170);
        UNITS_TO_EXPERIENCE.put(merui, 136);
        UNITS_TO_EXPERIENCE.put(elude, 120);
        UNITS_TO_EXPERIENCE.put(fortress, 180);
        UNITS_TO_EXPERIENCE.put(quasar, 128);
        UNITS_TO_EXPERIENCE.put(zenith, 140);
        UNITS_TO_EXPERIENCE.put(quad, 1200);
        UNITS_TO_EXPERIENCE.put(minke, 120);
        UNITS_TO_EXPERIENCE.put(oxynoe, 112);
        UNITS_TO_EXPERIENCE.put(locus, 420);
        UNITS_TO_EXPERIENCE.put(cleroi, 220);
        UNITS_TO_EXPERIENCE.put(avert, 220);
        UNITS_TO_EXPERIENCE.put(scepter, 1800);
        UNITS_TO_EXPERIENCE.put(vela, 1640);
        UNITS_TO_EXPERIENCE.put(antumbra, 1440);
        UNITS_TO_EXPERIENCE.put(oct, 3000);
        UNITS_TO_EXPERIENCE.put(bryde, 182);
        UNITS_TO_EXPERIENCE.put(cyerce, 174);
        UNITS_TO_EXPERIENCE.put(precept, 1000);
        UNITS_TO_EXPERIENCE.put(anthicus, 580);
        UNITS_TO_EXPERIENCE.put(obviate, 460);
        UNITS_TO_EXPERIENCE.put(reign, 4000);
        UNITS_TO_EXPERIENCE.put(corvus, 3000);
        UNITS_TO_EXPERIENCE.put(eclipse, 4000);
        UNITS_TO_EXPERIENCE.put(sei, 2000);
        UNITS_TO_EXPERIENCE.put(aegires, 2200);
        UNITS_TO_EXPERIENCE.put(vanquish, 2200);
        UNITS_TO_EXPERIENCE.put(tecta, 1460);
        UNITS_TO_EXPERIENCE.put(quell, 1200);
        UNITS_TO_EXPERIENCE.put(omura, 4400);
        UNITS_TO_EXPERIENCE.put(navanax, 4000);
        UNITS_TO_EXPERIENCE.put(conquer, 4400);
        UNITS_TO_EXPERIENCE.put(collaris, 3600);
        UNITS_TO_EXPERIENCE.put(disrupt, 2400);
    }
}
