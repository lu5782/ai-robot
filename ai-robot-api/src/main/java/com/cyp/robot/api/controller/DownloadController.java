package com.cyp.robot.api.controller;


import com.alibaba.fastjson.JSONObject;
import com.cyp.robot.api.common.Constants;
import com.cyp.robot.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Author      :   Jun
 * @Date        :   2020/10/29 22:23
 * @Description :   文件上传、下载
 */
@Slf4j
@RestController
@RequestMapping
public class DownloadController {

    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        FileUtils.downloadFile(request, response);
    }


    @RequestMapping("/upload")
    public void upload(@RequestBody JSONObject jsonObject) {
        String src = jsonObject.getString("file");
        File srcFile = new File(src);
        String dist = Constants.UPLOAD_PATH + File.separator + srcFile.getName();
        FileUtils.copyFile(src, dist);
    }


}
