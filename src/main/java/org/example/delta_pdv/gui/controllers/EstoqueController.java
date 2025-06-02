package org.example.delta_pdv.gui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import org.example.delta_pdv.entities.Categoria;
import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.gui.utils.Alerts;
import org.example.delta_pdv.gui.utils.ScreenLoader;
import org.example.delta_pdv.gui.utils.UpdateTableListener;
import org.example.delta_pdv.service.CategoriaService;
import org.example.delta_pdv.service.ProdutoService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EstoqueController implements Initializable, UpdateTableListener {
    @FXML
    private TableColumn<Produto, Long> idColumn;

    @FXML
    private TableColumn<Produto, String> nomeColumn;

    @FXML
    private TableColumn<Produto, String> imagemColumn;

    @FXML
    private TableColumn<Produto, String> DescricaoColumn;

    @FXML
    private TableColumn<Produto, Double> PrecoVendaColumn;

    @FXML
    private TableColumn<Produto, Double> custoColumn;

    @FXML
    private TableColumn<Produto, Double> lucroColumn;

    @FXML
    private TableColumn<Produto, Integer> qtdEstoque;

    @FXML
    private TableColumn<Produto, String> categoriaColumn;


    @FXML
    private TableView<Produto> tabelaProdutos;
    private final CategoriaService categoriaService = new CategoriaService();
    private final ProdutoService produtoService = new ProdutoService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCelulaAcoes();
        loadTableView();
    }

    @FXML
    void btnAdcionarOnAction() {
        System.out.println("BOTAO DE ADICONAR CLICADO!!!");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/delta_pdv/produtoCadastro.fxml"));
            Parent root = loader.load();
            ProdutoCadastroController  produtoCadastroController = loader.getController();
            produtoCadastroController.setUpdateTableListener(this);
            ScreenLoader.loadForm(root);
        }catch (Exception exception) {
            Alerts.showAlert("Erro", " ", "Erro ao carregar a tela", Alert.AlertType.ERROR);
            throw new RuntimeException("Erro ao carregar a tela" + exception.getMessage());
        }
    }

    private void editarProduto(Produto produto) {
        System.out.println("Editar produto: " + produto.getNome());
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (produtoSelecionado == null) {
            Alerts.showAlert("ERRO!", " ", "Selecione o produto antes de editar", Alert.AlertType.INFORMATION);
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/delta_pdv/produtoCadastro.fxml"));
            Parent root = loader.load();
            ProdutoCadastroController produtoCadastroController = loader.getController();
            produtoCadastroController.setUpdateProduto(produto);
            produtoCadastroController.setUpdateTableListener(this);
            ScreenLoader.loadForm(root);
        }catch (IOException exception) {
                throw new RuntimeException("Erro ao carregar a tela de cadastro!!: ", exception);
        }
    }

    private void removerProduto(Produto produto) {
        try {
            produtoService.delete(produto.getIdProduto());
            Optional<ButtonType> choice = Alerts.showAlertYesNo("AVISO!", " ", "Deseja Realmente deletar produto ?", Alert.AlertType.WARNING);
            if (choice.isPresent() && choice.get() == ButtonType.YES) {
                produtoService.delete(produto.getIdProduto());
                Alerts.showAlert("Sucesso!!", " ", "Produto deletado com sucesso!", Alert.AlertType.CONFIRMATION);
                reloadTable();
            }
        } catch (Exception exception) {
            Alerts.showAlert("Erro", " ", "Erro ao deletar o produto!", Alert.AlertType.ERROR);
        }
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



    private void loadTableView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        imagemColumn.setCellValueFactory(new PropertyValueFactory<>("caminhoImagem"));
        imagemColumn.setCellFactory(col -> carregandoImagemProduto());
        DescricaoColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        PrecoVendaColumn.setCellValueFactory(new PropertyValueFactory<>("precoUnitario"));
        custoColumn.setCellValueFactory(new PropertyValueFactory<>("custo"));

        lucroColumn.setCellValueFactory(new PropertyValueFactory<>("lucro"));
        lucroColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double lucro, boolean empty) {
                super.updateItem(lucro, empty);
                if (empty || lucro == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", lucro));
                }
            }
        });

        qtdEstoque.setCellValueFactory(new PropertyValueFactory<>("quantidadeEstoque"));

        categoriaColumn.setCellValueFactory(cellData -> {
            Categoria categoria = cellData.getValue().getCategoria();
            return new SimpleStringProperty(categoria != null ? categoria.getNome() : "");
        });

        List<Produto> listaDeprodutos = produtoService.findAll();
        tabelaProdutos.setItems(FXCollections.observableArrayList(listaDeprodutos));
    }

    private TableCell<Produto, String> carregandoImagemProduto() {
        return new TableCell<>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(String caminhoImagem, boolean empty) {
                super.updateItem(caminhoImagem, empty);

                if (empty || caminhoImagem == null || caminhoImagem.isEmpty()) {
                    setGraphic(null);
                } else {
                    try {
                        // Cria o caminho completo do arquivo da imagem
                        File arquivoImagem = new File( caminhoImagem);

                        if (!arquivoImagem.exists()) {
                            System.err.println("Arquivo de imagem não encontrado: " + arquivoImagem.getAbsolutePath());
                            setGraphic(null);
                            return;
                        }

                        Image imagem = new Image(arquivoImagem.toURI().toString());
                        imageView.setImage(imagem);
                        setGraphic(imageView);

                    } catch (Exception e) {
                        System.err.println("Erro ao carregar imagem: " + caminhoImagem);
                        e.printStackTrace();
                        setGraphic(null);
                    }
                }
            }
        };
    }

    public void loadCelulaAcoes() {
        TableColumn<Produto, Void> colunaAcoes = new TableColumn<>("Ações");
        colunaAcoes.setCellFactory(coluna -> criarCelulaDeAcoes());
        tabelaProdutos.getColumns().add(colunaAcoes);
    }

    private TableCell<Produto, Void> criarCelulaDeAcoes() {
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
                    Produto produto = getTableView().getItems().get(getIndex());
                    editarProduto(produto);
                });

                btnRemover.setOnAction(event -> {
                    Produto produto = getTableView().getItems().get(getIndex());
                    removerProduto(produto);
                    reloadTable();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hBox);
            }
        };
    }


    public void reloadTable() {
        loadTableView();
    }
}
