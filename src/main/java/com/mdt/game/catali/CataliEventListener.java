package com.mdt.game.catali;

import lombok.RequiredArgsConstructor;

import mindustry.game.EventType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class CataliEventListener {
    private final Catali catali;

    private final CataliInterfaceService interfaceService;

    // !---------------------------------------------------!

    public void listen(EventType.PlayerJoin event) {
        if (!catali.isActive()) return;

        interfaceService.showWelcomeMenu(event.player);
    }

    public void listen(EventType.TapEvent event) {
        if (!catali.isActive()) return;

    }
}
