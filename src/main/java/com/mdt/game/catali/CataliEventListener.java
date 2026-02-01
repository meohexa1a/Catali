package com.mdt.game.catali;

import com.mdt.game.catali.core.CataliRpgService;
import com.mdt.game.catali.store.CataliTeamStore;

import lombok.RequiredArgsConstructor;

import mindustry.game.EventType;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class CataliEventListener {
    private final Catali catali;

    private final CataliTeamStore teamStore;
    private final CataliRpgService rpgService;
    private final CataliInterfaceService interfaceService;

    // !---------------------------------------------------!

    public void listen(EventType.PlayerJoin event) {
        if (!catali.isActive()) return;

        interfaceService.showWelcomeMenu(event.player);
    }

    public void listen(EventType.TapEvent event) {
        if (!catali.isActive()) return;
        if (teamStore.isPlayed(event.player.getInfo())) return;

        interfaceService.showDeployMenu(event.player, event.tile);
    }

    public void listen(EventType.BuildingBulletDestroyEvent event) {
        if (!catali.isActive()) return;

        rpgService.earnExp(event.bullet.team().id, event.build.block.health);
    }

    public void listen(EventType.UnitBulletDestroyEvent event) {
        if (!catali.isActive()) return;

        rpgService.earnExp(event.bullet.team().id, (int) event.unit.type.health);
    }

    public void listen(EventType.UnitDestroyEvent event) {
        if (!catali.isActive()) return;


    }
}
