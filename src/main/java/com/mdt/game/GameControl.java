package com.mdt.game;

import arc.Core;
import com.mdt.common.shared.type.TimeHolder;
import com.mdt.game.catali.Catali;
import com.mdt.game.sleep.Sleep;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import lombok.Locked;
import lombok.RequiredArgsConstructor;
import mindustry.game.EventType;
import mindustry.gen.Groups;

@Singleton
@RequiredArgsConstructor
public class GameControl {
    private static final long UNNAMED = 3 * 60 * 1000;

    private final Sleep sleep;
    private final Catali catali;

    private boolean isSleep = false;
    private boolean requireWake = false;
    private final TimeHolder lastLeaveHolder = new TimeHolder();

    // !---------------------------------------------------!

    @PostConstruct
    public void init() {
        Core.app.post(catali::start);
    }

    @Scheduled(cron = "*/1 * * * * *")
    public void refresh() {
        Core.app.post(this::_refresh);
    }

    @Locked
    private void _refresh() {
        if (!isSleep) {
            if (!lastLeaveHolder.isHolding() || !lastLeaveHolder.isOver(UNNAMED)) return;

            lastLeaveHolder.release();
            catali.stop();
            sleep.start();
            isSleep = true;
            requireWake = false;
        } else {
            if (!requireWake) return;

            sleep.stop();
            catali.start();
            isSleep = false;
            requireWake = false;
        }
    }

    @Locked
    @EventListener
    public void listen(EventType.PlayerJoin ignore) {
        if (isSleep) this.requireWake = true;
        else lastLeaveHolder.release();
    }

    @Locked
    @EventListener
    public void listen(EventType.PlayerLeave ignore) {
        if (!isSleep && Groups.player.isEmpty()) lastLeaveHolder.hold();
    }
}
