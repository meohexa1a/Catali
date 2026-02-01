package com.mdt.game;

import arc.files.Fi;

import com.mdt.MintyMDTPlugin;
import com.mdt.common.signal.Result;
import com.mdt.common.signal.Unit;
import com.mdt.common.utils.CommonUtils;
import com.mdt.game.catali.Catali;

import com.mdt.game.eception.IdleException;
import com.mdt.mindustry.utils.MindustryMap;
import com.mdt.mindustry.utils.MindustryWorld;

import lombok.Locked;
import lombok.RequiredArgsConstructor;

import lombok.experimental.UtilityClass;

import mindustry.game.EventType;
import mindustry.gen.Groups;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
final class GameControl {
    private final Catali catali;

    private GCState gcState = new GCState.RequireToWake();

    // !--------------------------------------------------------!

    @Locked
    public void refresh() {
        switch (gcState) {
            case GCState.Idling ignored -> CommonUtils.doNothing();

            case GCState.Playing playing -> {
                if (!playing.timeHolder().isOver())
                    catali.refresh();
                else IdleUtils.load().onSuccess(u -> {
                    catali.stop();
                    this.gcState = new GCState.Idling();
                });
            }

            case GCState.RequireToWake ignored -> {
                catali.start();
                this.gcState = new GCState.Playing(new TimeHolder());
            }
        }
    }

    @Locked
    public void listen(EventType.PlayerLeave ignored) {
        if (gcState instanceof GCState.Playing(TimeHolder timeHolder) && Groups.player.isEmpty())
            timeHolder.keep();
    }

    @Locked
    public void listen(EventType.PlayerJoin ignored) {
        switch (gcState) {
            case GCState.Idling ignored1 -> this.gcState = new GCState.RequireToWake();
            case GCState.Playing(TimeHolder timeHolder) -> timeHolder.clear();
            case GCState.RequireToWake ignored1 -> CommonUtils.doNothing();
        }
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

final class TimeHolder {
    private long time = System.currentTimeMillis();

    @Locked
    void keep() {
        if (time == -1) time = System.currentTimeMillis() + CommonConfig.WORLD_IDLE_TIMEOUT;
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

@UtilityClass
class IdleUtils {

    @Locked
    public static Result<Unit, IdleException> load() {
        return loadFromInternal().recoverWith(e -> loadRandom());
    }

    // !--------------------------------------------------------!

    private static Result<Unit, IdleException> loadFromInternal() {
        try (var res = MintyMDTPlugin.class.getResourceAsStream(CommonConfig.IDLE_MAP_DIR)) {
            if (res == null) return Result.error(new IdleException.IdleMapFileNotFound());

            var file = File.createTempFile("sleep", ".msav");
            Files.copy(res, file.toPath());

            return MindustryWorld.loadFile(new Fi(file)).mapError(IdleException.MapLoadError::new);
        } catch (IOException e) {
            return Result.error(new IdleException.IdleMapFileCorrupted(e));
        }
    }

    private static Result<Unit, IdleException> loadRandom() {
        return MindustryWorld.loadMap(MindustryMap.getRandom()).mapError(IdleException.MapLoadError::new);
    }
}
