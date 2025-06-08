package org.example.delta_pdv.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.delta_pdv.entities.Cliente;
import org.example.delta_pdv.entities.Usuario;
import org.example.delta_pdv.gui.utils.Alerts;
import org.example.delta_pdv.service.UsuarioService;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfiguracoesController implements Initializable {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtNovaSenha;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btnSalvar;
    private Usuario usuario;
    private UsuarioService usuarioService = new UsuarioService();

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
        fillFormWithUsuario();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void onBtnSalvarAction() {
        btnSalvar.setDisable(true);
        try {
            usuarioService.saveUsuario(instantiateUsuario());
            Alerts.showAlert("Sucesso", null, "USUARIO SALVO COM SUCESSO !", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Aviso", "", "Erro ao salvar", Alert.AlertType.WARNING);
        } finally {
            btnSalvar.setDisable(false);
        }
    }

    @FXML
    void btnSalvarOnAction(ActionEvent event) {
        btnSalvar.setDisable(true);
        try {
            usuarioService.saveUsuario(instantiateUsuario());
            Alerts.showAlert("Sucesso", null, "USUARIO SALVO COM SUCESSO !", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Aviso", "", "Erro ao salvar", Alert.AlertType.WARNING);
        } finally {
            btnSalvar.setDisable(false);
        }
    }

    private Usuario instantiateUsuario() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String senhaAtual = txtSenha.getText();
        String novaSenha = txtNovaSenha.getText();

        if (nome == null || nome.isBlank() ||
                email == null || email.isBlank() ||
                senhaAtual == null || senhaAtual.isBlank() ||
                novaSenha == null || novaSenha.isBlank()) {

            Alerts.showAlert("Aviso", " ", "Os campos não podem estar vazios", Alert.AlertType.WARNING);
            return null;
        }

        Usuario usuario = new Usuario(this.usuario.getId_usuario(),nome, email, novaSenha, "ADMINISTRADOR");
        return usuario;
    }

    private void fillFormWithUsuario() {
        if (usuario == null) return;
        txtNome.setText(usuario.getNome());
        txtEmail.setText(usuario.getEmail());
        txtSenha.setText(usuario.getSenha());
        txtNovaSenha.setText("");
    }
}
