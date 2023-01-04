package tel2mapping.dat.subentity;

public class BinData {
    byte[] binBytes;
    Long binNum;
    String dieAttribute;

    public BinData(byte[] binBytes, Long binNum) {
        this.binBytes = binBytes;
        this.binNum = binNum;
    }

    public void increasedByBinByte(byte[] binBytes) {
        if (this.binBytes[0] == binBytes[0]) {
            binNum += 1;
        }
    }

    public String getBinAttribute(byte[] binBytes) {
        if ((binBytes[1]&0x01)==1){
            dieAttribute = "P";
        } else {
            dieAttribute = "F";
        }
        return dieAttribute;
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
