package tel2mapping.tester;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * className: ChipOneICTestData
 * package: tel2mapping.dat
 * Description:
 * collect SoftBin Info
 *
 * @author fangxu6@gmail.com
 * @since 2023/7/21 15:23
 */
public class ChipOneICTestData {
    private String binName;
    private Integer dieNumber;
    private double yield;
    private String description;


    public ChipOneICTestData read(String file) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(file));

        return this;
    }
}
