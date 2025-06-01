package org.example.delta_pdv.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.example.delta_pdv.entities.Produto;

import java.io.File;

public class ProdutoCardController {
    @FXML
    private HBox box;

    @FXML
    private ImageView produtoImage;

    @FXML
    private Label produtoNome;

    @FXML
    private Label produtoPreco;

    @FXML
    private Button btnAdicionar;

    private Produto produto;

    private PdvController pdvController;

    public void setPdvController(PdvController pdvController) {
        this.pdvController = pdvController;
    }

    public void setData(Produto produto) {
        this.produto = produto;
        System.out.println("Imagem: " + produto.getCaminhoImagem());

        String caminhoImagem = produto.getCaminhoImagem();  // Exemplo: "C:/imagens/1748620972817_shureki.jpg"

        File arquivoImagem = new File(caminhoImagem);
        if (!arquivoImagem.exists()) {
            System.out.println("ERRO CAMINHO INVALIDO!!! Arquivo não encontrado no disco.");
            // Coloque uma imagem padrão ou trate o erro
            produtoImage.setImage(new Image("file:/caminho/para/imagem/default.png"));
        } else {
            Image image = new Image(arquivoImagem.toURI().toString());
            produtoImage.setImage(image);
        }

        produtoNome.setText(produto.getNome());
        produtoPreco.setText(String.valueOf(produto.getPrecoUnitario()));

    }

    @FXML
    void onBtnAddOnAction(ActionEvent event) {
        pdvController.addProdutosSelecionado(produto);
    }


}
