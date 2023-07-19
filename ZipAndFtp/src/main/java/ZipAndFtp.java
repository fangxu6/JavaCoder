import bean.DataLogFileFormat;
import bean.FTPInfo;

import cn.hutool.core.io.FileUtil;
import com.google.common.io.Files;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigUtil;
import util.FTPUtil;
import util.XJSplitUtil;
import util.XJZipUtil;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * className: ZipAndFtp
 * package: com.xijie.FT
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/6/21 15:13
 */
public class ZipAndFtp {
    private static Logger logger = LoggerFactory.getLogger(ZipAndFtp.class);

    public static void main(String[] args) {

        File zipPeriodFile = new File(System.getProperty("user.dir") + "/res/zipPeriod.properties");
        System.out.println(zipPeriodFile.getAbsolutePath());
        ConfigUtil configUtil = new ConfigUtil();
        Configuration config = null;
        try {
            config = configUtil.getConfig(zipPeriodFile);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        String period = config.getString("period");
        if (period.equalsIgnoreCase("ALL")) {
            List<String> zipFileList = zipDataLogFile(period);
            if (zipFileList.size() == 0) {
                System.out.println("no zip file generated.");
                logger.info("no zip file generated.");
            } else {
                System.out.println("upload file.");
                logger.info("upload file.");
//                ftpUpload(zipFileList);
                moveZipList2TargetDir(zipFileList);
            }

        } else if (period.equalsIgnoreCase("PREDAY")) {
            List<String> zipFileList = zipDataLogFile(period);
            if (zipFileList.size() == 0) {
                System.out.println("no zip file generated.");
                logger.info("no zip file generated.");
            } else {
                System.out.println("upload file.");
                logger.info("upload file.");
//                ftpUpload(zipFileList);
                moveZipList2TargetDir(zipFileList);
            }

        }

    }

    private static void moveZipList2TargetDir(List<String> zipFileList) {
        File file = new File("D:/datalog/waitingCheck");

        for (String fileName : zipFileList) {
            File zipFile = FileUtil.file(fileName);
            String zipFileName = zipFile.getName();
            List<String> list = XJSplitUtil.split(zipFileName, '-');
            List<String> subList = new ArrayList<String>(list.subList(0, 3));
            String lastDir = String.join("-", subList);
            subList.set(subList.size() - 1, lastDir);
            String destFileName = "D:/datalog/waitingCheck" + "/" + String.join("/", subList);
            File destFile = new File(destFileName);
            try {
                FileUtils.copyFileToDirectory(zipFile, destFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            logger.info(zipFile);
        }

        if (!file.exists()) {
            try {
                FileUtils.forceMkdir(new File("D:/datalog/waitingCheck"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    private static void ftpUpload(List<String> zipFileList) {
        FTPInfo ftpInfo = new FTPInfo();
        File ftpConfigFile = new File(System.getProperty("user.dir") + "/res/ftp.properties");
        ftpInfo.getFTPINFOConfig(ftpConfigFile);
        for (String zipFile : zipFileList) {
            FTPUtil.upload(ftpInfo, zipFile);
//            logger.info(zipFile);
        }

    }

    public static List<String> zipDataLogFile(String period) {
        //获取配置文件：客户代码、工艺代码和datalog文件目录
        File file = new File(System.getProperty("user.dir") + "/res/custno.properties");
        ConfigUtil configUtil = new ConfigUtil();
        Configuration config = null;
        try {
            config = configUtil.getConfig(file);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        List<String> custCodeList = config.getList(String.class, "custCode");
        List<String> workFlowList = config.getList(String.class, "workFlow");

        //文件名校验
        String zipDir = config.getString("datalogfilePath");
//        String absolutePath = FileUtil.getAbsolutePath(inDir);
//        List<File> files = FileUtil.loopFiles(inDir);
        Collection<File> files = FileUtils.listFiles(new File(zipDir), new String[]{"xls", "cvs", "STD"}, false);
        Iterator<File> it = files.iterator();
        List<String> FTDataLogFileList = new ArrayList<>();
        while (it.hasNext()) {
            File dataLogFile = it.next();
            if (dataLogFile.isFile()) {
                if (period.equalsIgnoreCase("PREDAY")) {
                    ZonedDateTime yesterday = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"))
                            .minusDays(1)
                            .truncatedTo(ChronoUnit.DAYS);
                    Date preDay = Date.from(yesterday.toInstant());
                    ZonedDateTime today = yesterday.plusDays(1);
                    Date currentDay = Date.from(today.toInstant());
                    if (FileUtils.isFileNewer(dataLogFile, preDay) && FileUtils.isFileOlder(dataLogFile, currentDay)) {
                        add2FTDataLogFileList(FTDataLogFileList, dataLogFile, custCodeList, workFlowList);
                    }
                } else if (period.equalsIgnoreCase("ALL")) {
                    add2FTDataLogFileList(FTDataLogFileList, dataLogFile, custCodeList, workFlowList);
                }
            }
        }
        if (FTDataLogFileList.size() == 0) {
            return new ArrayList<>();
        }
        Collections.sort(FTDataLogFileList);
        String filePre = FTDataLogFileList.get(0);
        String nameWithoutExtensionPre = Files.getNameWithoutExtension(filePre);
        String nameWithoutExtensionCurrent;

        List<String> waitingZipFileList = new ArrayList<>();
        List<String> zipList = new ArrayList<>();
        Iterator<String> iterator = FTDataLogFileList.iterator();
        while (iterator.hasNext()) {
            String fileName = iterator.next();

            nameWithoutExtensionCurrent = Files.getNameWithoutExtension(fileName);
            if (nameWithoutExtensionCurrent.equals(nameWithoutExtensionPre) && iterator.hasNext()) {
                waitingZipFileList.add(zipDir + "\\" + fileName);
            } else {
                if (!iterator.hasNext()) {
                    String lastFileName = fileName;
                    waitingZipFileList.add(zipDir + "\\" + lastFileName);
                }
                String zipFile = zipDir + "\\" + nameWithoutExtensionPre + "." + "zip";
                XJZipUtil.zipMultiFiles(waitingZipFileList, zipFile);
                zipList.add(zipFile);
                waitingZipFileList.clear();
                waitingZipFileList.add(zipDir + "\\" + fileName);
            }
            nameWithoutExtensionPre = nameWithoutExtensionCurrent;
        }
        return zipList;
    }

    private static void add2FTDataLogFileList(List<String> ftDataLogFileList, File dataLogFile, List<String> custCodeList, List<String> workFlowList) {
        String fileNmae = dataLogFile.getName();
        logger.info("fileName:" + fileNmae);
        String nameWithoutExtension = Files.getNameWithoutExtension(fileNmae);
        logger.info("nameWithoutExtension:" + Files.getNameWithoutExtension(fileNmae));
        List<String> dataLogFileList = XJSplitUtil.split(nameWithoutExtension, '-');
        logger.info(dataLogFileList.toString());
        if (dataLogFileList.size() > 5 || dataLogFileList.size() < 5) {
            return;
        }
        DataLogFileFormat dataLogFileFormat = new DataLogFileFormat(dataLogFileList.get(0), dataLogFileList.get(1), dataLogFileList.get(2), dataLogFileList.get(3), dataLogFileList.get(4));
        boolean isCheckOut = dataLogFileFormat.checkFormat(custCodeList, workFlowList);
        if (isCheckOut) {
            ftDataLogFileList.add(fileNmae);
        }
    }
}
