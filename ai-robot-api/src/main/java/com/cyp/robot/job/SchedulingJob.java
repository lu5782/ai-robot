package com.cyp.robot.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Jun on 2021/1/26 22:41.
 */
@Slf4j
@Component
public class SchedulingJob implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        taskRegistrar.addTriggerTask(getRunnable(), getTrigger());
    }

    private Runnable getRunnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.info("SchedulingJob当前时间=" + new Timestamp(System.currentTimeMillis()).toString());
            }
        };
        return runnable;
    }

    private Trigger getTrigger() {
        Trigger trigger = new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                CronTrigger cronTrigger = new CronTrigger("0/10 * * * * ?");
                Date date = cronTrigger.nextExecutionTime(triggerContext);
                return date;
            }
        };
        return trigger;
    }

}
