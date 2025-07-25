package tsk;

import java.io.IOException;

public interface IMappingFile extends IConverter {
    DieMatrix getDieMatrix();
    String getWaferID();
    void setWaferID(String waferID);
    String getLotNo();
    void setLotNo(String lotNo);
    boolean isEmptyDie(DieData die);
    IMappingFile merge(IMappingFile map, String newfile) throws Exception;
}
