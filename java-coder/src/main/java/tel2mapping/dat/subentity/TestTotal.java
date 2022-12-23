package tel2mapping.dat.subentity;

import java.util.Date;

public class TestTotal {
    private short PassTotal;

    public short getPassTotal() {
        return PassTotal;
    }

    public void setPassTotal(short passTotal) {
        PassTotal = passTotal;
    }

    public short getFailTotal() {
        return FailTotal;
    }

    public void setFailTotal(short failTotal) {
        FailTotal = failTotal;
    }

    public short getTestTotal() {
        return TestTotal;
    }

    public void setTestTotal(short testTotal) {
        TestTotal = testTotal;
    }

    public Date getLotStartTime() {
        return LotStartTime;
    }

    public void setLotStartTime(Date lotStartTime) {
        LotStartTime = lotStartTime;
    }

    public Date getLotEndTime() {
        return LotEndTime;
    }

    public void setLotEndTime(Date lotEndTime) {
        LotEndTime = lotEndTime;
    }

    private short FailTotal;
    private short TestTotal;
    private Date LotStartTime;
    private Date LotEndTime;
}
