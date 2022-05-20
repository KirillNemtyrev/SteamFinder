package com.example.steam.controllers;

import com.example.steam.Window;
import com.example.steam.methods.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Settings {

    @FXML
    private Button btnLastSearch;

    @FXML
    private Button btnReminder;

    @FXML
    private Button btnSearch;

    @FXML
    private Label clearHistory;

    @FXML
    private Label clearVacBans;

    @FXML
    private Label fieldClearAll;

    @FXML
    private Label fieldHourChecker;

    @FXML
    private TextField fieldLink;

    @FXML
    private Label fieldMinusHour;

    @FXML
    private Label fieldName;

    @FXML
    private Label fieldPlusHour;

    @FXML
    private Label fieldReminder;

    @FXML
    private Label fieldSteamID;

    @FXML
    private Label fieldVacChecker;

    @FXML
    private WebView webAvatar;

    @FXML
    void initialize() {
        eventSetupText();

        eventMouseOnEntered();
        eventMouseOnExited();
        eventMouseOnClicked();
    }

    @FXML
    void eventSetupText(){
        configApp.getLastData();
        configApp.getMainData();

        fieldReminder.setText("Уведомления " + (configApp.isSendNotification() ? "разрешены" : "запрещены"));
        btnReminder.setText(configApp.isSendNotification() ? "Запретить" : "Разрешить");

        fieldVacChecker.setText("Список VacBans будет проверятся каждые " + configApp.getHourVacChecker() + " часов");
        fieldHourChecker.setText(String.valueOf(configApp.getHourVacChecker()));

        btnLastSearch.setText(configApp.isShowLastProfile() ? "Не Показывать" : "Показывать");

        WebEngine engine = webAvatar.getEngine();
        engine.load(configApp.isShowLastProfile() ? configApp.getLast_steamAvatar() : configApp.getMain_steamAvatar());
        fieldName.setText(configApp.isShowLastProfile() ? configApp.getLast_steamName() : configApp.getMain_steamName());
        fieldSteamID.setText("ID: " +
                (configApp.isShowLastProfile() ? configApp.steamIDValue() : configApp.getMain_steamID64()));
    }

    @FXML
    public void eventMouseOnEntered(){
        btnReminder.setOnMouseEntered(event ->
                btnReminder.setStyle("-fx-border-color: black; -fx-background-color: #c6ccd2"));
        btnLastSearch.setOnMouseEntered(event ->
                btnLastSearch.setStyle("-fx-border-color: black; -fx-background-color: #c6ccd2"));
        btnSearch.setOnMouseEntered(event ->
                btnSearch.setStyle("-fx-border-color: black; -fx-background-color: #c6ccd2"));

        clearHistory.setOnMouseEntered(event ->
                clearHistory.setStyle("-fx-text-fill:#545151"));
        clearVacBans.setOnMouseEntered(event ->
                clearVacBans.setStyle("-fx-text-fill:#545151"));
        fieldClearAll.setOnMouseEntered(event ->
                fieldClearAll.setStyle("-fx-text-fill:#5e0606"));

        fieldMinusHour.setOnMouseEntered(event ->
                fieldMinusHour.setStyle("-fx-text-fill:#545151"));
        fieldPlusHour.setOnMouseEntered(event ->
                fieldPlusHour.setStyle("-fx-text-fill:#545151"));
        fieldSteamID.setOnMouseEntered(event -> {
            fieldSteamID.setStyle("-fx-text-fill:#545151");
            fieldSteamID.setText("Чтобы скопировать, кликни.");
        });
    }

    @FXML
    public void eventMouseOnExited(){
        btnReminder.setOnMouseExited(event ->
                btnReminder.setStyle("-fx-border-color: black; -fx-background-color: LavenderBlush"));
        btnLastSearch.setOnMouseExited(event ->
                btnLastSearch.setStyle("-fx-border-color: black; -fx-background-color: LavenderBlush"));
        btnSearch.setOnMouseExited(event ->
                btnSearch.setStyle("-fx-border-color: black; -fx-background-color: LavenderBlush"));

        clearHistory.setOnMouseExited(event ->
                clearHistory.setStyle("-fx-text-fill:grey"));
        clearVacBans.setOnMouseExited(event ->
                clearVacBans.setStyle("-fx-text-fill:grey"));
        fieldClearAll.setOnMouseExited(event ->
                fieldClearAll.setStyle("-fx-text-fill:#9a1414"));

        fieldMinusHour.setOnMouseExited(event ->
                fieldMinusHour.setStyle("-fx-text-fill:grey"));
        fieldPlusHour.setOnMouseExited(event ->
                fieldPlusHour.setStyle("-fx-text-fill:grey"));
        fieldSteamID.setOnMouseExited(event -> {
            fieldSteamID.setStyle("-fx-text-fill:grey");
            fieldSteamID.setText("ID: " +
                    (configApp.isShowLastProfile() ? configApp.steamIDValue() : configApp.getMain_steamID64()));
        });
    }

    @FXML
    public void eventMouseOnClicked(){
        fieldMinusHour.setOnMouseClicked(event -> {
            int getValue = configApp.getHourVacChecker() - 1;
            if(getValue < 1) getValue = 24;

            fieldVacChecker.setText("Список VacBans будет проверятся каждые " + getValue + " часов");
            fieldHourChecker.setText(String.valueOf(getValue));
            fieldMinusHour.setStyle("-fx-text-fill:red");

            configApp.setHourVacChecker(getValue);
            configApp.saveParams();
        });

        fieldPlusHour.setOnMouseClicked(event -> {
            int getValue = configApp.getHourVacChecker() + 1;
            if(getValue > 24) getValue = 1;

            fieldVacChecker.setText("Список VacBans будет проверятся каждые " + getValue + " часов");
            fieldHourChecker.setText(String.valueOf(getValue));
            fieldPlusHour.setStyle("-fx-text-fill:lightgreen");

            configApp.setHourVacChecker(getValue);
            configApp.saveParams();
        });

        btnReminder.setOnAction(event -> {
            configApp.setSendNotification(!configApp.isSendNotification());
            configApp.saveParams();

            fieldReminder.setText("Уведомления " + (!configApp.isSendNotification() ? "запрещены" : "разрешены"));
            btnReminder.setText(!configApp.isSendNotification() ? "Разрешить" : "Запретить");
        });

        btnLastSearch.setOnAction(event -> {
            configApp.setShowLastProfile(!configApp.isShowLastProfile());
            configApp.saveParams();

            btnLastSearch.setText(configApp.isShowLastProfile() ? "Не Показывать" : "Показывать");

            WebEngine engine = webAvatar.getEngine();
            engine.load(configApp.isShowLastProfile() ? configApp.getLast_steamAvatar() : configApp.getMain_steamAvatar());
            fieldName.setText(configApp.isShowLastProfile() ? configApp.getLast_steamName() : configApp.getMain_steamName());
            fieldSteamID.setText("ID: " +
                    (configApp.isShowLastProfile() ? configApp.steamIDValue() : configApp.getMain_steamID64()));
        });

        fieldSteamID.setOnMouseClicked(event -> {
            StringSelection stringSelection = new StringSelection(
                    configApp.isShowLastProfile() ? configApp.steamIDValue() : configApp.getMain_steamID64());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            fieldSteamID.setText("Молодец! Скопировал");
            fieldSteamID.setStyle("-fx-text-fill:#1ca431");
        });

        clearHistory.setOnMouseClicked(event -> {
            Stage stage = (Stage) clearHistory.getScene().getWindow();
            history.clearHistory();
            Window.updateWindow((Stage) stage.getOwner(), "Главная", "account.fxml", 318, 646, false);
            stage.close();
        });

        clearVacBans.setOnMouseClicked(event -> {
            Stage stage = (Stage) clearVacBans.getScene().getWindow();
            vacChecker.clearVacBans();
            Window.updateWindow((Stage) stage.getOwner(), "Главная", "account.fxml", 318, 646, false);
            stage.close();
        });

        fieldClearAll.setOnMouseClicked(event -> {
            Stage stage = (Stage) fieldClearAll.getScene().getWindow();
            history.clearHistory();
            vacChecker.clearVacBans();
            configApp.clearSettings();
            Window.updateWindow((Stage) stage.getOwner(), "Главная", "account.fxml", 318, 646, true);
            stage.close();
        });

        btnSearch.setOnAction(event -> eventChangeMain());
    }

    @FXML
    void eventChangeMain(){
        String search = fieldLink.getText().trim();
        String oldSteamID = configApp.getMain_steamID64();

        if(search.isEmpty()) return;

        configApp.getInfoUrl("https://steamcommunity.com/profiles/" + search);
        configApp.getInfoUrl("https://steamcommunity.com/id/" + search);
        configApp.getInfoUrl(search);

        configApp.setShowLastProfile(true);
        configApp.saveParams();

        if(oldSteamID.equals(configApp.getMain_steamID64())) return;

        WebEngine engine = webAvatar.getEngine();
        engine.load(configApp.getMain_steamAvatar());

        btnLastSearch.setText("Не Показывать");
        fieldName.setText(configApp.getMain_steamName());
        fieldSteamID.setText("ID: " + configApp.getMain_steamID64());
    }
}