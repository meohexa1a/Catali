package com.mdt.game.catali.core;

import arc.math.geom.Vec2;
import arc.util.Strings;

import com.mdt.game.catali.CataliInterfaceService;
import com.mdt.game.catali.store.CataliTeamStore;

import lombok.RequiredArgsConstructor;

import mindustry.content.UnitTypes;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Player;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class CataliTeamService {

    private final CataliTeamStore teamStore;

    private final CataliInterfaceService interfaceService;

    // !-------------------------------------------------------------!

    public void requestToPlaying(Player player, Vec2 position) {
        var info = player.getInfo();

        if (teamStore.isPlayed(info)) {
            interfaceService.sendAlreadyCommander(player);
            return;
        }

        int teamId = teamStore.getNextTeamId();
        if (!teamStore.createTeam(teamId, info)) {
            interfaceService.sendCommandCreateFailed(player);
            return;
        }

        var team = Team.get(teamId);

        UnitTypes.mega.spawn(position, team);

        interfaceService.toastCommanderEntered(player);
        interfaceService.sendCommandCreated(player);
    }

    public void requestDisbandTeam(Player leader) {
        var info = leader.getInfo();
        var team = teamStore.getTeamByLeader(info);

        if (team == null) {
            leader.sendMessage("[scarlet]You are not a commander.");
            return;
        }

        teamStore.removeTeam(team.getTeamId());

        Call.infoToast(Strings.format(
            "[scarlet]Commander <@[white]{}[]> disbanded their force", leader.name), 5f);
    }

    public void requestApproveJoin(Player leader, Player target) {
        var leaderInfo = leader.getInfo();
        var targetInfo = target.getInfo();

        var team = teamStore.getTeamByLeader(leaderInfo);
        if (team == null) {
            leader.sendMessage("[scarlet]You have no authority.");
            return;
        }

        if (teamStore.isPlayed(targetInfo)) {
            leader.sendMessage("[scarlet]Target already serves another force.");
            return;
        }

        teamStore.join(team.getTeamId(), targetInfo);

        leader.sendMessage(Strings.format(
            "[accent]{} has joined your command.", target.name));
        target.sendMessage(Strings.format(
            "[accent]You have been enlisted by <@[white]{}[]>.", leader.name));
    }

    public void requestLeaveTeam(Player player) {
        var info = player.getInfo();
        var team = teamStore.getTeamByMember(info);

        if (team == null) {
            player.sendMessage("[scarlet]You are not assigned to any force.");
            return;
        }

        if (teamStore.getLeaders(team.getTeamId()) == info) {
            player.sendMessage("[scarlet]A commander cannot abandon their force.");
            return;
        }

        teamStore.leave(info);

        player.sendMessage("[accent]You have left the force.");
    }

    public void requestApproveLeave(Player leader, Player target) {
        var leaderInfo = leader.getInfo();
        var targetInfo = target.getInfo();

        var team = teamStore.getTeamByLeader(leaderInfo);
        if (team == null) {
            leader.sendMessage("[scarlet]You have no authority.");
            return;
        }

        if (!teamStore.isMember(team.getTeamId(), targetInfo)) {
            leader.sendMessage("[scarlet]Target is not under your command.");
            return;
        }

        teamStore.leave(targetInfo);

        leader.sendMessage(Strings.format(
            "[accent]{} has been dismissed.", target.name));
        target.sendMessage(Strings.format(
            "[scarlet]You were dismissed by <@[white]{}[]>.", leader.name));
    }

    public void requestChangeLeader(Player leader, Player newLeader) {
        var leaderInfo = leader.getInfo();
        var newLeaderInfo = newLeader.getInfo();

        var team = teamStore.getTeamByLeader(leaderInfo);
        if (team == null) {
            leader.sendMessage("[scarlet]You are not a commander.");
            return;
        }

        if (!teamStore.isMember(team.getTeamId(), newLeaderInfo)) {
            leader.sendMessage("[scarlet]Target is not part of your force.");
            return;
        }

        teamStore.changeLeader(team.getTeamId(), newLeaderInfo);

        leader.sendMessage(Strings.format(
            "[accent]Command transferred to {}.", newLeader.name));
        newLeader.sendMessage(
            "[accent]You are now the commander.");

        Call.infoToast(Strings.format(
            "[accent]{} is now commanding a new force", newLeader.name), 4f);
    }
}
