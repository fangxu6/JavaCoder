package tel2mapping.dat;

import tel2mapping.dat.subentity.CassetteInformation;
import tel2mapping.dat.subentity.TestTotal;

import java.util.Date;
import java.util.List;

public class LotDat {
    private String LotNo;
    private String CardNo;
    private String OperatorName;
    private String MachineNo;
    private int WaferNoList;

    private String WaferName;
    private String WaferSize;
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
}
