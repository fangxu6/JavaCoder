package scheduler.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * className: HelloJob
 * package: scheduler.quartz
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/8/23 21:54
 */
public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 获取当前时间,并格式化
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateSrting = format.format(date);
        // 业务功能模拟
        System.out.println("开始备份数据库,时间：" + dateSrting);
    }
}
