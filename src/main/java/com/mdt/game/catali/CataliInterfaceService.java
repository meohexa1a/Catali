package com.mdt.game.catali;

import com.mdt.common.utils.CommonUtils;
import com.mdt.game.catali.core.CataliTeamService;
import com.mdt.mindustry.command.ClientCommand;
import com.mdt.mindustry.command.ConsoleCommand;
import com.mdt.mindustry.menu.MenuService;
import com.mdt.mindustry.popup.PopupContent;

import lombok.RequiredArgsConstructor;

import mindustry.gen.Player;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class CataliInterfaceService {
    private final MenuService menuService;

    private final CataliTeamService teamService;

    private final Provider<Catali> catali;

    // !------------------------------------------------------!

    Set<ClientCommand> clientCommands() {
        var commands = new HashSet<ClientCommand>();

        commands.add(ClientCommand.builder()
            .prefix("leave").prefix("l")
            .description("Withdraw from your current force")

            .action((args, player) -> {
                if (!catali.get().isActive()) return;
                teamService.requestLeaveTeam(player);
            })
            .build());

        commands.add(ClientCommand.builder()
            .prefix("disband").prefix("d")
            .description("Dissolve your command structure (leader only)")

            .action((args, player) -> {
                if (!catali.get().isActive()) return;
                teamService.requestDisbandTeam(player);
            })
            .build());

        commands.add(ClientCommand.builder()
            .prefix("members").prefix("m")
            .description("Show member panel")

            .action((args, player) -> {
                if (!catali.get().isActive()) return;
                player.sendMessage("...");
            })
            .build());

        commands.add(ClientCommand.builder()
            .prefix("upgrades").prefix("u")
            .description("Show upgrade panel")

            .action((args, player) -> {
                if (!catali.get().isActive()) return;
                player.sendMessage("...");
            })
            .build());

        return commands;
    }

    Set<ConsoleCommand> consoleCommands() {
        var commands = new HashSet<ConsoleCommand>();

        commands.add(ConsoleCommand.builder()
            .prefix("catali-status")
            .description("Show catali game mode status")

            .action(CommonUtils::doNothing)
            .build());

        return commands;
    }

    public List<PopupContent> popupContents(Player player) {
        if (!catali.get().isActive()) return List.of();

        var list = new ArrayList<PopupContent>();

        return list;
    }

    // !------------------------------------------------------!


}
