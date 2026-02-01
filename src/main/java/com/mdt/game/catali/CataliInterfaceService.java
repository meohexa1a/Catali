package com.mdt.game.catali;

import arc.util.Strings;

import com.mdt.common.utils.CommonUtils;
import com.mdt.game.catali.core.CataliTeamService;
import com.mdt.mindustry.command.ClientCommand;
import com.mdt.mindustry.command.ConsoleCommand;
import com.mdt.mindustry.menu.MenuOption;
import com.mdt.mindustry.menu.MenuService;
import com.mdt.mindustry.popup.PopupContent;

import lombok.RequiredArgsConstructor;

import mindustry.gen.Call;
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

    // !------------------------------------------------------!

    List<PopupContent> popupContents(Player player) {
        if (!catali.get().isActive()) return List.of();

        var list = new ArrayList<PopupContent>();

        return list;
    }

    // !------------------------------------------------------!

    public void showWelcomeMenu(Player player) {
        menuService.showMenu(player, MenuOption.builder()
            .title("")
            .message("")

            .button("Help", CommonUtils::doNothing)
            .button("Credit", CommonUtils::doNothing)
            .row()

            .button(common_button_close(player), CommonUtils::doNothing)

            .completeContent()
            .build());
    }

    // !------------------------------------------------------!

    private String common_button_close(Player player) {
        return "[scarlet]Close[]";
    }

    // !------------------------------------------------------!

    public void sendAlreadyCommander(Player p) {
        p.sendMessage("[scarlet]You already command a force.");
    }

    public void sendCommandCreateFailed(Player p) {
        p.sendMessage("[scarlet]Command initialization failed.");
    }

    public void sendCommandCreated(Player p) {
        p.sendMessage("[accent]Command established.\n" +
            "[white]You are now leading a new force.");
    }

    public void sendNotInTeam(Player p) {
        p.sendMessage("[scarlet]You are not assigned to any force.");
    }

    public void sendLeaderCannotLeave(Player p) {
        p.sendMessage("[scarlet]A commander cannot abandon their force.");
    }

    public void sendLeaveSuccess(Player p) {
        p.sendMessage("[accent]You have left the force.");
    }

    // !-----------------!

    public void toastCommanderEntered(Player commander) {
        Call.infoToast(Strings.format(
            "[accent]Commander <@[white]{}[]> has entered Catali.io", commander.name), 5f);
    }

    public void toastTeamDisbanded(Player commander) {
        Call.infoToast(Strings.format(
            "[scarlet]Commander <@[white]{}[]> disbanded their force", commander.name), 5f);
    }


}
