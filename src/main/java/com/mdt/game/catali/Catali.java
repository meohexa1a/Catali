package com.mdt.game.catali;

import com.mdt.game.catali.spawner.CataliBlockSpawner;
import com.mdt.game.catali.spawner.CataliEnemySpawner;
import com.mdt.mindustry.command.CommandRegisterService;
import com.mdt.mindustry.popup.PopupProvider;
import com.mdt.mindustry.popup.PopupRegisterService;
import com.mdt.mindustry.utils.MindustryMap;
import com.mdt.mindustry.utils.MindustryWorld;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import mindustry.game.EventType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class Catali {
    private final CommandRegisterService commandRegisterService;
    private final PopupRegisterService popupRegisterService;

    private final CataliBlockSpawner blockSpawner;
    private final CataliEnemySpawner enemySpawner;

    private final CataliEventListener eventListener;
    private final CataliInterfaceService interfaceService;

    private boolean mapLoaded = false;
    public @Getter boolean isActive = false;

    // !-----------------------------------------------------!

    public void start() {
        isActive = true;

        commandRegisterService.register("catali", interfaceService.clientCommands(), interfaceService.consoleCommands());
        popupRegisterService.register("catali", Set.of(new PopupProvider(interfaceService::popupContents)));
    }

    public void stop() {
        mapLoaded = false;
        isActive = false;

        commandRegisterService.unregister("catali");
        popupRegisterService.unregister("catali");
    }

    public void refresh() {
        if (!mapLoaded) loadMap();

        blockSpawner.refresh();
        enemySpawner.refresh();
    }


    // !----------------------------------------------------!

    private void loadMap() {
        MindustryWorld.loadMap(MindustryMap.getRandom()).onSuccess(u -> this.mapLoaded = true);
    }
}
