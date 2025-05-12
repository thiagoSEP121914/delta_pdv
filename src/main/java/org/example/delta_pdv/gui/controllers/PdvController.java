package org.example.delta_pdv.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import org.example.delta_pdv.entities.Produto;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PdvController implements Initializable {

    @FXML
    private HBox cardLayout;

    private List<Produto> recentlyAdded;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       try {
           for (int i = 0; i < recentlyAdded.size(); i++) {
               FXMLLoader fxmlLoader = new FXMLLoader();
               fxmlLoader.setLocation(getClass().getResource("produtoCard.fxml"));
               HBox cardBox = fxmlLoader.load();
               ProdutoCardController cardController = fxmlLoader.getController();
               cardController.setData(recentlyAdded.get(i));
               cardLayout.getChildren().add(cardBox);
           }
       }catch (IOException exception) {
           exception.printStackTrace();
       }
    }

    private List<Produto> recentlyAdded() {
        List<Produto> list = new ArrayList<>();
        Produto produto = new Produto();
        produto.setNome("Sherek");
        produto.setCaminhoImagem("image/Shrek(personagem).jpg");
        produto.setPreco("5,99");
        list.add(produto);
        return list;
    }
}
