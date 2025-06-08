package org.example.delta_pdv.gui.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.example.delta_pdv.entities.FormaPagamento;
import org.example.delta_pdv.entities.ItemVenda;
import org.example.delta_pdv.entities.Venda;
import org.example.delta_pdv.gui.utils.Alerts;
import org.example.delta_pdv.gui.utils.ExcelExporter;
import org.example.delta_pdv.service.ItemVendaService;
import org.example.delta_pdv.service.VendaService;

import java.io.File;
import java.net.URL;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;


public class VendasController implements Initializable {

    @FXML
    private Label lblLucroPeriodo;

    @FXML
    private Label lblQtdVendido;

    @FXML
    private Label lblTicketMedio;

    @FXML
    private Label lblTotVendido;

    @FXML
    private TableColumn<Venda, Long> idColumn;

    @FXML
    private TableColumn<Venda, String> clienteColumn;

    @FXML
    private TableColumn<Venda, Double> totalColumn;

    @FXML
    private TableColumn<Venda, FormaPagamento> pagamentoColumn;


    @FXML
    private DatePicker datePicker;


    @FXML
    private ComboBox<String> mesComboBox;

    @FXML
    private TableView<Venda> tblVendas;

    @FXML
    private TableColumn<Venda, Date> dataColumn;

    private VendaService vendaService = new VendaService();

