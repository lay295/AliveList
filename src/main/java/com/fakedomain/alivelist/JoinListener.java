package com.fakedomain.alivelist;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class JoinListener implements Listener {
    ArrayList<PlayerData> playerList = new ArrayList<PlayerData>();
    public JoinListener(ArrayList<PlayerData> PlayerList) {
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

        DataSave.saveList(playerList);
    }
}
