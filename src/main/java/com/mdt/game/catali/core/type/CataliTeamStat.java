package com.mdt.game.catali.core.type;

import com.mdt.game.catali.config.CataliBalanceConfig;
import com.mdt.game.catali.core.enums.CataliCommonUpgrade;

import java.util.function.Consumer;

public final class CataliTeamStat {

    private int level = 1;
    private long expenrience = 0;
    private long required = 20;

    private int healthUpgrades;
    private int damageUpgrades;
    private int healingUpgrades;
    private int expenrienceUpgrades;

    private int commonUpgrades;
    private int rareUpgrades;

    // -------------------------------------------------------------

    public synchronized boolean earnExperience(long amount, Consumer<Long> notify) {
        if (amount <= 0) return false;

        var exp = (long) (amount * (level < 25 ? 4 : 1) *
                (1 + expenrienceUpgrades * CataliBalanceConfig.EXP_MULTIPLIER));
        expenrience += exp;
        notify.accept(exp);

        if (expenrience < required)
            return false;

        while (expenrience >= required) {
            expenrience -= required;
            level++;
            required += getNextRequired();

            if (level % 5 == 0)
                rareUpgrades++;
            else
                commonUpgrades++;

            if (level % 20 == 0)
                rareUpgrades++;
        }

        return true;
    }

    public void useCommonUpgrade(CataliCommonUpgrade statType, int amount) {
        for (int i = 0; i < amount; i++)
            useCommonUpgrade(statType);
    }

    public synchronized void useCommonUpgrade(CataliCommonUpgrade statType) {
        if (commonUpgrades <= 0 || statType == null) return;

        commonUpgrades--;
        switch (statType) {
            case HEALTH -> healthUpgrades++;
            case DAMAGE -> damageUpgrades++;
            case HEALING -> healingUpgrades++;
            case EXPERIENCE -> expenrienceUpgrades++;
        }
    }

    public void useRareUpgrade() {
        rareUpgrades--;
    }

    // !-----------------------------!

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
