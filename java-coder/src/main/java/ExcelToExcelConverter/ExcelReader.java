package ExcelToExcelConverter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * className: ExcelReader
 * package: ExcelToExcelConverter
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2025/2/19 10:12
 */
public class ExcelReader {
    private static final Logger logger = Logger.getLogger(ExcelReader.class.getName()); // 日志打印类

    /**
     * 读取Excel文件内容
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static List<ExcelDataOfCDYC> readExcel(String fileName) {
        List<ExcelDataOfCDYC> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(fileName);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Get first sheet

            // Get InvOutSourceId from row 1, column K (index 10)
            String invOutSourceId = getCellValueAsString(sheet.getRow(1).getCell(10));

            // Skip header row, start from row 1
            for (int i = 4; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                ExcelDataOfCDYC data = new ExcelDataOfCDYC();

                // Set the common InvOutSourceId for all records
                data.setInvOutSourceId(invOutSourceId);

                // Read the other fields starting from row 5
                String iPN = getCellValueAsString(getMergedCellValue(sheet, row, 1));
                //ipn=Total: 跳出循环
                if (iPN.equalsIgnoreCase("Total:")) {
                    break;
                }
                data.setIPN(iPN);        // 产品型号
                data.setCustLotId(getCellValueAsString(getMergedCellValue(sheet, row, 2)));  // 批号
                data.setWaferQty(getCellValueAsString(getMergedCellValue(sheet, row, 3)));   // 片数
                data.setWaferNo(getCellValueAsString(getMergedCellValue(sheet, row, 4)));    // 片号
                data.setProcessName(getCellValueAsString(getMergedCellValue(sheet, row, 5))); // 流程
                data.setDueDate(getCellValueAsString(getMergedCellValue(sheet, row, 6)));    // 要求交期
                data.setTestProgram(getCellValueAsString(getMergedCellValue(sheet, row, 10))); // 测试程序
                String address= getCellValueAsString(getMergedCellValue(sheet, row, 11));
                String convertedAddress = convertShipToAddress(address);
                data.setShipTo(convertedAddress);     // 出货地  TODO 只要4061需要这么做，其他的不需要
                data.setRemark(getCellValueAsString(getMergedCellValue(sheet, row, 12)));

                dataList.add(data);
            }

        } catch (IOException e) {
            logger.severe("读取Excel文件失败: " + e.getMessage());
            return null;
        }

        return dataList;
    }

    private static Cell getMergedCellValue(Sheet sheet, Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        if (cell == null || "".equals(getCellValueAsString(cell))) {
            for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
                CellRangeAddress mergedRegion = sheet.getMergedRegion(i);
                if (mergedRegion.isInRange(row.getRowNum(), columnIndex)) {
                    Row firstRow = sheet.getRow(mergedRegion.getFirstRow());
                    if (firstRow != null) {
                        return firstRow.getCell(mergedRegion.getFirstColumn());
                    }
                }
            }
        }
        return cell;
    }
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    private static String convertShipToAddress(String address) {
        if (address == null) return "";

        if (address.contains("芯德")) {
            return "JSSI";
        } else if (address.contains("长电")) {
            return "JCAP";
        } else if (address.contains("泰睿思")) {
            return "TRS";
        }
        return address;
    }
}
