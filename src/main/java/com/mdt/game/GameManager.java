package com.mdt.game;

import arc.util.Timer;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
final class GameManager {

    {
        Timer.schedule(this::init, 5);
    }

    private void init() {
        listenEvent();
        Timer.schedule(this::refresh, 0, 1);
    }

    // !-----------------------------------------------------------------!

    private void refresh() {

    }

    private void listenEvent() {

    }
}
