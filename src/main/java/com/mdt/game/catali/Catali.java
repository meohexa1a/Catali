package com.mdt.game.catali;

import com.mdt.game.catali.config.CataliBalanceConfig;
import jakarta.inject.Singleton;
import lombok.Locked;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class Catali {
    private boolean isStarted = false;

    // !----------------------------------------------------!

    @Locked
    public void start() {
        this.isStarted = true;

        CataliBalanceConfig.onModeLoaded();
    }

    @Locked
    public void stop() {
        this.isStarted = false;

        CataliBalanceConfig.onModeUnload();
    }

    // !----------------------------------------------------!



}
