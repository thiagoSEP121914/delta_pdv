package org.example.delta_pdv.gui.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.delta_pdv.entities.Categoria;
import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.service.CategoriaService;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProdutoCadastroController implements Initializable {

    @FXML
    private ComboBox<Categoria> categoriaComboBox;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField txtCodProduto;

    @FXML
    private TextField txtCusto;

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtLucro;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPrecoUnitario;

    @FXML
    private TextField txtQuantidadeEstoque;

    private CategoriaService service = new CategoriaService();
    private File imagemSelecionada;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCategoriaComboBox();
        setImagemPadrao();
    }

    @FXML
    private void btnSalvarOnAction() {
        Produto produto = instantiateProduto();
        System.out.println(produto);
    }

    @FXML
    private void btnLimparOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void btnBuscarImagemOnAction(ActionEvent event) {
        buscarImagem();
    }

    @FXML
   private void onLucroMouseCliked() {
        atualizarCampoLucro();
    }

    private void buscarImagem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.JPEG", "*.jpeg")
        );

        Stage stage = (Stage) imageView.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            imagemSelecionada = file;
            Image imagem = new Image(file.toURI().toString());
            imageView.setImage(imagem);
        }
    }

    private void loadCategoriaComboBox() {
        categoriaComboBox.setItems(FXCollections.observableArrayList(service.findAll()));
        categoriaComboBox.setConverter(new StringConverter<Categoria>() {
            @Override
            public String toString(Categoria categoria) {
                return categoria != null ? categoria.getNome() : "";
            }

            @Override
            public Categoria fromString(String nome) {
                if (nome == null || nome.isEmpty()) return null;
                for (Categoria c : categoriaComboBox.getItems()) {
                    if (c.getNome().equals(nome)) {
                        return c;
                    }
                }
                return null; // Não usado
            }
        });
    }

    private Produto instantiateProduto() {
        String caminhoImagem = imagemSelecionada != null ? imagemSelecionada.toURI().toString() : "";

        Categoria categoriaSelecionada = categoriaComboBox.getSelectionModel().getSelectedItem();
        if (categoriaSelecionada == null) {
            throw new IllegalArgumentException("Categoria não selecionada.");
        }

        return new Produto(
                txtNome.getText(),
                caminhoImagem,
                txtDescricao.getText(),
                Double.parseDouble(txtPrecoUnitario.getText()),
                Double.parseDouble(txtCusto.getText()),
                Integer.parseInt(txtQuantidadeEstoque.getText()),
                categoriaSelecionada
        );
    }

    private void atualizarCampoLucro() {
        double precoUnitario = Double.parseDouble(txtPrecoUnitario.getText());
        double custo = Double.parseDouble(txtCusto.getText());
        Produto produtoTemp = new Produto(precoUnitario, custo);
        txtLucro.setText(String.format("%.2f", produtoTemp.getLucro()));
    }

    private void clearFields() {
        txtNome.clear();
        txtDescricao.clear();
        txtPrecoUnitario.clear();
        txtCusto.clear();
        txtQuantidadeEstoque.clear();
        txtLucro.clear();
        categoriaComboBox.getSelectionModel().clearSelection();
        imagemSelecionada = null;
        setImagemPadrao();
    }

    private void setImagemPadrao() {
        String caminhoImagem = "/org/example/delta_pdv/icons/user.png";
        Image imagem = new Image(getClass().getResource(caminhoImagem).toExternalForm());
        imageView.setImage(imagem);
    }
}
