package org.example.delta_pdv.gui.controllers;

import javafx.collections.FXCollections;
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
import javafx.stage.FileChooser;
import org.example.delta_pdv.entities.Usuario;
import org.example.delta_pdv.gui.utils.Alerts;
import org.example.delta_pdv.gui.utils.ExcelExporter;
import org.example.delta_pdv.gui.utils.ScreenLoader;
import org.example.delta_pdv.gui.utils.UpdateTableListener;
import org.example.delta_pdv.service.UsuarioService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;

public class UsuariosController implements Initializable, UpdateTableListener {

    @FXML
    private TableColumn<Usuario, String> emailUsuarioColumn;

    @FXML
    private TableColumn<Usuario, Long> idUsuarioColumn;

    @FXML
    private TableColumn<Usuario, String> nomeUsuarioColumn;

    @FXML
    private TableColumn<Usuario, String> senhaUsuarioColumn;

    @FXML
    private TableView<Usuario> tabelaUsuarios;

    @FXML
    private TableColumn<Usuario, String> tipoUsuarioColumn; //alterar o tipo do dado no banco de dados
    
    private UsuarioService userService = new UsuarioService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCelulaAcoes();
        loadTableUsuarioView();
    }

    @FXML
    private void btnAddUsuarioOnAction() {
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

    @FXML
    void onBtnExportaAction() {
        exportToExcel();
    }

    private void editarUsuario(Usuario usuario) {
        if (usuario == null) {
            Alerts.showAlert("ERRO!", "", "Usuario inválido para edição.", Alert.AlertType.INFORMATION);
            return;
        }

        System.out.println("Editar Usuario: " + usuario.getNome());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/delta_pdv/UsuarioCadastro.fxml"));
            Parent root = loader.load();

            UsuarioCadastroController usuarioCadastroController = loader.getController();
            usuarioCadastroController.setUpdateTableListener(this);
            usuarioCadastroController.setUpdateUsuario(usuario);
            ScreenLoader.loadForm(root);
        } catch (IOException exception) {
            exception.printStackTrace();
            Alerts.showAlert("Erro", "", "Erro ao carregar a tela de cadastro!", Alert.AlertType.ERROR);
        }
    }

    private void removerUsuario(Usuario usuario) {
        try {
            Optional<ButtonType> choice = Alerts.showAlertYesNo("AVISO!", " ", "Deseja Realmente deletar Usuario ?", Alert.AlertType.WARNING);
            if (choice.isPresent() && choice.get().getButtonData() == ButtonBar.ButtonData.YES) {
                userService.delete(usuario.getId_usuario());
                Alerts.showAlert("Sucesso!!", " ", "Usuario deletado com sucesso!", Alert.AlertType.CONFIRMATION);
                reloadTable();
            }
        } catch (Exception exception) {
            System.out.println("Erro ao deletar " + exception.getMessage());
            Alerts.showAlert("Erro", " ", "Erro ao deletar o Usuario! "+ exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void loadTableUsuarioView() {
        idUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("Id_usuario"));
        nomeUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        emailUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        senhaUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("senha"));
        tipoUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        List<Usuario> listaUsuario = userService.findAll();
        tabelaUsuarios.setItems(FXCollections.observableList(listaUsuario));
        tabelaUsuarios.refresh();
    }


    public void loadCelulaAcoes() {
        TableColumn<Usuario, Void> colunaAcoes = new TableColumn<>("Ações");
        colunaAcoes.setCellFactory(coluna -> criarCelulaDeAcoes());
        tabelaUsuarios.getColumns().add(colunaAcoes);
    }

    private TableCell<Usuario, Void> criarCelulaDeAcoes() {
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
                    Usuario user = getTableView().getItems().get(getIndex());
                    editarUsuario(user);
                    reloadTable();
                });

                btnRemover.setOnAction(event -> {
                    Usuario user = getTableView().getItems().get(getIndex());
                    removerUsuario(user);
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
    private void exportToExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar arquivo Excel");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(tabelaUsuarios.getScene().getWindow());

        if (file != null) {
            TableView<Usuario> tabelaExport = new TableView<>();

            for (TableColumn<Usuario, ?> col : tabelaUsuarios.getColumns()) {
                String colName = col.getText().toLowerCase();
                if (!colName.equals("imagem") && !colName.equals("ações") && !colName.equals("acoes")) {
                    tabelaExport.getColumns().add(col);
                }
            }

            tabelaExport.setItems(tabelaUsuarios.getItems());
            // Função que mapeia Usuario para lista de objetos a serem exportados
            Function<Usuario, List<Object>> rowMapper = usuario -> Arrays.asList(
                    usuario.getId_usuario(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getSenha(),
                    usuario.getTipo()
            );

            ExcelExporter.exportTableViewToExcel(tabelaExport, file.getAbsolutePath(), rowMapper);
            Alerts.showAlert("Aviso", "Sucesso!!", "Exportação salva em " + file.getAbsolutePath(), Alert.AlertType.INFORMATION);
            System.out.println("Exportação salva em: " + file.getAbsolutePath());
        }
    }

    @Override
    public void reloadTable() {
        loadTableUsuarioView();
    }
}

