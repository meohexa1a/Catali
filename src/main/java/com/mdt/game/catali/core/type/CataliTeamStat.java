package com.mdt.game.catali.core.type;

import com.mdt.game.catali.config.CataliGeneralConfig;
import com.mdt.game.catali.core.enums.CataliCommonUpgrade;
import lombok.Getter;
import lombok.Locked;

@Getter(onMethod_ = @Locked)
public final class CataliTeamStat {
    private int level = 1;
    private long experience = 0;
    private long required = 20;

    private int healthUpgrades;
    private int damageUpgrades;
    private int healingUpgrades;
    private int experienceUpgrades;

    private int commonUpgrades;
    private int rareUpgrades;

    private long pendingExp = 0;

    // !-----------------------------------------------------------!

    @Locked
    public void pushPendingExp(long amount) {
        pendingExp += amount;
    }

    public record ExpEarnResult(long gainedExp, int levelUp) {

    }

    @Locked
    public ExpEarnResult earnExperience() {
        if (pendingExp <= 0) return new ExpEarnResult(0, 0);

        var exp = (long) (pendingExp
                * (level < CataliGeneralConfig.BEGINNER_LEVEL_LEAST_THAN ? CataliGeneralConfig.BEGINNER_EXP_MULTIPLIER : 1)
                * (1 + experienceUpgrades * CataliGeneralConfig.EXP_MULTIPLIER));
        experience += exp;

        pendingExp = 0;
        if (experience < required) return new ExpEarnResult(exp, 0);

        int levelUp = 0;
        while (experience >= required) {
            experience -= required;
            level++;
            levelUp++;
            required += getNextRequired();

            if (level % 20 == 0) rareUpgrades++;
            if (level % 5 == 0) rareUpgrades++;
            else commonUpgrades++;
        }

        return new ExpEarnResult(exp, levelUp);
    }

    @Locked
    public void useCommonUpgrade(CataliCommonUpgrade statType, int amount) {
        for (int i = 0; i < amount; i++) useCommonUpgrade(statType);
    }

    @Locked
    public void useCommonUpgrade(CataliCommonUpgrade statType) {
        if (commonUpgrades <= 0 || statType == null) return;

        commonUpgrades--;
        switch (statType) {
            case HEALTH -> healthUpgrades++;
            case DAMAGE -> damageUpgrades++;
            case HEALING -> healingUpgrades++;
            case EXPERIENCE -> experienceUpgrades++;
        }
    }

    @Locked
    public void useRareUpgrade() {
        rareUpgrades--;
    }

    // !----------------------------------------!

    private long getNextRequired() {
        return switch ((level - 1) / 25) {
            case 0 -> 20L;
            case 1 -> 50L;
            case 2 -> 200L;
            case 3 -> 400L;
            case 4 -> 750L;
            case 5 -> 1200L;
            case 6 -> 2500L;
            case 7 -> 4500L;
            case 8 -> 8000L;
            case 9 -> 12000L;
            case 10 -> 18000L;
            default -> 25000L;
        };
    }
}