package com.example.steam;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class Window {

    private static Scene scene;

    public static void open(String name, String file, double weight, double height){
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("scene/" + file));
            scene = new Scene(fxmlLoader.load(), weight, height);
            stage.setResizable(false);
            stage.setTitle(name);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void openModal(Stage stageOwner, String name, String file, double weight, double height){
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("scene/" + file));
            scene = new Scene(fxmlLoader.load(), weight, height);
            stage.setTitle(name);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(stageOwner);
            stage.show();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void updateWindow(Stage stage, String name, String file, double weight, double height) {
        updateWindow(stage, name,file, weight, height, true);
    }

    public static void updateWindow(Stage stage, String name, String file, double weight, double height, boolean hide){
        try {
            if (hide) stage.hide();
            FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("scene/" + file));
            scene = new Scene(fxmlLoader.load(), weight, height);
            stage.setTitle(name);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void openWebpage(String urlString) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(urlString));
            } catch (IOException | URISyntaxException e){
                throw new RuntimeException(e);
            }
        }
    }
}