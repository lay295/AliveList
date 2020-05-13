package com.fakedomain.alivelist;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class ListCommand implements CommandExecutor {
    ArrayList<PlayerData> playerList = new ArrayList<PlayerData>();
    public ListCommand(ArrayList<PlayerData> PlayerList) {
        playerList = PlayerList;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<ArrayList<PlayerData>> teamList = new ArrayList<>();
        ArrayList<PlayerData> lordList = new ArrayList<>();
        ArrayList<PlayerData> brotherList = new ArrayList<>();
        ArrayList<PlayerData> leaderList = new ArrayList<>();
        ArrayList<PlayerData> acolyteList = new ArrayList<>();
        ArrayList<PlayerData> serfList = new ArrayList<>();
        ArrayList<PlayerData> exiledList = new ArrayList<>();
        ArrayList<PlayerData> unknownList = new ArrayList<>();

        //This determines ordering in printing out
        teamList.add(lordList);
        teamList.add(brotherList);
        teamList.add(leaderList);
        teamList.add(acolyteList);
        teamList.add(serfList);
        teamList.add(exiledList);
        teamList.add(unknownList);

        for (PlayerData data : playerList) {
            if (data.latestTeam.toLowerCase().equals("lord"))
                lordList.add(data);
            else if (data.latestTeam.toLowerCase().equals("brother"))
                brotherList.add(data);
            else if (data.latestTeam.toLowerCase().equals("acolyte"))
                acolyteList.add(data);
            else if (data.latestTeam.toLowerCase().equals("leader"))
                leaderList.add(data);
            else if (data.latestTeam.toLowerCase().equals("serf"))
                serfList.add(data);
            else if (data.latestTeam.toLowerCase().contains("exiled"))
                exiledList.add(data);
            else
                unknownList.add(data);
        }

        for (ArrayList<PlayerData> groupList : teamList) {
            for (PlayerData data : groupList) {
                sender.sendMessage(data.latestName + " - " + data.latestTeam);
            }
        }
        return true;
    }
}
