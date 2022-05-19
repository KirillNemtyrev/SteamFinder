package com.example.steam.methods;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.example.steam.methods.getData.getGameBans;

public class vacChecker {
    private static JSONArray data = new JSONArray();
    private static final File file = new File("vacBans.json");

    public static void getVacBans(){
        try {
            if(file.createNewFile()) return;

            FileReader fileData = new FileReader("vacBans.json");
            Object obj = new JSONParser().parse(fileData);
            data = (JSONArray) obj;

        } catch (IOException | ParseException e) {
            System.out.println("Unable to read file: " + e);
        }
    }

    public static JSONObject getData(int index){
        return (JSONObject) data.get(index);
    }

    public static int getIndexSteamID(String steamID64) {

        for(int count = 0; count < data.size(); count++){
            JSONObject profile = (JSONObject) data.get(count);

            if (profile.get("SteamID64").equals(steamID64)){

                return count;
            }
        }
        return -1;
    }

    public static void removeVacChecker(int index){
        try {
            data.remove(index);
            Files.write(Paths.get("vacBans.json"), data.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getSizeBans() {
        return data.size();
    }

    public static void addData(String name, String steamID64, String Avatar, String VacBans, String GameBans, String TradeBan){
        try {
            if(!file.isFile()) file.createNewFile();

            JSONObject object = new JSONObject();
            object.put("Name", name);
            object.put("SteamID64", steamID64);
            object.put("Avatar", Avatar);
            object.put("VacBans", VacBans);
            object.put("GameBans", GameBans);
            object.put("TradeBans", TradeBan);
            object.put("NewVacBans", "None");
            object.put("NewGameBans", "None");
            object.put("NewTradeBans", "None");
            object.put("MessageCount", 0);

            data.add(object);
            Files.write(Paths.get("vacBans.json"), data.toString().getBytes());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getCheckedAccount() throws IOException, SAXException, ParserConfigurationException {
        if(data.isEmpty()) return;

        JSONArray temp = new JSONArray();
        for(int count = 0; count < data.size(); count++){
            JSONObject profile = (JSONObject) data.get(count);

            String VacBans = (String) profile.get("VacBans");
            String GameBans = (String) profile.get("GameBans");
            String TradeBans = (String) profile.get("TradeBans");

            String link = "https://steamcommunity.com/profiles/" + profile.get("SteamID64");
            if(!getData.download_file(link + "/?xml=1")) return;

            File xmlFile = new File("temp.html");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            Element dataProfile = document.getDocumentElement();
            String steamVacBans = dataProfile.getElementsByTagName("vacBanned").item(0).getTextContent();
            String steamTradeBan = dataProfile.getElementsByTagName("tradeBanState").item(0).getTextContent();
            String getGameBan = getGameBans(link + "/");
            String steamGameBans = getGameBan == null ? "Нет игровых блокировок" :
                    getGameBan.equals("Multiple") ? "Несколько игровых блокировок" :
                            getGameBan + " игровая(-ы) блокировка";

            int countMessage = 0;
            if(!VacBans.equals(steamVacBans)){
                profile.replace("NewVacBans", steamVacBans);
                countMessage += 1;
            }

            if(!GameBans.equals(steamGameBans)){
                profile.replace("NewGameBans", steamGameBans);
                countMessage += 1;
            }

            if(!TradeBans.equals(steamTradeBan)){
                profile.replace("NewTradeBans", steamTradeBan);
                countMessage += 1;
            }
            profile.replace("MessageCount", countMessage);
            temp.add(profile);

            if(countMessage != 0) sendNotification("Кто-то из списка получил блокировки..");
        }
        data = temp;
        Files.write(Paths.get("vacBans.json"), data.toString().getBytes());
    }

    public static void sendNotification(String text){
        try {
            if (SystemTray.isSupported()) {
                SystemTray tray = SystemTray.getSystemTray();

                java.awt.Image image = Toolkit.getDefaultToolkit().getImage("resources/steam.png");
                TrayIcon trayIcon = new TrayIcon(image);
                tray.add(trayIcon);
                trayIcon.displayMessage("SteamFinder", text, TrayIcon.MessageType.INFO);
            }
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
}
