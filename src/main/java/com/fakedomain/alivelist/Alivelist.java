package com.fakedomain.alivelist;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.fakedomain.alivelist.DataSave;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public final class AliveList extends JavaPlugin {
    CopyOnWriteArrayList<PlayerData> playerList = new CopyOnWriteArrayList<PlayerData>();
    @Override
    public void onEnable() {
        playerList = DataSave.loadList();
        getServer().getPluginManager().registerEvents(new JoinListener(playerList), this);
        getServer().getPluginManager().registerEvents(new DeathListener(playerList), this);
        this.getCommand("alivelist").setExecutor(new ListCommand(playerList));
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Set<Team> teamSet = manager.getMainScoreboard().getTeams();

            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerData currentData = null;
                Team currentTeam = null;
                UUID userId = player.getUniqueId();
                for (PlayerData data : playerList) {
                    if (data.uuid.equals(userId)) {
                        currentData = data;
                        break;
                    }
                }
                for (Team team : teamSet) {
                    if (team.hasEntry(player.getName())) {
                        currentTeam = team;
                        break;
                    }
                }
                if (currentData != null)
                    DataSave.updateRpName(player, currentData);
                if (currentTeam != null && currentTeam.getName() != null)
                    currentData.latestTeam = currentTeam.getName();
            }
        }, 0L, 300*20);
    }

    @Override
    public void onDisable() {
        DataSave.saveList(playerList);
    }
}
