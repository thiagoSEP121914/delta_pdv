package org.example.delta_pdv.gui.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts {
    public static void showAlert(String title, String header, String content, Alert.AlertType type) {
        // Cria o alerta com o tipo especificado
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static Optional<ButtonType> showAlertYesNo(String title, String header, String content, AlertType type) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType buttonSim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType buttonNao = new ButtonType("Não", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(buttonSim, buttonNao);

        return alert.showAndWait(); // Retorna a escolha do usuário
    }
}
