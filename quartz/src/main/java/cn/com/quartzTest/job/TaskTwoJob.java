package cn.com.quartzTest.job;

import org.springframework.stereotype.Component;

@Component
public class TaskTwoJob {
    public void check(){
        System.out.println("任务2执行..........");
    }
}
