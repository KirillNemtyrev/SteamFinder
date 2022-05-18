package com.example.steam.methods;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class getIDtoLink {

    public static void getID(String link, boolean temp) {
        try {
            String URLbasic = link + "/?xml=1";
            if(!download_file(URLbasic)) return;

            File xmlFile = new File("temp.html");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            Element profile = document.getDocumentElement();
            String steamID64 = profile.getElementsByTagName("steamID64").item(0).getTextContent();
            String steamOnline = profile.getElementsByTagName("onlineState").item(0).getTextContent();
            String steamVacBans = profile.getElementsByTagName("vacBanned").item(0).getTextContent();
            String steamTradeBan = profile.getElementsByTagName("tradeBanState").item(0).getTextContent();
            String steamLimit = profile.getElementsByTagName("isLimitedAccount").item(0).getTextContent();
            String memberSince = profile.getElementsByTagName("memberSince").item(0).getTextContent();
            String steamPrivacy = profile.getElementsByTagName("privacyState").item(0).getTextContent();

            // Remove CDATA from xml
            String steamName = profile.getElementsByTagName("steamID").item(0).
                    getTextContent().replaceAll("(<\\!\\[CDATA\\[)|(\\]\\]>)","");
            String steamAvatar = profile.getElementsByTagName("avatarFull").item(0).
                    getTextContent().replaceAll("(<\\!\\[CDATA\\[)|(\\]\\]>)","");
            String steamAvatarMini = profile.getElementsByTagName("avatarFull").item(0).
                    getTextContent().replaceAll("(<\\!\\[CDATA\\[)|(\\]\\]>)","");

            steamAccount.setSteamID64(steamID64);
            steamAccount.setSteamName(steamName);
            steamAccount.setSteamAvatar(steamAvatar);
            steamAccount.setSteamOnline(steamOnline);
            steamAccount.setSteamPrivacy(steamPrivacy);
            steamAccount.setSteamMember(memberSince);
            steamAccount.setSteamVacBans(steamVacBans);
            steamAccount.setSteamTradeBan(steamTradeBan);
            steamAccount.setSteamLimit(steamLimit);

            if(!temp) {
                history.addData(steamName, steamID64, steamAvatarMini);
                configApp.setSteamID_LAST(steamID64);
                configApp.saveParams();
            }

            // Remove file temp
            xmlFile.delete();
        }
        catch (IOException | SAXException | ParserConfigurationException e) {
            System.out.println("Open error parsing " + e);
        }
    }

    public static boolean download_file(String link){
        try {
            URL website = new URL(link);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream( "temp.html");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            return true;
        }
        catch (IOException e) {
            System.out.println("Open error download " + e);
            return false;
        }
    }
}
