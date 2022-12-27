package tel2mapping;

import tel2mapping.dat.LotDat;
import tel2mapping.dat.WaferDat;
import tel2mapping.dat.subentity.DieData;
import tel2mapping.dat.subentity.LineData;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReadP8File {
    public static void main(String[] args) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream("D:\\workspace\\articles\\dev\\c#\\封测TSK需求\\黄文龙tel开发示例\\新建文件夹\\LOT00001-TEL\\LOT1.DAT"));
        byte[] a = new byte[368];
        byte[] b = new byte[3];
        byte[] c = new byte[3];
        byte[] d = new byte[3];
        dis.skipBytes(368);
        dis.read(b, 0, b.length);//98 -51 1
        dis.read(c);//18 2 0
        dis.read(d);//116 -49 1

        String file = "D:\\Workspace\\articles\\dev\\c#\\封测TSK需求\\黄文龙tel开发示例\\新建文件夹\\LOT00001-TEL\\LOT1.DAT";
        LotDat lotDat = new LotDat();
        lotDat = lotDat.read(file);


        String file2 = "D:\\Workspace\\articles\\dev\\c#\\封测TSK需求\\黄文龙tel开发示例\\新建文件夹\\LOT00001-TEL\\WAFER011.DAT";
        WaferDat waferDat = new WaferDat();
        waferDat = waferDat.read(file2);


        File os = new File("out.txt");
        Writer fw = new FileWriter(os);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write("PRODUCT       = ");
        writer.write(lotDat.getWaferName());
        writer.newLine();
        writer.write("LOT           = ");
        writer.write(lotDat.getLotNo());
        writer.newLine();
        writer.write("WAFER ID      = ");
        writer.write(waferDat.getWaferID());
        writer.newLine();
        writer.write("START TIME    = ");
        writer.write(waferDat.getTestTotal().getLotStartTime().toString());
        writer.newLine();
        writer.write("END TIME      = ");
        writer.write(waferDat.getTestTotal().getLotEndTime().toString());
        writer.newLine();
        writer.write("X QUANTUM     = ");
        int XQuant = waferDat.getXMaximun() - waferDat.getXMinimin();
        writer.write(String.valueOf(XQuant));
        writer.newLine();
        writer.write("Y QUANTUM     = ");
        writer.write(String.valueOf(waferDat.getMdpData().getNoOfRecords()));
        writer.newLine();
        writer.write("FLAT/NOTCH    = ");//TODO
        writer.write(lotDat.getOrientationFlatAngle());

        writer.newLine();
        writer.newLine();

        writer.write("[ WAFER MAP]");
        writer.newLine();

        int lines = waferDat.getMdpData().getNoOfRecords();
        for (int i = 0; i < lines; i++) {
            List<LineData> lineDataList = waferDat.getMdpData().getRecords();
            LineData lineData = lineDataList.get(i);
            int rows = lineData.getNoOfDies();
//            writer.write(String.valueOf(rows)+"(");
//            writer.write(lineData.getFirstAddressXOfRecord()+",");
//            writer.write(lineData.getFirstAddressYOfRecord()+") ");

            int xmin = waferDat.getXMinimin();
            int xmax = waferDat.getXMaximun();
            int firstAddressXOfRecord = lineData.getFirstAddressXOfRecord();
            for (int j = xmin, k = 0; j <= xmax; j++) {
                //0-7bin 8result 12margin
//                List<DieData> dataLine = lineData.getLines();
                if (j < firstAddressXOfRecord || k >= rows) {
                    writer.write(".");
                } else {
                    DieData dieData = lineData.getLines().get(k);
                    writer.write(dieData.getBin());
                    k++;
                }

            }
            writer.newLine();

        }
        writer.newLine();

        writer.write("[ BIN SUMMARY]");
        writer.newLine();
        writer.write("BIN No.    Quan. Yield%   P/F  Bin Description");
        writer.newLine();
        writer.write("========================================================");
        writer.newLine();
        for (int i = 0; i < 10; i++) {
            writer.write("BIN   " + i + " =");//TODO Quan Yield P/F
            writer.newLine();
        }



        writer.write("========================================================");
        writer.newLine();

        writer.write("PassDie = ");
        short passDie = waferDat.getTestTotal().getPassTotal();
        short failDie = waferDat.getTestTotal().getFailTotal();
        short totalDie = waferDat.getTestTotal().getTestTotal();
        double passYield = ((double)passDie)/totalDie*100;
        double failYield = ((double)failDie)/totalDie*100;

        String passQuanYield = String.format("%6d %6.2f",passDie,passYield);
        writer.write(passQuanYield);
        writer.newLine();
        writer.write("FailDie = ");
        String failQuanYield = String.format("%6d %6.2f",failDie,failYield);
        writer.write(failQuanYield);

        writer.newLine();
        writer.write("TotalDie= ");
        String totalQuanYield = String.format("%6d",totalDie);
        writer.write(totalQuanYield);

        writer.newLine();

        writer.flush();
        writer.close();

        fw.close();

    }
}
