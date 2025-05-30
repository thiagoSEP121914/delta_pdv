package org.example.delta_pdv.gui.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.delta_pdv.entities.Categoria;
import org.example.delta_pdv.entities.Produto;
import org.example.delta_pdv.gui.utils.Alerts;
import org.example.delta_pdv.gui.utils.ImageUtils;
import org.example.delta_pdv.gui.utils.UpdateTableListener;
import org.example.delta_pdv.service.CategoriaService;
import org.example.delta_pdv.service.ProdutoService;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProdutoCadastroController implements Initializable {

    @FXML
    private ComboBox<Categoria> categoriaComboBox;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField txtCodProduto;

    @FXML
    private TextField txtCusto;

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtLucro;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPrecoUnitario;

    @FXML
    private TextField txtQuantidadeEstoque;

    private File imagemSelecionada;

    @FXML
    private Button btnSalvar;

    private Produto produto;

    //camada intermediaria entre controller e Dao
    private final CategoriaService categoriaService = new CategoriaService();

    private final ProdutoService produtoService = new ProdutoService();

    private UpdateTableListener updateTableListener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCategoriaComboBox();
        setImagemPadrao();
        configurarCampoNumerico(txtPrecoUnitario);
        configurarCampoNumerico(txtCusto);


    }

    public void setUpdateTableListener(UpdateTableListener updateTableListener) {
        this.updateTableListener = updateTableListener;
    }

    public void setUpdateProduto (Produto produto) {
        this.produto = produto;
    }

    @FXML
    private void btnSalvarOnAction(ActionEvent event) {
        btnSalvar.setDisable(true);
        try {
            produtoService.insert(instantiateProduto());
            Alerts.showAlertYesNo("Sucesso!"," " , "DADOS FORAM SALVOS COM SUCESSO!", Alert.AlertType.INFORMATION);
            updateTableListener.reloadTable();
        }catch (Exception exception) {
            Alerts.showAlert("ERRO!", "ERRO AO SALVAR DADOS NO BANCO", "Erro: " + exception.getMessage(), Alert.AlertType.ERROR);
        }finally {
            clearFields();
            btnSalvar.setDisable(false);
        }
    }
    @FXML
    private void btnLimparOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    private void btnBuscarImagemOnAction(ActionEvent event) {
        Stage stage = (Stage)  imageView.getScene().getWindow();
        File file = ImageUtils.buscarImagem(stage);

        if (file!= null) {
            imagemSelecionada = file;
            Image imagem = new Image(file.toURI().toString());
            imageView.setImage(imagem);
        }
    }

    @FXML
   private void onLucroMouseCliked() {
        atualizarCampoLucro();
    }

    /*
    private void buscarImagem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.JPEG", "*.jpeg")
        );

        Stage stage = (Stage) imageView.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            imagemSelecionada = file;
            Image imagem = new Image(file.toURI().toString());
            imageView.setImage(imagem);
        }
    }

        private String salvarImagem(File imagemOriginal) {
        try {
            String nomeArquivo = System.currentTimeMillis() + "_" + imagemOriginal.getName(); // evita conflitos
            File pastaDestino = new File("imgs");

            if (!pastaDestino.exists()) {
                pastaDestino.mkdirs(); // cria a pasta se não existir
            }

            File destino = new File(pastaDestino, nomeArquivo);

            java.nio.file.Files.copy(
                    imagemOriginal.toPath(),
                    destino.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            return "imgs/" + nomeArquivo;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar  imagem: " + e.getMessage(), e);
        }
    }
     */

    private void loadCategoriaComboBox() {
        categoriaComboBox.setItems(FXCollections.observableArrayList(categoriaService.findAll()));
        categoriaComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Categoria categoria) {
                return categoria != null ? categoria.getNome() : "";
            }

            @Override
            public Categoria fromString(String nome) {
                if (nome == null || nome.isEmpty()) return null;
                for (Categoria c : categoriaComboBox.getItems()) {
                    if (c.getNome().equals(nome)) {
                        return c;
                    }
                }
                return null; // Não usado
            }
        });
    }

    private Produto instantiateProduto() {

        String caminhoImagem = imagemSelecionada != null ? ImageUtils.salvarImagem(imagemSelecionada): "";

        Categoria categoriaSelecionada = categoriaComboBox.getSelectionModel().getSelectedItem();
        if (categoriaSelecionada == null) {
            throw new IllegalArgumentException("Categoria não selecionada.");
        }

        return new Produto(
                txtNome.getText(),
                caminhoImagem,
                txtDescricao.getText(),
                Double.parseDouble(txtPrecoUnitario.getText().replace(",", ".")),
                Double.parseDouble(txtCusto.getText().replace(",", ".")),
                Integer.parseInt(txtQuantidadeEstoque.getText()),
                categoriaSelecionada
        );
    }

    private void configurarCampoNumerico(TextField campo) {
        campo.textProperty().addListener((obs, oldValue, newValue) -> {
            // Permite apenas números com vírgula e no máximo duas casas decimais
            if (!newValue.matches("\\d{0,7}(,\\d{0,2})?")) {
                campo.setText(oldValue);
            }
        });

        campo.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
            if (!newFocus) { // perdeu o foco
                String texto = campo.getText().replace(",", ".");
                try {
                    double valor = Double.parseDouble(texto);
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
                    symbols.setDecimalSeparator(',');
                    DecimalFormat df = new DecimalFormat("###,##0.00", symbols);
                    campo.setText(df.format(valor));
                } catch (NumberFormatException e) {
                    campo.setText(""); // limpa se valor inválido
                }
            }
        });
    }
    private void atualizarCampoLucro() {
        double precoUnitario = Double.parseDouble(txtPrecoUnitario.getText().replace(",", "."));
        double custo = Double.parseDouble(txtCusto.getText().replace(",", "."));
        Produto produtoTemp = new Produto(precoUnitario, custo);
        txtLucro.setText(String.format("%.2f", produtoTemp.getLucro()));
    }

    private void clearFields() {
        txtNome.clear();
        txtDescricao.clear();
        txtPrecoUnitario.clear();
        txtCusto.clear();
        txtQuantidadeEstoque.clear();
        txtLucro.clear();
        categoriaComboBox.getSelectionModel().clearSelection();
        imagemSelecionada = null;
        setImagemPadrao();
    }

    private void setImagemPadrao() {
        String caminhoImagem = "/org/example/delta_pdv/icons/user.png";
        URL url = getClass().getResource(caminhoImagem);
        Optional<URL> optionalURL = Optional.ofNullable(url);
        Image imagem = optionalURL
                .map(u -> new Image(u.toExternalForm()))
                .orElseGet(() -> {
                    System.out.println("Imagem padrão não encontrada ligue para o nathan resolver: " + caminhoImagem);
                    return new Image("file:default-image.png");
                });
        imageView.setImage(imagem);
    }

   private void fillFormWithProduto() {
        if (produto != null) {
            txtCodProduto.setText(produto.getIdProduto().toString());

        }
   }
}
