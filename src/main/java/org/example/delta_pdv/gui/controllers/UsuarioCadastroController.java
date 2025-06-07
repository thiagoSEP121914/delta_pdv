package org.example.delta_pdv.gui.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.delta_pdv.entities.Usuario;
import org.example.delta_pdv.gui.utils.Alerts;
import org.example.delta_pdv.gui.utils.UpdateTableListener;
import org.example.delta_pdv.service.UsuarioService;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UsuarioCadastroController implements Initializable {

    @FXML
    private Button btnSalvar;

    @FXML
    private ChoiceBox<String> chcTipoUsuario; //Mudar pra Enum se quiser sim mano

    @FXML
    private PasswordField psdSenhaUsuario;

    @FXML
    private TextField txtEmailUsuario;

    @FXML
    private TextField txtIdUsuario;

    @FXML
    private TextField txtNomeUsuario;

    private UpdateTableListener updateTableListener;
    private Usuario usuario;
    private UsuarioService usuarioService = new UsuarioService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setUpdateTableListener(UpdateTableListener updateTableListener) {
        this.updateTableListener = updateTableListener;
    }

    public void setUpdateUsuario(Usuario usuario){
        this.usuario = usuario;
        fillFormsWithUsuario();
    }


    @FXML
    void btnLimparOnAction(ActionEvent event) {

    }

    @FXML
    void btnSalvarOnAction(ActionEvent event) {

    }

    private void fillFormsWithUsuario() {
        if (usuario == null) return;

        txtNomeUsuario.setText(usuario.getNome());
        txtEmailUsuario.setText(usuario.getEmail());
        txtIdUsuario.setText(String.valueOf(usuario.getId_usuario()));
        psdSenhaUsuario.setText(usuario.getSenha());
        // Seleciona o Tipo na ChoiceBox
        if (chcTipoUsuario != null && usuario.getTipo() != null) {
            chcTipoUsuario.getSelectionModel().select(usuario.getTipo());
        }
    }

    private void loadChoiceBox() {
        List<String> tipo = Arrays.asList("Adiministrador", "Comum");
        chcTipoUsuario.setItems(FXCollections.observableArrayList(tipo));
    }

    private Usuario instantiateUsuario(){
        Usuario usuario = new Usuario();

        try {
            if (!txtIdUsuario.getText().trim().isEmpty()) {
                usuario.setId_usuario(Long.parseLong(txtIdUsuario.getText().trim()));
            }
        } catch (NumberFormatException e) {
            // Exiba uma mensagem de erro amigável
            Alerts.showAlert("Erro", null, "ID inválido! Verifique os dados inseridos.", Alert.AlertType.ERROR);
        }

        usuario.setNome(txtNomeUsuario.getText());
        usuario.setEmail(txtEmailUsuario.getText());
        usuario.setSenha(psdSenhaUsuario.getText());
        usuario.setTipo(chcTipoUsuario.getValue());
        usuario.setId_usuario(Long.parseLong(txtIdUsuario.getText()));

        return usuario;
    }

    public void clearFields(){
        txtNomeUsuario.clear();
        txtEmailUsuario.clear();
        txtIdUsuario.clear();
        psdSenhaUsuario.clear();
        chcTipoUsuario.setItems(null);
    }
}
