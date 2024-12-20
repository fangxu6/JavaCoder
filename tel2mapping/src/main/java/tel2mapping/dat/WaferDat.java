package tel2mapping.dat;

import org.apache.commons.io.EndianUtils;
import org.apache.commons.lang.StringUtils;
import tel2mapping.dat.subentity.*;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class WaferDat {


    private String WaferID;
    private String WaferNo;
    private char CassetteNo;
    private String SlotNo;
    private char TestCount;
    private TestTotal testTotal;

    private MapData mapData;
    private int XMaximun;
    private int XMinimin;

    public TestTotal getTestTotal() {
        return testTotal;
    }

    public void setTestTotal(TestTotal testTotal) {
        this.testTotal = testTotal;
    }

    public MapData getMapData() {
        return mapData;
    }

    public void setMapData(MapData mdpData) {
        this.mapData = mdpData;
    }

    public String getWaferID() {
        return WaferID;
    }


    public int getXMaximun() {
        return XMaximun;
    }


    public int getXMinimin() {
        return XMinimin;
    }


    public WaferDat read(String file) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        byte[] bytes = new byte[200];
        dis.read(bytes, 0, 25);
        WaferID = new String(bytes).trim();
        dis.read(bytes, 0, 2);
        WaferNo = new String(bytes).trim();
        if (StringUtils.isBlank(WaferID)) {
            WaferID = WaferNo;
        }
        CassetteNo = (char) dis.readByte();
        dis.read(bytes, 0, 2);
        SlotNo = new String(bytes).trim();

        TestCount = (char) dis.readByte();
        TestTotal testTotal = new TestTotal();
        testTotal.setPassTotal(EndianUtils.swapShort(dis.readShort()));
        testTotal.setFailTotal(EndianUtils.swapShort(dis.readShort()));
        testTotal.setTestTotal(EndianUtils.swapShort(dis.readShort()));
        byte[] bytesTime = new byte[12];
        byte[] bytesendTime = new byte[12];

        dis.read(bytesTime, 0, 12);
        LocalDateTime startTime = ReadToDate(bytesTime);
        dis.read(bytesendTime, 0, 12);
        LocalDateTime endTime = ReadToDate(bytesendTime);
        testTotal.setLotStartTime(startTime);
        testTotal.setLotEndTime(endTime);
        setTestTotal(testTotal);

        MapData mapData = new MapData();
        short lineNumber = (short) (dis.readByte() & 0xFF);
        mapData.setNoOfRecords(lineNumber);
        mapData.setInitialDieDistanceX(EndianUtils.readSwappedUnsignedShort(dis));
        mapData.setInitialDieDistanceY(EndianUtils.readSwappedUnsignedShort(dis));
        List<LineData> records = new ArrayList<>(lineNumber);

        XMinimin = Integer.MAX_VALUE;
        XMaximun = Integer.MIN_VALUE;
//        BinData binData = new
        Map<Integer, Integer> binMap = new HashMap<>();
        for (int i = 0; i < lineNumber; i++) {
            LineData lineData = new LineData();
            int X = EndianUtils.readSwappedShort(dis);
            int Y = EndianUtils.readSwappedShort(dis);

            lineData.setFirstAddressXOfRecord(X);
            lineData.setFirstAddressYOfRecord(Y);
            short numberOfDies = (short) (dis.readByte() & 0xFF);
            XMinimin = Math.min(XMinimin, X);
            XMaximun = Math.max(X + numberOfDies - 1, XMaximun);
            lineData.setNoOfDies(numberOfDies);
            List<DieData> lineDies = new ArrayList<>(numberOfDies);
            for (int j = 0; j < numberOfDies; j++) {
                DieData dieData = new DieData();
                int binInt = EndianUtils.readSwappedUnsignedShort(dis);
                dieData.setDieData(binInt);
                byte binByte = (byte) (binInt & 0xFF);
                char lowInt = (char) (binByte + 48);

                if (lowInt > 57) {
                    lowInt += 8;//跳过9-A之间的字符
                }
                if ((binInt >> 8 & 0x01) == 1) {
                    dieData.setPassOrFail("F");
                } else {
                    dieData.setPassOrFail("P");
                }
                binMap.put(binInt, binMap.getOrDefault(binInt, 0) + 1);
                String ch = String.valueOf(lowInt);
                if (binByte == 0) {
                    ch = ".";
                }
                dieData.setBin(ch);

                lineDies.add(dieData);
            }
            lineData.setLines(lineDies);
            records.add(lineData);
        }

        List<BinData> binList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : binMap.entrySet()) {
            byte binNo = (byte) (entry.getKey() & 0xFF);
            if (binNo == 0x00) {
                continue;
            } else {
                BinData binData = new BinData(binNo, entry.getValue());
                if ((entry.getKey() >> 8 & 0x01) == 1) {
                    binData.setDieAttribute("F");
                } else {
                    binData.setDieAttribute("P");
                }
                binList.add(binData);
            }
        }

        mapData.setRecords(records);
        mapData.setBinMap(binMap);
        mapData.setBinData(binList);
        setMapData(mapData);


        byte b = dis.readByte();


        return this;
    }

    /**
     * @功能: BCD码转为10进制串(阿拉伯数据)
     * @参数: BCD码
     * @结果: 10进制串
     */
    public static String bcd2Str(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) (bytes[i] & 0xff));
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
                .toString().substring(1) : temp.toString();
    }

    LocalDateTime ReadToDate(byte[] bytes) throws IOException {

        byte[] yearBytes = new byte[2];
        byte[] monthBytes = new byte[2];
        byte[] dayBytes = new byte[2];
        byte[] hourBytes = new byte[2];
        byte[] minBytes = new byte[2];
        byte[] secondBytes = new byte[2];
        System.arraycopy(bytes, 0, yearBytes, 0, 2);
        System.arraycopy(bytes, 2, monthBytes, 0, 2);
        System.arraycopy(bytes, 4, dayBytes, 0, 2);
        System.arraycopy(bytes, 6, hourBytes, 0, 2);
        System.arraycopy(bytes, 8, minBytes, 0, 2);
        System.arraycopy(bytes, 10, secondBytes, 0, 2);


        int year = 2000 + Integer.valueOf(bcd2Str(yearBytes));
        int month = Integer.valueOf(bcd2Str(monthBytes));
        Integer day = Integer.valueOf(bcd2Str(dayBytes));
        Integer hour = Integer.valueOf(bcd2Str(hourBytes));
        Integer min = Integer.valueOf(bcd2Str(minBytes));
        Integer sec = Integer.valueOf(bcd2Str(secondBytes));
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, min, sec);


//        year = 2000 + dis.read(bytes,0,2);;
//        month = dis.read(bytes,0,2);;
//        day = dis.read(bytes,0,2);;
//        hour = dis.read(bytes,0,2);;
//        min = dis.read(bytes,0,2);;

        // reserved
//        dis.read(bytes,0,2);

        return localDateTime;
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
