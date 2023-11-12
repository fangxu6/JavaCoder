package scheduler.quartz;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import scheduler.quartz.job.AsinochipCronTrigger;
import scheduler.quartz.job.MoveCronTrigger;
import util.ConfigUtil;
import util.FTPUtil;

import java.io.File;

/**
 * className: HelloCronTriggerDemo
 * package: scheduler.quartz
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2023/8/23 22:03
 */
public class MoveCronTriggerDemo {
    public static void main(String[] args) throws SchedulerException {
        // 1、调度器 - 从工厂获取调度实例
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 2、任务实例 - 执行的任务对象
        JobDetail job = JobBuilder.newJob(MoveCronTrigger.class)
                .withIdentity("job1", "group1") // 任务名称,组名称
                .build();
        int min = 5;
        int hour = 8;
        String cronPeriod = "0" + " " + min + " " + hour + " " + "* * ?";
        // 3、触发器 - 控制执行次数和执行时间
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1") // 同上
                .startNow() // 立刻启动
                .withSchedule(CronScheduleBuilder.cronSchedule(cronPeriod))   // Cron 表达式
                .build();

        // 调度器关联触发器,并启动
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }
}
