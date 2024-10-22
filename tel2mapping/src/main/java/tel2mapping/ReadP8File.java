package tel2mapping;

import tel2mapping.dat.LotDat;
import tel2mapping.dat.TxtOut;
import tel2mapping.dat.WaferDat;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadP8File {
    public static void main(String[] args) throws IOException {


//        String file = "C:\\Users\\fangx\\Desktop\\TEL\\转换前（原始MAP）\\转换前（原始MAP）\\F2383\\LOT1.DAT";
////        String file = "D:\\Workspace\\articles\\dev\\c#\\封测TSK需求\\黄文龙tel开发示例\\新建文件夹\\LOT00001-TEL\\LOT1.DAT";
//
//        LotDat lotDat = new LotDat();
//        lotDat = lotDat.read(file);
//
//
//        String file2 = "C:\\Users\\fangx\\Desktop\\TEL\\转换
//        前（原始MAP）\\转换前（原始MAP）\\F2383\\WAFER011.DAT";
////        String file2 = "D:\\Workspace\\articles\\dev\\c#\\封测TSK需求\\黄文龙tel开发示例\\新建文件夹\\LOT00001-TEL\\WAFER011.DAT";
//        WaferDat waferDat = new WaferDat();
//        waferDat = waferDat.read(file2);
//        TxtOut txtOut = new TxtOut();
//        txtOut.printTxt(lotDat, waferDat);

        String directoryPath = "C:\\Users\\fangx\\Desktop\\TEL\\转换前（原始MAP）\\转换前（原始MAP）\\F2383";
        File directory = new File(directoryPath);

        if (directory.isDirectory()) {
            File[] lotFiles = directory.listFiles((dir, name) ->
                    name.toLowerCase().startsWith("lot") && name.toLowerCase().endsWith(".dat"));

            if (lotFiles != null) {
                for (File lotFile : lotFiles) {
                    try {
                        // Process the LOT file
                        LotDat lotDat = processLotFile(lotFile);

                        // Extract the number from the LOT file name
                        String lotFileName = lotFile.getName();
                        Pattern pattern = Pattern.compile("lot(\\d+)\\.dat", Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(lotFileName);
                        if (matcher.find()) {
                            String number = matcher.group(1);

                            // Find and process corresponding WAFER*<number>.dat files
                            File[] waferFiles = directory.listFiles((dir, name) ->
                                    name.toLowerCase().matches("wafer.*" + number + "\\.dat"));
                            if (waferFiles != null) {
                                for (File waferFile : waferFiles) {
                                    WaferDat waferDat = processWaferFile(waferFile);
                                    TxtOut txtOut = new TxtOut();
                                    txtOut.printTxt(lotDat, waferDat, waferFile);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static LotDat processLotFile(File file) throws IOException {
        System.out.println("Processing LOT file: " + file.getName());
        LotDat lotDat = new LotDat();
        lotDat = lotDat.read(file.getAbsolutePath());
        // Add further processing logic as needed
        return lotDat;
    }

    private static WaferDat processWaferFile(File file) throws IOException {
        System.out.println("Processing WAFER file: " + file.getName());
        WaferDat waferDat = new WaferDat();
        waferDat = waferDat.read(file.getAbsolutePath());
        // Add further processing logic as needed
        return waferDat;
    }

}
