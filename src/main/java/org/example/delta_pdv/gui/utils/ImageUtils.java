package org.example.delta_pdv.gui.utils;

import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static File buscarImagem(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.JPEG", "*.jpeg")
        );

        return fileChooser.showOpenDialog(stage);
    }

    public static String salvarImagem(File imagemOriginal) {
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
            throw new RuntimeException("Erro ao salvar a imagem: " + e.getMessage(), e);
        }
    }

}
