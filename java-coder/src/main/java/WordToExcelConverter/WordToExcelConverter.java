package WordToExcelConverter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;

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
        String wordFilePath = "C:\\Users\\fangx\\Desktop\\赛芯自动吃工单\\fx-2.docx";
        String excelFilePath = "C:\\Users\\fangx\\Desktop\\赛芯自动吃工单\\default.xlsx";


        try (FileInputStream fis = new FileInputStream(wordFilePath);
             XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(excelFilePath)) {
            // Step 1: Load Word document
            XWPFDocument doc = new XWPFDocument(fis);

            String tag = null;
            String title = null;//1 11-21
            List<XWPFAbstractSDT> sdts = extractAllSDTs(doc);
//            for (XWPFAbstractSDT sdt : sdts) {
//                if (sdt.getContent().toString().equals("Rich_text")) {
//                    tag = "MyTag";
//                    title = "MyTitle";
//                    break;
//                }
//
//            }
            //订单编号：
            String orderNo = sdts.get(1).getContent().toString();
            //赛芯料号
            String productNo = sdts.get(11).getContent().toString();
            //产品型号
            String produceID = sdts.get(12).getContent().toString();
            //晶圆版本
            String lotVersion = sdts.get(13).getContent().toString();
            //LOT号
            String lotNo = sdts.get(14).getContent().toString();
            //晶圆尺寸
            String waferSize = sdts.get(15).getContent().toString();
            //刻号
            String  carveNo = sdts.get(16).getContent().toString();
            //数量/片
            String  quantity = sdts.get(17).getContent().toString();
            //测试程序+Trim版本
            String testProgram = sdts.get(18).getContent().toString();
            //打点情况
            String dot = sdts.get(19).getContent().toString();
            //交期ETD ETD → 预计出发时间（Estimated / Expected Time of Departure）
            String expectedTimeOfDeparture = sdts.get(20).getContent().toString();
            //到货地址
            String deliveryAddress = sdts.get(21).getContent().toString();
            //备注
            String remark = sdts.get(22).getContent().toString();



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



    private static List<XWPFAbstractSDT> extractAllSDTs(XWPFDocument doc) {
        List<XWPFAbstractSDT> sdts = new ArrayList<>();

        List<XWPFHeader> headers = doc.getHeaderList();
        for (XWPFHeader header : headers) {
            sdts.addAll(extractSDTsFromBodyElements(header.getBodyElements()));
        }
        sdts.addAll(extractSDTsFromBodyElements(doc.getBodyElements()));

        List<XWPFFooter> footers = doc.getFooterList();
        for (XWPFFooter footer : footers) {
            sdts.addAll(extractSDTsFromBodyElements(footer.getBodyElements()));
        }

        for (XWPFFootnote footnote : doc.getFootnotes()) {
            sdts.addAll(extractSDTsFromBodyElements(footnote.getBodyElements()));
        }
        for (XWPFEndnote footnote : doc.getEndnotes()) {
            sdts.addAll(extractSDTsFromBodyElements(footnote.getBodyElements()));
        }
        return sdts;
    }

    private static List<XWPFAbstractSDT> extractSDTsFromBodyElements(List<IBodyElement> elements) {
        List<XWPFAbstractSDT> sdts = new ArrayList<>();
        for (IBodyElement e : elements) {
            if (e instanceof XWPFSDT) {
                XWPFSDT sdt = (XWPFSDT) e;
                sdts.add(sdt);
            } else if (e instanceof XWPFParagraph) {

                XWPFParagraph p = (XWPFParagraph) e;
                for (IRunElement e2 : p.getIRuns()) {
                    if (e2 instanceof XWPFSDT) {
                        XWPFSDT sdt = (XWPFSDT) e2;
                        sdts.add(sdt);
                    }
                }
            } else if (e instanceof XWPFTable) {
                XWPFTable table = (XWPFTable) e;
                sdts.addAll(extractSDTsFromTable(table));
            }
        }
        return sdts;
    }

    private static List<XWPFAbstractSDT> extractSDTsFromTable(XWPFTable table) {

        List<XWPFAbstractSDT> sdts = new ArrayList<>();
        for (XWPFTableRow r : table.getRows()) {
            for (ICell c : r.getTableICells()) {
                if (c instanceof XWPFSDTCell) {
                    sdts.add((XWPFSDTCell) c);
                } else if (c instanceof XWPFTableCell) {
                    sdts.addAll(extractSDTsFromBodyElements(((XWPFTableCell) c).getBodyElements()));
                }
            }
        }
        return sdts;
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