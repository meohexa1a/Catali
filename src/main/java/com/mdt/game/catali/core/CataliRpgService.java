package com.mdt.game.catali.core;

import com.mdt.game.catali.CataliInterfaceService;
import com.mdt.game.catali.store.CataliTeamStore;

import lombok.RequiredArgsConstructor;

import mindustry.gen.Groups;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class CataliRpgService {
    private final CataliTeamStore teamStore;

    private final CataliInterfaceService interfaceService;

    // !----------------------------------------------------!

    public void earnExp(int teamId, int exp) {
        var team = teamStore.getTeam(teamId);
        if (team == null) return;

        if (team.earnExp(exp)) return;
        var leaderInfo = teamStore.getLeaders(teamId);
        var leader = Groups.player.find(player -> player.getInfo() == leaderInfo);

        interfaceService.sendLevelUpMessage(leader, team.getLevel());
        interfaceService.toastLevelUp(leader, team.getLevel());
    }
}
