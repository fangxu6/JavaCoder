package scheduler.quartz.job;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FTPUtil;
import util.XJSplitUtil;
import util.XJZipUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * className: AsinochipCronTrigger
 * package: scheduler.quartz.job
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/8/24 10:30
 */
public class MoveCronTrigger implements Job {
    private static final Logger logger = LoggerFactory.getLogger(MoveCronTrigger.class);


    @Override
    public void execute(JobExecutionContext context) {
        logger.info("starting...");

        List<String> fileList ;
        try {
            fileList = XJZipUtil.zipDataLogFile();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        if (fileList.isEmpty()) {
            logger.info("no zip file generated.");
        } else {
            logger.info("upload file.");
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
        }

        if (!file.exists()) {
            try {
                FileUtils.forceMkdir(new File("D:/datalog/waitingCheck"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
