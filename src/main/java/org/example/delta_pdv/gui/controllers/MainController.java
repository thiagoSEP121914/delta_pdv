package org.example.delta_pdv.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import kotlin.jvm.internal.markers.KMutableIterable;
import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.entities.Usuario;
import org.example.delta_pdv.gui.utils.ProdutoSearchListener;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @FXML
    private StackPane contentArea; // A área onde o conteúdo será carregado

    private ProdutoSearchListener produtoSearchListener;

    @FXML
    private TextField searchBar;

    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        loadDefaultPage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        searchBar.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                resetarBuscar();
            }
        });
    }

    @FXML
    private void onSearchKeyTyped() {
        if (produtoSearchListener != null) {
            String termo = searchBar.getText();
            produtoSearchListener.onBuscarProduto(termo);
            // NÃO limpar aqui!
        }
    }

    @FXML
    private void onBuscarButtonMouseReleased() {
        if (produtoSearchListener != null) {
            String termo = searchBar.getText();
            Optional<Produto> find = produtoSearchListener.onBuscarProduto(termo);
            if (find.isPresent()) {
                searchBar.clear();  // Limpar só se encontrou o produto
            }
        }
    }

    @FXML
    public void onDashBoardMouseClicked(){
        loadPageIfAdmin("/org/example/delta_pdv/dashboard.fxml");
    }

    @FXML
    public void onPdvMouseClicked(){

        Object controller = loadPageWithController("/org/example/delta_pdv/pdv.fxml");
        if (controller instanceof ProdutoSearchListener) {
            this.produtoSearchListener = (ProdutoSearchListener) controller;
        }
    }

    @FXML
    public void onEstoqueMouseClicked () {
        loadPageIfAdmin("/org/example/delta_pdv/estoque.fxml");
    }


    @FXML
    public void onVendasClicked () {
        loadPageIfAdmin("/org/example/delta_pdv/vendas.fxml");
    }

    @FXML
    public void OnClienteMouseClicked(){
        loadPageIfAdmin("/org/example/delta_pdv/clientes.fxml");
    }

    @FXML
    private void OnMouseClickedUsuarios(){
        loadPageIfAdmin("/org/example/delta_pdv/usuarios.fxml");
    ;}

    @FXML
    private void OnMouseClickedConfiguracoes(){
        loadPageIfAdmin("/org/example/delta_pdv/configuracoes.fxml");
    }

    @FXML
    private void OnMouseClickedLogin(){loadPage("/org/example/delta_pdv/login.fxml");}

    @FXML
    private void OnMouseClickedSair() {
        Platform.exit();
    }

    // Método genérico para carregar qualquer página no StackPane
    private void loadPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            contentArea.getChildren().setAll(root); // Substitui o conteúdo da área central
        } catch (IOException  exception ) {
            throw new RuntimeException("Erro ao  carregar a tela", exception);
        }
    }
    private <T> T loadPageWithController(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            T controller = loader.getController();
            contentArea.getChildren().setAll(root);
            return controller;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar: " + fxmlPath, e);
        }
    }

    private void resetarBuscar() {
        if (produtoSearchListener != null) {
            produtoSearchListener.onBuscarProduto("");
            searchBar.clear();
        }
    }

    private void loadPageIfAdmin(String fxml) {
        if (!usuario.getTipo().equalsIgnoreCase("administrador")) {
            loadPage("/org/example/delta_pdv/acessoBloqueado.fxml");
            return;
        }
        loadPage(fxml);
    }

    private void loadDefaultPage() {
        loadPageIfAdmin("/org/example/delta_pdv/dashboard.fxml");
    }


}