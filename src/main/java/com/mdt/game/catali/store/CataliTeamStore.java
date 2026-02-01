package com.mdt.game.catali.store;

import lombok.Locked;
import lombok.RequiredArgsConstructor;

import mindustry.net.Administration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class CataliTeamStore {
    private final Map<Integer, CataliTeamStat> teams = new HashMap<>();

    private final Map<Integer, Administration.PlayerInfo> teamLeaders = new HashMap<>();
    private final Map<Integer, Set<Administration.PlayerInfo>> teamMembers = new HashMap<>();
    private final Map<Administration.PlayerInfo, Integer> playerTeamIndex = new HashMap<>();

    // !-------------------------------------------------!

    @Locked
    public boolean createTeam(int teamId, Administration.PlayerInfo leader) {
        if (teams.containsKey(teamId)) return false;

        teams.put(teamId, new CataliTeamStat(teamId));

        teamLeaders.put(teamId, leader);
        teamMembers.put(teamId, new HashSet<>(List.of(leader)));
        playerTeamIndex.put(leader, teamId);

        return true;
    }

    public boolean removeTeam(int teamId) {
        if (!teams.containsKey(teamId)) return false;

        teams.remove(teamId);

        teamLeaders.remove(teamId);
        var members = teamMembers.remove(teamId);
        if (members != null) members.forEach(playerTeamIndex::remove);

        return true;
    }

    public boolean changeLeader(int teamId, Administration.PlayerInfo newLeader) {
        if (!teams.containsKey(teamId)) return false;
        if (!isMember(teamId, newLeader)) return false;

        teamLeaders.put(teamId, newLeader);
        return true;
    }

    public boolean join(int teamId, Administration.PlayerInfo player) {
        if (!teams.containsKey(teamId)) return false;

        // Kiểm tra xem player đã ở trong team nào khác chưa?
        if (playerTeamIndex.containsKey(player)) {
            return false; // Phải rời team cũ trước khi vào team mới
        }

        teamMembers.computeIfAbsent(teamId, k -> new HashSet<>()).add(player);
        playerTeamIndex.put(player, teamId);
        return true;
    }

    public boolean leave(Administration.PlayerInfo player) {
        Integer teamId = playerTeamIndex.get(player);
        if (teamId == null) return false;

        // Xóa khỏi danh sách thành viên
        Set<Administration.PlayerInfo> members = teamMembers.get(teamId);
        if (members != null) {
            members.remove(player);
        }

        // Xóa khỏi chỉ mục tra cứu
        playerTeamIndex.remove(player);

        // Logic phụ: Nếu Leader rời team thì sao?
        // Option A: Xóa team -> gọi removeTeam(teamId)
        // Option B: Chuyển leader cho người khác -> xử lý thêm logic
        // Ở đây mình để đơn giản là xóa leader khỏi map leader nếu họ rời.
        if (teamLeaders.get(teamId) == player) {
            teamLeaders.remove(teamId);
            // Cần logic xử lý team không có leader ở đây (ví dụ disband team)
        }

        return true;
    }

    // --- Các hàm Getter & Helper ---

    public CataliTeamStat getTeam(int teamId) {
        return teams.get(teamId);
    }

    public CataliTeamStat getTeamByLeader(Administration.PlayerInfo leader) {
        Integer teamId = playerTeamIndex.get(leader);
        // Kiểm tra xem teamId có tồn tại VÀ người này có phải leader không
        if (teamId != null && teamLeaders.get(teamId) == leader) {
            return teams.get(teamId);
        }
        return null;
    }

    public CataliTeamStat getTeamByMember(Administration.PlayerInfo player) {
        Integer teamId = playerTeamIndex.get(player);
        return (teamId != null) ? teams.get(teamId) : null;
    }

    public Administration.PlayerInfo getLeaders(int teamId) {
        return teamLeaders.get(teamId);
    }

    public Set<Administration.PlayerInfo> getMembers(int teamId) {
        // Trả về Unmodifiable Set để tránh việc code bên ngoài sửa trực tiếp vào Map
        Set<Administration.PlayerInfo> members = teamMembers.get(teamId);
        return members != null ? Collections.unmodifiableSet(members) : Collections.emptySet();
    }

    public Administration.PlayerInfo getFirstMember(int teamId) {
        Set<Administration.PlayerInfo> members = teamMembers.get(teamId);
        if (members == null || members.isEmpty()) return null;
        return members.iterator().next();
    }

    public Integer hasTeam(Administration.PlayerInfo player) {
        return playerTeamIndex.get(player);
    }

    public boolean exists(int teamId) {
        return teams.containsKey(teamId);
    }

    private boolean isMember(int teamId, Administration.PlayerInfo player) {
        var members = teamMembers.get(teamId);
        return members != null && members.contains(player);
    }
}
