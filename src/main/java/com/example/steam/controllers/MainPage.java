package com.example.steam.controllers;

import com.example.steam.Window;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class MainPage {

    @FXML
    private Button btnSearch;

    @FXML
    private ImageView fieldGitHub;

    @FXML
    private Label fieldHistory;

    @FXML
    private TextField fieldLink;

    @FXML
    private Label fieldNotFound;

    @FXML
    private Label fieldSearch;

    @FXML
    private ImageView fieldSteam;

    @FXML
    private Label fieldVacBans;


    @FXML
    void initialize() {

        eventMouseOnEntered();
        eventMouseOnExited();
        eventMouseOnClicked();
        eventKeyOnField();
    }

    @FXML
    public void eventMouseOnEntered(){
        btnSearch.setOnMouseEntered(event ->
                btnSearch.setStyle("-fx-border-color: black; -fx-background-color: #c6ccd2"));

        fieldSearch.setOnMouseEntered(event ->
                fieldSearch.setStyle("-fx-text-fill:#545151"));
        fieldHistory.setOnMouseEntered(event ->
                fieldHistory.setStyle("-fx-text-fill:#545151"));
        fieldVacBans.setOnMouseEntered(event ->
                fieldVacBans.setStyle("-fx-text-fill:#545151"));
    }

    @FXML
    public void eventMouseOnExited(){
        btnSearch.setOnMouseExited(event ->
                btnSearch.setStyle("-fx-border-color: black; -fx-background-color: LavenderBlush"));

        fieldSearch.setOnMouseExited(event ->
                fieldSearch.setStyle("-fx-text-fill:grey"));
        fieldHistory.setOnMouseExited(event ->
                fieldHistory.setStyle("-fx-text-fill:grey"));
        fieldVacBans.setOnMouseExited(event ->
                fieldVacBans.setStyle("-fx-text-fill:grey"));
    }

    @FXML
    public void eventMouseOnClicked(){
        fieldGitHub.setOnMouseClicked(mouseEvent ->
                Window.openWebpage("https://github.com/KirillNemtyrev"));
        fieldSteam.setOnMouseClicked(mouseEvent ->
                Window.openWebpage("https://steamcommunity.com/profiles/76561199004803730"));
    }

    @FXML
    public void eventKeyOnField(){
        fieldLink.textProperty().addListener((observableValue, oldValue, newValue) -> {
            fieldNotFound.setVisible(false);
            fieldLink.setStyle("-fx-background-color: #c6ccd2; -fx-border-color: black");
        });
    }

}