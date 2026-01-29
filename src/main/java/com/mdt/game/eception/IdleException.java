package com.mdt.game.eception;

import com.mdt.common.signal.Failure;
import com.mdt.mindustry.utils.exception.WorldLoadException;

import java.io.IOException;

public sealed interface IdleException extends Failure {

    record IdleMapFileNotFound() implements IdleException {

    }

    record IdleMapFileCorrupted(IOException exception) implements IdleException {

    }

    record LoadError(WorldLoadException worldLoadException) implements IdleException {

    }
}