    private ItemVendaService itemVendaService = new ItemVendaService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadInitialTableView();
        loadComboBox();
    }


    @FXML
    public void onHojeMouseClicked() {

        datePicker.setValue(LocalDate.now());
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                // Exemplo: desabilitar datas futuras
                if (date.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE;");
                }
            }
        });
        loadTableView(vendaService.findAllVendasHoje());
       clearComboBox();
       clearDatePicker();
    }


    @FXML
    public void onSemanaMouseClicked() {
        LocalDate hoje = LocalDate.now();
        LocalDate segunda = hoje.with(DayOfWeek.MONDAY);
        Date dataInicio = java.sql.Date.valueOf(segunda);
        Date dataFim = java.sql.Date.valueOf(hoje.plusDays(1)); // inclui hoje inteiro
        List<Venda> todasAsVendas = vendaService.findAll();
        List<Venda> vendasSemana = todasAsVendas.stream()
                .filter(venda -> {
                    Date dataVenda = venda.getDataVenda();
                    return dataVenda != null && !dataVenda.before(dataInicio) && dataVenda.before(dataFim);
                })
                .toList();

        if (vendasSemana.isEmpty()) {
            Alerts.showAlert("Aviso", null, "Não há vendas registradas nesta semana.", Alert.AlertType.INFORMATION);
            loadInitialTableView();
        } else {
            loadTableView(vendasSemana);
        }
        clearDatePicker();
        clearComboBox();
    }

    @FXML
    private void onDatePickerOnAction() {
        if (datePicker.getValue() != null) {
            loadTableView(vendaPorData());
        }
    }


    @FXML
    private void onMesComboxAction() {
        int mesSelecionado = mesComboBox.getSelectionModel().getSelectedIndex();
        if (mesSelecionado < 0) return;

        LocalDate inicioMes = LocalDate.of(LocalDate.now().getYear(), mesSelecionado + 1, 1);
        LocalDate fimMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());

        Date dataInicio = java.sql.Date.valueOf(inicioMes);
        Date dataFim = java.sql.Date.valueOf(fimMes.plusDays(1));
        List<Venda> todasAsVendas = vendaService.findAll();
        List<Venda> vendasDoMes = todasAsVendas.stream()
                .filter(v -> {
                    Date dataVenda = v.getDataVenda();
                    return dataVenda != null && !dataVenda.before(dataInicio) && dataVenda.before(dataFim);
                })
                .toList();

        if (vendasDoMes.isEmpty()) {
            Alerts.showAlert("Aviso", null, "Não há vendas neste mês.", Alert.AlertType.INFORMATION);

            // Use runLater para limpar seleção após o evento atual finalizar
            Platform.runLater(() -> {
                mesComboBox.getSelectionModel().clearSelection();
                mesComboBox.setPromptText("Selecione um Mês");
            });

            return;
        }
        clearDatePicker();
        loadTableView(vendasDoMes);
        clearComboBox();
    }

    @FXML
    void onBtnExportarAction() {
        exportToExcel();
    }

    private void loadTableView(List<Venda> listOfvendas) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("idVenda"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        pagamentoColumn.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("dataVenda"));

        clienteColumn.setCellValueFactory(cellData -> {
            Venda venda = cellData.getValue();
            if (venda != null && venda.getCliente() != null && venda.getCliente().getNome() != null) {
                return new SimpleStringProperty(venda.getCliente().getNome());
            } else {
                return new SimpleStringProperty("Padrão");
            }
        });

        tblVendas.setItems(FXCollections.observableArrayList(listOfvendas));
        tblVendas.refresh();
        atualizarLabelsComVendas(listOfvendas);
    }

    private void loadComboBox() {
        List<String> meses = Arrays.asList(
                "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        );
        mesComboBox.setItems(FXCollections.observableArrayList(meses));
    }

    private void loadInitialTableView() {
        loadTableView(vendaService.findAll());
    }

    private List<Venda> vendaPorData() {
        if (datePicker.getValue() == null) {
            Alerts.showAlert("Aviso!!", " ", "Não foram encontrados dados de vendas para a data selecionada."
                    , Alert.AlertType.WARNING);
            return vendaService.findAll();
        }

        java.sql.Date data = java.sql.Date.valueOf(datePicker.getValue());
        if (vendaService.findallVendaPorData(data).isEmpty()) {
            Alerts.showAlert("Aviso!!", "", "Não foram encontrados dados de vendas no periodo selecionado!", Alert.AlertType.INFORMATION);
            return vendaService.findAll();
        }
        return vendaService.findallVendaPorData(data);
    }

    private void atualizarLabelsComVendas(List<Venda> vendas) {
        double lucroTotal = 0;
        double totalVendido = 0;
        int quantidadeTotal = 0;

        for (Venda venda : vendas) {
            List<ItemVenda> itens = itemVendaService.findByVenda(venda.getIdVenda());
            for (ItemVenda item : itens) {
                Double custoProduto = item.getProduto().getCusto();
                double precoUnitario = item.getPrecoUnitario();
                int quantidade = item.getQtd();

                // Verificação de segurança para evitar NullPointerException
                if (custoProduto != null) {
                    double lucroItem = (precoUnitario - custoProduto) * quantidade;
                    lucroTotal += lucroItem;
                    System.out.println(lucroTotal);
                }
                System.out.println(lucroTotal);

                totalVendido += precoUnitario * quantidade;
                quantidadeTotal += quantidade;
            }
        }


        lblLucroPeriodo.setText(getNumberFormat(lucroTotal));
        lblTotVendido.setText(getNumberFormat(totalVendido));
        lblQtdVendido.setText(String.valueOf(quantidadeTotal));
        double ticketMedio = quantidadeTotal > 0 ? totalVendido / quantidadeTotal : 0;
        lblTicketMedio.setText(getNumberFormat(ticketMedio));
    }

    private void exportToExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar arquivo Excel");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(tblVendas.getScene().getWindow());

        if (file != null) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            Function<Venda, List<Object>> vendaToList = venda -> Arrays.asList(
                    venda.getIdVenda(),
                    venda.getCliente() != null ? venda.getCliente().getNome() : "Padrão",
                    venda.getTotal(),
                    venda.getFormaPagamento(),
                    venda.getDataVenda() != null ? venda.getDataVenda() : ""
            );

            ExcelExporter.exportTableViewToExcel(tblVendas, file.getAbsolutePath(), vendaToList);

            System.out.println("Exportação salva em: " + file.getAbsolutePath());
        }
    }

    private String getNumberFormat(double valor) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt-br", "BR"));
        return numberFormat.format(valor);
    }

    private void clearComboBox() {
        mesComboBox.setPromptText("Selecione a data");
    }

    private void clearDatePicker() {
        datePicker.setValue(null);
    }
}