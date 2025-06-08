package org.example.delta_pdv.gui.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;


public class ExcelExporter {

    public static <T> void exportTableViewToExcel(
            TableView<T> table,
            String filePath,
            Function<T, List<Object>> rowMapper) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Dados");
        Row headerRow = sheet.createRow(0);
        List<TableColumn<T, ?>> columns = table.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            headerRow.createCell(i).setCellValue(columns.get(i).getText());
        }

        int rowIndex = 1;
        for (T item : table.getItems()) {
            Row row = sheet.createRow(rowIndex++);
            List<Object> values = rowMapper.apply(item);
            for (int i = 0; i < values.size(); i++) {
                Object val = values.get(i);
                Cell cell = row.createCell(i);

                if (val == null) {
                    cell.setCellValue("");
                } else if (val instanceof Number) {
                    cell.setCellValue(((Number) val).doubleValue());
                } else {
                    cell.setCellValue(val.toString());
                }
            }
        }

        // Ajusta as colunas
        for (int i = 0; i < columns.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        // Salvar arquivo
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            System.out.println("Arquivo exportado: " + filePath);
        } catch (IOException e) {
            Alerts.showAlert("Erro", " ", "Erro ao exportar" + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
}
