package com.example.steam.methods;

public class steamAccount {

    private static String steamID64;
    private static String steamName;
    private static String steamAvatar;
    private static String steamAvatarMini;
    private static String steamMember;
    private static String customURL;
    private static String steamPrivacy;
    private static String steamOnline;

    private static String steamVacBans;
    private static String steamGameBan;
    private static String steamTradeBan;
    private static String steamLimit;

    public static String getSteamID64() {
        return steamID64;
    }

    public static void setSteamID64(String steamID64) {
        steamAccount.steamID64 = steamID64;
    }

    public static String getSteamName() {
        return steamName;
    }

    public static void setSteamName(String steamName) {
        steamAccount.steamName = steamName;
    }

    public static String getCustomURL() {
        return customURL;
    }

    public static void setCustomURL(String customURL) {
        steamAccount.customURL = customURL;
    }

    public static String getSteamPrivacy() {
        return steamPrivacy;
    }

    public static void setSteamPrivacy(String steamPrivacy) {
        steamAccount.steamPrivacy = steamPrivacy;
    }

    public static String getSteamOnline() {
        return steamOnline;
    }

    public static void setSteamOnline(String steamOnline) {
        steamAccount.steamOnline = steamOnline;
    }

    public static String getSteamAvatar() {
        return steamAvatar;
    }

    public static void setSteamAvatar(String steamAvatar) {
        steamAccount.steamAvatar = steamAvatar;
    }

    public static String getSteamVacBans() {
        return steamVacBans;
    }

    public static void setSteamVacBans(String steamVacBans) {
        steamAccount.steamVacBans = steamVacBans;
    }

    public static String getSteamTradeBan() {
        return steamTradeBan;
    }

    public static void setSteamTradeBan(String steamTradeBan) {
        steamAccount.steamTradeBan = steamTradeBan;
    }

    public static String getSteamLimit() {
        return steamLimit;
    }

    public static void setSteamLimit(String steamLimit) {
        steamAccount.steamLimit = steamLimit;
    }

    public static String getSteamMember() {
        return steamMember;
    }

    public static void setSteamMember(String steamMember) {
        steamAccount.steamMember = steamMember;
    }

    public static String getSteamGameBan() {
        return steamGameBan;
    }

    public static void setSteamGameBan(String steamGameBan) {
        steamAccount.steamGameBan = steamGameBan;
    }

    public static String getSteamAvatarMini() {
        return steamAvatarMini;
    }

    public static void setSteamAvatarMini(String steamAvatarMini) {
        steamAccount.steamAvatarMini = steamAvatarMini;
    }
}
