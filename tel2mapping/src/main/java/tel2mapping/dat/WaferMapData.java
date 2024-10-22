package tel2mapping.dat;

/**
 * className: WaferMapDate
 * package: tel2mapping.dat
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/6/30 14:32
 */
public class WaferMapData {
    private int addressXOfRecord;
    private int addressYOfRecord;
    private int bin;
    //0-pass 1-fail
    private int visualInspection;

    public int getAddressXOfRecord() {
        return addressXOfRecord;
    }

    public int getAddressYOfRecord() {
        return addressYOfRecord;
    }

    public int getBin() {
        return bin;
    }

    public int getVisualInspection() {
        return visualInspection;
    }

    public WaferMapData(int addressXOfRecord, int addressYOfRecord, int bin, int visualInspection) {
        this.addressXOfRecord = addressXOfRecord;
        this.addressYOfRecord = addressYOfRecord;
        this.bin = bin;
        this.visualInspection = visualInspection;
    }
}
