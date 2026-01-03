package com.mdt.game;

import com.mdt.game.sleep.Sleep;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class GameControl {
    private final Sleep sleep;

}
