package com.mdt;

import arc.util.CommandHandler;

import com.mdt.game.GameManager;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import mindustry.mod.Plugin;

import org.codejargon.feather.Feather;

@Slf4j
public final class MintyMDTPlugin extends Plugin {
    private static @Getter CommandHandler serverHandler, clientHandler = null;
    private static @Getter Feather feather;
    private volatile boolean isStarted = false;

    // !--------------------------------------------------------!

    @Override
    public void registerServerCommands(CommandHandler handler) {
        MintyMDTPlugin.serverHandler = handler;
        initMintyMDT();
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        MintyMDTPlugin.clientHandler = handler;
        initMintyMDT();
    }

    // !--------------------------------------------------------!

    private synchronized void initMintyMDT() {
        if (isStarted || serverHandler == null || clientHandler == null) return;
        this.isStarted = true;

        try {
            MintyMDTPlugin.feather = Feather.with();
            var gameManager = feather.instance(GameManager.class);

            gameManager.init();
        } catch (Exception e) {
            log.error("Failed to initialize. Exit application", e);
            System.exit(-1);
        }

        log.info("MintyMDT Plugin Framework - v3.0");
    }
}
