package com.fakedomain.alivelist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class DeathListener implements Listener {
    CopyOnWriteArrayList<PlayerData> playerList = new CopyOnWriteArrayList<PlayerData>();
    public DeathListener(CopyOnWriteArrayList<PlayerData> PlayerList) {
        playerList = PlayerList;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        UUID deathId = event.getEntity().getUniqueId();

        for (int i = 0; i < playerList.size(); i++) {
            if (playerList.get(i).uuid.equals(deathId)) {
                playerList.remove(i);
                break;
            }
        }

        DataSave.saveList(playerList);
    }
}
