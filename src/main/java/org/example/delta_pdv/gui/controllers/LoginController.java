package org.example.delta_pdv.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.delta_pdv.entities.Usuario;
import org.example.delta_pdv.gui.utils.Alerts;
import org.example.delta_pdv.gui.utils.ScreenLoader;
import org.example.delta_pdv.service.UsuarioService;

public class LoginController {

    @FXML
    private PasswordField lblSenha;

    @FXML
    private TextField lblUsuario;


    @FXML
    private Button btnLogin;

    private  UsuarioService usuarioService = new UsuarioService();

    @FXML
    void onBtnLoginAction() {
        String email = lblUsuario.getText();
        String senha = lblSenha.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            Alerts.showAlert("Aviso!", "", "prencha todos os campos", Alert.AlertType.INFORMATION);
            return;
        }

        if (!usuarioService.validarLogin(email, senha)) {
            Alerts.showAlert("Erro", " ", "Usuario ou senha incorreto", Alert.AlertType.WARNING);
            return;
        }
        ScreenLoader.loadForm("/org/example/delta_pdv/main.fxml");
        Stage stage = (Stage) lblUsuario.getScene().getWindow();
        stage.close();
    }


}
