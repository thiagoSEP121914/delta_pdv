package org.example.delta_pdv.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.example.delta_pdv.entities.ItemVenda;
import org.example.delta_pdv.entities.Produto;

import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;

public class ProdutoCardController {

    @FXML
    private ImageView produtoImage;

    @FXML
    private Label produtoNome;

    @FXML
    private Label produtoPreco;
    private Produto produto;

    private PdvController pdvController;

    public void setPdvController(PdvController pdvController) {
        this.pdvController = pdvController;
    }

    public void setData(Produto produto) {
        this.produto = produto;
        produtoNome.setText(produto.getNome());

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        produtoPreco.setText(formatter.format(produto.getPrecoUnitario()));

        String caminhoImagem = produto.getCaminhoImagem();
        if (caminhoImagem != null && !caminhoImagem.trim().isEmpty()) {
            File arquivoImagem = new File(caminhoImagem);

            if (arquivoImagem.exists()) {
                Image image = new Image(arquivoImagem.toURI().toString());
                produtoImage.setImage(image);
            } else {
                System.out.println("Imagem não encontrada: " + caminhoImagem);
                setImagemPadrao();
            }
        } else {
            setImagemPadrao();
        }
    }

    private void setImagemPadrao() {
        Image imagemPadrao = new Image(getClass().getResource("/org/example/delta_pdv/icons/user.png").toExternalForm());
        produtoImage.setImage(imagemPadrao);
    }

    @FXML
    void onBtnAddOnAction(ActionEvent event) {
        if (produto != null && pdvController != null) {
            ItemVenda itemVenda = new ItemVenda();
            itemVenda.setProduto(produto);
            itemVenda.setPrecoUnitario(produto.getPrecoUnitario());
            itemVenda.setQtd(1);
            pdvController.addItemVenda(itemVenda);
        }
    }
}
