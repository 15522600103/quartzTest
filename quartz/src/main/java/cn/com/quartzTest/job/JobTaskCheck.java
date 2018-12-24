package cn.com.quartzTest.job;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobTaskCheck implements InitializingBean, ApplicationContextAware {
    @Autowired
    private JobConfig jobConfig ;

    private JobModel jobModel ;

    private ApplicationContext applicationContext ;

    @Override
    public void afterPropertiesSet() throws Exception {
        //任务1
        jobModel = new JobModel<TaskOneJob>() ;
        init(jobModel) ;
        jobModel = jobModel.getJob("taskone") ;
        jobConfig.addJob(jobModel);
        jobConfig.start();
        //任务2
        jobModel = new JobModel<TaskOneJob>() ;
        init(jobModel) ;
        jobModel = jobModel.getJob("tasktwo") ;
        jobConfig.addJob(jobModel);
        jobConfig.start();
    }

    private void init(JobModel jobModel) {
        jobModel.setApplicationContext(applicationContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext ;
    }
}
