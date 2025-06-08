package org.example.delta_pdv.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.delta_pdv.entities.Usuario;
import org.example.delta_pdv.gui.utils.Alerts;
import org.example.delta_pdv.gui.utils.ScreenLoader;
import org.example.delta_pdv.gui.utils.UpdateTableListener;
import org.example.delta_pdv.service.UsuarioService;

import java.io.IOException;

public class LoginController implements UpdateTableListener{

    @FXML
    private PasswordField lblSenha;

    @FXML
    private TextField lblUsuario;


    @FXML
    private Button btnLogin;

    private  UsuarioService usuarioService = new UsuarioService();

    @FXML
   private void onBtnLoginAction() {
        String email = lblUsuario.getText();
        String senha = lblSenha.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            Alerts.showAlert("Aviso!", "", "preencha todos os campos ", Alert.AlertType.INFORMATION);
            clearFields();
            return;
        }
        Usuario usuario = usuarioService.validarLogin(email, senha);

        if (usuario == null) {
            Alerts.showAlert("Erro", " ", "Usuario ou senha incorreto", Alert.AlertType.WARNING);
            clearFields();
            return;
        }
        loadMainController(usuario);
    }

    @FXML
    private void btnRegistrarOnAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/delta_pdv/usuarioCadastro.fxml"));
            Parent root = loader.load();
            UsuarioCadastroController usuarioCadastroController = loader.getController();
            usuarioCadastroController.setUpdateTableListener(this);
            ScreenLoader.loadForm(root);
        }catch(Exception e){
            Alerts.showAlert("Erro", " ", "Erro ao carregar a tela", Alert.AlertType.ERROR);
            throw new RuntimeException("Erro ao carregar a tela: " + e.getMessage());
        }

    }

    private void loadMainController(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/delta_pdv/main.fxml"));
            Parent root = loader.load();
            MainController mainController = loader.getController();
            mainController.setUsuario(usuario);
            ScreenLoader.loadForm(root, true);
            Stage stage = (Stage) lblUsuario.getScene().getWindow();
            stage.close();
        } catch (IOException exception) {
            Alerts.showAlert("ERRO", " ", "Erro ao carregar a tela", Alert.AlertType.ERROR);
            exception.printStackTrace();
            throw new RuntimeException("Erro absurdo!!!" +exception.getMessage());
        }
    }

    private void clearFields() {
        lblUsuario.clear();
        lblSenha.clear();
    }

    @Override
    public void reloadTable() {

    }
}
