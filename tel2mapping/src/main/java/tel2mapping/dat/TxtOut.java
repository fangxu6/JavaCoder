package tel2mapping.dat;

import tel2mapping.dat.subentity.BinData;
import tel2mapping.dat.subentity.DieData;
import tel2mapping.dat.subentity.LineData;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * className: TxtOut
 * package: tel2mapping.dat
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2024/10/18 15:40
 */
public class TxtOut {
    public void printTxt(LotDat lotDat,WaferDat waferDat,File waferFile) throws IOException {
        String parentDirectory = waferFile.getParent();
        String waferFileOutPut = parentDirectory + "\\" + lotDat.getLotNo()+"-"+waferDat.getWaferID()+".txt";
        File os = new File(waferFileOutPut);
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
        LocalDateTime lotStartTime = waferDat.getTestTotal().getLotStartTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedStartTime = lotStartTime.format(formatter);
        writer.write(formattedStartTime);
        writer.newLine();
        writer.write("END TIME      = ");
        LocalDateTime lotEndTime = waferDat.getTestTotal().getLotEndTime();
        String formattedEndTime = lotEndTime.format(formatter);
        writer.write(formattedEndTime);
        writer.newLine();
        writer.write("X QUANTUM     = ");
        int XQuant = waferDat.getXMaximun() - waferDat.getXMinimin();
        writer.write(String.valueOf(XQuant));
        writer.newLine();
        writer.write("Y QUANTUM     = ");
        writer.write(String.valueOf(waferDat.getMapData().getNoOfRecords()));
        writer.newLine();
        writer.write("FLAT/NOTCH    = ");
        writer.write(lotDat.getOrientationFlatAngle());

        writer.newLine();
        writer.newLine();

        writer.write("[ WAFER MAP]");
        writer.newLine();

        int lines = waferDat.getMapData().getNoOfRecords();
        for (int i = 0; i < lines; i++) {
            List<LineData> lineDataList = waferDat.getMapData().getRecords();
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

        Map<Integer, Integer> map = waferDat.getMapData().getBinMap();
        String binQuanYield;
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//            Integer key = entry.getKey();
//            double binYield = ((double) entry.getValue()) / waferDat.getTestTotal().getTestTotal() * 100;
//
//
//            String PFFlag = "F";
//            if ((key >> 8 & 0x01) == 1) {
//                PFFlag = "F";
//            } else {
//                PFFlag = "P";
//            }
//            binQuanYield = String.format("BIN %3d = %6d %6.2f %3s", entry.getKey(), entry.getValue(), binYield, PFFlag);
//            writer.write(binQuanYield);
//            writer.newLine();
//        }
        List<BinData> binList = waferDat.getMapData().getBinData();
        for (BinData bindata : binList) {
            Integer binNum = Integer.valueOf(bindata.getBinBytes());
            double binYield = ((double) bindata.getBinNum()) / waferDat.getTestTotal().getTestTotal() * 100;


            String PFFlag = bindata.getDieAttribute();
            binQuanYield = String.format("BIN %3d = %6d %6.2f %3s", binNum, bindata.getBinNum(), binYield, PFFlag);
            writer.write(binQuanYield);
            writer.newLine();

        }


        writer.write("========================================================");
        writer.newLine();

        writer.write("PassDie = ");
        short passDie = waferDat.getTestTotal().getPassTotal();
        short failDie = waferDat.getTestTotal().getFailTotal();
        short totalDie = waferDat.getTestTotal().getTestTotal();
        double passYield = ((double) passDie) / totalDie * 100;
        double failYield = ((double) failDie) / totalDie * 100;

        String passQuanYield = String.format("%6d %6.2f", passDie, passYield);
        writer.write(passQuanYield);
        writer.newLine();
        writer.write("FailDie = ");
        String failQuanYield = String.format("%6d %6.2f", failDie, failYield);
        writer.write(failQuanYield);

        writer.newLine();
        writer.write("TotalDie= ");
        String totalQuanYield = String.format("%6d", totalDie);
        writer.write(totalQuanYield);

        writer.newLine();

        writer.flush();
        writer.close();

        fw.close();    }
}
