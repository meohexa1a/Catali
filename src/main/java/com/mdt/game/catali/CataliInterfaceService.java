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
            .title("[accent]WELCOME, COMMANDER")

            .message("""
                [white]The battlefield awaits your command.

                [lightgray]From this moment onward,
                every unit deployed,
                every order issued,
                and every loss sustained
                will be under your authority.

                [gray]Command is established by selecting a deployment point
                directly on the battlefield.""")

            .button("[accent]Command Briefing", this::showQuickGuideMenu)
            .button("[lightgray]Credits", CommonUtils::doNothing)
            .row()

            .button(common_button_close(player), CommonUtils::doNothing)
            .row()

            .completeContent()
            .build()
        );
    }

    private void showQuickGuideMenu(Player player) {
        menuService.showMenu(player, MenuOption.builder()
            .title("[accent]COMMAND BRIEFING")

            .message("""
                [white]Operational Overview

                [lightgray]• Tap the battlefield to deploy your command unit
                • Each commander leads a single force
                • Units act under your command authority

                [gray]Use commands and panels to manage your force.""")

            .button("[accent]Understood", CommonUtils::doNothing)
            .row()

            .completeContent()
            .build()
        );
    }

    // !------------------------------------------------------!

    private String common_button_close(Player player) {
        return "[scarlet]Close[]";
    }

    // !------------------------------------------------------!

    // --- Error / Validation Messages ---

    public void sendAlreadyCommander(Player p) {
        p.sendMessage("[scarlet]You already command a force.");
    }

    public void sendCommandCreateFailed(Player p) {
        p.sendMessage("[scarlet]Command initialization failed.");
    }

    public void sendNotInTeam(Player p) {
        p.sendMessage("[scarlet]You are not assigned to any force.");
    }

    public void sendLeaderCannotLeave(Player p) {
        p.sendMessage("[scarlet]A commander cannot abandon their force.");
    }

    public void sendNotACommander(Player p) {
        p.sendMessage("[scarlet]You are not a commander.");
    }

    public void sendNoAuthority(Player p) {
        p.sendMessage("[scarlet]You have no authority.");
    }

    public void sendTargetAlreadyInTeam(Player p) {
        p.sendMessage("[scarlet]Target already serves another force.");
    }

    public void sendTargetNotUnderCommand(Player p) {
        p.sendMessage("[scarlet]Target is not under your command.");
    }

    public void sendTargetNotMember(Player p) {
        p.sendMessage("[scarlet]Target is not part of your force.");
    }

    // --- Success / Status Messages ---

    public void sendCommandCreated(Player p) {
        p.sendMessage("[accent]Command established.\n[white]You are now leading a new force.");
    }

    public void sendLeaveSuccess(Player p) {
        p.sendMessage("[accent]You have left the force.");
    }

    public void sendJoinedYourCommand(Player leader, Player target) {
        leader.sendMessage(Strings.format("[accent]{} has joined your command.", target.name));
    }

    public void sendDismissedSuccess(Player leader, Player target) {
        leader.sendMessage(Strings.format("[accent]{} has been dismissed.", target.name));
    }

    public void sendCommandTransferred(Player leader, Player newLeader) {
        leader.sendMessage(Strings.format("[accent]Command transferred to {}.", newLeader.name));
    }

    // --- Messages to Target/Member ---

    public void sendEnlistedBy(Player target, Player leader) {
        target.sendMessage(Strings.format("[accent]You have been enlisted by <@[white]{}[]>.", leader.name));
    }

    public void sendYouWereDismissed(Player target, Player leader) {
        target.sendMessage(Strings.format("[scarlet]You were dismissed by <@[white]{}[]>.", leader.name));
    }

    public void sendYouAreNowCommander(Player newLeader) {
        newLeader.sendMessage("[accent]You are now the commander.");
    }

    // --- Global/Regional Toasts ---

    public void toastCommanderEntered(Player commander) {
        Call.infoToast(Strings.format(
            "[accent]Commander <@[white]{}[]> has entered Catali.io", commander.name), 5f);
    }

    public void toastTeamDisbanded(Player leader) {
        Call.infoToast(Strings.format(
            "[scarlet]Commander <@[white]{}[]> disbanded their force", leader.name), 5f);
    }

    public void toastNewCommander(Player newLeader) {
        Call.infoToast(Strings.format(
            "[accent]{} is now commanding a new force", newLeader.name), 4f);
    }
}
