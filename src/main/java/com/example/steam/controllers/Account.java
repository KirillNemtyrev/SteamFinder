package com.example.steam.controllers;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.time.LocalTime;
import java.time.ZoneId;

import com.example.steam.Window;
import com.example.steam.methods.getData;
import com.example.steam.methods.history;
import com.example.steam.methods.steamAccount;
import com.example.steam.methods.vacChecker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Account {

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnTrack;

    @FXML
    private Label fieldAuthor;

    @FXML
    private WebView fieldAvatar;

    @FXML
    private Label fieldHistory;

    @FXML
    private Label fieldLimited;

    @FXML
    private TextField fieldLink;

    @FXML
    private ImageView fieldLogo;

    @FXML
    private Label fieldMember;

    @FXML
    private Label fieldName;

    @FXML
    private Label fieldNotFound;

    @FXML
    private Label fieldOnline;

    @FXML
    private Label fieldPrivacy;

    @FXML
    private Label fieldSettings;

    @FXML
    private Label fieldTextLogo;

    @FXML
    private Label fieldTrack;

    @FXML
    private Label fieldTradeBans;

    @FXML
    private Label fieldVacBan;

    @FXML
    private Label fieldVacBans;

    @FXML
    private Label fieldGameBans;

    @FXML
    void initialize() {

        eventMouseOnEntered();
        eventMouseOnExited();
        eventMouseOnClicked();
        eventKeyOnField();

        eventSetInfo();
    }

    @FXML
    public void eventMouseOnEntered(){
        btnTrack.setOnMouseEntered(event ->
                btnTrack.setStyle("-fx-border-color: black; -fx-background-color: #c6ccd2"));
        btnSearch.setOnMouseEntered(event ->
                btnSearch.setStyle("-fx-border-color: black; -fx-background-color: #c6ccd2"));

        fieldHistory.setOnMouseEntered(event ->
                fieldHistory.setStyle("-fx-text-fill:#545151"));
        fieldSettings.setOnMouseEntered(event ->
                fieldSettings.setStyle("-fx-text-fill:#545151"));
        fieldVacBans.setOnMouseEntered(event ->
                fieldVacBans.setStyle("-fx-text-fill:#545151"));
        fieldAuthor.setOnMouseEntered(event ->
                fieldAuthor.setStyle("-fx-text-fill:#545151"));

        fieldName.setOnMouseEntered(mouseEvent -> {
            fieldName.setStyle("-fx-text-fill:#545151");
            fieldName.setText("Кликни, скопируешь SteamID");
        });
    }

    @FXML
    public void eventMouseOnExited(){
        btnTrack.setOnMouseExited(event ->
                btnTrack.setStyle("-fx-border-color: black; -fx-background-color: LavenderBlush"));
        btnSearch.setOnMouseExited(event ->
                btnSearch.setStyle("-fx-border-color: black; -fx-background-color: LavenderBlush"));

        fieldHistory.setOnMouseExited(event -> {
                fieldHistory.setStyle("-fx-text-fill:grey");
                fieldHistory.setText("История");
        });
        fieldSettings.setOnMouseExited(event ->
                fieldSettings.setStyle("-fx-text-fill:grey"));
        fieldVacBans.setOnMouseExited(event -> {
            fieldVacBans.setStyle("-fx-text-fill:grey");
            fieldVacBans.setText("VacBans");
        });
        fieldAuthor.setOnMouseExited(event ->
                fieldAuthor.setStyle("-fx-text-fill:grey"));

        fieldName.setOnMouseExited(event -> {
            fieldName.setStyle("-fx-text-fill:grey");
            fieldName.setText(steamAccount.getSteamName());
        });
    }

    @FXML
    public void eventMouseOnClicked(){
        btnSearch.setOnAction(actionEvent -> eventActionOnSearch());

        fieldAvatar.setOnMouseClicked(event -> {
            Window.openWebpage("https://steamcommunity.com/profiles/" + steamAccount.getSteamID64());
            return;
        });

        fieldHistory.setOnMouseClicked(event -> {
            if(history.getSizeHistory() == 0) {
                fieldHistory.setText("Пусто..");
                fieldHistory.setStyle("-fx-text-fill:#9e1c1c");
                return;
            }
            Stage stage = (Stage) fieldHistory.getScene().getWindow();
            Window.updateWindow(stage, "История", "history.fxml", 318, 646, false);
        });

        fieldVacBans.setOnMouseClicked(event -> {
            if(vacChecker.getSizeBans() == 0) {
                fieldVacBans.setText("Пусто..");
                fieldVacBans.setStyle("-fx-text-fill:#9e1c1c");
                return;
            }
            Stage stage = (Stage) fieldVacBans.getScene().getWindow();
            Window.updateWindow(stage, "Чекер", "list_checker.fxml", 318, 646, false);
        });

        fieldName.setOnMouseClicked(event -> {
            StringSelection stringSelection = new StringSelection(steamAccount.getSteamID64());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            fieldName.setText("Молодец! Скопировал");
            fieldName.setStyle("-fx-text-fill:#1ca431");
        });

        btnTrack.setOnAction(event -> {
            int index = vacChecker.getIndexSteamID(steamAccount.getSteamID64());

            fieldTrack.setText(index != -1 ? "Не отслеживается" : "Отслеживается");
            fieldTrack.setStyle("-fx-text-fill:" +
                    (index != -1 ? "#0a8228" : "#0a6983"));

            btnTrack.setText(index != -1 ? "Следить" : "Удалить");

            if(index != -1) {
                vacChecker.removeVacChecker(index);
                return;
            }

            String name = steamAccount.getSteamName();
            String steamID64 = steamAccount.getSteamID64();
            String avatar = steamAccount.getSteamAvatarMini();
            String vacBans = steamAccount.getSteamVacBans();
            String gameBans = steamAccount.getSteamGameBan();
            String tradeBan = steamAccount.getSteamTradeBan();
            vacChecker.addData(name, steamID64, avatar, vacBans, gameBans, tradeBan);
        });
    }

    @FXML
    public void eventKeyOnField(){
        fieldLink.textProperty().addListener((observableValue, oldValue, newValue) -> {
            fieldNotFound.setVisible(false);
            fieldLink.setStyle("-fx-background-color: #c6ccd2; -fx-border-color: black");
        });
    }

    @FXML
    public void eventActionOnSearch(){
        String search = fieldLink.getText().trim();

        if(search.isEmpty()){
            fieldNotFound.setVisible(true);
            return;
        }

        getData.getID(search, false);
        eventSetInfo();
    }

    @FXML
    public void eventSetInfo(){
        // Get time
        ZoneId zoneId = ZoneId.systemDefault();
        LocalTime time = LocalTime.now(zoneId);
        int hour = time.getHour();
        String welcome = (hour < 6 ? "Доброй ночи" : hour < 12 ? "Доброго утра" :
                hour < 18 ? "Доброго дня" : "Доброго вечера") + " :)";
        fieldTextLogo.setText(welcome);

        WebEngine engine = fieldAvatar.getEngine();
        engine.load(steamAccount.getSteamAvatar());

        fieldName.setText(steamAccount.getSteamName());

        fieldOnline.setText(steamAccount.getSteamOnline().equals("online") ? "Аккаунт сейчас онлайн!" :
                (steamAccount.getSteamOnline().equals("in-game") ? "Сейчас находится в игре!" : "Аккаунт сейчас оффлайн!"));
        fieldOnline.setStyle(steamAccount.getSteamOnline().equals("online") ? "-fx-text-fill:#57cbde" :
                (steamAccount.getSteamOnline().equals("in-game") ? "-fx-text-fill:#90ba3c" : "-fx-text-fill:#898989"));

        fieldPrivacy.setText(steamAccount.getSteamPrivacy().equals("public") ? "Приватность: Открыт" : "Приватность: Закрыт");
        fieldPrivacy.setStyle(steamAccount.getSteamPrivacy().equals("public") ? "-fx-text-fill:#117539" : "-fx-text-fill:#9e1c1c");

        fieldMember.setText(steamAccount.getSteamMember());

        fieldGameBans.setText(steamAccount.getSteamGameBan());
        fieldGameBans.setStyle(steamAccount.getSteamGameBan().equals("Нет игровых блокировок") ?
                "-fx-text-fill:#117539" : "-fx-text-fill:#9e1c1c");

        fieldVacBan.setText("VAC Бан" +
                (steamAccount.getSteamVacBans().equals("0") ? " не имеется" : "имеется"));
        fieldVacBan.setStyle(steamAccount.getSteamVacBans().equals("0") ? "-fx-text-fill:#117539" : "-fx-text-fill:#9e1c1c");

        fieldTradeBans.setText("Trade бан " +
                (steamAccount.getSteamTradeBan().equals("None") ? "отстуствует" : "имеется"));
        fieldTradeBans.setStyle(steamAccount.getSteamTradeBan().equals("None") ? "-fx-text-fill:#117539" : "-fx-text-fill:#9e1c1c");

        fieldLimited.setText(steamAccount.getSteamLimit().equals("0") ? "Аккаунт без ограничений." : "Аккаунт с ограничениями..");
        fieldLimited.setStyle(steamAccount.getSteamLimit().equals("0") ? "-fx-text-fill:#117539" : "-fx-text-fill:#9e1c1c");

        fieldTrack.setText(
                vacChecker.getIndexSteamID(steamAccount.getSteamID64()) != -1 ? "Отслеживается" : "Не отслеживается");
        fieldTrack.setStyle("-fx-text-fill:" +
                (vacChecker.getIndexSteamID(steamAccount.getSteamID64()) != -1 ? "#0a6983" : "#0a8228"));

        btnTrack.setText(vacChecker.getIndexSteamID(steamAccount.getSteamID64()) != -1 ? "Удалить" : "Следить");
    }

}
