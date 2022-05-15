package com.example.steam;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Window.open("Главная", "main.fxml", 290, 342);
    }

    public static void main(String[] args) {
        launch();
    }
}
