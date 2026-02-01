package com.mdt.game.catali;

import com.mdt.common.utils.CommonUtils;
import com.mdt.game.catali.core.CataliTeamService;
import com.mdt.game.catali.spawner.CataliBlockSpawner;
import com.mdt.game.catali.spawner.CataliEnemySpawner;
import com.mdt.mindustry.command.ClientCommand;
import com.mdt.mindustry.command.CommandRegisterService;
import com.mdt.mindustry.command.ConsoleCommand;
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
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class Catali {
    private final CommandRegisterService commandRegisterService;
    private final PopupRegisterService popupRegisterService;

    private final CataliBlockSpawner blockSpawner;
    private final CataliEnemySpawner enemySpawner;

    private final CataliTeamService teamService;

    private final CataliInterfaceService interfaceService;

    private boolean mapLoaded = false;
    public @Getter boolean isActive = false;

    // !-----------------------------------------------------!

    private Set<ClientCommand> clientCommands() {
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
            .prefix("leave").prefix("l")
            .description("Withdraw from your current force")

            .action((args, player) -> teamService.requestLeaveTeam(player))
            .build());

        commands.add(ClientCommand.builder()
            .prefix("members").prefix("m")
            .description("Show member panel")

            .action((args, player) -> player.sendMessage("..."))
            .build());

        commands.add(ClientCommand.builder()
            .prefix("upgrades").prefix("u")
            .description("Show upgrade panel")

            .action((args, player) -> player.sendMessage("..."))
            .build());

        return commands;
    }

    private Set<ConsoleCommand> consoleCommands() {
        var commands = new HashSet<ConsoleCommand>();

        commands.add(ConsoleCommand.builder()
            .prefix("catali-status")
            .description("Show catali game mode status")

            .action(CommonUtils::doNothing)
            .build());

        return commands;
    }

    public void start() {
        isActive = true;

        commandRegisterService.register("catali", clientCommands(), consoleCommands());
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

    public void listen(EventType.TapEvent event) {
        if (!isActive) return;

    }

    // !----------------------------------------------------!

    private void loadMap() {
        MindustryWorld.loadMap(MindustryMap.getRandom()).onSuccess(u -> this.mapLoaded = true);
    }
}
