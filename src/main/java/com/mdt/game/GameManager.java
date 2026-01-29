package com.mdt.game;

import arc.util.Timer;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GameManager {

    {
        Timer.schedule(this::init, 3);
    }

    private void init() {

    }
}
