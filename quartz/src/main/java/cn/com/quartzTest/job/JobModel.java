package cn.com.quartzTest.job;

import cn.com.quartzTest.util.PropertiesUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.SchedulingException;
import org.springframework.stereotype.Component;

@Component
public class JobModel<T> {
    private String jobName ;

    private String jobGroupName ;

    private T jobObj ;

    private String jobObjName  ;

    private String jobMethod ;

    private String cronExpression ;

    private String cronName ;

    private ApplicationContext applicationContext ;

    private final static String SPLIT_MARK = "-";

    private static final String DEFAULT_JOB_GROUP_NAME = "defaultJobGroup";


    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public T getJobObj() {
        return jobObj;
    }

    public void setJobObj(T jobObj) {
        this.jobObj = jobObj;
    }

    public String getJobMethod() {
        return jobMethod;
    }

    public void setJobMethod(String jobMethod) {
        this.jobMethod = jobMethod;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getCronName() {
        return cronName;
    }

    public void setCronName(String cronName) {
        this.cronName = cronName;
    }

    public String getJobObjName() {
        return jobObjName;
    }

    public void setJobObjName(String jobObjName) {
        this.jobObjName = jobObjName;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public JobModel getJob(String jobKey){
        JobModel jobModel = new JobModel() ;

        String jobName = PropertiesUtil.getValue(jobKey + SPLIT_MARK + "jobname");

        jobModel.setJobName(jobName);

        String jobGroupName = PropertiesUtil.getValue(jobKey + SPLIT_MARK + "jobgroupname");

        jobModel.setJobGroupName((jobGroupName==null || "".equals(jobGroupName))?DEFAULT_JOB_GROUP_NAME : jobGroupName);

        String jobObjName = PropertiesUtil.getValue(jobKey + SPLIT_MARK + "jobobjname");

        if (jobObjName == null || "".equals(jobObjName)){
            throw new SchedulingException("请检查配置文件:schedulerJob.properties,jobobjname不能为空");
        }

        T jobBean = (T) applicationContext.getBean(jobObjName);

        jobModel.setJobObj(jobBean);

        String jobMethod = PropertiesUtil.getValue(jobKey + SPLIT_MARK + "jobmethod");

        if (jobMethod == null || "".equals(jobMethod)){
            throw new SchedulingException("请检查配置文件:schedulerJob.properties,jobmethod不能为空");
        }

        jobModel.setJobMethod(jobMethod);

        String cronExpression = PropertiesUtil.getValue(jobKey + SPLIT_MARK + "cronexpression");

        jobModel.setCronExpression(cronExpression);

        String cronName = PropertiesUtil.getValue(jobKey + SPLIT_MARK + "cronname");

        jobModel.setCronName(cronName);

        return jobModel ;
    }
}
