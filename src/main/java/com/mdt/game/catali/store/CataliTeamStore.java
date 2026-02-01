package com.mdt.game.catali.store;

import lombok.Locked;
import lombok.RequiredArgsConstructor;

import mindustry.game.Team;
import mindustry.net.Administration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public final class CataliTeamStore {
    private int lastTeamID = Team.baseTeams.length;
    private final Map<Integer, CataliTeamStat> teams = new HashMap<>();

    private final Map<Integer, Administration.PlayerInfo> teamLeaders = new HashMap<>();
    private final Map<Integer, Set<Administration.PlayerInfo>> teamMembers = new HashMap<>();
    private final Map<Administration.PlayerInfo, Integer> playerTeamIndex = new HashMap<>();

    // !-------------------------------------------------!

    @Locked
    public boolean createTeam(int teamId, Administration.PlayerInfo leader) {
        if (teams.containsKey(teamId)) return false;
        if (playerTeamIndex.containsKey(leader)) return false;

        teams.put(teamId, new CataliTeamStat(teamId));

        teamLeaders.put(teamId, leader);
        teamMembers.put(teamId, new HashSet<>(List.of(leader)));
        playerTeamIndex.put(leader, teamId);

        return true;
    }

    @Locked
    public boolean removeTeam(int teamId) {
        if (!teams.containsKey(teamId)) return false;

        teams.remove(teamId);

        teamLeaders.remove(teamId);
        var members = teamMembers.remove(teamId);
        members.forEach(playerTeamIndex::remove);

        return true;
    }

    @Locked
    public boolean changeLeader(int teamId, Administration.PlayerInfo newLeader) {
        if (!teams.containsKey(teamId)) return false;
        if (!isMember(teamId, newLeader)) return false;

        teamLeaders.put(teamId, newLeader);

        return true;
    }

    @Locked
    public boolean join(int teamId, Administration.PlayerInfo player) {
        if (!teams.containsKey(teamId)) return false;
        if (playerTeamIndex.containsKey(player)) return false;

        teamMembers.get(teamId).add(player);
        playerTeamIndex.put(player, teamId);

        return true;
    }

    @Locked
    public boolean leave(Administration.PlayerInfo player) {
        var teamId = playerTeamIndex.get(player);
        if (teamId == null) return false;
        if (teamLeaders.get(teamId) == player) return false;

        teamMembers.get(teamId).remove(player);
        playerTeamIndex.remove(player);

        return true;
    }

    // !-------------------------------------------------!

    @Locked
    public CataliTeamStat getTeam(int teamId) {
        return teams.get(teamId);
    }

    @Locked
    public CataliTeamStat getTeamByLeader(Administration.PlayerInfo leader) {
        var teamId = playerTeamIndex.get(leader);
        if (teamId == null || teamLeaders.get(teamId) != leader) return null;
        else return teams.get(teamId);
    }

    @Locked
    public CataliTeamStat getTeamByMember(Administration.PlayerInfo player) {
        var teamId = playerTeamIndex.get(player);
        return teamId != null ? teams.get(teamId) : null;
    }

    @Locked
    public Administration.PlayerInfo getLeaders(int teamId) {
        return teamLeaders.get(teamId);
    }

    @Locked
    public Set<Administration.PlayerInfo> getMembers(int teamId) {
        var members = teamMembers.get(teamId);
        return members != null ? Set.copyOf(members) : Set.of();
    }

    @Locked
    public boolean isPlayed(Administration.PlayerInfo player) {
        return playerTeamIndex.containsKey(player);
    }

    @Locked
    public boolean isMember(int teamId, Administration.PlayerInfo player) {
        var members = teamMembers.get(teamId);
        return members != null && members.contains(player);
    }

    @Locked
    public int getNextTeamId() {
        for (int i = 0; i < Team.all.length; i++) {
            if (lastTeamID >= Team.all.length) lastTeamID = Team.baseTeams.length;
            if (!teams.containsKey(lastTeamID)) return lastTeamID++;
            lastTeamID++;
        }

        return -1;
    }
}
