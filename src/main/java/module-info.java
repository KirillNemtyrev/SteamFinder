module com.example.steam {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    //requires json.simple;
    //requires org.jsoup;

    opens com.example.steam to javafx.fxml;
    exports com.example.steam;

    opens com.example.steam.controllers to javafx.fxml;
    exports com.example.steam.controllers;
}