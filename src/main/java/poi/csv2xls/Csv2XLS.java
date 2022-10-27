package poi.csv2xls;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Csv2XLS {
    public static void csvToXLSX() {
        try {
            String csvFileAddress = "test.csv"; //csv file address
            String xlsxFileAddress = "test.xlsx"; //xlsx file address
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("ck");
            String currentLine=null;
            int RowNum=0;
            BufferedReader br = new BufferedReader(new FileReader(csvFileAddress));
            while ((currentLine = br.readLine()) != null) {
                String str[] = currentLine.split(",");
                RowNum++;
                XSSFRow currentRow=sheet.createRow(RowNum);
                for(int i=0;i<str.length;i++){
                    currentRow.createCell(i).setCellValue(str[i]);
                }
            }

            // repeat
            String csvFileAddress2 = "test2.csv"; //csv file address

            XSSFSheet sheet2 = workBook.createSheet("dk");
            currentLine=null;
            RowNum=0;
            br = new BufferedReader(new FileReader(csvFileAddress2));
            while ((currentLine = br.readLine()) != null) {
                String str[] = currentLine.split(",");
                RowNum++;
                XSSFRow currentRow=sheet2.createRow(RowNum);
                for(int i=0;i<str.length;i++){
                    currentRow.createCell(i).setCellValue(str[i]);
                }
            }

            FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
            System.out.println("Done");
        } catch (Exception ex) {
            System.out.println(ex.getMessage()+"Exception in try");
        }
    }

    public static void main(String[] args) {
        csvToXLSX();
    }
}
