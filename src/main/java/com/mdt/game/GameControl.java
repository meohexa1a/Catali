package com.mdt.game;

import com.mdt.common.utils.CommonUtils;
import com.mdt.game.catali.Catali;

import lombok.Locked;
import lombok.RequiredArgsConstructor;

import mindustry.game.EventType;
import mindustry.gen.Groups;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
final class GameControl {
    private final Catali catali;

    private GCState gcState = new GCState.RequireToWake();

    // !--------------------------------------------------------!

    @Locked
    public void refresh() {
        switch (gcState) {
            case GCState.Idling ignored -> CommonUtils.doNothing();

            case GCState.Playing playing -> {
                if (!playing.timeHolder().isOver()) catali.refresh();
                else IdleProgram.load().onSuccess(u -> {
                    catali.stop();
                    this.gcState = new GCState.Idling();
                });
            }

            case GCState.RequireToWake ignored -> {
                catali.start();
                this.gcState = new GCState.Playing(new TimeHolder());
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
            case GCState.Idling ignored -> this.gcState = new GCState.RequireToWake();
            case GCState.Playing(TimeHolder timeHolder) -> timeHolder.clear();
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

final class TimeHolder {
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
