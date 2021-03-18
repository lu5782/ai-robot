package com.cyp.robot.job;

import com.cyp.robot.api.common.Constants;
import lombok.extern.slf4j.Slf4j;


import org.apache.commons.io.IOUtils;


import java.io.*;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Jun on 2020/7/27 20:53.
 */
@Slf4j
public class JobHandler {


    public void test() {
        log.info("===============test===================");

    }

    public void mkdir() {
        log.info("===============mkdir===================");
        mkDir(Constants.PICTURE_SOURCE);
    }


    public static void mkDir(String srcFile) {
        String suffix = srcFile.substring(srcFile.lastIndexOf("."));
        String distFile = Constants.TEMP_DIR + File.separator + getDateString("yyyyMMdd") + File.separator + getDateString("HHmmssSSS") + suffix;
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
        } finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(fos);
        }


    }


    public static String getDateString(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(LocalDateTime.now());
    }


}
