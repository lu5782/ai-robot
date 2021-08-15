package com.cyp.robot.config.configuration;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sean on 2018/1/30.
 * 监听器工厂
 * 文件热部署
 */
@Configuration
public class FileListenerFactory {

    public static ConcurrentHashMap map = new ConcurrentHashMap(10);

    /**
     * 系统配置
     */
    public static String configPath;

    static {
        try {
            if (System.getProperty("env") == null) {
//                configPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
                configPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
            } else {
                configPath = System.getProperty("env");
            }
            Properties properties = PropertiesLoaderUtils.loadProperties(new FileSystemResource(configPath + "/application.properties"));
            //加载密码
            if (StringUtils.isNoneEmpty(properties.getProperty("putiandi.yunpu.mobileCheckCode"))) {
                FileListenerFactory.map.put("mobileCheckCode", properties.getProperty("putiandi.yunpu.mobileCheckCode"));
            }
            // 轮询间隔 5 秒
            long interval = TimeUnit.SECONDS.toMillis(5);
            // 创建过滤器
            IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);
//            IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.prefixFileFilter("robot"));
            IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.suffixFileFilter(".java"));
            IOFileFilter filter = FileFilterUtils.or(directories, files);
            // 使用过滤器
            FileAlterationObserver observer = new FileAlterationObserver(new File(configPath), filter);
            observer.addListener(new FileListener());
            //创建文件变化监听器
            FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
            // 开始监控
            monitor.start();
        } catch (Exception e) {

        }
    }
}
