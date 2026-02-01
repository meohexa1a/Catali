package com.mdt.game.catali.core;

import arc.math.geom.Vec2;
import com.mdt.game.catali.CataliInterfaceService;
import com.mdt.game.catali.store.CataliTeamStore;
import lombok.RequiredArgsConstructor;
import mindustry.content.UnitTypes;
import mindustry.game.Team;
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
            interfaceService.sendNotACommander(leader);
            return;
        }

        teamStore.removeTeam(team.getTeamId());
        interfaceService.toastTeamDisbanded(leader);
    }

    public void requestApproveJoin(Player leader, Player target) {
        var leaderInfo = leader.getInfo();
        var targetInfo = target.getInfo();

        var team = teamStore.getTeamByLeader(leaderInfo);
        if (team == null) {
            interfaceService.sendNoAuthority(leader);
            return;
        }

        if (teamStore.isPlayed(targetInfo)) {
            interfaceService.sendTargetAlreadyInTeam(leader);
            return;
        }

        teamStore.join(team.getTeamId(), targetInfo);

        interfaceService.sendJoinedYourCommand(leader, target);
        interfaceService.sendEnlistedBy(target, leader);
    }

    public void requestLeaveTeam(Player player) {
        var info = player.getInfo();
        var team = teamStore.getTeamByMember(info);

        if (team == null) {
            interfaceService.sendNotInTeam(player);
            return;
        }

        if (teamStore.getLeaders(team.getTeamId()) == info) {
            interfaceService.sendLeaderCannotLeave(player);
            return;
        }

        teamStore.leave(info);
        interfaceService.sendLeaveSuccess(player);
    }

    public void requestApproveLeave(Player leader, Player target) {
        var leaderInfo = leader.getInfo();
        var targetInfo = target.getInfo();

        var team = teamStore.getTeamByLeader(leaderInfo);
        if (team == null) {
            interfaceService.sendNoAuthority(leader);
            return;
        }

        if (!teamStore.isMember(team.getTeamId(), targetInfo)) {
            interfaceService.sendTargetNotUnderCommand(leader);
            return;
        }

        teamStore.leave(targetInfo);

        interfaceService.sendDismissedSuccess(leader, target);
        interfaceService.sendYouWereDismissed(target, leader);
    }

    public void requestChangeLeader(Player leader, Player newLeader) {
        var leaderInfo = leader.getInfo();
        var newLeaderInfo = newLeader.getInfo();

        var team = teamStore.getTeamByLeader(leaderInfo);
        if (team == null) {
            interfaceService.sendNotACommander(leader);
            return;
        }

        if (!teamStore.isMember(team.getTeamId(), newLeaderInfo)) {
            interfaceService.sendTargetNotMember(leader);
            return;
        }

        teamStore.changeLeader(team.getTeamId(), newLeaderInfo);

        interfaceService.sendCommandTransferred(leader, newLeader);
        interfaceService.sendYouAreNowCommander(newLeader);
        interfaceService.toastNewCommander(newLeader);
    }
}
