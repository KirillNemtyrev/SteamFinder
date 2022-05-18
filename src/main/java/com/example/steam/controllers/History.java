package com.example.steam.controllers;

import com.example.steam.Window;
import com.example.steam.methods.configApp;
import com.example.steam.methods.getIDtoLink;
import com.example.steam.methods.history;
import com.example.steam.methods.steamAccount;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.time.LocalTime;
import java.time.ZoneId;

public class History {
    @FXML
    private Pagination PageCount;

    @FXML
    private Label fieldAuthor;

    @FXML
    private Label fieldMain;

    @FXML
    private Label fieldSettings;

    @FXML
    private Label fieldTextLogo;

    @FXML
    private Label fieldVacBans;

    @FXML
    void initialize() {

        eventMouseOnEntered();
        eventMouseOnExited();
        eventMouseOnClicked();

        eventSetInfo();

        PageCount.setPageCount((int) Math.ceil((double) history.getSizeHistory()/9.0));
        setupPagination();
    }

    @FXML
    public void eventMouseOnEntered(){
        fieldMain.setOnMouseEntered(event ->
                fieldMain.setStyle("-fx-text-fill:#545151"));
        fieldSettings.setOnMouseEntered(event ->
                fieldSettings.setStyle("-fx-text-fill:#545151"));
        fieldVacBans.setOnMouseEntered(event ->
                fieldVacBans.setStyle("-fx-text-fill:#545151"));
        fieldAuthor.setOnMouseEntered(event ->
                fieldAuthor.setStyle("-fx-text-fill:#545151"));
    }

    @FXML
    public void eventMouseOnExited(){
        fieldMain.setOnMouseExited(event ->
                fieldMain.setStyle("-fx-text-fill:grey"));
        fieldSettings.setOnMouseExited(event ->
                fieldSettings.setStyle("-fx-text-fill:grey"));
        fieldVacBans.setOnMouseExited(event ->
                fieldVacBans.setStyle("-fx-text-fill:grey"));
        fieldAuthor.setOnMouseExited(event ->
                fieldAuthor.setStyle("-fx-text-fill:grey"));
    }

    @FXML
    public void eventMouseOnClicked(){
        fieldMain.setOnMouseClicked(event -> {
            Stage stage = (Stage) fieldMain.getScene().getWindow();
            Window.updateWindow(stage, "Главная", "account.fxml", 318, 728, false);
        });
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
    }

    @FXML
    public void setupPagination(){
        PageCount.setPageFactory((pageIndex) -> {
            int count = 1;
            AnchorPane PagePane = createActivePane();
            while (9*pageIndex + count <= history.getSizeHistory() && count <= 9){

                final double posX = 15.0; // default
                double posY = 10.0 + 55.0*(count - 1);
                int index = 9*pageIndex + (count - 1);

                PagePane.getChildren().add(insertInfo(posX, posY, index));
                count++;
            }
            return PagePane;
        });
    }

    @FXML
    public AnchorPane createActivePane(){
        AnchorPane pane = new AnchorPane();
        pane.setPrefWidth(262);
        pane.setPrefHeight(527);
        pane.setLayoutX(14);
        pane.setLayoutY(86);
        return pane;
    }

    @FXML
    public AnchorPane createPane(double posX, double posY, String SteamID64) {

        AnchorPane ListPane = new AnchorPane();
        ListPane.setLayoutX(posX);
        ListPane.setLayoutY(posY);
        ListPane.setPrefWidth(253.0);
        ListPane.setPrefHeight(49.0);
        ListPane.setCursor(Cursor.cursor("HAND"));
        ListPane.setStyle("-fx-background-color: #2f353c");

        ListPane.setOnMouseEntered(event -> ListPane.setStyle("-fx-background-color: #192128"));
        ListPane.setOnMouseExited(event -> ListPane.setStyle("-fx-background-color: #2f353c"));
        ListPane.setOnMouseClicked(event -> {
            getIDtoLink.getID("https://steamcommunity.com/profiles/" + SteamID64, true);
            Stage stage = (Stage) fieldMain.getScene().getWindow();
            Window.updateWindow(stage, "Главная", "account.fxml", 318, 728, false);
        });

        return ListPane;
    }

    @FXML
    public void addLabel(AnchorPane ListPane, String Aligmnment, String name, String color, double posX, double posY,
                         double prefWidth, double prefHeight) {
        Label Name = new Label(name);
        Name.setFont(Font.font("Consolas Bold", 16.0));
        Name.setTextFill(Paint.valueOf(color));
        Name.setLayoutX(posX);
        Name.setLayoutY(posY);
        Name.setPrefWidth(prefWidth);
        Name.setPrefHeight(prefHeight);
        if(!Aligmnment.isEmpty()) Name.setAlignment(Pos.valueOf(Aligmnment));
        ListPane.getChildren().add(Name);
    }

    @FXML
    public AnchorPane insertInfo(double posX, double posY, int index){

        JSONObject data = history.getData(index);
        AnchorPane farmPane = createPane(posX, posY, (String) data.get("SteamID64"));

        String NAME = (String) data.get("Name");
        addLabel(farmPane, "CENTER", NAME, "GREY", 54.0, 15.0, 179,18);

        WebView Avatar = new WebView();
        Avatar.setPrefHeight(32.0);
        Avatar.setPrefWidth(32.0);
        Avatar.setLayoutX(14);
        Avatar.setLayoutY(9);
        farmPane.getChildren().add(Avatar);

        WebEngine engine = Avatar.getEngine();
        engine.load((String) data.get("Avatar"));

        return farmPane;
    }
}
