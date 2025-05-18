package org.example.delta_pdv.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @FXML
    private StackPane contentArea; // A área onde o conteúdo será carregado

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPage("/org/example/delta_pdv/dashboard.fxml");
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
    @FXML
    public void onDashBoardMouseClicked(){
        loadPage("/org/example/delta_pdv/dashboard.fxml");
    }

    @FXML
   public void onPdvMouseClicked(){
        loadPage("/org/example/delta_pdv/pdv.fxml");
    }

    @FXML
    public void onFinancasMouseClicked(){
        loadPage("/org/example/delta_pdv/financas.fxml");
    }

    @FXML
    public void onEstoqueMouseClicked () {
        loadPage("/org/example/delta_pdv/estoque.fxml");
    }


    @FXML
    public void onVendasClicked () {
        loadPage("/org/example/delta_pdv/vendas.fxml");
    }

}