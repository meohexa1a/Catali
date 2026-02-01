package com.mdt.game.catali.store;

import com.mdt.game.catali.core.CataliCommonConfig;
import com.mdt.game.catali.core.CataliTeamUnitConfig;
import com.mdt.game.catali.enums.UnitTier;

import lombok.Getter;
import lombok.Locked;
import lombok.RequiredArgsConstructor;

@Getter(onMethod_ = @Locked)
@RequiredArgsConstructor
public final class CataliTeamStat {
    private final int teamId;

    private int lastNotifyLevel = 0;
    private int level = 0;
    private int experience = 0;
    private int required = 100;

    private int gachaPoint = 0;
    private int unitTier = UnitTier.Tier1.ordinal();
    private int respawnSlot = CataliTeamUnitConfig.RESPAWN_START_SLOT;

    // !----------------------------------------------------!

    @Locked
    public boolean earnExp(int exp) {
        experience += exp;
        while (experience > required) {
            level++;
            gachaPoint++;

            experience -= required;
            required += (int) (required * CataliCommonConfig.EXP_REQ_MULTIPLIER);
        }

        if (level < lastNotifyLevel + 10) return false;

        lastNotifyLevel = level;
        return true;
    }

    @Locked
    public void useGachaPoint(int amount) {
        gachaPoint -= amount; // accept negative value
    }

    @Locked
    public void levelUpUnitTier(boolean isDouble) {
        unitTier += isDouble ? 2 : 1;
    }

    @Locked
    public void addRespawnSlot(boolean isDouble) {
        respawnSlot += isDouble ? 2 : 1;
    }
}
