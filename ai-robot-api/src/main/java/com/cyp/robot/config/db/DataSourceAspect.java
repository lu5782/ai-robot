package com.cyp.robot.config.db;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class DataSourceAspect {
    @Before("( @annotation(org.springframework.transaction.annotation.Transactional) && @annotation(com.cyp.robot.config.db.WritingDataSource) )")
    public void doBefore(JoinPoint joinPoint) {
        DataSourceHolder.setDataSource(DataSourceType.READ);
    }

//    @After("(execution( !@annotation(org.springframework.transaction.annotation.Transactional) && !@annotation(com.canyuepo.fitness.bootstrap.data.WritingDataSource)))")
//    public void after(JoinPoint joinPoint) {
//        DataSourceHolder.setDataSource(DataSourceType.WRITE);
//    }

}
