package com.mdt.game.catali.core.type;

import lombok.Locked;
import mindustry.net.Administration;

import java.util.HashSet;
import java.util.Set;

public final class CataliTeamMember {
    private Administration.PlayerInfo leader;
    private final Set<Administration.PlayerInfo> members = new HashSet<>();

    // !-------------------------------------------------------!

    public CataliTeamMember(Administration.PlayerInfo leader) {
        this.leader = leader;
        this.members.add(leader);
    }

    // !-------------------------------------------------------!

    @Locked
    public void memberJoin(Administration.PlayerInfo player) {
        if (player == leader) return;
        members.add(player);
    }

    @Locked
    public void memberLeave(Administration.PlayerInfo player) {
        if (player == leader) return;
        members.remove(player);
    }

    @Locked
    public void transferLeader(Administration.PlayerInfo newLeader) {
        if (!members.contains(newLeader)) return;
        leader = newLeader;
    }

    // !-------------------------------------------------------!

    @Locked
    public Administration.PlayerInfo getLeader() {
        return leader;
    }

    @Locked
    public Set<Administration.PlayerInfo> getMembers() {
        return new HashSet<>(members);
    }
}
