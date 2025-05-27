package org.example.delta_pdv.gui.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import org.example.delta_pdv.entities.ProdutoExemplo;
import org.example.delta_pdv.gui.utils.ScreenLoader;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EstoqueController implements Initializable {

    @FXML
    private TableColumn<ProdutoExemplo, Integer> idColumn;

    @FXML
    private TableColumn<ProdutoExemplo, String> nomeColumn;

    @FXML
    private TableColumn<ProdutoExemplo, Void> acoesColumn;

    @FXML
    private TableView<ProdutoExemplo> tabelaProdutos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTableView();
    }

    private void loadTableView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        acoesColumn.setCellFactory(coluna -> criarCelulaDeAcoes());
        List<ProdutoExemplo> produtoExemplos = List.of(new ProdutoExemplo(1, "Coca-cola"), new ProdutoExemplo(2, "Cocaina"));
        tabelaProdutos.setItems(FXCollections.observableArrayList(produtoExemplos));
    }

    private TableCell<ProdutoExemplo, Void> criarCelulaDeAcoes() {
        return new TableCell<>() {
            private final Button btnEditar = new Button();
            private final Button btnRemover = new Button();
            private final HBox hBox = new HBox(btnEditar, btnRemover);

            {
                hBox.setSpacing(10);
                hBox.setAlignment(Pos.CENTER);

                // Ícone Editar
                ImageView iconEditar = carregarIcone("/org/example/delta_pdv/icons/editing.png");
                iconEditar.setFitWidth(16);
                iconEditar.setFitHeight(16);
                btnEditar.setGraphic(iconEditar);
                btnEditar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                btnEditar.setFont(Font.font("Arial", 12));
                btnEditar.setText(""); // Remove texto

                // Ícone Remover
                ImageView iconRemover = carregarIcone("/org/example/delta_pdv/icons/trash.png");
                iconRemover.setFitWidth(16);
                iconRemover.setFitHeight(16);
                btnRemover.setGraphic(iconRemover);
                btnRemover.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                btnRemover.setFont(Font.font("Arial", 12));
                btnRemover.setText("");

                btnEditar.setOnAction(event -> {
                    ProdutoExemplo produto = getTableView().getItems().get(getIndex());
                    editarProduto(produto);
                });

                btnRemover.setOnAction(event -> {
                    ProdutoExemplo produto = getTableView().getItems().get(getIndex());
                    removerProduto(produto);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hBox);
            }
        };
    }

    @FXML
    void btnAdcionarOnAction() {
        System.out.println("BOTAO DE ADICONAR CLICADO!!!");
        ScreenLoader.loadForm("/org/example/delta_pdv/produtoCadastro.fxml", null);

    }



        private void editarProduto(ProdutoExemplo produto) {
        System.out.println("Editar produto: " + produto.getNome());
    }

    private void removerProduto(ProdutoExemplo produto) {
        tabelaProdutos.getItems().remove(produto);
        System.out.println("Removido: " + produto.getNome());
    }

    private ImageView carregarIcone(String caminho) {
        try {
            Image img = new Image(getClass().getResourceAsStream(caminho));
            ImageView view = new ImageView(img);
            view.setFitWidth(16);
            view.setFitHeight(16);
            return view;
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + caminho + " - " + e.getMessage());
            return new ImageView(); // retorna vazio para não quebrar layout
        }
    }
}
