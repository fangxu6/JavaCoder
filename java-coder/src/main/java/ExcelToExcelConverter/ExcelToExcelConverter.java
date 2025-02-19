package ExcelToExcelConverter;

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