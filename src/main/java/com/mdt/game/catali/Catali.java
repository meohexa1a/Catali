package com.mdt.game.catali;

import jakarta.inject.Singleton;
import lombok.Locked;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class Catali {
    private boolean isStarted = false;

    // !----------------------------------------------------!

    @Locked
    public void start() {

    }

    @Locked
    public void stop() {
    }

}
