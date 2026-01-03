package com.mdt.game.catali.core.type;

public record CataliTeam(
        int teamID,
        long createAt,

        CataliTeamMember teamMember,
        CataliTeamStat teamStat) {

}
