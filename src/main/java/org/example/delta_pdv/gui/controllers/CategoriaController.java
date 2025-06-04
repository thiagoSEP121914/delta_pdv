package org.example.delta_pdv.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.delta_pdv.gui.utils.Alerts;
import org.example.delta_pdv.gui.utils.CategoriaUpdateListener;
import org.example.delta_pdv.gui.utils.ScreenLoader;
import org.example.delta_pdv.gui.utils.UpdateTableListener;
import org.example.delta_pdv.service.CategoriaService;

public class CategoriaController {

    private CategoriaUpdateListener updateListener;
    private CategoriaService categoriaService = new CategoriaService();

    public void setCategoriaUpdateListener(CategoriaUpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    @FXML
    private TextField txtNomeCategoria;


    @FXML
    private void btnSalvarOnAction() {
        try {
            String nome = txtNomeCategoria.getText();
            if (nome == null || nome.trim().isEmpty()) {
                Alerts.showAlert("Erro", "Nome inválido", "Por favor, insira um nome válido para a categoria.", Alert.AlertType.WARNING);
                return;
            }
            categoriaService.insert(nome.trim());
            Alerts.showAlert("Sucesso", null, "Categoria salva com sucesso!", Alert.AlertType.INFORMATION);
            updateListener.onCategoriaUpdate();
            txtNomeCategoria.clear();
        } catch (Exception e) {
            Alerts.showAlert("Erro", "Falha ao salvar", "Erro ao salvar categoria: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnLimparOnAction() {
        try {
            String nome = txtNomeCategoria.getText();
            if (nome == null || nome.trim().isEmpty()) {
                Alerts.showAlert("Erro", "Nome inválido", "Por favor, insira um nome válido para excluir.", Alert.AlertType.WARNING);
                return;
            }
            categoriaService.delete(nome.trim());
            Alerts.showAlert("Sucesso", null, "Categoria excluída com sucesso!", Alert.AlertType.INFORMATION);
            updateListener.onCategoriaUpdate();
            txtNomeCategoria.clear();
        } catch (Exception e) {
            Alerts.showAlert("Erro", "Falha ao excluir", "Erro ao excluir categoria: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
