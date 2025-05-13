package org.example.delta_pdv.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import org.example.delta_pdv.entities.Produto;

import java.io.InputStream;

public class CategoryCardController {

    @FXML
    private Label categoryName;

    @FXML
    private HBox box;

    public void setData(String nomeCategoria) {
        categoryName.setText(nomeCategoria);
    }
}
