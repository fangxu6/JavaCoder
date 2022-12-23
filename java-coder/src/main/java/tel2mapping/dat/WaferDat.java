package tel2mapping.dat;

import org.apache.commons.io.EndianUtils;
import tel2mapping.dat.subentity.*;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WaferDat {
    public String getWaferID() {
        return WaferID;
    }

    public void setWaferID(String waferID) {
        WaferID = waferID;
    }

    public String getWaferNo() {
        return WaferNo;
    }

    public void setWaferNo(String waferNo) {
        WaferNo = waferNo;
    }

    public String getSlotNo() {
        return SlotNo;
    }

    public void setSlotNo(String slotNo) {
        SlotNo = slotNo;
    }

    private String WaferID;
    private String WaferNo;
    private char CassetteNo;
    private String SlotNo;
    private char TestCount;
    private TestTotal testTotal;

    private MapData mdpData;

    public TestTotal getTestTotal() {
        return testTotal;
    }

    public void setTestTotal(TestTotal testTotal) {
        this.testTotal = testTotal;
    }

    public MapData getMdpData() {
        return mdpData;
    }

    public void setMdpData(MapData mdpData) {
        this.mdpData = mdpData;
    }



    public WaferDat read(String file) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        byte[] bytes = new byte[200];
        dis.read(bytes,0,25);
        WaferID = new String(bytes).trim();
        dis.read(bytes,0,2);
        WaferNo= new String(bytes).trim();
        CassetteNo=(char)dis.readByte();
        dis.read(bytes,0,2);
        SlotNo= new String(bytes).trim();

        TestCount=(char)dis.readByte();
        TestTotal testTotal= new TestTotal();
        testTotal.setFailTotal(EndianUtils.swapShort(dis.readShort()));
        testTotal.setPassTotal(EndianUtils.swapShort(dis.readShort()));
        testTotal.setTestTotal(EndianUtils.swapShort(dis.readShort()));
        byte[] bytesTime = new byte[12];
        byte[] bytesendTime = new byte[12];
        dis.read(bytesTime,0,12);
        String startTime = bcd2Str(bytesTime);
        dis.read(bytesendTime,0,12);
        String endTime = bcd2Str(bytesendTime);
        setTestTotal(testTotal);

        MapData mapData = new MapData();
        short num = (short) (dis.readByte() & 0xFF);
        mapData.setNoOfRecords(num);
        mapData.setInitialDieDistanceX(EndianUtils.readSwappedUnsignedShort(dis));
        mapData.setInitialDieDistanceY(EndianUtils.readSwappedUnsignedShort(dis));
        List<LineData> records = new ArrayList<>(num);

        int byteCount=66;
        for (int i = 0; i < num; i++) {

            LineData lineData = new LineData();
            lineData.setFirstAddressXOfRecord(EndianUtils.readSwappedUnsignedShort(dis));
            lineData.setFirstAddressYOfRecord(EndianUtils.readSwappedUnsignedShort(dis));
            short numberOfDies = (short) (dis.readByte() & 0xFF);
            lineData.setNoOfDies(numberOfDies);
            List<DieData> dies = new ArrayList<>(numberOfDies);
            byteCount+=(5+numberOfDies*2);
            for (int j = 0; j < numberOfDies; j++) {
                DieData dieData = new DieData();
                dieData.setDieData(EndianUtils.readSwappedUnsignedShort(dis));
                dies.add(dieData);
            }
            lineData.setLines(dies);
            records.add(lineData);
        }
        mapData.setRecords(records);
        setMdpData(mapData);

        System.out.println(byteCount);
        byte b = dis.readByte();


        return this;
    }

    /**
     * @功能: BCD码转为10进制串(阿拉伯数据)
     * @参数: BCD码
     * @结果: 10进制串
     */
    public static String bcd2Str(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
                .toString().substring(1) : temp.toString();
    }
}
