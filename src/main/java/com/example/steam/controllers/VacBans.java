package com.example.steam.controllers;

import com.example.steam.Window;
import com.example.steam.methods.getData;
import com.example.steam.methods.history;
import com.example.steam.methods.vacChecker;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import java.time.LocalTime;
import java.time.ZoneId;

public class VacBans {
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
    private Label fieldHistory;

    @FXML
    void initialize() {

        eventMouseOnEntered();
        eventMouseOnExited();
        eventMouseOnClicked();

        eventSetInfo();

        PageCount.setPageCount((int) Math.ceil((double) vacChecker.getSizeBans()/8.0));
        setupPagination();
    }

    @FXML
    public void eventMouseOnEntered(){
        fieldMain.setOnMouseEntered(event ->
                fieldMain.setStyle("-fx-text-fill:#545151"));
        fieldSettings.setOnMouseEntered(event ->
                fieldSettings.setStyle("-fx-text-fill:#545151"));
        fieldHistory.setOnMouseEntered(event ->
                fieldHistory.setStyle("-fx-text-fill:#545151"));
        fieldAuthor.setOnMouseEntered(event ->
                fieldAuthor.setStyle("-fx-text-fill:#545151"));
    }

    @FXML
    public void eventMouseOnExited(){
        fieldMain.setOnMouseExited(event ->
                fieldMain.setStyle("-fx-text-fill:grey"));
        fieldSettings.setOnMouseExited(event ->
                fieldSettings.setStyle("-fx-text-fill:grey"));
        fieldHistory.setOnMouseExited(event -> {
            fieldHistory.setStyle("-fx-text-fill:grey");
            fieldHistory.setText("История");
        });
        fieldAuthor.setOnMouseExited(event ->
                fieldAuthor.setStyle("-fx-text-fill:grey"));
    }

    @FXML
    public void eventMouseOnClicked(){
        fieldMain.setOnMouseClicked(event -> {
            Stage stage = (Stage) fieldMain.getScene().getWindow();
            Window.updateWindow(stage, "Главная", "account.fxml", 318, 646, false);
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
            while (8*pageIndex + count <= vacChecker.getSizeBans() && count <= 8){

                final double posX = 15.0; // default
                double posY = 10.0 + 55.0*(count - 1);
                int index = vacChecker.getSizeBans() - (8*pageIndex + count);

                PagePane.getChildren().add(insertInfo(posX, posY, index));
                count++;
            }
            return PagePane;
        });
    }

    @FXML
    public AnchorPane createActivePane(){
        AnchorPane pane = new AnchorPane();
        pane.setPrefWidth(250);
        pane.setPrefHeight(449);
        pane.setLayoutX(14);
        pane.setLayoutY(86);
        return pane;
    }

    @FXML
    public AnchorPane createPane(double posX, double posY, String SteamID64, boolean border_red) {

        AnchorPane ListPane = new AnchorPane();
        ListPane.setLayoutX(posX);
        ListPane.setLayoutY(posY);
        ListPane.setPrefWidth(253.0);
        ListPane.setPrefHeight(49.0);
        ListPane.setCursor(Cursor.cursor("HAND"));
        ListPane.setStyle("-fx-background-color: #2f353c;" + (border_red ? "-fx-border-color:red" : ""));

        ListPane.setOnMouseEntered(event -> ListPane.setStyle("-fx-background-color: #192128"));
        ListPane.setOnMouseExited(event -> ListPane.setStyle("-fx-background-color: #2f353c"));
        ListPane.setOnMouseClicked(event -> {
            getData.getID("https://steamcommunity.com/profiles/" + SteamID64, true);
            Stage stage = (Stage) fieldMain.getScene().getWindow();
            Window.updateWindow(stage, "Главная", "account.fxml", 318, 646, false);
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

        JSONObject data = vacChecker.getData(index);
        int Remindes = (int) data.get("MessageCount");
        AnchorPane farmPane = createPane(posX, posY, (String) data.get("SteamID64"), Remindes != 0);

        String NAME = (String) data.get("Name");
        addLabel(farmPane, "CENTER", NAME, "GREY", 54.0, 15.0, 179,18);

        if(Remindes != 0)
            addLabel(farmPane, "CENTER",
                    "+" + ((Remindes <= 9) ? Remindes : "9"), "#b25050", 52, 27, 18,18);

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
