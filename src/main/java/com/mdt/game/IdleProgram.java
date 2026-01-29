package com.mdt.game;

import arc.files.Fi;
import com.mdt.MintyMDTPlugin;
import com.mdt.common.signal.Result;
import com.mdt.common.signal.Unit;
import com.mdt.game.eception.IdleException;
import com.mdt.mindustry.utils.MindustryMap;
import com.mdt.mindustry.utils.MindustryWorld;
import lombok.Locked;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@UtilityClass
class IdleProgram {

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

            return MindustryWorld.loadFile(new Fi(file))
                    .mapError(IdleException.MapLoadError::new);
        } catch (IOException e) {
            return Result.error(new IdleException.IdleMapFileCorrupted(e));
        }
    }

    private static Result<Unit, IdleException> loadRandom() {
        return MindustryWorld.loadMap(MindustryMap.getRandom())
                .mapError(IdleException.MapLoadError::new);
    }
}
