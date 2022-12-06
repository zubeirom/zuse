package com.htwsaar.zuse;


import com.htwsaar.zuse.util.JPAUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class IndexApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        JPAUtil.getEntityManagerFactory();
        FXMLLoader fxmlLoader = new FXMLLoader(IndexApplication.class.getResource("index-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        scene.getStylesheets().add("/zuse/src/main/resources/com/htwsaar/zuse/modena-dark.css");
        stage.setMinHeight(750);
        stage.setMinWidth(750);
        stage.setTitle("Zuse Warehouse Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch();}
}