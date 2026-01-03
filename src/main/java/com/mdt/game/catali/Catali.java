package com.mdt.game.catali;

import arc.Core;
import com.mdt.common.shared.utils.CommonUtils;
import com.mdt.game.catali.config.CataliBalanceConfig;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.Locked;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mindustry.Vars;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class Catali {

    // !----------------------------------------------------!

    private enum CataliState {
        INACTIVE,
        NOT_LOADED,
        LOADED;
    }

    private CataliState cataliState = CataliState.INACTIVE;

    // !----------------------------------------------------!

    @Locked
    public void start() {
        this.cataliState = CataliState.NOT_LOADED;

        CataliBalanceConfig.onModeLoaded();
    }

    @Locked
    public void stop() {
        switch (cataliState) {
            case INACTIVE -> CommonUtils.doNothing();

            case NOT_LOADED -> this.cataliState = CataliState.INACTIVE;
            case LOADED -> {
                Vars.logic.reset();

                this.cataliState = CataliState.INACTIVE;
            }
        }

        CataliBalanceConfig.onModeUnload();
    }

    // !----------------------------------------------------!

    @Scheduled(cron = "*/1 * * * * *")
    public void refresh() {
        Core.app.post(this::_refresh);
    }

    @Locked
    private void _refresh() {
    }


}
