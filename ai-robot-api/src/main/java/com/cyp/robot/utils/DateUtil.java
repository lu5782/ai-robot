package com.cyp.robot.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @Author :      luyijun
 * @Date :        2021/7/25 19:58
 * @Description :
 */
public class DateUtil {

    public static String timestamp2Str(long timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.of("+8"));
        return formatter.format(localDateTime);
    }

    public static String date2Str(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(localDateTime);
    }


    public static String dateTime2Str(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(localDateTime);
    }

}
