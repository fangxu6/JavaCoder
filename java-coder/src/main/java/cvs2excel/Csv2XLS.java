package cvs2excel;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Csv2XLS {
    public static void csvToXLSX(XSSFWorkbook workBook,String filename, String sheetname,String xlsfilename) {
        try {
            String csvFileAddress = filename+".csv"; //csv file address
            String xlsxFileAddress = xlsfilename+".xlsx"; //xlsx file address
//            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet(sheetname);
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

            FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
            //System.out.println("Done");
        } catch (Exception ex) {
            System.out.println(ex.getMessage()+"Exception in try");
        }
    }

    public static void main(String[] args) {
//        csvToXLSX();
        XSSFWorkbook workBook = new XSSFWorkbook();
        String filename="test";
        String xlsfilename="test";
        String sheetname="ck";
        csvToXLSX(workBook,filename,sheetname,xlsfilename);

        filename="test2";
        sheetname="dk";
        csvToXLSX(workBook,filename,sheetname,xlsfilename);
    }
}
