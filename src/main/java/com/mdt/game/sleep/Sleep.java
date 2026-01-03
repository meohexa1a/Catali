package com.mdt.game.sleep;

import arc.Core;
import arc.files.Fi;
import com.mdt.MintyMDTPlugin;
import com.mdt.common.shared.utils.CommonUtils;
import com.mdt.mindustry.utils.MindustryMap;
import com.mdt.mindustry.utils.MindustryWorld;
import com.mdt.mindustry.utils.exception.WorldLoadException;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import lombok.Locked;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mindustry.Vars;
import mindustry.game.Gamemode;

import java.io.File;
import java.nio.file.Files;

@Slf4j
@Singleton
@RequiredArgsConstructor
public final class Sleep {
    private static final String SLEEP_MAP = "maps/sleep.msav";

    // !------------------------------------------------!

    private enum SleepState {
        INACTIVE,
        NOT_LOADED,
        LOADED;
    }

    private SleepState sleepState = SleepState.INACTIVE;

    // !------------------------------------------------!

    @Locked
    public void start() {
        this.sleepState = SleepState.NOT_LOADED;
    }

    @Locked
    public void stop() {
        switch (sleepState) {
            case INACTIVE -> CommonUtils.doNothing();

            case NOT_LOADED -> this.sleepState = SleepState.INACTIVE;
            case LOADED -> {
                Vars.logic.reset();

                this.sleepState = SleepState.INACTIVE;
            }
        }
    }

    @Scheduled(cron = "*/1 * * * * *")
    public void refresh() {
        Core.app.post(this::_refresh);
    }

    @Locked
    private void _refresh() {
        if (sleepState != SleepState.NOT_LOADED) return;

        log.info("Loading sleep map...");
        if (loadSleepMapFromInternalPackage()) {
            Vars.state.rules = Vars.state.map.applyRules(Gamemode.sandbox);
            Vars.state.rules.canGameOver = false;

            this.sleepState = SleepState.LOADED;
            return;
        }

        log.info("Cannot load fron internal package. Trying to load with fallback function...");
        if (loadSleepMapFallback()) {
            Vars.state.rules = Vars.state.map.applyRules(Gamemode.sandbox);
            Vars.state.rules.canGameOver = false;

            this.sleepState = SleepState.LOADED;
        }
    }

    // !------------------------------------------------!

    private boolean loadSleepMapFromInternalPackage() {
        try (var resource = MintyMDTPlugin.class.getResourceAsStream(SLEEP_MAP)) {
            if (resource == null) return false;

            var file = File.createTempFile("sleep", ".msav");
            Files.copy(resource, file.toPath());

            return MindustryWorld.loadFile(new Fi(file))
                    .onError(e -> logLoadError(e, "Failed to load sleep map from internal package resource"))
                    .isSuccess();
        } catch (Exception e) {
            log.warn("Failed to load sleep map from internal package resource", e);
            return false;
        }
    }

    private boolean loadSleepMapFallback() {
        return MindustryWorld.loadMap(MindustryMap.getRandomDefaultMap())
                .onError(e -> logLoadError(e, "Failed to load sleep map"))
                .isSuccess();
    }

    private void logLoadError(WorldLoadException exception, String messagePrefix) {
        switch (exception) {
            case WorldLoadException.InvalidInputException ignored -> log.warn("{}: invalid file", messagePrefix);
            case WorldLoadException.WorldLoadRuntimeException runtime ->
                    log.warn("{}: runtime exception", messagePrefix, runtime.cause());
        }
    }
}
