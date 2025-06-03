package org.example.delta_pdv.gui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.example.delta_pdv.entities.Cliente;
import org.example.delta_pdv.entities.FormaPagamento;
import org.example.delta_pdv.entities.Venda;
import org.example.delta_pdv.gui.utils.ScreenLoader;
import org.example.delta_pdv.service.ProdutoService;
import org.example.delta_pdv.service.VendaService;

import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

public class DashboardController implements Initializable {


    @FXML
    private Label lblFaturamentoMes;

    @FXML
    private Label lblFaturamentoHoje;

    @FXML
    private Label lblQtdEstoque;


    @FXML
    private StackedBarChart<String, Number> barChatVendas;

    @FXML
    private PieChart pieChartProdutos;


    @FXML
    private TableView<Venda> tblVendas;

    @FXML
    private TableColumn<Venda, Long> idColumn;

    @FXML
    private TableColumn<Venda, String> clienteColumn;

    @FXML
    private TableColumn<Venda, Double> totalColumn;

    //forma pagamento é um ENUM
    @FXML
    private TableColumn<Venda, FormaPagamento> pagamentoColumn;

    @FXML
    private TableColumn<Venda, Date> dataColumn;


    private ProdutoService produtoService = new ProdutoService();
    private VendaService  vendaService = new VendaService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadFaturamentoMes();
        loadVendasHoje();
        loadQtdEstoque();
        loadStackedBarChart();
        loadPieChart();
        loadTableView();

    }

    private void loadFaturamentoMes() {
        double faturamento = vendaService.getfaturamentoNoMesAtual();
        lblFaturamentoMes.setText(getNumberFormat(faturamento));
    }

    private void loadVendasHoje() {
        double vendasHoje = vendaService.getVendasHoje();
        lblFaturamentoHoje.setText(getNumberFormat(vendasHoje));


    }

    private void loadQtdEstoque() {
        int quantidadeEstoque = produtoService.totEstoque();
        lblQtdEstoque.setText(String.valueOf(quantidadeEstoque));
    }

    private void loadStackedBarChart() {
        String[] meses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        Map<String, Double> dadosCompletos = new LinkedHashMap<>();
        for (String mes : meses) {
            dadosCompletos.put(mes, 0.0);
        }

        // Substitui com os dados reais vindos do Service
        Map<String, Double> dadosMes = vendaService.faturamentoMensal(); // Ex: {"Jun" -> 840.0, "Mai" -> 300.0}
        for (Map.Entry<String, Double> entry : dadosMes.entrySet()) {
            String mes = entry.getKey();
            if (dadosCompletos.containsKey(mes)) {
                dadosCompletos.put(mes, entry.getValue());
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Vendas");

        for (Map.Entry<String, Double> entry : dadosCompletos.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChatVendas.getData().clear();
        barChatVendas.getData().add(series);
    }


    private void loadPieChart() {

        Map<String, Integer> totVendidos = produtoService.totalVendidos();
        ObservableList<PieChart.Data> dados = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry: totVendidos.entrySet()) {
            dados.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        pieChartProdutos.setData(dados);
        pieChartProdutos.setTitle("Produtos mais vendidos");
    }
    private String getNumberFormat(double valor) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt-br", "BR"));
        return numberFormat.format(valor);
    }


    private void loadTableView() {
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

        List<Venda> vendaList = vendaService.findAllVendasHoje();
        ObservableList<Venda> obsList = FXCollections.observableArrayList(vendaList);
        tblVendas.setItems(obsList);
    }
}