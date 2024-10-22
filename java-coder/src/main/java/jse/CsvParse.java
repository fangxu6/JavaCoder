package jse;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

/**
 * className: CsvParse
 * package: jse
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/11/9 11:19
 */
public class CsvParse {
    public static void main(String[] args) throws IOException {
        String csvFile = "C:\\Users\\fangx\\Desktop\\陈鑫卡控客户gap要求\\OCP2130WPAD-G(SPG8929B)_V5_230412_JSSI_T23A2024-A_3RIY03098-01A2_20231106_170523.csv";
        int startRow = 1; // 指定从第5行开始解析
        List<SPG8929BBean> beanList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            int currentRow = 1; // 当前行数
            while ((line = reader.readLine()) != null) {
                if (line.contains("SITE_NUM")) {
                    line = reader.readLine();
                    line = reader.readLine();
                    line = reader.readLine();
                    line = reader.readLine();//读取四行
                    currentRow = currentRow + 4;
                    startRow = currentRow;
                    break;
                }
                currentRow++;
            }

            while ((line = reader.readLine()) != null) {
                if (currentRow >= startRow) {
                    // 进行解析逻辑
                    String[] values = line.split(","); // 假设逗号为分隔符
                    // 处理每一行的数据
                    Integer SITE_NUM = Integer.valueOf(values[0]);
                    Integer PART_ID = Integer.valueOf(values[1]);
                    String PASSFG = values[2];
                    Short SOFT_BIN = Short.valueOf(values[3]);
                    Integer T_TIME = Integer.valueOf(values[4]);
                    Short X_COORD = Short.valueOf(values[5]);
                    Short Y_COORD = Short.valueOf(values[6]);
                    Integer TEST_NUM = Integer.valueOf(values[7]);
                    if (PASSFG.equalsIgnoreCase("TRUE") && SOFT_BIN == 1) {
                        Double VREF1_PRE = Double.valueOf(values[34]);
                        Double IB_PRE = Double.valueOf(values[39]);
                        Short ILIMIT_AVDD_PRE = Short.valueOf(values[43]);
                        Short ILIMIT_BST_PRE = Short.valueOf(values[47]);
                        Double FREQ_PRE = Double.valueOf(values[51]);
                        Double VBST_PRE = Double.valueOf(values[55]);
                        Double AVDD_PRE = Double.valueOf(values[59]);
                        Double AVEE_FREQ_PRE = Double.valueOf(values[63]);
                        Double AVEE_PRE = Double.valueOf(values[67]);
                        Double VOS_BST_PRE = Double.valueOf(values[71]);
                        SPG8929BBean spg8929BBean = new SPG8929BBean(SITE_NUM, PART_ID, PASSFG, SOFT_BIN, T_TIME, X_COORD, Y_COORD, TEST_NUM, VREF1_PRE, IB_PRE, ILIMIT_AVDD_PRE, ILIMIT_BST_PRE, FREQ_PRE, VBST_PRE, AVDD_PRE, AVEE_FREQ_PRE, AVEE_PRE, VOS_BST_PRE);
                        beanList.add(spg8929BBean);
                    }

                }
                currentRow++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SPG8929BResult> spg8929BResults = SPG8929BResult.getSPG8929BResultList(beanList);

        SPG8929BResult gap = SPG8929BResult.getGap(spg8929BResults);
//        spg8929BResults.add(gap);

        final String[] FILE_HEADER = {"column", "1", "2", "3", "4", "5", "6", "7", "8"};
        final String[] COLUMN_HEADER = {"column",
                "VREF1_PRE",
                "IB_PRE",
                "ILIMIT_AVDD_PRE",
                "ILIMIT_BST_PRE",
                "FREQ_PRE",
                "VBST_PRE",
                "AVDD_PRE",
                "AVEE_FREQ_PRE",
                "AVEE_PRE",
                "VOS_BST_PRE"};
        final String FILE_NAME = "result.csv";


        // 这里显式地配置一下CSV文件的Header，然后设置跳过Header（要不然读的时候会把头也当成一条记录）
        CSVFormat format = CSVFormat.DEFAULT.withHeader(COLUMN_HEADER).withSkipHeaderRecord(false);

        // 这是写入CSV的代码
        try (Writer out = new FileWriter(FILE_NAME);
             CSVPrinter printer = new CSVPrinter(out, format)) {
            int i=1;
            for (SPG8929BResult spg8929BResult:spg8929BResults) {
                List<String> records = new ArrayList<>();
                records.add(String.valueOf(i));
                records.add(String.valueOf(spg8929BResult.VREF1_PRE));
                records.add(String.valueOf(spg8929BResult.IB_PRE));
                records.add(String.valueOf(spg8929BResult.ILIMIT_AVDD_PRE));
                records.add(String.valueOf(spg8929BResult.ILIMIT_BST_PRE));
                records.add(String.valueOf(spg8929BResult.FREQ_PRE));
                records.add(String.valueOf(spg8929BResult.VBST_PRE));
                records.add(String.valueOf(spg8929BResult.AVDD_PRE));
                records.add(String.valueOf(spg8929BResult.AVEE_FREQ_PRE));
                records.add(String.valueOf(spg8929BResult.AVEE_PRE));
                records.add(String.valueOf(spg8929BResult.VOS_BST_PRE));
                printer.printRecord(records);
                i++;
            }

            List<String> records = new ArrayList<>();
            records.add("gap");
            records.add(String.valueOf(gap.VREF1_PRE));
            records.add(String.valueOf(gap.IB_PRE));
            records.add(String.valueOf(gap.ILIMIT_AVDD_PRE));
            records.add(String.valueOf(gap.ILIMIT_BST_PRE));
            records.add(String.valueOf(gap.FREQ_PRE));
            records.add(String.valueOf(gap.VBST_PRE));
            records.add(String.valueOf(gap.AVDD_PRE));
            records.add(String.valueOf(gap.AVEE_FREQ_PRE));
            records.add(String.valueOf(gap.AVEE_PRE));
            records.add(String.valueOf(gap.VOS_BST_PRE));
            printer.printRecord(records);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
