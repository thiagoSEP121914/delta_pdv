package org.example.delta_pdv.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CategoriaCardController {

    @FXML
    private Label categoryName;

    @FXML
    private HBox box;

    public void setData(String nomeCategoria) {
        categoryName.setText(nomeCategoria);
    }
}
