package com.example.steam.controllers;

import com.example.steam.Window;
import com.example.steam.methods.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class VacChecker {

    @FXML
    private Button btnTrack;

    @FXML
    private Label fieldBans;

    @FXML
    private Label fieldName;

    @FXML
    private Label fieldSteamID;

    @FXML
    private Label fieldTrack;

    @FXML
    private WebView webAvatar;

    @FXML
    private Label fieldMore;

    @FXML
    void initialize() {
        eventMouseOnEntered();
        eventMouseOnExited();
        eventMouseOnClicked();
        
        eventSetupText();
    }

    @FXML
    public void eventMouseOnEntered(){

        btnTrack.setOnMouseEntered(event ->
                btnTrack.setStyle("-fx-border-color: black; -fx-background-color: #c6ccd2"));

        fieldMore.setOnMouseEntered(event -> fieldMore.setStyle("-fx-text-fill:#545151"));
        fieldSteamID.setOnMouseEntered(event -> {
            fieldSteamID.setStyle("-fx-text-fill:#545151");
            fieldSteamID.setText("Чтобы скопировать, кликни.");
        });
    }

    @FXML
    public void eventMouseOnExited(){

        btnTrack.setOnMouseExited(event ->
                btnTrack.setStyle("-fx-border-color: black; -fx-background-color: LavenderBlush"));

        fieldMore.setOnMouseExited(event -> fieldMore.setStyle("-fx-text-fill:grey"));
        fieldSteamID.setOnMouseExited(event -> {
            fieldSteamID.setStyle("-fx-text-fill:grey");
            fieldSteamID.setText("ID: " + vacChecker.getSteamID64());
        });
    }

    @FXML
    public void eventMouseOnClicked(){

        fieldMore.setOnMouseClicked(event -> {
            Stage stage = (Stage) fieldMore.getScene().getWindow();
            getData.getID("https://steamcommunity.com/profiles/" + vacChecker.getSteamID64(), true);
            Window.updateWindow((Stage) stage.getOwner(), "Главная", "account.fxml", 318, 646, false);
            stage.close();
        });
        webAvatar.setOnMouseClicked(event -> {
            Window.openWebpage("https://steamcommunity.com/profiles/" + vacChecker.getSteamID64());
            return;
        });

        fieldName.setOnMouseClicked(event -> {
            StringSelection stringSelection = new StringSelection(vacChecker.getSteamID64());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            fieldName.setText("Молодец! Скопировал");
            fieldName.setStyle("-fx-text-fill:#1ca431");
        });

        btnTrack.setOnAction(event -> {
            Stage stage = (Stage) btnTrack.getScene().getWindow();
            int index = vacChecker.getIndexSteamID(vacChecker.getSteamID64());

            fieldTrack.setText(index != -1 ? "Не отслеживается" : "Отслеживается");
            fieldTrack.setStyle("-fx-text-fill:" +
                    (index != -1 ? "#0a8228" : "#0a6983"));

            btnTrack.setText(index != -1 ? "Следить" : "Удалить");

            if(index != -1) {
                vacChecker.removeVacChecker(index);
                if(vacChecker.getSizeBans() == 0){
                    Window.updateWindow((Stage) stage.getOwner(), "Главная", "account.fxml", 318, 646, false);
                    return;
                }
                Window.updateWindow((Stage) stage.getOwner(), "Чекер", "list_checker.fxml", 318, 646, false);
                return;
            }

            String name = vacChecker.getSteamName();
            String steamID64 = vacChecker.getSteamID64();
            String avatar = vacChecker.getSteamAvatar();
            Long vacBans = vacChecker.getSteamVacBan();
            String gameBans = vacChecker.getSteamGameBan();
            String tradeBan = vacChecker.getSteamTradeBan();
            vacChecker.addData(name, steamID64, avatar, vacBans, gameBans, tradeBan);
            Window.updateWindow((Stage) stage.getOwner(), "Чекер", "list_checker.fxml", 318, 646, false);
        });
    }

    @FXML
    void eventSetupText(){
        JSONObject profile = vacChecker.getData(vacChecker.getIndexData());

        WebEngine engine = webAvatar.getEngine();
        engine.load(vacChecker.getSteamAvatar());

        fieldName.setText(vacChecker.getSteamName());
        fieldSteamID.setText("ID: " + vacChecker.getSteamID64());

        fieldTrack.setText(
                vacChecker.getIndexSteamID(vacChecker.getSteamID64()) != -1 ? "Отслеживается" : "Не отслеживается");
        fieldTrack.setStyle("-fx-text-fill:" +
                (vacChecker.getIndexSteamID(vacChecker.getSteamID64()) != -1 ? "#0a6983" : "#0a8228"));

        btnTrack.setText(vacChecker.getIndexSteamID(vacChecker.getSteamID64()) != -1 ? "Удалить" : "Следить");

        fieldBans.setText((int) profile.get("MessageCount") == 0 ? "Новых блокировок с момента слежки не обнаружено..." :
                ( (profile.get("VacBans") != profile.get("NewVacBans")) ? "Получен VacBan " : "") +
                        (!profile.get("TradeBans").equals(profile.get("NewTradeBans")) ? "Получен Трейд-Бан " : "") +
                        (!profile.get("GameBans").equals(profile.get("NewGameBans")) ? "Получен Game Бан " : ""));
        fieldBans.setStyle("-fx-text-fill:" + ((int) profile.get("MessageCount") == 0 ? "grey" : "#ae4040"));

        if((int) profile.get("MessageCount") != 0){
            //Stage stage = (Stage) webAvatar.getScene().getWindow();
            vacChecker.removeVacChecker(vacChecker.getIndexData());

            vacChecker.addData((String) profile.get("Name"), (String) profile.get("SteamID64"),
                    (String) profile.get("Avatar"), Long.valueOf(String.valueOf(profile.get("NewVacBans"))),
                    (String) profile.get("NewGameBans"), (String) profile.get("NewTradeBans"));

            vacChecker.setIndexData(vacChecker.getIndexSteamID(vacChecker.getSteamID64()));
            //Window.updateWindow((Stage) stage.getOwner(), "Чекер", "list_checker.fxml", 318, 646, false);
        }
    }
}
