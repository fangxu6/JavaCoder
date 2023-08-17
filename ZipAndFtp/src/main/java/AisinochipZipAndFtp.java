import bean.AisinochipDataLogFileFormat;
import bean.DataLogFileFormat;
import bean.FTPInfo;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
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
public class AisinochipZipAndFtp {
    private static Logger logger = LoggerFactory.getLogger(AisinochipZipAndFtp.class);

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
//        if (period.equalsIgnoreCase("ALL")) {
        List<String> zipFileList = zipDataLogFile(period);
        if (zipFileList.size() == 0) {
            System.out.println("no zip file generated.");
            logger.info("no zip file generated.");
        } else {
            System.out.println("upload file.");
            logger.info("upload file.");
            ftpUpload(zipFileList);
//                moveZipList2TargetDir(zipFileList);
        }

//        } else if (period.equalsIgnoreCase("PREDAY")) {
//            List<String> zipFileList = zipDataLogFile(period);
//            if (zipFileList.size() == 0) {
//                System.out.println("no zip file generated.");
//                logger.info("no zip file generated.");
//            } else {
//                System.out.println("upload file.");
//                logger.info("upload file.");
//                ftpUpload(zipFileList);
////                moveZipList2TargetDir(zipFileList);
//            }

//        }

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
            logger.info(zipFile);
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
        List<String> workFlowList = config.getList(String.class, "CPWorkFlow");

        //文件名校验
        String zipDir = config.getString("datalogfilePath");

        List<String> extentionNameList = config.getList(String.class, "extentionName");
        //不递归处理，只在zipDir匹配文件
        Collection<File> files = FileUtils.listFiles(new File(zipDir),
                Arrays.stream(extentionNameList.toArray()).toArray(String[]::new),
                true);
        Iterator<File> it = files.iterator();
        List<File> FTDataLogFileList = new ArrayList<>();
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
        String filePre = FTDataLogFileList.get(0).getName();
        String nameWithoutExtensionPre = Files.getNameWithoutExtension(filePre);
        String nameWithoutExtensionCurrent;

        List<File> waitingZipFileList = new ArrayList<>();
        List<String> zipList = new ArrayList<>();
        Iterator<File> iterator = FTDataLogFileList.iterator();
        List<String> fileNameList = new ArrayList<>();
        while (iterator.hasNext()) {
            File datalogFile = iterator.next();
            String fileName = datalogFile.getName();

            nameWithoutExtensionCurrent = Files.getNameWithoutExtension(fileName);
//            nameWithoutExtensionCurrent.
            String[] split = nameWithoutExtensionCurrent.split("-", 5);
            String[] split2 = nameWithoutExtensionPre.split("-", 5);

//            Arrays.stream(split).limit(3).toString();
            if (split[0].equals(split2[0]) && split[1].equals(split2[1]) && split[2].equals(split2[2]) && split[3].equals(split2[3]) && iterator.hasNext()) {

                waitingZipFileList.add(datalogFile);
                if (fileNameList.size() > 0 && fileNameList.contains(fileName)) {
                    waitingZipFileList.remove(datalogFile);
                } else {
                    fileNameList.add(fileName);
                }
            } else {
                if (!iterator.hasNext()) {
                    String lastFileName = fileName;
                    waitingZipFileList.add(datalogFile);
                    if (fileNameList.size() > 0 && fileNameList.contains(fileName)) {
                        waitingZipFileList.remove(datalogFile);
                    } else {
                        fileNameList.add(fileName);
                    }
                }
                String zipFile = zipDir + "\\" + nameWithoutExtensionPre + "." + "zip";
//                File[] listArray = (File[]) waitingZipFileList.toArray();
                File[] fileArray = waitingZipFileList.stream().toArray(File[]::new);
                ZipUtil.zip(FileUtil.file(zipFile), false, fileArray);
//                XJZipUtil.zipMultiFiles(waitingZipFileList, zipFile);
                zipList.add(zipFile);
                waitingZipFileList.clear();
                fileNameList.clear();
                waitingZipFileList.add(datalogFile);
            }

            nameWithoutExtensionPre = nameWithoutExtensionCurrent;
        }
        return zipList;
    }

    private static void add2FTDataLogFileList(List<File> ftDataLogFileList, File dataLogFile, List<String> custCodeList, List<String> workFlowList) {
        String fileNmae = dataLogFile.getName();
        logger.info("fileName:" + fileNmae);
        String nameWithoutExtension = Files.getNameWithoutExtension(fileNmae);
        logger.info("nameWithoutExtension:" + Files.getNameWithoutExtension(fileNmae));
        List<String> dataLogFileList = XJSplitUtil.split(nameWithoutExtension, '-');
        logger.info("name:"+dataLogFileList.toString());
        if ( dataLogFileList.size() < 5) {
            return;
        }
        AisinochipDataLogFileFormat dataLogFileFormat = new AisinochipDataLogFileFormat(dataLogFileList.get(0), dataLogFileList.get(1), dataLogFileList.get(2), dataLogFileList.get(3), dataLogFileList.get(4));
        boolean isCheckOut = dataLogFileFormat.checkFormat(custCodeList, workFlowList);
        if (isCheckOut) {
            ftDataLogFileList.add(dataLogFile);
        }
    }
}
