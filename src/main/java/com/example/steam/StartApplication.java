package com.example.steam;

import com.example.steam.methods.*;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        configApp.getParams();
        history.getHistory();

        getIDtoLink.getID("https://steamcommunity.com/profiles/" + configApp.steamIDValue(), true);
        Window.open("Главная", "account.fxml", 318, 728);
    }

    public static void main(String[] args) {
        launch();
    }
}
