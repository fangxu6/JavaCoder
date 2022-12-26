package tel2mapping.dat;

import tel2mapping.dat.subentity.CassetteInformation;
import tel2mapping.dat.subentity.TestTotal;

import java.io.*;
import java.util.Date;
import java.util.List;

public class LotDat {
    public String getLotNo() {
        return LotNo;
    }

    public String getCardNo() {
        return CardNo;
    }

    public String getOperatorName() {
        return OperatorName;
    }

    public String getMachineNo() {
        return MachineNo;
    }

    public int getWaferNoList() {
        return WaferNoList;
    }

    public String getWaferName() {
        return WaferName;
    }

    private String LotNo;
    private String CardNo;
    private String OperatorName;
    private String MachineNo;
    private int WaferNoList;

    private String WaferName;
    private byte WaferSize;
    private String OrientationFlatAngle;
    private String XIndexingSize;
    private String YIndexingSize;

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

    public LotDat read(String file) throws IOException {
        DataInputStream dis = new DataInputStream(new FileInputStream("D:\\workspace\\articles\\dev\\c#\\封测TSK需求\\黄文龙tel开发示例\\新建文件夹\\LOT00001-TEL\\LOT1.DAT"));
        byte[] bytes = new byte[200];
        //Problem 如果前面bytes实际值比较大，会代入到下面的bytes中
        dis.read(bytes, 0, 25);
        LotNo = new String(bytes).substring(0,25).trim();
        dis.read(bytes, 0, 5);
        CardNo = new String(bytes).substring(0,5).trim();
        dis.read(bytes, 0, 5);
        OperatorName = new String(bytes).trim();
        dis.read(bytes, 0, 3);
        MachineNo = new String(bytes).trim();

        dis.skipBytes(50 * 2);
        dis.read(bytes, 0, 12);
        WaferName = new String(bytes).trim();

        WaferSize = dis.readByte();
        dis.read(bytes, 0, 3);
        OrientationFlatAngle = new String(bytes).substring(0,3).trim();
        OrientatioFlatAngleEnum orientatioFlatAngleEnum = OrientatioFlatAngleEnum.getByOrientatioFlatAngle(Integer.valueOf(OrientationFlatAngle));
        OrientationFlatAngle = OrientationFlatAngle +"   "+orientatioFlatAngleEnum.name();


        return this;
    }
}
