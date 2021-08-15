package com.cyp.robot.config.configuration;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Sean on 2018/1/30.
 */
public class FileListener extends FileAlterationListenerAdaptor {
    @Override
    public void onFileChange(File file) {
        super.onFileChange(file);

        try {
            Properties sysProps = PropertiesLoaderUtils.loadProperties(new FileSystemResource(file));
            //加载密码
            if (sysProps.getProperty("putiandi.yunpu.mobileCheckCode") != null
                    && !sysProps.getProperty("putiandi.yunpu.mobileCheckCode").equals("")) {
                FileListenerFactory.map.put("mobileCheckCode", sysProps.getProperty("putiandi.yunpu.mobileCheckCode"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("---------onFileChange:" + file.getAbsolutePath());
    }
}
