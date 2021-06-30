package com.cyp.robot.auth.aspect;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 权限控制
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface Privilege {
    String[] value() default {};
}
