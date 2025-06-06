package org.example.delta_pdv.gui.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import org.example.delta_pdv.entities.Cliente;
import org.example.delta_pdv.gui.utils.Alerts;
import org.example.delta_pdv.gui.utils.UpdateTableListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.delta_pdv.service.ClienteService;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClienteCadastroController implements Initializable {

    @FXML
    private Button btnSalvar;

    @FXML
    private TextField txtCpfCliente;

    @FXML
    private TextField txtEmailCliente;

    @FXML
    private TextField txtIdCliente;

    @FXML
    private TextField txtNomeCliente;

    @FXML
    private TextField txtTelefoneCliente;

    private UpdateTableListener updateTableListener;

    private Cliente cliente;

    private ClienteService clienteService = new ClienteService();

    public void setUpdateTableListener(UpdateTableListener updateTableListener) {
        this.updateTableListener = updateTableListener;
        fillFormWithCliente();
    }

    public void setUpdateCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void btnLimparOnAction(ActionEvent event) {
        txtIdCliente.clear();
        txtNomeCliente.clear();
        txtEmailCliente.clear();
        txtTelefoneCliente.clear();
        txtCpfCliente.clear();
    }


    @FXML
    public void btnSalvarOnAction(ActionEvent event) {
        btnSalvar.setDisable(true);

        try{
            clienteService.saveCliente(instantiateCliente());
            Alerts.showAlert("Sucesso", null, "CLIENTE SALVO COM SUCESSO !", Alert.AlertType.INFORMATION);
            updateTableListener.reloadTable();
        } catch (Exception e) {
            e.printStackTrace(); // ajuda para debug
            Alerts.showAlert("Erro ao salvar", null, e.getMessage(), Alert.AlertType.ERROR);
        } finally{
            clearFields();
            btnSalvar.setDisable(false);
        }
    }

    private Cliente instantiateCliente(){
        Cliente clienteNovo = new Cliente();

        try {
            if (!txtIdCliente.getText().trim().isEmpty()) {
                clienteNovo.setIdCliente(Long.parseLong(txtIdCliente.getText().trim()));
            }
        } catch (NumberFormatException e) {
            // Exiba uma mensagem de erro amigável
            Alerts.showAlert("Erro", null, "ID inválido! Verifique os dados inseridos.", Alert.AlertType.ERROR);
        }

        clienteNovo.setNome(txtNomeCliente.getText());
        clienteNovo.setEmail(txtEmailCliente.getText());
        clienteNovo.setCpf(txtCpfCliente.getText());
        clienteNovo.setTelefone(txtTelefoneCliente.getText());

        return clienteNovo;
    }


    private void fillFormWithCliente() {
        if (cliente == null) return;

        // Preenche os campos do formulário
        txtCpfCliente.setText(cliente.getCpf());
        txtNomeCliente.setText(cliente.getNome());
        txtEmailCliente.setText(cliente.getEmail());
        txtTelefoneCliente.setText(cliente.getTelefone());
        txtIdCliente.setText(String.valueOf(cliente.getIdCliente()));
    }

    public void clearFields(){
        txtCpfCliente.clear();
        txtNomeCliente.clear();
        txtEmailCliente.clear();
        txtTelefoneCliente.clear();
        txtIdCliente.clear();
    }

}
