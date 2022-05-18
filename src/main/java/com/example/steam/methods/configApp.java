package com.example.steam.methods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class configApp {

    private static final String steamID64 = "76561199004803730";
    private static final File file = new File("config.ini");
    private static String last_steamID64 = null;

    public static void getParams(){
        try {
            if(!file.isFile()) saveParams();

            Properties props = new Properties();
            props.load(new FileInputStream(file));
            // Load props
            last_steamID64 = props.getProperty("SteamID64_LAST");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveParams(){
        try {
            if(!file.createNewFile()) System.out.println("File is created..");

            FileWriter writer = new FileWriter(file.getPath());

            String temp = "SteamID64_LAST = " + ( (last_steamID64 != null) ? last_steamID64 : steamID64 );

            writer.write(temp);
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String steamIDValue(){
        return last_steamID64;
    }
    public static void setSteamID_LAST(String steamID64) {
        last_steamID64 = steamID64;
    }
}
