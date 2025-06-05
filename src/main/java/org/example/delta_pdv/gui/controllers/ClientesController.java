package org.example.delta_pdv.gui.controllers;

import com.almasb.fxgl.multiplayer.PropertyUpdateReplicationEvent;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import org.example.delta_pdv.entities.Cliente;
import org.example.delta_pdv.entities.Cliente;
import org.example.delta_pdv.gui.utils.Alerts;
import org.example.delta_pdv.gui.utils.ScreenLoader;
import org.example.delta_pdv.gui.utils.UpdateTableListener;
import org.example.delta_pdv.service.ClienteService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientesController implements Initializable, UpdateTableListener {

    @FXML
    private TableColumn<Cliente, String> CPFClienteColumn;

    @FXML
    private TableColumn<Cliente, String> EmailClienteColumn1;

    @FXML
    private TableColumn<Cliente, String> TelefoneClienteColumn1;

    @FXML
    private TableColumn<Cliente, Long> idClienteColumn;

    @FXML
    private TableColumn<Cliente, String> nomeClienteColumn;

    @FXML
    private TableView<Cliente> tabelaClientes;

    private final ClienteService clienteService = new ClienteService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTableClienteView();
        loadCelulaAcoes();
    }

    @FXML
    void btnAddClienteOnAction(ActionEvent event) {

    }

    //Olhar EstoqueController e ProdutoCadastroController

    private void loadTableClienteView(){
        CPFClienteColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        idClienteColumn.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        EmailClienteColumn1.setCellValueFactory(new PropertyValueFactory<>("email"));
        TelefoneClienteColumn1.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        nomeClienteColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));

        List<Cliente> listaCliente = clienteService.findAll();

        tabelaClientes.setItems(FXCollections.observableList(listaCliente));

    }
    public void loadCelulaAcoes() {
        TableColumn<Cliente, Void> colunaAcoes = new TableColumn<>("Ações");
        colunaAcoes.setCellFactory(coluna -> criarCelulaDeAcoes());
        tabelaClientes.getColumns().add(colunaAcoes);
    }

    private TableCell<Cliente, Void> criarCelulaDeAcoes() {
        return new TableCell<>() {
            private final Button btnEditar = new Button();
            private final Button btnRemover = new Button();
            private final HBox hBox = new HBox(btnEditar, btnRemover);

            {
                hBox.setSpacing(10);
                hBox.setAlignment(Pos.CENTER);

                // Ícone Editar
                ImageView iconEditar = carregarIcone("/org/example/delta_pdv/icons/editing.png");
                iconEditar.setFitWidth(16);
                iconEditar.setFitHeight(16);
                btnEditar.setGraphic(iconEditar);
                btnEditar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                btnEditar.setFont(Font.font("Arial", 12));
                btnEditar.setText(""); // Remove texto

                // Ícone Remover
                ImageView iconRemover = carregarIcone("/org/example/delta_pdv/icons/trash.png");
                iconRemover.setFitWidth(16);
                iconRemover.setFitHeight(16);
                btnRemover.setGraphic(iconRemover);
                btnRemover.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                btnRemover.setFont(Font.font("Arial", 12));
                btnRemover.setText("");

                btnEditar.setOnAction(event -> {
                    Cliente cliente = getTableView().getItems().get(getIndex());
                    editarCliente(cliente);
                });

                btnRemover.setOnAction(event -> {
                    Cliente cliente = getTableView().getItems().get(getIndex());
                    removerCliente(cliente);
                    reloadTable();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hBox);
            }
        };
    }

    private void editarCliente(Cliente cliente) {
        System.out.println("Editar Cliente: " + cliente.getNome());
        Cliente clienteSelecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado == null) {
            Alerts.showAlert("ERRO!", " ", "Selecione o Cliente antes de editar", Alert.AlertType.INFORMATION);
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/delta_pdv/clienteCadastro.fxml"));
            Parent root = loader.load();
            ClienteCadastroController clienteCadastroController = loader.getController();
            clienteCadastroController.setUpdateCliente(cliente);
            clienteCadastroController.setUpdateTableListener(this);
            ScreenLoader.loadForm(root);
        }catch (IOException exception) {
            throw new RuntimeException("Erro ao carregar a tela de cadastro!!: ", exception);
        }
    }

    private void removerCliente(Cliente cliente) {
        try {
            clienteService.delete(cliente.getIdCliente());
            Optional<ButtonType> choice = Alerts.showAlertYesNo("AVISO!", " ", "Deseja Realmente deletar Cliente ?", Alert.AlertType.WARNING);
            if (choice.isPresent() && choice.get() == ButtonType.YES) {
                clienteService.delete(cliente.getIdCliente());
                Alerts.showAlert("Sucesso!!", " ", "Cliente deletado com sucesso!", Alert.AlertType.CONFIRMATION);
                reloadTable();
            }
        } catch (Exception exception) {
            System.out.println("Erro ao deletar " + exception.getMessage());
            Alerts.showAlert("Erro", " ", "Erro ao deletar o Cliente! "+ exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private ImageView carregarIcone(String caminho) {
        try {
            Image img = new Image(getClass().getResourceAsStream(caminho));
            ImageView view = new ImageView(img);
            view.setFitWidth(16);
            view.setFitHeight(16);
            return view;
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + caminho + " - " + e.getMessage());
            return new ImageView(); // retorna vazio para não quebrar layout
        }
    }
    
    public void reloadTable(){ loadTableClienteView();}
}
