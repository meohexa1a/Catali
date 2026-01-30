package com.mdt.game;

import arc.Events;
import arc.util.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mindustry.game.EventType;

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
        Events.on(EventType.PlayerJoin.class, e -> {
            logEventError(() -> gameControl.listen(e));
        });

        Events.on(EventType.PlayerLeave.class, e -> {
            logEventError(() -> gameControl.listen(e));
        });
    }

    private void registerCommands() {

    }

    private void logEventError(Runnable r) {
        try {
            r.run();
        } catch (Exception e) {
            log.error("Event error", e);
        }
    }

    private void logCommandError(Runnable r) {
        try {
            r.run();
        } catch (Exception e) {
            log.error("Command error", e);
        }
    }
}
