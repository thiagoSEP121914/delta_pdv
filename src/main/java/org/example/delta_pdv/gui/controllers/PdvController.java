package org.example.delta_pdv.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.gui.utils.ProdutoSearchListener;
import org.example.delta_pdv.service.ProdutoService;

import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.text.NumberFormat;
import java.util.*;

public class PdvController implements Initializable, ProdutoSearchListener {

    @FXML
    private FlowPane cardLayout;

    @FXML
    private HBox categoriaHbox;

    @FXML
    private TableView<Produto> TabelaVenda;

    private List<Produto> recentlyAdded;

    @FXML
    private VBox itensVendaBox;

    @FXML
    private Label labelSubTotal;

    private List<Produto> produtosSelecionados = new ArrayList<>();

    private ProdutoService produtoService = new ProdutoService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarCategorias();
        carregarProdutosSelecionados();
    }

    public void carregarProdutoNaTela() {
        itensVendaBox.getChildren().clear();
        carregarProdutosSelecionados();
    }

    private void carregarProdutosSelecionados() {
        try {
            for (Produto produto : produtosSelecionados) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/org/example/delta_pdv/produtoItemVenda.fxml"));
                VBox itemBox = fxmlLoader.load();
                ProdutoItemVendaController itemController = fxmlLoader.getController();
                itemController.setData(produto);
                itemController.setPdvController(this);
                itensVendaBox.getChildren().add(itemBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarProdutos() {
        cardLayout.getChildren().clear();

        recentlyAdded = recentlyAdded();
        try {


            for (int i = 0; i < recentlyAdded.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/org/example/delta_pdv/produtoCard.fxml"));
                HBox cardBox = fxmlLoader.load();
                ProdutoCardController cardController = fxmlLoader.getController();
                cardController.setData(recentlyAdded.get(i));
                cardController.setPdvController(this);
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

    public void addProdutosSelecionado (Produto produto) {
            produtosSelecionados.add(produto);
            carregarProdutoNaTela();
    }

    public void deleteProdutosSelecionados (Produto produto) {
        produtosSelecionados.remove(produto);
        carregarProdutoNaTela();
    }

    private void atualizarSubTotal() {
        double subTotal = 0;
        int qtd = 0;
        for (Produto produto: produtosSelecionados) {
            qtd++;
            subTotal += produto.getPrecoUnitario() * qtd;
        }

        NumberFormat formater = NumberFormat.getCurrencyInstance(new Locale("pt-br", "BR"));
        labelSubTotal.setText(String.valueOf(formater.format(subTotal)));

    }

    @Override
    public Optional<Produto> onBuscarProduto(String nomeBusca) {
        cardLayout.getChildren().clear();

        List<Produto> produtosFiltrados = produtoService.findByName(nomeBusca);

        try {
            for (Produto produto : produtosFiltrados) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/org/example/delta_pdv/produtoCard.fxml"));
                HBox cardBox = fxmlLoader.load();
                ProdutoCardController cardController = fxmlLoader.getController();
                cardController.setData(produto);
                cardController.setPdvController(this);
                cardLayout.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Retorna o primeiro produto encontrado (ou vazio se nenhum)
        return produtosFiltrados.stream().findFirst();
    }
}