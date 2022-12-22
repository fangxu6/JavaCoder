package tel2mapping;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadP8File {
    public static void main(String[] args) throws IOException {
        InputStream is = new FileInputStream("D:\\workspace\\articles\\dev\\c#\\封测TSK需求\\黄文龙tel开发示例\\新建文件夹\\LOT00001-TEL\\LOT1.DAT");
        byte[] a = new byte[388];
        byte[] b = new byte[3];
        byte[] c = new byte[3];
        byte[] d = new byte[3];

        OutputStream os = new FileOutputStream("out.txt");

        int readBytes = 0;

//        while ((readBytes  = is.read(b)) != -1) {
//            os.write(b, 0, readBytes);
//        }
//        is.close();
//        os.close();

        Path fileLocation = Paths.get("D:\\workspace\\articles\\dev\\c#\\封测TSK需求\\黄文龙tel开发示例\\新建文件夹\\LOT00001-TEL\\LOT1.DAT");
        byte[] data = Files.readAllBytes(fileLocation);
        System.arraycopy(data,0,a,0,368);
        System.arraycopy(data,368,b,0,3);//pass
        System.arraycopy(data,371,c,0,3);//fail
        System.arraycopy(data,374,d,0,3);//total


        byte[] pass = {0b00010010, 0b00000010};
        ByteBuffer wrapped = ByteBuffer.wrap(pass);
        short num = wrapped.getShort();
        System.out.println(num);

        int val = ((pass[1] & 0xff) << 8) | (pass[0] & 0xff);
        System.out.println(val);
    }
}
