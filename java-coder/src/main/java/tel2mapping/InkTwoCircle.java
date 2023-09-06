package tel2mapping;

import lombok.extern.slf4j.Slf4j;
import tel2mapping.dat.TSKData;
import tel2mapping.dat.WaferMapData;

import java.io.*;
import java.util.List;

/**
 * className: InkTwoCircle
 * package: tel2mapping
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/8/29 21:05
 */
@Slf4j
public class InkTwoCircle {
    public static void main(String[] args) throws IOException {
        String file = "D:\\data\\TSK\\NB9S14.09\\001.NB9S14.09-1";
//        String file = "D:\\Workspace\\articles\\dev\\c#\\封测TSK需求\\黄文龙tel开发示例\\新建文件夹\\LOT00001-TEL\\LOT1.DAT";

//        LotDat lotDat = new LotDat();
//        lotDat = lotDat.read(file);
        TSKData originalData = new TSKData();
        originalData = originalData.read(file);


    }
}
