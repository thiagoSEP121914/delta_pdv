package org.example.delta_pdv.gui.utils;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ScreenLoader {


    public static void loadForm(Parent parent, boolean resizable) {
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setResizable(resizable);
        stage.getIcons().add(new Image(ScreenLoader.class.getResourceAsStream("/org/example/delta_pdv/icons/cabala.png")));
        stage.setTitle("Sistema PDV");
        stage.show();
    }

    public static void loadForm(Parent parent) {
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setResizable(true);
        stage.show();
    }



}
