package com.fakedomain.alivelist;

import com.google.gson.Gson;
import org.bukkit.plugin.java.JavaPlugin;
import com.fakedomain.alivelist.DataSave;

import java.util.ArrayList;

public final class AliveList extends JavaPlugin {
    ArrayList<PlayerData> playerList = new ArrayList<PlayerData>();
    @Override
    public void onEnable() {
        playerList = DataSave.loadList();
        getServer().getPluginManager().registerEvents(new JoinListener(playerList), this);
        getServer().getPluginManager().registerEvents(new DeathListener(playerList), this);
        this.getCommand("alivelist").setExecutor(new ListCommand(playerList));
    }

    @Override
    public void onDisable() {
        DataSave.saveList(playerList);
    }
}
