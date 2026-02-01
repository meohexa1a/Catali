package com.mdt.game.catali.store;

import com.mdt.game.catali.core.CataliTeamUnitConfig;
import com.mdt.game.catali.enums.UnitTier;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CataliTeamStat {
    private final int teamId;

    private int level = 0;
    private int expenrience = 0;
    private int required = 100;

    private int gachaPoint = 0;
    private int unitTier = UnitTier.Tier1.ordinal();
    private int respawnSlot = CataliTeamUnitConfig.RESPAWN_START_SLOT;
}
