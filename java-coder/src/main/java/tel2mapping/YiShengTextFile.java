package tel2mapping;

import tel2mapping.dat.WaferMapData;
import tel2mapping.dat.YiShengData;

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

        String fileNmae = "D:\\jse\\文件归档\\易冲要求\\TSK\\002.H4KA50-02B7";

        YiShengData yiShengData = new YiShengData();
        yiShengData = yiShengData.read(fileNmae);



        File file = new File(fileNmae);
        String[] split = yiShengData.getWaferId().split("-");
        String waferID = split[1].substring(0,2);
        int id = Integer.parseInt(waferID);
        String idString = String.format("%02d", id);

        File os = new File(file.getParent() + File.separator + yiShengData.getLotNo() + "-2-" + idString + ".txt");
        Writer fw = new FileWriter(os);
        BufferedWriter writer = new BufferedWriter(fw);

        String binQuanYield;

        binQuanYield = String.format("%-12s%2s", yiShengData.getLotNo(), idString);
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
        //获取x和y轴的最小值，用于格式化x和y轴
        int xMin = Integer.MAX_VALUE;
        int yMin = Integer.MAX_VALUE;
        for (int i = 0; i < waferMapDataList.size(); i++) {
            WaferMapData waferMapData = waferMapDataList.get(i);
            if (xMin > waferMapData.getAddressXOfRecord()) {
                xMin = waferMapData.getAddressXOfRecord();
            }
            if (yMin > waferMapData.getAddressYOfRecord()) {
                yMin = waferMapData.getAddressYOfRecord();
            }
        }
        for (int i = 0; i < waferMapDataList.size(); i++) {
            WaferMapData waferMapData = waferMapDataList.get(i);
            binQuanYield = String.format("%4d%4d%4d%4d", waferMapData.getAddressXOfRecord() - xMin, waferMapData.getAddressYOfRecord() - yMin,
                    waferMapData.getBin(), waferMapData.getVisualInspection());
            writer.write(binQuanYield);
            if (i < waferMapDataList.size()) {
                writer.newLine();
            }
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
            @Override
            public int compare(Integer obj1, Integer obj2) {
                return obj1.compareTo(obj2);//升序排序
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }
}
