package scheduler.quartz.job;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FTPUtil;
import util.XJZipUtil;

import java.util.List;

/**
 * className: AsinochipCronTrigger
 * package: scheduler.quartz.job
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/8/24 10:30
 */
public class AsinochipCronTrigger implements Job {
    private static final Logger logger = LoggerFactory.getLogger(AsinochipCronTrigger.class);


    @Override
    public void execute(JobExecutionContext context) {
        logger.info("starting...");

        List<String> zipFileList ;
        try {
            zipFileList = XJZipUtil.zipDataLogFile();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        if (zipFileList.isEmpty()) {
            logger.info("no zip file generated.");
        } else {
            logger.info("upload file.");
            FTPUtil.ftpUpload(zipFileList);
//                moveZipList2TargetDir(zipFileList);
        }
    }
}
