package org.example.delta_pdv.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import org.example.delta_pdv.entities.Produto;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PdvController implements Initializable {

    @FXML
    private FlowPane cardLayout;

    @FXML
    private HBox categoriaHbox;

    @FXML
    private TableView<Produto> TabelaVenda;

    private List<Produto> recentlyAdded;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarProdutos();
        carregarCategorias();
    }


    private void carregarProdutos() {
        recentlyAdded = recentlyAdded();
        try {
            for (int i = 0; i < recentlyAdded.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/org/example/delta_pdv/produtoCard.fxml"));
                HBox cardBox = fxmlLoader.load();
                ProdutoCardController cardController = fxmlLoader.getController();
                cardController.setData(recentlyAdded.get(i));
                cardLayout.getChildren().add(cardBox);
            }
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void carregarCategorias() {
        List<String> categorias = List.of("Bebidas", "Comidas", "Higiene");
        try {
            for (String nomeCategoria : categorias) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/org/example/delta_pdv/categoryCard.fxml"));
                HBox categoriaBox = fxmlLoader.load();
                CategoriaCardController categoriaCardController = fxmlLoader.getController();
                categoriaCardController.setData(nomeCategoria);
                categoriaHbox.getChildren().add(categoriaBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Produto> recentlyAdded() {
        List<Produto> list = new ArrayList<>();
        Produto produto = new Produto();
        produto.setNome("Sherek");
        produto.setCaminhoImagem("/org/example/delta_pdv/image/shureki.jpg");
        produto.setPrecoUnitario(4.50);

        Produto coca = new Produto();
        coca.setNome("cokinha");
        coca.setCaminhoImagem("/org/example/delta_pdv/image/coca.jpg");
        coca.setPrecoUnitario(4.20);

        Produto arroz = new Produto();
        arroz.setNome("Arroz");
        arroz.setCaminhoImagem("/org/example/delta_pdv/image/coca.jpg");
        arroz.setPrecoUnitario(35.97);

        Produto cachimbo = new Produto();
        cachimbo.setNome("Cachimbo de crack");
        cachimbo.setCaminhoImagem("/org/example/delta_pdv/image/cachimbo.jpg");

        list.add(produto);
        list.add(coca);
        list.add(arroz);
        list.add(cachimbo);
        return list;
    }
}