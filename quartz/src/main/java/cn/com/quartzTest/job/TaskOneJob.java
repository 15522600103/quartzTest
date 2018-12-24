package cn.com.quartzTest.job;

import org.springframework.stereotype.Component;

@Component
public class TaskOneJob {
    public void check(){
        System.out.println("任务1执行..........");
    }
}
