package com.mdt.game.catali;

import arc.Core;
import com.mdt.common.shared.utils.CommonUtils;
import com.mdt.game.catali.config.CataliGeneralConfig;
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

        CataliGeneralConfig.onModeLoaded();
    }

    @Locked
    public void stop() {
        switch (cataliState) {
            case INACTIVE -> CommonUtils.doNothing();

            case NOT_LOADED -> this.cataliState = CataliState.INACTIVE;
            case LOADED -> {
                unload();
                Vars.logic.reset();

                this.cataliState = CataliState.INACTIVE;
            }
        }

        CataliGeneralConfig.onModeUnload();
    }

    // !----------------------------------------------------!

    @Scheduled(cron = "*/1 * * * * *")
    public void refresh() {
        Core.app.post(this::_refresh);
    }

    @Locked
    private void _refresh() {
        switch (cataliState) {
            case INACTIVE -> CommonUtils.doNothing();
            case NOT_LOADED -> load();
            case LOADED -> __refresh();
        }
    }

    @Locked
    private void load() {

    }

    @Locked
    private void unload() {

    }

    @Locked
    private void __refresh() {

    }



}
