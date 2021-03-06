package com.example.steam.methods;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class history {
    private static JSONArray data = new JSONArray();
    private static final File file = new File("history.json");

    public static void getHistory(){
        try {
            if(file.createNewFile()) return;

            FileReader fileData = new FileReader("history.json");
            Object obj = new JSONParser().parse(fileData);
            data = (JSONArray) obj;

        } catch (IOException | ParseException e) {
             System.out.println("Unable to read file: " + e);
        }
    }

    public static JSONObject getData(int index){
        return (JSONObject) data.get(index);
    }

    public static int getSizeHistory() {
        return data.size();
    }

    public static void clearHistory() {
        try {
            data.clear();
            Files.write(Paths.get("history.json"), data.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addData(String name, String steamID64, String Avatar){
        try {
            if(!file.isFile()) file.createNewFile();

            JSONObject object = new JSONObject();
            object.put("Name", name);
            object.put("SteamID64", steamID64);
            object.put("Avatar", Avatar);

            data.add(object);
            Files.write(Paths.get("history.json"), data.toString().getBytes());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
