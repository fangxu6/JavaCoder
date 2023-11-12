package jse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
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
        // 创建CSVParser对象
        CSVParser parser = CSVParser.parse(new File
                ("C:\\Users\\fangx\\Desktop\\陈鑫卡控客户gap要求\\OCP2130WPAD-G(SPG8929B)_V5_230412_JSSI_T23A2024-A_3RIY03098-01A2_20231106_170523.csv"), Charset.defaultCharset(), CSVFormat.DEFAULT);

// 遍历解析得到的记录
//        for (CSVRecord record : parser) {
//            // 处理每一行的数据
//            String value1 = record.get(0); // 获取第一列的值
////            String value2 = record.get(1); // 获取第二列的值
//            // 进行统计和计算操作
//            // ...
//            if (value1.trim().equalsIgnoreCase("SITE_NUM")){
//                System.out.println("value1:" + value1);
//            }
//        }

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

        List<SPG8929BResult> spg8929BResults = SPG8929BResult.gapList(beanList);
    }



}
