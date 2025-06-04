package org.example.delta_pdv.gui.utils;

import com.almasb.fxgl.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ScreenLoader {

    private BorderPane borderPane;

    public ScreenLoader(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    public static void loadForm(String url) {
        try {
            URL resource = ScreenLoader.class.getResource(url);
            if (resource == null) {
                throw new RuntimeException("Não foi possível encontrar o caminho: " + url);
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Formulário");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível carregar a view: " + e.getMessage(), e);
        }
    }

    public static void loadForm(Parent parent) {
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
