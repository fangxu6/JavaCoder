import bean.AisinochipDataLogFileFormat;
import bean.FTPInfo;
import cn.hutool.core.io.FileUtil;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import mybatis.service.DatalogUploadRecordIml;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;
import util.FTPUtil;
import util.XJSplitUtil;
import util.XJZipUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * className: ChipTestZipAndFtp
 * package: com.xijie.FT
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/6/21 15:13
 */
@Slf4j
public class ChipTestZipAndFtp {

    public static void main(String[] args) {

        log.info("starting...");

        List<String> zipFileList;
        try {
            zipFileList = XJZipUtil.zipDataLogFile();
        } catch (ConfigurationException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
        if (zipFileList.isEmpty()) {
            log.info("no zip file generated.");
        } else {
            log.info("upload file.");
            FTPUtil.ftpUpload(zipFileList);
//                moveZipList2TargetDir(zipFileList);
        }


    }

    private static void moveZipList2TargetDir(List<String> zipFileList) {
        File file = new File("D:/datalog/waitingCheck");

        for (String fileName : zipFileList) {
            File zipFile = FileUtil.file(fileName);
            String zipFileName = zipFile.getName();
            List<String> list = XJSplitUtil.split(zipFileName, '-');
            List<String> subList = new ArrayList<>(list.subList(0, 3));
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
            boolean upload = FTPUtil.upload(ftpInfo, zipFile);
            if (upload) {
                DatalogUploadRecordIml.updateByZipFile(zipFile);
            }
        }

    }

    private static void add2FTDataLogFileList(List<File> ftDataLogFileList, File dataLogFile, List<String> custCodeList, List<String> workFlowList) {
        String fileNmae = dataLogFile.getName();
        log.info("fileName:" + fileNmae);
        String nameWithoutExtension = Files.getNameWithoutExtension(fileNmae);
        log.info("nameWithoutExtension:" + Files.getNameWithoutExtension(fileNmae));
        List<String> dataLogFileList = XJSplitUtil.split(nameWithoutExtension, '-');
        log.info("name:" + dataLogFileList);
        if (dataLogFileList.size() < 5) {
            return;
        }
        AisinochipDataLogFileFormat dataLogFileFormat = new AisinochipDataLogFileFormat(dataLogFileList.get(0), dataLogFileList.get(1), dataLogFileList.get(2), dataLogFileList.get(3), dataLogFileList.get(4));
        boolean isCheckOut = dataLogFileFormat.checkFormat(custCodeList, workFlowList);
        if (isCheckOut) {
            ftDataLogFileList.add(dataLogFile);
        }
    }
}
