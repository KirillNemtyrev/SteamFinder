package com.example.steam.methods;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class configApp {

    private static final String steamID64 = "76561199004803730";
    private static final String path = "config.ini";
    private static final File file = new File(path);
    private static String last_steamID64 = null;
    private static String last_steamName = null;
    private static String last_steamAvatar = null;
    private static String main_steamID64 = null;
    private static String main_steamName = null;
    private static String main_steamAvatar = null;
    private static boolean sendNotification = true;
    private static boolean showLastProfile = true;
    private static int hourVacChecker = 3;

    public static void clearSettings() {
        last_steamID64 = "76561199004803730";
        main_steamID64 = "76561199004803730";
        sendNotification = true;
        showLastProfile = true;
        hourVacChecker = 3;

        saveParams();
    }

    public static void getParams(){
        try {
            if(!file.isFile()) saveParams();

            Properties props = new Properties();
            props.load(new FileInputStream(file));
            // Load props
            last_steamID64 = props.getProperty("SteamID64_LAST");
            main_steamID64 = props.getProperty("SteamID64_MAIN");
            sendNotification = Boolean.parseBoolean(props.getProperty("sendNotification"));
            showLastProfile = Boolean.parseBoolean(props.getProperty("showLastProfile"));
            hourVacChecker = Integer.parseInt(props.getProperty("hourVacChecker"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveParams(){
        try {
            if(!file.isFile()) Files.createFile(Path.of(path));

            FileWriter writer = new FileWriter(file.getPath());

            String temp = "SteamID64_LAST = " + ( (last_steamID64 != null) ? last_steamID64 : steamID64 ) + "\n"
                    + "SteamID64_MAIN = " + ( (main_steamID64 != null) ? main_steamID64 : steamID64 ) + "\n"
                    + "sendNotification = " + sendNotification + "\n"
                    + "hourVacChecker = " + hourVacChecker + "\n"
                    + "showLastProfile = " + showLastProfile;

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

    public static String getMain_steamID64() {
        return main_steamID64;
    }

    public static void setMain_steamID64(String main_steamID64) {
        configApp.main_steamID64 = main_steamID64;
    }

    public static boolean isSendNotification() {
        return sendNotification;
    }

    public static void setSendNotification(boolean sendNotification) {
        configApp.sendNotification = sendNotification;
    }

    public static boolean isShowLastProfile() {
        return showLastProfile;
    }

    public static void setShowLastProfile(boolean showLastProfile) {
        configApp.showLastProfile = showLastProfile;
    }

    public static int getHourVacChecker() {
        return hourVacChecker;
    }

    public static void setHourVacChecker(int hourVacChecker) {
        configApp.hourVacChecker = hourVacChecker;
    }

    public static String getLast_steamAvatar() {
        return last_steamAvatar;
    }

    public static void setLast_steamAvatar(String last_steamAvatar) {
        configApp.last_steamAvatar = last_steamAvatar;
    }

    public static String getLast_steamName() {
        return last_steamName;
    }

    public static void setLast_steamName(String last_steamName) {
        configApp.last_steamName = last_steamName;
    }

    public static String getMain_steamAvatar() {
        return main_steamAvatar;
    }

    public static void setMain_steamAvatar(String main_steamAvatar) {
        configApp.main_steamAvatar = main_steamAvatar;
    }

    public static String getMain_steamName() {
        return main_steamName;
    }

    public static void setMain_steamName(String main_steamName) {
        configApp.main_steamName = main_steamName;
    }

    public static void getMainData() {
        getInfoUrl("https://steamcommunity.com/profiles/" + main_steamID64 + "/?xml=1");
    }

    public static void getLastData() {
        try {
            String URLbasic = "https://steamcommunity.com/profiles/" + last_steamID64 + "/?xml=1";
            if(!getData.download_file(URLbasic)) return;

            File xmlFile = new File("temp.html");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            Element profile = document.getDocumentElement();
            String steamID64 = profile.getElementsByTagName("steamID64").item(0).getTextContent();
            // Remove CDATA from xml
            String steamName = profile.getElementsByTagName("steamID").item(0).
                    getTextContent().replaceAll("(<\\!\\[CDATA\\[)|(\\]\\]>)","");
            String steamAvatarMini = profile.getElementsByTagName("avatarIcon").item(0).
                    getTextContent().replaceAll("(<\\!\\[CDATA\\[)|(\\]\\]>)","");

            setLast_steamName(steamName);
            setLast_steamAvatar(steamAvatarMini);
            setSteamID_LAST(steamID64);

        } catch (IOException | SAXException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getInfoUrl(String url) {
        try {
            String URLbasic = url + "/?xml=1";
            if(!getData.download_file(URLbasic)) return;

            File xmlFile = new File("temp.html");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            Element profile = document.getDocumentElement();
            if(profile.getElementsByTagName("steamID64").item(0) == null) return;
            String steamID64 = profile.getElementsByTagName("steamID64").item(0).getTextContent();
            // Remove CDATA from xml
            String steamName = profile.getElementsByTagName("steamID").item(0).
                    getTextContent().replaceAll("(<\\!\\[CDATA\\[)|(\\]\\]>)","");
            String steamAvatarMini = profile.getElementsByTagName("avatarIcon").item(0).
                    getTextContent().replaceAll("(<\\!\\[CDATA\\[)|(\\]\\]>)","");

            setMain_steamName(steamName);
            setMain_steamID64(steamID64);
            setMain_steamAvatar(steamAvatarMini);

        } catch (IOException | SAXException | ParserConfigurationException e) {
            System.out.println("not problem, but " + e);
        }
    }
}
