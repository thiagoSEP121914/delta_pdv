package org.example.delta_pdv.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import org.example.delta_pdv.gui.utils.ScreenLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private StackedBarChart<String, Number> barChatVendas;

    @FXML
    private PieChart pieChartProdutos;

    @FXML
    private BorderPane mainPane;
    private ScreenLoader screenLoader;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadStackedBarChart();
        loadPieChart();
    }



    private void loadStackedBarChart() {
        XYChart.Series<String, Number> dados = new XYChart.Series<>();
        dados.getData().add(new XYChart.Data<>("Jan", 200));
        dados.getData().add(new XYChart.Data<>("Fev", 150));
        dados.getData().add(new XYChart.Data<>("Mar", 300));
        dados.getData().add(new XYChart.Data<>("Abr", 300));
        dados.getData().add(new XYChart.Data<>("Mai", 300));
        dados.getData().add(new XYChart.Data<>("Jun", 300));
        dados.getData().add(new XYChart.Data<>("Jul", 300));
        dados.getData().add(new XYChart.Data<>("Agos", 300));
        dados.getData().add(new XYChart.Data<>("Set", 300));
        dados.getData().add(new XYChart.Data<>("Out", 300));
        dados.getData().add(new XYChart.Data<>("Nov", 300));
        dados.getData().add(new XYChart.Data<>("Dez", 300));
        barChatVendas.getData().add(dados);
    }
    private void loadPieChart() {
        ObservableList<PieChart.Data> dados = FXCollections.observableArrayList(
                new PieChart.Data("Arroz tio Adilson", 40),
                new PieChart.Data("coca 350ml", 25),
                new PieChart.Data("pedra de crack", 35)
        );
        pieChartProdutos.setData(dados);
        pieChartProdutos.setTitle("Distribuição de Produtos");
    }



}