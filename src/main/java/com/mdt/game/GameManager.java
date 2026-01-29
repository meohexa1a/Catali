package com.mdt.game;

import arc.util.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
final class GameManager {
    private final GameControl gameControl;

    {
        Timer.schedule(this::init, 5);
    }

    private void init() {
        listenEvent();
        registerCommands();
        Timer.schedule(this::refresh, 0, 1);
    }

    // !-----------------------------------------------------------------!

    private void refresh() {
        try {
            gameControl.refresh();
        } catch (Exception e) {
            log.error("Refresh failed", e);
        }
    }

    private void listenEvent() {

    }

    private void registerCommands() {

    }
}
