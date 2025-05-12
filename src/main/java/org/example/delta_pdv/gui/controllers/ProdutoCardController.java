package org.example.delta_pdv.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.example.delta_pdv.entities.Produto;

public class ProdutoCardController {
    @FXML
    private HBox box;

    @FXML
    private ImageView produtoImage;

    @FXML
    private Label produtoNome;

    @FXML
    private Label produtoPreco;

    public void setData(Produto produto) {
        Image image = new Image(getClass().getResourceAsStream(produto.getCaminhoImagem()));
        produtoImage.setImage(image);
        produtoNome.setText(produto.getNome());
        produtoPreco.setText(produto.getPreco());
    }
}
