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

    public static int getExperienceForBlock(Block block) {
        return block.health;
    }

    public static int getExperienceForUnit(UnitType unit) {
        return (int) unit.health;
    }
}
