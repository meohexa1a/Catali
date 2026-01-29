package com.mdt.game;

import arc.files.Fi;
import com.mdt.MintyMDTPlugin;
import com.mdt.common.signal.Result;
import com.mdt.common.signal.Unit;
import com.mdt.game.eception.IdleException;
import com.mdt.mindustry.utils.MindustryMap;
import com.mdt.mindustry.utils.MindustryWorld;
import lombok.Locked;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class IdleProgram {

    @Locked
    public Result<Unit, IdleException> load() {
        return loadFromInternal().recoverWith(e -> loadRandom());
    }

    // !--------------------------------------------------------!

    private Result<Unit, IdleException> loadFromInternal() {
        try (var res = MintyMDTPlugin.class.getResourceAsStream(CommonConfig.IDLE_MAP_DIR)) {
            if (res == null) return Result.error(new IdleException.IdleMapFileNotFound());

            var file = File.createTempFile("sleep", ".msav");
            Files.copy(res, file.toPath());

            return MindustryWorld.loadFile(new Fi(file))
                    .mapError(IdleException.LoadError::new);
        } catch (IOException e) {
             return Result.error(new IdleException.IdleMapFileCorrupted(e));
        }
    }

    private Result<Unit, IdleException> loadRandom() {
        return MindustryWorld.loadMap(MindustryMap.getRandom())
                .mapError(IdleException.LoadError::new);
    }
}
