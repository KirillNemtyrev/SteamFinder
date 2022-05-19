package com.example.steam;

import com.example.steam.methods.*;
import javafx.application.Application;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, ParserConfigurationException, SAXException {

        configApp.getParams();
        history.getHistory();
        vacChecker.getVacBans();
        vacChecker.getCheckedAccount();

        getData.getID("https://steamcommunity.com/profiles/" + configApp.steamIDValue(), true);
        Window.open("Главная", "account.fxml", 318, 646);
    }

    public static void main(String[] args) {
        launch();
    }
}
