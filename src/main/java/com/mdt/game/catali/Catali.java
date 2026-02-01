package com.mdt.game.catali;

import com.mdt.game.catali.core.CataliTeamService;
import com.mdt.game.catali.spawner.CataliBlockSpawner;
import com.mdt.game.catali.spawner.CataliEnemySpawner;
import com.mdt.mindustry.command.ClientCommand;
import com.mdt.mindustry.utils.MindustryMap;
import com.mdt.mindustry.utils.MindustryWorld;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class Catali {
    private final CataliBlockSpawner blockSpawner;
    private final CataliEnemySpawner enemySpawner;

    private final CataliTeamService teamService;

    private boolean mapLoaded = false;
    public @Getter boolean isActive = false;

    // !-----------------------------------------------------!

    public Set<ClientCommand> clientCommands() {
        var commands = new HashSet<ClientCommand>();

        commands.add(ClientCommand.builder()
            .prefix("leave").prefix("l")
            .description("Withdraw from your current force")

            .action((args, player) -> teamService.requestLeaveTeam(player))
            .build());

        commands.add(ClientCommand.builder()
            .prefix("disband").prefix("d")
            .description("Dissolve your command structure (leader only)")

            .action((args, player) -> teamService.requestDisbandTeam(player))
            .build());

        commands.add(ClientCommand.builder()
            .prefix("members").prefix("m")
            .description("Show member panel")

            .action((args, player) -> player.sendMessage("..."))
            .build());

        return commands;
    }

    public void start() {
        isActive = true;
    }

    public void stop() {
        mapLoaded = false;
        isActive = false;
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
