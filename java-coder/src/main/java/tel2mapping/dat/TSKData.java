package tel2mapping.dat;

import tel2mapping.dat.subentity.CassetteInformation;
import tel2mapping.dat.subentity.TestTotal;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * className: TSKData
 * package: tel2mapping.dat
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/6/30 10:23
 */
public class TSKData {
    private String operatorName;//0
    private String deviceName;//20
    private Short waferSize;//36 2
    private String machineNo;//38 2
    private Integer XIndexingSize;//40 4
    private Integer YIndexingSize;//44 4
    private Short orientationFlatAngle;//48 2
    private String orientationFlatAngleName;

    private byte finalEditingMachineType;//50 1
    private byte mapVersion;//51 1
    private Short mapDataAreaRowSize; //52 2
    private Short mapDataAreaLineSize;// 54 2
    private String mapDataForm;// 56 4

    private Short totalTestedDice;
    private Short totalPassDice;

    public Short getTotalTestedDice() {
        return totalTestedDice;
    }

    public Short getTotalPassDice() {
        return totalPassDice;
    }

    public Short getTotalFailDice() {
        return totalFailDice;
    }

    private Short totalFailDice;

    public List<WaferMapData> getWaferMapDataList() {
        return waferMapDataList;
    }

    private List<WaferMapData> waferMapDataList = new ArrayList<>();

    public String getWaferId() {
        return waferId;
    }

    private String waferId;// 60 21
    private String nemberOfProbing;// 81 1
    private String lotNo;//82 18  need
    private String cassetteNo;//100 2
    private String slotNo;//102 2
    private String XCoordinatesIncreaseDirection;//104 1
    private String YCoordinatesIncreaseDirection;//105 1

    private String startYear;//148 2
    private String startMonth;//150 2
    private String startDay;//152 2
    private String startHour;//154 2
    private String startMinute;//156 2
    private String startTimeReserved;//158 2

    private String endYear;//160 2
    private String endMonth;//162 2
    private String endDay;//164 2
    private String endHour;//166 2
    private String endMinute;//168 2
    private String endTimeReserved;//170 2


    private String CardNo;
    private String OperatorName;

    private int WaferNoList;

    private String WaferName;


    private String Alignment;

    private String ProbeSize;

    private String TargetMode;
    private String TargetPositionX;
    private String TargetPositionY;
    private String TargetReserved;
    private String ReferenceDieX;
    private String ReferenceDieY;
    private String TargetReserved2;

    private String OrientationFlat;

    private String ProbeAreaSelection;

    private String InkerOffset;

    private String Sample;//1+60+1=1=4=4

    private String Multi;//1+2

    private String ConsecutiveFail;

    private String NeedlePolish;

    private String HotChuckTemperature;

    private String PresetAddressX;
    private String PresetAddressY;

    private String MarkingMode;

    private String Reserved;

    private TestTotal testTotal;

    private List<CassetteInformation> cassetteInformationList;
    private String cassetteInformation;
    private String ProberModelInformation;

    public String getLotNo() {
        return lotNo;
    }

    public String getStartTime() {
        return String.join("-", "20" + startYear, startMonth, startDay) + " " + String.join(":", startHour, startMinute, "00");
    }

    public String getEndTime() {
        return String.join("-", "20" + endYear, endMonth, endDay) + " " + String.join(":", endHour, endMinute, "00");
    }

    public String getWaferName() {
        return WaferName;
    }

    public Short getOrientationFlatAngle() {
        return orientationFlatAngle;
    }

    public String getOrientationFlatAngleName() {
        return orientationFlatAngleName;
    }

    public TSKData read(String file) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        byte[] bytes = new byte[200];
        //Problem 如果前面bytes实际值比较大，会代入到下面的bytes中
        dis.read(bytes, 0, 20);
        operatorName = new String(bytes).substring(0, 20).trim();
        dis.read(bytes, 0, 16);
        deviceName = new String(bytes).substring(0, 16).trim();

