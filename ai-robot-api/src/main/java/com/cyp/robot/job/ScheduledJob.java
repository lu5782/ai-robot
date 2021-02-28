package com.cyp.robot.job;

import lombok.extern.slf4j.Slf4j;


import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import java.io.*;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Jun on 2020/7/27 20:53.
 */
@Component
@Slf4j
public class ScheduledJob {

    private static String rootDir = "C:\\Users\\jun\\Desktop\\TEMP";
    private static String srcFile = "C:\\Users\\jun\\Desktop\\项目\\图片\\ymgvlk.jpg";


    @Scheduled(cron = "0 0/1 * * * ? ")
    public void get() {
        log.info("FileJob定时任务开始");
        mkDir(srcFile);
    }



    public static void mkDir(String srcFile) {
        String suffix = srcFile.substring(srcFile.lastIndexOf("."));
        String distFile = rootDir + File.separator + getDateString("yyyyMMdd") + File.separator + getDateString("HHmmssSSS") + suffix;
        File file = new File(distFile);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(distFile);
            FileChannel fisChannel = fis.getChannel();
            FileChannel fosChannel = fos.getChannel();
            long size = fisChannel.size();
            fosChannel.transferFrom(fisChannel, 0, size);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(fos);
        }


    }


    public static String getDateString() {
        return getDateString("yyyyMMdd HHmmss");
    }

    public static String getDateString(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(LocalDateTime.now());
    }


}
