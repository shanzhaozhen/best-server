package org.shanzhaozhen.bestserver;

import org.junit.jupiter.api.Test;
import org.shanzhaozhen.bestserver.schedule.CronTaskRegistrar;
import org.shanzhaozhen.bestserver.schedule.SchedulingRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BestServerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Test
    public void testTask() throws InterruptedException {
        SchedulingRunnable task = new SchedulingRunnable("demoTask", "taskNoParams");
        cronTaskRegistrar.addCronTask(66L, task, "0/10 * * * * ?");

        // 便于观察
        Thread.sleep(3000000);
    }

    @Test
    public void testHaveParamsTask() throws InterruptedException {
        SchedulingRunnable task = new SchedulingRunnable("demoTask", "taskWithParams", new Class<?>[] {String.class, int.class}, "haha", 23);
        cronTaskRegistrar.addCronTask(55L, task, "0/10 * * * * ?");

        // 便于观察
        Thread.sleep(3000000);
    }

}
