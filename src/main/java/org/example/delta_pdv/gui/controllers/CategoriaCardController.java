package org.example.delta_pdv.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.example.delta_pdv.service.CategoriaService;

public class CategoriaCardController {

    @FXML
    private Label categoryName;

    @FXML
    private HBox box;

    private PdvController pdvController;

    private String nomeCategoria; // <- Adicione isto

    public void setPdvController(PdvController pdvController) {
        this.pdvController = pdvController;
    }

    public void setData(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria; // Salva para uso posterior
        categoryName.setText(nomeCategoria);
    }

    @FXML
    void onCategoriaClicked(MouseEvent event) {
        System.out.println("Botao clicade!!!!!");
        if (pdvController != null && nomeCategoria != null) {
            pdvController.carregarProdutosPorCategoria(nomeCategoria);
        }
    }
}
