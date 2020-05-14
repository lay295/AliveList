package com.fakedomain.alivelist;

import com.Alvaeron.Engine;
import com.Alvaeron.api.RPEngineAPI;
import com.Alvaeron.player.RoleplayPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListCommand implements CommandExecutor {
    CopyOnWriteArrayList<PlayerData> playerList = new CopyOnWriteArrayList<PlayerData>();
    public ListCommand(CopyOnWriteArrayList<PlayerData> PlayerList) {
        playerList = PlayerList;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (!args[0].equals("remove") || args.length < 2)
                return false;

            try {
                String playerName = args[1];
                boolean found = false;
                for (int i = 0; i < playerList.size(); i++) {
                    if (playerName.toLowerCase().equals(playerList.get(i).latestName.toLowerCase())) {
                        playerList.remove(i);
                        found = true;
                        break;
                    }
                }
                if (found)
                    sender.sendMessage("Successfully removed user from alive list");
                else
                    sender.sendMessage("No user was found with that username");
            } catch (Exception ex) {
                sender.sendMessage("Error");
                Bukkit.getLogger().info(ex.getStackTrace().toString());
            }
            return true;
        } else {

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
            teamList.add(exiledList);
            teamList.add(serfList);
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
            ArrayList<TextComponent> finalfinalMessage = new ArrayList<>();

            for (ArrayList<PlayerData> groupList : teamList) {
                ArrayList<TextComponent> finalComponents = new ArrayList<>();
                ChatColor color = ChatColor.GRAY;
                String groupString = "Unknown";
                if (groupList.equals(lordList)) {
                    color = ChatColor.RED;
                    groupString = "Lord";
                }
                if (groupList.equals(brotherList)) {
                    color = ChatColor.BLUE;
                    groupString = "Brothers";
                }
                if (groupList.equals(leaderList)) {
                    color = ChatColor.AQUA;
                    groupString = "Serfs";
                }
                if (groupList.equals(acolyteList)) {
                    color = ChatColor.GREEN;
                    groupString = "Acolytes";
                }
                if (groupList.equals(exiledList)) {
                    color = ChatColor.GOLD;
                    groupString = "Exiled";
                }

                if (groupList.size() > 0) {
                    TextComponent groupName = new TextComponent(groupString);
                    groupName.setBold(true);
                    groupName.setColor(color);
                    TextComponent playerCount = new TextComponent(" (" + groupList.size() + ")\n");
                    playerCount.setColor(ChatColor.GREEN);
                    groupName.addExtra(playerCount);
                    finalfinalMessage.add(groupName);
                }


                for (int i = 0; i < groupList.size(); i++) {
                    TextComponent mainComponent = new TextComponent(groupList.get(i).latestRoleplayName.equals("NONE") ? groupList.get(i).latestName : groupList.get(i).latestRoleplayName);
                    mainComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(groupList.get(i).latestName).create()));
                    mainComponent.setColor(color);
                    finalComponents.add(mainComponent);
                }

                TextComponent finalMessage = new TextComponent("");
                for (int i = 0; i < finalComponents.size(); i++) {
                    finalMessage.addExtra(finalComponents.get(i));
                    if (i != finalComponents.size() - 1) {
                        finalMessage.addExtra(", ");
                    } else {
                        finalMessage.addExtra("\n");
                    }
                }
                finalfinalMessage.add(finalMessage);
            }

            TextComponent finalMessage = new TextComponent("");
            for (int i = 0; i < finalfinalMessage.size(); i++) {
                finalMessage.addExtra(finalfinalMessage.get(i));
            }
            sender.spigot().sendMessage(finalMessage);

            return true;
        }
    }
}
