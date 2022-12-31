package tel2mapping.dat.subentity;

public class BinData {
    byte[] binBytes;
    Long binNum;
    String dieAttribute;

    //TODO 初始化
    public void increasedByBinByte(byte[] binBytes) {
        if (this.binBytes[0] == binBytes[0]) {
            binNum += 1;
        }
    }

    public String getBinAttribute(byte[] binBytes) {
        //TODO  if else
        return null;
    }

}


//    mappingData
//            binData
//
//    second wafer
//1完整走完
//for(i)
//    failBin=>otherBin
//2非完整走完
//	0 pass
//    failBin=>otherBin
//	1 =>Bin1
//}
