package com.cyp.robot.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by luyijun on 2021/1/26 22:41.
 */
@Slf4j
@Component
public class SchedulingJob implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        for (JobDetail jobDetail : JobDetail.values()) {
            taskRegistrar.addTriggerTask(getRunnable(jobDetail.clazz, jobDetail.methodName), getTrigger(jobDetail.cron));
        }
    }

    private Runnable getRunnable(Class<?> clazz, String methodName) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Method declaredMethod = clazz.getDeclaredMethod(methodName);
                    Object invoke = declaredMethod.invoke(clazz.newInstance());
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private Trigger getTrigger(String cron) {
        return new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                CronTrigger cronTrigger = new CronTrigger(cron);
                return cronTrigger.nextExecutionTime(triggerContext);
            }
        };
    }

    enum JobDetail {
        test(JobHandler.class, "test", "0 0/10 * * * ?", "test"),
        mkdir(JobHandler.class, "mkdir", "0 0/10 * * * ?", "复制文件"),
        ;
        Class<?> clazz;
        String methodName;
        String cron;
        String desc;

        JobDetail(Class<?> clazz, String methodName, String cron, String desc) {
            this.clazz = clazz;
            this.methodName = methodName;
            this.cron = cron;
            this.desc = desc;
        }
    }

}
