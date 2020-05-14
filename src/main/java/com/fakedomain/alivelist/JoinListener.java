package com.fakedomain.alivelist;

import com.Alvaeron.Engine;
import com.Alvaeron.api.RPEngineAPI;
import com.Alvaeron.player.RoleplayPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class JoinListener implements Listener {
    CopyOnWriteArrayList<PlayerData> playerList = new CopyOnWriteArrayList<PlayerData>();
    public JoinListener(CopyOnWriteArrayList<PlayerData> PlayerList) {
        playerList = PlayerList;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player currentPlayer = event.getPlayer();
        UUID userId = currentPlayer.getUniqueId();
        Team currentTeam = null;
        PlayerData currentData = null;
        for (PlayerData data : playerList) {
            if (data.uuid.equals(userId)) {
                currentData = data;
                break;
            }
        }

        //Find out what team the player is on
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Set<Team> teamSet = manager.getMainScoreboard().getTeams();

        for (Team team : teamSet) {
            if (team.hasEntry(currentPlayer.getName())) {
                currentTeam = team;
                break;
            }
        }

        if (currentData == null) {
            //If currentData is null player hasn't joined before
            currentData = new PlayerData();
            currentData.uuid = userId;
            currentData.latestName = currentPlayer.getName();
            if (currentTeam == null)
                currentData.latestTeam = "Unknown";
            else
                currentData.latestTeam = currentTeam.getName();
            playerList.add(currentData);
        }
        else
        {
            //They've joined before, let's update their data
            currentData.latestTeam = currentPlayer.getName();
            if (currentTeam == null)
                currentData.latestTeam = "Unknown";
            else
                currentData.latestTeam = currentTeam.getName();
        }
        PlayerData finalCurrentData = currentData;
        //Have to delay getting RP name because of database shenanigans
        Bukkit.getScheduler ().runTaskLater (Bukkit.getPluginManager().getPlugin("AliveList"), () -> DataSave.updateRpName(currentPlayer, finalCurrentData), 20 * 5);
        DataSave.saveList(playerList);
    }
}
