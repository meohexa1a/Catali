package com.mdt.game;

import lombok.Locked;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor
final class GameControl {

    private GCState gcState = new GCState.RequireToWake();

    // !--------------------------------------------------------!

    public void refresh() {

    }

    public void listen() {

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
    void keep(long additiveTime) {
        if (time == -1) time = System.currentTimeMillis() + additiveTime;
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