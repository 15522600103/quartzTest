package cn.com.quartzTest.job;

import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class JobConfig {
    private StdScheduler scheduler;

    private Logger logger = LoggerFactory.getLogger(JobConfig.class) ;

    public void addJob(JobModel job) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean() ;

        MethodInvokingJobDetailFactoryBean jobDetailFactory = new MethodInvokingJobDetailFactoryBean() ;
        jobDetailFactory.setConcurrent(false);
        jobDetailFactory.setName(job.getJobName());
        jobDetailFactory.setGroup(job.getJobGroupName());
        jobDetailFactory.setTargetObject(job.getJobObj());
        jobDetailFactory.setTargetMethod(job.getJobMethod());
        try {
            jobDetailFactory.afterPropertiesSet();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        CronTriggerFactoryBean cronTrigger = new CronTriggerFactoryBean() ;

        System.out.println(jobDetailFactory.getObject());
        cronTrigger.setJobDetail(jobDetailFactory.getObject());
        cronTrigger.setCronExpression(job.getCronExpression());
        cronTrigger.setName(job.getCronName());

        try {
            cronTrigger.afterPropertiesSet();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        schedulerFactory.setOverwriteExistingJobs(true);
        schedulerFactory.setTriggers(cronTrigger.getObject());

        try {
            schedulerFactory.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }


        scheduler = (StdScheduler) schedulerFactory.getScheduler();
    }


    public void start(){
        try{
            if (scheduler.isShutdown()){
                scheduler.resumeAll();
            }else {
                scheduler.start();
            }
        }catch (SchedulerException ex){
            logger.error("---------任务启动异常: " + ex.getMessage() + "-------------") ;
        }

    }

    public void updateJob(JobModel job) {
        removeJob(job);
        addJob(job);
    }

    public void removeJob(JobModel job) {

        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(job.getJobName(),job.getJobGroupName()));
            scheduler.unscheduleJob(TriggerKey.triggerKey(job.getJobName(),job.getJobGroupName()));
            scheduler.deleteJob(JobKey.jobKey(job.getJobName(),job.getJobGroupName()));
            logger.info("---------删除定时任务：" + job + " -------------");
        } catch (SchedulerException e) {
            logger.error("----------移除定时任务失败：" + job + e.getMessage() + "----------------");
            e.printStackTrace();
        }
    }

    public void pauseJob(JobModel job) {
        try {
            scheduler.pauseJob(JobKey.jobKey(job.getJobName(),job.getJobGroupName()));
            logger.info("---------暂停定时任务：" + job + " ---------");
        } catch (SchedulerException e) {
            logger.error("---------暂停定时任务失败：" + job + e.getMessage() + "---------");
            e.printStackTrace();
        }
    }

    public void resumeJob(JobModel job) {

        try {
            scheduler.resumeJob(JobKey.jobKey(job.getJobName(),job.getJobGroupName()));
            logger.info("---------恢复定时任务：" + job + " ---------");
        } catch (SchedulerException e) {
            logger.error("---------恢复定时任务失败：" + job + e.getMessage() + "---------");
            e.printStackTrace();
        }
    }
}
