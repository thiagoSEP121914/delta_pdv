package org.example.delta_pdv.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.gui.utils.Alerts;
import org.example.delta_pdv.gui.utils.ScreenLoader;
import org.example.delta_pdv.gui.utils.UpdateClienteListener;
import org.example.delta_pdv.service.ClienteService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientesController implements Initializable, UpdateClienteListener {

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
        loadCelulaAcoes();
        loadTableClienteView();

    }

    @FXML
    void btnAddClienteOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/delta_pdv/clienteCadastro.fxml"));
            Parent root = loader.load();
            ClienteCadastroController clienteCadastroController = loader.getController();
            clienteCadastroController.setUpdateClienteListener(this);
            ScreenLoader.loadForm(root);
        }catch(Exception e){
            Alerts.showAlert("Erro", " ", "Erro ao carregar a tela", Alert.AlertType.ERROR);
            throw new RuntimeException("Erro ao carregar a tela: " + e.getMessage());
        }
    }

    private void editarCliente(Cliente cliente) {
        if (cliente == null) {
            Alerts.showAlert("ERRO!", "", "Cliente inválido para edição.", Alert.AlertType.INFORMATION);
            return;
        }

        System.out.println("Editar Cliente: " + cliente.getNome());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/delta_pdv/clienteCadastro.fxml"));
            Parent root = loader.load();

            ClienteCadastroController clienteCadastroController = loader.getController();
            clienteCadastroController.setUpdateClienteListener(this);
            clienteCadastroController.setUpdateCliente(cliente);

            ScreenLoader.loadForm(root);
        } catch (IOException exception) {
            exception.printStackTrace();
            Alerts.showAlert("Erro", "", "Erro ao carregar a tela de cadastro!", Alert.AlertType.ERROR);
        }
    }

    private void removerCliente(Cliente cliente) {
        try {
            Optional<ButtonType> choice = Alerts.showAlertYesNo("AVISO!", " ", "Deseja Realmente deletar Cliente ?", Alert.AlertType.WARNING);
            if (choice.isPresent() && choice.get().getButtonData() == ButtonBar.ButtonData.YES) {
                clienteService.delete(cliente.getIdCliente());
                Alerts.showAlert("Sucesso!!", " ", "Cliente deletado com sucesso!", Alert.AlertType.CONFIRMATION);
                loadtable();
            }
        } catch (Exception exception) {
            System.out.println("Erro ao deletar " + exception.getMessage());
            Alerts.showAlert("Erro", " ", "Erro ao deletar o Cliente! "+ exception.getMessage(), Alert.AlertType.ERROR);
        }
    }


    //Olhar EstoqueController e ProdutoCadastroController

    private void loadTableClienteView() {
        CPFClienteColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        idClienteColumn.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        EmailClienteColumn1.setCellValueFactory(new PropertyValueFactory<>("email"));
        TelefoneClienteColumn1.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        nomeClienteColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        List<Cliente> listaCliente = clienteService.findAll();
        tabelaClientes.setItems(FXCollections.observableArrayList(listaCliente));
        tabelaClientes.refresh();//essa porrinha aqui aparentemente que estava quebrando
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
                    loadtable();
                });

                btnRemover.setOnAction(event -> {
                    Cliente cliente = getTableView().getItems().get(getIndex());
                    removerCliente(cliente);
                    loadtable();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hBox);
            }
        };
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

    public void loadtable() {
        loadTableClienteView();
    }
}
