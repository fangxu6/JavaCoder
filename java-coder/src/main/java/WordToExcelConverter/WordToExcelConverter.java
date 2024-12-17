package WordToExcelConverter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * className: WordToExcelConverter
 * package: WordToExcelConverter
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2024/12/10 16:48
 */
public class WordToExcelConverter {
    public static void main(String[] args) {
        String wordFilePath = "C:\\Users\\fangx\\Desktop\\赛芯自动吃工单\\fx.docx";
        String excelFilePath = "C:\\Users\\fangx\\Desktop\\赛芯自动吃工单\\default.xlsx";


        try (FileInputStream fis = new FileInputStream(wordFilePath);
             XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(excelFilePath)) {
            // Step 1: Load Word document
            XWPFDocument doc = new XWPFDocument(fis);

            // Step 2: Create an Excel sheet
            Sheet sheet = workbook.createSheet("Word Content");

            // Step 3: Read Word paragraphs and write to Excel
            int rowIndex = 0;
//            for (XWPFParagraph paragraph : doc.getParagraphs()) {
//
//                Row row = sheet.createRow(rowIndex++);
//                Cell cell = row.createCell(0);
//                cell.setCellValue(paragraph.getText());
//            }

            List<DataRecord> dataRecords = new ArrayList<>();
            // Step 4: Handle Word tables (if any)
            List<XWPFTable> tablesOutSide = doc.getTables();
//            for (XWPFTable table : tablesOutSide) {
            for(int i=0;i<tablesOutSide.size();i++){
                XWPFTable table = tablesOutSide.get(i); //table
                for (XWPFTableRow tableRow : table.getRows()) {

                    DataRecord dataRecord = new DataRecord();

                    Row row = sheet.createRow(rowIndex++);
                    int cellIndex = 0;

                    for (XWPFTableCell tableCell : tableRow.getTableCells()) {
                        String text = tableCell.getText();
                        Cell cell = row.createCell(cellIndex++);
                        cell.setCellValue(tableCell.getText());
                        //cell中还有tables
                        List<XWPFTable> tables = tableCell.getTables();
                        List<IBodyElement> bodyElements = tableCell.getBodyElements();
                        for (IBodyElement bodyElement : bodyElements                             ) {

                            if(bodyElement instanceof XWPFSDT){
                                XWPFSDT sdt = (XWPFSDT) bodyElement;
                                String textOfSdt = sdt.getContent().getText();
                                System.out.println("找到 XWPFSDT: " + sdt.getContent().getText());

                            }

                        }
//                        for (XWPFTable table1 : tables) {
                        for(int j=0;j<tables.size();j++){
                            XWPFTable table1 = tables.get(i); //table
                            for (XWPFTableRow tableRow1 : table1.getRows()) {
                                for (XWPFTableCell tableCell1 : tableRow1.getTableCells()) {
                                    Cell cell1 = row.createCell(cellIndex++);
                                    cell1.setCellValue(tableCell1.getText());
                                    for (XWPFParagraph paragraph : tableCell1.getParagraphs()) {
                                        for (XWPFRun run : paragraph.getRuns()) {
                                            dataRecord.addField(run.getText(0));
                                        }
                                    }
                                }
                            }

                        }
                        dataRecords.add(dataRecord);
                    }
                }
            }

            // Step 5: Save Excel file
            workbook.write(fos);
            System.out.println("Conversion completed. Excel file saved to " + excelFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 检查边框是否设置
    private static boolean hasBorders(CTTblBorders borders) {
        return isBorderSet(borders.getTop()) ||
                isBorderSet(borders.getBottom()) ||
                isBorderSet(borders.getLeft()) ||
                isBorderSet(borders.getRight()) ||
                isBorderSet(borders.getInsideH()) ||
                isBorderSet(borders.getInsideV());
    }

    // 检查单个边框是否设置
    private static boolean isBorderSet(CTBorder border) {
        return border != null && border.getVal() != null && !"none".equals(border.getVal().toString());
    }

    static class DataRecord {
        private List<String> fields = new ArrayList<>();

        public void addField(String value) {
            fields.add(value);
        }

        @Override
        public String toString() {
            return fields.toString();
        }
    }
}