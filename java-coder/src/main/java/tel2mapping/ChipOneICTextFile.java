package tel2mapping;

import tel2mapping.dat.TSKData;
import tel2mapping.dat.WaferMapData;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * className: ChipOneICTextFile
 * package: tel2mapping
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/7/21 14:53
 */
public class ChipOneICTextFile {
    public static void main(String[] args) throws IOException {

        String file = "D:\\D09H17\\D09H17-01_3380P_D09H17_2023MAY19201059_3380P-ATE-1005_1_1_1_ICNT8901WAA_1_1_030.0_1_1_sumryTDO.txt";
//        String file = "D:\\Workspace\\articles\\dev\\c#\\封测TSK需求\\黄文龙tel开发示例\\新建文件夹\\LOT00001-TEL\\LOT1.DAT";

//        LotDat lotDat = new LotDat();
//        lotDat = lotDat.read(file);
        TSKData yiChongData = new TSKData();
        yiChongData = yiChongData.read(file);


//        String file2 = "C:\\Users\\fang\\Desktop\\新建文件夹\\LOT00001-TEL\\WAFER011.DAT";
////        String file2 = "D:\\Workspace\\articles\\dev\\c#\\封测TSK需求\\黄文龙tel开发示例\\新建文件夹\\LOT00001-TEL\\WAFER011.DAT";
//        WaferDat waferDat = new WaferDat();
//        waferDat = waferDat.read(file2);


        File os = new File("out.txt");
        Writer fw = new FileWriter(os);
        BufferedWriter writer = new BufferedWriter(fw);

        String binQuanYield;
        String[] split = yiChongData.getWaferId().split("-");
        String waferID = split[1];//last
        binQuanYield = String.format("%-12s%-2s", yiChongData.getLotNo(), waferID);
        writer.write(binQuanYield);
        //ProductName MPW-Code ProductCode TestID OperID TestProgram StartTime
        String startTime = yiChongData.getStartTime();
        binQuanYield = String.format("%-32s%-4s%-6s%-8s%-8s%-30s%-19s", "", "", "", "N/A", "", "", startTime);
        writer.write(binQuanYield);

        String endTime = yiChongData.getEndTime();
        //EndTime ProbleCardID LoadBoardID Bd_File Notch SortID Test_Site Fd_File
        binQuanYield = String.format("%-19s%-12s%-12s%-20s%-1s%-1s%-8s%-20s", endTime, "", "", "Bd_File", yiChongData.getOrientationFlatAngleName(), "1", "JSE", "");

        writer.write(binQuanYield);
        writer.newLine();

        List<WaferMapData> waferMapDataList = yiChongData.getWaferMapDataList();
        for (int i = 0; i < waferMapDataList.size(); i++) {
            WaferMapData waferMapData = waferMapDataList.get(i);
            int addressXOfRecord = waferMapData.getAddressXOfRecord();
            binQuanYield = String.format("%4d%4d%4d%4d", waferMapData.getAddressXOfRecord(), waferMapData.getAddressYOfRecord(), waferMapData.getBin(), waferMapData.getVisualInspection());
            writer.write(binQuanYield);
            if (i < waferMapDataList.size())
                writer.newLine();
        }

        writer.flush();
        writer.close();

        fw.close();

    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<Integer, Integer> sortMapByKey(Map<Integer, Integer> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
//        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
        Map<Integer, Integer> sortMap = new TreeMap<Integer, Integer>(new Comparator<Integer>() {
            public int compare(Integer obj1, Integer obj2) {
                return obj1.compareTo(obj2);//升序排序
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }
}
