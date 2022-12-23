package tel2mapping;

import tel2mapping.dat.LotDat;
import tel2mapping.dat.WaferDat;
import tel2mapping.dat.subentity.LineData;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReadP8File {
    public static void main(String[] args) throws IOException {
        InputStream is = new FileInputStream("D:\\workspace\\articles\\dev\\c#\\封测TSK需求\\黄文龙tel开发示例\\新建文件夹\\LOT00001-TEL\\LOT1.DAT");
        byte[] a = new byte[388];
        byte[] b = new byte[3];
        byte[] c = new byte[3];
        byte[] d = new byte[3];

        String file ="D:\\workspace\\articles\\dev\\c#\\封测TSK需求\\黄文龙tel开发示例\\新建文件夹\\LOT00001-TEL\\LOT1.DAT";
        LotDat lotDat = new LotDat();
        lotDat=lotDat.read(file);


        String file2 ="D:\\workspace\\articles\\dev\\c#\\封测TSK需求\\黄文龙tel开发示例\\新建文件夹\\LOT00001-TEL\\WAFER011.DAT";
        WaferDat waferDat = new WaferDat();
        waferDat=waferDat.read(file2);



        File os = new File("out.txt");
        Writer fw = new FileWriter(os);
        BufferedWriter  writer = new BufferedWriter(fw);
        writer.write("PRODUCT       = ");
        writer.newLine();
        writer.write("LOT           = ");
        writer.write(lotDat.getLotNo());
        writer.newLine();
        writer.write("WAFER ID      = ");
        writer.write(waferDat.getWaferID());
        writer.newLine();
        writer.write("START TIME    = ");
//        writer.write(waferDat.getTestTotal().getLotStartTime());
        writer.newLine();
        writer.write("END TIME      = ");
//        writer.write(waferDat.getTestTotal().getLotEndTime());
        writer.newLine();
        writer.write("X QUANTUM     = ");//TODO
        writer.newLine();
        writer.write("Y QUANTUM     = ");//TODO
        writer.newLine();
        writer.write("FLAT/NOTCH    = ");//TODO
        writer.newLine();
        writer.write("[ WAFER MAP]");
        writer.newLine();

        int lines = waferDat.getMdpData().getNoOfRecords();
        for (int i = 0; i < lines; i++) {
            List<LineData> lineDataList = waferDat.getMdpData().getRecords();
            LineData lineData = lineDataList.get(i);
            int rows = lineData.getNoOfDies();
            for (int j = 0; j < rows; j++) {
                //0-7bin 8result 12margin
            }

        }
        writer.flush();
        writer.close();

        fw.close();

    }
}
