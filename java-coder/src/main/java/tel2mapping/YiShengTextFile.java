package tel2mapping;

import tel2mapping.dat.LotDat;
import tel2mapping.dat.WaferDat;
import tel2mapping.dat.WaferMapData;
import tel2mapping.dat.YiShengData;
import tel2mapping.dat.subentity.BinData;
import tel2mapping.dat.subentity.DieData;
import tel2mapping.dat.subentity.LineData;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * className: YiShengTextFile
 * package: tel2mapping
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/6/30 9:53
 */
public class YiShengTextFile {
    public static void main(String[] args) throws IOException {

        String file = "C:\\Users\\fang\\Desktop\\易冲要求\\TSK\\NB9S14.09\\002.NB9S14.09-2";
//        String file = "D:\\Workspace\\articles\\dev\\c#\\封测TSK需求\\黄文龙tel开发示例\\新建文件夹\\LOT00001-TEL\\LOT1.DAT";

//        LotDat lotDat = new LotDat();
//        lotDat = lotDat.read(file);
        YiShengData yiShengData = new YiShengData();
        yiShengData = yiShengData.read(file);


//        String file2 = "C:\\Users\\fang\\Desktop\\新建文件夹\\LOT00001-TEL\\WAFER011.DAT";
////        String file2 = "D:\\Workspace\\articles\\dev\\c#\\封测TSK需求\\黄文龙tel开发示例\\新建文件夹\\LOT00001-TEL\\WAFER011.DAT";
//        WaferDat waferDat = new WaferDat();
//        waferDat = waferDat.read(file2);


        File os = new File("out.txt");
        Writer fw = new FileWriter(os);
        BufferedWriter writer = new BufferedWriter(fw);

        String binQuanYield;
        String[] split = yiShengData.getWaferId().split("-");
        String waferID = split[1];//last
        binQuanYield = String.format("%-12s%-2s", yiShengData.getLotNo(), waferID);
        writer.write(binQuanYield);
        //ProductName MPW-Code ProductCode TestID OperID TestProgram StartTime
        String startTime = yiShengData.getStartTime();
        binQuanYield = String.format("%-32s%-4s%-6s%-8s%-8s%-30s%-19s", "", "", "", "N/A", "", "", startTime);
        writer.write(binQuanYield);

        String endTime = yiShengData.getEndTime();
        //EndTime ProbleCardID LoadBoardID Bd_File Notch SortID Test_Site Fd_File
        binQuanYield = String.format("%-19s%-12s%-12s%-20s%-1s%-1s%-8s%-20s", endTime, "", "", "Bd_File", yiShengData.getOrientationFlatAngleName(), "1", "JSE", "");

        writer.write(binQuanYield);
        writer.newLine();

        List<WaferMapData> waferMapDataList = yiShengData.getWaferMapDataList();
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
