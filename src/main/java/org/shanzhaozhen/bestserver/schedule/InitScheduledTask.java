package org.shanzhaozhen.bestserver.schedule;

import lombok.extern.slf4j.Slf4j;
import org.shanzhaozhen.bestserver.service.TaskService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitScheduledTask implements ApplicationRunner {

    private final TaskService taskService;

    public InitScheduledTask(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            taskService.initTask();
        } catch (Exception e) {
            log.error("定时任务初始化出现异常");
        }
    }

}
