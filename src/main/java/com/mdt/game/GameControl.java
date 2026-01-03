package com.mdt.game;

import arc.Core;
import com.mdt.common.shared.type.TimeHolder;
import com.mdt.common.shared.utils.CommonUtils;
import com.mdt.game.catali.Catali;
import com.mdt.game.sleep.Sleep;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import lombok.Locked;
import lombok.RequiredArgsConstructor;
import mindustry.game.EventType;
import mindustry.gen.Groups;

@Singleton
@RequiredArgsConstructor
public class GameControl {
    private static final long WORLD_IDLE_TIMEOUT  = 3 * 60 * 1000;

    private final Sleep sleep;
    private final Catali catali;

    public sealed interface GCState {

        record Playing(TimeHolder lastLeaveHolder) implements GCState {

        }

        record Sleeping() implements GCState {

        }

        record RequireToWake() implements GCState {

        }
    }

    private GCState gcState = new GCState.Playing(new TimeHolder());

    // !---------------------------------------------------!

    @PostConstruct
    public void init() {
        Core.app.post(catali::start);
    }

    @Scheduled(cron = "*/1 * * * * *")
    public void refresh() {
        Core.app.post(this::_refresh);
    }

    @Locked
    private void _refresh() {
        switch (gcState) {
            case GCState.Playing playing -> {
                var holder = playing.lastLeaveHolder();
                if (!holder.isHolding() || !holder.isOver(WORLD_IDLE_TIMEOUT)) return;

                catali.stop();
                sleep.start();
                gcState = new GCState.Sleeping();
            }

            case GCState.RequireToWake ignored -> {
                sleep.stop();
                catali.start();
                gcState = new GCState.Playing(new TimeHolder());
            }

            default -> CommonUtils.doNothing();
        }
    }

    @Locked
    @EventListener
    public void listen(EventType.PlayerJoin ignore) {
        switch (gcState) {
            case GCState.Playing playing -> playing.lastLeaveHolder().release();
            case GCState.Sleeping ignored -> this.gcState = new GCState.RequireToWake();

            default -> CommonUtils.doNothing();
        }
    }

    @Locked
    @EventListener
    public void listen(EventType.PlayerLeave ignore) {
        if (gcState instanceof GCState.Playing(var lastLeaveHolder) && Groups.player.isEmpty())
            lastLeaveHolder.hold();
    }
}
