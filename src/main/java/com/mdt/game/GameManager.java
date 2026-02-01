package com.mdt.game;

import arc.Events;
import arc.util.Timer;

import com.mdt.game.catali.Catali;

import com.mdt.game.catali.CataliEventListener;

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
    private final CataliEventListener eventListener;

    {
        Timer.schedule(this::init, 5);
        log.info("Catali.io start after 5 seconds...");
    }

    private void init() {
        listenEvent();
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
            logEventError(() -> eventListener.listen(e));
        });

        Events.on(EventType.PlayerLeave.class, e -> logEventError(() -> gameControl.listen(e)));

        Events.on(EventType.TapEvent.class, e -> logEventError(() -> eventListener.listen(e)));

        Events.on(EventType.BuildingBulletDestroyEvent.class, e -> logEventError(() -> eventListener.listen(e)));

        Events.on(EventType.UnitBulletDestroyEvent.class, e -> logEventError(() -> eventListener.listen(e)));

        Events.on(EventType.UnitDestroyEvent.class, e -> logEventError(() -> eventListener.listen(e)));
    }

    // !-------------------------------------------------------!

    private void logEventError(Runnable r) {
        try {
            r.run();
        } catch (Exception e) {
            log.error("Event error", e);
        }
    }
}
