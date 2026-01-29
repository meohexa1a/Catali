package com.mdt.game;

import com.mdt.common.utils.CommonUtils;
import lombok.Locked;
import lombok.RequiredArgsConstructor;
import mindustry.game.EventType;
import mindustry.gen.Groups;

import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor
final class GameControl {

    private GCState gcState = new GCState.RequireToWake();

    // !--------------------------------------------------------!

    @Locked
    public void refresh() {
        switch (gcState) {
            case GCState.Idling idling -> {
            }
            case GCState.Playing playing -> {
            }
            case GCState.RequireToWake requireToWake -> {
            }
        }
    }

    @Locked
    public void listen(EventType.PlayerLeave event) {
        if (gcState instanceof GCState.Playing(TimeHolder timeHolder) && Groups.player.isEmpty())
            timeHolder.keep();
    }

    @Locked
    public void listen(EventType.PlayerJoin event) {
        switch (gcState) {
            case GCState.Idling ignored -> gcState = new GCState.RequireToWake();
            case GCState.Playing(TimeHolder timeHolder ) -> timeHolder.clear();
            case GCState.RequireToWake ignored -> CommonUtils.doNothing();
        }
    }
}

sealed interface GCState {
    record Playing(TimeHolder timeHolder) implements GCState {

    }

    record Idling() implements GCState {

    }

    record RequireToWake() implements GCState {

    }
}

class TimeHolder {
    private long time = -1;

    @Locked
    void keep() {
        if (time == -1) time = System.currentTimeMillis() + CommonConfig.WORLD_IDLE_TIMEOUT;
    }

    @Locked
    void clear() {
        time = -1;
    }

    @Locked
    boolean isOver() {
        return System.currentTimeMillis() > time;
    }
}