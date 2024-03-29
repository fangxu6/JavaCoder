package jse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * className: CSVDemo
 * package: jse
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/11/12 22:23
 */
public class CSVDemo {
    public static void main(String[] arg) {
        final String[] FILE_HEADER = {"ID","Name","Gender","Major"};
        final String FILE_NAME = "student.csv";

        Student stuTZY = new Student("001", "aaa", "male", "GIS");
        Student stuZJL = new Student("002", "周杰伦", "男", "音乐");
        List<Student> students = new ArrayList<>();
        students.add(stuTZY);
        students.add(stuZJL);

        // 这里显式地配置一下CSV文件的Header，然后设置跳过Header（要不然读的时候会把头也当成一条记录）
        CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withSkipHeaderRecord(false);

        // 这是写入CSV的代码
        try(Writer out = new FileWriter(FILE_NAME);
            CSVPrinter printer = new CSVPrinter(out, format)) {
            for (Student student : students) {
                List<String> records = new ArrayList<>();
                records.add(student.getID());
                records.add(student.getName());
                records.add(student.getGender());
                records.add(student.getMajor());
                printer.printRecord(records);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 这是从上面写入的文件中读出数据的代码
        try(Reader in = new FileReader(FILE_NAME)) {
            Iterable<CSVRecord> records = format.parse(in);
            String strID;
            String strName;
            for (CSVRecord record : records) {
                strID = record.get("ID");
                strName = record.get("Name");
                System.out.println(strID + " " + strName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
