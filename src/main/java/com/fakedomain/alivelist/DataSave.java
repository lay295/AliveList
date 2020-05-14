package com.fakedomain.alivelist;

import com.Alvaeron.api.RPEngineAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.bukkit.Bukkit.getServer;

public class DataSave {
    public static void saveList(CopyOnWriteArrayList<PlayerData> playerList) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(playerList);
        try {
            FileOutputStream outputStream = new FileOutputStream("alivelist.json");
            byte[] strToBytes = jsonString.getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
        } catch (Exception ex) {
            getServer().getLogger().info(ex.getStackTrace().toString());
        }
    }

    public static CopyOnWriteArrayList<PlayerData> loadList() {
        CopyOnWriteArrayList<PlayerData> returnList = new CopyOnWriteArrayList<PlayerData>();
        File f = new File("alivelist.json");
        if (f.exists() && !f.isDirectory()) {
            //File exists
            String jsonString = "";
            Scanner myReader = null;
            try {
                myReader = new Scanner(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (myReader.hasNextLine()) {
                jsonString += myReader.nextLine();
            }
            myReader.close();

            returnList = new Gson().fromJson(jsonString, new TypeToken<CopyOnWriteArrayList<PlayerData>>(){}.getType());
            if (returnList == null)
                returnList = new CopyOnWriteArrayList<PlayerData>();
        }

        return returnList;
    }

    public static void updateRpName(Player player, PlayerData data) {
        data.latestRoleplayName = RPEngineAPI.getRpName(player.getName());
    }
}
