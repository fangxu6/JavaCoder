package util;

import bean.AisinochipDataLogFileFormat;
import bean.CustNoProperties;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import com.google.common.io.Files;
import mybatis.service.DatalogUploadRecordIml;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


/**
 * className: Zip
 * package: com.xijie.FT.util
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/6/21 15:15
 */
public class XJZipUtil {
    private static final Logger logger = LoggerFactory.getLogger(XJZipUtil.class);

    public static void zipMultiFiles(List<String> fileList, String outPath) {
        File[] files = new File[fileList.size()];
        for (int i = 0; i < fileList.size(); i++) {
            files[i] = FileUtil.file(fileList.get(i));
        }

        ZipUtil.zip(FileUtil.file(outPath), false, files);
    }

    public static List<String> zipDataLogFile() throws ConfigurationException {

        //获取配置文件：周期（需要上传最近多少天的文件）
        File zipPeriodFile = new File(System.getProperty("user.dir") + "/res/zipPeriod.properties");
        logger.info(zipPeriodFile.getAbsolutePath());
        ConfigUtil configUtil = new ConfigUtil();
        Configuration config = configUtil.getConfig(zipPeriodFile);
        String period = config.getString("period");
        String hour = config.getString("beginHour");

        //获取配置文件：客户代码、工艺代码和datalog文件目录
        File file = new File(System.getProperty("user.dir") + "/res/custno.properties");
        config = configUtil.getConfig(file);
        String zipType = config.getString("zipType");
        List<String> custCodeList = config.getList(String.class, "custCode");
        List<String> deviceNameList = config.getList(String.class, "deviceName");
        String serialNo = config.getString("serialNo");
        List<String> workFlowList2 = config.getList(String.class, "workFlow");
        List<String> workFlowList = config.getList(String.class, "CPWorkFlow");
        String zipDir = config.getString("datalogfilePath");
        List<String> extentionNameList = config.getList(String.class, "extentionName");


        CustNoProperties custNoProperties = new CustNoProperties(zipType, custCodeList,
                deviceNameList, serialNo, workFlowList2, workFlowList, zipDir, extentionNameList);

        //递归处理，只在zipDir匹配文件
        Collection<File> files = FileUtils.listFiles(new File(zipDir),
                Arrays.stream(extentionNameList.toArray()).toArray(String[]::new),
                true);
        Iterator<File> it = files.iterator();
        List<File> FTDataLogFileList = new ArrayList<>();
        while (it.hasNext()) {
            File dataLogFile = it.next();
            if (dataLogFile.isFile()) {
                ZonedDateTime beginDate = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"))
                        .minusDays(Integer.parseInt(period))
                        .truncatedTo(ChronoUnit.DAYS)
                        .plusHours(Integer.parseInt(hour));
                Date preDay = Date.from(beginDate.toInstant());
                ZonedDateTime today = beginDate.plusDays(Integer.parseInt(period));
                Date currentDay = Date.from(today.toInstant());
                if (FileUtils.isFileNewer(dataLogFile, preDay) && FileUtils.isFileOlder(dataLogFile, currentDay)) {
                    add2FTDataLogFileListWithoutRules(FTDataLogFileList, dataLogFile);
                }
            }
        }
        if (FTDataLogFileList.isEmpty()) {
            return new ArrayList<>();
        }
        return getZipList(FTDataLogFileList, custNoProperties);
    }

    private static List<String> getZipList(List<File> FTDataLogFileList, CustNoProperties custNoProperties) {
        String zipDir = custNoProperties.getDatalogfilePath();
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
            // TODO 是否需要根据后缀名过滤


            if (custNoProperties.getZipType().equals("aisino")) {
                String[] split = nameWithoutExtensionCurrent.split("-", 5);
                String[] split2 = nameWithoutExtensionPre.split("-", 5);
                if (split[0].equals(split2[0]) && split[1].equals(split2[1]) &&
                        split[2].equals(split2[2]) && split[3].equals(split2[3])) {
                    waitingZipFileList.add(datalogFile);
                    if (!fileNameList.isEmpty() && fileNameList.contains(fileName)) {
                        waitingZipFileList.remove(datalogFile);
                    } else {
                        fileNameList.add(fileName);
                    }
                } else {
                    String zipFile = uploadZipFile(zipDir, nameWithoutExtensionPre, waitingZipFileList);
                    zipList.add(zipFile);
                    waitingZipFileList.clear();
                    fileNameList.clear();
                    waitingZipFileList.add(datalogFile);
                }
            } else {
                if (nameWithoutExtensionCurrent.equals(nameWithoutExtensionPre)) {
                    waitingZipFileList.add(datalogFile);
                    if (!fileNameList.isEmpty() && fileNameList.contains(fileName)) {
                        waitingZipFileList.remove(datalogFile);
                    } else {
                        fileNameList.add(fileName);
                    }
                } else {
                    String zipFile = uploadZipFile(zipDir, nameWithoutExtensionPre, waitingZipFileList);
                    zipList.add(zipFile);
                    waitingZipFileList.clear();
                    fileNameList.clear();
                    waitingZipFileList.add(datalogFile);
                }
            }

            nameWithoutExtensionPre = nameWithoutExtensionCurrent;
        }
        String zipFile = uploadZipFile(zipDir, nameWithoutExtensionPre, waitingZipFileList);
        zipList.add(zipFile);

        return zipList;
    }


    private static String uploadZipFile(String zipDir, String nameWithoutExtensionPre, List<File> waitingZipFileList) {
        String zipFile = zipDir + "\\" + nameWithoutExtensionPre + "." + "zip";
//                File[] listArray = (File[]) waitingZipFileList.toArray();
        File[] fileArray = waitingZipFileList.stream().toArray(File[]::new);

        ZipUtil.zip(FileUtil.file(zipFile), false, fileArray);
        DatalogUploadRecordIml.insertIntoTable(nameWithoutExtensionPre + "." + "zip", waitingZipFileList); //初始化表
//                XJZipUtil.zipMultiFiles(waitingZipFileList, zipFile);
        return zipFile;
    }

    private static void add2FTDataLogFileListWithoutRules(List<File> ftDataLogFileList, File dataLogFile) {
        String fileNmae = dataLogFile.getName();
        logger.info("fileName:" + fileNmae);
        String nameWithoutExtension = Files.getNameWithoutExtension(fileNmae);
        logger.info("nameWithoutExtension:" + Files.getNameWithoutExtension(fileNmae));
        ftDataLogFileList.add(dataLogFile);
    }

    private static void add2FTDataLogFileList(List<File> ftDataLogFileList, File dataLogFile, List<String> custCodeList, List<String> workFlowList) {
        String fileNmae = dataLogFile.getName();
        logger.info("fileName:" + fileNmae);
        String nameWithoutExtension = Files.getNameWithoutExtension(fileNmae);
        logger.info("nameWithoutExtension:" + Files.getNameWithoutExtension(fileNmae));
        List<String> dataLogFileList = XJSplitUtil.split(nameWithoutExtension, '-');
        logger.info("name:" + dataLogFileList.toString());
        if (dataLogFileList.size() < 5) {
            return;
        }
        AisinochipDataLogFileFormat dataLogFileFormat = new AisinochipDataLogFileFormat(dataLogFileList.get(0), dataLogFileList.get(1), dataLogFileList.get(2), dataLogFileList.get(3), dataLogFileList.get(4));
        //TODO check规则 不同的客户型号 不同的规则
        boolean isCheckOut = dataLogFileFormat.checkFormat(custCodeList, workFlowList);
        if (isCheckOut) {
            ftDataLogFileList.add(dataLogFile);
        }
    }
}
