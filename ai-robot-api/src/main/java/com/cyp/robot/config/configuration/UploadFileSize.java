package com.cyp.robot.config.configuration;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @Author :      luyijun
 * @Date :        2021/8/15 22:25
 * @Description : 配置上传文件大小
 */
@Configuration
public class UploadFileSize {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大 10M  10*1024*1024
        factory.setMaxFileSize(DataSize.ofBytes(20L * 1024 * 1024 * 1024));
        // 总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofBytes(20L * 1024 * 1024 * 1024));
        return factory.createMultipartConfig();
    }

}
