package org.example.delta_pdv.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.example.delta_pdv.entities.ItemVenda;
import org.example.delta_pdv.gui.utils.Alerts;

import java.io.File;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class ProdutoItemVendaController {

    @FXML
    private ImageView image;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label precoTotal;

    @FXML
    private Label precoUni;

    private PdvController pdvController;

    @FXML
    private Label qtdLabel;
    private ItemVenda itemVendasAtual;
    private Integer qtd = 1;

    public void setPdvController(PdvController pdvController) {
        this.pdvController = pdvController;
    }

    public Integer getQtd() {
        return qtd;
    }

    @FXML
   private void btnDeletarOnAction(ActionEvent event) {
        pdvController.deleteItensVendas(itemVendasAtual);
    }

    @FXML
    private void onBtnMaisOnAction(ActionEvent event) {
        int estoqueDisponivel = itemVendasAtual.getProduto().getQuantidadeEstoque();

        if (qtd + 1 > estoqueDisponivel) {
            Alerts.showAlert("Aviso", "", "Quantidade selecionada indisponível no estoque!", Alert.AlertType.WARNING);
            return;
        }

        qtd++;
        qtdLabel.setText(String.valueOf(qtd));
        atualizarPreco();

        if (pdvController != null) {
            itemVendasAtual.setQtd(qtd);
            pdvController.atualizarSubTotal();
        }
    }


    @FXML
     private void onBtnMenorOnACtion(ActionEvent event) {
        if (qtd <= 1) {
            return;
        }
        qtd--;
        qtdLabel.setText(String.valueOf(qtd));
        atualizarPreco();

        if (pdvController != null) {
            itemVendasAtual.setQtd(qtd);
            pdvController.atualizarSubTotal();
        }
    }

    public void setData(ItemVenda itemVendas) {
        if (itemVendas == null) return;

        this.itemVendasAtual = itemVendas;

        nomeLabel.setText(itemVendas.getProduto().getNome());
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        qtd = itemVendas.getQtd();
        qtdLabel.setText(String.valueOf(qtd));

        Double precoUnitario = itemVendas.getPrecoUnitario();
        loadPrecos(precoUnitario);

        try {
            String caminhoImagem = itemVendas.getProduto().getCaminhoImagem();

            if (caminhoImagem != null && !caminhoImagem.isBlank()) {
                File imagemArquivo = new File(caminhoImagem);

                if (imagemArquivo.exists()) {
                    Image imagem = new Image(imagemArquivo.toURI().toString());
                    image.setImage(imagem);
                } else {
                    System.out.println("Imagem não encontrada no caminho: " + caminhoImagem);
                    // Aqui você pode definir uma imagem padrão se quiser
                    image.setImage(null); // ou new Image("/imagens/placeholder.png") se estiver embutido no projeto
                }
            } else {
                System.out.println("Caminho da imagem vazio ou nulo.");
                image.setImage(null);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem: " + e.getMessage());
            image.setImage(null); // Evita travamento da aplicação
        }
    }

    private void loadPrecos(Double precoUnitario) {
        Format formater = NumberFormat.getCurrencyInstance(new Locale("pt-br", "BR"));
        if (precoUnitario == null) {
            precoUni.setText("");
            precoTotal.setText("");
            return;
        }
        precoUni.setText(formater.format(precoUnitario));
        Double total = precoUnitario * qtd;
        precoTotal.setText(formater.format(total));
    }

    private void atualizarPreco() {
        loadPrecos(itemVendasAtual.getPrecoUnitario());
    }
}
