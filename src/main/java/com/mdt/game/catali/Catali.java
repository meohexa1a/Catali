package com.mdt.game.catali;

import com.mdt.game.catali.config.CataliGeneralConfig;
import com.mdt.mindustry.utils.MindustryMap;
import com.mdt.mindustry.utils.MindustryWorld;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class Catali {
    private boolean mapLoaded = false;

    public void start() {
        CataliGeneralConfig.onModeLoaded();

    }

    public void stop() {
        CataliGeneralConfig.onModeUnload();

        mapLoaded = false;
    }

    public void refresh() {
        if (!mapLoaded) loadMap();


    }

    // !----------------------------------------------------!

    private void loadMap() {
        MindustryWorld.loadMap(MindustryMap.getRandom())
            .onSuccess(u -> this.mapLoaded = true);
    }
}
