package ExcelToExcelConverter;

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
 * @since 2025/2/19 16:48
 */
public class ExcelToExcelConverter {
    public static void main(String[] args) {
        String origianlFilePath = "C:\\Users\\fangx\\Desktop\\易冲订单导入\\M603-2502100002_CPS4061A0.xlsx";

        // 读取Excel文件内容
        List<ExcelDataOfCDYC> readResult = ExcelReader.readExcel(origianlFilePath);
    }
}