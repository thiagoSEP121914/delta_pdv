package org.example.delta_pdv.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.example.delta_pdv.entities.Produto;

import java.io.InputStream;

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
        System.out.println("Imagem: " + produto.getCaminhoImagem());
        InputStream imageStream = getClass().getResourceAsStream(produto.getCaminhoImagem());

        if (imageStream == null) {
            System.out.println("ERRO CAMINHO INVALIDO!!!");
        }
        Image image = new Image(imageStream);
        produtoImage.setImage(image);
        produtoNome.setText(produto.getNome());
        produtoPreco.setText(String.valueOf(produto.getPrecoUnitario()));
    }
}
