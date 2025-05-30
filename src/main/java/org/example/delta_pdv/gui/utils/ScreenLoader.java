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

public class ScreenLoader {

    private BorderPane borderPane;

    public ScreenLoader(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    public void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            borderPane.setCenter(root);
        } catch (IOException exception) {
            throw new RuntimeException("Erro ao carregar a página: " + fxmlPath, exception);
        }
    }

    public static void loadForm(Parent parent) {
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
