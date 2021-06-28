package com.cyp.robot.es;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author :      luyijun
 * @Date :        2021/5/19 21:38
 * @Description :
 */
@Data
@ToString
@NoArgsConstructor
public class Employee {
    private String id;
    private Long version;
    String firstName;
    String lastName;
    String age;
    String[] interests;
}