        waferSize = dis.readShort();

        dis.read(bytes, 0, 2);
        machineNo = new String(bytes).substring(0, 2).trim();
        XIndexingSize = dis.readInt();
        YIndexingSize = dis.readInt();

        orientationFlatAngle = dis.readShort();


        OrientatioFlatAngleEnum orientatioFlatAngleEnum = OrientatioFlatAngleEnum.getByOrientatioFlatAngle(Integer.valueOf(orientationFlatAngle));

        orientationFlatAngleName = orientatioFlatAngleEnum.name();

        finalEditingMachineType = dis.readByte();
        mapVersion = dis.readByte();

        mapDataAreaRowSize = (short) dis.readUnsignedShort();// 记录行数
        mapDataAreaLineSize = (short) dis.readUnsignedShort();// 记录列数

        dis.skipBytes(4);
        dis.read(bytes, 0, 21);
        waferId = new String(bytes).substring(0, 21).trim();
        dis.read(bytes, 0, 1);
        nemberOfProbing = new String(bytes).substring(0, 1).trim();
        dis.read(bytes, 0, 18);
        lotNo = new String(bytes).substring(0, 18).trim();


        dis.skipBytes(48);
        dis.read(bytes, 0, 2);
        startYear = new String(bytes).substring(0, 2).trim();
        dis.read(bytes, 0, 2);
        startMonth = new String(bytes).substring(0, 2).trim();
        dis.read(bytes, 0, 2);
        startDay = new String(bytes).substring(0, 2).trim();
        dis.read(bytes, 0, 2);
        startHour = new String(bytes).substring(0, 2).trim();
        dis.read(bytes, 0, 2);
        startMinute = new String(bytes).substring(0, 2).trim();
        dis.read(bytes, 0, 2);
        startTimeReserved = new String(bytes).substring(0, 2).trim();

        dis.read(bytes, 0, 2);
        endYear = new String(bytes).substring(0, 2).trim();
        dis.read(bytes, 0, 2);
        endMonth = new String(bytes).substring(0, 2).trim();
        dis.read(bytes, 0, 2);
        endDay = new String(bytes).substring(0, 2).trim();
        dis.read(bytes, 0, 2);
        endHour = new String(bytes).substring(0, 2).trim();
        dis.read(bytes, 0, 2);
        endMinute = new String(bytes).substring(0, 2).trim();
        dis.read(bytes, 0, 2);
        endTimeReserved = new String(bytes).substring(0, 2).trim();

        //172 210
        dis.skipBytes(38);
        short totalTestedDice = dis.readShort();
        short totalPassDice = dis.readShort();
        short totalFailDice = dis.readShort();
        //216 236
        dis.skipBytes(20);//64

        int sumDie = mapDataAreaRowSize * mapDataAreaLineSize;
        for (int i = 0; i < sumDie; i++) {
            //FirstWord
            byte byte1 = dis.readByte();
            byte byte2 = dis.readByte();
            int XCord = (byte1 & 0x01) << 8 | byte2 & 0xff;
            int dieTestResult = (byte1 >> 6) & 0x03;


//        short dieTestResult = dis.readShort();
//        byte marking = dis.readByte();
//        byte failMarkingInspection = dis.readByte();
//        short reProbingResult = dis.readShort();
//        byte needMarkingInspectionResult= dis.readByte();

            //SecondWord
            byte byte3 = dis.readByte();
            byte byte4 = dis.readByte();
            int YCord = (byte1 & 0x01) << 8 | (byte4 & 0xff);
            //ThirdWord
            byte byte5 = dis.readByte();
            byte byte6 = dis.readByte();
            int bin = byte6 & 0x3f;
            if (dieTestResult == 0) {
                continue;
            }
            int visualInspection = 0;
            if (bin >= 1) {
                visualInspection = 1;
            }
            WaferMapData waferMapData = new WaferMapData(XCord, YCord, bin, visualInspection);

            waferMapDataList.add(waferMapData);
        }


        return this;
    }
}
