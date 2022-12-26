package tel2mapping.dat.subentity;

import java.util.Date;
import java.util.List;

public class MapData {
    private short NoOfRecords;
    private int  InitialDieDistanceX;
    private int InitialDieDistanceY;
    private List<LineData> records;

    public short getNoOfRecords() {
        return NoOfRecords;
    }

    public void setNoOfRecords(short noOfRecords) {
        NoOfRecords = noOfRecords;
    }

    public int getInitialDieDistanceX() {
        return InitialDieDistanceX;
    }

    public void setInitialDieDistanceX(int initialDieDistanceX) {
        InitialDieDistanceX = initialDieDistanceX;
    }

    public int getInitialDieDistanceY() {
        return InitialDieDistanceY;
    }

    public void setInitialDieDistanceY(int initialDieDistanceY) {
        InitialDieDistanceY = initialDieDistanceY;
    }

    public List<LineData> getRecords() {
        return records;
    }

    public void setRecords(List<LineData> records) {
        this.records = records;
    }

}
