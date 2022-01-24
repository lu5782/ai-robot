package com.cyp.robot.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyp.robot.api.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/")
@RestController
@Slf4j
public class IdiomController {

    @Resource
    private RestTemplate restTemplate;
    List<String> existsList = new ArrayList<>();

    @RequestMapping("/getIdiom")
    private void getIdiom() {
        String filePathName = Constants.TEMP_DIR + "/" + "idiom.txt";
        int i = 0;
        String url = String.format("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=28204&query=成语大全&pn=%s&rn=30", i);
        while (true) {
            log.info("=================采集百度成语第 {} 轮=================", i);
//            RestTemplate restTemplate = new RestTemplate();
            JSONObject resultObj;
            try {
                String result = restTemplate.getForObject(url, String.class);
                log.info("result :{}", result);
                resultObj = JSONObject.parseObject(result);
            } catch (Exception e) {
                log.error("采集百度成语 Exception,message:{}", e.getMessage(), e);
                continue;
            }

            if (resultObj == null) {
                log.error("resultObj is null");
                break;
            }

            JSONArray data = resultObj.getJSONArray("data");
            if (data == null) {
                log.error("data is null");
                break;
            }

            JSONObject jsonObject = data.getJSONObject(0);
            JSONArray result = jsonObject.getJSONArray("result");

            ArrayList<String> arrayList = new ArrayList<>();
            result.forEach(e -> {
                JSONObject jsonObject1 = JSONObject.parseObject(e.toString());
                String ename = jsonObject1.getString("ename");
                String jumplink = jsonObject1.getString("jumplink");
                String newJumplink = null;
                try {
                    newJumplink = URLDecoder.decode(jumplink, "utf-8");
                } catch (UnsupportedEncodingException unsupportedEncodingException) {
                    unsupportedEncodingException.printStackTrace();
                }

//                log.info("ename:{}, newJumplink:{}", ename, newJumplink);
                //重复的不处理
                if (!existsList.contains(ename)) {
                    arrayList.add(ename + "," + newJumplink);
                }
            });
            save(filePathName, arrayList);
            i++;
        }
    }


    //在文件最后追加数据
    private static void save(String filePathName, List<String> sourceList) {
        BufferedWriter bufferedWriter = null;
        try {
            //文件最后追加数据
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePathName, true), StandardCharsets.UTF_8));
            for (String source : sourceList) {
                //每次另起一行追加数据
                bufferedWriter.newLine();
                bufferedWriter.write(source);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(bufferedWriter);
        }
    }

}
