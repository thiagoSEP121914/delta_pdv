package org.example.delta_pdv.gui.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.example.delta_pdv.entities.ItemVenda;

import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PaymentPdf {

    public void gerarPDF(List<ItemVenda> itens, String caminhoArquivo) {
        try {
            PdfWriter writer = new PdfWriter(caminhoArquivo);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Título
            Paragraph titulo = new Paragraph("Comprovante de Venda")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(18);
            document.add(titulo);
            document.add(new Paragraph("\n"));

            // Colunas da tabela: Produto, Qtde, Preço Unit., Total
            float[] columnWidths = {200F, 50F, 80F, 80F};
            Table table = new Table(columnWidths);

            // Cabeçalho
            table.addHeaderCell(new Cell().add(new Paragraph("Produto").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Qtde").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Preço Unit.").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Total").setBold()));

            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            double subTotal = 0;

            // Preenchendo a tabela
            for (ItemVenda item : itens) {
                String nomeProduto = item.getProduto().getNome();
                int quantidade = item.getQtd();
                double precoUnitario = item.getProduto().getPrecoUnitario();
                double totalItem = item.getTotal();

                subTotal += totalItem;

                table.addCell(new Cell().add(new Paragraph(nomeProduto)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(quantidade))));
                table.addCell(new Cell().add(new Paragraph(formatter.format(precoUnitario))));
                table.addCell(new Cell().add(new Paragraph(formatter.format(totalItem))));
            }

            document.add(table);

            document.add(new Paragraph("\n"));

            // Subtotal
            Paragraph subtotalParagraph = new Paragraph("Subtotal: " + formatter.format(subTotal))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBold();
            document.add(subtotalParagraph);

            document.close();

            System.out.println("PDF gerado com sucesso em: " + caminhoArquivo);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Erro ao gerar pdf: " + e.getMessage());
        }
    }
}
