package com.fakedomain.alivelist;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static org.bukkit.Bukkit.getServer;

public class DataSave {
    public static void saveList(ArrayList<PlayerData> playerList) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(playerList);
        getServer().getLogger().info(jsonString);
        try {
            FileOutputStream outputStream = new FileOutputStream("alivelist.json");
            byte[] strToBytes = jsonString.getBytes();
            outputStream.write(strToBytes);

            outputStream.close();
        } catch (Exception ex) {
            getServer().getLogger().info(ex.getStackTrace().toString());
        }
    }

    public static ArrayList<PlayerData> loadList() {
        ArrayList<PlayerData> returnList = new ArrayList<PlayerData>();
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

            returnList = new Gson().fromJson(jsonString, new TypeToken<ArrayList<PlayerData>>(){}.getType());
            if (returnList == null)
                returnList = new ArrayList<PlayerData>();
        }

        return returnList;
    }
}
