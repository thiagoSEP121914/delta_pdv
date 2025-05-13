package org.example.delta_pdv.gui;

import com.almasb.fxgl.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

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
}
