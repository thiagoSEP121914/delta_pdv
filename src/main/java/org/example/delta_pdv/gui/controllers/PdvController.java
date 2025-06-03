package org.example.delta_pdv.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.example.delta_pdv.entities.*;
import org.example.delta_pdv.gui.utils.Alerts;
import org.example.delta_pdv.gui.utils.PaymentPdf;
import org.example.delta_pdv.gui.utils.ProdutoSearchListener;
import org.example.delta_pdv.service.CategoriaService;
import org.example.delta_pdv.service.ItemVendaService;
import org.example.delta_pdv.service.ProdutoService;
import org.example.delta_pdv.service.VendaService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class PdvController implements Initializable, ProdutoSearchListener {

    @FXML
    private FlowPane cardLayout;

    @FXML
    private HBox categoriaHbox;

    @FXML
    private VBox itensVendaBox;

    @FXML
    private Label labelSubTotal;

    @FXML
    private ToggleGroup pagamentoGroup;

    @FXML
    private RadioButton radioCartao;

    @FXML
    private RadioButton radioDinheiro;

    @FXML
    private RadioButton radioPix;

    @FXML
    private Button btnPagamento;

    private List<ItemVenda> itensVendas = new ArrayList<>();

    private VendaService vendaService = new VendaService();
    private ProdutoService produtoService = new ProdutoService();
    private ItemVendaService itemVendaService = new ItemVendaService();
    private CategoriaService categoriaService = new CategoriaService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarCategorias();
        carregarItensVendas();
        setRadioButtons();
    }


    @FXML
    private void onBtnPagamentoOnAction() {
        if (itensVendas.isEmpty()) {
            Alerts.showAlert("Aviso!", "", "Nenhum item selecionado", Alert.AlertType.INFORMATION);
            return;
        }

        Optional<ItemVenda> itemComEstoqueInsuficiente = verificarEstoqueInsuficiente();
        if (itemComEstoqueInsuficiente.isPresent()) {
            Produto produto = itemComEstoqueInsuficiente.get().getProduto();
            Alerts.showAlert("Aviso!", "", "O produto " + produto.getNome() + "Possui apenas " + produto.getQuantidadeEstoque() + " Quantidade em estoque", Alert.AlertType.WARNING);
            return;
        }

        Optional<ButtonType> result = Alerts.showAlertYesNo("Confirmação", "", "Deseja confirmar o pagamento?", Alert.AlertType.CONFIRMATION);
        if (result.isEmpty() || result.get().getButtonData() != ButtonBar.ButtonData.YES) return;

        try {
            File arquivoSelecionado = abrirDialogoSalvarArquivo();

            if (arquivoSelecionado == null) {
                Alerts.showAlert("Aviso", "", "Operação cancelada pelo usuário.", Alert.AlertType.INFORMATION);
                return;
            }

            String caminhoArquivo = corrigirExtensaoPdf(arquivoSelecionado.getAbsolutePath());

            salvarItemVenda(); // Salvar no banco
            System.out.println("Venda salva no banco");

            new PaymentPdf().gerarPDF(itensVendas, caminhoArquivo); // Gerar PDF
            System.out.println("PDF salvo em: " + caminhoArquivo);

            itensVendas.clear();
            carregarItensVendas();
            atualizarSubTotal();
            Alerts.showAlert("Sucesso", null, "Venda finalizada e PDF salvo em:\n" + caminhoArquivo, Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            Alerts.showAlert("Erro", null, "Erro ao finalizar venda:\n" + e.getMessage(), Alert.AlertType.ERROR);
            throw new RuntimeException("Erro ao salvar no banco: " + e.getMessage());
        } finally {
            clearAll();
        }
    }

    @FXML
    private void onBtnCancelarOnAction() {
        clearAll();
    }

    private Venda salvarVenda () {
        if (itensVendas == null || itensVendas.isEmpty()) {
            Alerts.showAlert("Erro", " ", "A venda não possue itens", Alert.AlertType.INFORMATION);
            return null;
        }


        Venda venda = new Venda();
        Cliente cliente = new Cliente();
        cliente.setIdCliente(2L); // seria o cliente padrao generico do banco caso o usuario nao escolha cliente
        venda.setCliente(cliente);
        LocalDate localDate = LocalDate.now();
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        venda.setDataVenda(date);
        Toggle selectedToggle = pagamentoGroup.getSelectedToggle();

        if (selectedToggle == null) {
            Alerts.showAlert("Aviso", " ", "Selecione uma forma de pagamento", Alert.AlertType.INFORMATION);
            return null;
        }

        FormaPagamento formaPagamento = (FormaPagamento) selectedToggle.getUserData();
        venda.setFormaPagamento(formaPagamento);
        venda.setTotal(getTotal());
        long idGerado =  vendaService.insert(venda);
        venda.setIdVenda(idGerado);
        return venda;
    }

    private void salvarItemVenda() {
        Venda venda = salvarVenda();
        if (venda == null) {
            return;
        }

        for (ItemVenda item : itensVendas) {
            Produto produto = item.getProduto();

            item.setVenda(venda); // ID_Venda
            item.setProduto(produto); // ID_Produto
            item.setQtd(item.getQtd()); // ← garante que Quantidade será salva
            item.setPrecoUnitario(produto.getPrecoUnitario()); // Preco_Unitario
            itemVendaService.insert(item);
            int novaQuantidade = produto.getQuantidadeEstoque() - item.getQtd();
            produto.setQuantidadeEstoque(novaQuantidade);
            produtoService.saveProducts(produto);
        }
    }


    public void carregarProdutoNaTela() {
        itensVendaBox.getChildren().clear();
        carregarItensVendas();
    }

    private void carregarItensVendas() {
        try {
            for (ItemVenda itemVenda : itensVendas) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/org/example/delta_pdv/produtoItemVenda.fxml"));
                VBox itemBox = fxmlLoader.load();
                ProdutoItemVendaController itemController = fxmlLoader.getController();
                itemController.setData(itemVenda);
                itemController.setPdvController(this);
                itensVendaBox.getChildren().add(itemBox);
            }
            atualizarSubTotal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Optional<ItemVenda> verificarEstoqueInsuficiente() {
        return itensVendas.stream()
                .filter(item -> item.getQtd() > item.getProduto().getQuantidadeEstoque())
                .findFirst();
    }

    private void carregarCategorias() {
        categoriaHbox.getChildren().clear();

        List<Categoria> categorias = categoriaService.findAll();

        try {
            for (Categoria categoria : categorias) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/org/example/delta_pdv/categoryCard.fxml"));
                HBox categoriaBox = fxmlLoader.load();
                CategoriaCardController categoriaCardController = fxmlLoader.getController();
                categoriaCardController.setData(categoria.getNome());
                categoriaCardController.setPdvController(this);  // IMPORTANTE!
                categoriaHbox.getChildren().add(categoriaBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void carregarProdutosPorCategoria(String nomeCategoria) {
        cardLayout.getChildren().clear();

        List<Produto> produtos = produtoService.findByCategoria(nomeCategoria)
                .stream()
                .filter(p -> p.getQuantidadeEstoque() > 0)
                .toList();

        try {
            for (Produto produto : produtos) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/delta_pdv/produtoCard.fxml"));
                HBox cardBox = fxmlLoader.load();
                ProdutoCardController cardController = fxmlLoader.getController();
                cardController.setData(produto);
                cardController.setPdvController(this);
                cardLayout.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addItemVenda(ItemVenda itemVenda) {
        Optional<ItemVenda> itemExistente = itensVendas.stream()
                        .filter(iv ->  iv.getProduto().getIdProduto() == itemVenda.getProduto().getIdProduto()).findFirst();

        if (itemExistente.isPresent()) {
            ItemVenda item = itemExistente.get();
            item.setQtd(item.getQtd() + itemVenda.getQtd());
            item.getTotal();
        } else {
            itensVendas.add(itemVenda);
        }
        carregarProdutoNaTela();
        atualizarSubTotal();
    }

    public void deleteItensVendas(ItemVenda itemVenda) {
        itensVendas.removeIf(iv -> iv.getProduto().getIdProduto() == itemVenda.getProduto().getIdProduto());
        carregarProdutoNaTela();
    }

    public Double getTotal() {
        return itensVendas.stream()
                .mapToDouble(ItemVenda::getTotal)
                .sum();
    }




    private void setRadioButtons() {
        radioCartao.setUserData(FormaPagamento.CARTAO);
        radioDinheiro.setUserData(FormaPagamento.DINHEIRO);
        radioPix.setUserData(FormaPagamento.PIX);
    }


    public void atualizarSubTotal() {
        double subTotal = 0;
        for (ItemVenda itemVenda : itensVendas) {
            subTotal += itemVenda.getTotal();
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        labelSubTotal.setText(formatter.format(subTotal));
    }

    @Override
    public Optional<Produto> onBuscarProduto(String nomeBusca) {
        cardLayout.getChildren().clear();

        // Filtra os produtos com estoque > 0
        List<Produto> produtosFiltrados = produtoService.findByName(nomeBusca)
                .stream()
                .filter(p -> p.getQuantidadeEstoque() > 0)
                .toList();

        try {
            for (Produto produto : produtosFiltrados) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/org/example/delta_pdv/produtoCard.fxml"));
                HBox cardBox = fxmlLoader.load();
                ProdutoCardController cardController = fxmlLoader.getController();
                cardController.setData(produto);
                cardController.setPdvController(this);
                cardLayout.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return produtosFiltrados.stream().findFirst();
    }


    private File abrirDialogoSalvarArquivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Comprovante");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf"));
        fileChooser.setInitialFileName("comprovante_" + System.currentTimeMillis() + ".pdf");

        return fileChooser.showSaveDialog(null); // null = janela principal
    }

    private String corrigirExtensaoPdf(String caminho) {
        return caminho.toLowerCase().endsWith(".pdf") ? caminho : caminho + ".pdf";
    }

    private void clearAll() {
        itensVendas.clear();
        itensVendaBox.getChildren().clear();
        cardLayout.getChildren().clear();
        labelSubTotal.setText("0, 00");
        carregarCategorias();
    }
}
