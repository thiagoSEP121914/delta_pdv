package org.example.delta_pdv.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.example.delta_pdv.entities.Produto;

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
    private Produto produtoAtual;
    private Integer qtd = 1;


    public Integer getQtd() {
        return qtd;
    }

    public void setPdvController(PdvController pdvController) {
        this.pdvController = pdvController;
    }

    @FXML
   private void btnDeletarOnAction(ActionEvent event) {
        pdvController.deleteProdutosSelecionados(produtoAtual);
    }
    @FXML
     private void onBtnMaisOnAction(ActionEvent event) {
        qtd++;
        qtdLabel.setText(String.valueOf(qtd));
        atualizarPreco();
    }

    @FXML
     private void onBtnMenorOnACtion(ActionEvent event) {
        if (qtd > 1) {
            qtd--;
            qtdLabel.setText(String.valueOf(qtd));
            atualizarPreco();
        }
    }

    public void setData(Produto produto) {
        if (produto == null) return;

        this.produtoAtual = produto;

        nomeLabel.setText(produto.getNome());
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        qtd = 1;
        qtdLabel.setText(String.valueOf(qtd));

        Double precoUnitario = produto.getPrecoUnitario();
        loadPrecos(precoUnitario);

        try {
            String caminhoImagem = produto.getCaminhoImagem();

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
        loadPrecos(produtoAtual.getPrecoUnitario());
    }
}
